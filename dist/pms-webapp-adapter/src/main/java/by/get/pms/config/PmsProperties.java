package by.get.pms.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by milos.savic on 10/5/2016.
 */

/**
 * Properties specific to PMS.
 * <p/>
 * <p>
 * Properties are configured in the application.properties file.
 * </p>
 */
@ConfigurationProperties(prefix = "pms", ignoreUnknownFields = false)
public class PmsProperties {

    private final DefaultSystemProperties defaultSystemProperties = new DefaultSystemProperties();

    public DefaultSystemProperties getDefaultSystemProperties() {
        return defaultSystemProperties;
    }

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
