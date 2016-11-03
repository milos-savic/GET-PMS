function clearCreateTaskForm(){
    $("#createTaskFormId").val([]);
    $("#createTaskFormName").val([]);
    $("#createTaskFormStatus").val(1);
    $("#createTaskFormProgress").val([]);
    $("#createTaskFormDeadline").val(DateFormat.format.date(new Date(), Configs.inputFieldDatePickerFormat));
    $("#createTaskFormDescription").val([]);
    $("#createTaskFormAssignee").val(1);
}

function addRecordCreateUserFormSaveData() {
    var recordData = collectCreateFromData();

    if(!recordData.progress) recordData.progress = 0;

    if (isCreateDataValid(recordData)) {
        sendPostRequest(Configs.createTaskUrl, recordData, addRecordCreateSuccessHandler, null);
    }
}

function collectCreateFromData() {
    var newRecordData = {};
    newRecordData.id = $("#createTaskFormId").val();
    newRecordData.project = $("#createTaskFormProject").val();
    newRecordData.name = $("#createTaskFormName").val();
    newRecordData.taskStatus = $("#createTaskFormStatus").val();
    newRecordData.progress = $("#createTaskFormProgress").val();
    newRecordData.deadline = $("#createTaskFormDeadline").val();
    newRecordData.description = $("#createTaskFormDescription").val();
    newRecordData.assignee = $("#createTaskFormAssignee").val();

    return newRecordData;
}

var requiredCreateStringProps = {
    name: {maxLength: 50},
    taskStatus: {maxLength: 30},
    deadline: {maxLength: 30},
    description: {maxLength: 255}
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

        if(prop == 'progress' && recordData[prop] != Math.floor(recordData[prop])){
            addError(prop, "must be integer");
            continue;
        }

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
    $("#createTaskDialog").modal("hide");
    var selectedData = getSelectedTableRow();
    var serverData = json.model.task;
    serverData.assignee = serverData.assignee.userName;
    serverData.project = serverData.project.id;

    addNewTableRow(serverData);

    showSuccessDialog(json);
}


function initCreateTaskForm(){

    /*
    Check https://github.com/xdan/datetimepicker/blob/master/build/jquery.datetimepicker.full.js for more options
    var default_options = {...} object contains default datetimepicker options that can be overridden
    */
    $.datetimepicker.setLocale('en');
    $('#createTaskFormDeadline').datetimepicker({
        yearStart: 1999,
        yearEnd: 2050,
        format: Configs.datePickerFormat,
        dayOfWeekStart: 1,
        lang: 'en'
    });

    $("#createTaskButton").click(function () {
        clearCreateTaskForm();
    });

    $("#addRecordOkButton").click(function () {
        $('input[type=submit]', this).attr('disabled', 'disabled');
        addRecordCreateUserFormSaveData();
    });
}