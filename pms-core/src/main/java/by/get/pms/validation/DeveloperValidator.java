package by.get.pms.validation;

import by.get.pms.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by milos on 23-Oct-16.
 */
public class DeveloperValidator implements ConstraintValidator<Developer, UserDTO> {
    @Override
    public void initialize(Developer developer) {
    }

    @Override
    public boolean isValid(UserDTO userDTO, ConstraintValidatorContext constraintValidatorContext) {
        if (userDTO == null) return false;

        return userDTO.isDeveloper();
    }
}
