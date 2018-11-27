package com.noblapp.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noblapp.controller.support.AbstractController;
import com.noblapp.model.support.ParamCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.service.EtcService;
import com.noblapp.support.ServiceConfig;

/**
 * 기타 관련 컨트롤러 (/etc)
 * @author juniverse
 */
@RestController
@RequestMapping("/etc")
public class EtcController extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(EtcController.class);

	@Autowired private EtcService etc;
	@Autowired private ServiceConfig serviceConfig;
	
	@RequestMapping("/weather")
	public Map<String, Object> weather(){
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		
		try{
			param = super.createParamVo(new ParamCmd());

			super.makeSuccessMap(param, model, etc.getWeather());
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/saveWeather",  method=RequestMethod.POST)
	public Map<String, Object> saveWeather(@RequestBody ParamCmd pc){
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		
		try{
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);
			
			Map<String, Object> result = new HashMap<>();
			result.put("reuslt", etc.insertWeather(cmd));
			super.makeSuccessMap(param, model, result);
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	

	@RequestMapping({"/stringTable", "/stringTable/{lang}"})
	public Map<String, Object> stringTable(@PathVariable("lang") Optional<String> lang) {
		return etc.getStringTable(lang.orElse("ko"));
	}

	
	@RequestMapping("/lang")
	public Map<String, Object> languages() {
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		
		try{
			param = super.createParamVo(new ParamCmd());
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("lang", serviceConfig.getSupportedLanguage());
			super.makeSuccessMap(param, model, result);
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}

	@RequestMapping("/getOpenAPI")
	public String relayOpenAPI(@RequestBody ParamCmd pc) {
		// TODO 보안 이슈... 그냥 열려있는 부분이라 우리 api키를 가지고 맘껏 부를 수 있음... CORS 이슈???
		try {
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node = mapper.readTree(pc.getParameter());
			System.out.println(node.get("url").asText());
			URL url = new URL(node.get("url").asText());
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("CONTENT-TYPE","text/xml"); 
		    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(),"utf-8"));
		    String inputLine;
		    String buffer = "";
		    while ((inputLine = in.readLine()) != null){
		     	buffer += inputLine.trim();
		    }
		    System.out.println("buffer : " + buffer);
		    in.close();

		    return buffer;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	
	
	// 통계 관련
	@RequestMapping("/stats/trekking")
	public Map<String, Object> statTrekking() {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
	
		try{
			param = super.createParamVo(null);
			super.makeSuccessMap(param, model, etc.getTrekkingStats());
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}

//	@RequestMapping("/stats/visitors")
//	public Map<String, Object> statVisitors() {
//		/// 필요한 데이터 객체 생성
//		Map<String, Object> model = new HashMap<String, Object>();
//		ParamVo param = null;
//		
//		try{
//			param = super.createParamVo(null);
//			super.makeSuccessMap(param, model, etc.getVisitorStats());
//		}catch(Exception e){
//			super.makeErrorMap(param, model, e);
//		}
//		
//		return model;
//	}
	
	
	@RequestMapping("/stats/visitors")
	public Map<String, Object> statVisitors() {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		
		try{
			param = super.createParamVo(null);
			super.makeSuccessMap(param, model, etc.getVisitorStats());
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
}
