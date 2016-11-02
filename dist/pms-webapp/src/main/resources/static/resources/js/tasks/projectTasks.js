var table = null;
var loadingMessage = "";

function showLoadingDialog(loadingModalAlertBody) {
    $("#loadingModalAlertBody").html(loadingModalAlertBody);
    $("#loadingDialog").modal('show');
}

function hideLoadingDialog() {
    loadingMessage = "";
    $("#loadingModalAlertBody").html("");
    $("#loadingDialog").modal('hide');
}

function getSelectedTableRow() {
    var sel = $('tr.selected');
    var data = {};
    if (sel && table.row(sel).length > 0) {
        data['id'] = table.row(sel).data().id;
        data['project'] = table.row(sel).data().project;
        data['name'] = table.row(sel).data().name;
        data['taskStatus'] = table.row(sel).data().taskStatus;
        data['progress'] = table.row(sel).data().progress;
        data['deadline'] = table.row(sel).data().deadline;
        data['description'] = table.row(sel).data().description;
        data['assignee'] = table.row(sel).data().assignee;
        return data;
    } else {
        return null;
    }
}

function addNewTableRow(serverData) {
    table.row.add(serverData).draw(false);
}

function updateSelectedTableRow(data) {
    var sel = $('tr.selected');
    if (sel) {
        table.row(sel).data(data).draw(false);
    }
}

function removeTaskAction() {
    var selectedData = getSelectedTableRow();
    if (selectedData && selectedData['id']) {
        var id = selectedData['id'];
        deleteRequest(Configs.removeTaskUrl + "/" + id, function (jsonData) {
            removeSelectedTableRow();
            showSuccessDialog(jsonData);
        }, null);
    }
}

function removeSelectedTableRow() {
    var sel = $('tr.selected');
    if (sel) {
        table.row(sel).remove().draw(false);
    }
}

function initProjectTasksDataTablePlugin() {
    table = $('#tableProjectTasks').DataTable({
            "paging": true,
            "ordering": true,
            "info": true,
            orderClasses: false,
            autoWidth: false,
            processing: true,
            deferRender: true,
            columns: [
                {data: "id"},
                {data: "project"},
                {data: "name"},
                {data: "taskStatus"},
                {data: "progress"},
                {data: "deadline"},
                {data: "description"},
                {data: "assignee"}
            ],
            "columnDefs" : [{
                "targets" : [ 0 ],
                "visible" : false,
                "searchable": false
            },{
                "targets" : [ 1 ],
                "visible" : false,
                "searchable": false
            }]
        }
    );
    //single selection mode for tableUser table
    $('#tableProjectTasks tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

    // add double click listener for thr row
    $('#tableProjectTasks tbody').on('dblclick', 'tr', function (e) {
        e.preventDefault();
        if (!$(this).hasClass('selected')) {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }

        var data = getSelectedTableRow();
        fillUpdateFormWithData(data);
    });
}



$(document).ajaxStart(function () {
    showLoadingDialog(loadingMessage);
});

$(document).ajaxComplete(function () {
    hideLoadingDialog();
});

$(document).ready(function () {

    initProjectTasksDataTablePlugin();
    initCreateTaskForm();
    initUpdateTaskForm();

    $("#removeTaskButton").click(function () {
        if (!getSelectedTableRow()) {
            return false; // prevent confirmation dialog
        }
    });

    $("#removeRecordOkButton").click(function () {
        $('input[type=submit]', this).attr('disabled', 'disabled');
        removeTaskAction();
    });
});