package com.noblapp.service;

import com.noblapp.model.vo.StringInfo;
import com.noblapp.service.support.AbstractService;
import com.noblapp.support.ErrorCode;
import com.noblapp.support.NoblappException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 기타 서비스. 인자와 결과는 아래 spec문서 참조.<br>
 * <a href="https://github.com/noblapp/noblappServer/wiki/etc-api">etc api 문서</a>
 * @author juniverse
 */
@Service
public class EtcService extends AbstractService {
	private Logger logger = LoggerFactory.getLogger(EtcService.class);
	
	@Transactional
	public Map<String, Object> getWeather() throws Exception {
		Map<String, Object> map = db.getWeather();
		if (map == null) {
			logger.error("no related data.");
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}
		
		return map;
	}
	
	@Transactional
	public int insertWeather(Map<String, Object> cmd) throws Exception {		
		db.deleteWeather();
		return db.insertWeather(cmd);
	}
	
	@Transactional
	public Map<String, Object> getStringTable(String lang) {
		List<StringInfo> strings = db.getStringTable(lang);

		Map<String, Object> map = new HashMap<>();
		map.put("list", strings);
		return map;
	}
	
	
	
	// 트레킹 관련 통계...
	@Transactional
	public Map<String, Object> getTrekkingStats() throws Exception {
		return db.getTrekkingStats();
	}
	
	// 방문자 관련 통계...
	@Transactional
	public Map<String, Object> getVisitorStats() throws Exception {
		Map<String, Object> map = new HashMap<>();
		
		// FIXME 문제는 만약 빈 날짜가 있다면??? 그걸 몰라.... {d, counts}
		List<Map<String, Object>> daily = db.getVisitorsCountBy("daily", 10);
		List<Map<String, Object>> monthly = db.getVisitorsCountBy("monthly", 10);
		List<Map<String, Object>> yearly = db.getVisitorsCountBy("yearly", 10);

		map.put("today", db.getVisitorTodayCount());
		map.put("total", db.getVisitorTotalCount());
		map.put("last_ten_days", daily);
		map.put("last_ten_months", monthly);
		map.put("last_ten_years", yearly);

		return map;
	}
	
	// 방문자 통계용...
	private SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	private SimpleDateFormat monthFormat = new SimpleDateFormat("YYYY-MM");
	private SimpleDateFormat yearFormat = new SimpleDateFormat("YYYY");
	@Transactional
	public void insertVisitorStats(HttpServletRequest request) throws Exception {
		String servlet = request.getServletPath();
		
		// CMS는 제외... 통계 보기 제외...
		// root는 front-end접속 용이긴 한데, 어짜피 다른 api들 때문에 기록이 남어...
		// 이미지 / 파일 다운로드 제외
		if (!servlet.equals("/") || !servlet.startsWith("/cms") || !servlet.startsWith("/etc/stats") || !servlet.startsWith("/assets")) {
			Date now = new Date();

			Map<String, Object> cmd = new HashMap<>();
			cmd.put("date", dateFormat.format(now));
			cmd.put("month", monthFormat.format(now));
			cmd.put("year", yearFormat.format(now));
			cmd.put("remote_addr", request.getRemoteAddr());
			cmd.put("servlet", servlet);
			db.insertVisitorLog(cmd);
		}

	}


	
}
