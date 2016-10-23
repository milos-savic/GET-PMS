package by.get.pms.web.conversion;

import by.get.pms.dto.UserDTO;
import by.get.pms.service.user.UserFacade;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by milos on 23-Oct-16.
 */
public class UserFormatter implements Formatter<UserDTO> {

    @Autowired
    private UserFacade userFacade;

    @Override
    public UserDTO parse(String id, Locale locale) throws ParseException {
        if (Strings.isNullOrEmpty(id)) return null;

        Long userId = Long.valueOf(id);
        return userFacade.getUser(userId);
    }

    @Override
    public String print(UserDTO userDTO, Locale locale) {
        return userDTO != null ? userDTO.getId().toString() : "";
    }
}
