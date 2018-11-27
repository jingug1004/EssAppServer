package com.noblapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noblapp.controller.support.AbstractController;
import com.noblapp.model.cmd.JoinCmd;
import com.noblapp.model.support.ParamCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.model.vo.CmsListItemVo;
import com.noblapp.service.CmsService;
import com.noblapp.service.ShopService;
import com.noblapp.support.ErrorCode;
import com.noblapp.support.NoblappException;
import com.noblapp.support.ServiceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cms")
public class CmsController extends AbstractController {

	private Logger logger = LoggerFactory.getLogger(CmsController.class);

	private static final String redirectFormat = "../../process/%s?p=%d&u=%d&c=%d";

	@Autowired private CmsService cms;
	@Autowired private ShopService shop;
	@Autowired private ServiceConfig serviceConfig;

	@RequestMapping(value = "")
	public RedirectView root1() throws Exception {
		return new RedirectView("cms/login");
	}

	@RequestMapping(value = "/")
	public RedirectView root2() throws Exception {
		return new RedirectView("login");
	}

	@RequestMapping(value = "/setup")
	public ModelAndView setup() throws Exception {
		try {
			ModelAndView mav = new ModelAndView("addAdmin");
			Map<String, Object> admin = cms.getAdminInfo();
			if (admin != null && admin.get("id") != null) {
				// 이미 admin이 있음... 로그인으로 들어가..
				mav = new ModelAndView("redirect:/cms/login");
			} else {
				mav = new ModelAndView("addAdmin");
				mav.addObject("gname", serviceConfig.getServiceRegionName());
			}
			return mav;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@RequestMapping(value = "/addAdmin.do", method=RequestMethod.POST)
	public Map<String, Object> doAddAdmin(@RequestBody ParamCmd pc) {
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		
		try{
			param = super.createParamVo(pc);
			Map<String, String> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.

			if (cms.addAdmin(param, cmd))
				super.makeSuccessMap(param, model, null);
			else
				super.makeErrorMap(param, model, new NoblappException(ErrorCode.UNKNOWN));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value = "/login")
	public ModelAndView login() throws Exception {
		System.out.println("hello cms login???");
		Map<String, Object> admin = cms.getAdminInfo();
		if (admin == null) {
			return new ModelAndView("redirect:/cms/setup");
		}
	
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("adminId", admin.get("id"));
		mav.addObject("gname", serviceConfig.getServiceRegionName());
		return mav;
	}

	@RequestMapping(value = "/login.do", method=RequestMethod.POST)
	public Map<String, Object> doLogin(@RequestBody ParamCmd pc) {
		// 관리자 로그인 처리... 세션 관리도...
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		
		try{
			param = super.createParamVo(pc);
			JoinCmd cmd = new ObjectMapper().readValue(param.getCmd(), JoinCmd.class);	/// 인자 읽어오기.

			super.makeSuccessMap(param, model, cms.loginManagement(cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}

	@RequestMapping(value = "/logout")
	public RedirectView doLogout() throws Exception {
		// 관리자 로그아웃 처리.
		cms.logoutManagement();
		return new RedirectView("login");
	}
	
	// 다른 관리자로도 들어올 수 있게 하려면....
	// 다른 관리자들의 access_token들을 확인해야 하겠지???
	private boolean checkAdminSession(String access_token) throws Exception {
		List<Map<String, Object>> admins = cms.getAdministrators();
		if (admins == null || admins.size() <= 0) {
			logger.error("user not exist.");
			throw new NoblappException(ErrorCode.NO_USER);
		}
		
		for (Map<String, Object> admin : admins) {
			try {
				if (sessionService.checkValid((int)admin.get("uid"), access_token))
					return true;
			} catch (Exception e) { }
		}
		
		throw new NoblappException(ErrorCode.INVALID_SESSION);
	}
	
	
	
	@RequestMapping(value = "/process/")
	public RedirectView processSub() {
		return new RedirectView("category");
	}
	
	@RequestMapping(value = "/process")
	public RedirectView process() {
		return new RedirectView("process/category");
	}

	@RequestMapping(value = "/process/{target}", method=RequestMethod.GET)
	public ModelAndView process(@PathVariable("target") String target
//			, @RequestParam(value="lang", defaultValue="ko") String lang
			, @RequestParam(value="p", defaultValue="1") int page
			, @RequestParam(value="u", defaultValue="10") int unit
			, @RequestParam(value="c", defaultValue="0") int cid
			, @RequestParam(value="m", defaultValue="0") int mid
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {

		// 세션 확인..
		try {
			checkAdminSession(access_token);
		} catch (Exception e) {
			return new ModelAndView("redirect:/cms/login");
		}
		
		if (target.isEmpty())
			target = "category";
		
		String list_title = "";
		int total = 0;

		ModelAndView mav = new ModelAndView("process");		// name of .jsp file

		mav.addObject("cid", "undefined");
		List<Map<String, Object>> catList = cms.getPlaceCategories("ko", 0, 0);


		// 필요한 diaplay 항목
		// id, name, status, object
		List<CmsListItemVo> list = new ArrayList<>();
		if (target.equals("category")) {
			
			list_title = "카테고리";
			total = cms.getCategoryCount();

			List<Map<String, Object>> categoryList = cms.getPlaceCategories("", (page - 1) * unit, unit);
			for (Map<String, Object> category : categoryList) {
				list.add(new CmsListItemVo((long)category.get("cid"), category.get("title").toString(), (int)category.get("status"), category));
			}
			
			mav.addObject("roots", cms.getRootCategories());
		} else if (target.equals("place")){
			// TODO cid가지고 구분하기???

			list_title = "장소";
			total = cms.getPlaceCount();
			
			List<Map<String, Object>> placeList = cms.getPlaces("", (page - 1) * unit, unit, cid);
			for (Map<String, Object> place : placeList) {
				//logger.debug("pid " + place.get("pid"));
				//logger.debug("name " + place.get("name"));
				//logger.debug("status " + place.get("status"));
				list.add(new CmsListItemVo((long)place.get("pid"), place.get("name").toString(), (int)place.get("status"), place));
			}

			// 픽토그램도 필요해...
			List<Map<String, Object>> pictogramsList = cms.getPictograms(0, 0);
			mav.addObject("pictograms", pictogramsList);			
			mav.addObject("cid", cid);
		} else if (target.equals("pictogram")){
			list_title = "픽토그램";
			total = cms.getPictogramCount();

			List<Map<String, Object>> pictogramsList = cms.getPictograms((page - 1) * unit, unit);
			for (Map<String, Object> pictogram : pictogramsList) {
				list.add(new CmsListItemVo((long)pictogram.get("pid"), pictogram.get("title").toString(), 1, pictogram));
			}
		} else if (target.equals("system")){
			List<Map<String, Object>> serviceConfig = cms.getServiceConfig();
			StringBuffer buffer = new StringBuffer();
			for (Map<String, Object> serviceItem : serviceConfig) {
				buffer.setLength(0);
				buffer.append(serviceItem.get("name")).append(": ").append(serviceItem.get("the_value"));
				long id = (long)serviceItem.get("id");
				list.add(new CmsListItemVo(id, buffer.toString(), 1, serviceItem));
			}

			list_title = "서비스 변수";
			total = serviceConfig.size();
		} else if (target.equals("user")){
			list_title = "유저";
			total = cms.getUserCount();
			
			List<Map<String, Object>> users = cms.getUsers((page - 1) * unit, unit);
			for (Map<String, Object> user : users) {
				list.add(new CmsListItemVo((int)user.get("uid"), user.get("name").toString(), (int)user.get("status"), user));
			}
		} else if (target.equals("trek")){
			// TDOO trekking
			list_title = "트레킹";
			total = cms.getTrekCourseCount();
			
			List<Map<String, Object>> treks = cms.getTrekCourses((page - 1) * unit, unit);
			for (Map<String, Object> trek : treks) {
				list.add(new CmsListItemVo((long)trek.get("tc_id"), trek.get("course_name").toString(), (int)trek.get("status"), trek));
			}
			mav.addObject("def_dist", serviceConfig.getTrekMinDistance());
		} else if (target.equals("imap")){
			list_title = "이미지 지도";
			total = cms.getImapCount();
			
			List<Map<String, Object>> imapList = cms.getImaps();
			for (Map<String, Object> imap : imapList) {
				list.add(new CmsListItemVo((long)imap.get("mid"), imap.get("name").toString(), (int)imap.get("status"), imap));
			}
		} else if (target.equals("spot")){
			list_title = "지도 장소";
			total = cms.getSpotCount();
			
			List<Map<String, Object>> spotList = cms.getSpots((page - 1) * unit, unit, mid);
			for (Map<String, Object> spot : spotList) {
				list.add(new CmsListItemVo((long)spot.get("pid"), spot.get("name").toString(), (int)spot.get("status"), spot));
			}

			// 픽토그램도 필요해...
			List<Map<String, Object>> pictogramsList = cms.getPictograms(0, 0);			
			mav.addObject("pictograms", pictogramsList);
			
			List<Map<String, Object>> imapList = cms.getImaps();
			mav.addObject("imaps", imapList);
		} else if (target.equals("event")){
			list_title = "행사 목록";
			total = cms.getEventCount();

			List<Map<String, Object>> eventList = cms.getEvents("",(page - 1) * unit, unit);
			for (Map<String, Object> event : eventList) {
				list.add(new CmsListItemVo(Long.parseLong(event.get("eid").toString()), event.get("name").toString(), (int)event.get("status"), event));
			}

			List<Map<String, Object>> orderedCateList = new ArrayList<>();
			List<Map<String, Object>> cid0List = catList.stream().filter(cate -> Integer.parseInt(cate.get("pcid").toString()) == 0).collect(Collectors.toList());
			cid0List.forEach((cate) -> {
				orderedCateList.add(cate);
				orderedCateList.addAll(catList.stream().filter(cate2 -> Integer.parseInt(cate.get("cid").toString()) == Integer.parseInt(cate2.get("pcid").toString())).collect(Collectors.toList()));
			});

			mav.addObject("ordered_categories", orderedCateList);
			List<Map<String, Object>> placeList = cms.getPlaces("ko", 0, 0, 0);
			List<Map<String, Object>> newPlaceList = placeList.stream().map(place -> {
				place.remove("address");
				place.remove("phone");
				place.remove("ccv");
				place.remove("map_res_url");
				place.remove("map_type");
				place.remove("text");
				place.remove("cf");
				place.remove("cv");
				place.remove("images");
				place.remove("lat");
				place.remove("lng");
				place.remove("display_zoom");
				place.remove("update_dt");
				place.remove("others");
				place.remove("videos");
				return place;
			}).collect(Collectors.toList());
			mav.addObject("places", new ObjectMapper().writeValueAsString(newPlaceList));
		} else if (target.equals("hits")){
			list_title = "누적 조회 수";
			total = cms.getTouchLogCount();

			List<Map<String, Object>> touchlogList = cms.getTouchLogList((page - 1) * unit, unit);
			for (Map<String, Object> touchlog : touchlogList) {
				list.add(new CmsListItemVo(Long.valueOf(touchlog.get("tid").toString()), touchlog.get("name").toString(), Integer.valueOf(touchlog.get("cnt").toString()), touchlog, touchlog.get("table_type").toString()));
			}
		}

		mav.addObject("categories", catList);
		mav.addObject("categories_js", new ObjectMapper().writeValueAsString(catList));
			
		int current = page;
		int per_page = unit;

		mav.addObject("gname", serviceConfig.getServiceRegionName());
		mav.addObject("target", target);
		mav.addObject("list_title", list_title);
		mav.addObject("list", list);
		mav.addObject("total", total);		
		mav.addObject("current", current);
		mav.addObject("per_page", per_page);
		mav.addObject("mid", mid);
		mav.addObject("languages", serviceConfig.getSupportedLanguage());
		
		return mav;
	}
	
	@RequestMapping(value = "/process/{target}/add", method=RequestMethod.POST)
//	@ResponseBody
	public RedirectView processAdd(@PathVariable("target") String target
			, MultipartHttpServletRequest mrequest
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {

		logger.info("add body=============");
		Enumeration<String> params = mrequest.getParameterNames();
		while (params.hasMoreElements()) {
			String name = params.nextElement();
			String val = new String(mrequest.getParameter(name).getBytes("iso-8859-1"), "UTF-8");
			logger.info(name + " : " + val);
		}

		try {
			checkAdminSession(access_token);
		} catch (Exception e) {
			return new RedirectView("../../login");
		}
	
		int page = 1;
		int unit = 10;
		int total = 0;

		try {
			if (target.equals("category")) {
				cms.updateCategory(mrequest);
				total = cms.getCategoryCount();
			} else if (target.equals("place")) {
				cms.updatePlace(mrequest);
				total = cms.getPlaceCount();
			} else if (target.equals("pictogram")) {
				cms.updatePictogram(mrequest);
				total = cms.getPictogramCount();
			} else if (target.equals("user")) {
				cms.updateUser(mrequest);
				total = cms.getUserCount();
			} else if (target.equals("trek")) {
				cms.updateTrekCourse(mrequest);
				total = cms.getTrekCourseCount();
			} else if (target.equals("imap")) {
				cms.updateImap(mrequest);
				total = cms.getImapCount();
			} else if (target.equals("spot")) {				
				cms.updateSpot(mrequest);
				total = cms.getSpotCount();
			} else if (target.equals("event")) {
				cms.updateEvent(mrequest);
				total = cms.getEventCount();
			}
			page = ((total - 1) / unit) + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 맨 뒤로 보내줘야 할 것 같은데...
		return new RedirectView(String.format(redirectFormat, target, page, unit, 0));
	}

	// TODO status 변경용 api를 따로 하나 만들어야 할듯???
	@RequestMapping(value = "/process/{target}/update", method=RequestMethod.POST)
	public RedirectView processUpdate(@PathVariable("target") String target
			, @RequestParam(value="p", defaultValue="1") int page
			, @RequestParam(value="u", defaultValue="10") int unit
			, @RequestParam(value="c", defaultValue="0") int cid
			, @RequestParam(value="c", defaultValue="0") int mid
			, MultipartHttpServletRequest mrequest
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {

		logger.info("add body=============");
		Enumeration<String> params = mrequest.getParameterNames();
		while (params.hasMoreElements()) {
			String name = params.nextElement();
			String val = new String(mrequest.getParameter(name).getBytes("iso-8859-1"), "UTF-8");
			logger.info(name + " : " + val);
		}

		
		// 세션 확인..
		try {
			checkAdminSession(access_token);
		} catch (Exception e) {
			return new RedirectView("../../login");
		}

		try {
			if (target.equals("category")) {
				cms.updateCategory(mrequest);
			} else if (target.equals("place")) {
				cms.updatePlace(mrequest);
			} else if (target.equals("pictogram")) {
				cms.updatePictogram(mrequest);
			} else if (target.equals("system")) {
				cms.updateServiceConfig(mrequest);
			} else if (target.equals("mascot")) {
				cms.updateMascot(mrequest);
				target = "system";
			} else if (target.equals("font")) {
				cms.updateFont(mrequest);
				target = "system";
			} else if (target.equals("user")) {
				cms.updateUser(mrequest);
			} else if (target.equals("trek")) {
				cms.updateTrekCourse(mrequest);
			} else if (target.equals("imap")) {
				cms.updateImap(mrequest);
			} else if (target.equals("spot")) {
				cms.updateSpot(mrequest);
			} else if (target.equals("event")) {
				cms.updateEvent(mrequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return new RedirectView(String.format(redirectFormat, target, page, unit, cid, mid));
	}
	
	// TODO status 변경용 api를 따로 하나 만들어야 할듯???
	@RequestMapping(value = "/process/{target}/status", method=RequestMethod.POST)
	public Map<String, Object> processUpdateStatus(@PathVariable("target") String target
			, @RequestBody ParamCmd pc
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {

		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
				
		try{
			// 세션 확인..
			checkAdminSession(access_token);
			
			param = super.createParamVo(pc);
			logger.info("cmd? " + param.getCmd());
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.

			// sessionService.checkValid(cmd.getUid(), access_token);

			if (target.equals("category")) {
				cms.updateCategoryStatus(cmd);
			} else if (target.equals("place")) {
				cms.updatePlaceStatus(cmd);
			} else if (target.equals("user")) {
				cms.updateUserStatus(cmd);
			} else if (target.equals("trek")) {
				cms.updateTrekCourseStatus(cmd);
			} else if (target.equals("imap")) {
				cms.updateImapStatus(cmd);
			} else if (target.equals("spot")) {				
				cms.updateSpotStatus(cmd);
			} else if (target.equals("event")) {
				cms.updateEventStatus(cmd);
			} else {
				// pictogram은 status 따위는 없다!!
			}

			super.makeSuccessMap(param, model, null);
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
	
		return model;
	}

	@RequestMapping(value = "/process/{target}/delete", method=RequestMethod.POST)
	public Map<String, Object> processDelete(@PathVariable("target") String target
			, @RequestBody ParamCmd pc
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {

		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
				
		try{
			// 세션 확인..
			checkAdminSession(access_token);
			
			param = super.createParamVo(pc);
			logger.info("cmd? " + param.getCmd());
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.
//			for (String key : cmd.keySet()) {
//				logger.info(key + " : " + cmd.get(key));
//			}

			// sessionService.checkValid(cmd.getUid(), access_token);
			Long id = Long.parseLong(cmd.get("id").toString());
			if (target.equals("category")) {
				// 의존성이 있으면 삭제 안함.
				cms.deleteCategory(id);
			} else if (target.equals("place")) {
				cms.deletePlace(id);
			} else if (target.equals("pictogram")) {
				cms.deletePictogram(id);
			} else if (target.equals("user")) {
				cms.deleteUser(id);
			} else if (target.equals("trek")) {
				cms.deleteTrekCourse(id);
			} else if (target.equals("imap")) {
				cms.deleteImap(id);
			} else if (target.equals("spot")) {
				cms.deleteSpot(id);
			} else if (target.equals("event")) {
				cms.deleteEvent(id);
			} else {
			}
			
			super.makeSuccessMap(param, model, null);
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
	
		return model;
	}

	/**
	 * 이 함수는 config를 다시 읽어오는 처리를 한다.
	 */
	@RequestMapping("/reloadConfig")
	public Map<String, Object> reloadConfig(@RequestBody ParamCmd pc
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {
				
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		try{
			// 세션 확인..
			checkAdminSession(access_token);
			
			param = super.createParamVo(pc);
			serviceConfig.loadConfig();
			super.makeSuccessMap(param, model, null);
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
	
		return model;
	}
	
	
	
	/**
	 * Trekking 코스 섹션 입력용 API
	 */
	@RequestMapping("/process/trek/addSectors")
	public Map<String, Object> addSectors(@RequestBody ParamCmd pc
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {
				
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		try{
			// 세션 확인..
			checkAdminSession(access_token);
			
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.
			super.makeSuccessMap(param, model, cms.updateTrekSectors(cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
	
		return model;
	}

//	@RequestMapping(value = "/process/trek/sectors")
//	@RequestMapping(value = "/process/trek/sectors", method=RequestMethod.GET)
	@RequestMapping(value = "/process/trek/sectors", method=RequestMethod.POST)
	public ModelAndView sectorPlotter(
			@RequestParam(value="tc_id", defaultValue="0") int tc_id
			, @RequestParam(value="g_idx", defaultValue="-1") int g_idx
			, @RequestParam(value="g_name", defaultValue="") String g_name
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {

		try {
			checkAdminSession(access_token);
		} catch (Exception e) {
			return new ModelAndView("redirect:/cms/login");
		}
		
		// TODO should check variables for validity
		Map<String, Object> courseInfo = cms.getTrekCourseInfo(tc_id);
		
		ModelAndView mav = new ModelAndView("plotter");		// name of .jsp file
		mav.addObject("tc_id", tc_id);
		mav.addObject("tc_type", courseInfo.get("tc_type"));
		mav.addObject("g_idx", g_idx);
		mav.addObject("g_name", new String(g_name.getBytes("iso-8859-1"), "UTF-8"));
		// TODO 기본 지도 위치를 넣어줘야 할 듯...???
		mav.addObject("def_lat", serviceConfig.getBaseLatLng()[0]);
		mav.addObject("def_lng", serviceConfig.getBaseLatLng()[1]);
		
		// 기존 sector들이 있으면 그것들도 넣어줘야 함...
		mav.addObject("prev_sectors", new ObjectMapper().writeValueAsString(cms.getTrekSectors(tc_id, g_idx)));
		mav.addObject("oth_sectors", new ObjectMapper().writeValueAsString(cms.getTrekOtherSectors(tc_id, g_idx)));

		return mav;
	}

	@RequestMapping(value = "/process/{target}/picker", method=RequestMethod.POST)
	public ModelAndView placePicker(@PathVariable("target") String target
			, @RequestParam(value="pid", defaultValue="0") int pid
			, @RequestParam(value="p", defaultValue="1") int page
			, @RequestParam(value="u", defaultValue="10") int unit
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {

		try {
			checkAdminSession(access_token);
		} catch (Exception e) {
			return new ModelAndView("redirect:/cms/login");
		}
		
		ModelAndView mav = new ModelAndView("gps_picker");		// name of .jsp file

		Map<String, Object> placeInfo;
		if (target.equals("place")) {
			placeInfo = cms.getPlace(pid);
			mav.addObject("cid", placeInfo.get("cid"));
			mav.addObject("address", placeInfo.get("address"));
		} else if (target.equals("spot")) {
			placeInfo = cms.getSpot(pid);
			mav.addObject("cid", "undefined");
			mav.addObject("address", "");
		} else {
			return new ModelAndView("redirect:/cms/login");
		}
		
		mav.addObject("target", target);
		mav.addObject("name", placeInfo.get("name"));
		mav.addObject("pid", pid);

		mav.addObject("page", page);
		mav.addObject("unit", unit);

		double pLat = (double)placeInfo.get("lat");
		double pLng = (double)placeInfo.get("lng");
		if (pLat == 0.0 || pLng == 0.0) {
			mav.addObject("def_lat", serviceConfig.getBaseLatLng()[0]);
			mav.addObject("def_lng", serviceConfig.getBaseLatLng()[1]);
			mav.addObject("prev_loc", "undefined");
		} else {
			mav.addObject("def_lat", pLat);
			mav.addObject("def_lng", pLng);
			mav.addObject("prev_loc", "[" + pLat + "," + pLng + "]");
		}

		return mav;
	}
	
	@RequestMapping("/process/{target}/updateLoc")
	public Map<String, Object> updateLocation(@PathVariable("target") String target
			, @RequestBody ParamCmd pc
			, @CookieValue(value="access_token", required=false, defaultValue="") String access_token
			) throws Exception {
				
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		try{
			// 세션 확인..
			checkAdminSession(access_token);
			
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.
			if (target.equals("place"))
				super.makeSuccessMap(param, model, cms.updatePlaceLocation(cmd));
			else if (target.equals("spot"))
				super.makeSuccessMap(param, model, cms.updateSpotLocation(cmd));
			else
				throw new NoblappException(ErrorCode.UNKNOWN);
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
	
		return model;
	}
}
