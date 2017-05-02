<div id="em-title" class="visits">
	<i class='icon-file-alt small'></i>
	<span class="section-title">VISIT INFORMATION</span>
	<% if (current.program.program.programId == 1) { %>
		<span class="right update-vitals" style="margin-top: 6px;"><a class="popups add-visit href="#"><i class="icon-pencil small"></i>Add Visit</a></span>
	<% } else { %>
		<div style="float: right; margin-top: -3px;">
			<div style="position: static" class="dropdown">
				<span class="dropdown-name"><i class="icon-cog"></i>Actions<i class="icon-sort-down"></i></span>
				<ul style="z-index: 99">
					<li><a data-value="1"><i class="icon-file-text"></i>Add GenXpert</a></li>
					<li><a data-value="2"><i class="icon-file-text"></i>Add Culture</a></li>
					<li><a data-value="3"><i class="icon-retweet"></i>Add D.S.T</a></li>
					<li><a data-value="4"><i class="icon-reply"></i>Exit from Program</a></li>
				</ul>
			</div>
		</div>
	<% } %>
</div>

<div class="aside">
	<ul id="ul-left-menu" class="left-menu">
		<% visitSummaries.each { summary -> %>
			<li class="menu-item visit-summary" visitid="54">
				<input type="hidden" class="encounter-id" value="${summary.encounterId}" >
				<span class="menu-date">
					<i class="icon-time"></i>
					${ui.formatDatetimePretty(summary.encounterDatetime)}
				</span>
				<span class="menu-title">
					<i class="icon-retweet"></i>
					${summary.encounterType.description}
				</span>
				<span class="arrow-border"></span>
				<span class="arrow"></span>
			</li>
		<% } %>
	
		<li class="menu-item">
			&nbsp;
		</li>
	</ul>
</div>

<div class="content dashboard" style="margin-left: 0px;">
	<div class="info-section" id="visit-detail">
		
	</div>
	
	<div class="info-section" id="visit-dst">
		
	</div>
</div>

<div class="clear"></div>

<script id="visit-detail-template" type="text/template">
	<div class="info-header">
		<i class="icon-user-md"></i>
		<h3>VISIT SUMMARY INFORMATION</h3>
	</div>

	<div class="info-body">
		<label><span class='status active'></span>LOCATION:</label>
		<span>{{-location}}</span>
		<br/>
		
		<label><span class='status active'></span>DATE:</label>
		<span>{{-date}}</span>
		<br/>
		
		{{ if(facility !== '' ) { }}
			<label><span class='status active'></span>FACILITY:</label>
			<span>{{-facility}}</span>
			<br/>
		{{ } }}
		
		<div style="min-height: 7px;"></div>
		
		{{ if(weight > 0 ) { }}
			<label><span class='status active'></span>WEIGHT:</label>
			<span>{{-weight}}</span>
			<br/>		
		{{ } }}

		{{ if(height > 0 ) { }}
			<label><span class="status active"></span>HEIGHT:</label>
			<span>{{-height}}</span>
			<br/>
		{{ } }}
		
		{{ if(bmi > 0 ) { }}
			<label><span class="status active"></span>B.M.I:</label>
			<span>{{-bmi}}</span>
			<br/>
		{{ } }}
		
		{{ if(muac > 0 ) { }}		
			<label><span class="status active"></span>M.U.A.C:</label>
			<span>{{-muac}}</span>
			<br/>
		{{ } }}
	</div>
	
	{{ if(showTests == 1) { }}
		<div class="info-header" style="margin-top: 15px;">
			<i class="icon-beaker"></i>
			<h3>EXAMINATION DETAILS</h3>
		</div>
		
		<div class="info-body">
			{{ if(labNumber !== '' ) { }}
				<label><span class='status active'></span>LAB NUMBER:</label>
				<span>{{-labNumber}}</span>
				<br/>
			{{ } }}
			
			{{ if(sputumSmear !== '' ) { }}
				<label><span class='status active'></span>SPUTUM SMEAR:</label>
				<span>{{-sputumSmear!=''?sputumSmear:'Not Done'}}</span>
				<br/>
			{{ } }}
			
			{{ if(genXpert !== '' ) { }}
				<label><span class='status active'></span>GENXPERT:</label>
				<span>{{-genXpert!=''?genXpert:'Not Done'}}</span>
				<br/>
			{{ } }}
			
			{{ if(culture !== '' ) { }}
				<label><span class='status active'></span>CULTURE:</label>
				<span>{{-culture!=''?culture:'Not Done'}}</span>
				<br/>
			{{ } }}
			
			{{ if(hivExam !== '' ) { }}
				<label><span class='status active'></span>HIV TEST:</label>
				<span>{{-hivExam!=''?hivExam:'Not Done'}}</span>
				<br/>
			{{ } }}
			
			{{ if(artStarted !== '' ) { }}
				<label>&nbsp;</label>
				<span><small>x {{-artStarted}}</small></span>
				<br/>
			{{ } }}
			
			{{ if(cptStarted !== '' ) { }}
				<label>&nbsp;</label>
				<span><small>x {{-cptStarted}}</small></span>
				<br/>
			{{ } }}
			
			{{ if(xrayExam !== '' ) { }}
				<label><span class='status active'></span>X-RAY EXAM:</label>
				<span>{{-xrayExam!=''?xrayExam:'Not Done'}}</span>
				<br/>
			{{ } }}
			
			{{ if(drugTest !== '' ) { }}
				<label><span class='status active'></span>DRUG SENSITIVITY:</label>
				<span>{{-drugTest!=''?drugTest:'Not Done'}}</span>
				<br/>
			{{ } }}
		</div>	
	{{ } }}
