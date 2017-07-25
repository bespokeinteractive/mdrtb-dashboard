<form method="post" id="visit" class="simple-form-ui" style="margin-top:10px;" action="addVisit.page?patient=${patient.patientId}">
	<section>
		<span class="title">Visit Details</span>
		<fieldset id="visit-description">
			<legend>Vitals/Regimen</legend>
			
			<field>
				<label for="vitalsWeight">
					Weight :
					<span class="mandatory"></span>
				</label>
				<input id="vitalsWeight" class="" name="vitals.weight" type="text" placeholder="Weight">
				<span class="append-label">KG</span>
				<input type="hidden" name="patient.id" value="${patient.patientId}">
			</field>
			
			<field>
				<label for="vitalsHeight">
					Height :
					<span class="mandatory"></span>
				</label>
				<input id="vitalsHeight" class="" name="vitals.height" type="text" placeholder="Height">
				<span class="append-label">CM</span>
			</field>
			
			<field>
				<label for="bmi">
					${patient.age>5?"B.M.I":"MUAC"} :
					<span class="mandatory"></span>
				</label>
				<input id="${patient.age>5?'bmi':'muac'}" class="" type="text" name="vitals.${patient.age>5?'bmi':'muac'}" placeholder="${patient.age>5?'B.M.I':'M.U.A.C'}" ${patient.age>5?'readonly=""':''} />
				<span class="append-label">${patient.age>5?'':'CM'}</span>
				<div class="separater"></div>
			</field>
			
			<div>
				<label for="regimenCurrent">
					Regimen :
					<span class="mandatory"></span>
				</label>
				<input id="regimenCurrent" class="" name="regimen.current" type="text" readonly="" value="${details.regimen?details.regimen.name:'NOT ON TREATMENT'}">
				<div id="regimenTypeName">${details.regimen?details.regimen.type.name:''}</div>
			</div>
			
			<field>
				<label for="regimenType">
					Change To :
					<span class="mandatory"></span>
				</label>
				
				<select id="regimenType" class="" name="regimen.type">
					<option value="">&nbsp;</option>
					<% regimenTypes.eachWithIndex { types, index -> %>
						<option value="${types.answerConcept.id}" data-uuid="${types.answerConcept.uuid}">${types.answerConcept.name.toString().toUpperCase()}</option>
					<% } %>
				</select>
			</field>
			
			<field>
				<label for="regimenSelect">Regimen :</label>
				<input id="regimenText" class="" name="regimen.name" type="text" placeholder="Specify Regimen">
				<select id="regimenSelect">
					<option value="">&nbsp;</option>
				</select>
			</field>
			
			<div>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'regimen.started', id: 'regimen-started', label: 'Start Date:', useTime: false, defaultToday: true, endDate: new Date()])}
			</div>
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
			
			<div class="genxpert-date">
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.genxpert.date', id: 'genXpertDate', label: 'Exam Date:', useTime: false, defaultToday: true, endDate: new Date()])}
			</div>			
		</fieldset>
		
		<fieldset id = "hivResult-description">
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
			
			<div class="hivtest-date">
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.hiv.date', id: 'hivDate', label: 'Exam Date:', useTime: false, defaultToday: true, endDate: new Date()])}
			</div>
			
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
				
				<div>
					${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.art.date', id: 'artDate', label: 'ART Start Date:', useTime: false, defaultToday: true, endDate: new Date()])}
				</div>
				
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
				
				<div>
					${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.cpt.date', id: 'cptDate', label: 'CPT Start Date:', useTime: false, defaultToday: true, endDate: new Date()])}
				</div>
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

								<tr >
									<td><span class="status active"></span>Regimen</td>
									<td>${details.regimen?details.regimen.name:'N/A'}</td>
								</tr>

								<tr>
									<td><span class="status active"></span>Lab. No.:</td>
									<td>N/A</td>
								</tr>

								<tr>
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