package by.get.pms.config;

import by.get.pms.security.AjaxAuthExceptionTranslationFilter;
import by.get.pms.security.SimpleSocialUsersDetailService;
import by.get.pms.security.SocialAuthenticationService;
import by.get.pms.web.controller.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import javax.sql.DataSource;

/**
 * Created by milos on 20-Nov-16.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private SocialAuthenticationService socialAuthenticationService;

	@Autowired
	private DataSource dataSource;

//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		auth.
//				jdbcAuthentication().dataSource(dataSource).withDefaultSchema();
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// SpringSocialConfigurer has SocialAuthenticationFilter extends AbstractAuthenticationProcessingFilter
		// AbstractAuthenticationProcessingFilter#doFilter(...) -> successfulAuthentication(..) -> put signed-in user into Security Context

		http.authorizeRequests().antMatchers("/signin", "/h2-console/**", "/favicon.ico", "/resources/**").permitAll()
				.antMatchers("/**").authenticated().and().exceptionHandling()
				.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(WebConstants.SIGNIN_PAGE)).and().logout()
				.logoutUrl(WebConstants.LOGOUT_URL).logoutSuccessUrl(WebConstants.LOGOUT_SUCCESS_URL)
				.clearAuthentication(true).invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll().and()
				.csrf().disable().headers().frameOptions().disable().and().rememberMe().and()
				.apply(new SpringSocialConfigurer().postLoginUrl("/").alwaysUsePostLoginUrl(true)).and()
				.addFilterAfter(ajaxAuthExceptionTranslationFilter(), ExceptionTranslationFilter.class);
	}

	@Bean
	public SocialUserDetailsService socialUsersDetailService() {
		return new SimpleSocialUsersDetailService(socialAuthenticationService);
	}

	@Bean
	public ExceptionTranslationFilter ajaxAuthExceptionTranslationFilter() {
		return new AjaxAuthExceptionTranslationFilter(ajaxAuthEntryPoint());
	}

	@Bean
	public Http403ForbiddenEntryPoint ajaxAuthEntryPoint() {
		return new Http403ForbiddenEntryPoint();
	}
}
