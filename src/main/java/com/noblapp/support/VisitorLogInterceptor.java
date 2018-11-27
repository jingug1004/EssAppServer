package com.noblapp.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.noblapp.service.EtcService;

/**
 * api 시간 체크용 인터셉터
 * 
 * @author juniverse
 */
public class VisitorLogInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(VisitorLogInterceptor.class);

	@Autowired private EtcService etcService;
	
//	@Override
//	public boolean preHandle(HttpServletRequest request,
//			HttpServletResponse response, Object handler) throws Exception {
//		return super.preHandle(request, response, handler);
//	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		etcService.insertVisitorStats(request);
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
}
