//package by.get.pms.config;
//
//import by.get.pms.security.ApplicationAuthenticationProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.context.SecurityContextHolder;
//
///**
// * Created by Milos.Savic on 10/12/2016.
// */
//
//@Configuration
//public class PreAuthConfig {
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        System.setProperty(SecurityContextHolder.SYSTEM_PROPERTY,
//                "by.get.pms.security.ApplicationSecurityContextHolderStrategy");
//
//        final ApplicationAuthenticationProvider applicationAuthenticationProvider = new ApplicationAuthenticationProvider();
//
//        return applicationAuthenticationProvider;
//    }
//}
