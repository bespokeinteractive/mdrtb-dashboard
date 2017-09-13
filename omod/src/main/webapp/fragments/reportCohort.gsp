<style>
	table th, table td {
		text-align: left;
		white-space: nowrap;
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

<p class="reportTitle"><br/>Block 1: NEW BACTERIOLOGICALLY CONFIRMED CASES (PULMONARY)</p>
<table id="tb1">	
	<tr>
		<th><small>Results at 2 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Smear Negative</small></th>
		<th><small>Smear Not Done</small></th>
		<th><small>Bacteriologically<br/>Confirmed Cases</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	<tr>
		<td><small>&nbsp;</small></td>
		<td><small>${report.pbcnxTotal}</small></td>
		<td><small>${report.pbcnxNegativeTwo}</small></td>
		<td><small>${report.pbcnxNotDoneTwo}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxDied}</small></td>
		<td><small>${report.pbcnxLTFU}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxTotal - report.pbcnxEvaluated}</small></td>
		<td><small>${report.pbcnxEvaluated}</small></td>
	</tr>	
</table>

<table id="tb2" style="margin-top: 2px;">	
	<tr>
		<th><small>Results at 5 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Smear Negative</small></th>
		<th><small>Smear Not Done</small></th>
		<th><small>Bacteriologically<br/>Confirmed Cases</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	<tr>
		<td><small>&nbsp;</small></td>
		<td><small>${report.pbcnxTotal}</small></td>
		<td><small>${report.pbcnxNegativeFive}</small></td>
		<td><small>${report.pbcnxNotDoneFive}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxDied}</small></td>
		<td><small>${report.pbcnxLTFU}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxTotal - report.pbcnxEvaluated}</small></td>
		<td><small>${report.pbcnxEvaluated}</small></td>
	</tr>	
</table>


<table id="tb3" style="margin-top: 2px;">	
	<tr>
		<th><small>Results at 8 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Smear Negative</small></th>
		<th><small>Smear Not Done</small></th>
		<th><small>Bacteriologically<br/>Confirmed Cases</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	
	<tr>
		<td><small>Results at 8 months</small></td>
		<td><small>${report.pbcnxTotal}</small></td>
		<td><small>${report.pbcnxNegativeEight}</small></td>
		<td><small>${report.pbcnxNotDoneEight}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxDied}</small></td>
		<td><small>${report.pbcnxLTFU}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxTotal - report.pbcnxEvaluated}</small></td>
		<td><small>${report.pbcnxEvaluated}</small></td>
	</tr>
	
	<tr>
		<td><small>HIV+</small></td>
		<td><small>${report.pbcnxHivPosTotal}</small></td>
		<td><small>${report.pbcnxHivPosNegative}</small></td>
		<td><small>${report.pbcnxHivPosNotDone}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivPosDied}</small></td>
		<td><small>${report.pbcnxHivPosLTFU}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivPosTotal - report.pbcnxHivPosEvaluated}</small></td>
		<td><small>${report.pbcnxHivPosEvaluated}</small></td>
	</tr>
	
	<tr>
		<td><small>HIV-</small></td>
		<td><small>${report.pbcnxHivNegTotal}</small></td>
		<td><small>${report.pbcnxHivNegNegative}</small></td>
		<td><small>${report.pbcnxHivNegNotDone}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivNegDied}</small></td>
		<td><small>${report.pbcnxHivNegLTFU}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivNegTotal - report.pbcnxHivNegEvaluated}</small></td>
		<td><small>${report.pbcnxHivNegEvaluated}</small></td>
	</tr>
	
	<tr>
		<td><small>ND</small></td>
		<td><small>${report.pbcnxHivNdTotal}</small></td>
		<td><small>${report.pbcnxHivNdNegative}</small></td>
		<td><small>${report.pbcnxHivNdNotDone}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivNdDied}</small></td>
		<td><small>${report.pbcnxHivNdLTFU}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivNdTotal - report.pbcnxHivNdEvaluated}</small></td>
		<td><small>${report.pbcnxHivNdEvaluated}</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on CPT</small></td>
		<td><small>${report.pbcnxHivCptTotal}</small></td>
		<td><small>${report.pbcnxHivCptNegative}</small></td>
		<td><small>${report.pbcnxHivCptNotDone}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivCptDied}</small></td>
		<td><small>${report.pbcnxHivCptLTFU}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivCptTotal - report.pbcnxHivCptEvaluated}</small></td>
		<td><small>${report.pbcnxHivCptEvaluated}</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on ART</small></td>
		<td><small>${report.pbcnxHivArtTotal}</small></td>
		<td><small>${report.pbcnxHivArtNegative}</small></td>
		<td><small>${report.pbcnxHivArtNotDone}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivArtDied}</small></td>
		<td><small>${report.pbcnxHivArtLTFU}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxHivArtTotal - report.pbcnxHivArtEvaluated}</small></td>
		<td><small>${report.pbcnxHivArtEvaluated}</small></td>
	</tr>
	
	<tr>
		<td><small>Total</small></td>
		<td><small>${report.pbcnxTotal}</small></td>
		<td><small>${report.pbcnxNegativeEight}</small></td>
		<td><small>${report.pbcnxNotDoneEight}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxDied}</small></td>
		<td><small>${report.pbcnxLTFU}</small></td>
		<td><small>0</small></td>
		<td><small>${report.pbcnxTotal - report.pbcnxEvaluated}</small></td>
		<td><small>${report.pbcnxEvaluated}</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 2: NEW SMEAR NEGATIVE PTB CASES</p>
