package com.noblapp.controller.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noblapp.model.support.ParamCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.service.UserSessionService;
import com.noblapp.support.ErrorCode;
import com.noblapp.support.NoblappException;

public abstract class AbstractController {

	@Autowired protected UserSessionService sessionService;

	protected ParamVo createParamVo(ParamCmd pc) {
		ParamVo paramVo = new ParamVo();
		// todo 나중에 cmd가 암호화 처리를 한다면 여기서 풀어줘야 함...
//		System.out.println("parameter? " + pc.getParameter());
		if (pc != null)
			paramVo.setCmd(pc.getParameter());
		return paramVo;
	}

	/*
	protected ParamVo createAuthParamVo(ParamCmd pc, int uid, String access_token) throws Exception {
		ParamVo paramVo = new ParamVo();
		// todo 나중에 cmd가 암호화 처리를 한다면 여기서 풀어줘야 함...
		paramVo.setCmd(pc.getParameter());

		sessionService.checkValid(uid, access_token);

		return paramVo;
	}
	*/

	/**
	 * 성공시 데이터를 생성한다.
	 * @param param module 결과 데이터
	 * @param model module 반환용 map
	 * @param result module의 결과 데이터
	 */
	protected void makeSuccessMap(ParamVo param, Map<String, Object> model, Map<String, Object> result) {
		if (result == null)
			result = new HashMap<String, Object>();

		try {
			model.put("module", getRequest().getServletPath());
			model.put("success", true);
			model.put("ecode", 0);
			model.put("time", param.getTxtime());
			model.put("response", new ObjectMapper().writeValueAsString(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 실패시 데이터를 생성한다.
	 * @param param
	 * @param map
	 */
	protected void makeErrorMap(ParamVo param, Map<String, Object> model, Exception err) {
		err.printStackTrace();
		try {
			param = (param == null ? new ParamVo() : param);

			model.put("module", getRequest().getServletPath());
			model.put("success", false);
			if (err instanceof NoblappException)
				model.put("ecode", ((NoblappException)err).getCode());
			else
				model.put("ecode", ErrorCode.UNKNOWN);
			model.put("time", param.getTxtime());
			model.put("response", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * web.xml에 아래 listener 추가
	 * <listener>
	 *   <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
	 * </listener>
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}
	
}
