package com.noblapp.support;

import com.noblapp.dao.DbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * 이 안에 변수들은 'service_config'의 table 값들과 동일해야 함...
 * 아니면 최소 사용할 것들만 로딩 하던가...
 * 
 * @author juniverse
 */
@Component
public class ServiceConfig {
	@Autowired protected DbMapper db; //db

	private int trekkingPoint;
	private double trekMinDistance;
	private String[] supportedLanguage;
	private String serviceRegionName;
	private double[] baseLatLng;
	private String googleClientId;
	private String googleClientSecret;
	private String googleRedirectUri;
	private String naverClientId;
	private String naverClientSecret;
	private String naverRedirectUri;
	/**
	 * @PostConstruct annotation을 통해 처음 기동시 호출된다. service_config 테이블에 있는 데이터를 읽어와서 미리 메모리에 저장해둔다.
	 */
	@PostConstruct
	public void loadConfig() {
		try {
			List<Map<String, Object>> configList = db.getServiceConfig();
			for (Map<String, Object> config : configList) {
				String name = (String) config.get("name");
				String value = (String) config.get("the_value");
				System.out.printf("config name : %s%n", config.get("name"));

				if (name.equals("trekking_point")) {
					trekkingPoint = Integer.parseInt(value);
					System.out.printf("trekkingPoint : %d%n", trekkingPoint);
				} else if (name.equals("trek_min_distance")) {
					trekMinDistance = Double.parseDouble(value);
					System.out.printf("trekMinDistance : %f%n", trekMinDistance);
				} else if (name.equals("supported_language")) {
					supportedLanguage = value.split(",");
					System.out.printf("supportedLanguage : %s%n", supportedLanguage.toString());
				} else if (name.equals("service_region_name")) {
					serviceRegionName = value;
					System.out.printf("serviceRegionName : %s%n", serviceRegionName);
				} else if (name.equals("base_lat")) {
					if (baseLatLng == null)
						baseLatLng = new double[2];
					baseLatLng[0] = Double.parseDouble(value);
					System.out.printf("trekMinDistance : %f%n", baseLatLng[0]);
				} else if (name.equals("base_lng")) {
					if (baseLatLng == null)
						baseLatLng = new double[2];
					baseLatLng[1] = Double.parseDouble(value);
					System.out.printf("trekMinDistance : %f%n", baseLatLng[1]);
				} else if (name.equals("APIKEYS.GOOGLE.clientId")) {
					googleClientId = value;
				} else if (name.equals("APIKEYS.GOOGLE.clientSecret")) {
					googleClientSecret = value;
				} else if (name.equals("APIKEYS.GOOGLE.redirectUri")) {
					googleRedirectUri = value;
				} else if (name.equals("APIKEYS.NAVER.clientId")) {
					naverClientId = value;
				} else if (name.equals("APIKEYS.NAVER.clientSecret")) {
					naverClientSecret = value;
				} else if (name.equals("APIKEYS.NAVER.redirectUri")) {
					naverRedirectUri = value;
				}
				
				// db table에 더 추가하면 여기에다가 추가해야 함...
			}
		} catch (Exception e) {
		}
	}


	// GET 함수들...
	public int getTrekkingPoint() {
		return trekkingPoint;
	}

	public double getTrekMinDistance() {
		return trekMinDistance;
	}

	public String[] getSupportedLanguage() {
		return supportedLanguage;
	}

	public String getServiceRegionName() {
		return serviceRegionName;
	}

	public double[] getBaseLatLng() {
		return baseLatLng;
	}

	public String getGoogleClientId() {
		return googleClientId;
	}

	public String getGoogleClientSecret() {
		return googleClientSecret;
	}

	public String getGoogleRedirectUri() {
		return googleRedirectUri;
	}

	public String getNaverClientId() { return naverClientId; }

	public String getNaverClientSecret() { return naverClientSecret; }

	public String getNaverRedirectUri() { return naverRedirectUri; }
}
