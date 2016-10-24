package by.get.pms.service.user;

import by.get.pms.dataaccess.ProjectRepository;
import by.get.pms.dataaccess.TaskRepository;
import by.get.pms.dataaccess.UserAccountRepository;
import by.get.pms.dataaccess.UserRepository;
import by.get.pms.dto.UserDTO;
import by.get.pms.exception.ApplicationException;
import by.get.pms.model.Project;
import by.get.pms.model.Task;
import by.get.pms.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Milos.Savic on 10/20/2016.
 */
@Component
public class UserPreconditionImpl implements UserPrecondition {

	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Autowired
	private ProjectRepository projectRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Override
	public void checkCreateUserPreconditions(UserDTO userParams) throws ApplicationException {
		if (userExistsByUsername(userParams.getUsername())) {
			ApplicationException applicationException = new ApplicationException("users.createUser.AlreadyExists");
			applicationException.setParams(new String[] { userParams.getUsername() });
			throw applicationException;
		}
	}

	@Override
	public void checkUpdateUserPreconditions(UserDTO userParams) throws ApplicationException {

		if (!userRepository.exists(userParams.getId())) {
			ApplicationException applicationException = new ApplicationException(
					"users.updateUser.NonExistingRecordForUpdate");
			applicationException.setParams(new String[] { userParams.getId().toString() });
			throw applicationException;
		}

		if(userExistsByUsername(userParams.getUsername())){
			User user = userAccountRepository.findUserAccountByUsername(userParams.getUsername()).getUser();
			if(!user.getId().equals(userParams.getId())){
				ApplicationException applicationException = new ApplicationException("users.updateUser.AlreadyExists");
				applicationException.setParams(new String[] { userParams.getUsername() });
				throw applicationException;
			}
		}

		// precondition for role update;
		// situation: PM -> DEV, ADMIN and PM is assigned to project
		if (userParams.isProjectManager())
			return;

		User projectManager = userRepository.findOne(userParams.getId());
		if (!projectManager.isProjectManager())
			return;

		List<Project> projectManagerProjects = projectRepository.findProjectManagerProjects(projectManager);

		if (!projectManagerProjects.isEmpty()) {
			ApplicationException applicationException = new ApplicationException("users.updateUser.roleUpdate");
			applicationException.setParams(new String[] { userParams.getRole().name(), String.join(", ",
					projectManagerProjects.parallelStream().map(Project::getName).collect(Collectors.toList())) });
			throw applicationException;
		}
	}

	@Override
	public void checkRemoveUserPreconditions(Long userId) throws ApplicationException {
		// precondition for referential integrities (user assigned to task, pm assidned to project)

		User user = userRepository.findOne(userId);

		List<Task> tasksAssignedToUser = taskRepository.findTasksAssignedToUser(user);
		if (!tasksAssignedToUser.isEmpty()) {
			ApplicationException applicationException = new ApplicationException("users.removeUser.userWithTasks");
			applicationException.setParams(new String[] { String.join(", ",
					tasksAssignedToUser.parallelStream().map(Task::getName).collect(Collectors.toList())) });
			throw applicationException;
		}

		if (user.isProjectManager()) {
			List<Project> projectManagerProjects = projectRepository.findProjectManagerProjects(user);
			if (!projectManagerProjects.isEmpty()) {
				ApplicationException applicationException = new ApplicationException("users.removeUser.pmWithProjects");
				applicationException.setParams(new String[] { String.join(", ",
						projectManagerProjects.parallelStream().map(Project::getName).collect(Collectors.toList())) });
				throw applicationException;
			}
		}
	}

	private boolean userExistsByUsername(String username) {
		return userAccountRepository.findUserAccountByUsername(username) != null;
	}
}
