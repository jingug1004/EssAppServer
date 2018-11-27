package com.noblapp.support;

import com.noblapp.dao.DbMapper;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * mybatis용 db 처리하는거...
 * 정확한 메커니즘은 모르지만, db와 연결해줌.
 * 
 * @author juniverse
 */
@Configuration
@EnableTransactionManagement
public class DBConfig {
	
//	@Autowired private Aes128Crypt crypt;
//	private static final String DB_KEY = "jhkim,./!@#$%^_";
	
	@Value(value = "${jdbc.driverClassName}") private String driverClassName;
	
	//gamedb
	@Value(value = "${jdbc.db.url}") private String dbUrl;
	@Value(value = "${jdbc.db.user}") private String dbUsername;
	@Value(value = "${jdbc.db.password}") private String dbPassword;
//	@Value(value = "${jdbc.db.initialSize}") private int dbInitialSize;
//	@Value(value = "${jdbc.db.minIdle}") private int dbMinIdle;
//	@Value(value = "${jdbc.db.maxActive}") private int dbMaxActive;
//	@Value(value = "${jdbc.db.maxIdle}") private int dbMaxIdle;
//	@Value(value = "${jdbc.db.maxWait}") private long dbMaxWait;
	
	@Bean
	public BasicDataSource dbDataSource(){
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(driverClassName);
		basicDataSource.setUrl(dbUrl);
		basicDataSource.setUsername(dbUsername);
		
		// 나중에 패스워드 암호화 해야 할까나???
//		String password = null;
//		try{
//			password = crypt.decrypt(dbPassword, DB_KEY);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		basicDataSource.setPassword(password);
		basicDataSource.setPassword(dbPassword);
//		basicDataSource.setInitialSize(dbInitialSize);
//		basicDataSource.setMinIdle(dbMinIdle);
//		basicDataSource.setMaxActive(dbMaxActive);
//		basicDataSource.setMaxIdle(dbMaxIdle);
//		basicDataSource.setMaxWait(dbMaxWait);
		basicDataSource.setTestOnBorrow(true);
		basicDataSource.setValidationQuery("select 1");
		
		return basicDataSource;
	}
	
	@Bean
	public DataSourceTransactionManager transactionManager(){
		return new DataSourceTransactionManager(this.dbDataSource());
	}
	
	@Bean
	public SqlSessionFactory dbSqlSessionFactory(){
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(this.dbDataSource());
		
		Resource configLocation = new ClassPathResource("mybatis-config.xml");
		sqlSessionFactoryBean.setConfigLocation(configLocation);
		
		SqlSessionFactory sqlSessionFactory = null;
		try {
			sqlSessionFactory = sqlSessionFactoryBean.getObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlSessionFactory;
	}	
	
	@Bean(name = "db") 
	public MapperFactoryBean<DbMapper> dbMapper(){
		MapperFactoryBean<DbMapper> mapperFactoryBean = new MapperFactoryBean<>();
		// [juniverse] 다른 query를 쓰고 싶다면.. 여기서 바꿔줄 수도 있겠는데... 그럼 반환하는건? 그냥 코드 분리는 못하겠군...
		mapperFactoryBean.setMapperInterface(DbMapper.class);
		mapperFactoryBean.setSqlSessionFactory(this.dbSqlSessionFactory());
		return mapperFactoryBean;
	}
}