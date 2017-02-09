package by.get.pms.web.conversion;

import by.get.pms.dto.UserRole;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by milos on 23-Oct-16.
 */
public class UserRoleFormatter implements Formatter<UserRole> {

    @Override
    public UserRole parse(String roleName, Locale locale) throws ParseException {
        return UserRole.make(roleName);
    }

    @Override
    public String print(UserRole userRole, Locale locale) {
        return userRole != null ? userRole.name() : "";
    }
}
