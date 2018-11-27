package com.noblapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.noblapp.model.cmd.LocationCmd;
import com.noblapp.model.cmd.SpendConfirmCmd;
import com.noblapp.model.cmd.SpendReqCmd;
import com.noblapp.model.cmd.StoreCmd;
import com.noblapp.model.cmd.StoreUpdateCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.model.vo.ShortStoreInfo;
import com.noblapp.model.vo.SpendingInfo;
import com.noblapp.model.vo.StoreInfo;
import com.noblapp.model.vo.UserInfo;
import com.noblapp.model.vo.VSpendingInfo;
import com.noblapp.service.support.AbstractService;
import com.noblapp.support.ErrorCode;
import com.noblapp.support.NoblappException;

/**
 * 쇼핑용 서비스. 인자와 결과는 아래 spec문서 참조.<br>
 * <a href="https://github.com/noblapp/noblappServer/wiki/shopping-api">shopping api 문서</a>
 * @author juniverse
 */
@Service
public class ShopService extends AbstractService {
	private Logger logger = LoggerFactory.getLogger(ShopService.class);
	
	private boolean CHECK_OWNER = true;
	
	/**
	 * 특정 api에서 상점 주인만 사용할 수 있게 해주는거...
	 */
	private boolean isOwner(int masterUid, int uid) throws Exception {
		if (!CHECK_OWNER)
			return true;

		if (masterUid != uid) {
			logger.error("not the owner! {}, {}", masterUid, uid);
			throw new NoblappException(ErrorCode.INVALID_OWNER);
		}

		return true;
	}
	
	/**
	 * 제휴 상점 목록 가져오기..
	 */
	@Transactional
	public Map<String, Object> getStoreList(ParamVo param, LocationCmd cmd) throws Exception {
		List<ShortStoreInfo> stores = db.getPointStoreList(cmd);
		if (stores == null || stores.size() <= 0) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", stores);
		return map;
	}
	
	/**
	 * 상점 포인트 가져오기.
	 */
	@Transactional
	public Map<String, Object> getStorePoint(ParamVo param, StoreCmd cmd) throws Exception {
		StoreInfo storeInfo = db.getStoreInfo(cmd.getSid());
		if (storeInfo == null) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}
		
