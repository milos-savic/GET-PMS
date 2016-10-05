package by.get.pms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
@ComponentScan({ "by.get.pms.web" })
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(Locale.US);
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
