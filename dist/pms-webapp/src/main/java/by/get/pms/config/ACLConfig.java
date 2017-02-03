package by.get.pms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;

/**
 * Created by Milos.Savic on 2/3/2017.
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class ACLConfig extends GlobalMethodSecurityConfiguration {

	@Autowired
	private DataSource dataSource;

	@Bean
	EhCacheBasedAclCache aclCache() {
		EhCacheFactoryBean factoryBean = new EhCacheFactoryBean();
		EhCacheManagerFactoryBean cacheManager = new EhCacheManagerFactoryBean();

		factoryBean.setName("aclCache");
		factoryBean.setCacheManager(cacheManager.getObject());

		return new EhCacheBasedAclCache(factoryBean.getObject(),
				new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger()), aclAuthorizationStrategy());
	}

	AclAuthorizationStrategy aclAuthorizationStrategy() {
		return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ACL_ADMIN"));
	}

	@Bean
	LookupStrategy lookupStrategy() {
		return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(), new ConsoleAuditLogger());
	}

	@Bean
	JdbcMutableAclService aclService() {
		JdbcMutableAclService service = new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
		return service;
	}

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(permissionEvaluator());
		expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));
		return expressionHandler;
	}

	@Bean
	AclPermissionEvaluator permissionEvaluator() {
		return new AclPermissionEvaluator(aclService());
	}

}
