package by.get.pms.service.user;

import by.get.pms.dataaccess.ProjectRepository;
import by.get.pms.dataaccess.TaskRepository;
import by.get.pms.dataaccess.UserAccountRepository;
import by.get.pms.dataaccess.UserRepository;
import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/20/2016.
 */
@Component
public class UserPreconditionImpl implements UserPrecondition {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public void checkUpdateUserPreconditions(UserDTO userParams) throws ApplicationException {
		// precondition for role update;
		// situation: PM -> DEV, ADMIN and PM is assigned to project
		UserRole newUserRole = UserRole.make(userParams.getRoleName());

		if (newUserRole.equals(UserRole.PROJECT_MANAGER))
			return;

		UserRole oldUserRole = userAccountRepository.findUserAccountByUser(userParams.getId()).getRole();
		if (!oldUserRole.equals(UserRole.PROJECT_MANAGER))
			return;

		User projectManager = userRepository.findOne(userParams.getId());
		List<Project> projectManagerProjects = projectRepository.findProjectManagerProjects(projectManager);

		if (!projectManagerProjects.isEmpty()) {
			ApplicationException applicationException = new ApplicationException("users.updateUser.roleUpdate");
			applicationException.setParams(new String[] { newUserRole.name(), String.join(", ",
					projectManagerProjects.parallelStream().map(Project::getName).collect(Collectors.toList())) });
			throw applicationException;
		}
	}

	@Override
	public void checkRemoveUserPreconditions(Long userId) throws ApplicationException {
		// precondition for referential integrities (user assigned to task, pm assidned to project)

		User user = userRepository.findOne(userId);
		UserAccount userAccount = userAccountRepository.findUserAccountByUser(userId);

		List<Task> tasksAssignedToUser = taskRepository.findTasksAssignedToUser(user);
		if(!tasksAssignedToUser.isEmpty()){
			ApplicationException applicationException = new ApplicationException("users.removeUser.userWithTasks");
			applicationException.setParams(new String[] { String.join(", ",
					tasksAssignedToUser.parallelStream().map(Task::getName).collect(Collectors.toList())) });
			throw applicationException;
		}


		if (UserRole.PROJECT_MANAGER.equals(userAccount.getRole())) {
			List<Project> projectManagerProjects = projectRepository.findProjectManagerProjects(user);
			if (!projectManagerProjects.isEmpty()) {
				ApplicationException applicationException = new ApplicationException("users.removeUser.pmWithProjects");
				applicationException.setParams(new String[] { String.join(", ",
						projectManagerProjects.parallelStream().map(Project::getName).collect(Collectors.toList())) });
				throw applicationException;
			}
		}

	}
}
