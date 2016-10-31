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
        data['code'] = table.row(sel).data().code;
        data['name'] = table.row(sel).data().name;
        data['description'] = table.row(sel).data().description;
        data['projectManager'] = table.row(sel).data().projectManager;
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

function removeProjectAction() {
    var selectedData = getSelectedTableRow();
    if (selectedData && selectedData['id']) {
        var id = selectedData['id'];
        deleteRequest(Configs.removeProjectUrl + "/" + id, function (jsonData) {
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

function initProjectsDataTablePlugin() {
    table = $('#tableProjects').DataTable({
            "paging": true,
            "ordering": true,
            "info": true,
            orderClasses: false,
            autoWidth: false,
            processing: true,
            deferRender: true,
            columns: [
                {data: "id"},
                {data: "code"},
                {data: "name"},
                {data: "description"},
                {data: "projectManager"}
            ],
            "columnDefs" : [{
                "targets" : [ 0 ],
                "visible" : false,
                "searchable": false
            }]
        }
    );
    //single selection mode for tableUser table
    $('#tableProjects tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

    // add double click listener for thr row
    $('#tableProjects tbody').on('dblclick', 'tr', function (e) {
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

    initProjectsDataTablePlugin();
    initCreateProjectForm();
    initUpdateProjectForm();

    $("#removeProjectButton").click(function () {
        if (!getSelectedTableRow()) {
            return false; // prevent confirmation dialog
        }
    });

    $("#removeRecordOkButton").click(function () {
        $('input[type=submit]', this).attr('disabled', 'disabled');
        removeProjectAction();
    });
});