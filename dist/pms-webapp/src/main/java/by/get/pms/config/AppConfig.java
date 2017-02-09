package by.get.pms.config;

/**
 * Created by milos.savic on 10/5/2016.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Stuff configured here will be available on root application context level.
 * <p>
 * Configuration directives, bean definitions etc specific to the Spring MVC
 * dispatcher servlet should go into WebMvcConfig.
 */
@Configuration
@Import({SpringJpaConfig.class, SecurityConfiguration.class, LiquibaseConfiguration.class})
public class AppConfig {
}
