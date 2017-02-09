package by.get.pms.validation;

import by.get.pms.dto.UserDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by milos on 19-Oct-16.
 */
public class ProjectManagerValidator implements ConstraintValidator<ProjectManager, UserDTO> {

	@Override
	public void initialize(ProjectManager constraintAnnotation) {
		// nothing to initialize
	}

	@Override
	public boolean isValid(final UserDTO user, final ConstraintValidatorContext context) {
		return user != null && user.isProjectManager();
	}
}
