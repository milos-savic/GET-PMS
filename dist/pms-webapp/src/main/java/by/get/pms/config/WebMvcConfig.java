package by.get.pms.config;

import by.get.pms.web.conversion.ProjectFormatter;
import by.get.pms.web.conversion.TaskStatusFormatter;
import by.get.pms.web.conversion.UserFormatter;
import by.get.pms.web.conversion.UserRoleFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
@ComponentScan({"by.get.pms.web"})
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private MessageSource messageSource;

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
    public TaskStatusFormatter taskStatusFormatter(){
        return new TaskStatusFormatter();
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
