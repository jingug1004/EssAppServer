package com.noblapp.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.noblapp.model.vo.UserInfo;
import com.noblapp.service.support.AbstractService;
import com.noblapp.support.ErrorCode;
import com.noblapp.support.MvcApplicationConfig;
import com.noblapp.support.NoblappException;

/**
 * 유저 세션 관리 서비스
 * user 테이블에 access_token을 관리한다.
 * 세션 시작시에는 새로운 access_token을 생성하여 저장하고 추후 들어오는 api에 세션 체크가 필요한 경우 비교해서 허용하던가 아니면 막던가...
 * 
 * @author juniverse
 */
@Service
public class UserSessionService extends AbstractService {
	private Logger logger = LoggerFactory.getLogger(UserSessionService.class);

	@Autowired protected MvcApplicationConfig applicationConfig;

	/**
	 * 새로운 access_token 생성 및 db에 저장.
	 * 
	 * @param uid 로그인한 유저 아이디
	 * @return 생성된 access_token
	 * @throws Exception
	 */
	@Transactional
	public String startSession(int uid) throws Exception {
		String access_token = UUID.randomUUID().toString();

		int result = db.updateUserSession(uid, access_token);
		if (result == 0) {
			logger.error("user not exist.");
			throw new NoblappException(ErrorCode.NO_USER);
		}

		return access_token;
	}
	
	/**
	 * 세션 종료 처리. 저장된 access_token을 삭제한다.
	 * 
	 * @param uid 세션 종료할 유저 아이디
	 * @return true
	 * @throws Exception
	 */
	@Transactional
	public boolean endSession(int uid) throws Exception {
		int result = db.updateUserSession(uid, null);
		if (result == 0) {
			logger.error("user not exist.");
			throw new NoblappException(ErrorCode.NO_USER);
		}

		return true;
	}
	
	/**
	 * 세션 확인. 해당 유저의 access_token이 유효한지 확인한다.
	 * 
	 * @param uid
	 * @param access_token
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean checkValid(int uid, String access_token) throws Exception {
		logger.info("checking user: " + uid + " with token: " + access_token);

		// 개발 단계에서는 체크하지 말자.
		if (applicationConfig.getDevStage().equals("alpha"))
			return true;
		
		UserInfo userInfo = db.getUserWithUid(uid);

		if (userInfo == null) {
			logger.error("user not exist");
			throw new NoblappException(ErrorCode.NO_USER);
		}
		
		if (userInfo.getAccess_token() == null || !userInfo.getAccess_token().equals(access_token)) {
			// 확인했는데 토큰이 틀려... 그러면 세션을 날린다...
			db.updateUserSession(uid, null);
			logger.error("invalid access_token!! [" + uid + "] " + access_token);
			throw new NoblappException(ErrorCode.INVALID_SESSION);
		}

		return true;
	}

}
