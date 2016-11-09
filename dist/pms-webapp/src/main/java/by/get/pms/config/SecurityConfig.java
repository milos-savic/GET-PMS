package by.get.pms.config;

import by.get.pms.security.AjaxAuthExceptionTranslationFilter;
import by.get.pms.security.PreAuthRequestHeaderAuthenticationFilter;
import by.get.pms.web.controller.WebConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by milos.savic on 10/5/2016.
 */
@Configuration
@PropertySource("classpath:/config/security.properties")
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/signin", "/h2-console/**", "/favicon.ico", "/resources/**").permitAll()
                .antMatchers("/**").authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint(WebConstants.SIGNIN_PAGE))
                .and().logout().logoutUrl(WebConstants.LOGOUT_URL).logoutSuccessUrl(WebConstants.LOGOUT_SUCCESS_URL).invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll()
                .and().csrf().disable()
                .headers().frameOptions().disable()
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
				.addFilterAfter(preAuthFilter(), RequestHeaderAuthenticationFilter.class)
                .addFilterAfter(ajaxAuthExceptionTranslationFilter(), ExceptionTranslationFilter.class);
    }

//    @Override
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder authenticationMenagerBuilder) throws Exception {
        authenticationMenagerBuilder.authenticationProvider(authenticationProvider);
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        filters.add(ssoFilter(facebook(), WebConstants.LOGIN_TO_FB_URL));
        filters.add(ssoFilter(github(), WebConstants.LOGIN_TO_GITHUB_URL));
        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(template);
        filter.setTokenServices(
                new UserInfoTokenServices(client.getResource().getUserInfoUri(), client.getClient().getClientId()));
        return filter;
    }

    @Bean
    @ConfigurationProperties(prefix = "facebook")
    public ClientResources facebook() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties(prefix = "github")
    public ClientResources github() {
        return new ClientResources();
    }

    // registration  OAuth2ClientContextFilter filter to explicitly support redirects from our app to auth providers (Facebook, GitHub)
    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    public Http403ForbiddenEntryPoint ajaxAuthEntryPoint() {
        return new Http403ForbiddenEntryPoint();
    }

    @Bean
    public ExceptionTranslationFilter ajaxAuthExceptionTranslationFilter() {
        return new AjaxAuthExceptionTranslationFilter(ajaxAuthEntryPoint());
    }

	@Bean
	public PreAuthRequestHeaderAuthenticationFilter preAuthFilter() throws Exception {
		PreAuthRequestHeaderAuthenticationFilter filter = new PreAuthRequestHeaderAuthenticationFilter();
		filter.setAuthenticationManager(authenticationManager());

        filter.setExceptionIfHeaderMissing(false);
		return filter;
	}

    // storing the Security Context between requests into HTTP Session
    @Bean
    SecurityContextPersistenceFilter securityContextPersistenceFilter() {
        HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
        repo.setAllowSessionCreation(true);
        return new SecurityContextPersistenceFilter(repo);
    }
}

class ClientResources {

    @NestedConfigurationProperty
    private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

    @NestedConfigurationProperty
    private ResourceServerProperties resource = new ResourceServerProperties();

    public AuthorizationCodeResourceDetails getClient() {
        return client;
    }

    public ResourceServerProperties getResource() {
        return resource;
    }
}

