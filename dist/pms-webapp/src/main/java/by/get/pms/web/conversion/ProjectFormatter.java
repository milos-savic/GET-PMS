package by.get.pms.web.conversion;

import by.get.pms.dto.ProjectDTO;
import by.get.pms.service.project.ProjectService;
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
    private ProjectService projectService;

    @Override
    public ProjectDTO parse(String code, Locale locale) throws ParseException {
        if (Strings.isNullOrEmpty(code)) return null;
        return projectService.getProjectByCode(code);
    }

    @Override
    public String print(ProjectDTO projectDTO, Locale locale) {
        return projectDTO != null ? projectDTO.getCode() : "";
    }
}
