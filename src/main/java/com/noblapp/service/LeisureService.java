package com.noblapp.service;

import com.noblapp.model.support.AbstractCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.service.support.AbstractService;
import com.noblapp.support.ErrorCode;
import com.noblapp.support.NoblappException;
import com.noblapp.support.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.IntStream;

/**
 * 레져용 서비스. 인자와 결과는 아래 spec문서 참조.<br>
 * <a href="https://github.com/noblapp/noblappServer/wiki/leisure-api">leisure api 문서</a>
 * @author juniverse
 */
@Service
public class LeisureService extends AbstractService {
	private Logger logger = LoggerFactory.getLogger(LeisureService.class);

	@Autowired private ServiceConfig serviceConfig;

	/**
	 * 근처 섹터 반환.
	 */
	@Transactional
	public Map<String, Object> getNearSector(ParamVo param, Map<String, Object> cmd) throws Exception {
		List<Map<String, Object>> sectors = db.getNearSector(cmd);
		if (sectors == null || sectors.size() <= 0) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		return sectors.get(0);
	}


	// 아래 함수들을 테스트 해야 함..
	@Transactional
	public Map<String, Object> updateTrekking(ParamVo param, Map<String, Object> cmd) throws Exception {
		List<Map<String, Object>> checkedSectors;

		// 1. 일단 가까운 섹터를 찾는다... getNearSector.. 근데 체크 포인트여야 함...
		cmd.put("tcs_type", 2);
		checkedSectors = db.getNearSector(cmd);
		if (checkedSectors == null || checkedSectors.size() <= 0) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_NEAREST_CP);
		}

		logger.info("checkedSectors size? " + checkedSectors.size());

		Map<String, Object> normalCourseSector = null;
		
		int prev_tc_type = -1;		// 같은 코스 타입의 섹터인지 확인해보기...
		boolean eventTrekkingInserted = false;
		// 일단 uid 받고..
		int uid = (int)cmd.get("uid");
		int sectorPoint = serviceConfig.getTrekkingPoint();

		for (Map<String, Object> checkedSector : checkedSectors) {

			logger.info("checkedSector[{}]", checkedSector);

			// 해당 섹터의 코스 정보도 일단 가져와...
			Map<String, Object> trekCourseInfo = db.getTrekCourse(checkedSector);
			if (trekCourseInfo == null) {
				continue;
//				logger.error("no related data. cmd[{}]", cmd);
//				throw new NoblappException(ErrorCode.NO_RELATED_DATA);
			}
			
			int tc_type = (int) trekCourseInfo.get("tc_type");
			// 전에 처리했던 코스 타입과 같은 녀석이야.. 무시해.
			if (prev_tc_type == tc_type)
				continue;
			// 거리 체크... 이거는 trek course 에서...
			double minDistance = (double) trekCourseInfo.get("chk_dist");
			// 혹시 없으면 default로 설정하자.
			if (minDistance <= 0.0)
				minDistance = serviceConfig.getTrekMinDistance();
			
			logger.info("minDistance[{}]", minDistance);

			double distance = (double)checkedSector.get("distance");
			if (distance > minDistance) {
				// 멀어... 가까운게 없음.
				continue;
//				throw new NoblappException(ErrorCode.NO_NEAREST_CP);
			}

			long tc_id = (long) checkedSector.get("tc_id");		// 이제 알았음... unsigned 데이터이면 long으로 해야 함..

			// 날짜 체크.. 같은 날의 기록은 남기지 않는다.
			Map<String, Object> p = new HashMap<>();
			p.put("uid", uid);
			p.put("tc_id", tc_id);
			p.put("g_idx", checkedSector.get("g_idx"));
			p.put("s_idx", checkedSector.get("s_idx"));
			p.put("tcs_id", checkedSector.get("tcs_id"));
			p.put("issued_date", new Date());
			p.put("point", sectorPoint);
			p.put("tc_type", tc_type);		// event냐 아니냐 판단...
			try {
				logger.info(p.toString());
				db.insertUserTrekkingLog(p);
			} catch (Exception e) {
				// 여기도 딱히 오류가 아닐 수 있는데...
				continue;
//				return checkedSector;
			}

			// 위에서 중복이면 여기로 오지 않음... 왜? exception 때문에...
			logger.info("log complete!");

			// 이전 처리했던 tc_type 기억해.
			prev_tc_type = tc_type;
		
			// 이벤트 트랙인 경우는....
			if (tc_type == 2) {
				// 이벤트 트래킹 용도 처리해야 함!!!
				// 결과 리턴용.. 근데 
				eventTrekkingInserted = true;
				if (normalCourseSector == null)
					normalCourseSector = checkedSector;		// 결과 리턴용...

			} else {
				// 일반 코스인 경우는 다음을 처리...
				normalCourseSector = checkedSector;		// 결과 리턴용...

				// 유저에게 포인트 줘..
				int updateResult = db.updateUserPoint(uid, sectorPoint);
				if (updateResult != 1) {
					logger.error("couldn't update user point. cmd[{}]", cmd);
					throw new NoblappException(ErrorCode.UNKNOWN);
				}
			}
			

			// 이제...코스 완료 확인...
			// 완주 처리...
			// 1. 각 코스 완료 확인.
			// 체크 포인트 갯수를 가져와야 함...
			// 완주 인증서 : 코스 별 완주 기록. 등록되면 코스 체크포인트 완주 기록은 초기화. -> user_trekking_cert
			// 또 완주를 할 수 있음....
			String checkPointCounts =  (String) trekCourseInfo.get("counts");		// 이 카운트는 모든 sector들이네.. 이건 아냐...
			//		String[] checkPoints = checkPointCounts.split(",");
			int[] checkPoints = Arrays.asList(checkPointCounts.split(","))
					.stream()
					.map(String::trim)
					.mapToInt(Integer::parseInt).toArray();
			int incompleteCount = db.getIncompleteUserTrekCount(p);
			int allCheckPoints = IntStream.of(checkPoints).sum();
			if (incompleteCount >= allCheckPoints) {
				// 기록은 됬지만 완주 처리를 하지 않은 것들이 같거다 더 많으면 해당 코스 완주 완료....
				db.updateTrekCourseCmplt(p);

				// 완주 기록 넣어주자...
				db.insertTrekCertCmplt(uid, tc_id);

				checkedSector.put("tc_cmplt", true);

				// 그럼 이제 모든 코스가 다 완료 했는지 확인하자... 근데 이건 일반 코스만 했을 때만...
				if (tc_type == 1) {
					int completeCourseCount = db.getUserTrekkingCertCmplt(uid);
					int totalCourseCount = db.getTrekCourseCount(1);			// 여기서는 일반 코스 갯수만 주면 됨!!!
					if (completeCourseCount >= totalCourseCount) {
						// 모든 코스 완료... 명예의 전당에 올려주자... 다만 try 문으로 감싸는 이유는 중복이면 exception이 나기 때문에.. 근데 그건 오류가 아니라서...
						try {
							db.insertUserTrekkingFame(uid);

							checkedSector.put("fame_cmplt", true);

						} catch (Exception e) {
							// 진짜 오류는 아님.. 그냥 중복만 안할 뿐...
							logger.info("log complete!");
						}
					}
				}
			}
		}

		if (normalCourseSector == null) {
			logger.error("normalCourseSector is null");
			throw new NoblappException(ErrorCode.NO_NEAREST_CP);
		}

		if (eventTrekkingInserted) {
			normalCourseSector.put("event", true);
		}
		return normalCourseSector;
	}

	@Transactional
	public Map<String, Object> getUserTrekkingTotalPoints(ParamVo param, AbstractCmd cmd) throws Exception {
		int total = db.getUserTrekkingTotalPoints(cmd.getUid());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		return map;
	}

	
	
	@Transactional
	public Map<String, Object> getTrekCourseList(ParamVo param, Map<String, Object> cmd) throws Exception {
		
		int count = (int)cmd.get("count");
		int page = ((int)cmd.get("page") - 1) * count;
		// from, count
		List<Map<String, Object>> courses = db.getTrekCourses(page, count, 1);
		if (courses == null || courses.isEmpty()) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("list", courses);
		return map;
	}
	
	@Transactional
	public Map<String, Object> getTrekCourseSectors(ParamVo param, Map<String, Object> cmd) throws Exception {
		
		List<Map<String, Object>> sectors = db.getCourseSectors(cmd);
		if (sectors == null || sectors.isEmpty()) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", sectors);
		return map;
	}
	
	@Transactional
	public Map<String, Object> getUserTrekkingCourse(ParamVo param, Map<String, Object> cmd) throws Exception {
		List<Map<String, Object>> sectorList = db.getUserTrekkingLog(cmd);
		if (sectorList == null || sectorList.size() <= 0) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		int total = 0;
		for (Map<String, Object> sector : sectorList) {
			total += (int)sector.get("point");
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("list", sectorList);
		return map;
	}

	@Transactional
	public Map<String, Object> getUserTrekkingCert(ParamVo param, AbstractCmd cmd) throws Exception {
		List<Map<String, Object>> trekkingCertInfo = db.getUserTrekkingCert(cmd.getUid());
		if (trekkingCertInfo == null || trekkingCertInfo.size() <= 0) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("list", trekkingCertInfo);
		return map;
	}

	@Transactional
	public Map<String, Object> getUserTrekkingFame(ParamVo param, AbstractCmd cmd) throws Exception {
		List<Map<String, Object>> trekFameInfo = db.getUserTrekkingFame(cmd.getUid());
		if (trekFameInfo == null || trekFameInfo.size() <= 0) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", trekFameInfo);
		return map;
	}
	
	@Transactional
	public Map<String, Object> getTrekEventLog(ParamVo param, Map<String, Object> cmd) throws Exception {

		Map<String, Object> result = db.getUserEventTrekkingLog(cmd);
		if (result == null) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		return result;
	}

	@Transactional
	public Map<String, Object> getTrekkingGroupByPid(Map<String, Object> cmd) throws Exception {
		List<Map<String, Object>> list = db.getTrekkingGroupByPid(cmd);
		if (list == null || list.size() <= 0) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		return map;
	}

	@Transactional
	public Map<String, Object> getTrekkingCourseByCid(Map<String, Object> cmd) {
		List<Map<String, Object>> list = db.getTrekkingCourseByCid(cmd);
		if (list == null || list.size() <= 0) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("list", list);
		return map;
	}

	
}