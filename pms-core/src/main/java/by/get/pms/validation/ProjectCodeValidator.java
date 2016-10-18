package by.get.pms.validation;

import by.get.pms.dataaccess.ProjectRepository;
import by.get.pms.model.Project;
import by.get.pms.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Milos.Savic on 10/18/2016.
 */
public class ProjectCodeValidator implements ConstraintValidator<ProjectCode, String> {

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public void initialize(ProjectCode constraintAnnotation) {
		// nothing to initialize
	}

	@Override
	public boolean isValid(final String projectCode, final ConstraintValidatorContext context) {
		Project projectByProjectCode = projectRepository.findProjectByCode(projectCode);
		return projectCode != null && !projectCode.isEmpty() && projectByProjectCode == null;
	}
}
