var sendPostJsonRequest = function sendPostJsonRequest(url, data, successHandler, failHandler) {
    var ajaxCallParameters = {
        type: "POST",
        url: url,
        contentType: "application/json",
        dataType: "json"
    };
    if (data != null) {
        ajaxCallParameters.data = JSON.stringify(data);
    }
    $.ajax(ajaxCallParameters)
        .done(function (json) {
            if (json.success) {
                successHandler(json);
            } else {
                if (failHandler != null) {
                    failHandler(json);
                }
                var msg = extractTypeMessages(json, "ERROR");
                showModalDialog(msg);
            }
        })
        .fail(function (errorObject, textStatus, errorThrown) {
            ajaxError(errorObject, textStatus, errorThrown);
        });
}

/**
 * Function sends POST request to server
 * successHandler can be either function or object like this:
 * successHandler = {functionObject: functionObject, additionalParameters: additionalParameters}
 *
 * @param url
 * @param data
 * @param successHandler
 * @param failHandler
 */
var sendPostRequest = function sendPostRequest(url, data, successHandler, failHandler) {
    var ajaxCallParameters = {
        type: "POST",
        url: url,
        dataType: "json",
        timeout: Configs.serverTimeout
    };
    if (data != null) {
        ajaxCallParameters.data = data;
    }
    $.ajax(ajaxCallParameters)
        .done(function (json) {
            if (json.success) {
                if (successHandler != null) {
                    if (typeof successHandler == 'function') {
                        successHandler(json);
                    } else if (typeof successHandler == 'object') {
                        if (successHandler.functionObject != null && typeof successHandler.functionObject == 'function') {
                            successHandler.functionObject(json, successHandler.additionalParameters);
                        }
                    }
                }
            } else {
                if (failHandler != null) {
                    failHandler(json);
                    return;
                }
                var msg = extractTypeMessages(json, "ERROR");
                if (!msg) {
                    msg = extractFieldErrorMessages(json);
                }
                showModalDialog(msg);
            }
        })
        .fail(function (errorObject, textStatus, errorThrown) {
            ajaxError(errorObject, textStatus, errorThrown);
        });
}

var deleteRequest = function deleteRequest(url, successHandler, failHandler) {
    var ajaxCallParameters = {
        type: "DELETE",
        url: url,
        dataType: "json",
        timeout: Configs.serverTimeout
    };
    $.ajax(ajaxCallParameters)
        .done(function (json) {
            if (json.success) {
                if (successHandler != null) {
                    if (typeof successHandler == 'function') {
                        successHandler(json);
                    } else if (typeof successHandler == 'object') {
                        if (successHandler.functionObject != null && typeof successHandler.functionObject == 'function') {
                            successHandler.functionObject(json, successHandler.additionalParameters);
                        }
                    }
                }
            } else {
                if (failHandler != null) {
                    failHandler(json);
                    return;
                }
                var msg = extractTypeMessages(json, "ERROR");
                if (!msg) {
                    msg = extractFieldErrorMessages(json);
                }
                showModalDialog(msg);
            }
        })
        .fail(function (errorObject, textStatus, errorThrown) {
            ajaxError(errorObject, textStatus, errorThrown);
        });
}

function ajaxError(errorObject, textStatus, errorThrown) {
    if (errorObject.status === 0) {
        showModalDialog(errorObject.statusText + ' ' + errorThrown.message);
        return; // swallow errors caused by user clicking away before AJAX call is complete
    } else if (errorObject.status === 401) { //UNAUTHORIZED
        // show message
        showModalDialog(errorObject.responseText ? errorObject.responseText : errorObject.response);
        // and redirect in 2 secs
        setTimeout(function () {
            window.location.href = '/';
        }, 2000);
    } else if (errorThrown.message) {
        console.error("Error code = " + errorObject.status + "\n" + "Error message: " + errorThrown.message)
        showModalDialog(errorThrown.message);
    } else {
        console.error("Error code = " + errorObject.status + "\n" + "Error message: " + errorThrown)
        showModalDialog(errorThrown);
    }
}

function extractTypeMessages(jsonData, msgType) {
    var item = 0;
    var msg = "";
    while (item < jsonData.messages.length) {
        if (jsonData.messages[item].type == msgType) {
            msg += jsonData.messages[item].message;
            msg += "<br />";
        }
        item++;
    }
    return msg;
}

function extractFieldErrorMessages(jsonData) {
    var item = 0;
    var msg = "";
    while (item < jsonData.fieldErrors.length) {
        msg += jsonData.fieldErrors[item].message;
        msg += "<br />";
        item++;
    }
    return msg;
}

function showSuccessDialog(jsonData) {
    var msg = extractTypeMessages(jsonData, "SUCCESS");
    showModalDialog(msg);
}

function showModalDialog(modalAlertBody) {
    $("#modalAlertDetailsPanel").hide();
    $("#modalAlertBody").html(modalAlertBody);
    $("#messageDialog").modal('show');
}

function isModalDialogShown() {
    return $("#messageDialog").data('bs.modal').isShown;
}

function showModalDialogWithDetails(body, details) {
    $("#modalAlertBody").html(body);
    $("#details").removeClass("in");
    $("#modalAlertDetailsPanel").show();
    $("#modalAlertDetails").html(details);
    $("#messageDialog").modal('show');
}

// add support for IE, Chrome for hiding/showing select options!!!
function hideElements(selector) {
    $(selector).each(function (index, val) {
        if ($(this).is('option') && (!$(this).parent().is('span')))
            $(this).wrap((navigator.userAgent.toLowerCase().indexOf('firefox') > -1) ? null : '<span>').hide();
    })
}

/**
 * Check if 2 string arrays have intersection.
 */
function hasIntersection(arr1, arr2) {
    for (var i = 0; i < arr1.length; i++) {
        if (arr2.indexOf(arr1[i]) !== -1) {
            return true;
        }
    }
    return false;
}
