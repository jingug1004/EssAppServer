package com.noblapp.support;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * 스프링 어플리케이션 설정
 * 
 * @author juniverse
 */
@Configuration
@EnableWebMvc
@EnableScheduling
@EnableAsync
@ComponentScan(basePackages = {"com.noblapp"})
public class MvcApplicationConfig extends WebMvcConfigurerAdapter {
	private String assetsPath;
	/*
	@Bean
	public ReloadableResourceBundleMessageSource messageSource(){

		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasenames(new String[]{"classpath:/messages/message"});
		messageSource.setDefaultEncoding("UTF-8");
		
		return messageSource;
	}
	*/
	
	private String devStage = null;
	/**
	 * 현재 개발 스테이지를 반환한다. 환경 변수에서 읽어옴.
	 */
	public String getDevStage() {
		if (devStage != null)
			return devStage;
		
		devStage = System.getenv("SERVER_INDICATOR");
		
		System.out.println("[juniverse] getenv serverIndicator? " + devStage);
		// 없으면 시스템의 환경변수에서 값을 읽어온다.
		if(devStage == null)
			devStage = System.getProperty("SERVER_INDICATOR");

		System.out.println("[juniverse] getProperty serverIndicator? " + devStage);
		// 그래도 없으면 release 환경이라고 판단한다.
		if (devStage == null)
			devStage = "release";
		
		return devStage;
	}

	private String getProperties(String name) throws Exception {
		String propFile = "/conf/" + getDevStage() + ".properties";

		// 프로퍼티 객체 생성
		Properties props = new Properties();

		// 프로퍼티 파일 스트림에 담기
		FileInputStream fis = new FileInputStream(new ClassPathResource(propFile).getFile());

		// 프로퍼티 파일 로딩
		props.load(new java.io.BufferedInputStream(fis));

		// 항목 읽기
		return props.getProperty(name) ;
	}

	/**
	 */
	@Override
    public void addCorsMappings(CorsRegistry registry) {
		List<String> origins = new ArrayList<>();

		try {
			String allowedOrigin = getProperties("domain.allowed.origin").trim();
            origins.add(allowedOrigin);
            origins.add("https://test.noblapp.com");
			origins.add("http://localhost:81");
		} catch (Exception e) {
			e.printStackTrace();
			origins.add("*");
		}

		List<String> pathList = Arrays.asList("/auth/**", "/etc/**", "/leisure/**","/shop/**","/user/**","/map/**", "/assetImg/**", "/client/**");
        for(String path : pathList) {
            registry.addMapping(path)
                    .allowedOrigins(origins.toArray(new String[origins.size()]))
					.allowedMethods("GET",  "POST", "HEAD", "PUT", "DELETE", "OPTIONS")
					.allowCredentials(true)
                    .allowedHeaders("*");
        }
    }

	/**
	 * 개발 스테이지에 따른 다른 프로퍼티 값 처리용.
	 * 프로퍼티를 추가하고 싶으면, 해당 .properties 파일에 값을 추가하고,
	 * 사용하는 방법은 
	 * @Value(value = "${jdbc.db.url}") private String dbUrl;
	 * 이런 식으로...
	 * DBConfig.java 파일 참조.
	 */
	@Bean
	public PropertyPlaceholderConfigurer propertyConfigurer(){
		// 시스템의 환경 변수에서 값을 읽어와본다.
		String serverIndicator = getDevStage();
		try {
			assetsPath = getProperties("assets.path");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String confProp = "/conf/" + serverIndicator + ".properties";
//		String repoProp = "/conf/" + serverIndicator + "-repository.properties";
		
		PropertyPlaceholderConfigurer props = new PropertyPlaceholderConfigurer();
//		props.setLocations(new ClassPathResource(confProp), new ClassPathResource(repoProp));
		props.setLocation(new ClassPathResource(confProp));
		System.out.println("PropertyPlaceholderConfigurer done~~~ name of repoProp is [" + confProp + "]");
		return props;
	}
	
	/**
	 * 인터셉터.
	 * api 콜이 들어가기 전에 인터셉터에 한번 wrapping 된다.
	 * 거의 그냥 로그용...
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		// todo interceptor~~~???
		if (!getDevStage().equals("release"))
			registry.addInterceptor(new ElapsedTimeLogInterceptor());

		registry.addInterceptor(visitorLogInterceptor());
	}
	
	@Bean
	public VisitorLogInterceptor visitorLogInterceptor() {
		return new VisitorLogInterceptor();
	}

	/**
	 * 직접 파일을 웹에 서비스하기 위한 용도.
	 * 아래 client 폴더를 오픈해주고 클라이언트 코드가 그 안에 있어야 index.html 파일을 로딩할 수 있음.
	 */

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		// todo 나중에 web에 resource가 필요하면 그때...
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
		registry.addResourceHandler("/client/**").addResourceLocations("/client/");
		registry.addResourceHandler("/**").addResourceLocations("/");
		registry.addResourceHandler("/assetImg/**").addResourceLocations("file:"+assetsPath); //외부이미지 폴더
	}

	@Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(500000000);
        return multipartResolver;
    }


	/*@Bean
	public CacheManager cacheManager(){
		return new ConcurrentMapCacheManager("board_group");
	}*/
	
	/*@Bean(name = "facebook")
	public FacebookConnectionFactory facebookFactory(){
		String appId = "";
		String appSecret = "";
		return new FacebookConnectionFactory(appId, appSecret);
	}
	
	@Bean(name = "facebookOauth2")
	public OAuth2Parameters facebookOauth2(){
		String scope = "";
		String redirectUri = "";
		OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
		oAuth2Parameters.setScope(scope);
		oAuth2Parameters.setRedirectUri(redirectUri);
		return oAuth2Parameters;
	}
	
	@Bean(name = "google")
	public GoogleConnectionFactory googleFactory(){
		String clientId = "";
		String clientSecret = "";
		return new GoogleConnectionFactory(clientId, clientSecret);
	}
	
	@Bean(name = "googleOauth2")
	public OAuth2Parameters googleOauth2(){
		String scope = "";
		String redirectUri = "";
	
		OAuth2Parameters oAuth2Parameters = new OAuth2Parameters();
		oAuth2Parameters.setScope(scope);
		oAuth2Parameters.setRedirectUri(redirectUri);
		return oAuth2Parameters;
	}*/
}	
