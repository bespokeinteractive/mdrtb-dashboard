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
		
		jq("#session-location ul.select li").click(function (event) {
			jq.ajax({
				type: "GET",
				url: '${ ui.actionLink("mdrtbdashboard", "dashboard", "getSelectedLocation") }',
				dataType: "json",
				success: function (data) {
					jq('#location').val(data);
				},
				error: function (xhr, ajaxOptions, thrownError) {
					alert(thrownError);
				}

			});
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
				jq('.hiv-positive-section').show(300);
			} else {
				jq('.hiv-positive-section').hide(300);
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
</style>

${ ui.includeFragment("mdrtbdashboard", "header", [patientId: patient.id, programId: program.patientProgramId]) }

<% if (program.program.id == 1) { %>
	${ui.includeFragment("mdrtbdashboard","intakeTbbPatients", [patient: patient.patientId])}
<% } else { %>
	${ui.includeFragment("mdrtbdashboard","intakeMdrPatients", [patient: patient.patientId])}
<% } %>