package by.get.pms.web.conversion;

import by.get.pms.dtos.ProjectDTO;
import by.get.pms.facade.project.ProjectFacade;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

/**
 * Created by milos on 23-Oct-16.
 */
public class ProjectFormatter implements Formatter<ProjectDTO> {

	@Autowired
	private ProjectFacade projectFacade;

	@Override
	public ProjectDTO parse(String id, Locale locale) throws ParseException {
		if (Strings.isNullOrEmpty(id))
			return null;
		Long projectId = Long.valueOf(id);
		return projectFacade.getProject(projectId);
	}

	@Override
	public String print(ProjectDTO projectDTO, Locale locale) {
		return projectDTO != null ? projectDTO.getId().toString() : "";
	}
}