</script>

<script id="visit-dst-template" type="text/template">		
	{{ if (drugTest.length > 0 ) { }}
		<div class="info-header" style="margin-top: 15px;">
			<i class="icon-medicine"></i>
			<h3>DRUGS SUSCEPTIBILITY TEST</h3>
		</div>

		<table id="drugList">
			<thead>
				<tr style="border-bottom: 1px solid #eee;">
					<th style="width: 1px;">#</th>
					<th style="width: 1px;">DRUG</th>
					<th>RESULTS</th>
				</tr>
			</thead>
			<tbody>
			{{ _.each(drugTest, function(drug, index) { }}
				<tr style="border: 1px solid #eee; font-size: 0.95em;">
					<td style="border: 1px solid #eee; padding: 5px 10px; margin: 0;">{{=index+1}}.</td>
					<td style="border: 1px solid #eee; padding: 5px 10px; margin: 0;">{{-drug.drugName}}</td>
					<td style="border: 1px solid #eee; padding: 5px 10px; margin: 0;">{{-drug.testResult}}</td>
				</tr>
			{{ }); }}
			</tbody>
		</table>
	{{ } }}
</script>

<div id="visit-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>
        <h3>UPDATE VISIT</h3>
    </div>

    <div class="dialog-content">
        <ul>
			<li>
				<label for="LabNumber">
					Lab. Number :
				</label>
				<input id="LabNumber" class="required" name="exams.lab.number" type="text" placeholder="Laboratory Number" />
			</li>
			
            <li>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.sputum.date', id: 'sputum-date', label: 'Exam Date:', useTime: false, defaultToday: true, endDate: new Date()])}
            </li>
			
			<li>
				<label for="sputumResult">
					Microscopy Result:
				</label>
				
				<select id="sputumResult" class="required" name="exams.sputum.result">
					<option value="">&nbsp;</option>
					<% smearResults.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name.toString().toUpperCase()}</option>
					<% } %>
				</select>
			</li>
			
			<li class="outcome" style="width:100%; border-top:1px dotted; padding-top:6px; margin-top:15px;">
				<label for="outcomeResults">
					Treatment Outcome:
				</label>
				
				<select id="outcomeResults" class="required" name="program.outcome">
					<option value="">&nbsp;</option>
					<% if (program.program.programId == 1) {%>
						<% tbbOutcomes.eachWithIndex { outcome, index -> %>
							<option value="${outcome.uuid}">${outcome.concept.name.toString().toUpperCase()}</option>
						<% } %>
					<% } else { %>
						<% mdrOutcomes.eachWithIndex { outcome, index -> %>
							<option value="${outcome.uuid}">${outcome.concept.name.toString().toUpperCase()}</option>
						<% } %>
					<% } %>
				</select>
			</li>
			
			<li class="outcome">
				<label for="outcomeRemarks">
					Outcome Remarks :
				</label>
				<textarea id="outcomeRemarks" name="program.remarks" placeholder="Remarks" style="height:100px; resize:none;"></textarea>
			</li>
			
			<li>
				<label style="width: 100%; border-top: 1px dotted; margin-top: 6px;">
					<input type="checkbox" id="lastVisit" style="margin-left: 168px;"> This is the Last Visit
				</label>
			</li>
        </ul>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>

