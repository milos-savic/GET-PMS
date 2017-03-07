package by.get.pms.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class SpringJpaDataSourceConfig extends HikariConfig {

	private static final Logger log = LoggerFactory.getLogger(SpringJpaDataSourceConfig.class);

	@Primary
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		final String url = this.getJdbcUrl();
		log.info("Configuring Primary Datasource url={} and username={}", url, this.getUsername());
		if (url == null) {
			log.error("Your database connection pool configuration is incorrect! The application cannot start.");
			throw new ApplicationContextException("Database connection pool is not configured correctly");
		}

		return new HikariDataSource(this);
	}
}
