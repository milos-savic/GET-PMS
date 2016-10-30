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
        data['userName'] = table.row(sel).data().userName;
        data['firstName'] = table.row(sel).data().firstName;
        data['lastName'] = table.row(sel).data().lastName;
        data['email'] = table.row(sel).data().email;
        data['role'] = table.row(sel).data().role;
        data['active'] = table.row(sel).data().active == 'Yes';
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

function removeUserAction() {
    var selectedData = getSelectedTableRow();
    if (selectedData && selectedData['id']) {
        var id = selectedData['id'];
        deleteRequest(Configs.deleteUserUrl + "/" + id, function (jsonData) {
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

function initUserDataTablePlugin() {
    table = $('#tableUser').DataTable({
            "paging": true,
            "ordering": true,
            "info": true,
            orderClasses: false,
            autoWidth: false,
            processing: true,
            deferRender: true,
            columns: [
                {data: "id"},
                {data: "userName"},
                {data: "firstName"},
                {data: "lastName"},
                {data: "email"},
                {data: "role"},
                {data: "active"},
            ],
            "columnDefs" : [{
                "targets" : [ 0 ],
                "visible" : false,
                "searchable": false
            }]
        }
    );
    //single selection mode for tableUser table
    $('#tableUser tbody').on('click', 'tr', function () {
        if ($(this).hasClass('selected')) {
            $(this).removeClass('selected');
        }
        else {
            table.$('tr.selected').removeClass('selected');
            $(this).addClass('selected');
        }
    });

    // add double click listener for thr row
    $('#tableUser tbody').on('dblclick', 'tr', function (e) {
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

    initUserDataTablePlugin();
    initCreateUserForm();
    initUpdateUserForm();

    $("#removeUserButton").click(function () {
        if (!getSelectedTableRow()) {
            return false; // prevent confirmation dialog
        }
    });

    $("#removeRecordOkButton").click(function () {
        $('input[type=submit]', this).attr('disabled', 'disabled');
        removeUserAction();
    });
});