<div id="em-title" class="visits">
	<i class='icon-file-alt small'></i>
	<span class="section-title">VISIT INFORMATION</span>
	<span class="right update-vitals" style="margin-top: 6px;"><a class="popups ${current.program.program.programId == 1?'add-visit':'choose-visit'}" href="#"><i class="icon-pencil small"></i>Add Visit</a></span>
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
		</div>	
	{{ } }}
</script>

<div id="visit-dialog" class="dialog" style="display:none;">
    <div class="dialog-header">
        <i class="icon-folder-open"></i>
        <h3>ADD VISIT</h3>
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