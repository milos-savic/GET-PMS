package by.get.pms.validation;

import by.get.pms.model.UserRole;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public class ValidRoleValidator implements ConstraintValidator<ValidRole, String> {

	@Override
	public void initialize(ValidRole constraintAnnotation) {
		// nothing to initialize
	}

	@Override
	public boolean isValid(final String code, final ConstraintValidatorContext context) {
		if (code == null) {
			return false;
		}

		for (UserRole role : UserRole.values()) {
			if (role.name().equals(code)) {
				return true;
			}
		}
		return false;
	}
}
