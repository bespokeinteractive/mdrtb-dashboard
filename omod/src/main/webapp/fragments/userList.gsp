<%
    ui.decorateWith("appui", "standardEmrPage", [title: "Manage Users"])

    ui.includeCss("mdrtbregistration", "onepcssgrid.css")
    ui.includeCss("uicommons", "datatables/dataTables_jui.css")

    ui.includeJavascript("mdrtbregistration", "jq.dataTables.min.js")
%>

<script>
//    jq(function () {
//
//    });



</script>

<script>


    var searchTable;
    var searchTableObject;
    var searchResultsData = [];
    var searchHighlightedKeyboardRowIndex;

    var getMdrtbUsers = function () {
        searchTableObject.find('td.dataTables_empty').html('<span><img class="search-spinner" src="' + emr.resourceLink('uicommons', 'images/spinner.gif') + '" /></span>');
        jq.getJSON(emr.fragmentActionLink("mdrtbdashboard", "userList", "getAuthenticatedUsers"))
            .success(function (data) {
                updateSearchResults(data);
            }).error(function (xhr, status, err) {
                updateSearchResults([]);
            }
        );
    };

    var passwordStrenth = function (pass) {
        var strength = 1;
        var arr = [/.{5,}/, /[a-z]+/, /[0-9]+/, /[A-Z]+/];
        jQuery.map(arr, function (regexp) {
            if (pass.match(regexp))
                strength++;
        });

        return strength;
    }

    var updateSearchResults = function (results) {
        searchResultsData = results || [];
        var dataRows = [];
        _.each(searchResultsData, function (result) {
            var names = '<a href="users.page?userId=' + result.user.userId + '">' + result.wrapperNames + '</a>';
            var icons = '<a title="Edit User" class="edit-user" data-idnt=' + result.user.userId + '><i class="icon-edit small"></i></a> <a title="Reset Password" class="reset-password" data-idnt=' + result.user.userId + '><i class="icon-lock small"></i></a>';

            dataRows.push([0, result.user.systemId, names, result.user.username, result.wrapperLocations, icons]);
        });

        searchTable.api().clear();

        if (dataRows.length > 0) {
            searchTable.fnAddData(dataRows);
        }

        refreshInTable(searchResultsData, searchTable);
    };

    var refreshInTable = function (resultData, dTable) {
        var rowCount = resultData.length;
        if (rowCount == 0) {
            dTable.find('td.dataTables_empty').html("No Users Found");
        }
        dTable.fnPageChange(0);
    };

    var isTableEmpty = function (resultData, dTable) {
        if (resultData.length > 0) {
            return false
        }
        return !dTable || dTable.fnGetNodes().length == 0;
    };

    jq(function () {
        searchTableObject = jq("#searchList");
        var tabs = jq(".mdrtb-tabs").tabs();
        searchTable = searchTableObject.dataTable({
            bAutoWidth: false,
            bFilter: true,
            bJQueryUI: true,
            bLengthChange: false,
            iDisplayLength: 25,
            sPaginationType: "full_numbers",
            bSort: false,
            sDom: 't<"fg-toolbar ui-toolbar ui-corner-bl ui-corner-br ui-helper-clearfix datatables-info-and-pg"ip>',
            oLanguage: {
                "sInfo": "_TOTAL_ User(s) Found",
                "sInfoEmpty": " ",
                "sZeroRecords": "No Users Found",
                "sInfoFiltered": "(Showing _TOTAL_ of _MAX_ Users)",
                "oPaginate": {
                    "sFirst": "First",
                    "sPrevious": "Previous",
                    "sNext": "Next",
                    "sLast": "Last"
                }
            },

            fnDrawCallback: function (oSettings) {
                if (isTableEmpty(searchResultsData, searchTable)) {
                    //this should ensure that nothing happens when the use clicks the
                    //row that contain the text that says 'No data available in table'
                    return;
                }

                if (searchHighlightedKeyboardRowIndex != undefined && !isHighlightedRowOnVisiblePage()) {
                    unHighlightRow(searchTable.fnGetNodes(searchHighlightedKeyboardRowIndex));
                }
            },

            fnRowCallback: function (nRow, aData, index) {
                return nRow;
            }
        });

        searchTable.on('order.dt search.dt', function () {
            searchTable.api().column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
                cell.innerHTML = i + 1;
            });
        }).api().draw();

        //End of DataTables

        jq('#advanced').on('click', function () {
            jq.getJSON('${ ui.actionLink("mdrtbdashboard", "userList", "getAllLocations") }')
                .success(function (data) {
                    jq('#addNames').val('');
                    jq('#addGender').val('');
                    jq('#addUsername').val('');
                    jq('#addPassword').val('');
                    jq('#addConfirm').val('');

                    updateLocationList(data.location, jq('#addLocations'))


                    addDialog.show();
                });
        });

        jq('#searchList').on('click', '.edit-user', function () {
            var idnt = jq(this).data('idnt');
            jq.getJSON('${ ui.actionLink("mdrtbdashboard", "userList", "getUserDetails") }', {
                userId: idnt
            }).success(function (data) {
                jq('#editUserId').val(idnt);
                jq('#editNames').val(data.user.names);
                jq('#editGender').val(data.user.gender);
                jq('#editSystemId').val(data.user.systemId);
                jq('#editUsername').val(data.user.username);

                updateLocationList(data.location, jq('#editLocations'));
                updateRoles(data.role, jq('#editRoles'));


                editDialog.show();
            });
        });

        jq('#searchList').on('click', '.reset-password', function () {
            var idnt = jq(this).data('idnt');
            jq.getJSON('${ ui.actionLink("mdrtbdashboard", "userList", "getUserDetails") }', {
                userId: idnt
            }).success(function (data) {
                jq('#passUserId').val(idnt);
                jq('#passNames').val(data.user.names);
                jq('#passUsername').val(data.user.username);
                jq('#passPassword').val('');
                jq('#passConfirm').val('');

                passwordDialog.show();
            });
        });

        var updateRoles = function (results, ulselect) {
            searchResultsData = results || [];

            ulselect.empty();

            _.each(searchResultsData, function (item) {
                var checked = '';
               var name = item.name;
                var name2 = 'Access All';

                if (item.checked) {
                    checked = 'checked';
            }

                var row = '<label class="user-locations" id="role.' + item.name + '"><input type="checkbox" name="role.' + item.name + '" ' + checked + ' />' + item.name + '</label>';
                ulselect.append(row);

            });
        }


        var updateLocationList = function (results, ulselect) {
            searchResultsData = results || [];

            ulselect.empty();

            _.each(searchResultsData, function (item) {
                var checked = '';

                if (item.checked) {
                    checked = 'checked';
                }

                var row = '<label class="user-locations" id="' + item.id + '"><input type="checkbox"  name="location.' + item.id + '" ' + checked + ' />' + item.name + '</label>';
                ulselect.append(row);

            });
        }


        var addDialog = emr.setupConfirmationDialog({
            dialogOpts: {
                overlayClose: false,
                close: true
            },
            selector: '#add-dialog',
            actions: {
                confirm: function () {
                    var dataString = jq('#add-form').serialize();

                    if (jq('#addNames').val().split(' ').length == 1) {
                        jq().toastmessage('showErrorToast', 'Kindly provide atleast two names');
                        return false;
                    }

                    if (jq('#addGender').val() == "") {
                        jq().toastmessage('showErrorToast', 'Kindly specify the gender of the user');
                        return false;
                    }

                    if (jq('#addUsername').val() == "") {
                        jq().toastmessage('showErrorToast', 'Kindly specify the Password for the user');
                        return false;
                    }

                    if (jq('#addPassword').val() == "") {
                        jq().toastmessage('showErrorToast', 'Kindly specify the Password for the user');
                        return false;
                    }

                    if (jq('#addPassword').val() !== jq('#addConfirm').val()) {
                        jq().toastmessage('showErrorToast', 'Passwords do not match');
                        return false;
                    }

                    if (passwordStrenth(jq('#addPassword').val()) != 5) {
                        jq().toastmessage('showErrorToast', 'Passwords must be a combination Capital letters, Small letters and Numbers');
                        return false;
                    }

                    jq.ajax({
                        type: "POST",
                        url: '${ui.actionLink("mdrtbdashboard", "userList", "addUserDetails")}',
                        data: dataString,
                        dataType: "json",
                        success: function (data) {
                            if (data.status == "success") {
                                jq().toastmessage('showSuccessToast', data.message);
                                window.location.href = "users.page";
                            }
                            else {
                                jq().toastmessage('showErrorToast', 'x:' + data.message);
                            }
                        },
                        error: function (data) {
                            jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
                        }
                    });


                    editDialog.close();
                },
                cancel: function () {
                    editDialog.close();
                }
            }
        });

        var editDialog = emr.setupConfirmationDialog({
            dialogOpts: {
                overlayClose: false,
                close: true
            },
            selector: '#edit-dialog',
            actions: {
                confirm: function () {
                    var dataString = jq('#edit-form').serialize();

                    if (jq('#editNames').val().split(' ').length == 1) {
                        jq().toastmessage('showErrorToast', 'Kindly provide atleast two names');
                        return false;
                    }

                    if (jq('#editGender').val() == "") {
                        jq().toastmessage('showErrorToast', 'Kindly specify the gender of the user');
                        return false;
                    }

                    jq.ajax({
                        type: "POST",
                        url: '${ui.actionLink("mdrtbdashboard", "userList", "updateUserDetails")}',
                        data: dataString,
                        dataType: "json",
                        success: function (data) {
                            if (data.status == "success") {
                                jq().toastmessage('showSuccessToast', data.message);
                                window.location.href = "users.page";
                            }
                            else {
                                jq().toastmessage('showErrorToast', 'x:' + data.message);
                            }
                        },
                        error: function (data) {
                            jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
                        }
                    });


                    editDialog.close();
                },
                cancel: function () {
                    editDialog.close();
                }
            }
        });

        var passwordDialog = emr.setupConfirmationDialog({
            dialogOpts: {
                overlayClose: false,
                close: true
            },
            selector: '#password-dialog',
            actions: {
                confirm: function () {
                    var dataString = jq('#password-form').serialize();

                    if (jq('#passUsername').val() == "") {
                        jq().toastmessage('showErrorToast', 'Kindly specify the Password for the user');
                        return false;
                    }

                    if (jq('#passPassword').val() == "") {
                        jq().toastmessage('showErrorToast', 'Kindly specify the Password for the user');
                        return false;
                    }

                    if (jq('#passPassword').val() !== jq('#passConfirm').val()) {
                        jq().toastmessage('showErrorToast', 'Passwords do not match');
                        return false;
                    }

                    if (passwordStrenth(jq('#passPassword').val()) != 5) {
                        jq().toastmessage('showErrorToast', 'Passwords must be a combination Capital letters, Small letters and Numbers');
                        return false;
                    }

                    jq.ajax({
                        type: "POST",
                        url: '${ui.actionLink("mdrtbdashboard", "userList", "changePasswordDetails")}',
                        data: dataString,
                        dataType: "json",
                        success: function (data) {
                            if (data.status == "success") {
                                jq().toastmessage('showSuccessToast', data.message);
                                passwordDialog.close();
                            }
                            else {
                                jq().toastmessage('showErrorToast', 'x:' + data.message);
                            }
                        },
                        error: function (data) {
                            jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
                        }
                    });


                },
                cancel: function () {
                    passwordDialog.close();
                }
            }
        });

        jq('#searchPhrase').on('keyup', function () {
            searchTable.api().search(this.value).draw();
        });

        getMdrtbUsers();

    });

    jq(document).ready(function(){
        jq('.check:button').toggle(function(){
            jq('input:checkbox').attr('checked','checked');
            jq(this).val('uncheck all')
        },function(){
            jq('input:checkbox').removeAttr('checked');
            jq(this).val('check all');
        })
    })

