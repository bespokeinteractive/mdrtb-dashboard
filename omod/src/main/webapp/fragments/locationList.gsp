<%
    ui.decorateWith("appui", "standardEmrPage", [title: "Manage Locations"])
	
	ui.includeCss("mdrtbregistration", "onepcssgrid.css")
	ui.includeCss("uicommons", "datatables/dataTables_jui.css")
	
    ui.includeJavascript("mdrtbregistration", "jq.dataTables.min.js")
%>

<script>
	var searchTable;
	var searchTableObject;
	var searchResultsData = [];
	var searchHighlightedKeyboardRowIndex;
	
	var getMdrtbLocations = function(){
		searchTableObject.find('td.dataTables_empty').html('<span><img class="search-spinner" src="'+emr.resourceLink('uicommons', 'images/spinner.gif')+'" /></span>');		
		jq.getJSON(emr.fragmentActionLink("mdrtbdashboard", "LocationList", "getMdrtbLocations"))
			.success(function (data) {
				updateSearchResults(data);
			}).error(function (xhr, status, err) {
				updateSearchResults([]);
			}
		);
	};
	
	var updateSearchResults = function(results){
		searchResultsData = results || [];
		var dataRows = [];
		_.each(searchResultsData, function(result){
			var names = '<a href="locations.page?locationId=' + result.location.id + '">' + result.location.name + '</a>';
			var icons = '<a title="Edit Location" class="edit-location" data-idnt=' + result.location.id + '><i class="icon-edit small"></i>EDIT</a>';
				
			dataRows.push([0, result.serialNumber, names, result.region.name, result.agency.name, icons]);
		});

		searchTable.api().clear();
		
		if(dataRows.length > 0) {
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
				"sInfo": "_TOTAL_ Location(s) Found",
				"sInfoEmpty": " ",
				"sZeroRecords": "No Locations Found",
				"sInfoFiltered": "(Showing _TOTAL_ of _MAX_ Locations)",
				"oPaginate": {
					"sFirst": "First",
					"sPrevious": "Previous",
					"sNext": "Next",
					"sLast": "Last"
				}
			},

			fnDrawCallback : function(oSettings){
				if(isTableEmpty(searchResultsData, searchTable)){
					//this should ensure that nothing happens when the use clicks the
					//row that contain the text that says 'No data available in table'
					return;
				}

				if(searchHighlightedKeyboardRowIndex != undefined && !isHighlightedRowOnVisiblePage()){
					unHighlightRow(searchTable.fnGetNodes(searchHighlightedKeyboardRowIndex));
				}
			},
			
			fnRowCallback : function (nRow, aData, index){
				return nRow;
			}
		});
		
		searchTable.on( 'order.dt search.dt', function () {
			searchTable.api().column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
				cell.innerHTML = i+1;
			} );
		}).api().draw();
		
		jq('#searchPhrase').on('keyup', function(){
			searchTable.api().search(this.value).draw();
		});
		
		// End of Data Tables
		
		jq('#advanced').on('click', function(){			
			addDialog.show();
		});
		
		jq('#searchList').on('click', '.edit-location', function(){
			var idnt = jq(this).data('idnt');			
			jq.getJSON('${ ui.actionLink("mdrtbdashboard", "LocationList", "getLocationDetails") }', {
				locationId : idnt
			}).success(function (data) {
				jq('#locationId').val(idnt);
				jq('#editNames').val(data.location.names);
				jq('#editSerial').val(data.location.serial);
				jq('#editAgency').val(data.location.agency);
				jq('#editRegion').val(data.location.region);
			
				editDialog.show();
			});
		});
		
		var addDialog = emr.setupConfirmationDialog({
			dialogOpts: {
				overlayClose: false,
				close: true
			},
			selector: '#add-dialog',
			actions: {
				confirm: function() {
					if (jq('#addNames').val() == ""){
						jq().toastmessage('showErrorToast', 'Kindly specify Location Name');						
						return false;
					}
					
					if (jq('#addSerial').val() == ""){
						jq().toastmessage('showErrorToast', 'Kindly specify Location Serial');						
						return false;
					}
					
					if (jq('#addAgency').val() == ""){
						jq().toastmessage('showErrorToast', 'Kindly specify the Agency');						
						return false;
					}
					
					if (jq('#addRegion').val() == ""){
						jq().toastmessage('showErrorToast', 'Kindly specify the Region');						
						return false;
					}
					
					jq.ajax({
						type: "POST",
						url: '${ui.actionLink("mdrtbdashboard", "LocationList", "addLocationDetails")}',
						data: ({
							names:	jq('#addNames').val(),
							serial:	jq('#addSerial').val(),
							agency:	jq('#addAgency').val(),
							region:	jq('#addRegion').val(),
						}),
						dataType: "json",
						success: function(data) {
							if (data.status == "success"){
								jq().toastmessage('showSuccessToast', data.message);
								window.location.href = "locations.page";					
							}
							else {
								jq().toastmessage('showErrorToast', 'x:'+ data.message);
							}							
						},
						error: function(data){
							jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
						}
					});
				},
				cancel: function() {
					addDialog.close();
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
				confirm: function() {
					if (jq('#editNames').val() == ""){
						jq().toastmessage('showErrorToast', 'Kindly specify Location Name');						
						return false;
					}
					
					if (jq('#editSerial').val() == ""){
						jq().toastmessage('showErrorToast', 'Kindly specify Location Serial');						
						return false;
					}
					
					if (jq('#editAgency').val() == ""){
						jq().toastmessage('showErrorToast', 'Kindly specify the Agency');						
						return false;
					}
					
					if (jq('#aeditRegion').val() == ""){
						jq().toastmessage('showErrorToast', 'Kindly specify the Region');						
						return false;
					}
					
					jq.ajax({
						type: "POST",
						url: '${ui.actionLink("mdrtbdashboard", "LocationList", "updateLocationDetails")}',
						data: ({
							location:	jq('#locationId').val(),
							names:		jq('#editNames').val(),
							serial:		jq('#editSerial').val(),
							agency:		jq('#editAgency').val(),
							region:		jq('#editRegion').val(),
						}),
						dataType: "json",
						success: function(data) {
							if (data.status == "success"){
								jq().toastmessage('showSuccessToast', data.message);
								window.location.href = "locations.page";					
							}
							else {
								jq().toastmessage('showErrorToast', 'x:'+ data.message);
							}							
						},
						error: function(data){
							jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
						}
					});
				},
				cancel: function() {
					editDialog.close();
				}
			}
		});
		
		getMdrtbLocations();
	});
