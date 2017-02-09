function updateProjectAction(row, openDialog) {
	var data = row ? table.row(row).data() : getSelectedTableRow();
	if (data) {
		fillUpdateFormWithData(data);
		if (openDialog) {
			$('#updateProjectDialog').modal('show');
		}
	} else {
		return false;
	}
}

function fillUpdateFormWithData(editData) {
    $("#updateProjectFormId").val(editData.id);
    $("#updateProjectFormCode").val(editData.code);
    $('#updateProjectFormName').val(editData.name);
    $('#updateProjectFormDescription').val(editData.description);
    $('#updateProjectFormPM').val(editData.projectManager);
}

function updateFormSaveData() {
    var recordData = collectUpdateFromData();
    if (isUpdateDataValid(recordData)) {
        sendPostRequest(Configs.updateProjectUrl, recordData, addRecordUpdateSuccessHandler, null);
    }
}

function collectUpdateFromData() {
    var editData = {};
    editData.id = $("#updateProjectFormId").val();
    editData.code = $("#updateProjectFormCode").val();
    editData.name = $("#updateProjectFormName").val();
    editData.description = $("#updateProjectFormDescription").val();
    editData.projectManager = $("#updateProjectFormPM").val();

    return editData;
}

var requiredUpdateStringProps = {
   code: {maxLength: 30},
   name: {maxLength: 50},
   description: {maxLength: 255},
   projectManager: {maxLength: 30}
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
    $("#updateProjectDialog").modal("hide");
    var selectedData = getSelectedTableRow();
    var serverData = json.model.project;
    serverData.projectManager = serverData.projectManager.userName;

    updateSelectedTableRow(serverData);

    showSuccessDialog(json);
}


function initUpdateProjectForm(){

    $("#updateProjectButton").click(function () {
            return updateProjectAction();
      });

     $("#updateRecordOkButton").click(function () {
            $('input[type=submit]', this).attr('disabled', 'disabled');
            updateFormSaveData();
      });
}