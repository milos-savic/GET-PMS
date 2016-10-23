package by.get.pms.validation;

import by.get.pms.dto.UserDTO;
import by.get.pms.model.UserRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by milos on 23-Oct-16.
 */
public class AdminValidator implements ConstraintValidator<Admin, UserDTO> {
    @Override
    public void initialize(Admin admin) {
    }

    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext constraintValidatorContext) {
        if (user == null) return false;

        return user.isAdmin();
    }
}
