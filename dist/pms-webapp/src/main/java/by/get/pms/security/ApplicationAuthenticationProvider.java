//package by.get.pms.security;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.social.security.SocialAuthenticationToken;
//
///**
// * Created by Milos.Savic on 10/12/2016.
// */
//public class ApplicationAuthenticationProvider implements AuthenticationProvider {
//
//    protected static final Log LOGGER = LogFactory.getLog(ApplicationAuthenticationProvider.class);
//
//    @Autowired
//    private SocialAuthenticationService socialAuthenticationService;
//
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        if (!this.supports(authentication.getClass())) {
//            return null;
//        } else {
//            if (LOGGER.isDebugEnabled()) {
//                LOGGER.debug("PreAuthenticated authentication request: " + authentication);
//            }
//
//            if (authentication.getPrincipal() == null) {
//                LOGGER.debug("No pre-authenticated principal found in request.");
//                throw new BadCredentialsException("No pre-authenticated principal found in request.");
//            } else {
//                SocialAuthenticationToken socialAuthenticationToken = (SocialAuthenticationToken) authentication;
//                UserDetails ud = socialAuthenticationService.authenticate(socialAuthenticationToken);
//                SocialAuthenticationToken preAuthenticatedAuthenticationToken = new SocialAuthenticationToken(socialAuthenticationToken.getConnection(),
//                        socialAuthenticationToken.getPrincipal(), socialAuthenticationToken.getProviderAccountData(),
//                        ud.getAuthorities());
//
//                SecurityContextHolder.getContext().setAuthentication(preAuthenticatedAuthenticationToken);
//
//                return preAuthenticatedAuthenticationToken;
//            }
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
