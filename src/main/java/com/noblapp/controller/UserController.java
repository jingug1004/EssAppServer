package com.noblapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noblapp.controller.support.AbstractController;
import com.noblapp.model.cmd.JoinCmd;
import com.noblapp.model.cmd.SocialLoginCmd;
import com.noblapp.model.cmd.UpdateCmd;
import com.noblapp.model.support.AbstractCmd;
import com.noblapp.model.support.ParamCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController extends AbstractController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired private UserService user;

	@RequestMapping(value="/join", method=RequestMethod.POST)
	public Map<String, Object> join(@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		JoinCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), JoinCmd.class);	/// 인자 읽어오기.

			super.makeSuccessMap(param, model, user.join(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public Map<String, Object> login(@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		JoinCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), JoinCmd.class);	/// 인자 읽어오기.

			super.makeSuccessMap(param, model, user.login(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}

	@RequestMapping(value="/checkId", method=RequestMethod.POST)
	public Map<String, Object> checkId(@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		JoinCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), JoinCmd.class);	/// 인자 읽어오기.

			super.makeSuccessMap(param, model, user.checkId(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public Map<String, Object> logout(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		AbstractCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), AbstractCmd.class);	/// 인자 읽어오기.

			// 세션 정보 확인
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, user.logout(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/info", method=RequestMethod.POST)
	public Map<String, Object> info(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		AbstractCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), AbstractCmd.class);	/// 인자 읽어오기.

			// 세션 정보 확인
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, user.info(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public Map<String, Object> update(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		UpdateCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), UpdateCmd.class);	/// 인자 읽어오기.

			// 세션 정보 확인
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, user.update(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/spending", method=RequestMethod.POST)
	public Map<String, Object> spending(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		AbstractCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), AbstractCmd.class);	/// 인자 읽어오기.
			
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, user.spending(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}

	@RequestMapping(value="/point", method=RequestMethod.POST)
	public Map<String, Object> point(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		AbstractCmd cmd = null;
		
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), AbstractCmd.class);	/// 인자 읽어오기.

			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, user.point(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
		
		return model;
	}
	
	@RequestMapping(value="/loginGoogle", method=RequestMethod.POST)
	public Map<String, Object> loginWithGoogle(@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		System.out.println(pc.getParameter());
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		SocialLoginCmd cmd = null;
				
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), SocialLoginCmd.class);	/// 인자 읽어오기.

			super.makeSuccessMap(param, model, user.loginWithGoogle(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
				
		return model;
	}

	@RequestMapping(value="/loginFB")
	public Map<String, Object> loginWithFacebook(@RequestBody ParamCmd pc) {
		/// 필요한 데이터 객체 생성
		System.out.println(pc.getParameter());
		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		SocialLoginCmd cmd = null;
				
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), SocialLoginCmd.class);	/// 인자 읽어오기.

			super.makeSuccessMap(param, model, user.loginWithFacebook(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
				
		return model;
	}

}
