package by.get.pms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
@ComponentScan("by.get.pms.config")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "by.get.pms.dataaccess" })
public class SpringJpaConfig {

	@Autowired
	private DataSource dataSource;

	@Autowired
	public JpaVendorAdapter jpaVendorAdapter;

	@Bean
	public JdbcTemplate jdbcTemplate() {
		return new JdbcTemplate(dataSource);
	}

	// @Primary
	// @Bean(name = "entityManager")
	// public EntityManager entityManager() {
	// return entityManagerFactory().getObject().createEntityManager();
	// }

	@Primary
	@Bean(name = "entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		emf.setDataSource(dataSource);
		emf.setJpaVendorAdapter(jpaVendorAdapter);
		emf.setPackagesToScan("by.get.pms.model");
		emf.setPersistenceUnitName("default"); // <- giving 'default' as name
		emf.afterPropertiesSet();
		return emf;
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager tm = new JpaTransactionManager();
		tm.setEntityManagerFactory(entityManagerFactory().getObject());
		return tm;
	}
}
