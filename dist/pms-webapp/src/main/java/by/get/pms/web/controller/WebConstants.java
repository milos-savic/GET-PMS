package by.get.pms.web.controller;

/**
 * Created by milos.savic on 10/5/2016.
 */
public final class WebConstants {

	private WebConstants() {
	}

	public final static String REST_API_URL = "/rest";

	public final static String DEFAULT_PAGE = "/";
	public final static String SIGNIN_PAGE = "/signin";
	public final static String LOGIN_TO_FB_URL = "/login/facebook";
	public final static String LOGIN_TO_GITHUB_URL = "/login/github";

	public final static String LOGOUT_URL = "/logout";
	public final static String LOGOUT_SUCCESS_URL = SIGNIN_PAGE + "?logout";

	public final static String USER_MANAGEMENT_URL = "/userManagement";
	public final static String USER_MANAGEMENT_INDEX_HTML_PATH = "user/userManagement";
	public final static String CREATE_UPDATE_USER_URL = "/createUpdateUser";
	public final static String DELETE_USER_URL = "/deleteUser";

	public final static String TASKS_URL = "/tasks";
	public final static String TASKS_HTML_PATH = "/task/tasks";
	public final static String CREATE_TASK_URL = "/createTask";
	public final static String UPDATE_TASK_URL = "/updateTask";
	public final static String DELETE_TASK_URL = "/deleteTask";

	public final static String PROJECTS_URL = "/projects";
	public final static String PROJECTS_HTML_PATH = "/project/projects";
	public final static String CREATE_PROJECT_URL = "/createProject";
	public final static String UPDATE_PROJECT_URL = "/updateProject";
	public final static String DELETE_PROJECT_URL = "/removeProject";
}
