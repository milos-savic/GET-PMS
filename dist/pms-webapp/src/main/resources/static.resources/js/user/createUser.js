function clearCreateUserForm()(){
    $("#createUserFormId").val([]);
    $("#createUserFormUserName").val([]);
    $("#createUserFormFirstName").val([]);
    $("#createUserFormLastName").val([]);
    $("#createUserFormEmail").val([]);
    $("#createUserFormRole").val(1);
    $('#createUserFormActive').prop('checked', false);
}

function addRecordCreateUserFormSaveData() {
    var recordData = collectCreateFromData();
    if (isCreateDataValid(recordData)) {
        sendPostRequest(Configs.createUserUrl, recordData, addRecordCreateSuccessHandler, null);
    }
}

function collectCreateFromData() {
    var newRecordData = {};
    newRecordData.userId = $("#createUserFormId").val();
    newRecordData.userName = $("#createUserFormUserName").val();
    newRecordData.firstName = $("#createUserFormFirstName").val();
    newRecordData.lastName = $("#createUserFormLastName").val();
    newRecordData.email = $("#createUserFormEmail").val();
    newRecordData.role = $("#createUserFormRole").val();
    newRecordData.active = $("#createUserFormActive").is(":checked");

    return newRecordData;
}

var requiredCreateStringProps = {
    userName: {maxLength: 30},
    firstName: {maxLength: 255},
    lastName: {maxLength: 255},
    email: {maxLength: 255},
    role: {maxLength: 60}
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
    $("#createUserDialog").modal("hide");
    var selectedData = getSelectedTableRow();
    var serverData = json.model.user;
    serverData.active = Configs.userActive[serverData.active].value;

    if (serverData) {
        addNewTableRow(serverData);
    }

    showSuccessDialog(json);
}


function initCreateUserForm(){

    $("#createUserButton").click(function () {
        clearCreateUserForm();
    });

    $("#addRecordOkButton").click(function () {
        $('input[type=submit]', this).attr('disabled', 'disabled');
        addRecordCreateUserFormSaveData();
    });


}