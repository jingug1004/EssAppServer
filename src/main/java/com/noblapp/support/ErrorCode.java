package com.noblapp.support;

/**
 * 에러코드들....
 * @author juniverse
 *
 */
public class ErrorCode {
	public static final int UNKNOWN = 99999;				// 모르는 에러...대부분 db쿼리때 뭔지 모를 때 발생

	public static final int NO_RELATED_DATA = 99998;		// 관련된 데이터가 없
	//public static final int NOT_AVAILABLE_API = 99997;		// 존재하지 않는 API
	
	// user related
	public static final int EMPTY_ID = 10001;				// 아이디 없음
	public static final int EXISTING_USER = 10002;			// 존재하는 사용자
	public static final int NO_USER = 10003;				// 존재하지 않는 사용자
	public static final int WRONG_PASSWORD = 10004;			// 비밀번호 틀림
	public static final int INVALID_SESSION = 10005;		// 유효하지 않은 세션
	
	
	// shop related
	public static final int INVALID_OWNER = 20001;			// 상점 주인이 아님
	public static final int NOT_ENOUGH_POINT = 20002;		// 포인트가 모자람
	
	
	// leisure 관련 
	public static final int NO_NEAREST_CP = 30001;			// 가까운 체크 포인트가 없음.
	
	// cms related
	public static final int HAS_RELATED_PLACE_DATA = 80001;		// 관련된 장소 데이터가 있음
	public static final int HAS_RELATED_CATEGORY_DATA = 80002;	// 관련된 카테고리 데이터가 있음
	public static final int HAS_RELATED_TREKKING_DATA = 80006;	// 관련된 트레킹 데이터가 있음
	public static final int INCORRECT_FORMAT = 80003;			// 포맷이 틀림
	public static final int NOT_ADMIN = 80004;					// 관리자가 아님
	public static final int NOT_ALLOWED = 80005;				// 허용되지 않은 액션
}
