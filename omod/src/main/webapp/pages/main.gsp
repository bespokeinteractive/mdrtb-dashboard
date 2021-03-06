<%
    ui.decorateWith("appui", "standardEmrPage", [title: "MDR-TB Dashboard"])
%>

<script>
	jq(function(){
		var tabs = jq(".mdrtb-tabs").tabs();
	
		jq("#ul-left-menu").on("click", ".visit-summary", function(){
			jq("#visit-detail").html('<i class=\"icon-spinner icon-spin icon-2x pull-left\"></i> <span style="float: left; margin-top: 12px;">Loading...</span>');	
			jq("#visit-dst").html('');
			
			var visitSummary = jq(this);
			jq(".visit-summary").removeClass("selected");
			jq(visitSummary).addClass("selected");			
			
			jq.getJSON('${ ui.actionLink("mdrtbdashboard", "dashboard" ,"getVisitSummaryDetails") }', { 
				'encounterId' : jq(visitSummary).find(".encounter-id").val() 
			}).success(function (data) {
				var visitDetailTemplate =  _.template(jq("#visit-detail-template").html());
				var visitDstTemplate =  _.template(jq("#visit-dst-template").html());
				
				jq("#visit-detail").html(visitDetailTemplate(data.details));
				jq("#visit-dst").html(visitDstTemplate(data));
			});
		});
		
		jq(document).on("change", "#lastVisit", function () {
			if (jq(this).attr('checked')) {
				jq('.outcome').show();
				
				visitDialog.close();
				visitDialog.show();
			}
			else {
				jq('.outcome').hide();
				jq('#outcomeResults').val('');
				jq('#outcomeRemarks').val('');
			}			
		});
		
		jq('.update-details').on('click', function(e){
			e.preventDefault();
			
			jq('#regimenType').val('').change();
			jq('#regimenRemarks').val('');
			
			updateDetailsDialog.show();
		});
		
		jq('.update-treatment').on('click', function(e){
			e.preventDefault();
			
			jq('#regimenType').val('').change();
			jq('#regimenRemarks').val('');
			
			updateRegimenDialog.show();
		});
		
		jq('#outcomeResults').change(function(){
			var value = jq('#outcomeResults').val();
			
			if (value == 147 || value == 171){
				jq('.transfer_to').show();
			}
			else {
				jq('.transfer_to').hide();
			}
		}).change();
		
		jq('#regimenType').change(function(){
			jq('#regimenNew').val('');
			jq("#regimenNew").attr("readonly", true);
			jq('#regimenSelect').empty();
			
			if (jq('#regimenType').val() == ""){
				return false;
			}			
			
			if (${program.program.programId == 1}){
				if (jq('#regimenType').val() == 56){
					jq("#regimenNew").attr("readonly", false); 
					return false;
				}
				
				jq.ajax({
					type: "GET",
					url: '${ ui.actionLink("mdrtbdashboard", "dashboard", "getRegimenName") }',
					data: ({
						concept: jq('#regimenType').val(),
						program: ${program.program.programId}
					}),
					dataType: "json",
					success: function (data) {
						jq('#regimenNew').val(data);
					},
					error: function (xhr, ajaxOptions, thrownError) {
						return false;
					}
				});
			
			}
			else {
				if (jq('#regimenType').val() == 56){
					jq('#regimenNew').show();
					jq('#regimenSelect').hide();
				}
				else {
					jq('#regimenNew').hide();
					jq('#regimenSelect').show();
				}
				
				jq.ajax({
					type: "GET",
					url: '${ ui.actionLink("mdrtbdashboard", "dashboard", "getRegimenNames") }',
					data: ({
						concept: jq('#regimenType').val(),
						program: ${program.program.programId}
					}),
					dataType: "json",
					success: function (data) {
						jq.each(data, function(key,item) {
							jq('#regimenSelect').append(jq('<option>', { 
								value: item.name,
								text : item.name 
							}));
						}); 
						
						jq('#regimenSelect').change();
					},
					error: function (xhr, ajaxOptions, thrownError) {
						return false;
					}
				});
			}
			
		}).change();
		
		jq('#regimenSelect').change(function(){
			jq('#regimenNew').val(jq(this).val())
		}).change();
		
		jq('#genxpert-date-display').change(function(){
			loadGenXpertDialog();
		});
		
		jq('#culture-date-display').change(function(){
			loadCultureDialog();
		});
		
		jq('.dropdown ul li a').click(function(){			
			var selected = jq(this).data('value');
			
			if (selected == 1){
				jq('#genxpertResult').val('');
				loadTBExitDialog();
			}
			else if (selected == 2){
				jq('#cultureResult').val('');
				loadCultureDialog();
			}
			else if (selected == 3){
				loadDSTDialog();
			}
			else if (selected == 4){
				jq('#MdrtbOutcome').val('');
				jq('#MdrtbRemarks').val('');
				
				outcomeDialog.show();
			}
		});
		
		function loadTBExitDialog(){
			jq.getJSON('${ ui.actionLink("mdrtbdashboard", "dashboardVisits", "getObsLabNumber") }', { 
				patientId	: ${patient.id},
				date		: jq('#sputum-date-field').val()
			}).success(function (data) {
				jq('#LabNumber').val(data);
				visitDialog.show();				
			});
		}
		
		function loadGenXpertDialog(){
			jq.getJSON('${ ui.actionLink("mdrtbdashboard", "dashboardVisits", "getObsLabNumber") }', { 
				patientId	: ${patient.id},
				date		: jq('#genxpert-date-field').val()
			}).success(function (data) {
				jq('#genxpertLabNumber').val(data);
				genxpertDialog.show();				
			});
		}
		
		function loadCultureDialog(){
			jq.getJSON('${ ui.actionLink("mdrtbdashboard", "dashboardVisits", "getObsLabNumber") }', { 
				patientId	: ${patient.id},
				date		: jq('#culture-date-field').val()
			}).success(function (data) {
				jq('#cultureLabNumber').val(data);
				cultureDialog.show();				
			});
		}
		
		function loadDSTDialog(){
			jq.getJSON('${ ui.actionLink("mdrtbdashboard", "dashboardVisits", "getObsLabNumber") }', { 
				patientId	: ${patient.id},
				date		: jq('#dst-date-field').val()
			}).success(function (data) {
				jq('#dstLabNumber').val(data);
				dstDialog.show();				
			});
		}
		
		var updateDetailsDialog = emr.setupConfirmationDialog({
			dialogOpts: {
				overlayClose: false,
				close: true
			},
			selector: '#update-details-dialog',
			actions: {
				confirm: function() {
					if (jq('#detailsType').val() == ''){
						jq().toastmessage('showErrorToast', 'Ensure that you have specified the Tuberculosis Type');
						jq('#detailsType').focus();
						return false;
					}
					
					if (jq('#detailsClass').val() == ''){
						jq().toastmessage('showErrorToast', 'Ensure that you have specified the Classication of Tuberculosis');
						jq('#detailsClass').focus();
						return false;
					}
					
					if (jq('#regimenNew').val() === jq('#regimenCurrent').val()){
						jq().toastmessage('showErrorToast', "You can't change a Regimen to the same Regimen");
						jq('#regimenNew').focus();
						return false;
					}
					
					jq.ajax({
						type: "POST",
						url: '${ui.actionLink("mdrtbdashboard", "dashboardOverview", "updatePatientProgramDetails")}',
						data: ({
							programId:		${current.program.patientProgramId},
							enrolledOn:		jq('#details-date-field').val(),
							detailsType:	jq('#detailsType').val(),
							detailsClass: 	jq('#detailsClass').val(),
							detailsRemarks:	jq('#detailsRemarks').val()
						}),
						dataType: "json",
						success: function(data) {
							if (data.status == "success"){
								jq().toastmessage('showSuccessToast', data.message);
								window.location.href = "main.page?patient=${patient.id}&programId=${current.program.patientProgramId}";
							}
							else {
								jq().toastmessage('showErrorToast', 'x:'+ data.message);
							}							
						},
						error: function(data){
							jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
						}
					});
					
					updateRegimenDialog.close();
				},
				cancel: function() {
					updateRegimenDialog.close();
				}
			}
		});
		
		var updateRegimenDialog = emr.setupConfirmationDialog({
			dialogOpts: {
				overlayClose: false,
				close: true
			},
			selector: '#update-regimen-dialog',
			actions: {
				confirm: function() {
					if (jq('#regimenType').val() == ''){
						jq().toastmessage('showErrorToast', 'Ensure that you have specified the Regimen Type');
						jq('#regimenType').focus();
						return false;
					}
					
					if (jq('#regimenNew').val() == ''){
						jq().toastmessage('showErrorToast', 'Ensure that you have specified the Custom Regimen');
						jq('#regimenNew').focus();
						return false;
					}
					
					if (jq('#regimenNew').val() === jq('#regimenCurrent').val()){
						jq().toastmessage('showErrorToast', "You can't change a Regimen to the same Regimen");
						jq('#regimenNew').focus();
						return false;
					}
					
					jq.ajax({
						type: "POST",
						url: '${ui.actionLink("mdrtbdashboard", "dashboard", "updatePatientRegimen")}',
						data: ({
							ppdId:	${details.id},
							type:	jq('#regimenType').val(),
							name: 	jq('#regimenNew').val(),
							start:	jq('#regimen-date-field').val(),
							remarks:jq('#regimenRemarks').val()
						}),
						dataType: "json",
						success: function(data) {
							if (data.status == "success"){
								jq().toastmessage('showSuccessToast', data.message);
								window.location.href = "main.page?patient=${patient.id}";					
							}
							else {
								jq().toastmessage('showErrorToast', 'x:'+ data.message);
							}							
						},
						error: function(data){
							jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
						}
					});
					
					updateRegimenDialog.close();
				},
				cancel: function() {
					updateRegimenDialog.close();
				}
			}
		});
		
		var visitDialog = emr.setupConfirmationDialog({
			dialogOpts: {
				overlayClose: false,
				close: true
			},
			selector: '#visit-dialog',
			actions: {
				confirm: function() {
					if (jq('#LabNumber').val() == '' || jq('#sputumResult').val() == '' || jq('#outcomeResults').val() == ''){
						jq().toastmessage('showErrorToast', 'Ensure all fields have been properly filled before you continue');
						return false;
					}
					
					jq.ajax({
						type: "POST",
						url: '${ui.actionLink("mdrtbdashboard", "dashboardVisits", "addPatientVisit")}',
						data: ({
							patientId:			${patient.id},
							programId:			${current.program.patientProgramId},
							labNumber: 			jq('#LabNumber').val(),
							testedOn: 			jq('#sputum-date-field').val(),
							testResult:			jq('#sputumResult').val(),
							transferTo:			jq('#transferTo').val(),
							outcomeResults:		jq('#outcomeResults').val(),
							outcomeRemarks:		jq('#outcomeRemarks').val(),
						}),
						dataType: "json",
						success: function(data) {
							if (data.status == "success"){
								jq().toastmessage('showSuccessToast', data.message);
								window.location.href = "../referenceapplication/home.page";
							}
							else {
								jq().toastmessage('showErrorToast', 'x:'+ data.message);
							}							
						},
						error: function(data){
							jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
						}
					});
					
					visitDialog.close();
				},
				cancel: function() {
					visitDialog.close();
				}
			}
		});
		
		var outcomeDialog = emr.setupConfirmationDialog({
			dialogOpts: {
				overlayClose: false,
				close: true
			},
			selector: '#outcome-dialog',
			actions: {
				confirm: function() {
					if (jq('#MdrtbOutcome').val() == ''){
						jq().toastmessage('showErrorToast', 'Ensure that the outcome has been specified');
						return false;
					}
					
					jq.ajax({
						type: "POST",
						url: '${ui.actionLink("mdrtbdashboard", "dashboardVisits", "exitMdrtbPatients")}',
						data: ({
							patientId:			${patient.id},
							programId:			${current.program.patientProgramId},
							outcomeDate:		jq('#exit-date-field').val(),
							outcomeResult:		jq('#MdrtbOutcome').val(),
							outcomeRemarks:		jq('#MdrtbRemarks').val()
						}),
						dataType: "json",
						success: function(data) {
							if (data.status == "success"){
								jq().toastmessage('showSuccessToast', data.message);
								window.location.href = "../referenceapplication/home.page";
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
					outcomeDialog.close();
				}
			}
		});
		
		var genxpertDialog = emr.setupConfirmationDialog({
			dialogOpts: {
				overlayClose: false,
				close: true
			},
			selector: '#genxpert-dialog',
			actions: {
				confirm: function() {
					if (jq('#genxpertLabNumber').val() == '' || jq('#genxpertResult').val() == ''){
						jq().toastmessage('showErrorToast', 'Ensure all fields have been properly filled before you continue');
						return false;
					}
					
					jq.ajax({
						type: "POST",
						url: '${ui.actionLink("mdrtbdashboard", "dashboardVisits", "updateGenXpert")}',
						data: ({
							patientId:			${patient.id},
							labNumber: 			jq('#genxpertLabNumber').val(),
							testedOn: 			jq('#genxpert-date-field').val(),
							testResult:			jq('#genxpertResult').val()
						}),
						dataType: "json",
						success: function(data) {
							if (data.status == "success"){
								jq().toastmessage('showSuccessToast', data.message);
								window.location.href = "main.page?patient=${patient.id}&tabs=visits";					
							}
							else {
								jq().toastmessage('showErrorToast', 'x:'+ data.message);
							}
							
						},
						error: function(data){
							jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
						}
					});
					
					genxpertDialog.close();
				},
				cancel: function() {
					genxpertDialog.close();
				}
			}
		});
		
		var cultureDialog = emr.setupConfirmationDialog({
			dialogOpts: {
				overlayClose: false,
				close: true
			},
			selector: '#culture-dialog',
			actions: {
				confirm: function() {
					if (jq('#cultureLabNumber').val() == '' || jq('#cultureResult').val() == ''){
						jq().toastmessage('showErrorToast', 'Ensure all fields have been properly filled before you continue');
						return false;
					}
					
					jq.ajax({
						type: "POST",
						url: '${ui.actionLink("mdrtbdashboard", "dashboardVisits", "updateCulture")}',
						data: ({
							patientId:			${patient.id},
							labNumber: 			jq('#cultureLabNumber').val(),
							testedOn: 			jq('#culture-date-field').val(),
							testResult:			jq('#cultureResult').val()
						}),
						dataType: "json",
						success: function(data) {
							if (data.status == "success"){
								jq().toastmessage('showSuccessToast', data.message);
								window.location.href = "main.page?patient=${patient.id}&tabs=visits";					
							}
							else {
								jq().toastmessage('showErrorToast', 'x:'+ data.message);
							}
							
						},
						error: function(data){
							jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
						}
					});
					
					cultureDialog.close();
				},
				cancel: function() {
					cultureDialog.close();
				}
			}
		});
		
		var dstDialog = emr.setupConfirmationDialog({
			dialogOpts: {
				overlayClose: false,
				close: true
			},
			selector: '#dst-dialog',
			actions: {
				confirm: function() {
					var dataString = jq('#dst-form').serialize();
					
					if (jq('#dstLabNumber').val() == ''){
						jq().toastmessage('showErrorToast', 'Ensure all fields have been properly filled before you continue');
						return false;
					}
					
					jq.ajax({
						type: "POST",
						url: '${ui.actionLink("mdrtbdashboard", "dashboardVisits", "updateDrugSusceptibilityTests")}',
						data: dataString,
						dataType: "json",
						success: function(data) {
							if (data.status == "success"){
								jq().toastmessage('showSuccessToast', data.message);
								window.location.href = "main.page?patient=${patient.id}&tabs=visits";					
							}
							else {
								jq().toastmessage('showErrorToast', 'x:'+ data.message);
							}							
						},
						error: function(data){
							jq().toastmessage('showErrorToast', "Post Failed. " + data.statusText);
						}
					});
					
					dstDialog.close();
				},
				cancel: function() {
					dstDialog.close();
				}
			}
		});
		
		if ('${tabs}' == 'visits'){
			tabs.tabs('option','selected',1);
		}
		else if ('${tabs}' == 'chart'){
			tabs.tabs('option','selected',3);
		}
		
		if (${program.program.programId} == 2){
			jq('#regimenNew').hide();
		}
		
		jq(".visit-summary")[0].click();
	});
</script>

<style>
	.main-container {
		border: 1px solid #808080;
	}
	#patient-names .col1 {
		margin: 5px 0;
		text-align: center;
		width: 15%;
	}
	#patient-names .col2 {
		margin-top: 10px;
		width: 83%;
	}
	#patient-names .col2 div {
		margin-bottom: 5px;
		padding-bottom: 5px;
	}
	.patient-items {
		border-bottom: 1px solid #ccc;
		margin-bottom: 10px;
		width: 100%;
	}
	#patient-names div, #patient-names span, #patient-names img {
		display: inline-block;
		margin: 0;
		vertical-align: top;
	}
	.span-left {
		color: #0088cc;
		font-size: 14px;
		font-weight: bold;
		padding-left: 15px;
		width: 20%;
	}
	.span-right {
		font-size: 14px;
		width: auto;
	}
	#em-title {
		margin-top: 15px;	
	}
	#em-title.visits {
		border-bottom: 1px solid #ddd;
		margin-bottom: 5px;
		margin-top: 0;
	}
	#em-title span.section-title {
		color: #f26522;
		font-family:OpenSansBold, Arial, sans-serif;
		font-size: 20px;
		margin-bottom: 3px;
	}
	#em-title span.section-descr {
		font-family: "Myriad Pro",OpenSansBold,Arial,sans-serif;
		font-size: 16px;
		font-weight: bold;
		margin-bottom: 3px;
	}
	table {
		background-color: transparent;
		border-collapse: collapse;
		border-spacing: 0;
		max-width: 100%;
	}
	#em-title-content table tr, 
	.table-content {
		border-bottom: 1px solid #808080 !important;
		vertical-align: top;
	}
	.table-title {
		background: #000 none repeat scroll 0 0 !important;
		border-bottom: medium none !important;
		border-top: medium none !important;
		color: #fff;
	}
	#em-title-content table{
		font-size: 14px;
	}
	#em-title-content td {
		border: 1px none #ddd;
		padding-left: 10px;
		padding-right: 15px;
		white-space: nowrap;
	}
	table tr {
		border: 1px none #eeeeee;
	}
	.table-title td {
		border-right: 2px solid #fff !important;
		font-weight: bold;
	}
	#overview {
		 min-height: 450px;
		 padding: 5px;
	 }
	.title-answer {
		font-family: "Myriad Pro",Arial,Helvetica,Tahoma,sans-serif;
		font-size: 13px;
	}
	#whv {
		display: inline-block;
		width: 100%;
	}
	#whv .col1 {
		float: left;
		width: 48%;
	}	
	#whv .col2 {
		float: right;
		width: 47%;
	}
	#whv .col1, #whv .col2 {
		border: 1px solid #808080;
		display: inline-block;
		min-height: 20px;
		padding: 5px;
	}
	#whv .col1 .tital, #whv .col2 .tital {
		border-bottom: 1px solid #808080;
		font-family: "Myriad Pro",Arial,Helvetica,Tahoma,sans-serif;
		font-weight: bold;
		display: inline-block;
		min-height: 5px;
		padding-left: 10px;
		width: 95%;
	}	
	.entry {
		background: rgba(0, 0, 0, 0) url("${ui.resourceLink('mdrtbdashboard', 'images/arrow_grey_right.png')}") no-repeat scroll 0 center;
		color: #0088cc;
		display: inline-block;
		font-size: 14px;
		font-weight: bold;
		padding-left: 20px;
		width: 25%;
	}	
	.value {
		display: inline-block;
		width: 67%;
	}
	.warning {
		background: rgba(0, 0, 0, 0) url("${ui.resourceLink('mdrtbdashboard', 'images/ico_warning.png')}") no-repeat scroll 8px center / 20px auto;
		font-family: "Myriad Pro", Arial, Helvetica, Tahoma, sans-serif;
		color: #f00;
		font-size: 14px;
		padding-bottom: 5px !important;
		padding-left: 35px !important;
		padding-top: 5px !important;
	}
	.ui-widget-content a.popups {
		color: #007faa;
		font-family: "Myriad Pro", Arial, Helvetica, Tahoma, sans-serif;
		font-size: 12px;
		margin-right: 10px;
		text-decoration: none;
	}
	.ui-widget-content a.popups:hover {
		color: #007fff;
		text-decoration: none;
	}
	#ul-left-menu {
		border-left: 1px solid #ccc;
	}
	.aside {
		display: inline-block;
	}
	.content {
		border: 1px solid #ddd;
		box-sizing: border-box;
		display: inline-block;
		float: right;
		min-height: 200px;
		padding: 7px;
		vertical-align: top;
		width: 768px;
	}
	#visit-detail.info-section .info-body label {
		display: inline-block;
		font-family: "Myriad Pro","OpenSans",Arial,sans-serif;
		font-weight: bold;
		width: 190px;
	}
	#visit-detail.info-section .info-body label span {
		margin-right: 10px;
	}
	.dashboard .info-section {
		margin: 0;
		width: 100%;
	}	
	#visit-dst .info-header h3,
	#visit-detail .info-header h3 {
		color: #f26522;
	}
	#modal-overlay {
		background: #000 none repeat scroll 0 0;
		opacity: 0.3!important;
	}
	#visit-dialog.dialog {
		width: 500px;
	}	
	.dialog .dialog-content li {
		margin-bottom: 0;
	}
	.dialog-content ul li label{
		display: inline-block;
		width: 150px;
	}
	.dialog-content ul li input[type=text],
	.dialog-content ul li select,
	.dialog-content ul li textarea {
		border: 1px solid #ddd;
		display: inline-block;
		height: 40px;
		margin: 1px 0;
		min-width: 20%;
		padding: 5px 0 5px 10px;
		width: 60%;
	}
	form input:focus, 
	form select:focus, 
	form textarea:focus, 
	form ul.select:focus, 
	.form input:focus, 
	.form select:focus, 
	.form textarea:focus, 
	.form ul.select:focus {
		background: lightyellow none repeat scroll 0 0;
		outline: 0px none #007fff;
	}
	.add-on {
		left: auto;
		margin-left: -39px;
		position: relative;
	}	
	.dialog select option {
		font-size: 1em;
	}
	label span.mandatory {
		color: #f00;
		float: right;
		padding-right: 5px;
	}
	.dialog ul {
		margin-bottom: 20px;
	}
	.button.confirm{
		margin-right: 6px;
	}
	.outcome {
		display: none;
	}
	.dropdown ul {
		position: absolute;
		right: 4px;
	}
	.dropdown ul li {
		cursor: pointer;
	}
	#dst-form{
		margin: 0;
	}
	#dstTable{
		margin-top: 3px;
	}
	#dstTable tr th:nth-child(2),
	#dstTable tr td:nth-child(2),
	#dstTable tr th:nth-child(3),
	#dstTable tr td:nth-child(3),
	#dstTable tr th:nth-child(4),
	#dstTable tr td:nth-child(4),
	#dstTable tr th:nth-child(5),
	#dstTable tr td:nth-child(5){
		text-align: center;	
	}
	#dstTable tr td input[type="radio"]{
		cursor: pointer;
		-webkit-appearance: checkbox;
		-moz-appearance: checkbox;
		-ms-appearance: checkbox;
		appearance: checkbox;
	}
	.dialog-content ul li td label {
		display: inline-block;
		cursor: pointer;
		width: 100%;
	}
	.add-visit {
		cursor: pointer;
	}
	.dropdown ul li {
		width: 180px;
	}
