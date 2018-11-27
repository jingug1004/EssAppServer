package com.noblapp.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noblapp.controller.support.AbstractController;
import com.noblapp.model.cmd.JoinCmd;
import com.noblapp.model.support.ParamCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.service.MainService;
import com.noblapp.support.ServiceConfig;

/**
 * 서비스 API의 진입점.
 * "@RestController"의 의미는 일반 Web Controller가 아닌 restful 방식의 컨트롤러 라는 것.<br>
 * "@RequestMapping"은 이 클래스 안의 메소드들의 최상위 경로로 설정하는 것.
 * 
 * @author juniverse
 */
//@CrossOrigin(origins="*", maxAge=3600)
@RestController
@RequestMapping("/")
public class MainController extends AbstractController {
	private Logger logger = LoggerFactory.getLogger(MainController.class);

	// [juniverse] 메인 서비스 객체. 각 지역별 다른 녀석이 생성되도 이녀석을 통해 인터페이스가 구현이 된다.
	@Autowired private MainService mainService;
	@Autowired private ServiceConfig serviceConfig;
	
	/**
	 * 샘플 mapping 메소드<br>
	 * "@RequestMapping"은 이 메소드를 어느 URL로 노출시키는지.
	 * @return 뭘로 하던 알아서 json형태로 serialize 해준다... 는 뻥이고 설정은 해야 하는데 이 샘플에는 설정 되어 있음
	 */
	@RequestMapping("/home")
	public Map<String, Object> home(){
		return mainService.home();
	}
	
	@RequestMapping("")
	public RedirectView index() {
		return new RedirectView("client/index.html");
	}

//	@RequestMapping("/master")
//	public Map<String, Object> master(){
//
//	}

	@RequestMapping("/d")
//	public RedirectView dev() {
	public String dev() {
		/*
		try {
			String hashedPassword = Password.getSaltedHash("password");
			long before = System.currentTimeMillis();
			System.out.printf("hashedPassword: %s [%d] time: %dm.%n", hashedPassword, hashedPassword.length(), System.currentTimeMillis() - before);

			
			boolean result = Password.check("password", hashedPassword);
			System.out.printf("is 'password' same???: %b%n", result);

			result = Password.check("password1", hashedPassword);
			System.out.printf("is 'password1' same???: %b%n", result);

			Random random = new Random();
			byte[] password = new byte[16];
			for (int i = 0; i < 10; i++) {
				random.nextBytes(password);
				String pwStr = new String(password);
				hashedPassword = Password.getSaltedHash(pwStr);
				System.out.printf("password: %s [%d], hashedPassword: %s [%d]%n", pwStr, pwStr.length(), hashedPassword, hashedPassword.length());
			}

//			byte[] salt = new byte[16];
//			new Random().nextBytes(salt);
//			KeySpec spec = new PBEKeySpec("password".toCharArray(), salt, 65536, 128);
//			SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//			byte[] hash = f.generateSecret(spec).getEncoded();
//			Base64.Encoder enc = Base64.getEncoder();
//			System.out.printf("salt: %s%n", enc.encodeToString(salt));
//			System.out.printf("hash: %s%n", enc.encodeToString(hash));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/

//		serviceConfig.loadConfig();
		System.out.printf("trekking point? %d%n", serviceConfig.getTrekkingPoint());
//		return new RedirectView("resources/script_test.html");
		return "hello";
	}

	
	
	
	@RequestMapping(value = "/mvcTest")
	public ModelAndView mvcTest() throws Exception {

		ModelAndView mav = new ModelAndView("something");
		mav.addObject("some", "thing");
		return mav;
	}

}
