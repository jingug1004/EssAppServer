package com.noblapp.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.noblapp.model.cmd.JoinCmd;
import com.noblapp.model.cmd.SocialLoginCmd;
import com.noblapp.model.cmd.UpdateCmd;
import com.noblapp.model.support.AbstractCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.model.vo.SpendingInfo;
import com.noblapp.model.vo.UserInfo;
import com.noblapp.model.vo.VSpendingInfo;
import com.noblapp.service.support.AbstractService;
import com.noblapp.support.Aes128Crypt;
import com.noblapp.support.ErrorCode;
import com.noblapp.support.NoblappException;
import com.noblapp.support.Password;

/**
 * 유저용 서비스. 인자와 결과는 아래 spec문서 참조.<br>
 * <a href="https://github.com/noblapp/noblappServer/wiki/user-api">User api 문서</a>
 * @author juniverse
 */
@Service
public class UserService extends AbstractService {
	private Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired protected UserSessionService sessionService;

	private static final boolean USE_ENCRYPTED_PASSWORD = true;
	
	/**
	 * 회원 가입 처리.
	 */
	@Transactional
	public Map<String, Object> join(ParamVo param, JoinCmd cmd) throws Exception {
		// 빈 아이디 체크
		if (StringUtils.isEmpty(cmd.getId())) {
			logger.error("no id. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.EMPTY_ID);
		}

		// 존재 유무 체크
		UserInfo userInfo = db.getUserWithId(cmd.getId());
		if (userInfo != null) {
			logger.error("duplicate id. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.EXISTING_USER);
		}
		
		// 비밀번호 암호화
		if (USE_ENCRYPTED_PASSWORD) {
			String hashedPassword = Password.getSaltedHash(cmd.getPassword());
			logger.info("new password... length?", hashedPassword.length());
			cmd.setPassword(hashedPassword);
		}

		// 가입 처리
		int count = db.joinUser(cmd);
		if (count <= 0) {
			logger.error("unknown insert error. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.UNKNOWN);
		}

		// 유저 정보 다시 가져오기
		userInfo = db.getUserWithId(cmd.getId());
		if (userInfo == null) {
			logger.error("unknown insert error. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.UNKNOWN);
		}
		
		// TODO 상점 주인인 경우 체크를 해야 한다.... 그래서 그것도 같이 넘겨주기..

		// 유저 세션 시작
		String access_token = sessionService.startSession(userInfo.getUid());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", userInfo.getUid());
		map.put("access_token", access_token);
		map.put("name", userInfo.getName());
		return map;
	}
	
	/**
	 * 로그인 처리
	 */
	@Transactional
	public Map<String, Object> login(ParamVo param, JoinCmd cmd) throws Exception {
		UserInfo userInfo = db.getUserWithId(cmd.getId());
		if (userInfo == null) {
			logger.error("user not exist. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_USER);
		}

		// 비밀번호 암호화 사용시에는 해시 처리 한다.
		if (USE_ENCRYPTED_PASSWORD) {
			String hashedPassword = userInfo.getPassword();
			if (!Password.check(cmd.getPassword(), hashedPassword)) {
				logger.error("incorrect password. cmd[{}]", cmd);
				throw new NoblappException(ErrorCode.WRONG_PASSWORD);
			}
		} else {

			if (!userInfo.getPassword().equals(cmd.getPassword())) {
				logger.error("incorrect password. cmd[{}]", cmd);
				throw new NoblappException(ErrorCode.WRONG_PASSWORD);
			}
		}
		
		// 유저 세션 시작
		String access_token = sessionService.startSession(userInfo.getUid());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", userInfo.getUid());
		map.put("access_token", access_token);
		map.put("name", userInfo.getName());
		
		// TODO 추가 정보 필요...

		return map;
	}
	
	/**
	 * 로그인 처리
	 */
	@Transactional
	public Map<String, Object> checkId(ParamVo param, JoinCmd cmd) throws Exception {
		UserInfo userInfo = db.getUserWithId(cmd.getId());
		if (userInfo == null) {
			logger.error("user not exist. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_USER);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", userInfo.getUid());
		map.put("name", userInfo.getName());
		
		return map;
	}

	/**
	 * 로그아웃 처리. 그냥 세션 종료시킨다.
	 */
	@Transactional
	public Map<String, Object> logout(ParamVo param, AbstractCmd cmd) throws Exception {
	
		sessionService.endSession(cmd.getUid());
		
		return null;
	}
	
	/**
	 * 회원 정보 가져오기
	 */
	@Transactional
	public Map<String, Object> info(ParamVo param, AbstractCmd cmd) throws Exception {

		UserInfo userInfo = db.getUserWithUid(cmd.getUid());
		if (userInfo == null) {
			logger.error("user not exist. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_USER);
		}

		// TODO 무슨 데이터를 넣어줄지???
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", userInfo.getName());
		map.put("gender", userInfo.getGender());
		map.put("birth", userInfo.getDate());
		map.put("point", userInfo.getPoint());
		return map;
	}
	
	/**
	 * 회원 정보 업데이트
	 */
	@Transactional
	public Map<String, Object> update(ParamVo param, UpdateCmd cmd) throws Exception {

		UserInfo userInfo = db.getUserWithUid(cmd.getUid());
		if (userInfo == null) {
			logger.error("user not exist. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_USER);
		}
	
		// TODO 저 업데이트 인자에 더 필요한 정보가 있을 것 같은데...
		int result = db.updateUserInfo(cmd);
		if (result == 0) {
			logger.error("update user. unknown error. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.UNKNOWN);
		}

		// 딱히 성공 실패 말고는 넘겨줄게 있나???
		return null;
	}

	/**
	 * 포인트 사용 조회
	 */
	@Transactional
	public Map<String, Object> spending(ParamVo param, AbstractCmd cmd) throws Exception {

		List<Map<String, Object>> spendingInfo = db.getUserSpending(cmd.getUid());
	
		int total = 0;
		for (Map<String, Object> info : spendingInfo) {
			total += (int)info.get("point");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", total);
		map.put("list", spendingInfo);
		return map;
	}
	
	/**
	 * 보유 포인트 조회
	 */
	@Transactional
	public Map<String, Object> point(ParamVo param, AbstractCmd cmd) throws Exception {

		UserInfo userInfo = db.getUserWithUid(cmd.getUid());
		if (userInfo == null) {
			logger.error("user not exist. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_USER);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("point", userInfo.getPoint());
		return map;
	}
	

	
	// 아래는 소셜 로그인들...
	// 현재는 Google만 되어 있음 [2018.05.17 juniverse]
	private String getRedirectUrl() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String url = request.getRequestURL().toString();
		return url.substring(0, url.indexOf("/", url.indexOf(":") + 3));
	}

	@Transactional
	public Map<String, Object> loginWithGoogle(ParamVo param, SocialLoginCmd cmd) throws Exception {

		logger.info("login with google with code: " + cmd.getCode());

		String CLIENT_SECRET_FILE = "google_client_secret.json";
		ClassPathResource res = new ClassPathResource(CLIENT_SECRET_FILE);

		// Exchange auth code for access token
		GoogleClientSecrets clientSecrets =
		    GoogleClientSecrets.load(
		        JacksonFactory.getDefaultInstance(), new InputStreamReader(res.getInputStream()));

		// TODO 이걸 가지고 아래 redirect URI를 만들어야 겠다...??? 어짜피 cloud console에도 url을 넣어야 하는건데... 쩝... config에 넣을까???
		String url = getRedirectUrl();
//		logger.info("scheme: " + request.getLocalAddr());

		GoogleTokenResponse tokenResponse =
		          new GoogleAuthorizationCodeTokenRequest(
		              new NetHttpTransport(),
		              JacksonFactory.getDefaultInstance(),
		              "https://www.googleapis.com/oauth2/v4/token",
		              clientSecrets.getDetails().getClientId(),
		              clientSecrets.getDetails().getClientSecret(),
		              cmd.getCode(),
		              url)  // Specify the same redirect URI that you use with your web
		                             // app. If you don't have a web version of your app, you can
		                             // specify an empty string.
		              .execute();

		// accessToken은 나중에 google api를 사용할 때 쓰이는거... 지금은 딱히 필요 없음.
//		String accessToken = tokenResponse.getAccessToken();
//		logger.info("executed!! accessToken? " + accessToken);
		
		// Get profile info from ID token
		GoogleIdToken idToken = tokenResponse.parseIdToken();
		GoogleIdToken.Payload payload = idToken.getPayload();

		boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
		if (!emailVerified) {
			// TODO 이메일이 확인된게 아니라면 어떻게 해야 하지???
		}

		String userId = payload.getSubject();  // Use this value as a key to identify a user.
		String email = payload.getEmail();
		String name = (String) payload.get("name");

//		String pictureUrl = (String) payload.get("picture");
//		String locale = (String) payload.get("locale");
//		String familyName = (String) payload.get("family_name");
//		String givenName = (String) payload.get("given_name");
		
//		System.out.printf("profiles!!!\n%s, %s, %s, %s, %s, %s, %s, %b"
//				, userId, email, name, pictureUrl, locale, familyName, givenName, emailVerified);
		
		logger.info(String.format("payload: %s, %s, %s", userId, email, name));

		Map<String, Object> map;

		// check or add user
		// email(id), name(name), userId(password) ... userId를 저장할 곳이 필요하다.... (각 social 별로???)
		JoinCmd joinCmd = new JoinCmd();
		joinCmd.setId(email);
		joinCmd.setPassword(userId);
		joinCmd.setName(name);

		UserInfo user = db.getUserWithId(email);
		if (user == null) {
			// add user
			map = join(param, joinCmd);
		} else {
			// check user
			map = login(param, joinCmd);
		}
		
		return map;
	}
	
	@Transactional
	public Map<String, Object> loginWithFacebook(ParamVo param, SocialLoginCmd cmd) throws Exception {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			String fbGraphAPI = "https://graph.facebook.com/v2.12";
			String code = cmd.getCode();			// verification code
			String redirectUri = getRedirectUrl();
			String clientId = "";		// TODO
			String clientSecret = "";		// TODO

			String endpoint = String.format("%s/oauth/access_token?client_id=%s&redirect_uri=%s&client_secret=%s&code=%s",
					fbGraphAPI, clientId, redirectUri, clientSecret, code);
	
			HttpGet httpGet = new HttpGet(endpoint);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
			httpGet.setConfig(requestConfig);
		
			ResponseHandler<String> responseHandler = new ResponseHandler<String>(){
				@Override
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
				
			};

			String result = httpClient.execute(httpGet, responseHandler);
			JsonNode jsonNode = new ObjectMapper().readTree(result);
			String accessToken = jsonNode.get("access_token").asText();
			String tokenType = jsonNode.get("token_type").asText();
			int expireIn  = jsonNode.get("expires_in").asInt(-1);
			
			logger.info("access_token? " + accessToken);
			
			try {httpClient.close();} catch (IOException e) {e.printStackTrace();}

			endpoint = String.format("%s/me?fields=id,name,email&access_token=%s", fbGraphAPI, accessToken);
			httpGet = new HttpGet(endpoint);
			httpGet.setConfig(requestConfig);
			
			result = httpClient.execute(httpGet, responseHandler);

			logger.info("/me result? " + result);

		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try {httpClient.close();} catch (IOException e) {e.printStackTrace();}
		}

		return null;
	}
	
	
	@Transactional
	public Map<String, Object> loginWithNaver(ParamVo param, SocialLoginCmd cmd) throws Exception {
		// TODO 추가!!!

		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			String naverAPI = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code";
			String code = cmd.getCode();			// verification code
			String redirectUri = getRedirectUrl();
			String clientId = "";		// TODO
			String clientSecret = "";		// TODO
			String state = "";

			String endpoint = String.format("%s&client_id=%s&redirect_uri=%s&client_secret=%s&code=%s&state=%s",
					naverAPI, clientId, redirectUri, clientSecret, code, state);
	
			HttpGet httpGet = new HttpGet(endpoint);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(60000).setConnectTimeout(60000).build();
			httpGet.setConfig(requestConfig);
		
			ResponseHandler<String> responseHandler = new ResponseHandler<String>(){
				@Override
				public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};

			String result = httpClient.execute(httpGet, responseHandler);
			logger.info("token result? " + result);
//
//			JsonNode jsonNode = new ObjectMapper().readTree(result);
//			String accessToken = jsonNode.get("access_token").asText();
//			String tokenType = jsonNode.get("token_type").asText();
//			int expireIn  = jsonNode.get("expires_in").asInt(-1);
//			
//			logger.info("access_token? " + accessToken);
//			
//			try {httpClient.close();} catch (IOException e) {e.printStackTrace();}
//
//			endpoint = String.format("%s/me?fields=id,name,email&access_token=%s", fbGraphAPI, accessToken);
//			httpGet = new HttpGet(endpoint);
//			httpGet.setConfig(requestConfig);
//			
//			result = httpClient.execute(httpGet, responseHandler);
//
//			logger.info("/me result? " + result);

		} catch(Exception e){
			e.printStackTrace();
		}finally {
			try {httpClient.close();} catch (IOException e) {e.printStackTrace();}
		}

		return null;
	}
}
