package by.get.pms.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
@ComponentScan({ "by.get.pms.service", "by.get.pms.dataaccess",
		"by.get.pms.service.security" })
public class PackageConfig {
}
