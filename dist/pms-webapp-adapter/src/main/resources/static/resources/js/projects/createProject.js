function clearCreateProjectForm(){
    $("#createProjectFormId").val([]);
    $("#createProjectFormCode").val([]);
    $("#createProjectFormName").val([]);
    $("#createProjectFormDescription").val([]);
    $("#createProjectFormPM").val(1);
}

function addRecordCreateUserFormSaveData() {
    var recordData = collectCreateFromData();
    if (isCreateDataValid(recordData)) {
        sendPostRequest(Configs.createProjectUrl, recordData, addRecordCreateSuccessHandler, null);
    }
}

function collectCreateFromData() {
    var newRecordData = {};
    newRecordData.id = $("#createProjectFormId").val();
    newRecordData.code = $("#createProjectFormCode").val();
    newRecordData.name = $("#createProjectFormName").val();
    newRecordData.description = $("#createProjectFormDescription").val();
    newRecordData.projectManager = $("#createProjectFormPM").val();

    return newRecordData;
}

var requiredCreateStringProps = {
    code: {maxLength: 30},
    name: {maxLength: 50},
    description: {maxLength: 255},
    projectManager: {maxLength: 30}
};

var nonRequiredStringProps = {};


function isCreateDataValid(recordData) {
    var errors = [];

    function addError(prop, message) {
        var newError = "Field `" + propsNames[prop] + "` has error: " + message;
        errors.push(newError);
    }

    for (var prop in recordData) {
        if (!recordData.hasOwnProperty(prop)) continue;
        var stringValue = recordData[prop];
        var errorFound = false;
        var length = stringValue ? ("" + stringValue).trim().length : 0;

        if (requiredCreateStringProps.hasOwnProperty(prop) || nonRequiredStringProps.hasOwnProperty(prop)) {
            var maxLength = 255;

            if (requiredCreateStringProps.hasOwnProperty(prop)) {
                maxLength = requiredCreateStringProps[prop].maxLength;
            } else if (nonRequiredStringProps.hasOwnProperty(prop)) {
                maxLength = nonRequiredStringProps[prop].maxLength;
            }
            if (requiredCreateStringProps.hasOwnProperty(prop) && length == 0) {
                addError(prop, "value is required");
            } else if (length > maxLength) {
                addError(prop, "Max length is " + maxLength);
            }
        }
    }

    if (errors.length > 0) {
        showModalDialog(errors.join("<br>\n"));
        return false;
    }
    return true;
}

function addRecordCreateSuccessHandler(json) {
    $("#createProjectDialog").modal("hide");
    var selectedData = getSelectedTableRow();
    var serverData = json.model.project;
    serverData.projectManager = serverData.projectManager.userName;

    addNewTableRow(serverData);

    showSuccessDialog(json);
}

function initCreateProjectForm(){

    $("#createProjectButton").click(function () {
        clearCreateProjectForm();
    });

    $("#addRecordOkButton").click(function () {
        $('input[type=submit]', this).attr('disabled', 'disabled');
        addRecordCreateUserFormSaveData();
    });
}