</script>

<style>

#breadcrumbs a, #breadcrumbs a:link, #breadcrumbs a:visited {
    text-decoration: none;
}

body {
    margin-top: 20px;
}

form input {
    margin: 0px;
    display: inline-block;
    min-width: 50px;
    padding: 2px 10px;
}

.info-header span {
    cursor: pointer;
    display: inline-block;
    float: right;
    margin-top: -2px;
    padding-right: 5px;
}

.dashboard .info-section {
    margin: 2px 5px 5px;
}

.toast-item {
    background-color: #222;
}

form input:focus, form select:focus, form textarea:focus, form ul.select:focus, .form input:focus, .form select:focus, .form textarea:focus, .form ul.select:focus {
    outline: 1px none #007fff;
    box-shadow: 0 0 1px 0px #888 !important;
}

.name {
    color: #f26522;
}

@media all and (max-width: 768px) {
    .onerow {
        margin: 0 0 100px;
    }
}

form .advanced {
    background: #363463 none repeat scroll 0 0;
    border-color: #dddddd;
    border-style: solid;
    border-width: 1px;
    color: #fff;
    cursor: pointer;
    float: right;
    padding: 5px 0;
    text-align: center;
    width: 18%;
}

form .advanced i {
    font-size: 24px;
}

.add-on {
    float: right;
    left: auto;
    margin-left: -29px;
    margin-top: 5px;
    position: absolute;
    color: #f26522;
}

