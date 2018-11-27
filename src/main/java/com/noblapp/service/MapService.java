package com.noblapp.service;

import com.noblapp.service.support.AbstractService;
import com.noblapp.support.ErrorCode;
import com.noblapp.support.NoblappException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 지도 관련 서비스. 인자와 결과는 아래 spec문서 참조.<br>
 * <a href="https://github.com/noblapp/noblappServer/wiki/location-api">location api 문서</a>
 * @author juniverse
 */
@Service
public class MapService extends AbstractService {
	private Logger logger = LoggerFactory.getLogger(MapService.class);
	
	
	/**
	 * 장소 카테고리 가져오기.
	 */
	@Transactional
	public Map<String, Object> getCategories(String lang) throws Exception {
		List<Map<String, Object>> categories = db.getPlaceCategories(lang, 0, 0);

		if (categories == null || categories.size() <= 0) {
			logger.error("getSpots. no related data.");
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("list", categories);
		return map;
	}

	/**
	 * 지도상 주요 장소 가져오기.
	 */
	@Transactional
	public Map<String, Object> getMappedPlaces(long categoryid, String lang) throws Exception {
		List<Map<String, Object>> spots = db.getMappedPlaces(categoryid, lang);

		if (spots == null || spots.size() <= 0) {
			logger.error("getSpots. no related data.");
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("list", spots);
		return map;
	}

	/**
	 * 주요 장소 목록 가져오기.
	 */
	@Transactional
	public Map<String, Object> getPlaces(long categoryid, String lang, int page, int count) throws Exception {

		if (page < 0)
			page = 0;

		int from = page * count;
		List<Map<String, Object>> spots = db.getPlacesList(lang, categoryid, from, count);
		if (spots == null || spots.size() <= 0) {
			logger.error("getSpots. no related data.");
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", spots);
		return map;
	}
	
	/**
	 * 주요 장소의 상세 정보 가져오기.
	 */
	@Transactional
	public Map<String, Object> getPlacesDetail(long pid, String lang) throws Exception {
	Map<String, Object> spot = db.getPlacesDetail(lang, pid);

		if (spot == null) {
			logger.error("getSpotDetail. no related data.");
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		return spot;
	}
	
	/**
	 * 장소 검색.
	 */
	@Transactional
	public Map<String, Object> searchPlaces(String lang, String value) throws Exception {
		value = "%" + value + "%";
		List<Map<String, Object>> results = db.searchPlaces(lang, value);

		if (results == null || results.size() <= 0) {
			logger.error("searchPlaces. no related data.");
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> list = new HashMap<>();
		list.put("list", results);
		return list;
	}
	
	
	@Transactional
	public Map<String, Object> getImaps(String lang) throws Exception {
		List<Map<String, Object>> results = db.getImaps(lang);

		if (results == null || results.size() <= 0) {
			logger.error("getImaps. no related data.");
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> list = new HashMap<>();
		list.put("list", results);
		return list;
	}
	
	@Transactional
	public Map<String, Object> getAllPlaces(String lang) throws Exception {		
		List<Map<String, Object>> results = db.getAllPlaces(lang);

		if (results == null || results.size() <= 0) {
			logger.error("allPlaces. no related data.");
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> list = new HashMap<>();
		list.put("list", results);
		return list;
	}

	@Transactional
	public Map<String, Object> getSpotsList(String lang) throws Exception {
		List<Map<String, Object>> imaps = db.getImaps("ko");
		List<Map<String, Object>> spotsList = db.getSpotsList(lang);
		Map<String, Object> results_map = new HashMap<>();

		for(int i=0, n=imaps.size(); i<n; i++){			
			int mid = Integer.parseInt(imaps.get(i).get("mid").toString()) ;
			results_map.put("spot_"+mid, spotsList.stream().filter(spot -> Integer.parseInt(spot.get("mid").toString()) == mid).collect(Collectors.toList()));
		}
		
		if (results_map == null || results_map.size() <= 0) {
			logger.error("getSpotsList. no related data.");
			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
		}

		Map<String, Object> list = new HashMap<>();
		list.put("map", results_map);
		
		return list;
	}

	@Transactional
	public Map<String, Object> getSpotDetail(String lang, long pid) throws Exception {
		return db.getSpotDetail(lang, pid);
	}


	@Transactional
	public void updateTouchLog(String table_type, long pid) throws Exception {
		db.updateTouchLog(table_type, pid);
	}

	@Transactional
	public void deleteTouchLog(long tid) throws Exception {
		db.deleteTouchLog(tid);
	}
	
	
}