		// 주인 체크. 실패면 익셉션
		isOwner(storeInfo.getMaster_uid(), cmd.getUid());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("point", storeInfo.getPoint());
		return map;
	}

	/**
	 * 포인트 사용 요청
	 */
	@Transactional
	public Map<String, Object> setUserSpendingReq(ParamVo param, SpendReqCmd cmd) throws Exception {
		// 이건 사용자가 요청하는 것...

		// 포인트 부족 체크
		UserInfo userInfo = db.getUserWithUid(cmd.getUid());
		if (userInfo == null) {
			logger.error("no such user. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_USER);
		}
		
		if (userInfo.getPoint() < cmd.getPoint()) {
			logger.error("no such user. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NOT_ENOUGH_POINT);
		}

		// uid가 sid에 point를 쓰겠다고 user_spending 테이블에 추가해. us_id가 발급이 되겠지.
		int result = db.insertUserSpending(cmd);
		if (result != 1) {
			logger.error("couldn't insert user spending info. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.UNKNOWN);
		}
		
		// 사용자한테서 point를 빼... 에스크로~~~
		result = db.updateUserPoint(cmd.getUid(), -cmd.getPoint());
		if (result != 1) {
			logger.error("couldn't insert user spending info. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.UNKNOWN);
		}
		
		// 다 성공했음.. 남은 포인트 줄까?
		userInfo = db.getUserWithUid(cmd.getUid());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("point", userInfo.getPoint());
		return map;
	}

	/**
	 * 포인트 사용 요청 리스트 가져오기.
	 */
	@Transactional
	public Map<String, Object> getUserSpendingReqList(ParamVo param, Map<String, Object> cmd) throws Exception {
		// user_spending 테이블에 sid에 요청이 온 리스트를 줘
		// 여기에는 두가지 케이스가 있음. 1. 사용자가 요청하는거 (취소를 위해???), 2. 상점 주인이 요청하는거... (승인 또는 취소를 위해??)
		// 1. uid만 있음. sid는 -1
		// 2. uid(상점 주인) 와 sid 둘다 존재...
		int uid = (int)cmd.get("uid");
		int sid = -1;
		if (cmd.containsKey("sid"))
			sid = (int)cmd.get("sid");
		else
			cmd.put("sid", -1);

		if (sid > 0) {
			// 상점 주인이 요청.
			StoreInfo storeInfo = db.getStoreInfo(sid);
			if (storeInfo == null) {
				logger.error("no related data. cmd[{}]", cmd);
				throw new NoblappException(ErrorCode.NO_RELATED_DATA);
			}
			
			// 주인 체크. 실패면 익셉션
			isOwner(storeInfo.getMaster_uid(), uid);
		}

		List<Map<String, Object>> spendingList = db.getSpendingReqList(cmd);

		if (spendingList == null || spendingList.size() <= 0) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", spendingList);
		return map;
	}
	
	/**
	 * 포인트 사용 요청 처리.
	 */
	@Transactional
	public Map<String, Object> setUserSpendingRes(ParamVo param, SpendConfirmCmd cmd) throws Exception {
		// 위에서 본 리스트에서 처리할 녀석을 이 api에 날려줘... 처리 하겠다고...
		// 사용자가 상점 주인인지 확인 해야 함!!!
		// 이때 유저한테서 point 빼고, 상점한테 point를 줘야 하는거 아닌가???? 다 승인하기 전에 취소 할 수가 있으니까????
		// 이것도 두가지 케이스. 1. 유저가 취소하는거... 2. 상점 주인이 취소 또는 승인 하는거...
	
		logger.info("setUserSpendingRes. cmd[{}]", cmd);
		
		int updateResult = 0;
		
		// 일단 승인 요청 내역 가져와..
		SpendingInfo spendingInfo = db.getSpendingReq(cmd.getUs_id());
		if (spendingInfo == null) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}
		
		// 1: 승인 (상점 주인만), 2: 거절 (사용자, 상점 주인 둘다 가능)
		if (cmd.getStatus() == 1) {
			// 상점 주인인지 확인...
			StoreInfo storeInfo = db.getStoreInfo(spendingInfo.getSid());
			if (storeInfo == null) {
				logger.error("no related data. cmd[{}]", cmd);
				throw new NoblappException(ErrorCode.NO_RELATED_DATA);
			}
			
			// 주인 체크. 실패면 익셉션
			isOwner(storeInfo.getMaster_uid(), cmd.getUid());
			
			// 승인 됬으니까 상점으로 포인트 옮겨...
			updateResult = db.updateStorePoint(spendingInfo.getSid(), spendingInfo.getPoint());
			if (updateResult != 1) {
				logger.error("couldn't update store point!. cmd[{}]", cmd);
				throw new NoblappException(ErrorCode.UNKNOWN);
			}

		} else {
			// 거절 케이스.
			// 유저 포인트 돌려주기!!!!
			updateResult = db.updateUserPoint(spendingInfo.getUid(), spendingInfo.getPoint());
			if (updateResult != 1) {
				logger.error("couldn't return user point. cmd[{}]", cmd);
				throw new NoblappException(ErrorCode.UNKNOWN);
			}
		}

		// spending 정보 수정.
		updateResult = db.updateUserSpendingPoint(cmd);
		if (updateResult != 1) {
			logger.error("couldn't update user spending info. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.UNKNOWN);
		}
		
		// 다 잘 되면... 뭘 주지???
		StoreInfo storeInfo = db.getStoreInfo(spendingInfo.getSid());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("point", storeInfo.getPoint());
		return map;
	}
	
	/**
	 * 포인트 거래 내역 가져오기.
	 */
	@Transactional
	public Map<String, Object> getStoreIncome(ParamVo param, StoreCmd cmd) throws Exception {
		StoreInfo storeInfo = db.getStoreInfo(cmd.getSid());
		if (storeInfo == null) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}
		
		// 주인 체크. 실패면 익셉션
		isOwner(storeInfo.getMaster_uid(), cmd.getUid());

		List<SpendingInfo> spendingList = db.getSpendingList(cmd.getSid());
		int total = 0;
		for (SpendingInfo info : spendingList) {
			total += info.getPoint();
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("list", spendingList);
		return map;
	}

	/**
	 * 제휴 상점 데이터 업데이트
	 */
	@Transactional
	public Map<String, Object> updateStore(ParamVo param, StoreUpdateCmd cmd) throws Exception {
		StoreInfo storeInfo = db.getStoreInfo(cmd.getSid());
		if (storeInfo == null) {
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}
		
		// 주인 체크. 실패면 익셉션
		isOwner(storeInfo.getMaster_uid(), cmd.getUid());

		if (db.updateStoreInfo(cmd) != 1) {
			// update가 안됬어???
			logger.error("no related data. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.UNKNOWN);
		}

		return null;
	}

	
	

}
