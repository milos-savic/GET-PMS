package by.get.pms;

import by.get.pms.config.PmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.net.UnknownHostException;

/**
 * Created by milos.savic on 10/5/2016.
 */

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableConfigurationProperties({ PmsProperties.class, LiquibaseProperties.class })
public class PmsApplication extends SpringBootServletInitializer {

	private final static Logger log = LoggerFactory.getLogger(PmsApplication.class);

	@Autowired
	private Environment env;

	/**
	 * Main method, used to run the application.
	 *
	 * @param args
	 *            the command line arguments
	 * @throws UnknownHostException
	 *             if the local host name could not be resolved into an address
	 */
	public static void main(String[] args) throws UnknownHostException {
		SpringApplication app = new SpringApplication(PmsApplication.class);
		Environment env = app.run(args).getEnvironment();
		log.info("\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URL:\n\t" + "Local: \t\thttp://127.0.0.1:{}\n\t"
						+ "\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), env.getProperty("server.port"));
	}
}
