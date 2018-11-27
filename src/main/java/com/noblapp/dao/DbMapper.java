package com.noblapp.dao;

import com.noblapp.model.cmd.*;
import com.noblapp.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

//import kr.kjh.pp.model.AccountDevice;
//import kr.kjh.pp.model.AccountInfo;
//import kr.kjh.pp.model.AccountPlatform;
//import kr.kjh.pp.model.BoardComment;
//import kr.kjh.pp.model.BoardGroup;
//import kr.kjh.pp.model.BoardInfo;
//import kr.kjh.pp.model.FriendInfo;
//import kr.kjh.pp.model.NoteInfo;
//import kr.kjh.pp.model.PollInfo;
//import kr.kjh.pp.model.UserInfo;

/**
 * DB 쿼리를 매핑해주는 것. DbMapper.xml 파일에 같은 이름으로 선언되어 있음.
 * @author juniverse
 */
public interface DbMapper {
	
	int updateUserSession(@Param("uid") int uid, @Param("key") String key);

	// user related
	UserInfo getUserWithId(String id);
	UserInfo getUserWithPassword(JoinCmd cmd);
	UserInfo getUserWithUid(int uid);

	int joinUser(JoinCmd cmd);
	int updateUserInfo(UpdateCmd cmd);
	
	List<Map<String, Object>> getUserSpending(int uid);

	
	// leisure related
	List<Map<String, Object>> getNearSector(Map<String, Object> cmd);
	Map<String, Object> getTrekCourse(Map<String, Object> cmd);
	Map<String, Object> getTrekCourse(@Param("tc_id") long tc_id);

	int getIncompleteUserTrekCount(Map<String, Object> cmd);
	int insertUserTrekkingLog(Map<String, Object> cmd);
	int updateTrekCourseCmplt(Map<String, Object> cmd);
	int insertTrekCertCmplt(@Param("uid") int uid, @Param("tc_id") long tc_id);
	int insertUserTrekkingFame(int uid);
	int getUserTrekkingCertCmplt(int uid);
	int getUserTrekkingTotalPoints(int uid);
	List<Map<String, Object>> getUserTrekkingCert(int uid);
	List<Map<String, Object>> getUserTrekkingFame(int uid);
	
	List<Map<String, Object>> getUserTrekkingLog(Map<String, Object> cmd);
	Map<String, Object> getUserEventTrekkingLog(Map<String, Object> cmd);
	
	
	// shop related
	List<ShortStoreInfo> getPointStoreList(LocationCmd cmd);
	StoreInfo getStoreInfo(int sid);
	List<SpendingInfo> getSpendingList(int sid);
	int updateStoreInfo(StoreUpdateCmd cmd);
	
	// point 사용 transactions
	int insertUserSpending(SpendReqCmd cmd);
	int updateUserPoint(@Param("uid") int uid, @Param("point") int point);
//	List<Map<String, Object>> getSpendingReqList(StoreCmd cmd);
	List<Map<String, Object>> getSpendingReqList(Map<String, Object> cmd);
	SpendingInfo getSpendingReq(int us_id);
	int updateStorePoint(@Param("sid") int sid, @Param("point") int point);
	int updateUserSpendingPoint(SpendConfirmCmd cmd);


	// location related
	List<Map<String, Object>> getMappedPlaces(@Param("cid") long cid, @Param("lang") String lang);
	List<Map<String, Object>> getPlacesList(@Param("lang") String lang, @Param("cid") long cid, @Param("from") int from, @Param("count") int count);
	Map<String, Object> getPlacesDetail(@Param("lang") String lang, @Param("pid") long pid);
	Map<String, Object> getSpotDetail(@Param("lang") String lang, @Param("pid") long pid);
	List<Map<String, Object>> getSpotsList(@Param("lang") String lang);
	
	List<Map<String, Object>> searchPlaces(@Param("lang") String lang, @Param("search_value") String search_value);
	
	
	// etc related
	Map<String, Object> getWeather();
	int insertWeather(Map<String, Object> map);
	int deleteWeather();	
	List<StringInfo> getStringTable(@Param("lang") String lang);
	Map<String, Object> getTrekkingStats();
	void insertVisitorLog(Map<String, Object> cmd);
	List<Map<String, Object>> getVisitorsCountBy(@Param("type") String type, @Param("max") int max);
	int getVisitorTodayCount();
	int getVisitorTotalCount();
	
	
	// serivce config
	List<Map<String, Object>> getServiceConfig();
	List<Map<String, Object>> getServiceConfigNonSecret();
    int insertServiceConfig(@Param("name") String name, @Param("value") String value, @Param("desc") String desc);
	int updateServiceConfig(@Param("id") long id, @Param("value") String value, @Param("desc") String desc);
    int deleteServiceConfig(@Param("id") long id);
	
	// CMS 관리용...
	Map<String, Object> getAdminUser();
	List<Map<String, Object>> getAdministrators();
	int addAdmin(Map<String, String> admin);
	ManagementInfo getManagementUser(@Param("id") String id);
	int updateManagerPassword(@Param("id") String id, @Param("password") String password);