.ui-widget-content a {
    color: #007fff;
}

td a {
    cursor: pointer;
    text-decoration: none;
}

td a:hover {
    text-decoration: none;
}

.recent-seen {
    background: #fff799 none repeat scroll 0 0 !important;
    color: #000 !important;
}

.recent-lozenge {
    border: 1px solid #f00;
    border-radius: 4px;
    color: #f00;
    display: inline-block;
    font-size: 0.7em;
    padding: 1px 2px;
    vertical-align: text-bottom;
}

table th, table td {
    vertical-align: baseline;
    white-space: nowrap;
}

table th:nth-child(5),
table td:nth-child(5) {
    white-space: normal;
}

.dialog-content ul li input[type="text"],
.dialog-content ul li input[type="password"],
.dialog-content ul li select,
.dialog-content ul li textarea {
    border: 1px solid #ddd;
    display: inline-block;
    height: 40px;
    margin: 1px 0;
    min-width: 20%;
    padding: 5px 0 5px 10px;
    width: 68%;
}

#modal-overlay {
    background: #000 none repeat scroll 0 0;
    opacity: 0.3 !important;
}

form label,
.form label {
    display: inline-block;
    width: 110px;
}

.dialog select option {
    font-size: 1em;
}

#editLocations,
#addLocations {
    border-top: 1px solid #ddd;
    margin-top: 3px;
    padding-left: 0px;
}

