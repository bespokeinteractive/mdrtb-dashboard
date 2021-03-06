<form method="post" id="intake" class="simple-form-ui" style="margin-top:10px;" action="intake.page?patientId=17&patientProgramId=20&encounterId=-1">
	<section>
		<span class="title">Intake Form</span>
		<fieldset id="visit-description">
			<legend>Visit Details</legend>
			<field>
				<label for="secondlineNumber">
					Second Line No. :
					<span class="mandatory">*</span>
				</label>
				<input id="secondlineNumber" class="required" name="register.number" type="text" placeholder="Second Line Register Number">
			</field>
			
			<field>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'register.date', id: 'register-date', label: 'Register Date:', useTime: false, defaultToday: true, endDate: new Date()])}
			</field>
			
			<field>
				<label for="locationFacility">
					Health Facility:
					<span class="mandatory">*</span>
				</label>
				<select id="locationFacility" class="required" name="treatment.facility">
					<% facilities.eachWithIndex { fac, index -> %>
						<option value="${fac.id}">${fac.name.toString().toUpperCase()}</option>
					<% } %>
				</select>
				<input id="locationName" name="treatment.location" type="hidden" value="${location.name}" readonly="" class="locations" />
				<input name="patient.id" type="hidden" value="${patient.id}" />
			</field>
			
			<field>
				<label for="registerNumber">
					Register Number :
				</label>
				<input id="registerNumber" name="facility.number" type="text" placeholder="Facilty Register Number">
				<div class="separater"></div>
			</field>
			
			<field>
				<label for="vitalsWeight">
					Weight :
					<span class="mandatory">*</span>
				</label>
				<input id="vitalsWeight" class="required" name="vitals.weight" type="text" placeholder="Weight">
				<span class="append-label">KG</span>
			</field>
			
			<field>
				<label for="vitalsHeight">
					Height :
					<span class="mandatory">*</span>
				</label>
				<input id="vitalsHeight" class="required" name="vitals.height" type="text" placeholder="Height">
				<span class="append-label">CM</span>
			</field>
			
			<field>
				<label for="bmi">
					${patient.age>5?"B.M.I":"MUAC"} :
					<span class="mandatory">*</span>
				</label>
				<input id="${patient.age>5?'bmi':'muac'}" class="required" type="text" name="vitals.${patient.age>5?'bmi':'muac'}" placeholder="${patient.age>5?'B.M.I':'M.U.A.C'}" ${patient.age>5?'readonly=""':''} />
				<span class="append-label">${patient.age>5?'':'CM'}</span>
				<div class="separater"></div>
			</field>
			
			<field>
				<label for="regimenType">
					Type :
					<span class="mandatory">*</span>
				</label>
				
				<select id="regimenType" class="required" name="regimen.type">
					<option value="">&nbsp;</option>
					<% regimenTypes.eachWithIndex { types, index -> %>
						<option value="${types.answerConcept.id}" data-uuid="${types.answerConcept.uuid}">${types.answerConcept.name.toString().toUpperCase()}</option>
					<% } %>
				</select>
			</field>
			
			<field>
				<label for="regimenSelect">
					Regimen :
					<span class="mandatory">*</span>
				</label>
				<input id="regimenText" class="required" name="regimen.name" type="text" placeholder="Specify Regimen">
				<select id="regimenSelect">
					<option value="">&nbsp;</option>
				</select>
			</field>
			
			<field>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'regimen.started', id: 'regimen-started', label: 'Start Date:', useTime: false, defaultToday: true, endDate: new Date()])}
			</field>			
		</fieldset>
		
		<fieldset id = "treatment-description">
			<legend>Treatment</legend>
			<field>
				<label for="treatmentSupporter">
					Treatment Supporter:
					<span class="mandatory">*</span>
				</label>
				<input id="treatmentSupporter" class="required" name="treatment.supporter" type="text" placeholder="Treatment Supporter (Damiin)">
			</field>
			
			<field>
				<label for="treatmentSupporterContacts">
					Supporter Contacts:
					<span class="mandatory"></span>
				</label>
				<input id="treatmentSupporterContacts" class="" name="treatment.supporter.contacts" type="text" placeholder="Damiin Contacts" style="margin-bottom: 15px;">
			</field>
			
			<field>
				<label for="treatmentReferral">
					Referred By:
					<span class="mandatory">*</span>
				</label>
				
				<select id="treatmentReferral" class="required" name="treatment.referral">
					<option value="">&nbsp;</option>
					<% referringDepartments.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name.toString().toUpperCase()}</option>
					<% } %>
				</select>
			</field>
			
			<field>
				<label for="treatmentDots">
					D.O.T.S By:
					<span class="mandatory">*</span>
				</label>
				<select id="treatmentDots" class="required" name="treatment.dots">
					<option value="1160667" data-uuid="5234b4e7-d544-4e18-b79f-78c5df68fd3d">TB CENTER / TBMU</option>
				</select>
			</field>
		</fieldset>		
	</section>
	
	<section>
		<span class="title">Examinations</span>
		<fieldset id = "genXpert-description">
			<legend>GenXpert</legend>
			<field>
				<label for="LabNumber">
					Lab. No.:
					<span class="mandatory">*</span>
				</label>
				<input id="LabNumber" class="required" name="exams.lab.number" type="text" placeholder="Laboratory Number" />
			</field>
			
			<field>
				<label for="genXpertResult">
					GenXpert Result:
					<span class="mandatory">*</span>
				</label>
				
				<select id="genXpertResult" class="required" name="exams.genxpert.result">
					<option value="">&nbsp;</option>
					<% genXpertResults.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name}</option>
					<% } %>
				</select>
			</field>
			
			<field class="genxpert-date">
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.genxpert.date', id: 'genXpertDate', label: 'Exam Date:', useTime: false, defaultToday: true, endDate: new Date()])}
			</field>			
		</fieldset>
		
		<fieldset id="hivResult-description">
			<legend>HIV Result</legend>
			<field>
				<label for="hivResult">
					HIV Result:
					<span class="mandatory">*</span>
				</label>
				
				<select id="hivResult" class="required" name="exams.hiv.result">
					<option value="">&nbsp;</option>
					<% hivTestResults.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name}</option>
					<% } %>
				</select>
			</field>
			
			<field class="hivtest-date">
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.hiv.date', id: 'hivDate', label: 'Exam Date:', useTime: false, defaultToday: true, endDate: new Date()])}
			</field>
			
			<div class="hiv-positive-section" style="display:none;">
				<div class="separater"></div>
			
				<field>
					<label for="artStatus">
						Started on ART:
						<span class="mandatory">*</span>
					</label>
					
					<select id="artStatus" name="exams.art.started">
						<option value="">&nbsp;</option>
						<option value="127">YES</option>
						<option value="126">NO</option>
					</select>
				</field>
				
				<field>
					${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.art.date', id: 'artDate', label: 'ART Start Date:', useTime: false, defaultToday: true, endDate: new Date()])}
				</field>
				
				<field>
					<label for="cptStatus">
						Started on CPT:
						<span class="mandatory">*</span>
					</label>
					
					<select id="cptStatus" name="exams.cpt.started">
						<option value="">&nbsp;</option>
						<option value="127">YES</option>
						<option value="126">NO</option>
					</select>
				</field>
				
				<field>
					${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.cpt.date', id: 'cptDate', label: 'CPT Start Date:', useTime: false, defaultToday: true, endDate: new Date()])}
				</field>
			</div>
			
		</fieldset>
		
		<fieldset id = "xrayresult-description">
			<legend>X-ray Result</legend>
			<field>
				<label for="xray-result">
					X-ray Result:
					<span class="mandatory">*</span>
				</label>
				
				<select id="xray-result" class="required" name="exams.xray.result">
					<option value="">&nbsp;</option>
					<% xrayTestResults.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name}</option>
					<% } %>
				</select>
				</select>
			</field>
			
			<field class="xray-date">
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.xray.date', id: 'xrayDate', label: 'Exam Date:', useTime: false, defaultToday: true, endDate: new Date()])}
			</field>			
		</fieldset>
	</section>
	
	<div id="confirmation">
		<span class="title">Summary Page</span>
		<div id="confirmationQuestion" class="focused" style="margin-top:20px">
			<div class="dashboard">
				<div class="info-section">
					<div class="info-header">
						<i class="icon-list-ul"></i>
						<h3>TB INTAKE SUMMARY &amp; CONFIRMATION</h3>
					</div>

					<div class="info-body">
						<table id="summaryTable">
							<tbody>
								<tr>
									<td><span class="status active"></span>Register No.</td>
									<td>N/A</td>
								</tr>
								
								<tr>
									<td><span class="status active"></span>Facility</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>Weight</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>Height</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>${patient.age>5?'B.M.I':'M.U.A.C'}</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>Supporter</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>Referred by</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>D.O.T.S</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>Disease Sites</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>Lab. No.:</td>
									<td>N/A</td>
								</tr>

								<tr style="${program.program.programId == 2?'display:none':''}">
									<td><span class="status active"></span>Sputum Smear</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>GenXpert Result</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>HIV Result</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>Started on ART:</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>Started on CPT:</td>
									<td>N/A</td>
								</tr>

								<tr>
									<td><span class="status active"></span>X-Ray Result</td>
									<td>N/A</td>
								</tr>

							</tbody>
						</table>

					</div>
				</div>
			</div>
		
		
		
			<field style="display: inline"> 
				<button class="button submit confirm" style="display: none;"></button>
			</field>
			
			<span value="Submit" class="button submit confirm" id="antenatalExaminationSubmitButton">
                <i class="icon-save small"></i>
                Save
            </span>
			
            <span id="cancelButton" class="button cancel">
                <i class="icon-remove small"></i>			
				Cancel
			</span>
		</div>
	</div>
</form>