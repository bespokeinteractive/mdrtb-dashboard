<%
    ui.decorateWith("appui", "standardEmrPage", [title: "Intake Form"])
	
	ui.includeJavascript("uicommons", "handlebars/handlebars.min.js", Integer.MAX_VALUE - 1)
    ui.includeJavascript("uicommons", "navigator/validators.js", Integer.MAX_VALUE - 19)
    ui.includeJavascript("uicommons", "navigator/navigator.js", Integer.MAX_VALUE - 20)
    ui.includeJavascript("uicommons", "navigator/navigatorHandlers.js", Integer.MAX_VALUE - 21)
    ui.includeJavascript("uicommons", "navigator/navigatorModels.js", Integer.MAX_VALUE - 21)
    ui.includeJavascript("uicommons", "navigator/navigatorTemplates.js", Integer.MAX_VALUE - 21)
    ui.includeJavascript("uicommons", "navigator/exitHandlers.js", Integer.MAX_VALUE - 22)
	
    ui.includeJavascript("mdrtbdashboard", "moment.js", Integer.MAX_VALUE - 23)
%>

<script>
	var NavigatorController;
	var emrMessages = {};
	
	emrMessages["numericRangeHigh"] = "value should be less than {0}";
	emrMessages["numericRangeLow"] = "value should be more than {0}";
	emrMessages["requiredField"] = "Required Field";
	emrMessages["numberField"] = "Value not a number";
	
	jq(function() {
		NavigatorController = new KeyboardController();
		
		jq("#session-location ul.select").on('click', 'li', function (event) {
			setTimeout(function() {
				jq.ajax({
					type: "GET",
					url: '${ ui.actionLink("mdrtbdashboard", "dashboard", "getSelectedLocation") }',
					dataType: "json",
					success: function (data) {
						jq('#location').val(data);
						jq('.locations').val(data);
					},
					error: function (xhr, ajaxOptions, thrownError) {
						alert(thrownError);
						return false;
					}
				});
				
				jq.ajax({
					type: "GET",
					url: '${ ui.actionLink("mdrtbdashboard", "dashboard", "getSelectedLocationFacilities") }',
					dataType: "json",
					success: function (data) {
						jq('#locationFacility').empty();
						jq('#locationFacility').append('<option value=""></option>');
						
						for (index in data.facilities) {
							var item = data.facilities[index];
							
							var row = '<option value="' + item.id + '">' + item.name + '</option>';
							jq('#locationFacility').append(row);
						}
					},
					error: function (xhr, ajaxOptions, thrownError) {
						alert(thrownError);
					}
				});				
			}, 1500);
			
		});
		
		jq("#vitalsHeight, #vitalsWeight").on('blur', function () {
			if (!jq.isNumeric(jq(this).val())){
				jq(this).val('');				
				return false;
			}
			
			if (${patient.age} <= 5){
				return false;
			}
			var height = getFloatValue(jq("#vitalsHeight").val())/100;
			var weight = getFloatValue(jq("#vitalsWeight").val());
			
			var bmi = weight/(height * height);
			
			if (bmi == 'Infinity') {
				jq("#bmi").val('');
			}else {
				jq("#bmi").val(String(bmi).formatToAccounting());			
			}
        });
		
		jq("#bmi").on('blur', function () {
			if (!jq.isNumeric(jq(this).val())){
				jq(this).val('');
			}
		});
		
		jq('#sputumResult').change(function(){
			if (jq(this).val() == 30 || jq(this).val() == ""){
				jq('.smear-date').hide();
			} else {
				jq('.smear-date').show();
			}
		});
		
		jq('#genXpertResult').change(function(){
			if (jq(this).val() == 30 || jq(this).val() == ""){
				jq('.genxpert-date').hide();
			} else {
				jq('.genxpert-date').show();
			}
		});
		
		jq('#hivResult').change(function(){
			if (jq(this).val() == 30 || jq(this).val() == ""){
				jq('.hivtest-date').hide();
			} else {
				jq('.hivtest-date').show();
			}			
			
			if (jq(this).val() == 28){
				// Positive
				jq('.hiv-positive-section').show(300);
				jq('#summaryTable tr:eq(13)').show();
				jq('#summaryTable tr:eq(14)').show();
				
			} else {
				// Negative
				jq('.hiv-positive-section').hide(300);
				
				jq('#summaryTable tr:eq(13)').hide();
				jq('#summaryTable tr:eq(14)').hide();
			}
		});
		
		jq('#xray-result').change(function(){
			if (jq(this).val() == 30 || jq(this).val() == ""){
				jq('.xray-date').hide();
			} else {
				jq('.xray-date').show();
			}
		});
		
		jq('#artStatus').change(function(){
			if (jq(this).val() == 127){
				jq('#artDate').show();
			} else {
				jq('#artDate').hide();
				jq('#artDate-field').val('');
				jq('#artDate-display').val('');
			}
		});
		
		jq('#cptStatus').change(function(){
			if (jq(this).val() == 127){
				jq('#cptDate').show();
			} else {
				jq('#cptDate').hide();
				jq('#cptDate-field').val('');
				jq('#cptDate-display').val('');
			}
		});
		
		jq('#visit-description input').change(function(){
			jq('#summaryTable tr:eq(0) td:eq(1)').text(jq('#registerNumber').val());
			jq('#summaryTable tr:eq(1) td:eq(1)').text(jq('#locationFacility :selected').text());
			jq('#summaryTable tr:eq(2) td:eq(1)').text(jq('#vitalsWeight').val());
			jq('#summaryTable tr:eq(3) td:eq(1)').text(jq('#vitalsHeight').val());
			jq('#summaryTable tr:eq(4) td:eq(1)').text(jq('#bmi').val());
		
		});

		jq('#treatment-description input, #treatment-description select').change(function(){
            jq('#summaryTable tr:eq(5) td:eq(1)').text(jq('#treatmentSupporter').val());
            jq('#summaryTable tr:eq(6) td:eq(1)').text(jq('#treatmentReferral :selected').text());
            jq('#summaryTable tr:eq(7) td:eq(1)').text(jq('#treatmentDots :selected').text());
            jq('#summaryTable tr:eq(8) td:eq(1)').text(jq('#treatmentSite :selected').text());
        
        });

        jq('#spaturm-description input, #spaturm-description select').change(function(){
            jq('#summaryTable tr:eq(9) td:eq(1)').text(jq('#LabNumber').val());
            jq('#summaryTable tr:eq(10) td:eq(1)').text(jq('#sputumResult :selected').text());
        
        });
        

        jq('#genXpert-description input, #genXpert-description select').change(function(){
            jq('#summaryTable tr:eq(11) td:eq(1)').text(jq('#genXpertResult :selected').text());
        
        });

        jq('#hivResult-description input, #hivResult-description select').change(function(){
			var artStatus = jq('#artStatus :selected').text();
			var cptStatus = jq('#cptStatus :selected').text();
			
            jq('#summaryTable tr:eq(12) td:eq(1)').text(jq('#hivResult :selected').text());
            jq('#summaryTable tr:eq(13) td:eq(1)').text(artStatus);
            jq('#summaryTable tr:eq(14) td:eq(1)').text(cptStatus);        
        });

        jq('#xrayresult-description input, #xrayresult-description select').change(function(){
            jq('#summaryTable tr:eq(15) td:eq(1)').text(jq('#xray-result :selected').text());
        
        });
        
        
		jq('.submit').click(function(){
			jq("#intake").submit();		
		});
	});
	
	function getFloatValue(source) {
		return isNaN(parseFloat(source)) ? 0 : parseFloat(source);
	}