label.user-locations {
    margin-top: 4px;
    width: 33%;
}

.dialog .dialog-content li {
    margin-bottom: 0;
}

.dialog ul {
    margin-bottom: 10px;
}

label.user-locations input {
    margin-top: 4px;
}

#overview {
    min-height: 450px;
    padding: 5px;
}

.title-answer {
    font-family: "Myriad Pro", Arial, Helvetica, Tahoma, sans-serif;
    font-size: 13px;
}

#whv {
    display: inline-block;
    width: 100%;
}


</style>

<div class="clear"></div>

<div class="container">
    <div class="example">
        <ul id="breadcrumbs">
            <li>
                <a href="${ui.pageLink('referenceapplication', 'home')}">
                    <i class="icon-home small"></i></a>
            </li>
            <li>
                <i class="icon-chevron-right link"></i>
                <a href="#">Manage Users</a>
            </li>
            <li>
            </li>
        </ul>
    </div>

    <div class="patient-header new-patient-header">
        <div class="demographics">
            <h1 class="name" style="border-bottom: 1px solid #ddd;">
                <span><i class="icon-group small"></i> SYSTEM USERS &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </h1>
            <br/>
        </div>

        <div class="onepcssgrid-1000">
            <br/><br/>

            <form onsubmit="return false" id="users-search-form" method="get" style="margin: 0px;">
                <input type="text" autocomplete="off" placeholder="Filter Users" id="searchPhrase"
                       style="float:left; width:80%; padding:6px 10px 7px;"/>

                <div id="advanced" class="advanced"><i class="icon-user"></i>ADD USERS</div>
            </form>

            <div id="userList" style="display: block; margin-top:3px;">
                <table id="searchList">
                    <thead>
                    <th>#</th>
                    <th>IDENTIFIER</th>
                    <th>NAMES</th>
                    <th>USERNAME</th>
                    <th>LOCATIONS</th>
                    <th style="width:1px; align: center">ACTIONS</th>
                    </thead>

                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<div id="add-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>

        <h3>ADD USER</h3>
    </div>

    <div class="dialog-content">
        <form id="add-form">
            <ul>
                <li>
                    <label for="addNames">FULL NAMES:</label>
                    <input type="text" name="wrap.names" id="addNames" placeholder="Full Names"/>
                </li>

                <li>
                    <label for="addGender">GENDER:</label>
                    <select type="text" name="wrap.gender" id="addGender">
                        <option value=""></option>
                        <option value="M">Male</option>
                        <option value="F">Female</option>
                    </select>
                </li>

                <li>
                    <label for="editNames">USERNAME:</label>
                    <input type="text" name="wrap.username" id="addUsername" placeholder="Username"/>
                </li>

                <li>
                    <label for="editNames">PASSWORD:</label>
                    <input type="password" name="wrap.password" id="addPassword" placeholder="Password"/>
                </li>

                <li>
                    <label for="editNames">CONFIRM:</label>
                    <input type="password" name="wrap.confirm" id="addConfirm" placeholder="Password"/>
                </li>

                <li id="addLocations">

                </li>
            </ul>
        </form>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>

