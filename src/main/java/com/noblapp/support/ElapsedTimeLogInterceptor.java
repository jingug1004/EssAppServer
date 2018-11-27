package com.noblapp.support;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.noblapp.model.vo.ElapseTime;

/**
 * api 시간 체크용 인터셉터
 * 
 * @author juniverse
 */
public class ElapsedTimeLogInterceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(ElapsedTimeLogInterceptor.class);

//	@Autowired private LogService logSerivce;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		logger.info(">> request.getContentType() : " + request.getContentType());
		logger.info(">> request.getContextPath() : " + request.getContextPath());
		logger.info(">> request.getMethod() : " + request.getMethod());
		
//		StringWriter writer = new StringWriter();
//		IOUtils.copy(request.getInputStream(), writer, "utf-8");
//		String reqData = writer.toString();
//		logger.info(">> reqData : " + reqData);

		request.setAttribute("request_time", System.currentTimeMillis());
		logger.debug(">> interceptor prehandler");
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug(">> interceptor posthandler");
		long elapsedTime = System.currentTimeMillis() - (Long)(request.getAttribute("request_time"));
		
		if(elapsedTime > 100){
			ElapseTime elapsetime = new ElapseTime();
			elapsetime.setPath(request.getServletPath());
			elapsetime.setElapsed_time((int)elapsedTime);;
			elapsetime.setElapse_reg_dt(new Date());
			
//			logSerivce.saveElapsetime(elapsetime);
		}
		logger.info(">>>> request.getServletPath()[{}], elapsedTime[{}]", request.getServletPath(), elapsedTime);
		
		super.postHandle(request, response, handler, modelAndView);
	}
	
}
