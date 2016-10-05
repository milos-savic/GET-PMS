package by.get.pms.config;

import liquibase.integration.spring.SpringLiquibase;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
public class LiquibaseConfiguration {

	private final Logger log = LoggerFactory.getLogger(LiquibaseConfiguration.class);

	@Autowired
	private Environment env;

	/**
	 * Open the TCP port for the H2 database, so it is available remotely.
	 *
	 * @return the H2 database TCP server
	 * @throws SQLException
	 *             if the server failed to start
	 */
	@Bean(initMethod = "start", destroyMethod = "stop")
	public Server h2TCPServer() throws SQLException {
		return Server.createTcpServer("-tcp", "-tcpAllowOthers");
	}

	@Bean
	public SpringLiquibase liquibase(DataSource dataSource, DataSourceProperties dataSourceProperties,
			LiquibaseProperties liquibaseProperties) {

		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:changelog-master.xml");
		liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
		liquibase.setDropFirst(liquibaseProperties.isDropFirst());
		liquibase.setShouldRun(liquibaseProperties.isEnabled());

		log.debug("Configuring Liquibase");

		return liquibase;
	}
}
