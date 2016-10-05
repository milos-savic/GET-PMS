package by.get.pms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
@ImportResource(value = "classpath:spring-security-context.xml")
public class SecurityConfig {

	@Bean
	SecurityContextPersistenceFilter securityContextPersistenceFilter() {
		HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
		repo.setAllowSessionCreation(true);
		return new SecurityContextPersistenceFilter(repo);
	}
}