<div id="genxpert-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>
        <h3>UPDATE GENXPERT</h3>
    </div>
    <div class="dialog-content">
        <ul>
			<li>
				<label for="genxpertLabNumber">
					Lab. Number :
				</label>
				<input id="genxpertLabNumber" class="required" name="exams.lab.number" type="text" placeholder="Laboratory Number" />
			</li>
			
            <li>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.sputum.date', id: 'genxpert-date', label: 'Exam Date:', useTime: false, defaultToday: true, endDate: new Date()])}
            </li>
			
			<li>
				<label for="genxpertResult">
					GenXpert Result:
				</label>
				
				<select id="genxpertResult" class="required">
					<option value="">&nbsp;</option>
					<% genXpertResults.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name}</option>
					<% } %>
				</select>
			</li>
        </ul>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>

<div id="culture-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>
        <h3>UPDATE CULTURE</h3>
    </div>
    <div class="dialog-content">
        <ul>
			<li>
				<label for="cultureLabNumber">
					Lab. Number :
				</label>
				<input id="cultureLabNumber" class="required" type="text" placeholder="Laboratory Number" />
			</li>
			
            <li>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exams.sputum.date', id: 'culture-date', label: 'Exam Date:', useTime: false, defaultToday: true, endDate: new Date()])}
            </li>
			
			<li>
				<label for="cultureResult">
					Culture Result:
				</label>
				
				<select id="cultureResult" class="required">
					<option value="">&nbsp;</option>
					<% cultureResults.eachWithIndex { sites, index -> %>
						<option value="${sites.answerConcept.id}" data-uuid="${sites.answerConcept.uuid}">${sites.answerConcept.name}</option>
					<% } %>
				</select>
			</li>
        </ul>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>

<div id="dst-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>
        <h3>UPDATE DST</h3>
    </div>
    <div class="dialog-content">
		<form id="dst-form">
			<ul>
				<li>
					<label for="dstLabNumber">
						Lab. Number :
					</label>
					<input type="text" name="wrap.labNumber" id="dstLabNumber" placeholder="Laboratory Number" />
					<input type="hidden" name="wrap.patient" value="${patient.id}" />
				</li>
				
				<li>
					${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'wrap.testDate', id: 'dst-date', label: 'Date Completed:', useTime: false, defaultToday: true, endDate: new Date()])}
				</li>
				
				<li>
					<table id="dstTable">
						<thead>
							<tr>
								<th style="width: 133px;">DRUG</td>
								<th title="Drug Resistance">DR</td>
								<th title="Drug Sensitive">DS</td>
								<th title="Culture Contaminated">CC</td>
								<th title="Not Done">ND</td>
							</tr>
						</thead>
						<tbody>
							<% dstDrugs.eachWithIndex { drug, index -> %>
								<tr>
									<td>${drug.name}</td>
									<td><label><input type="radio" data-value="Resistant" name="results.${drug.conceptId}" value="78"/></label></td>
									<td><label><input type="radio" data-value="Resistant" name="results.${drug.conceptId}" value="79"/></label></td>
									<td><label><input type="radio" data-value="Resistant" name="results.${drug.conceptId}" value="77"/></label></td>
									<td><label><input type="radio" data-value="Resistant" name="results.${drug.conceptId}" value="30"/></label></td>
								</tr>
							<% } %>
						</tbody>
					</table>
				</li>
			</ul>		
		</form>

        <label class="button confirm right" style="margin-right:0px">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>

<div id="outcome-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>
        <h3>EXIT FROM PROGRAM</h3>
    </div>

    <div class="dialog-content">
        <ul>
			<li>
				<label for="dstLabNumber">
					Patient :
				</label>
				<input type="text" name="patient.name" id="patientName" readonly="" value="${patient.familyName} ${patient.givenName} ${patient.middleName ? patient.middleName : ''}" />
			</li>
				
            <li>
				${ui.includeFragment("uicommons", "field/datetimepicker", [formFieldName: 'exit.date', id: 'exit-date', label: 'Exit Date:', useTime: false, defaultToday: true, endDate: new Date()])}
            </li>
			
			<li>
				<label for="MdrtbOutcome">
					Outcome:
				</label>
				
				<select id="MdrtbOutcome" class="required" name="program.outcome">
					<option value="">&nbsp;</option>
					<% mdrOutcomes.eachWithIndex { outcome, index -> %>
						<option value="${outcome.uuid}">${outcome.concept.name.toString().toUpperCase()}</option>
					<% } %>					
				</select>
			</li>
			
			<li>
				<label for="MdrtbRemarks">
					Outcome Remarks :
				</label>
				<textarea id="MdrtbRemarks" name="program.remarks" placeholder="Remarks" style="height:100px; resize:none;"></textarea>
			</li>
        </ul>

        <label class="button confirm right">Confirm</label>
        <label class="button cancel">Cancel</label>
    </div>
</div>