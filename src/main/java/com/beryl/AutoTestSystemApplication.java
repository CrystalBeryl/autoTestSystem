package com.beryl;

import com.beryl.servlet.SessionFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("com.beryl")
@MapperScan("com.beryl.mapper")
@EnableRedisRepositories("com.beryl.repository")
@ServletComponentScan
public class AutoTestSystemApplication {

	@Bean
	RedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory();
	}

	@Bean
	RedisTemplate<byte[], byte[]> redisTemplate(RedisConnectionFactory connectionFactory) {

		RedisTemplate<byte[], byte[]> template = new RedisTemplate<byte[], byte[]>();
		template.setConnectionFactory(connectionFactory);

		return template;
	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	public DataSource dataSource() {
		return new org.apache.tomcat.jdbc.pool.DataSource();
	}

	@Bean
	public SqlSessionFactory sqlSessionFactoryBean() throws Exception {

		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:/mybatis/*Mapper.xml"));

		return sqlSessionFactoryBean.getObject();
	}

	@EnableRedisHttpSession
	public class HttpSessionConfig {
	}

	@Bean
	public FilterRegistrationBean registrationBean(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		SessionFilter sessionFilter= new SessionFilter();
		registrationBean.setFilter(sessionFilter);
		registrationBean.addUrlPatterns("/autoTest/*");
		return registrationBean;
	}

		//注册servletBean
	/*@Bean
	public ServletRegistrationBean servletRegistrationBean(){
			return new ServletRegistrationBean(new SessionServlet(),"/autoTest*//*");
		}
*/

	@Bean
	public DataSourceTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}




	public static void main(String[] args){
	SpringApplication.run(AutoTestSystemApplication.class, args);
	}
}