<table id="tb4">	
	<tr>
		<th><small>Results at 2 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>TC</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	<tr>
		<td><small>&nbsp;</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>	
</table>

<table id="tb41" style="margin-top: 2px;">	
	<tr>
		<th><small>Results at 8 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>TC</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	
	<tr>
		<td><small>Results at 8 months</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV+</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV-</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>ND</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on CPT</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on ART</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Total</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 3: NEW EXTRA PULMONARY CASES (BACTERIOLOGICALLY CONFIRMED / CLINICALLY DIAGNOSED)</p>
<table id="tb5">	
	<tr>
		<th><small>Results at 2 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Completed Treatment</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	<tr>
		<td><small>&nbsp;</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>	
</table>

<table id="tb51" style="margin-top: 2px;">	
	<tr>
		<th><small>Results at 8 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Completed Treatment</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	
	<tr>
		<td><small>Results at 8 months</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV+</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV-</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>ND</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on CPT</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on ART</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Total</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 4: NEW PTB SMEAR NOT DONE TB CASES</p>
<table id="tb5">	
	<tr>
		<th><small>Results at 2 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Completed Treatment</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	<tr>
		<td><small>&nbsp;</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>	
</table>

<table id="tb51" style="margin-top: 2px;">	
	<tr>
		<th><small>Results at 8 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Completed Treatment</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	
	<tr>
		<td><small>Results at 8 months</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV+</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV-</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>ND</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on CPT</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on ART</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Total</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 5: PULMONARY, BACTERIOLOGICAL CONFIRMED PREVIOUSLY TREATED</p>
<table id="tb5">	
	<tr>
		<th><small>Results at 2 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Smear<br/>Negative</small></th>
		<th><small>Smear Not<br/>Done</small></th>
		<th><small>Bacteriologically<br/>Confirmed Cases</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	<tr>
		<td><small>&nbsp;</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>	
</table>

<table id="tb51" style="margin-top: 2px;">	
	<tr>
		<th><small>Results at 8 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Smear<br/>Negative</small></th>
		<th><small>Smear Not<br/>Done</small></th>
		<th><small>Bacteriologically<br/>Confirmed Cases</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	
	<tr>
		<td><small>Results at 8 months</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV+</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV-</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>ND</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on CPT</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on ART</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Total</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 6: BACTERIOLOGICALLY CONFIRMED TREATMENT AFTER FAILURE</p>
<table id="tb5">	
	<tr>
		<th><small>Results at 2 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Smear<br/>Negative</small></th>
		<th><small>Smear Not<br/>Done</small></th>
		<th><small>Bacteriologically<br/>Confirmed Cases</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	<tr>
		<td><small>&nbsp;</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>	
</table>

<table id="tb51" style="margin-top: 2px;">	
	<tr>
		<th><small>Results at 8 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Smear<br/>Negative</small></th>
		<th><small>Smear Not<br/>Done</small></th>
		<th><small>Bacteriologically<br/>Confirmed Cases</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	
	<tr>
		<td><small>Results at 8 months</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV+</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV-</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>ND</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on CPT</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on ART</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Total</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 7: PULMONARY, BACTERIOLOGICAL CONFIRMED TREATMENT AFTER LOSS TO FOLLOW UP</p>
<table id="tb5">	
	<tr>
		<th><small>Results at 2 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Smear<br/>Negative</small></th>
		<th><small>Smear Not<br/>Done</small></th>
		<th><small>Bacteriologically<br/>Confirmed Cases</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	<tr>
		<td><small>&nbsp;</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>	
</table>

<table id="tb51" style="margin-top: 2px;">	
	<tr>
		<th><small>Results at 8 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Smear<br/>Negative</small></th>
		<th><small>Smear Not<br/>Done</small></th>
		<th><small>Bacteriologically<br/>Confirmed Cases</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	
	<tr>
		<td><small>Results at 8 months</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV+</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV-</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>ND</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on CPT</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on ART</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Total</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 8: OTHER RETREATMENTS</p>
<table id="tb5">	
	<tr>
		<th><small>Results at 2 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Finalied Initial<br/>Treatment</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	<tr>
		<td><small>&nbsp;</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>	
</table>

<table id="tb51" style="margin-top: 2px;">	
	<tr>
		<th><small>Results at 8 months</small></th>
		<th><small>Total Enrolled</small></th>
		<th><small>Finalied Initial<br/>Treatment</small></th>
		<th><small>Died</small></th>
		<th><small>LTFU</small></th>
		<th><small>MT4</small></th>
		<th><small>Not Evaluated</small></th>
		<th><small>Total Evaluated</small></th>
	</tr>
	
	<tr>
		<td><small>Results at 8 months</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV+</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>HIV-</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>ND</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on CPT</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Nr. on ART</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
	
	<tr>
		<td><small>Total</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
		<td><small>0</small></td>
	</tr>
</table>