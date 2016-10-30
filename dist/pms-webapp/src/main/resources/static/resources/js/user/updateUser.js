function updateUserAction(row, openDialog) {
	var data = row ? table.row(row).data() : getSelectedTableRow();
	if (data) {
		fillUpdateFormWithData(data);
		if (openDialog) {
			$('#updateUserDialog').modal('show');
		}
	} else {
		return false;
	}
}

function fillUpdateFormWithData(editData) {
    $("#updateUserFormId").val(editData.id);
    $("#updateUserFormUserName").val(editData.userName);
    $('#updateUserFormFirstName').val(editData.firstName);
    $('#updateUserFormLastName').val(editData.lastName);
    $('#updateUserFormEmail').val(editData.email);
    $('#updateUserFormRole').val(editData.role);
    $('#updateUserFormActive').prop('checked', editData.active);
}

function updateFormSaveData() {
    var recordData = collectUpdateFromData();
    if (isUpdateDataValid(recordData)) {
        sendPostRequest(Configs.updateUserUrl, recordData, addRecordUpdateSuccessHandler, null);
    }
}

function collectUpdateFromData() {
    var editData = {};
    editData.id = $("#updateUserFormId").val();
    editData.userName = $("#updateUserFormUserName").val();
    editData.firstName = $("#updateUserFormFirstName").val();
    editData.lastName = $("#updateUserFormLastName").val();
    editData.email = $("#updateUserFormEmail").val();
    editData.role = $("#updateUserFormRole").val();
    editData.active = $("#updateUserFormActive").is(":checked");

    return editData;
}

var requiredUpdateStringProps = {
    userName: {maxLength: 30},
    firstName: {maxLength: 255},
    lastName: {maxLength: 255},
    email: {maxLength: 255},
    role: {maxLength: 60}
};

var nonRequiredStringProps = {};

function isUpdateDataValid(recordData) {
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

        if (requiredUpdateStringProps.hasOwnProperty(prop) || nonRequiredStringProps.hasOwnProperty(prop)) {
            var maxLength = 255;

            if (requiredUpdateStringProps.hasOwnProperty(prop)) {
                maxLength = requiredUpdateStringProps[prop].maxLength;
            } else if (nonRequiredStringProps.hasOwnProperty(prop)) {
                maxLength = nonRequiredStringProps[prop].maxLength;
            }
            if (requiredUpdateStringProps.hasOwnProperty(prop) && length == 0) {
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


function addRecordUpdateSuccessHandler(json) {
    $("#updateUserDialog").modal("hide");
    var selectedData = getSelectedTableRow();
    var serverData = json.model.user;
    serverData.active = serverData.active ? 'Yes' : 'No';
    updateSelectedTableRow(serverData);

    showSuccessDialog(json);
}


function initUpdateUserForm(){

    $("#updateUserButton").click(function () {
            return updateUserAction();
      });

     $("#addRecordUpdateOkButton").click(function () {
            $('input[type=submit]', this).attr('disabled', 'disabled');
            updateFormSaveData();
      });
}