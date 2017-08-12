package com.demo.spring.config;

import java.util.Properties;
import java.util.concurrent.Executor;

import javax.annotation.Resource;
import javax.sql.DataSource;

/*import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;*/
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author sachin
 *
 */

@EnableAsync
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({ "com.demo.spring"})
@EnableJpaRepositories({ "com.demo.spring.repositories" })
@PropertySources(value = {@PropertySource("classpath:application.properties")})
public class AppConfig extends WebMvcConfigurerAdapter{

	@Resource
	private Environment env;

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		dataSource.setUrl(env.getRequiredProperty("jdbc.url"));
		dataSource.setUsername(env.getRequiredProperty("jdbc.username"));
		dataSource.setPassword(env.getRequiredProperty("jdbc.password"));

		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		entityManagerFactoryBean.setPackagesToScan("com.demo.spring.domain");

		entityManagerFactoryBean.setJpaProperties(hibProperties());

		return entityManagerFactoryBean;
	}

	private Properties hibProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));

		// properties.put("hibernate.show_sql",
		// env.getRequiredProperty("hibernate.show_sql"));
		return properties;
	}

	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

/*	@Bean
	public JavaMailSenderImpl javaMailSenderImpl() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		// Set gmail email id
		mailSender.setUsername("testvdotnode2017@gmail.com");
		// Set gmail email password
		mailSender.setPassword("123@test");
		Properties prop = mailSender.getJavaMailProperties();
		prop.put("mail.transport.protocol", "smtp");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		prop.put("From", "testvdotnode2017@gmail.com");
		prop.put("mail.smtp.ssl.trust", "*");
		prop.put("mail.smtp.ssl", "true");
		prop.put("mail.smtp.debug", "true");
		env.getProperty("From");
		return mailSender;
	}*/

	/*
	 * @Bean public VelocityEngine getVelocityEngine() throws VelocityException,
	 * IOException { VelocityEngineFactory factory = new
	 * VelocityEngineFactory(); Properties props = new Properties();
	 * props.put("resource.loader", "class");
	 * props.put("class.resource.loader.class",
	 * "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	 *
	 * factory.setVelocityProperties(props); return
	 * factory.createVelocityEngine(); }
	 */

	/*
	 * FreeMarker configuration.
	 */
	/*
	 * @Bean public FreeMarkerConfigurationFactoryBean
	 * getFreeMarkerConfiguration() { FreeMarkerConfigurationFactoryBean bean =
	 * new FreeMarkerConfigurationFactoryBean();
	 * bean.setTemplateLoaderPath("/fmtemplates/"); return bean; }
	 */

/*	@Override
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(7);
		executor.setMaxPoolSize(42);
		executor.setQueueCapacity(11);
		executor.setThreadNamePrefix("MyExecutor-");
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		// TODO Auto-generated method stub
		return null;
	}*/

	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver getMultipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		multipartResolver.setMaxInMemorySize(1048576);
		multipartResolver.setMaxUploadSize(20971520);

		return multipartResolver;
	}
	/*
	 * @Bean public LoginInterceptor loginInterceptor(){ return new
	 * LoginInterceptor(); }
	 *
	 *
	 * @Override public void addInterceptors(InterceptorRegistry registry) {
	 * registry.addInterceptor(loginInterceptor()).addPathPatterns("/*").
	 * excludePathPatterns("/login").excludePathPatterns("/utils/*").
	 * excludePathPatterns("/users/register"); registry.addInterceptor(new
	 * TransactionInterceptor()).addPathPatterns("/person/save/*"); }
	 */
}