package com.noblapp.controller;

import com.noblapp.support.ServiceConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

/**
 * 소셜 로그인 관련 컨트롤러 (/auth)
 * @author juniverse
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
	private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private ServiceConfig serviceConfig;

    private Map<String, Object> googleMap;

    @ResponseBody
	@RequestMapping(value="/google", method=RequestMethod.POST)
	public Map<String, Object> google() throws Exception{
//        Iterator<String> paramterKeysItr = tokenMap.keySet().iterator();
//
//        while(paramterKeysItr.hasNext()) {
//            String key = paramterKeysItr.next();
//            logger.info("parameterName : {}, parameterValue : {}", key, tokenMap.get(key));
//        }

		return googleMap;
	}
	
	@RequestMapping(value="/google/callback", method=RequestMethod.GET)
	public String googleCallback(HttpServletRequest request) throws  Exception{
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String[]> parameterMap = request.getParameterMap();
//
//        Iterator<String> paramterKeysItr = parameterMap.keySet().iterator();
//
//        while(paramterKeysItr.hasNext()) {
//            String key = paramterKeysItr.next();
//            logger.info("parameterName : {}, parameterValue : {}", key, parameterMap.get(key));
//        }

        String code = parameterMap.get("code") != null ? parameterMap.get("code")[0] : null;
        if(!Optional.ofNullable(code).isPresent()) {
            return new ResponseEntity<>(parameterMap, HttpStatus.BAD_REQUEST).toString();
        }
        MultiValueMap<String, Object> uriVariables = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        uriVariables.add("code", code);
        uriVariables.add("client_id", serviceConfig.getGoogleClientId());
        uriVariables.add("client_secret", serviceConfig.getGoogleClientSecret());
        uriVariables.add("redirect_uri", serviceConfig.getGoogleRedirectUri());
        uriVariables.add("grant_type", "authorization_code");

        HttpEntity<?> httpEntity = new HttpEntity<>(uriVariables, headers);

        ResponseEntity<Map> tokenResponse = restTemplate.exchange(new URI("https://www.googleapis.com/oauth2/v4/token"), HttpMethod.POST, httpEntity, Map.class);
        Map<String, Object> tokenMap = tokenResponse.getBody();
        Map<String, Object> userMap = getGoogleUserInfo(tokenMap.get("token_type").toString(), tokenMap.get("access_token").toString());
        googleMap = new HashMap<>();
        googleMap.putAll(tokenMap);
        googleMap.putAll(userMap);

        return "";
	}

    @ResponseBody
    @RequestMapping(value="/google/userInfo", method=RequestMethod.POST)
    public Map<String, Object> getUserInfo(HttpServletRequest request) throws Exception{
        Map<String, String> params = new HashMap<>();
        if(StringUtils.isNotBlank(request.getParameter("token_type"))) {
            params.put("tokenType", request.getParameter("token_type"));
            System.out.println(request.getParameter("token_type"));
        }

        if(StringUtils.isNotBlank(request.getParameter("access_token"))) {
            params.put("accessToken", request.getParameter("access_token"));
            System.out.println(request.getParameter("access_token"));
        }

        return getGoogleUserInfo(params.get("tokenType"), params.get("accessToken"));
    }

	public Map<String, Object> getGoogleUserInfo (String tokenType, String accessToken) throws  Exception {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> uriVariables = new LinkedMultiValueMap<>();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", tokenType + " " + accessToken);

        HttpEntity<?> httpEntity = new HttpEntity<>(uriVariables, headers);

        ResponseEntity<Map> tokenResponse = restTemplate.exchange(new URI("https://www.googleapis.com/oauth2/v1/userinfo"), HttpMethod.GET, httpEntity, Map.class);
        Map<String, Object> userInfoMap = tokenResponse.getBody();
        System.out.println(userInfoMap.toString());
        return userInfoMap;
    }


    @RequestMapping(value="/naver/callback", method=RequestMethod.GET)
    public String naverCallback(HttpServletRequest request) throws  Exception{
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String[]> parameterMap = request.getParameterMap();

        Iterator<String> paramterKeysItr = parameterMap.keySet().iterator();

        while(paramterKeysItr.hasNext()) {
            String key = paramterKeysItr.next();
            logger.info("parameterName : {}, parameterValue : {}", key, parameterMap.get(key));
        }

//        String code = parameterMap.get("code") != null ? parameterMap.get("code")[0] : null;
//        if(!Optional.ofNullable(code).isPresent()) {
//            return new ResponseEntity<>(parameterMap, HttpStatus.BAD_REQUEST).toString();
//        }
//        MultiValueMap<String, Object> uriVariables = new LinkedMultiValueMap<>();
//        HttpHeaders headers = new HttpHeaders();
//        uriVariables.add("code", code);
//        uriVariables.add("client_id", serviceConfig.getGoogleClientId());
//        uriVariables.add("client_secret", serviceConfig.getGoogleClientSecret());
//        uriVariables.add("redirect_uri", serviceConfig.getGoogleRedirectUri());
//        uriVariables.add("grant_type", "authorization_code");
//
//        HttpEntity<?> httpEntity = new HttpEntity<>(uriVariables, headers);
//
//        ResponseEntity<Map> tokenResponse = restTemplate.exchange(new URI("https://www.googleapis.com/oauth2/v4/token"), HttpMethod.POST, httpEntity, Map.class);
//        Map<String, Object> tokenMap = tokenResponse.getBody();
//        Map<String, Object> userMap = getGoogleUserInfo(tokenMap.get("token_type").toString(), tokenMap.get("access_token").toString());
//        googleMap = new HashMap<>();
//        googleMap.putAll(tokenMap);
//        googleMap.putAll(userMap);

        return "";
    }
}
