<form method="post" id="intake" class="simple-form-ui" style="margin-top:10px;" action="intake.page?patientId=17&patientProgramId=20&encounterId=-1">
	<section>
		<span class="title">Intake Form</span>
		<fieldset>
			<legend>Visit Details</legend>			
			<field>
				<label for="locationFacility">
					Health Facility:
					<span class="mandatory">*</span>
				</label>
				<input id="locationFacility" class="required" name="treatment.facility" type="text" placeholder="Treatment Facility">
				<input id="locationName" name="treatment.location" type="hidden" value="${location.name}" readonly="" />
				<input name="patient.id" type="hidden" value="${patient.id}" />
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
					${patient.age>5?'B.M.I :':'M.U.A.C :'}
					<span class="mandatory">*</span>
				</label>
				<input id="bmi" class="required" type="text" ${patient.age>5?'name="vitals.bmi" placeholder="B.M.I" readonly=""':'name="vitals.muac" placeholder="M.U.A.C"'}>
				<span class="append-label">${patient.age>5?'':'CM'}</span>
			</field>
		</fieldset>
		
		<fieldset>
			<legend>Treatment</legend>
			<field>
				<label for="treatmentSupporter">
					Treatment Supporter:
					<span class="mandatory">*</span>
				</label>
				<input id="treatmentSupporter" class="required" name="treatment.supporter" type="text" placeholder="Treatment Supporter (Damiin)">
			</field>
			
			<field>
				<label for="treatmentReferral">
					Referred By:
					<span class="mandatory">*</span>
				</label>
				
				<select id="treatmentReferral" class="required" name="treatment.referral">
					<option value="">&nbsp;</option>
					<% referringDepartments.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name}</option>
					<% } %>
				</select>
			</field>
			
			<field>
				<label for="treatmentDots">
					D.O.T.S By:
					<span class="mandatory">*</span>
				</label>
				<select id="treatmentDots" class="required" name="treatment.dots">
					<option value="">&nbsp;</option>
					<% directObservers.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name.toString().toUpperCase()}</option>
					<% } %>
				</select>
			</field>
			
			<field>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'treatment.started', id: 'date-started', label: 'Started On:', useTime: false, defaultToday: true, endDate: new Date()])}
			</field>
			
			<field>
				<label for="treatmentSite">
					Disease Site:
					<span class="mandatory">*</span>
				</label>
				
				<select id="treatmentSite" class="required" name="treatment.site">
					<option value="">&nbsp;</option>
					<% anatomicalSites.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name}</option>
					<% } %>
				</select>
			</field>
		</fieldset>		
	</section>
	
	<section>
		<span class="title">Examinations</span>
		<fieldset>
			<legend>Sputum Smear</legend>
			<field>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.sputum.date', id: 'sputum-date', label: 'Exam Date:', useTime: false, defaultToday: false, endDate: new Date()])}
			</field>
			
			<field>
				<label for="LabNumber">
					Lab. No.:
					<span class="mandatory">*</span>
				</label>
				<input id="LabNumber" class="required" name="exams.lab.number" type="text" placeholder="Laboratory Number" />
			</field>
			
			<field>
				<label for="sputumResult">
					Microscopy Result:
					<span class="mandatory">*</span>
				</label>
				
				<select id="sputumResult" class="required" name="exams.sputum.result">
					<option value="">&nbsp;</option>
					<% smearResults.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name.toString().toUpperCase()}</option>
					<% } %>
				</select>
			</field>
		</fieldset>
		
		<fieldset>
			<legend>GenXpert</legend>
			<field>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.genxpert.date', id: 'genXpertDate', label: 'Exam Date:', useTime: false, defaultToday: false, endDate: new Date()])}
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
		</fieldset>
		
		<fieldset>
			<legend>HIV Result</legend>
			<field>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.hiv.date', id: 'hivDate', label: 'Exam Date:', useTime: false, defaultToday: false, endDate: new Date()])}
			</field>
			
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
					${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.art.date', id: 'artDate', label: 'Exam Date:', useTime: false, defaultToday: false, endDate: new Date()])}
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
					${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.cpt.date', id: 'cptDate', label: 'Exam Date:', useTime: false, defaultToday: false, endDate: new Date()])}
				</field>
			</div>
			
		</fieldset>
		
		<fieldset>
			<legend>X-ray Result</legend>
			<field>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.xray.date', id: 'xrayDate', label: 'Exam Date:', useTime: false, defaultToday: false, endDate: new Date()])}
			</field>
			
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
		</fieldset>
	</section>
	
	<div id="confirmation">
		<span class="title">Summary Page</span>
		<div id="confirmationQuestion" class="focused" style="margin-top:20px">		
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