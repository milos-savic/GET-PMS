function updateTaskAction(row, openDialog) {
	var data = row ? table.row(row).data() : getSelectedTableRow();
	if (data) {
		fillUpdateFormWithData(data);
		if (openDialog) {
			$('#updateTaskDialog').modal('show');
		}
	} else {
		return false;
	}
}

function fillUpdateFormWithData(editData) {
    $("#createTaskFormId").val(editData.id);
    $("#createTaskFormProject").val(editData.project);
    $('#updateProjectFormName').val(editData.name);
    $("#createTaskFormStatus").val(editData.status);
    $("#createTaskFormProgress").val(editData.progress);
    $("#createTaskFormDeadline").val(editData.deadline);
    $('#updateProjectFormDescription').val(editData.description);
    $('#createTaskFormAssignee').val(editData.assignee);
}

function updateFormSaveData() {
    var recordData = collectUpdateFromData();
    if (isUpdateDataValid(recordData)) {
        sendPostRequest(Configs.updateProjectUrl, recordData, addRecordUpdateSuccessHandler, null);
    }
}

function collectUpdateFromData() {
    var editData = {};

    editData.id = $("#createTaskFormId").val();
    editData.project = $("#createTaskFormProject").val();
    editData.name = $("#createTaskFormName").val();
    editData.status = $("#createTaskFormStatus").val();
    editData.progress = $("#createTaskFormProgress").val();
    editData.deadline = $("#createTaskFormDeadline").val();
    editData.description = $("#createTaskFormDescription").val();
    editData.assignee = $("#createTaskFormAssignee").val();

    return editData;
}

var requiredUpdateStringProps = {
   name: {maxLength: 50},
   taskStatus: {maxLength: 30},
   deadline: {maxLength: 30},
   description: {maxLength: 255}
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
    $("#updateTaskDialog").modal("hide");
    var selectedData = getSelectedTableRow();
    var serverData = json.model.task;
    serverData.assignee = serverData.assignee.userName;
    serverData.project = serverData.project.id;

    updateSelectedTableRow(serverData);

    showSuccessDialog(json);
}


function initUpdateTaskForm(){

    $("#updateTaskButton").click(function () {
            return updateTaskAction();
      });

     $("#updateRecordOkButton").click(function () {
            $('input[type=submit]', this).attr('disabled', 'disabled');
            updateFormSaveData();
      });
}