	// CMS 카테고리
	int getPlaceCategoryCount();
	int getPlaceCategoryCount(@Param("pcid") long pcid);
	List<Map<String, Object>> getPlaceCategories(@Param("lang") String lang, @Param("from") int from, @Param("count") int count);
	List<Map<String, Object>> getRootCategories();
	int insertPlaceCategory(Map<String, Object> category);
	int updatePlaceCategory(Map<String, Object> category);
	int insertCategoryLocalizedStrings(Map<String, Object> map);
	int updateCategoryLocalizedStrings(Map<String, Object> map);
	int deletePlaceCategory(long cid);

	// CMS 주요 장소
	int getPlaceCount();
	int getPlaceCount(@Param("cid") long cid);
	List<Map<String, Object>> getPlaces(@Param("lang") String lang, @Param("from") int from, @Param("count") int count);
	List<Map<String, Object>> getPlaces(@Param("lang") String lang, @Param("from") int from, @Param("count") int count, @Param("cid") long cid, @Param("pid") long pid);
	int insertPlace(Map<String, Object> map);
	int updatePlace(Map<String, Object> map);
	int insertPlacesLocalizedStrings(Map<String, Object> map);
	int updatePlacesLocalizedStrings(Map<String, Object> map);
	int deletePlace(long pid);

	// CMS 픽토그램
	int getPictogramCount();
	List<Map<String, Object>> getPictograms(@Param("from") int from, @Param("count") int count);
	int insertPictogram(Map<String, Object> map);
	int updatePictogram(Map<String, Object> map);
	int deletePictogram(long pid);

	// CMS 유저 관련
	int getUserCount();
	List<Map<String, Object>> getUsers(@Param("from") int from, @Param("count") int count);
	int insertUser(Map<String, Object> map);
	int updateUser(Map<String, Object> map);
	int deleteUser(long uid);

	// CMS trekking 관련 
	int getTrekCourseCount(@Param("tc_type") int tc_type);
	List<Map<String, Object>> getTrekCourses(@Param("from") int from, @Param("count") int count, @Param("status") int status);
	int insertTrekCourse(Map<String, Object> map);
	int updateTrekCourse(Map<String, Object> map);
	List<Map<String, Object>> getCourseSectors(Map<String, Object> cmd);
	int deleteTrekCourse(long tc_id);
	int deleteCategoryRelatedTrekCourse(long cid);

	int insertTrekGroup(Map<String, Object> map);
	int removeTrekGroup(@Param("tc_id") long tc_id, @Param("g_idx") long g_idx);
	
	List<Map<String, Object>> getGroupIndexOfSectors(@Param("tc_id") long tc_id);
	List<Map<String, Object>> getSectors(@Param("tc_id") long tc_id, @Param("g_idx") long g_idx);
	List<Map<String, Object>> getSectorsExcept(@Param("tc_id") long tc_id, @Param("g_idx") long g_idx);
	int insertSector(Map<String, Object> sector);
	int insertSectors(@Param("sectors") List<Map<String, Object>> sectors);
	int updateSectorGroupIndex(@Param("tc_id") long tc_id, @Param("old_g_idx") long old_g_idx, @Param("new_g_idx") long new_g_idx);
	int deleteSectors(@Param("tc_id") long tc_id, @Param("g_idx") long g_idx);
	int deleteSectorsInGroups(@Param("tc_id") long tc_id, @Param("g_idxs") int[] g_idxs);
	
	
	
	
	int updateRelatedSector(Map<String, Object> relation);
	
	
	
	int getSpotCount();
	Map<String, Object> getSpot(@Param("lang") String lang, @Param("pid") long pid);
	List<Map<String, Object>> getSpots(@Param("lang") String lang, @Param("from") int from, @Param("count") int count, @Param("mid") long mid);
	int insertSpot(Map<String, Object> map);
	int updateSpot(Map<String, Object> map);
	int insertSpotLocalizedStrings(Map<String, Object> map);
	int deleteSpot(long id);


	
	int getImapCount();
	Map<String, Object> getImap(@Param("mid") long mid);
	List<Map<String, Object>> getImaps(@Param("lang") String lang);
	int insertImap(Map<String, Object> map);
	int updateImap(Map<String, Object> map);


	int insertImapLocalizedStrings(Map<String, Object> map);
	int deleteImap(long id);
	
	List<Map<String, Object>> getAllPlaces(@Param("lang") String lang);
	
	
	// 1: 일반 코스, 2: 이벤트 코스
	List<Map<String, Object>> getTrekkingCompleteByUser(@Param("uid") int uid, @Param("tc_type") int tc_type);
	List<Map<String, Object>> getTrekkingGroupByPid(Map<String, Object> map);
	List<Map<String, Object>> getTrekkingCourseByCid(Map<String, Object> map);


	int getEventCount();
	List<Map<String, Object>> getEvents(@Param("lang") String lang, @Param("from") int from, @Param("count") int count);
	int insertEvent(Map<String, Object> map);
	int updateEvent(Map<String, Object> map);
	int insertEventSub(Map<String, Object> map);
	int updateEventSub(Map<String, Object> map);
	int deleteEvent(long id);

	int getTouchLogCount();
	List<Map<String, Object>> getTouchLogList(@Param("from") int from, @Param("count") int count);
	void updateTouchLog(@Param("table_type") String table_type, @Param("pid") long pid);
	void deleteTouchLog(long id);
}

