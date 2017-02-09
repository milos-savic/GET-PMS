package by.get.pms.service.project;

import by.get.pms.dtos.ProjectDTO;
import by.get.pms.dtos.UserDTO;
import by.get.pms.exception.ApplicationException;

/**
 * Created by Milos.Savic on 10/26/2016.
 */
interface ProjectPreconditions {

	void checkCreateProjectPreconditions(ProjectDTO projectParams) throws ApplicationException;

	void checkCreateProjectByPMPreconditions(UserDTO projectManager, ProjectDTO projectParams)
			throws ApplicationException;

	void checkUpdateProjectPreconditions(ProjectDTO projectParams) throws ApplicationException;

	void checkRemoveProjectPreconditions(Long projectId) throws ApplicationException;
}