</script>


<style>
	#breadcrumbs a, #breadcrumbs a:link, #breadcrumbs a:visited {
		text-decoration: none;
	}
	body {
		margin-top: 20px;
	}
	form input{
		margin: 0px;
		display: inline-block;
		min-width: 50px;
		padding: 2px 10px;
	}
	.info-header span{
		cursor: pointer;
		display: inline-block;
		float: right;
		margin-top: -2px;
		padding-right: 5px;
	}
	.dashboard .info-section {
		margin: 2px 5px 5px;
	}
	.toast-item{
		background-color: #222;
	}
	form input:focus, form select:focus, form textarea:focus, form ul.select:focus, .form input:focus, .form select:focus, .form textarea:focus, .form ul.select:focus{
		outline: 1px none #007fff;
		box-shadow: 0 0 1px 0px #888!important;
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
	form .advanced i{
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
	td a{
		cursor: pointer;
		text-decoration: none;
	}
	td a:hover{
		text-decoration: none;
	}
	.recent-seen{
		background: #fff799 none repeat scroll 0 0!important;
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
		white-space: nowrap;
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
	.dialog .dialog-content li {
		margin-bottom: 0;
	}
	.dialog ul {
		margin-bottom: 10px;
	}
	label.user-locations input{
		margin-top: 4px;
	}
</style>

<div class="clear"></div>
<div class="container">
    <div class="example">
        <ul id="breadcrumbs">
            <li>
                <a href="${ui.pageLink('referenceapplication','home')}">
                    <i class="icon-home small"></i></a>
            </li>
            <li>
                <i class="icon-chevron-right link"></i>
                <a href="#">Manage Locations</a>
            </li>
            <li>
            </li>
        </ul>
    </div>
	
    <div class="patient-header new-patient-header">
        <div class="demographics">
            <h1 class="name" style="border-bottom: 1px solid #ddd;">
                <span><i class="icon-map-marker small"></i> MANAGE LOCATIONS &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </h1>
            <br/>
        </div>
		
        <div class="onepcssgrid-1000">
            <br/><br/>
			
			<form onsubmit="return false" id="locations-search-form" method="get" style="margin: 0px;">
				<input type="text" autocomplete="off" placeholder="Filter Locations" id="searchPhrase" style="float:left; width:80%; padding:6px 10px 7px;" />
				<div id="advanced" class="advanced"><i class="icon-location"></i>ADD LOCATIONS</div>
			</form>
			
			<div id="locationList" style="display: block; margin-top:3px;">
				<table id="searchList">
					<thead>
						<th style="width:1px;">#</th>
						<th>SERIAL</th>
						<th>NAME</th>
						<th>REGION</th>
						<th>AGENCY</th>
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
        <h3>ADD LOCATION</h3>
    </div>
    <div class="dialog-content">
		<form id="add-form">
			<ul>
				<li>
					<label for="addNames">LOCATION :</label>
					<input type="text" id="addNames" placeholder="Location Name" />
				</li>
				
				<li>
					<label for="addNames">SERIAL :</label>
					<input type="text" id="addSerial" placeholder="Location Serial" />
				</li>
				
				<li>
					<label for="addGender">AGENCY :</label>
					<select type="text" id="addAgency">
						<option value=""></option>
						<% agencies.eachWithIndex { sites, index -> %>
							<option value="${sites.id}">${sites.name}</option>
						<% } %>
					</select>
				</li>
				
				<li>
					<label for="addGender">REGION :</label>
					<select type="text" id="addRegion">
						<option value=""></option>
						<% regions.eachWithIndex { sites, index -> %>
							<option value="${sites.id}">${sites.name}</option>
						<% } %>
					</select>
				</li>				
			</ul>		
		</form>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>

<div id="edit-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>
        <h3>EDIT LOCATION</h3>
    </div>
    <div class="dialog-content">
		<form id="edit-form">
			<ul>
				<li>
					<label for="editNames">LOCATION :</label>
					<input type="text" id="editNames" placeholder="Location Name" />
					<input type="hidden" id="locationId" />
				</li>
				
				<li>
					<label for="editSerial">SERIAL :</label>
					<input type="text" id="editSerial" placeholder="Location Serial" />
				</li>
				
				<li>
					<label for="editAgency">AGENCY :</label>
					<select type="text" id="editAgency">
						<option value=""></option>
						<% agencies.eachWithIndex { sites, index -> %>
							<option value="${sites.id}">${sites.name}</option>
						<% } %>
					</select>
				</li>
				
				<li>
					<label for="editRegion">REGION :</label>
					<select type="text" id="editRegion">
						<option value=""></option>
						<% regions.eachWithIndex { sites, index -> %>
							<option value="${sites.id}">${sites.name}</option>
						<% } %>
					</select>
				</li>				
			</ul>		
		</form>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>