package by.get.pms.security;

import by.get.pms.dtos.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.dtos.UserRole;
import by.get.pms.facade.user.UserFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by milos on 20-Nov-16.
 */
@Service
public class AccountConnectionSignUpService implements ConnectionSignUp {

	@Autowired
	private UserFacade userFacade;

	@Override
	public String execute(Connection<?> connection) {
		UserDTO userParams;

		// https://github.com/spring-projects/spring-social-facebook/issues/217
		if (connection.getApi() instanceof Facebook) {
			// all FB fields
			// { "id", "about", "age_range", "birthday", "context", "cover", "currency", "devices", "education", "email", "favorite_athletes", "favorite_teams", "first_name", "gender", "hometown", "inspirational_people", "installed", "install_type", "is_verified", "languages", "last_name", "link", "locale", "location", "meeting_for", "middle_name", "name", "name_format", "political", "quotes", "payment_pricepoints", "relationship_status", "religion", "security_settings", "significant_other", "sports", "test_group", "timezone", "third_party_id", "updated_time", "verified", "video_upload_limits", "viewer_can_send_gift", "website", "work"}
			Facebook facebook = (Facebook) connection.getApi();
			String[] fields = { "id", "email", "first_name", "last_name" };
			org.springframework.social.facebook.api.User user = facebook
					.fetchObject("me", org.springframework.social.facebook.api.User.class, fields);
			userParams = new UserDTO(-1L, user.getFirstName(), user.getLastName(), user.getEmail(), user.getEmail(),
					LocalDateTime.now(), true, UserRole.ROLE_GUEST);
		} else {
			org.springframework.social.connect.UserProfile profile = connection.fetchUserProfile();
			userParams = new UserDTO(-1L, profile.getFirstName(), profile.getLastName(), profile.getEmail(),
					profile.getEmail(), LocalDateTime.now(), true, UserRole.ROLE_GUEST);
		}
		if (userFacade.userExistsByUserName(userParams.getUserName())) {
			return userParams.getUserName();
		} else {
			try {
				UserDTO newUser = userFacade.createUser(userParams);
				return newUser.getUserName();
			} catch (ApplicationException ae) {
				throw new RuntimeException(ae);
			}
		}
	}
}
