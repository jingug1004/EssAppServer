package com.noblapp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.noblapp.model.vo.StringInfo;
import com.noblapp.service.support.AbstractService;

/**
 * 메인 서비스 클래스.<br>
 * 특정 지자체에만 적용되는 서비스가 있다면 이 클래스를 상속 받아서 필요한 서비스 메소드만 구현하면 됨.
 * @author juniverse
 */
@Service
public class MainService extends AbstractService {
	private Logger logger = LoggerFactory.getLogger(MainService.class);
	
	public Map<String, Object> home() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("home", "yes");
		return map;
	}

	
	public Map<String, Object> getMaster() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("home", "yes");
		return map;
	}


}
