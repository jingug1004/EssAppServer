package com.noblapp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noblapp.controller.support.AbstractController;
import com.noblapp.model.support.AbstractCmd;
import com.noblapp.model.support.ParamCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.service.LeisureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 레져 관련 컨트롤러. (/leisure)
 * 
 * @author juniverse
 */
@RestController
@RequestMapping("/leisure")
public class LeisureController extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(LeisureController.class);

	@Autowired private LeisureService leisure;
	
	@RequestMapping(value="/trek/near", method=RequestMethod.POST)
	public Map<String, Object> nearTrek(@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		
		try{
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);

			super.makeSuccessMap(param, model, leisure.getNearSector(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/trek/update", method=RequestMethod.POST)
	public Map<String, Object> updateTrek(
			@RequestHeader("access_token") String access_token,
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		
		try{
			System.out.println(access_token);
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);
			
			sessionService.checkValid((int)cmd.get("uid"), access_token);

			super.makeSuccessMap(param, model, leisure.updateTrekking(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}

	// TODO 일단 api를 다시 naming 하자.. obsolete
	// 얘는 각 코스별 총 누적 획득 포인트
	@RequestMapping(value="/trek/total", method=RequestMethod.POST)
	public Map<String, Object> totalTrekPoints(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		AbstractCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), AbstractCmd.class);	/// 인자 읽어오기.
			
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, leisure.getUserTrekkingTotalPoints(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}

	// 코스 기록... /trek/course/log
	@RequestMapping(value="/trek/course/list", method=RequestMethod.POST)
	public Map<String, Object> getTrekCourseList (
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
//		TrekCourseCmd cmd = null;

		try{
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.
//			cmd = new ObjectMapper().readValue(param.getCmd(), TrekCourseCmd.class);	/// 인자 읽어오기.
			
			super.makeSuccessMap(param, model, leisure.getTrekCourseList(param, cmd));
		} catch(Exception e) {
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}


	// 코스 기록... /trek/course/log
	@RequestMapping(value="/trek/course/sectors", method=RequestMethod.POST)
	public Map<String, Object> getTrekCourseSectors (
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;

		try{
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.
			
			super.makeSuccessMap(param, model, leisure.getTrekCourseSectors(param, cmd));
		} catch(Exception e) {
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	// 코스 기록... /trek/course/log
	@RequestMapping(value="/trek/course/log", method=RequestMethod.POST)
	public Map<String, Object> userCourseTrek(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
//		TrekCourseCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.
//			cmd = new ObjectMapper().readValue(param.getCmd(), TrekCourseCmd.class);	/// 인자 읽어오기.
			
			sessionService.checkValid((int)cmd.get("uid"), access_token);

			super.makeSuccessMap(param, model, leisure.getUserTrekkingCourse(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/trek/cert", method=RequestMethod.POST)
	public Map<String, Object> certTrek(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		AbstractCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), AbstractCmd.class);	/// 인자 읽어오기.
			
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, leisure.getUserTrekkingCert(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/trek/fame", method=RequestMethod.POST)
	public Map<String, Object> fameTrek(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
		AbstractCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), AbstractCmd.class);	/// 인자 읽어오기.
			
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, leisure.getUserTrekkingFame(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/trek/event/log", method=RequestMethod.POST)
	public Map<String, Object> trekEventLog(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
//		AbstractCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.
			
			sessionService.checkValid((int)cmd.get("uid"), access_token);

			super.makeSuccessMap(param, model, leisure.getTrekEventLog(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}

	@RequestMapping(value="/trek/group", method=RequestMethod.POST)
	public Map<String, Object> trekGroup(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
//			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
//		AbstractCmd cmd = null;

		try{
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.
			super.makeSuccessMap(param, model, leisure.getTrekkingGroupByPid(cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}

	@RequestMapping(value="/trek/course/cid", method=RequestMethod.POST)
	public Map<String, Object> trekCourseCid(
			@RequestBody ParamCmd pc) {
		Map<String, Object> model = new HashMap<>();
		ParamVo param = null;
//		AbstractCmd cmd = null;

		try{
			param = super.createParamVo(pc);
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);	/// 인자 읽어오기.

			super.makeSuccessMap(param, model, leisure.getTrekkingCourseByCid(cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}

		return model;
	}
}
