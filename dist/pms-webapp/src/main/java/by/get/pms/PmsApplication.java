package by.get.pms;

import by.get.pms.config.spring.PmsProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.SimpleCommandLinePropertySource;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

/**
 * Created by milos.savic on 10/5/2016.
 */

@Configuration
@ComponentScan
@EnableConfigurationProperties({PmsProperties.class, LiquibaseProperties.class })
public class PmsApplication extends SpringBootServletInitializer{

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
		log.info(
				"\n----------------------------------------------------------\n\t"
						+ "Application '{}' is running! Access URL:\n\t" + "Local: \t\thttp://127.0.0.1:{}\n\t"
						+ "\n----------------------------------------------------------",
				env.getProperty("spring.application.name"), env.getProperty("server.port"));
	}
}
