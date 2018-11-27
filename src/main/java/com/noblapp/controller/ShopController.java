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
import com.noblapp.model.cmd.LocationCmd;
import com.noblapp.model.cmd.SpendConfirmCmd;
import com.noblapp.model.cmd.SpendReqCmd;
import com.noblapp.model.cmd.StoreCmd;
import com.noblapp.model.cmd.StoreUpdateCmd;
import com.noblapp.model.support.ParamCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.service.ShopService;

/**
 * Shopping 관련 컨트롤러 (/shop)
 * @author juniverse
 */
@RestController
@RequestMapping("/shop")
public class ShopController extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(ShopController.class);

	@Autowired private ShopService shop;
	
	@RequestMapping(value="/list", method=RequestMethod.POST)
	public Map<String, Object> list(@RequestBody ParamCmd pc){

		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		LocationCmd cmd = null;
				
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), LocationCmd.class);	/// 인자 읽어오기.

			super.makeSuccessMap(param, model, shop.getStoreList(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
				
		return model;
	}
	
	@RequestMapping(value="/point", method=RequestMethod.POST)
	public Map<String, Object> point(@RequestBody ParamCmd pc){

		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		StoreCmd cmd = null;
				
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), StoreCmd.class);	/// 인자 읽어오기.

			super.makeSuccessMap(param, model, shop.getStorePoint(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
				
		return model;
	}
	
	@RequestMapping(value="/spend/req", method=RequestMethod.POST)
	public Map<String, Object> spendReq(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc){

		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		SpendReqCmd cmd = null;
				
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), SpendReqCmd.class);	/// 인자 읽어오기.
			
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, shop.setUserSpendingReq(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
				
		return model;
	}

	@RequestMapping(value="/spend/req/list", method=RequestMethod.POST)
	public Map<String, Object> spendReqList(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc){

		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
//		StoreCmd cmd = null;
				
		try{
			param = super.createParamVo(pc);
//			cmd = new ObjectMapper().readValue(param.getCmd(), StoreCmd.class);	/// 인자 읽어오기.
//			sessionService.checkValid(cmd.getUid(), access_token);
			
			Map<String, Object> cmd = new ObjectMapper().readValue(param.getCmd(), Map.class);
			sessionService.checkValid((int)cmd.get("uid"), access_token);

			super.makeSuccessMap(param, model, shop.getUserSpendingReqList(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
				
		return model;
	}

	
	@RequestMapping(value="/spend/req/confirm", method=RequestMethod.POST)
	public Map<String, Object> spendReqConfirm(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc){

		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		SpendConfirmCmd cmd = null;
				
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), SpendConfirmCmd.class);	/// 인자 읽어오기.
			
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, shop.setUserSpendingRes(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
				
		return model;
	}
	
	// TODO 이거는 유저 세션이 있어야 하나???
	@RequestMapping(value="/income", method=RequestMethod.POST)
	public Map<String, Object> income(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc){

		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		StoreCmd cmd = null;
				
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), StoreCmd.class);	/// 인자 읽어오기.
			
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, shop.getStoreIncome(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
	
		return model;
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public Map<String, Object> update(
//			@CookieValue(value="access_token", required=false, defaultValue="") String access_token,
			@RequestHeader("access_token") String access_token,
			@RequestBody ParamCmd pc){

		Map<String, Object> model = new HashMap<String, Object>();
		ParamVo param = null;
		StoreUpdateCmd cmd = null;
				
		try{
			param = super.createParamVo(pc);
			cmd = new ObjectMapper().readValue(param.getCmd(), StoreUpdateCmd.class);	/// 인자 읽어오기.
			
			sessionService.checkValid(cmd.getUid(), access_token);

			super.makeSuccessMap(param, model, shop.updateStore(param, cmd));
		}catch(Exception e){
			super.makeErrorMap(param, model, e);
		}
	
		return model;
	}
	

}
