var Configs = {
    serverTimeout: 3600000, // 60 mins for now 60*60*1000

    /* base url must be set on the main page on server side*/
    createUserUrl: baseUrl + "/rest/createUpdateUser",
    updateUserUrl: baseUrl + "/rest/createUpdateUser",
    deleteUserUrl: baseUrl + "/rest/deleteUser",

    createProjectUrl: baseUrl + "/rest/createProject",
    updateProjectUrl: baseUrl + "/rest/updateProject",
    removeProjectUrl: baseUrl + "/rest/removeProject",

    createTaskUrl: baseUrl + "/rest/createTask",
    updateTaskUrl: baseUrl + "/rest/updateTask",
    removeTaskUrl: baseUrl + "/rest/deleteTask",


    inputFieldDatePickerFormat: 'dd/MM/yyyy',
    datePickerFormat: 'd/m/Y'
}