</style>

<div class="clear"></div>
<div>
    <div class="example">
        <ul id="breadcrumbs">
            <li>
                <a href="${ui.pageLink('referenceapplication', 'home')}">
                <i class="icon-home small"></i></a>
            </li>

            <li>
                <i class="icon-chevron-right link"></i>
                Patient Dashboard
            </li>
        </ul>
    </div>
</div>

${ ui.includeFragment("mdrtbdashboard", "header", [patientId: patient.id, programId: program.patientProgramId]) }

<div class="mdrtb-tabs">
	<ul>
		<li id="cn"><a href="#overview">Overview</a></li>
		<li id="ti"><a href="#visits">Visits</a></li>
		<li id="cs"><a href="#treatment">Treatment</a></li>
		<li id="lr"><a href="#chart">Chart</a></li>
		<li id="hs"><a href="#history">Program History</a></li>
	</ul>
	
	<div id="overview">
		${ ui.includeFragment("mdrtbdashboard", "dashboardOverview", [patientId: patient.id]) }
	</div>

	<div id="visits">
		${ ui.includeFragment("mdrtbdashboard", "dashboardVisits", [patientId: patient.id]) }
	</div>
	
	<div id="treatment">
		${ ui.includeFragment("mdrtbdashboard", "dashboardTreatment", [patientId: patient.id]) }
	</div>
	
	<div id="chart">
		${ ui.includeFragment("mdrtbdashboard", "dashboardChart", [patientId: patient.id]) }
	</div>
	
	<div id="history">
		History NOT Available
	</div>
</div>