<style>
	.name {
		color: #f26522;
	}
	.new-patient-header {
		margin: 3px 0;
	}
	.new-patient-header .demographics .gender-age {
		font-size: 14px;
		margin-left: -55px;
		margin-top: 12px;
	}	
	.new-patient-header .demographics .gender-age span {
		border-bottom: 1px none #ddd;
	}	
	.new-patient-header .demographics {
		width: 60%;
	}
	.new-patient-header .identifiers{
		margin-top: 10px;
	}
	.new-patient-header .identifiers .catg{
		margin: 40px 10px 0 0;
	}	
	#breadcrumbs a, #breadcrumbs a:link, #breadcrumbs a:visited {
		color: #555;
		text-decoration: none;
	}	
</style>

<div class="patient-header new-patient-header">
    <div class="demographics">
        <h1 class="name">		
			<img src="${ui.resourceLink('mdrtbdashboard', patient.gender=='F'?'images/avatar-female.png':'images/avatar-male.png')}" style="height: 35px;"/>
            <span id="surname">${patient.familyName},<em>surname</em></span>
            <span id="othname">${patient.givenName} ${patient.middleName ? patient.middleName : ''} &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <em>other names</em>
            </span>

            <span class="gender-age">
                <span>
                    ${patient.gender=='M'?'Male':'Female'}
                </span>
                <span id="agename">(${patient.age} ${patient.age==1?'yr':'yrs'})</span>
            </span>
        </h1>

        <br/>

        <div id="stacont" class="status-container">
            <span class="status active"></span>
            Registered Location
        </div>

        <div class="tag">${program.location.name}</div>
    </div>

    <div class="identifiers">
        <em>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;TBMU No.</em>
        <span>${patientDetails.tbmuNumber?patientDetails.tbmuNumber:'N/A'}</span>
        <br>

        <div class="catg">
            <i class="icon-tags small" style="font-size: 16px"></i><small>Enrolled: ${program.program.programId==1?'TB Programme':'MDR-TB Programme'}</small>
        </div>
    </div>
</div>

<div class="clear"></div>