package by.get.pms.validation;

import by.get.pms.dto.UserDTO;
import by.get.pms.model.UserRole;

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
    public boolean isValid(final UserDTO projectManager, final ConstraintValidatorContext context) {
        if (projectManager == null) {
            return false;
        }

        return UserRole.PROJECT_MANAGER.name().equals(projectManager.getRoleName());
    }
}
