package by.get.pms.config.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by milos.savic on 10/5/2016.
 */

/**
 * Properties specific to PMS.
 * <p/>
 * <p>
 * Properties are configured in the application.properites file.
 * </p>
 */
@ConfigurationProperties(prefix = "pms", ignoreUnknownFields = false)
public class PmsProperties {

	public static class DefaultSystemProperties {
		private String locale;

		public String getLocale() {
			return locale;
		}

		public void setLocale(String locale) {
			this.locale = locale;
		}
	}

}
