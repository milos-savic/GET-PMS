package by.get.pms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
@Import(PackageConfig.class)
@PropertySource("classpath:commons-default.properties")
public class CoreConfig {
}
