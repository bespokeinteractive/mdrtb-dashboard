<div id="patient-names" class="main-container">
	<div class="col1">
		<img src="${ui.resourceLink('mdrtbdashboard', patient.gender=='F'?'images/fmale.png':'images/male.png')}" style="height: 135px;"/>
	</div>
	
	<div class="col2">
		<div class="patient-items first">
			<span class="span-left">ENROLLED ON</span>
			<span class="span-right">${ui.formatDatePretty(program.dateEnrolled).toUpperCase()}</span>
			<span class="right update-description"><a class="popups" href="#"><i class="icon-pencil small"></i>Edit Details</a></span>
		</div>
		
		<div class="patient-items first">
			<span class="span-left">LOCATION</span>
			<span class="span-right">${program.location.name.toUpperCase()}</span>
		</div>
		
		<% if (current.program.program.programId == 1) { %>
			<div class="patient-items">
				<span class="span-left">PATIENT TYPE</span>
				<span class="span-right">${current.getClassificationAccordingToPatientType()?current.getClassificationAccordingToPatientType().concept.name:'NEW'}</span>
			</div>

			<div class="patient-items">
				<span class="span-left">PATIENT CATEGORY</span>
				<span class="span-right">${current.getClassificationAccordingToTreatmentCategory()?current.getClassificationAccordingToTreatmentCategory().concept.name:'NEW'}</span>
			</div>
		<% } else { %>
			<div class="patient-items">
				<span class="span-left">SECOND LINE NO.</span>
				<span class="span-right">${current.getInitialSecondLineNumberDuringProgram()?current.getInitialSecondLineNumberDuringProgram():'N/A'}</span>
			</div>
			
			<div class="patient-items">
				<span class="span-left">REGISTRATION GROUP</span>
				<span class="span-right">${current.getClassificationAccordingToPreviousDrugUse()?current.getClassificationAccordingToPreviousDrugUse().concept.name:'NEW'}</span>
			</div>
		<% } %>
		
	</div>		
</div>

<div id="em-title" class="">
	<i class='icon-file-alt small'></i>
	<span class="section-title">DIAGNOSIS</span>
	<span class="right" style="padding-right: 15px; padding-top: 10px;"><small><i class=" icon-random  small"></i>Resistance Type: </small><span class="title-answer">${program.program.programId==1?'N/A':'MULTIDRUG RESISTANT'}</span></span>
	<span class="right" style="margin-right: 15px; padding-top: 10px;"><small><i class=" icon-retweet small"></i>Site: </small><span class="title-answer">${current.getCurrentAnatomicalSiteDuringProgram().name}</span></span>
</div>

<div id="em-title-content">
	<table width="100%">
	  <tbody>
		<tr class="table-title">
			<td width="1px">DATE</td>
			<td width="1px">EXAMINATION</td>
			<td width="1px">LAB.NO</td>
			<td>RESULT</td>
		</tr>
		
		<% if (current.program.program.programId == 1) { %>
			<tr class="table-content">
				<td>${ui.formatDatePretty(current.getInitialSputumSmearDateDuringProgram()).toUpperCase()}</td>
				<td>SPUTUM SMEAR</td>
				<td>${current.getInitialLabNumberDuringProgram()}</td>
				<td>${current.getInitialSputumSmearResultDuringProgram().name}</td>
			</tr>		
		<% } %>		  
		
		<tr class="table-content">
			<td>${ui.formatDatePretty(current.getInitialGenXpertDateDuringProgram()).toUpperCase()}</td>
			<td>GENXPERT</td>
			<td>N/A</td>
			<td>${current.getInitialGenXpertResultDuringProgram().name.toString().toUpperCase()}</td>
		</tr>
		
		<tr class="table-content">
			<td>${ui.formatDatePretty(current.getInitialHivTestDateDuringProgram()).toUpperCase()}</td>
			<td>HIV TEST</td>
			<td>N/A</td>
			<td>${current.getInitialHivTestResultDuringProgram().name}</td>
		</tr>
		
		<tr class="table-content">
			<td>${ui.formatDatePretty(current.getInitialXrayDateDuringProgram()).toUpperCase()}</td>
			<td>X-RAY EXAM</td>
			<td>N/A</td>
			<td>${current.getInitialXrayResultDuringProgram().name}</td>
		</tr>
	  </tbody>
	</table>
</div>

<div id="em-title" class="">
	<i class='icon-vitals small'></i>
	<span class="section-title">LATEST VITAL SIGNS</span>
	<span class="right update-vitals" style="margin-top: 6px;"><a class="popups" href="#"><i class="icon-pencil small"></i>Update Vitals</a></span>
</div>