</script>

<style>
	.name {
		color: #f26522;
	}
	.simple-form-ui input, 
	.simple-form-ui form input, 
	.simple-form-ui textarea, 
	.simple-form-ui form textarea, 
	.simple-form-ui select, 
	.simple-form-ui ul.select, 
	.simple-form-ui form select, 
	.simple-form-ui form ul.select {
		box-sizing: border-box;
		height: 40px;
		width: 71%;
	}
	.simple-form-ui section fieldset select:focus, 
	.simple-form-ui section fieldset input:focus, 
	.simple-form-ui section #confirmationQuestion select:focus, 
	.simple-form-ui section #confirmationQuestion input:focus, 
	.simple-form-ui #confirmation fieldset select:focus,
	.simple-form-ui #confirmation fieldset input:focus, 
	.simple-form-ui #confirmation #confirmationQuestion select:focus, 
	.simple-form-ui #confirmation #confirmationQuestion input:focus, 
	.simple-form-ui form section fieldset select:focus, 
	.simple-form-ui form section fieldset input:focus, 
	.simple-form-ui form section #confirmationQuestion select:focus, 
	.simple-form-ui form section #confirmationQuestion input:focus, 
	.simple-form-ui form #confirmation fieldset select:focus, 
	.simple-form-ui form #confirmation fieldset input:focus, 
	.simple-form-ui form #confirmation #confirmationQuestion select:focus, 
	.simple-form-ui form #confirmation #confirmationQuestion input:focus{
		outline: 2px none #007fff;
		box-shadow: 0 0 0.3px 0 #888 !important;
	}
	.simple-form-ui section, 
	.simple-form-ui #confirmation, 
	.simple-form-ui form section, 
	.simple-form-ui form #confirmation {
		background: #fff none repeat scroll 0 0;
		border-top: 1px solid #eee;
		border-right: 1px solid #eee;
		border-bottom: 1px solid #eee;
		box-sizing: border-box;
		min-height: 400px;
		width: 78%;
	}
	form label, .form label {
		box-sizing: border-box;
		display: inline-block;
		width: 24%;
	}
	form label span.mandatory,
	.form label span.mandatory {
		color: #f00;
		float: right;
		padding-right: 5px;
	}	
	.simple-form-ui .field-error, .simple-form-ui form .field-error {
		margin-left: 25%;
	}
	fieldset field span label span{
		display: none;
	}
	div.separater{
		border-bottom: 1px dotted #ddd;
		margin: 15px 0 5px 10px;
		width: 94%;
	}
	#artDate, #cptDate {
		display: none;
	}
	.append-label {
		color: #999999;
		left: -45px;
		position: relative;
	}
	.genxpert-date,
	.hivtest-date,
	.xray-date,
	.smear-date{
		display: none;
	}	
	table th, table td {
		border: 1px none #ddd;
		padding: 5px 10px;
	}
	#summaryTable tr:nth-child(2n), #summaryTable tr:nth-child(2n+1) {
		background: none;
	}
	#summaryTable tr, 
	#summaryTable th, 
	#summaryTable td {
		border: 1px none #eee;
	}
	#summaryTable tr {
		border-bottom: 1px solid #eee;
	}
	#summaryTable tr:last-child{
		border-bottom: 1px none #eee;
	}
	#summaryTable td:first-child{
		width: 30%;
	}
</style>

${ ui.includeFragment("mdrtbdashboard", "header", [patientId: patient.id, programId: programDetails.patientProgramId]) }

<% if (programDetails.programDetails.id == 1) { %>
	${ui.includeFragment("mdrtbdashboard","intakeTbbPatients", [patient: patient.patientId])}
<% } else { %>
	${ui.includeFragment("mdrtbdashboard","intakeMdrPatients", [patient: patient.patientId])}
<% } %>