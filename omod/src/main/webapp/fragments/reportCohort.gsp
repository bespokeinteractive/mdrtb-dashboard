<style>
	#tb6 tr:first-child td,
	#tb6 tr:nth-child(2) td {
		font-weight: bold;
		font-size: 0.9em;
		text-align: center;
	}
	#tb6 tr td:nth-child(1){
		font-weight: bold;
		font-size: 0.9em;
		text-transform: uppercase;
	}
	#tb7 {
		width: 60%;
		margin: 0 0;
	}
</style>

<p>
	Somalia TB Control Program
	<span style="float:right;">TB 10</span>
</p>

<p class="reportTitle" style="text-align: center;">TB10 Quarterly report on TB treatment outcome in Basic management Unit</p>

<table id="tb1">
	<tr>
		<td>
			<div>
				Name of  MBU:<span class="underline" style="width: 130px">&nbsp;</span>
				Facility:<span class="underline" style="width: 170px"><small>&nbsp; ${facility.name}</small></span>
			</div>
			
			<div style="margin: 10px 0">
				Name of the TB Coordinator: <span class="underline" style="width: 255px">&nbsp;</span>
			</div>
		</td>
		
		<td>
			<div>
				Patients registered during:<span class="underline" style="width: 285px">&nbsp; <small>${mnth}<sup>${sups}</sup> QTR, ${year}</small></span>
			</div>
			
			<div style="margin: 10px 0">
				Date of completion of this form : <span class="underline" style="width: 240px">&nbsp; <small>${date}</small></span>
			</div>
		</td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 1: All TB cases registered during the quarter</p>
<table id="tb6">
	<tr>
		<td rowspan="2"></td>		
		<td rowspan="2">Number of cases<br/>registered</td>
		<td colspan="6">Treatment outcomes</td>
	</tr>
	
	<tr>
		<td><small>CURED</small></td>
		<td><small>COMPLETED</small></td>
		<td><small>FAILED</small></td>
		<td><small>DIED</small></td>
		<td><small>LOST TO FOLLOWUP</small></td>
		<td><small>NOT EVALUATED</small></td>
	</tr>
	
	<tr>
		<td>Bacteriologically<br/>confirmed, new<br/>and relapse</td>		
		<td style="width:10%"><small>${report.bcnrCured + report.bcnrComplete + report.bcnrFailed + report.bcnrDied + report.bcnrLost + report.bcnrOther}</small></td>
		<td style="width:10%"><small>${report.bcnrCured}</small></td>
		<td style="width:10%"><small>${report.bcnrComplete}</small></td>
		<td style="width:10%"><small>${report.bcnrFailed}</small></td>
		<td style="width:10%"><small>${report.bcnrDied}</small></td>
		<td style="width:10%"><small>${report.bcnrLost}</small></td>
		<td style="width:10%"><small>${report.bcnrOther}</small></td>
	</tr>
	<tr>
		<td>Clinically<br/>diagnosed, new<br/>and relapse</td>		
		<td style="width:10%"><small>${report.cdnrCured + report.cdnrComplete + report.cdnrFailed + report.cdnrDied + report.cdnrLost + report.cdnrOther}</small></td>
		<td style="width:10%"><small>${report.cdnrCured}</small></td>
		<td style="width:10%"><small>${report.cdnrComplete}</small></td>
		<td style="width:10%"><small>${report.cdnrFailed}</small></td>
		<td style="width:10%"><small>${report.cdnrDied}</small></td>
		<td style="width:10%"><small>${report.cdnrLost}</small></td>
		<td style="width:10%"><small>${report.cdnrOther}</small></td>
	</tr>
	<tr>
		<td>Retreatment<br/>excluding<br/>relapse</td>		
		<td style="width:10%"><small>${report.rtrrCured + report.rtrrComplete + report.rtrrFailed + report.rtrrDied + report.rtrrLost + report.rtrrOther}</small></td>
		<td style="width:10%"><small>${report.rtrrCured}</small></td>
		<td style="width:10%"><small>${report.rtrrComplete}</small></td>
		<td style="width:10%"><small>${report.rtrrFailed}</small></td>
		<td style="width:10%"><small>${report.rtrrDied}</small></td>
		<td style="width:10%"><small>${report.rtrrLost}</small></td>
		<td style="width:10%"><small>${report.rtrrOther}</small></td>
	</tr>
	<tr>
		<td>HIV-positive;<br/>all<br/>types</td>		
		<td style="width:10%"><small>${report.hpatCured + report.hpatComplete + report.hpatFailed + report.hpatDied + report.hpatLost + report.hpatOther}</small></td>
		<td style="width:10%"><small>${report.hpatCured}</small></td>
		<td style="width:10%"><small>${report.hpatComplete}</small></td>
		<td style="width:10%"><small>${report.hpatFailed}</small></td>
		<td style="width:10%"><small>${report.hpatDied}</small></td>
		<td style="width:10%"><small>${report.hpatLost}</small></td>
		<td style="width:10%"><small>${report.hpatOther}</small></td>
	</tr>
</table>


<p class="reportTitle"><br/>Block 2: TB/HIV activities (All TB cases registered during the quarter)</p>
<table id="tb7">
	<tr>
		<td style="width: 33%">HIV positive<br/>TB patients</td>
		<td style="width: 33%">HIV positive TB<br/>patients on antiretroviral<br/>therapy (ART)</td>
		<td>HIV positive TB<br/>patients on cotrimoxazole<br/>preventive therapy (CPT)</td>
	</tr>
	<tr>
		<td><small>${report.hivStatus}</small></td>		
		<td><small>${report.startedArt}</small></td>		
		<td><small>${report.startedCpt}</small></td>
	</tr>
</table>