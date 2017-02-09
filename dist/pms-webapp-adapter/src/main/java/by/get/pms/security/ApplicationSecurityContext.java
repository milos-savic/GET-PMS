package by.get.pms.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

/**
 * Custom implementation of {@link org.springframework.security.core.context.SecurityContext}.
 */
public class ApplicationSecurityContext extends SecurityContextImpl {

	private static final Log LOGGER = LogFactory.getLog(ApplicationSecurityContext.class);

	private ApplicationAttributes attributes;

	public static ApplicationAttributes getContext() {
		final SecurityContext context = SecurityContextHolder.getContext();
		if (!(context instanceof ApplicationSecurityContext)) {
			LOGGER.warn("ApplicationSecurityContext expected but found: " + context + ". Replacing it...");
			SecurityContextHolder.setContext(new ApplicationSecurityContext());
		}

		ApplicationAttributes attributes = ((ApplicationSecurityContext) SecurityContextHolder.getContext())
				.getAttributes();
		if (attributes == null) {
			attributes = new ApplicationAttributes();
			setContext(attributes);
		}
		return attributes;
	}

	public static void setContext(ApplicationAttributes applicationAttributes) {
		ApplicationSecurityContext securityContext = (ApplicationSecurityContext) SecurityContextHolder.getContext();
		if (securityContext == null) {
			securityContext = new ApplicationSecurityContext();
			SecurityContextHolder.setContext(securityContext);
		}
		securityContext.setAttributes(applicationAttributes);
	}

	public static void clear() {
		SecurityContextHolder.clearContext();
	}

	public ApplicationAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(ApplicationAttributes attributes) {
		this.attributes = attributes;
	}
}
