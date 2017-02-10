package by.get.pms.validation;

import by.get.pms.data.UserData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by milos on 19-Oct-16.
 */
public class ProjectManagerValidator implements ConstraintValidator<ProjectManager, UserData> {

	@Override
	public void initialize(ProjectManager constraintAnnotation) {
		// nothing to initialize
	}

	@Override
	public boolean isValid(final UserData user, final ConstraintValidatorContext context) {
		return user != null && user.isProjectManager();
	}
}
