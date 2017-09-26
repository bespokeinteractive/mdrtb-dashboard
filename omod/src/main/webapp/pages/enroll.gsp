<%
    ui.decorateWith("appui", "standardEmrPage", [title: "Enrollment Form"])
%>

<script>
	jq(function() {		
		jq('#enrollmentClassifications').change(function(){
			if(jq(this).val() == 31){
				jq('#enrollmentPreviousTreatment').val(46);
				jq("#enrollmentPreviousTreatment").attr("disabled",true);
			}
			else if(jq(this).val() == ""){
				jq('#enrollmentPreviousTreatment').val("");
				jq("#enrollmentPreviousTreatment").attr("disabled",true);
			}
			else{
				jq("#enrollmentPreviousTreatment").attr("disabled",false);
			}
		});

        jq("#session-location ul.select").on('click', 'li', function (event) {
			setTimeout(function() {
				GenerateTbmuNumber();
			}, 1500);
		});
		
		jq('#date-enrolled-display').change(function(){
			GenerateTbmuNumber();
		}).change();
		
		jq('#enrollmentType').change(function(){
			if (jq(this).val() == 1){
				jq('.tbb'  ).show(300);
				jq('.mdrtb').hide(300);
				jq('.menu-title span').text('TB PROGRAMME');
			}
			else  if (jq(this).val() == 2){
				jq('.tbb'  ).hide(300);
				jq('.mdrtb').show(300);
				jq('.menu-title span').text('MDR-TB PROGRAMME');
			}
			else{
				jq('.tbb'  ).hide(300);
				jq('.mdrtb').hide(300);
				jq('.menu-title span').text('SELECT PROGRAMME');
			}
		});
		
		jq('#enrollmentPatientType').change(function(){
			var catg = jq('#enrollmentTreatmentCategory');
			catg.empty();
			
			if (jq(this).val() == 24){
				catg.append(jq('<option></option>').attr("value", '').text(''));
				if (${patient.age} > 14){
					catg.append(jq('<option></option>').attr("value", '23').attr("data-uuid", '3745a3bc-c9ad-4011-85ee-c8dcbfd1ed97').text('NEW ADULT'));				
				}
				catg.append(jq('<option></option>').attr("value", '48').attr("data-uuid", '3745a3bc-c9ad-4011-85ee-c8dcbfd1ed98').text('PEDIATRIC PATIENT'));			
			}
			else if (jq(this).val() == 25 || jq(this).val() == 26){
				catg.append(jq('<option></option>').attr("value", '').text(''));
				catg.append(jq('<option></option>').attr("value", '48').attr("data-uuid", '3745a3bc-c9ad-4011-85ee-c8dcbfd1ed98').text('PEDIATRIC PATIENT'));
				catg.append(jq('<option></option>').attr("value", '21').attr("data-uuid", 'd4f92bdd-1e22-4578-a535-93dbb44c1fd4').text('PREVIOUSLY TREATED WITH FIRST LINE DRUGS'));
			}
			else {
				catg.append(jq('<option></option>').attr("value", '').text(''));
				if (${patient.age} > 14){
					catg.append(jq('<option></option>').attr("value", '23').attr("data-uuid", '3745a3bc-c9ad-4011-85ee-c8dcbfd1ed97').text('NEW ADULT'));				
				}
				catg.append(jq('<option></option>').attr("value", '48').attr("data-uuid", '3745a3bc-c9ad-4011-85ee-c8dcbfd1ed98').text('PEDIATRIC PATIENT'));
				catg.append(jq('<option></option>').attr("value", '21').attr("data-uuid", 'd4f92bdd-1e22-4578-a535-93dbb44c1fd4').text('PREVIOUSLY TREATED WITH FIRST LINE DRUGS'));
			}
		});
		
		jq('.confirm').click(function(){
			if (jq('#treatmentSite').val() == ''){
				jq().toastmessage('showErrorToast', 'Kindly select the type of TB the Patient is suffering from');
				return false;				
			}
			
			if (jq('#confirmationSite').val() == ''){
				jq().toastmessage('showErrorToast', 'Kindly select the classification of the Type of Tb for the Patient');
				return false;				
			}
			
			if (jq('#enrollmentType').val() == ''){
				jq().toastmessage('showErrorToast', 'Kindly select the group you want to Enroll the Patient');
				return false;				
			}
			
			jq.ajax({
				type: "POST",
				url: '${ui.actionLink("mdrtbdashboard", "dashboard", "enrollPatient")}',
				data: ({
					patientId:			${patient.id},
					programId: 			jq('#enrollmentType').val(),
					enrolledOn: 		jq('#date-enrolled-field').val(),
					treatmentSite: 		jq('#treatmentSite').val(),
					confirmationSite: 	jq('#confirmationSite').val(),
					enrollmentNotes:	'',
					previousTreatment:	jq('option:selected', '#enrollmentPreviousTreatment').data('uuid'),					
					classification:		jq('option:selected', '#enrollmentClassifications').data('uuid'),
					patientType:		jq('option:selected', '#enrollmentPatientType').data('uuid'),
					treatmentCategory:	jq('option:selected', '#enrollmentTreatmentCategory').data('uuid')
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
		});
		
		function GenerateTbmuNumber(){
			jq.ajax({
				type: "GET",
				url: '${ ui.actionLink("mdrtbdashboard", "dashboard", "generateBMUNumber") }',
				dataType: "json",
				data: ({
					regdate: jq('#date-enrolled-field').val()
				}),
				success: function (data) {
					jq('.identifiers span').text(data);
					jq('#identifier').val(data);
					jq('.tag').text(sessionLocationModel.text());
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert(thrownError);
				}

			});
		}
	});
	
</script>

<style>
	.toast-item {
		background-color: #222;
	}

	.name {
		color: #f26522;
	}

	#breadcrumbs a, #breadcrumbs a:link, #breadcrumbs a:visited {
		text-decoration: none;
	}	
	
	.new-patient-header .demographics {
		width: 60%;
	}

	.new-patient-header .demographics .gender-age {
		font-size: 14px;
		margin-left: -55px;
		margin-top: 12px;
	}

	.new-patient-header .demographics .gender-age span {
		border-bottom: 1px none #ddd;
	}

	.new-patient-header .identifiers {
		margin-top: 5px;
	}

	.tag {
		padding: 2px 10px;
	}

	.tad {
		background: #666 none repeat scroll 0 0;
		border-radius: 1px;
		color: white;
		display: inline;
		font-size: 0.8em;
		padding: 2px 10px;
	}

	.status-container {
		padding: 5px 10px 5px 5px;
	}

	.catg {
		color: #363463;
		margin: 35px 10px 0 0;
	}

	.print-only {
		display: none;
	}

	.button-group {
		display: inline-block;
		position: relative;
		vertical-align: middle;
	}

	.button-group > .button:first-child:not(:last-child):not(.dropdown-toggle) {
		border-bottom-right-radius: 0;
		border-top-right-radius: 0;
	}

	.button-group > .button:first-child {
		margin-left: 0;
	}

	.button-group > .button:hover, .button-group > .button:focus, .button-group > .button:active, .button-group > .button.active {
		z-index: 2;
	}

	.button-group > .button {
		border-radius: 5px;
		float: left;
		position: relative;
	}

	.button.active, button.active, input.active[type="submit"], input.active[type="button"], input.active[type="submit"], a.button.active {
		background: #d8d8d8 none repeat scroll 0 0;
		border-color: #d0d0d0;
	}

	.button-group > .button:not(:first-child):not(:last-child) {
		border-radius: 0;
	}

	.button-group > .button {
		border-radius: 5px;
		float: left;
		position: relative;
	}

	.button-group > .button:last-child:not(:first-child) {
		border-bottom-left-radius: 0;
		border-top-left-radius: 0;
	}

	.button-group .button + .button, .button-group .button + .button-group, .button-group .button-group + .button, .button-group .button-group + .button-group {
		margin-left: -1px;
	}

	ul.left-menu {
		border-style: solid;
	}

	.col15 {
		display: inline-block;
		float: left;
		max-width: 22%;
		min-width: 22%;
	}

	.col16 {
		display: inline-block;
		float: left;
		width: 730px;
	}

	#date-enrolled label {
		display: none;
	}

	.add-on {
		color: #f26522;
		left: -38px;
	}

	.append-to-value {
		color: #999;
		float: right;
		left: auto;
		margin-left: -200px;
		margin-top: 13px;
		padding-right: 55px;
		position: relative;
	}

	.menu-title span {
		display: inline-block;
	}

	span a:hover {
		text-decoration: none;
	}

	form label,
	.form label {
		display: inline-block;
		padding-left: 10px;
		width: 140px;
	}

	form input,
	form textarea,
	.form input,
	.form textarea {
		display: inline-block;
		min-width: 70%;
	}

	form select,
	form ul.select,
	.form select,
	.form ul.select {
		display: inline-block;
		min-width: 73%;
	}

	#5596AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA label,
	#1427AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA label {
		display: none;
	}

	form input:focus, form select:focus, form textarea:focus, form ul.select:focus, .form input:focus, .form select:focus, .form textarea:focus, .form ul.select:focus {
		outline: 2px none #007fff;
		box-shadow: 0 0 1px 0 #ccc !important;
	}

	form input[type="checkbox"], 
	.form input[type="checkbox"] {
		margin-top: 4px;
		cursor: pointer;
	}
	#modal-overlay {
		background: #000 none repeat scroll 0 0;
		opacity: 0.4 !important;
	}	
	.dialog-content label {
		display: inline-block;
		width: 130px !important;
	}
	.dialog-content select,
	.dialog-content input[type="text"],
	.dialog-content input[type="number"] {
		display: inline-block;
		margin: 0 !important;
		min-width: 30% !important;
		width: 210px !important;
	}
	.dialog-content .append-to-value {
		margin-top: 7px;
		padding-right: 44px;
	}
	.dialog select option {
		font-size: 1em;
	}
	input[type="text"], 
	input[type="number"], 
	input[type="password"], 
	select {
		border: 1px solid #aaa !important;
		border-radius: 2px !important;
		box-shadow: none !important;
		box-sizing: border-box !important;
		height: 38px !important;
		line-height: 18px !important;
		padding: 0 10px !important;
		width: 68%!important;
	}
	span.date input {
		display: inline-block;
		padding: 5px 10px;
		text-transform: uppercase;
	}
	.info-header h3{
		color: #f26522;
	}
	.info-body div{
		margin-bottom: 5px;	
	}
	.info-body div:first-child{
		margin-top: 5px;	
	}
	.info-body div:last-child{
		margin-bottom: 40px;	
	}
	.info-body div label{
		display: inline-block;
		font-weight: bold;
		font-size: 0.85em;
		padding-left: 10px;
		text-transform: uppercase;
		width: 190px;
	}
	.dashboard .info-section {
		box-sizing: border-box;
		width: 100%;
	}
	textarea {
		resize: none;
		width: 68%;
	}
	.tbb,
	.mdrtb{
		display: none;
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
                <a href="${ui.pageLink('mdrtbregistration', 'search')}">MDR-TB Dashboard</a>
            </li>

            <li>
                <i class="icon-chevron-right link"></i>
                Enroll Patient
            </li>
        </ul>
    </div>
</div>

<div class="patient-header new-patient-header">
    <div class="demographics">
        <h1 class="name">
            <span id="surname">${patient.familyName},<em>surname</em></span>
            <span id="othname">${patient.givenName} ${patient.middleName ? patient.middleName : ''} &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <em>other names</em>
            </span>

            <span class="gender-age">
                <span>
                    ${gender}
                </span>
                <span id="agename"></span>
            </span>
        </h1>

        <br/>

        <div id="stacont" class="status-container">
            <span class="status active"></span>
            Registration Center
        </div>

        <div class="tag">${ ui.format(sessionContext.sessionLocation) }</div>
    </div>

    <div class="identifiers">
        <em>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Patient ID</em>
        <span>${patient.getPatientIdentifier()}</span>
        <br>

        <div class="catg">
            <i class="icon-tags small" style="font-size: 16px"></i><small>Agency:</small> ${agency}
        </div>
    </div>
</div>

<div>
	<div id="div-left-menu" style="padding-top: 15px; color: #363463;" class="col15 clear">
		<ul id="ul-left-menu" class="left-menu">
			<li class="menu-item visit-summary selected" visitid="54">
				<span class="menu-date">
					<i class="icon-user"></i>
					<span id="vistdate">
						PATIENT ENROLMENT
					</span>
				</span>
				<span class="menu-title">
					<i class="icon-info-sign"></i>
					<span>ENROLL PROGRAMME</span>
				</span>
				<span class="arrow-border"></span>
				<span class="arrow"></span>
			</li>
			
			<li style="height: 30px;" class="menu-item">
			</li>
		</ul>
	</div>
	
	<div style="min-width: 77.4%" class="col16 dashboard">			
		<div id="visit-detail" class="info-section">
			<div class="info-header">
				<i class="icon-user-md"></i>
				<h3>ENROLMENT DETAILS</h3>
			</div>

			<div class="info-body">
				<div>
					<label for="date-enrolled-display">Enrollment Date :</label>
					${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'date-enrolled', id: 'date-enrolled', label: 'Date of Enrollment', useTime: false, defaultToday: true, endDate: new Date()])}
					<input type="hidden" id="patientId"  name="patient.id" value="${patient.id}" />
					<input type="hidden" id="identifier" name="patient.identifier" />
				</div>
				
				<div>
					<label for="treatmentSite">Tuberculosis Type :</label>					
					<select id="treatmentSite" class="required" name="treatment.site">
						<option value="">&nbsp;</option>
						<% anatomicalSites.eachWithIndex { sites, index -> %>
							<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name}</option>
						<% } %>
					</select>
				</div>
				
				<div>
					<label for="confirmationSite">Classification of TB:</label>					
					<select id="confirmationSite" class="required" name="confirmation.site">
						<option value="">&nbsp;</option>
						<% siteConfirmation.eachWithIndex { sites, index -> %>
							<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name}</option>
						<% } %>
					</select>
				</div>
				
				<div>
					<label for="enrollmentType">Resistance Profile:</label>
					<select id="enrollmentType" name="enrollment.Type">
						<option value=""></option>
						<option value="1">DRUG SENSITIVE TUBERCULOSIS</option>
						<option value="2">DRUG RESISTANT TUBERCULOSIS</option>
					</select>
				</div>
				
				<div class="tbb">
					<label for="enrollmentPatientType">Patient Classification:</label>
					<select id="enrollmentPatientType" name="enrollment.type">
						<option value="" data-uuid=""></option>
						<% enrollmentPatientType.eachWithIndex { classification, index -> %>
							<option value="${classification.id}" data-uuid="${classification.uuid}">${classification.concept.displayString}</option>
						<% } %>
					</select>
				</div>
				
				<div class="tbb">
					<label for="enrollmentTreatmentCategory">Treatment Category:</label>
					<select id="enrollmentTreatmentCategory" name="enrollment.category">
						<option value="" data-uuid=""></option>
						<% enrollmentTreatmentCategory.eachWithIndex { classification, index -> %>
							<option value="${classification.id}" data-uuid="${classification.uuid}">${classification.concept.displayString}</option>
						<% } %>
					</select>
				</div>
				
				<div class="mdrtb">
					<label for="enrollmentClassifications">Registration Category:</label>
					<select id="enrollmentClassifications" name="enrollment.Classifications">
						<option value="" data-uuid=""></option>
						<% enrollmentClassifications.eachWithIndex { classification, index -> %>
							<option value="${classification.id}" data-uuid="${classification.uuid}">${classification.concept.displayString}</option>
						<% } %>
					</select>
				</div>
				
				<div style="display:none">
					<label for="enrollmentPreviousTreatment">Previous Treatment:</label>
					<select id="enrollmentPreviousTreatment" name="enrollment.PreviousTreatment" disabled="true">
						<% enrollmentPreviousTreatment.eachWithIndex { classification, index -> %>
							<option value="${classification.id}" data-uuid="${classification.uuid}">${classification.concept.displayString}</option>
						<% } %>
					</select>
				</div>
			</div>
		</div>		
		
		<button style="float: right; margin: 10px; display: block;" class="confirm">
			<i class="icon-save small"></i>
			Enroll
		</button>
	</div>
</div>

<div class="container">	
	<br style="clear: both">
</div>

