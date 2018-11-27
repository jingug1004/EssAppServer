package com.noblapp.controller;

import com.noblapp.controller.support.AbstractController;
import com.noblapp.model.support.ParamCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.service.CmsService;
import com.noblapp.service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/map")
public class MapController extends AbstractController {

	@Autowired private MapService map;
	@Autowired private CmsService cms;


	@RequestMapping({"/categories", "/categories/{lang}"})
	public Map<String, Object> categories(@PathVariable("lang") Optional<String> lang){

		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;

		try {
			param = super.createParamVo(new ParamCmd());
			super.makeSuccessMap(param, model, map.getCategories(lang.orElse("ko")));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}


	/**
	 * 지도상의 주요 장소
	 */
	@RequestMapping({"/mappedPlaces/{category}", "/mappedPlaces/{category}/{lang}"})
	public Map<String, Object> mapSpots(@PathVariable("category") long categoryid
			, @PathVariable("lang") Optional<String> lang) {

		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;

		try {
			param = super.createParamVo(new ParamCmd());
			super.makeSuccessMap(param, model, map.getMappedPlaces(categoryid, lang.orElse("ko")));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping({"/places/{category}", "/places/{category}/{lang}"})
	public Map<String, Object> spots(@PathVariable("category") long categoryid
			, @PathVariable("lang") Optional<String> lang
			, @RequestParam(value="page", defaultValue="0") int page
			, @RequestParam(value="count", defaultValue="20") int count) {

		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;

		try {
			param = super.createParamVo(new ParamCmd());
			super.makeSuccessMap(param, model, map.getPlaces(categoryid, lang.orElse("ko"), page, count));
		} catch(Exception e) {
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping({"/places/detail/{pid}", "/places/detail/{pid}/{lang}"})
	public Map<String, Object> placeDetail(@PathVariable(value="pid") long pid
			, @PathVariable(value="lang") Optional<String> lang) {

		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;

		try {
			map.updateTouchLog("P", pid);
			param = super.createParamVo(new ParamCmd());
			super.makeSuccessMap(param, model, map.getPlacesDetail(pid, lang.orElse("ko")));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping({"/search", "/search/{lang}"})
	public Map<String, Object> search(@PathVariable(value="lang") Optional<String> lang
			, @RequestParam(value="v", defaultValue="") String v
			) {

		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		try{
			param = super.createParamVo(new ParamCmd());
			super.makeSuccessMap(param, model, map.searchPlaces(lang.orElse("ko"), v));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping({"/imaps", "/imaps/{lang}"})
	public Map<String, Object> imaps(@PathVariable(value="lang") Optional<String> lang) {

		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		try{
			param = super.createParamVo(new ParamCmd());
			super.makeSuccessMap(param, model, map.getImaps(lang.orElse("ko")) );
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping({"/spots", "/spots/{lang}"})
	public Map<String, Object> spotsList(@PathVariable(value="lang") Optional<String> lang) {

		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		try{
			param = super.createParamVo(new ParamCmd());
			super.makeSuccessMap(param, model, map.getSpotsList(lang.orElse("ko")) );
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping({"/spotDetail/{pid}", "/spotDetail/{pid}/{lang}"})
	public Map<String, Object> spotDetail(@PathVariable(value="pid") long pid,
										  @PathVariable(value="lang") Optional<String> lang) {

		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		try{
			map.updateTouchLog("S", pid); // 상세 카운트 로그
			param = super.createParamVo(new ParamCmd());
			super.makeSuccessMap(param, model, map.getSpotDetail(lang.orElse("ko"), pid));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}


	@RequestMapping({"/allplaces", "/allplaces/{lang}"})
	public Map<String, Object> allPlaces(@PathVariable(value="lang") Optional<String> lang) {
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		try{
			param = super.createParamVo(new ParamCmd());
			super.makeSuccessMap(param, model, map.getAllPlaces(lang.orElse("ko")));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping({"/allPictograms"})
	public Map<String, Object> allPictograms() {
		Map<String, Object> model = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		ParamVo param = null;
		try{
			param = super.createParamVo(new ParamCmd());
			result.put("list", cms.getPictograms(0, 1000));
			super.makeSuccessMap(param, model, result);
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping({"/configdata"})
	public Map<String, Object> allServiceConfig() {
		Map<String, Object> model = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		ParamVo param = null;
		try{
			param = super.createParamVo(new ParamCmd());
			result.put("list", cms.getServiceConfigNonSecret());
			super.makeSuccessMap(param, model, result);
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping({"/events", "/events/{lang}"})
	public Map<String, Object> events(@PathVariable(value="lang") Optional<String> lang) {
		Map<String, Object> model = new HashMap<>();
		Map<String, Object> result = new HashMap<>();
		ParamVo param = null;
		try{
			param = super.createParamVo(new ParamCmd());
            List<Map<String, Object>> list = cms.getEvents(lang.orElse("ko") ,0 ,0);
			result.put("list", list.stream().filter(event -> Integer.parseInt(event.get("status").toString()) == 1).collect(Collectors.toList()));
			super.makeSuccessMap(param, model, result);
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}
}
