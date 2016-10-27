var Configs = {
    serverTimeout: 3600000, // 60 mins for now 60*60*1000

    /* base url must be set on the main page on server side*/

    createTableUserUrl: baseUrl + "/rest/createTableUser",
    updateTableUserUrl: baseUrl + "/rest/updateTableUser",
    findTableUserUrl: baseUrl + "/rest/findTableUser",
    deleteTableUserUrl: baseUrl + "/rest/deleteTableUser",

    monthPickerFormat: 'dd/MM/yyyy HH:mm',
    dateTimePickerFormat: 'd/m/Y H:i',
    totalNumberFormat: '0,0[.]00 $',

    userRole: {
        Administrator: {
            code: 'Admin',
            value: 'Administrator'
        },
        PROJECT_MANAGER: {
            code: 'PM',
            value: 'PROJECT_MANAGER'
        },
        DEVELOPER: {
                    code: 'DEV',
                    value: 'DEVELOPER'
        }
    },

    userActive: {
        Yes: {
            code: 'Yes',
            value: 'Yes'
        },
        No: {
            code: 'No',
            value: 'No'
        }
    }
}
