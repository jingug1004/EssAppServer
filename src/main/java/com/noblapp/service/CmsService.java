package com.noblapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.noblapp.model.cmd.JoinCmd;
import com.noblapp.model.support.ParamVo;
import com.noblapp.model.vo.UserInfo;
import com.noblapp.service.support.AbstractService;
import com.noblapp.support.ErrorCode;
import com.noblapp.support.NoblappException;
import com.noblapp.support.Password;
import com.noblapp.support.ServiceConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * CMS용 서비스.
 * @author juniverse
 */
@Service
public class CmsService extends AbstractService {

	@Autowired private ServiceConfig serviceConfig;
	@Autowired private AssetService asset;
	@Autowired protected UserSessionService sessionService;
	
	private static final int SUPER_ADMIN = 9;
	private static final int ADMIN = 8;

	private Logger logger = LoggerFactory.getLogger(CmsService.class);
	
	
	@Transactional
	public Map<String, Object> getAdminInfo() throws Exception {
		return db.getAdminUser();
	}
	
	@Transactional
	public List<Map<String, Object>> getAdministrators() throws Exception {
		return db.getAdministrators();
	}

	@Transactional
	public boolean addAdmin(ParamVo param, Map<String, String> cmd) throws Exception {
		if (db.getAdminUser() != null) {
			throw new NoblappException(ErrorCode.EXISTING_USER);
		}
		
		// 빈 아이디 체크
		if (StringUtils.isEmpty(cmd.get("id"))) {
			logger.error("no id. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.EMPTY_ID);
		}

		// 비밀번호 암호화
		String hashedPassword = Password.getSaltedHash(cmd.get("password"));
		logger.info("new password... length?", hashedPassword.length());
		cmd.put("password", hashedPassword);

//		Set<String> keys = cmd.keySet();
//		for (String key: keys) 
//			logger.info(key + " : " + cmd.get(key));

		int count = db.addAdmin(cmd);
		if (count <= 0) {
			logger.error("unknown insert error. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.UNKNOWN);
		}

		return true;
	}

	// 관리용!!! 절대 외부로 노출되서는 안됨!!!
//	@Transactional
//	public int changeManagerPassword(String id, String password) throws Exception {
//		// 알파 스테이지에서만 가능함...
//		if (!appConfig.getDevStage().equals("alpha")) {
//			throw new NoblappException(ErrorCode.NOT_AVAILABLE_API);
//		}
//			
//		ManagementInfo info = db.getManagementUser(id);
//		if (info == null) {
//			logger.error("no related data.");
//			throw new NoblappException(ErrorCode.NO_RELATED_DATA);
//		}
//		
//		String hashedPassword = Password.getSaltedHash(password);
//		logger.info("new password... length?", hashedPassword.length());
////		cmd.setPassword(hashedPassword);
//		
//		return db.updateManagerPassword(id,  hashedPassword);
//	}

	
	@Transactional
	public Map<String, Object> loginManagement(JoinCmd cmd) throws Exception {
		UserInfo userInfo = db.getUserWithId(cmd.getId());
		if (userInfo == null) {
			logger.error("user not exist. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NO_USER);
		}

		if (userInfo.getType() != SUPER_ADMIN && userInfo.getType() != ADMIN) {
			logger.error("duplicate id. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.NOT_ADMIN);
		}

		String hashedPassword = userInfo.getPassword();
		if (!Password.check(cmd.getPassword(), hashedPassword)) {
			logger.error("incorrect password. cmd[{}]", cmd);
			throw new NoblappException(ErrorCode.WRONG_PASSWORD);
		}

		// session 관리..
		String access_token = sessionService.startSession(userInfo.getUid());

		Map<String, Object> map = new HashMap<>();
		map.put("uid", userInfo.getUid());
		map.put("access_token", access_token);
		map.put("name", userInfo.getName());
		map.put("type", userInfo.getType());
		return map;
	}
	
	@Transactional
	public boolean logoutManagement() throws Exception {
		Map<String, Object> admin = db.getAdminUser();
		if (admin == null) {
			logger.error("user not exist.");
			throw new NoblappException(ErrorCode.NO_USER);
		}

		sessionService.endSession((int)admin.get("uid"));
		return true;
	}
	
	
	
	
	

	@Transactional
	public int getCategoryCount() throws Exception {
		return db.getPlaceCategoryCount();
	}
	
	@Transactional
	public List<Map<String, Object>> getRootCategories() throws Exception {
		List<Map<String, Object>> categories = db.getRootCategories();
		Map<String, Object> root = new HashMap<>();
		root.put("cid", 0);
		root.put("title", "최상");
		root.put("icon_url", "");
		root.put("pcid", 0);
		root.put("status", 1);
		categories.add(0, root);
		return categories;
	}
	
	@Transactional
	public List<Map<String, Object>> getPlaceCategories(String lang, int page, int count) throws Exception {
		List<Map<String, Object>> categories = db.getPlaceCategories(lang, page, count);
		return categories;
	}
	
	
	private String[] category_tables = {"place_category_names", "place_category_fields"};
	private String[] category_prefixs = {"name_", "ccf_"};
	@Transactional
	public void updateCategory(MultipartHttpServletRequest mreq) throws Exception {
		Map<String, Object> parameter = new HashMap<>();
		long cid = Long.parseLong(mreq.getParameter("cid"));
		boolean isInsert = cid == 0;
		
		parameter.put("pcid", Integer.parseInt(mreq.getParameter("pcid")));
		parameter.put("ccu", mreq.getParameter("ccu"));
		parameter.put("link", mreq.getParameter("link"));
		parameter.put("sort", mreq.getParameter("sort"));
		
		if (isInsert) {
			db.insertPlaceCategory(parameter);
//			cid = (long) parameter.get("cid");
			cid = ((BigInteger) parameter.get("cid")).longValue();
			logger.info("inserted id? " + cid);
		} else {
			parameter.put("cid", cid);
			db.updatePlaceCategory(parameter);
			logger.info("updated id? " + cid);
		}
		
		MultipartFile mfile = mreq.getFile("icon_file");
		if (mfile != null && mfile.getSize() > 0) {
			parameter.put("icon_url", asset.storeCategoryAssets(cid, mfile));
			db.updatePlaceCategory(parameter);
		}

		mfile = mreq.getFile("help_file");
		if (mfile != null && mfile.getSize() > 0) {
			parameter.put("help_file", asset.storeCategoryAssets(cid, mfile));
			db.updatePlaceCategory(parameter);
		}

		String help_file_delete = mreq.getParameter("help_file_delete");
		if(StringUtils.isNotBlank(help_file_delete)){
			asset.deleteFile("category", parameter.get("cid").toString(), help_file_delete);
			parameter.put("help_file", "");
			db.updatePlaceCategory(parameter);
		}

		String[] languages = serviceConfig.getSupportedLanguage();

		// language 별로 합치기...
		Map<String, Object> localStringMap = new HashMap<>();

		// 이름...
		localStringMap.clear();
		localStringMap.put("cid", cid);
		localStringMap.put("table_name", category_tables[0]);

		for (String lang : languages) {
			String val = new String(mreq.getParameter(category_prefixs[0] + lang).getBytes("iso-8859-1"), "UTF-8");
			localStringMap.put(lang, val);
		}

		if (isInsert)
			db.insertCategoryLocalizedStrings(localStringMap);
		else
			db.updateCategoryLocalizedStrings(localStringMap);

		// 전용 필드 ..
		ObjectMapper mapper = new ObjectMapper();
		localStringMap.clear();
		localStringMap.put("cid", cid);
		localStringMap.put("table_name", category_tables[1]);

		for (String lang : languages) {
			String field_names = "";
			int num_of_fields = Integer.parseInt(mreq.getParameter("num_of_fields"));
			if (num_of_fields > 0) {
				List<String> names = new ArrayList<>();
				for (int j = 0; j < num_of_fields; j++) {
					String val = new String(mreq.getParameter(category_prefixs[1] + lang + "_" + j).getBytes("iso-8859-1"), "UTF-8");
					names.add(val);
				}
				field_names = mapper.writeValueAsString(names);
			}
			localStringMap.put(lang, field_names);
		}

		if (isInsert)
			db.insertCategoryLocalizedStrings(localStringMap);
		else
			db.updateCategoryLocalizedStrings(localStringMap);
	}

	@Transactional
	public void updateCategoryStatus(Map<String, Object> parameter) throws Exception {
		if (parameter.get("id") != null)
			parameter.put("cid", parameter.get("id"));
		db.updatePlaceCategory(parameter);
		
		int cid = (int) parameter.get("id");
		logger.info("status changed for id? " + cid);
	}
	
	@Transactional
	public int deleteCategory(long cid) throws Exception {
		// 의존성 체크를 해야 함...
		// 1. 해당 카테고리를 부모로 하고 삼고 있는 카테고리가 있는지 확인.
		if (db.getPlaceCategoryCount(cid) > 0) {
			throw new NoblappException(ErrorCode.HAS_RELATED_CATEGORY_DATA);
		}

		// 2. 해당 카테고리의 장소가 있는지 확인...
		if (db.getPlaceCount(cid) > 0) {
			throw new NoblappException(ErrorCode.HAS_RELATED_PLACE_DATA);
		}
		
		// 3. 이벤트 트레킹 관련 데이터 삭제...
		if (db.deleteCategoryRelatedTrekCourse(cid) > 0) {
			throw new NoblappException(ErrorCode.HAS_RELATED_TREKKING_DATA);
		}

		return db.deletePlaceCategory(cid);
	}

	
	
	
	
	
	
	
	@Transactional
	public int getPlaceCount() throws Exception {
		return db.getPlaceCount();
	}
	
	@Transactional
	public List<Map<String, Object>> getPlaces(String lang, int page, int count, int cid) throws Exception {
		List<Map<String, Object>> places = db.getPlaces(lang, page, count, cid, 0);
		logger.info("places? " + places.size());
		return places;
	}

	
	private String[] place_tables = {"places_names", "places_addresses", "places_texts", "places_c_custom_values", "places_custom_fields", "places_custom_values"};
	private String[] place_prefixs = {"name_", "address_", "text_", "c_custom_value_", "custom_field_", "custom_value_"};
	@Transactional
	public void updatePlace(MultipartHttpServletRequest mreq) throws Exception {
		mreq.setCharacterEncoding("UTF-8");

		ObjectMapper mapper = new ObjectMapper();
		TypeFactory typeFactory = mapper.getTypeFactory();

		// 추가에 필요한 데이터 구성...
		Map<String, Object> parameter = new HashMap<>();
		boolean isInsert = Integer.parseInt(mreq.getParameter("pid")) == 0;

		parameter.put("cid", Long.parseLong(mreq.getParameter("cid")));
		
		parameter.put("phone", mreq.getParameter("phone"));
		parameter.put("lat", Double.parseDouble(mreq.getParameter("lat")));
		parameter.put("lng", Double.parseDouble(mreq.getParameter("lng")));
		long map_type = Long.parseLong(mreq.getParameter("map_type"));
		parameter.put("map_type", map_type);

		String file_delete = mreq.getParameter("file_delete");

		// map_type에 따른 다른 처리 방식...
		if (map_type == 0) {
			parameter.put("map_res_url", "");
		} else if (map_type == 1) {
			parameter.put("map_res_url", mreq.getParameter("pictogram_id"));
			// pictogram
		} else {
			// 아래에서 처리한다... 아우.. 지저분해.. ㅠ.ㅠ
		}

		if(StringUtils.isNotBlank(mreq.getParameter("display_zoom")))
			parameter.put("display_zoom", Integer.parseInt(mreq.getParameter("display_zoom")));
		
		long pid = 0;

		List<String>[] prevAssets = new List[3];
		String[] assetNames = new String[] {"image", "video", "other"};
		
		// 장소 정보를 추가할지 업데이트 할지...
		if (isInsert) {
			db.insertPlace(parameter);
			pid = ((BigInteger) parameter.get("pid")).longValue();
			logger.info("inserted id? " + pid);

			prevAssets[0] = new ArrayList<>();
			prevAssets[1] = new ArrayList<>();
			prevAssets[2] = new ArrayList<>();

		} else {
			parameter.put("pid", Long.parseLong(mreq.getParameter("pid")));
			db.updatePlace(parameter);
			pid = Long.parseLong(mreq.getParameter("pid"));
			logger.info("updated id? " + pid);
			
			parameter = db.getPlacesDetail("ko", pid);
			
			// 업데이트 하는 경우 바뀌는게 있나 보자.. 없어지는 경우에만 처리가 된다. 삭제하고 새로 추가했으면 아래에서 바뀌????
			for (int i = 0; i < assetNames.length; i++) {
				String arr = "";
				Object arrString = parameter.get(assetNames[i] + "s");
				if (arrString != null)
					arr = arrString.toString();
				if (arr.isEmpty())
					arr = "[]";
				prevAssets[i] = mapper.readValue(arr, typeFactory.constructCollectionType(List.class, String.class));
				Integer[] incomings = mapper.readValue(mreq.getParameter("incoming_" + assetNames[i]), Integer[].class);
				
				List<String> newArray = new ArrayList<>();
				for (int idx : incomings) {
					if (idx < prevAssets[i].size())
						newArray.add(prevAssets[i].get(idx));
				}
				prevAssets[i] = newArray;
					
				logger.info("recontructed : " + mapper.writeValueAsString(prevAssets[i]));
			}
		}
		
		String mapRes = null;
		Iterator<String> iter = mreq.getFileNames();
		while (iter.hasNext()) {
			String fn = iter.next();

			if(fn.startsWith("input_custom_field")) {
				continue;
			}

			MultipartFile file = mreq.getFile(fn);

			if (file == null || file.getSize() <= 0)
				continue;

			String filename = asset.storePlacesAssets(pid, file);
			
			// 합쳐서 보여줘야 할 듯...
			String res_name = file.getName();
			if (res_name.startsWith("image")) {
				int index = 0;
				if (!res_name.startsWith("image_main"))
					index = Integer.parseInt(res_name.split("_")[2]);

				if (prevAssets[0].size() > index)
					prevAssets[0].set(index, filename);
				else 
					prevAssets[0].add(filename);
			} else if (res_name.startsWith("video")) {
				prevAssets[1].add(filename);
			} else if (res_name.startsWith("other")) {
				prevAssets[2].add(filename);
			} else if (res_name.equals("map_res_url")) {
				mapRes = filename;
			}
		}

		// 이미지 (또는 비디오) 파일 등이 업데이트 됬는지 확인하고...
		boolean update = false;
		for (int i = 0; i < assetNames.length; i++) {
			if (prevAssets[i].size() > 0) {
				// check count...
				parameter.put(assetNames[i] + "s", mapper.writeValueAsString(prevAssets[i]));
				update = true;
			}
		}

//		if (mapRes != null) { // 이거 있으면 삭제 안됨.
        System.out.println("mapRes : "+mapRes);
		if (mapRes != null || file_delete.equals("delete")) {
			parameter.put("map_res_url", mapRes);
			update = true;
		}
		
		// 업데이트 된거면 데이터 업데이트 해.
		if (update)
			db.updatePlace(parameter);

		String[] languages = serviceConfig.getSupportedLanguage();

		int cfNum = Integer.parseInt(mreq.getParameter("custom_field_num"));
		System.out.println(cfNum);

		List<String> tempList;
		Map<String, String> saveCfDataMap = new HashMap<>();

		for (String lang : languages) {
			tempList = new ArrayList();
			for(int i=1; i<=cfNum; i++) {
				tempList.add(new String(mreq.getParameter("input_custom_field_name_" + lang + "_" + i).getBytes("iso-8859-1"), "UTF-8"));
			}
			saveCfDataMap.put("name_"+lang, mapper.writeValueAsString(tempList));
		}

		for (String lang : languages) {
			tempList = new ArrayList();
			for(int i=1; i<=cfNum; i++) {
				String key = "input_custom_field_value_" + lang + "_" + i;
				if(StringUtils.isBlank(mreq.getParameter(key))){
					MultipartFile file = mreq.getFile(key);
					if (file == null || file.getSize() <= 0) {
						if(StringUtils.isEmpty(mreq.getParameter("before_"+key))) {
							tempList.add("");
						}else{
							tempList.add(mreq.getParameter("before_"+key));
						}
					}else{
						tempList.add(asset.storePlacesAssets(pid, file));
					}
				}else{
					tempList.add(new String(mreq.getParameter(key).getBytes("iso-8859-1"), "UTF-8"));
				}
			}
			saveCfDataMap.put("value_"+lang, mapper.writeValueAsString(tempList));
		}

		Map<String, Object> localStringMap = new HashMap<>();


		for (int i = 0; i < place_tables.length; i++) {
			localStringMap.clear();
			localStringMap.put("pid", pid);
			localStringMap.put("table_name", place_tables[i]);

			if(place_tables[i].equals("places_custom_fields")) {
				for (String lang : languages) {
					localStringMap.put(lang, saveCfDataMap.get("name_"+lang));
				}
			}else if(place_tables[i].equals("places_custom_values")) {
				for (String lang : languages) {
					localStringMap.put(lang, saveCfDataMap.get("value_"+lang));
				}
			}else{
				for (String lang : languages) {
					localStringMap.put(lang, new String(mreq.getParameter(place_prefixs[i] + lang).getBytes("iso-8859-1"), "UTF-8"));
				}
			}

			if (isInsert)
				db.insertPlacesLocalizedStrings(localStringMap);
			else
				db.updatePlacesLocalizedStrings(localStringMap);
		}
	}
		
	@Transactional
	public void updatePlaceStatus(Map<String, Object> parameter) throws Exception {
		if (parameter.get("id") != null)
			parameter.put("pid", parameter.get("id"));
		db.updatePlace(parameter);
		
		int pid = (int) parameter.get("id");
		logger.info("status changed for id? " + pid);
	}
	
	@Transactional
	public int deletePlace(long pid) throws Exception {
		return db.deletePlace(pid);
	}

	@Transactional
	public Map<String, Object> getPlace(long pid) throws Exception {
		return db.getPlacesDetail("ko", pid);
	}
	
	@Transactional
	public Map<String, Object> updatePlaceLocation(Map<String, Object> parameter) throws Exception {
		logger.info("updatePlaceLocation");
		Set<String> keys = parameter.keySet();
		for (String key: keys) 
			logger.info(key + " : " + parameter.get(key));

		db.updatePlace(parameter);
		
		// 만약 해당 장소가 이벤트 트래킹과 관련이 있는 장소이면 해당 정보도 바꿔줘야 함... (바뀐게 lat, lng) 이라면...
		// 먼저 이벤트 트래킹과 관련이 있는지 확인.
		// 아래 쿼리가 다 알아서 해줌.... 진짜루!?!?!?!
		int result = db.updateRelatedSector(parameter);
		logger.info("updateRelatedSector count? " + result);

		return null;
	}	
	
	

	@Transactional
	public int getPictogramCount() throws Exception {
		return db.getPictogramCount();
	}
	
	@Transactional
	public List<Map<String, Object>> getPictograms(int page, int count) throws Exception {
		return db.getPictograms(page, count);
	}

	
	@Transactional
	public void updatePictogram(MultipartHttpServletRequest mreq) throws Exception {

		Map<String, Object> parameter = new HashMap<>();
		boolean isInsert = Integer.parseInt(mreq.getParameter("pid")) == 0;

		String val = new String(mreq.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
		parameter.put("title", val);
		
		long pid = 0;
		if (isInsert) {
			db.insertPictogram(parameter);
//			pid = (long) parameter.get("pid");
			pid = ((BigInteger) parameter.get("pid")).longValue();
			logger.info("inserted id? " + pid);
		} else {
			pid = Long.parseLong(mreq.getParameter("pid"));
			parameter.put("pid", pid);
			db.updatePictogram(parameter);

			logger.info("updated id? " + pid);
		}
		
		MultipartFile mfile = mreq.getFile("icon_file");
		if (mfile != null && mfile.getSize() > 0) {
			String filename = asset.storePictogramAssets(pid, mfile);

			parameter.put("icon_url", filename);
			db.updatePictogram(parameter);
		}
	}
	
	
	@Transactional
	public int deletePictogram(long pid) throws Exception {
		return db.deletePictogram(pid);
	}

	

	@Transactional
	public List<Map<String, Object>> getServiceConfig() throws Exception {
		return db.getServiceConfig();
	}

	@Transactional
	public List<Map<String, Object>> getServiceConfigNonSecret() throws Exception {
		return db.getServiceConfigNonSecret();
	}

    @Transactional
    public int insertServiceConfig(MultipartHttpServletRequest mreq) throws Exception {
        String name = new String(mreq.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
        String value = new String(mreq.getParameter("the_value").getBytes("iso-8859-1"), "UTF-8");
        String desc = new String(mreq.getParameter("description").getBytes("iso-8859-1"), "UTF-8");
        return db.insertServiceConfig(name, value, desc);
    }

	@Transactional
	public int updateServiceConfig(MultipartHttpServletRequest mreq) throws Exception {
		long id = Long.parseLong(mreq.getParameter("id"));
		String value = new String(mreq.getParameter("the_value").getBytes("iso-8859-1"), "UTF-8");
		String desc = new String(mreq.getParameter("description").getBytes("iso-8859-1"), "UTF-8");
		return db.updateServiceConfig(id, value, desc);
	}

    @Transactional
    public void updateFont(MultipartHttpServletRequest mreq) throws Exception {
	    String name = "setFont";
        String value = new String(mreq.getParameter("setFont").getBytes("iso-8859-1"), "UTF-8");
        String desc = "폰트를 설정합니다";

        if(StringUtils.isNotEmpty(mreq.getParameter("id"))) {
            long id = Long.parseLong(mreq.getParameter("id"));
            if(value.equals("false")) {
                db.deleteServiceConfig(id);
            }else{
                db.updateServiceConfig(id, value, desc);
            }
        }else{
            db.insertServiceConfig(name, value, desc);
        }
    }

	@Transactional
	public void updateMascot(MultipartHttpServletRequest mreq) throws Exception {
		String filename = asset.storeMascotAssets(mreq.getFile("mascot"));
		String ext = filename.substring(filename.lastIndexOf(".")+1);
		List<Map<String, Object>> systemList = db.getServiceConfig();
		List<Map<String, Object>> updateList = systemList.stream().filter(sys -> sys.get("name").toString().equals("loadingSetSystem")).collect(Collectors.toList());
		db.updateServiceConfig(Long.parseLong(updateList.get(0).get("id").toString()), filename, null);

        updateList = systemList.stream().filter(sys -> sys.get("name").toString().equals("loadingImgLottie")).collect(Collectors.toList());
        long id = Long.parseLong(updateList.get(0).get("id").toString());
		if(ext.toLowerCase().equals("json")) {
			db.updateServiceConfig(id, "true", null);
		}else{
            db.updateServiceConfig(id, "false", null);
        }
	}


	@Transactional
	public int getUserCount() throws Exception {
		return db.getUserCount();
	}
	
	@Transactional
	public List<Map<String, Object>> getUsers(int page, int count) throws Exception {
		return db.getUsers(page, count);
	}

	
	@Transactional
	public void updateUser(MultipartHttpServletRequest mreq) throws Exception {

		// 일단 관리자로는 변경 못함...
		int type = Integer.parseInt(mreq.getParameter("type"));

		Map<String, Object> parameter = new HashMap<String, Object>();
		boolean isInsert = Integer.parseInt(mreq.getParameter("uid")) == 0;

		if (isInsert) {
			// 최고 관리자는 추가 못함.
			if (type == SUPER_ADMIN) 
				throw new NoblappException(ErrorCode.NOT_ALLOWED);
						
			
		} else {
			
			// 바꾸려는 유저가 최고 관리자인데, 타입을 최고 관리자가 아닌걸로 바꾸려고 해... 허용 못함.
			Map<String, Object> admin = db.getAdminUser();
			int adminUid = (int)admin.get("uid");
			long targetUid = Long.parseLong(mreq.getParameter("uid"));
		
			if ((adminUid == targetUid) != (type == SUPER_ADMIN))
				throw new NoblappException(ErrorCode.NOT_ALLOWED);
		}
		
		parameter.put("id", mreq.getParameter("id"));
		if(mreq.getParameter("password") != null && !mreq.getParameter("password").equals("")) {
			String hashedPassword = Password.getSaltedHash(mreq.getParameter("password"));
			parameter.put("password", hashedPassword);	
		}		
		parameter.put("type", type);
		parameter.put("name", new String(mreq.getParameter("name").getBytes("iso-8859-1"), "UTF-8"));
		String birth = mreq.getParameter("birth");
		if (!StringUtils.isEmpty(birth))
			parameter.put("birth", new SimpleDateFormat("yyyy-MM-dd").parse(mreq.getParameter("birth")));
		parameter.put("mobile", mreq.getParameter("mobile"));
		parameter.put("gender", Integer.parseInt(mreq.getParameter("gender")));
		
		long uid = 0;
		if (isInsert) {

			db.insertUser(parameter);
//			uid = (long) parameter.get("uid");
			uid = ((BigInteger) parameter.get("uid")).longValue();
			logger.info("inserted id? " + uid);
		} else {
			uid = Long.parseLong(mreq.getParameter("uid"));
			parameter.put("uid", uid);
			db.updateUser(parameter);

			logger.info("updated id? " + uid);
		}
	}
	
	@Transactional
	public void updateUserStatus(Map<String, Object> parameter) throws Exception {
		if (parameter.get("id") != null)
			parameter.put("uid", parameter.get("id"));

		db.updateUser(parameter);
		
		int uid = (int) parameter.get("uid");
		logger.info("status changed for id? " + uid);
	}	

	@Transactional
	public int deleteUser(long uid) throws Exception {
		return db.deleteUser(uid);
	}
	
	
	
	
	
	
	// Trekking
	@Transactional
	public int getTrekCourseCount() throws Exception {
		return db.getTrekCourseCount(0);
	}
	
	@Transactional
	public List<Map<String, Object>> getTrekCourses(int page, int count) throws Exception {
		return db.getTrekCourses(page, count, 0);
	}

	
	@Transactional
	public void updateTrekCourse(MultipartHttpServletRequest mreq) throws Exception {
		// TODO 정식 cms에서는 추가/변경 하는건, 코스와 그룹이다.

		Map<String, Object> parameter = new HashMap<>();
		long tc_id = Long.parseLong(mreq.getParameter("tc_id"));
		long cid = Long.parseLong(mreq.getParameter("cid"));

		parameter.put("course_name", new String(mreq.getParameter("course_name").getBytes("iso-8859-1"), "UTF-8"));
		parameter.put("color", mreq.getParameter("color"));
		parameter.put("tc_type", mreq.getParameter("tc_type"));
		parameter.put("chk_dist", mreq.getParameter("chk_dist"));
		parameter.put("cid", cid);
		
		if (tc_id == 0) {
			db.insertTrekCourse(parameter);
//			tc_id = (long)parameter.get("tc_id");
			tc_id = ((BigInteger) parameter.get("tc_id")).longValue();
		} else {
			parameter.put("tc_id", tc_id);
			db.updateTrekCourse(parameter);
		}
		
		ObjectMapper mapper = new ObjectMapper();

		// 먼저 지워지는 것들이 있나 보자..
		String toBeDeleted = mreq.getParameter("to_delete");
		List<Map<String, Object>> groupIndecies = db.getGroupIndexOfSectors(tc_id);
		logger.info("toBeDeleted? " + toBeDeleted);
		if (!toBeDeleted.isEmpty()) {
			logger.info("something to delete");

			// 일단 지워야 하는 그룹의 섹터들은 지워버리자.
			int[] deleteArray = mapper.readValue(toBeDeleted, int[].class);
			db.deleteSectorsInGroups(tc_id, deleteArray);

			for (Map<String, Object> index : groupIndecies) {
				long old_g_idx = (long)index.get("g_idx");
				int negative = 0;
				for (int i : deleteArray) {
					if (old_g_idx < i)
						break;
					negative++;
				}
				if (negative > 0)
					db.updateSectorGroupIndex(tc_id, old_g_idx, old_g_idx - negative);
			}
		}
		
		// 지우면 알아서 인덱스가 다시 재정리가 되는데,
		// 섹터들은 인덱스가 꼬인다...
		String names = new String(mreq.getParameter("groups").getBytes("iso-8859-1"), "UTF-8");
		String[] nameArray = mapper.readValue(names, String[].class);
		String pids = mreq.getParameter("pids");
		Integer[] pidArray = null;
		if (!pids.isEmpty()) {
			pidArray = mapper.readValue(pids, Integer[].class);
			logger.info("pidArray? " + pidArray.length);
//			if (pidArray.length > 0) {
//				db.deleteSectors(tc_id, g_idx);
//			}
		}
		Map<String, Object> groupMap = new HashMap<String, Object>();
		for (int i = 0; i < nameArray.length; i++) {
			groupMap.clear();
			groupMap.put("tc_id", tc_id);
			groupMap.put("g_idx", i);
			groupMap.put("name", nameArray[i]);
			if (pidArray != null && i < pidArray.length) {
				groupMap.put("pid", pidArray[i]);
				Map<String, Object> targetPlace = db.getPlacesDetail("ko", pidArray[i]);
				
				// 해당 장소가 있으니까 그 장소의 gps 좌표를 sector로 넣자.
				if (targetPlace != null) {
					groupMap.put("s_idx", 0);
					groupMap.put("lat", targetPlace.get("lat"));
					groupMap.put("lng", targetPlace.get("lng"));
					groupMap.put("tcs_type", 2);		// 체크 포인트 임...
					db.insertSector(groupMap);
				}
			}
			db.insertTrekGroup(groupMap);
		}

		// 그 뒤에 것들을 지우자.. (있으면...)
		db.removeTrekGroup(tc_id, nameArray.length);
	
	}
	
	@Transactional
	public Map<String, Object> updateTrekSectors(Map<String, Object> parameter) throws Exception {
		// 리스트로 받아오자...
		int tc_id = (int)parameter.get("tc_id");
		int g_idx = (int)parameter.get("g_idx");
		
		// 먼저 해당 그룹을 지우고 나서 하자... 그래야 쓸데없는 데이터가 남아있지 않음...
		db.deleteSectors(tc_id, g_idx);

		List<Map<String, Object>> sectors = (List<Map<String, Object>>) parameter.get("sectors");
		int s_idx = 0;
		for (Map<String, Object> sector : sectors) {
			sector.put("tc_id", tc_id);
			sector.put("g_idx", g_idx);
			sector.put("s_idx", s_idx);
			s_idx++;
		}
		
		db.insertSectors(sectors);
		return null;
	}
	
	@Transactional
	public void updateTrekCourseStatus(Map<String, Object> parameter) throws Exception {
		if (parameter.get("id") != null)
			parameter.put("tc_id", parameter.get("id"));

		db.updateTrekCourse(parameter);
	}	

	@Transactional
	public int deleteTrekCourse(long tc_id) throws Exception {
		// 관련된 모든 group과 sector들을 삭제해야 한다..
		return db.deleteTrekCourse(tc_id);
	}
	
	// 섹터 목록만 가져오는거... 근데...
	// TODO tc_id에 속한 모든 녀석들을 가져오자.. g_idx는 빼고... 그래서 한번에 다 보여줄 수 있게.. 이어져야지...
	@Transactional
	public List<Map<String, Object>> getTrekSectors(int tc_id, int g_idx) throws Exception {
		return db.getSectors(tc_id, g_idx);
	}
	
	@Transactional
	public List<Map<String, Object>> getTrekOtherSectors(int tc_id, int g_idx) throws Exception {
		return db.getSectorsExcept(tc_id, g_idx);
	}
	
	@Transactional
	public Map<String, Object> getTrekCourseInfo(int tc_id) throws Exception {
		return db.getTrekCourse(tc_id);
	}
	
	@Transactional
	public int getImapCount() throws Exception {
		return db.getImapCount();
	}
	
	@Transactional
	public Map<String, Object> getImap(long mid) throws Exception {
		return db.getImap(mid);
	}
	
	@Transactional
	public List<Map<String, Object>> getImaps() throws Exception {
		return db.getImaps("");
	}
	
	@Transactional
	public int insertImap(Map<String, Object> map) throws Exception {
		return db.insertImap(map);
	}
	
	@Transactional
	public void updateImap(MultipartHttpServletRequest mreq) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory typeFactory = mapper.getTypeFactory();

		// 추가에 필요한 데이터 구성...
		Map<String, Object> parameter = new HashMap<>();
		boolean isInsert = Integer.parseInt(mreq.getParameter("mid")) == 0;

//		parameter.put("ko", new String(mreq.getParameter("name_ko").getBytes("iso-8859-1"), "UTF-8"));
//		if(!mreq.getParameter("zoom").equals("") && mreq.getParameter("zoom") != null)
		if(StringUtils.isNotBlank(mreq.getParameter("zoom")))
			parameter.put("zoom", Integer.parseInt(mreq.getParameter("zoom")));

//		if(!mreq.getParameter("lat").equals("") && mreq.getParameter("lat") != null)
		if(StringUtils.isNotBlank(mreq.getParameter("lat")))
			parameter.put("lat", Double.parseDouble(mreq.getParameter("lat")));

//		if(!mreq.getParameter("lng").equals("") && mreq.getParameter("lng") != null)
		if(StringUtils.isNotBlank(mreq.getParameter("lng")))
			parameter.put("lng", Double.parseDouble(mreq.getParameter("lng")));

//		if(!mreq.getParameter("width").equals("") && mreq.getParameter("width") != null)
		if(StringUtils.isNotBlank(mreq.getParameter("width")))
			parameter.put("width", Integer.parseInt(mreq.getParameter("width")));
		else
			parameter.put("width", null);

//		if(!mreq.getParameter("height").equals("") && mreq.getParameter("height") != null)
		if(StringUtils.isNotBlank(mreq.getParameter("height")))
			parameter.put("height", Integer.parseInt(mreq.getParameter("height")));
		else
			parameter.put("height", null);

//		if(!mreq.getParameter("header").equals("") && mreq.getParameter("header") != null)
		if(StringUtils.isNotBlank(mreq.getParameter("header")))
			parameter.put("header", mreq.getParameter("header"));

		String file_delete = mreq.getParameter("file_delete");

		parameter.put("tile_info", mreq.getParameter("tile_info"));

		long mid = 0;

		String[] languages = serviceConfig.getSupportedLanguage();

		// 장소 정보를 추가할지 업데이트 할지...
		if (isInsert) {
			System.out.println(parameter.toString());
			// 여기는 추가 후에...
			db.insertImap(parameter);

			mid = ((BigInteger) parameter.get("mid")).longValue();
			logger.info("inserted id? " + mid);

			Map<String, Object> localStringMap = new HashMap<>();
			localStringMap.put("mid", mid);
			for (String lang : languages) {
				String val = new String(mreq.getParameter("name_" + lang).getBytes("iso-8859-1"), "UTF-8");
				localStringMap.put(lang, val);
			}
			db.insertImapLocalizedStrings(localStringMap);
			
		} else {			
			// 업데이트시에는 이름도 같이 처리함...
			mid = Long.parseLong(mreq.getParameter("mid"));
			parameter.put("mid", mid);
			for (String lang : languages) {
				String val = new String(mreq.getParameter("name_" + lang).getBytes("iso-8859-1"), "UTF-8");
				parameter.put(lang, val);
			}
			db.updateImap(parameter);			
			logger.info("updated id? " + mid);
		}

		// pid가 있어야 하는 이유로... 먼저 추가 후... 아니면 업데이트 후에 하자...
		String mapRes = null;
		Iterator<String> iter = mreq.getFileNames();
		while (iter.hasNext()) {
			String fn = iter.next();
			MultipartFile file = mreq.getFile(fn);

			if (file == null || file.getSize() <= 0)
				continue;

			String filename = asset.storeSpotsAssets(mid, file);

			// 합쳐서 보여줘야 할 듯...
			String res_name = file.getName();
			if (res_name.equals("map_res_url")) {
				mapRes = filename;
			}
		}

		// 이미지 (또는 비디오) 파일 등이 업데이트 됬는지 확인하고...
		boolean update = false;

		//파일이 지워지는 로직이 있어야 해서 아래 조건 주석처리 함.
//		if (mapRes != null) {
		// mapRes는 파일이 변경(추가, 수정)됐을 땐 되는데 삭제 했을 땐 null 임.
		// 그래서 file_change란 parameter를 받아서 처리함.
		if(mapRes != null || file_delete.equals("delete")) {
			parameter.clear();
			parameter.put("mid", mid);
			parameter.put("map_res_url", mapRes);
			update = true;
		}

		// 업데이트 된거면 데이터 업데이트 해.
		if (update)
			db.updateImap(parameter);	
	}

	@Transactional
	public void updateImapStatus(Map<String, Object> parameter) throws Exception {
		if (parameter.get("id") != null)
			parameter.put("mid", parameter.get("id"));

		db.updateImap(parameter);
		
		int mid = (int) parameter.get("mid");
		logger.info("status changed for id? " + mid);
	}
	
	@Transactional
	public int deleteImap(long pid) throws Exception {
		return db.deleteImap(pid);
	}
	

	@Transactional
	public int getSpotCount() throws Exception {
		return db.getSpotCount();
	}
	
	@Transactional
	public Map<String, Object> getSpot(long pid) throws Exception {
		return db.getSpot("ko", pid);
	}
	
	@Transactional
	public List<Map<String, Object>> getSpots(int page, int count, long mid) throws Exception {
		return db.getSpots("", page, count, mid);
	}
	
	@Transactional
	public void updateSpot(MultipartHttpServletRequest mreq) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory typeFactory = mapper.getTypeFactory();

		// 추가에 필요한 데이터 구성...
		Map<String, Object> parameter = new HashMap<>();
		boolean isInsert = Integer.parseInt(mreq.getParameter("pid")) == 0;

		parameter.put("lat", Double.parseDouble(mreq.getParameter("lat")));
		parameter.put("lng", Double.parseDouble(mreq.getParameter("lng")));
		parameter.put("mid", Integer.parseInt(mreq.getParameter("mid")));
		
//		if(mreq.getParameter("text_direction") != null && !mreq.getParameter("text_direction").equals("") )
		if(StringUtils.isNotBlank(mreq.getParameter("text_direction")))
			parameter.put("text_direction", mreq.getParameter("text_direction"));
		
//		if(mreq.getParameter("display_zoom") != null && !mreq.getParameter("display_zoom").equals("") )
		if(StringUtils.isNotBlank(mreq.getParameter("display_zoom")))
			parameter.put("display_zoom", Integer.parseInt(mreq.getParameter("display_zoom")));

//		if(mreq.getParameter("display_zoom_max") != null && !mreq.getParameter("display_zoom_max").equals("") )
		if(StringUtils.isNotBlank(mreq.getParameter("display_zoom_max")))
			parameter.put("display_zoom_max", Integer.parseInt(mreq.getParameter("display_zoom_max")));

//		if(mreq.getParameter("name_display") != null && !mreq.getParameter("name_display").equals(""))
		if(StringUtils.isNotBlank(mreq.getParameter("name_display")))
			parameter.put("name_display", mreq.getParameter("name_display"));
		
//		if(mreq.getParameter("width") != null && !mreq.getParameter("width").equals("") )
		if(StringUtils.isNotBlank(mreq.getParameter("width")))
			parameter.put("width", Integer.parseInt(mreq.getParameter("width")));

		
//		if(mreq.getParameter("height") != null && !mreq.getParameter("height").equals("") )
		if(StringUtils.isNotBlank(mreq.getParameter("height")))
			parameter.put("height", Integer.parseInt(mreq.getParameter("height")));

		if(StringUtils.isNotBlank(mreq.getParameter("type")))
			parameter.put("type", mreq.getParameter("type"));

		if(StringUtils.isNotBlank(mreq.getParameter("text_rotate")))
			parameter.put("text_rotate", mreq.getParameter("text_rotate"));

		if(StringUtils.isNotBlank(mreq.getParameter("font_size")))
			parameter.put("font_size", mreq.getParameter("font_size"));
		
		
		long map_type = Long.parseLong(mreq.getParameter("map_type"));
		parameter.put("map_type", map_type);
		System.out.println(parameter.toString());
		
		// map_type에 따른 다른 처리 방식...
		if (map_type == 0) {
			parameter.put("map_res_url", "");
			
		} else if (map_type == 1) {
			parameter.put("map_res_url", mreq.getParameter("pictogram_id"));
			// pictogram
		} else {
			// 아래에서 처리한다... 아우.. 지저분해.. ㅠ.ㅠ
		}
		
		long pid = 0;

		String[] languages = serviceConfig.getSupportedLanguage();

		// 장소 정보를 추가할지 업데이트 할지...
		if (isInsert) {
			System.out.println(parameter.toString());
			// 여기는 추가 후에...
			db.insertSpot(parameter);
//			pid = (long) parameter.get("pid");
			pid = ((BigInteger) parameter.get("pid")).longValue();
			logger.info("inserted id? " + pid);

			// 언어 스트링 추가.
			Map<String, Object> localStringMap = new HashMap<>();
			localStringMap.put("pid", pid);
			for (String lang : languages) {
				String val = new String(mreq.getParameter("name_" + lang).getBytes("iso-8859-1"), "UTF-8");
				localStringMap.put(lang, val);
			}
			db.insertSpotLocalizedStrings(localStringMap);
			
		} else {
			
			// 업데이트시에는 이름도 같이 처리함...
			parameter.put("pid", Long.parseLong(mreq.getParameter("pid")));
			for (String lang : languages) {
				String val = new String(mreq.getParameter("name_" + lang).getBytes("iso-8859-1"), "UTF-8");
				parameter.put(lang, val);
			}
			db.updateSpot(parameter);

			pid = Long.parseLong(mreq.getParameter("pid"));
			logger.info("updated id? " + pid);
		}
		
		
		
		// pid가 있어야 하는 이유로... 먼저 추가 후... 아니면 업데이트 후에 하자...
		String mapRes = null;
		Iterator<String> iter = mreq.getFileNames();
		while (iter.hasNext()) {
			String fn = iter.next();
			MultipartFile file = mreq.getFile(fn);

			if (file == null || file.getSize() <= 0)
				continue;

			String filename = asset.storeSpotsAssets(pid, file);

			// 합쳐서 보여줘야 할 듯...
			String res_name = file.getName();
			if (res_name.equals("map_res_url")) {
				mapRes = filename;
			}
		}

		// 이미지 (또는 비디오) 파일 등이 업데이트 됬는지 확인하고...
		boolean update = false;
		if (mapRes != null) {
			parameter.clear();
			parameter.put("pid", pid);
			parameter.put("map_res_url", mapRes);
			update = true;
		}
		
		// 업데이트 된거면 데이터 업데이트 해.
		if (update)
			db.updateSpot(parameter);	
	}
	
	@Transactional
	public void updateSpotStatus(Map<String, Object> parameter) throws Exception {
		if (parameter.get("id") != null)
			parameter.put("pid", parameter.get("id"));

		db.updateSpot(parameter);
		
		int uid = (int) parameter.get("pid");
		logger.info("status changed for id? " + uid);
	}

	@Transactional
	public Map<String, Object> updateSpotLocation(Map<String, Object> parameter) throws Exception {
		logger.info("updateSpotLocation");
		Set<String> keys = parameter.keySet();
		for (String key: keys) 
			logger.info(key + " : " + parameter.get(key));

		db.updateSpot(parameter);
		return null;
	}	

	@Transactional
	public int deleteSpot(long pid) throws Exception {
		return db.deleteSpot(pid);
	}

	@Transactional
	public int getEventCount() throws Exception {
		return db.getEventCount();
	}

	@Transactional
	public List<Map<String, Object>> getEvents(String lang, int page, int count) throws Exception {
		return db.getEvents(lang, page, count);
	}

	private String[] event_tables = {"events_nm", "events_tx", "events_cf"};
	private String[] event_prefixs = {"name_", "text_", "cf_"};
	@Transactional
	public void updateEvent(MultipartHttpServletRequest mreq) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		TypeFactory typeFactory = mapper.getTypeFactory();

		// 추가에 필요한 데이터 구성...
		Map<String, Object> parameter = new HashMap<>();

		boolean isInsert = StringUtils.isBlank(mreq.getParameter("eid"));

		if(StringUtils.isNotBlank(mreq.getParameter("pid"))) {
			parameter.put("pid", Long.parseLong(mreq.getParameter("pid")));
		}

		long eid = 0;

		// 장소 정보를 추가할지 업데이트 할지...
		if (isInsert) {
			db.insertEvent(parameter);
			eid = ((BigInteger) parameter.get("eid")).longValue();
			logger.info("inserted id? " + eid);
		} else {
			eid = Long.parseLong(mreq.getParameter("eid"));
			if(parameter.size() > 0) {
				parameter.put("eid", eid);
				db.updateEvent(parameter);
				logger.info("updated id? " + eid);
			}
		}

		String image = null;
		Iterator<String> iter = mreq.getFileNames();
		while (iter.hasNext()) {
			String fn = iter.next();
			MultipartFile file = mreq.getFile(fn);

			if (file == null || file.getSize() <= 0)
				continue;

			image = asset.storeEventsAssets(eid, file);
		}

		if(StringUtils.isNotEmpty(image)) {
			parameter.put("image", image);
			db.updateEvent(parameter);
		}

		String[] languages = serviceConfig.getSupportedLanguage();

		Map<String, Object> localStringMap = new HashMap<>();

		for (int i=0, n=event_tables.length; i < n; i++) {
			localStringMap.clear();
			localStringMap.put("eid", eid);
			localStringMap.put("table", event_tables[i]);

			for (String lang : languages) {
				String val = mreq.getParameter(event_prefixs[i] + lang);

				if(StringUtils.isNotBlank(val)) {
					localStringMap.put(lang, new String(val.getBytes("iso-8859-1"), "UTF-8"));
				}
			}

			if (isInsert){
				db.insertEventSub(localStringMap);
			}else{
				if(localStringMap.size() > 2) {
					db.updateEventSub(localStringMap);
				}
			}
		}
	}

	@Transactional
	public void updateEventStatus(Map<String, Object> parameter) throws Exception {
		if (parameter.get("id") != null)
			parameter.put("eid", parameter.get("id"));
		db.updateEvent(parameter);

		int pid = (int) parameter.get("id");
		logger.info("status changed for id? " + pid);
	}

	@Transactional
	public int deleteEvent(long eid) throws Exception {
		return db.deleteEvent(eid);
	}

	@Transactional
	public int getTouchLogCount() throws Exception {
		return db.getTouchLogCount();
	}

	@Transactional
	public List<Map<String, Object>> getTouchLogList(int from, int count) throws Exception {
		return db.getTouchLogList(from, count);
	}
	
}
