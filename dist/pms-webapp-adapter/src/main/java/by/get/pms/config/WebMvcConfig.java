package by.get.pms.config;

import by.get.pms.web.conversion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
@ComponentScan({ "by.get.pms.web" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Environment env;

	@Bean
	public Validator validator() {
		LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
		validator.setValidationMessageSource(messageSource);
		return validator;
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(Locale.US);
	}

	@Override
	public void addFormatters(final FormatterRegistry registry) {
		super.addFormatters(registry);
		registry.addFormatter(userFormatter());
		registry.addFormatter(projectFormatter());
		registry.addFormatter(userRoleFormatter());
		registry.addFormatter(taskStatusFormatter());
		registry.addFormatter(localDateFormatter());
	}

	@Bean
	public UserFormatter userFormatter() {
		return new UserFormatter();
	}

	@Bean
	public ProjectFormatter projectFormatter() {
		return new ProjectFormatter();
	}

	@Bean
	public UserRoleFormatter userRoleFormatter() {
		return new UserRoleFormatter();
	}

	@Bean
	public TaskStatusFormatter taskStatusFormatter() {
		return new TaskStatusFormatter();
	}

	@Bean
	public LocalDateFormatter localDateFormatter() {
		String mvcDateFormat = env.getProperty("spring.mvc.date-format", "dd/MM/yyyy");
		return new LocalDateFormatter(DateTimeFormatter.ofPattern(mvcDateFormat));
	}

	/**
	 * Handles favicon.ico requests assuring no <code>404 Not Found</code> error
	 * is returned.
	 */
	@Controller
	static class FaviconController {

		@RequestMapping("favicon.ico")
		String favicon() {
			return "forward:/resources/images/favicon.ico";
		}
	}
}
