package by.get.pms.security;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.util.Assert;

/**
 * Custom security context holder strategy.
 */
public class ApplicationSecurityContextHolderStrategy implements SecurityContextHolderStrategy {

	private static final ThreadLocal<SecurityContext> contextHolder = new ThreadLocal<>();

	/**
	 * {@inheritDoc}
	 */
	public void clearContext() {
		contextHolder.set(null);
	}

	/**
	 * {@inheritDoc}
	 */
	public SecurityContext getContext() {
		SecurityContext ctx = contextHolder.get();

		if (ctx == null) {
			ctx = createEmptyContext();
			contextHolder.set(ctx);
		}

		return ctx;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setContext(SecurityContext context) {
		Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
		contextHolder.set(context);
	}

	/**
	 * {@inheritDoc}
	 */
	public SecurityContext createEmptyContext() {
		return new ApplicationSecurityContext();
	}
}