<div id="whv">
	<div class="col1 first-item">
		<div class="tital">WEIGHT</div>
		<div style="display:inline-block; width: 100%">
			<span class="entry">DATE</span>
			<span class="value">${current.getInitialWeightObsDuringProgram()?ui.formatDatePretty(current.getInitialWeightObsDuringProgram().obsDatetime).toUpperCase():'N/A'}</span>
		</div>
		
		<div>
			<span class="entry">RESULTS</span>
			<span class="value">${current.getInitialWeightObsDuringProgram()?current.getInitialWeightObsDuringProgram().valueNumeric:'N/A'}</span>
		</div>
	</div>
	
	<div class="col2 mid-item">
		<div class="tital">HEIGHT</div>
		<div style="display:inline-block; width: 100%">
			<span class="entry">DATE</span>
			<span class="value">${current.getInitialHeightObsDuringProgram()?ui.formatDatePretty(current.getInitialHeightObsDuringProgram().obsDatetime).toUpperCase():'N/A'}</span>
		</div>
		
		<div>
			<span class="entry">RESULTS</span>
			<span class="value">${current.getInitialHeightObsDuringProgram()?current.getInitialHeightObsDuringProgram().valueNumeric:'N/A'}</span>
		</div>
	</div>
</div>

<div id="em-title" class="">
	<i class='icon-medicine small'></i>
	<span class="section-title">TREATMENT STATUS</span>
	<span class="section-descr"> :: ${details.regimen?'ON TREATMENT':'CURRENTLY NOT ON TREATMENT'} </span>
	<span class="right update-treatment" style="margin-top: 6px;"><a class="popups" href="#"><i class="icon-pencil small"></i>Update Treatment</a></span>
</div>

<div id="em-title-content">
	<table width="100%">
	  <tbody>
		  <tr class="table-title">
			<td width="1px">TREATMENT PERIOD</td>
			<td width="15%">TYPE</td>
			<td width="25%">DOSAGE</td>
			<td width="*">NOTES</td>
		  </tr>
		  
		  <% if (details.regimen){ %>
			<% regimens.eachWithIndex { regx, index -> %>
				<tr class="table-content">
				  <td>${ui.formatDatePretty(regx.startedOn).toUpperCase()} &mdash; ${regx.finishedOn?ui.formatDatePretty(regx.finishedOn).toUpperCase():'ACTIVE'}</td>
				  <td>${regx.type.name}</td>
				  <td>${regx.name}</td>
				  <td>${regx.remarks.toUpperCase()}</td>
				</tr>
			<% } %>
		  <% } else { %>
			<tr class="table-content">
			  <td colspan="4" class="warning">No treatment Regimen Records Found</td>
			</tr>		  
		  <% } %>		  
		  
		  <tr class="table-content">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
		  </tr>
	  </tbody>
	</table>
</div>

<div class="clear"></div>

<div id="update-regimen-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>
        <h3>UPDATE REGIMEN</h3>
    </div>

    <div class="dialog-content">
        <ul>
			<li>
				<label for="regimenPatient">
					Patient :
				</label>
				<input type="text" name="regimen.patient" id="regimenPatient" readonly="" value="${patient.familyName} ${patient.givenName} ${patient.middleName ? patient.middleName : ''}" />
			</li>
			
			<li>
				<label for="regimenCurrent">
					Current :
				</label>
				<input type="text" name="regimen.name" id="regimenCurrent" readonly="" value="${details.regimen?details.regimen.name:'NOT ON TREATMENT'}" />
			</li>
			
			<li>
				<div style="width: 100%; border-top: 1px dotted; margin: 5px 0;"></div>
			</li>
				
            <li>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'regimen.date', id: 'regimen-date', label: 'Start Date:', useTime: false, defaultToday: true, endDate: new Date()])}
            </li>
			
			<li>
				<label for="regimenCurrent">
					Type :
				</label>
				<select id="regimenType" class="required" name="regimen.type">
					<option value="">&nbsp;</option>
					<% regimenTypes.eachWithIndex { types, index -> %>
						<option value="${types.answerConcept}">${types.answerConcept.name.toString().toUpperCase()}</option>
					<% } %>
				</select>
			</li>
			
			<li>
				<label for="regimenNew">
					Regimen :
				</label>
				
				<% if (program.program.programId == 2) { %>
					<select id="regimenSelect">
						<option value="">&nbsp;</option>
					</select>				
				<% } %>
				<input type="text" name="regimen.new" id="regimenNew" readonly="" placeholder="Specify Regimen" />
			</li>
			
			<li>
				<label for="regimenRemarks">
					Remarks :
				</label>
				<textarea id="regimenRemarks" name="regimen.remarks" placeholder="Remarks" style="height:100px; resize:none;"></textarea>
			</li>
        </ul>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>