<div id="edit-dialog" class="dialog" style="display:none; width: 650px">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>

        <h3>EDIT USER</h3>
    </div>

    <div class="dialog-content">
        <form id="edit-form">
            <ul>
                <li>
                    <label for="editNames">FULL NAMES:</label>
                    <input type="text" name="wrap.names" id="editNames" placeholder="Full Names"/>
                    <input type="hidden" name="wrap.user" id="editUserId"/>
                </li>

                <li>
                    <label for="editGender">GENDER:</label>
                    <select type="text" name="wrap.gender" id="editGender">
                        <option value=""></option>
                        <option value="M">Male</option>
                        <option value="F">Female</option>
                    </select>
                </li>

                <li>
                    <label for="editNames">SYSTEM ID:</label>
                    <input type="text" name="wrap.systemId" id="editSystemId" readonly=""/>
                </li>

                <li>
                    <label for="editNames">USERNAME:</label>
                    <input type="text" name="wrap.username" id="editUsername" placeholder="Username"/>
                </li>


                <div class="mdrtb-tabs">
                    <ul>
                        <li id="cn"><a href="#overview">Locations</a></li>
                        <li id="ti"><a href="#userRoles">Roles</a></li>

                    </ul>

                    <div id="overview">

                        <li id="editLocations">

                        </li>
                        <input type="button" class="check" value="check all" />
                    </div>

                    <div id="userRoles">

                        <li id="editRoles">
                        </li>

                    </div>
                </div>

            </ul>
        </form>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>

<div id="password-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>

        <h3>CHANGE PASSWORD</h3>
    </div>

    <div class="dialog-content">
        <form id="password-form">
            <ul>
                <li>
                    <label for="addNames">FULL NAMES:</label>
                    <input type="text" name="wrap.names" id="passNames" placeholder="Full Names" readonly=""/>
                    <input type="hidden" name="wrap.user" id="passUserId"/>
                </li>

                <li>
                    <label for="editNames">USERNAME:</label>
                    <input type="text" name="wrap.username" id="passUsername" placeholder="Username"/>
                </li>

                <li>
                    <label for="editNames">PASSWORD:</label>
                    <input type="password" name="wrap.password" id="passPassword" placeholder="Password"/>
                </li>

                <li>
                    <label for="editNames">CONFIRM:</label>
                    <input type="password" name="wrap.confirm" id="passConfirm" placeholder="Password"/>
                </li>
            </ul>
        </form>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>