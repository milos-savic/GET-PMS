package by.get.pms.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by Milos.Savic on 10/12/2016.
 */
public class User extends org.springframework.security.core.userdetails.User {

	private String displayName = "";

	public User(final String username, final String password,
			final Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

	public User(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof User)) {
			return false;
		}
		if (!super.equals(o)) {
			return false;
		}

		final User user = (User) o;

		if (!displayName.equals(user.displayName)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + displayName.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer("User{");
		sb.append(", displayName='").append(displayName).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
