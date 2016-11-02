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
    $("#updateTaskFormId").val(editData.id);
    $("#updateTaskFormProject").val(editData.project);
    $('#updateTaskFormName').val(editData.name);
    $("#updateTaskFormStatus").val(editData.taskStatus);
    $("#updateTaskFormProgress").val(editData.progress);
    $("#updateTaskFormDeadline").val(editData.deadline);
    $('#updateTaskFormDescription').val(editData.description);
    $('#updateTaskFormAssignee').val(editData.assignee);
}

function updateFormSaveData() {
    var recordData = collectUpdateFromData();
    if (isUpdateDataValid(recordData)) {
        sendPostRequest(Configs.updateTaskUrl, recordData, addRecordUpdateSuccessHandler, null);
    }
}

function collectUpdateFromData() {
    var editData = {};

    editData.id = $("#updateTaskFormId").val();
    editData.project = $("#updateTaskFormProject").val();
    editData.name = $("#updateTaskFormName").val();
    editData.taskStatus = $("#updateTaskFormStatus").val();
    editData.progress = $("#updateTaskFormProgress").val();
    editData.deadline = $("#updateTaskFormDeadline").val();
    editData.description = $("#updateTaskFormDescription").val();
    editData.assignee = $("#updateTaskFormAssignee").val();

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

    /*
    Check https://github.com/xdan/datetimepicker/blob/master/build/jquery.datetimepicker.full.js for more options
    var default_options = {...} object contains default datetimepicker options that can be overridden
    */
    $.datetimepicker.setLocale('en');
    $('#updateTaskFormDeadline').datetimepicker({
        yearStart: 1999,
        yearEnd: 2050,
        format: Configs.datePickerFormat,
        dayOfWeekStart: 1,
        lang: 'en'
    });

    $("#updateTaskButton").click(function () {
            return updateTaskAction();
      });

     $("#updateRecordOkButton").click(function () {
            $('input[type=submit]', this).attr('disabled', 'disabled');
            updateFormSaveData();
      });
}