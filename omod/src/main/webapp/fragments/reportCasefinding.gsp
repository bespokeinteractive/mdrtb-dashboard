<style>
	#tb1, #tb2{
		font-size: 15px;	
	}
	#tb1 td{
		width: 50%;
		padding: 10px;
	}
	#tb2 td{
		valign: bottom;
		vertical-align: bottom;
	}
	#tb2 td{
		width: 14%;
	}
	#tb2 td:first-child{
		width: 30%;
	}
	#tb2 tr:first-child td:nth-child(1){
		border: 1px transparent;
	}
	#tb3 tr:first-child td:nth-child(1),
	#tb3 tr:nth-child(2) td:nth-child(1){
		border: 1px transparent;
	}
	table tr {
		border: 1px none #eeeeee;
	}
	div.row1 {
		display: inline-block;
		width: 40%;
	}
	div.row2 {
		display: inline-block;
		width: 59%;
	}
	#tb3 tr:first-child td,
	#tb3 tr:nth-child(2) td{
		font-weight: bold;
		font-size: 0.9em;
	}
	#tb3 tr td:nth-child(1){
		font-weight: bold;
		font-size: 0.8em;
	}
	#tb4 {
		 width: 95%;
	}
	#tb4 td,
	#tb5 td{
		white-space: normal;
	}
	#tb4 tr:first-child td,
	#tb5 tr:first-child td {
		height: 120px;
	}
</style>

<p>
	Somalia TB Control Program
	<span style="float:right;">TB 09</span>
</p>

<p class="reportTitle" style="text-align: center;">TB 09 Quarterly Report on TB case Registration in Basic Management Unit</p>

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

<p class="reportTitle"><br/>Block 1: Summary of all cases</p>
<table id="tb2">
	<tr>
		<td>&nbsp;</td>		
		<td>New</td>
		<td>Relapse</td>
		<td>Previously Treated<br/>(Excluding Relapse)</td>
		<td>Previously Treated<br/>(History Unknow)</td>
		<td>Total</td>
	</tr>
	
	<tr>
		<td>Pulmonary, Bacteriologically Confirmed</td>		
		<td><small>${report.pbcNew}</small></td>
		<td><small>${report.pbcRelapse}</small></td>
		<td><small>${report.pbcPrevTreated}</small></td>
		<td><small>${report.pbcHistUnknown}</small></td>
		<td><small>${report.pbcNew + report.pbcRelapse + report.pbcPrevTreated + report.pbcHistUnknown}</small></td>
	</tr>
	
	<tr>
		<td>Pulmonary, Clinically Diagnosed</td>		
		<td><small>${report.pcdNew}</small></td>
		<td><small>${report.pcdRelapse}</small></td>
		<td><small>${report.pcdPrevTreated}</small></td>
		<td><small>${report.pcdHistUnknown}</small></td>
		<td><small>${report.pcdNew + report.pcdRelapse + report.pcdPrevTreated + report.pcdHistUnknown}</small></td>
	</tr>
	
	<tr>
		<td>Extrapulmonary, Confirmed/Diagnosed</td>		
		<td><small>${report.ecdNew}</small></td>
		<td><small>${report.ecdRelapse}</small></td>
		<td><small>${report.ecdPrevTreated}</small></td>
		<td><small>${report.ecdHistUnknown}</small></td>
		<td><small>${report.ecdNew + report.ecdRelapse + report.ecdPrevTreated + report.ecdHistUnknown}</small></td>
	</tr>
	
	<tr>
		<td>Totals</td>		
		<td><small>${report.pbcNew + report.pcdNew + report.ecdNew}</small></td>
		<td><small>${report.pbcRelapse + report.pcdRelapse + report.ecdRelapse}</small></td>
		<td><small>${report.pbcPrevTreated + report.pcdPrevTreated + report.ecdPrevTreated}</small></td>
		<td><small>${report.pbcHistUnknown + report.pcdHistUnknown+ report.ecdHistUnknown}</small></td>
		<td><small>${report.pbcNew + report.pbcRelapse + report.pbcPrevTreated + report.pbcHistUnknown + report.pcdNew + report.pcdRelapse + report.pcdPrevTreated + report.pcdHistUnknown + report.ecdNew + report.ecdRelapse + report.ecdPrevTreated + report.ecdHistUnknown}</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 2. Age and Sex distribution (Bacteriologically Confirmed)</p>
<table id="tb3">
	<tr>
		<td>&nbsp;</td>
		<td colspan=2>0-4</td>
		<td colspan=2>5-14</td>
		<td colspan=2>15-24</td>
		<td colspan=2>25-34</td>
		<td colspan=2>35-44</td>
		<td colspan=2>45-54</td>
		<td colspan=2>55-64</td>
		<td colspan=2>65+</td>
		<td colspan=2>TOTAL</td>
		<td rowspan=2 style="vertical-align: bottom">TOTAL</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>		
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
	</tr>
	
	<tr>
		<td>NEW Bacteriologically<br/>Confirmed</td>		
		<td><small>${report.bcnm0004}</small></td>
		<td><small>${report.bcnf0004}</small></td>		
		<td><small>${report.bcnm0514}</small></td>
		<td><small>${report.bcnf0514}</small></td>		
		<td><small>${report.bcnm1524}</small></td>
		<td><small>${report.bcnf1524}</small></td>
		<td><small>${report.bcnm2534}</small></td>
		<td><small>${report.bcnf2534}</small></td>
		<td><small>${report.bcnm3544}</small></td>
		<td><small>${report.bcnf3544}</small></td>
		<td><small>${report.bcnm4554}</small></td>
		<td><small>${report.bcnf4554}</small></td>
		<td><small>${report.bcnm5564}</small></td>
		<td><small>${report.bcnf5564}</small></td>
		<td><small>${report.bcnm65XX}</small></td>
		<td><small>${report.bcnf65XX}</small></td>
		<td><small><b>${report.bcnmTotal}</b></small></td>
		<td><small><b>${report.bcnfTotal}</b></small></td>		
		<td><small>${report.bcnmTotal + report.bcnfTotal}</small></td>
	</tr>
	
	<tr>
		<td>Number tested for<br/>HIV</td>		
		<td><small>${report.bctm0004}</small></td>
		<td><small>${report.bctf0004}</small></td>		
		<td><small>${report.bctm0514}</small></td>
		<td><small>${report.bctf0514}</small></td>		
		<td><small>${report.bctm1524}</small></td>
		<td><small>${report.bctf1524}</small></td>
		<td><small>${report.bctm2534}</small></td>
		<td><small>${report.bctf2534}</small></td>
		<td><small>${report.bctm3544}</small></td>
		<td><small>${report.bctf3544}</small></td>
		<td><small>${report.bctm4554}</small></td>
		<td><small>${report.bctf4554}</small></td>
		<td><small>${report.bctm5564}</small></td>
		<td><small>${report.bctf5564}</small></td>
		<td><small>${report.bctm65XX}</small></td>
		<td><small>${report.bctf65XX}</small></td>
		<td><small><b>${report.bctmTotal}</b></small></td>
		<td><small><b>${report.bctfTotal}</b></small></td>		
		<td><small>${report.bctmTotal + report.bctfTotal}</small></td>
	</tr>
	
	<tr>
		<td>Number of HIV<br/>Positive</td>		
		<td><small>${report.bcpm0004}</small></td>
		<td><small>${report.bcpf0004}</small></td>		
		<td><small>${report.bcpm0514}</small></td>
		<td><small>${report.bcpf0514}</small></td>		
		<td><small>${report.bcpm1524}</small></td>
		<td><small>${report.bcpf1524}</small></td>
		<td><small>${report.bcpm2534}</small></td>
		<td><small>${report.bcpf2534}</small></td>
		<td><small>${report.bcpm3544}</small></td>
		<td><small>${report.bcpf3544}</small></td>
		<td><small>${report.bcpm4554}</small></td>
		<td><small>${report.bcpf4554}</small></td>
		<td><small>${report.bcpm5564}</small></td>
		<td><small>${report.bcpf5564}</small></td>
		<td><small>${report.bcpm65XX}</small></td>
		<td><small>${report.bcpf65XX}</small></td>
		<td><small><b>${report.bcpmTotal}</b></small></td>
		<td><small><b>${report.bcpfTotal}</b></small></td>		
		<td><small>${report.bcpmTotal + report.bcpfTotal}</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 3. Age and Sex distribution (Clinically Diagnosed)</p>
<table id="tb3">
	<tr>
		<td>&nbsp;</td>
		<td colspan=2>0-4</td>
		<td colspan=2>5-14</td>
		<td colspan=2>15-24</td>
		<td colspan=2>25-34</td>
		<td colspan=2>35-44</td>
		<td colspan=2>45-54</td>
		<td colspan=2>55-64</td>
		<td colspan=2>65+</td>
		<td colspan=2>TOTAL</td>
		<td rowspan=2 style="vertical-align: bottom">TOTAL</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>		
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
	</tr>
	
	<tr>
		<td>NEW Clinically<br/>Diagnosed</td>		
		<td><small>${report.cdnm0004}</small></td>
		<td><small>${report.cdnf0004}</small></td>		
		<td><small>${report.cdnm0514}</small></td>
		<td><small>${report.cdnf0514}</small></td>		
		<td><small>${report.cdnm1524}</small></td>
		<td><small>${report.cdnf1524}</small></td>
		<td><small>${report.cdnm2534}</small></td>
		<td><small>${report.cdnf2534}</small></td>
		<td><small>${report.cdnm3544}</small></td>
		<td><small>${report.cdnf3544}</small></td>
		<td><small>${report.cdnm4554}</small></td>
		<td><small>${report.cdnf4554}</small></td>
		<td><small>${report.cdnm5564}</small></td>
		<td><small>${report.cdnf5564}</small></td>
		<td><small>${report.cdnm65XX}</small></td>
		<td><small>${report.cdnf65XX}</small></td>
		<td><small><b>${report.cdnmTotal}</b></small></td>
		<td><small><b>${report.cdnfTotal}</b></small></td>		
		<td><small>${report.cdnmTotal + report.cdnfTotal}</small></td>
	</tr>
	
	<tr>
		<td>Number tested for<br/>HIV</td>		
		<td><small>${report.cdtm0004}</small></td>
		<td><small>${report.cdtf0004}</small></td>		
		<td><small>${report.cdtm0514}</small></td>
		<td><small>${report.cdtf0514}</small></td>		
		<td><small>${report.cdtm1524}</small></td>
		<td><small>${report.cdtf1524}</small></td>
		<td><small>${report.cdtm2534}</small></td>
		<td><small>${report.cdtf2534}</small></td>
		<td><small>${report.cdtm3544}</small></td>
		<td><small>${report.cdtf3544}</small></td>
		<td><small>${report.cdtm4554}</small></td>
		<td><small>${report.cdtf4554}</small></td>
		<td><small>${report.cdtm5564}</small></td>
		<td><small>${report.cdtf5564}</small></td>
		<td><small>${report.cdtm65XX}</small></td>
		<td><small>${report.cdtf65XX}</small></td>
		<td><small><b>${report.cdtmTotal}</b></small></td>
		<td><small><b>${report.cdtfTotal}</b></small></td>		
		<td><small>${report.cdtmTotal + report.cdtfTotal}</small></td>
	</tr>
	
	<tr>
		<td>Number of HIV<br/>Positive</td>		
		<td><small>${report.cdpm0004}</small></td>
		<td><small>${report.cdpf0004}</small></td>		
		<td><small>${report.cdpm0514}</small></td>
		<td><small>${report.cdpf0514}</small></td>		
		<td><small>${report.cdpm1524}</small></td>
		<td><small>${report.cdpf1524}</small></td>
		<td><small>${report.cdpm2534}</small></td>
		<td><small>${report.cdpf2534}</small></td>
		<td><small>${report.cdpm3544}</small></td>
		<td><small>${report.cdpf3544}</small></td>
		<td><small>${report.cdpm4554}</small></td>
		<td><small>${report.cdpf4554}</small></td>
		<td><small>${report.cdpm5564}</small></td>
		<td><small>${report.cdpf5564}</small></td>
		<td><small>${report.cdpm65XX}</small></td>
		<td><small>${report.cdpf65XX}</small></td>
		<td><small><b>${report.cdpmTotal}</b></small></td>
		<td><small><b>${report.cdpfTotal}</b></small></td>		
		<td><small>${report.cdpmTotal + report.cdpfTotal}</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 4. Age and Sex distribution (Previously Treated)</p>
<table id="tb3">
	<tr>
		<td>&nbsp;</td>
		<td colspan=2>0-4</td>
		<td colspan=2>5-14</td>
		<td colspan=2>15-24</td>
		<td colspan=2>25-34</td>
		<td colspan=2>35-44</td>
		<td colspan=2>45-54</td>
		<td colspan=2>55-64</td>
		<td colspan=2>65+</td>
		<td colspan=2>TOTAL</td>
		<td rowspan=2 style="vertical-align: bottom">TOTAL</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>		
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
	</tr>
	
	<tr>
		<td>Previously Treated<br/>All Confirmations</td>		
		<td><small>${report.ptam0004}</small></td>
		<td><small>${report.ptaf0004}</small></td>		
		<td><small>${report.ptam0514}</small></td>
		<td><small>${report.ptaf0514}</small></td>		
		<td><small>${report.ptam1524}</small></td>
		<td><small>${report.ptaf1524}</small></td>
		<td><small>${report.ptam2534}</small></td>
		<td><small>${report.ptaf2534}</small></td>
		<td><small>${report.ptam3544}</small></td>
		<td><small>${report.ptaf3544}</small></td>
		<td><small>${report.ptam4554}</small></td>
		<td><small>${report.ptaf4554}</small></td>
		<td><small>${report.ptam5564}</small></td>
		<td><small>${report.ptaf5564}</small></td>
		<td><small>${report.ptam65XX}</small></td>
		<td><small>${report.ptaf65XX}</small></td>
		<td><small><b>${report.ptamTotal}</b></small></td>
		<td><small><b>${report.ptafTotal}</b></small></td>		
		<td><small>${report.ptamTotal + report.ptafTotal}</small></td>
	</tr>
	
	<tr>
		<td>Number tested for<br/>HIV</td>		
		<td><small>${report.pttm0004}</small></td>
		<td><small>${report.pttf0004}</small></td>		
		<td><small>${report.pttm0514}</small></td>
		<td><small>${report.pttf0514}</small></td>		
		<td><small>${report.pttm1524}</small></td>
		<td><small>${report.pttf1524}</small></td>
		<td><small>${report.pttm2534}</small></td>
		<td><small>${report.pttf2534}</small></td>
		<td><small>${report.pttm3544}</small></td>
		<td><small>${report.pttf3544}</small></td>
		<td><small>${report.pttm4554}</small></td>
		<td><small>${report.pttf4554}</small></td>
		<td><small>${report.pttm5564}</small></td>
		<td><small>${report.pttf5564}</small></td>
		<td><small>${report.pttm65XX}</small></td>
		<td><small>${report.pttf65XX}</small></td>
		<td><small><b>${report.pttmTotal}</b></small></td>
		<td><small><b>${report.pttfTotal}</b></small></td>		
		<td><small>${report.pttmTotal + report.pttfTotal}</small></td>
	</tr>
	
	<tr>
		<td>Number of HIV<br/>Positive</td>		
		<td><small>${report.ptpm0004}</small></td>
		<td><small>${report.ptpf0004}</small></td>		
		<td><small>${report.ptpm0514}</small></td>
		<td><small>${report.ptpf0514}</small></td>		
		<td><small>${report.ptpm1524}</small></td>
		<td><small>${report.ptpf1524}</small></td>
		<td><small>${report.ptpm2534}</small></td>
		<td><small>${report.ptpf2534}</small></td>
		<td><small>${report.ptpm3544}</small></td>
		<td><small>${report.ptpf3544}</small></td>
		<td><small>${report.ptpm4554}</small></td>
		<td><small>${report.ptpf4554}</small></td>
		<td><small>${report.ptpm5564}</small></td>
		<td><small>${report.ptpf5564}</small></td>
		<td><small>${report.ptpm65XX}</small></td>
		<td><small>${report.ptpf65XX}</small></td>
		<td><small><b>${report.ptpmTotal}</b></small></td>
		<td><small><b>${report.ptpfTotal}</b></small></td>		
		<td><small>${report.ptpmTotal + report.ptpfTotal}</small></td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 5. Age and Sex distribution (Extra Pulmonary)</p>
<table id="tb3">
	<tr>
		<td>&nbsp;</td>
		<td colspan=2>0-4</td>
		<td colspan=2>5-14</td>
		<td colspan=2>15-24</td>
		<td colspan=2>25-34</td>
		<td colspan=2>35-44</td>
		<td colspan=2>45-54</td>
		<td colspan=2>55-64</td>
		<td colspan=2>65+</td>
		<td colspan=2>TOTAL</td>
		<td rowspan=2 style="vertical-align: bottom">TOTAL</td>
	</tr>
	
	<tr>
		<td>&nbsp;</td>		
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
		<td>M</td>
		<td>F</td>
	</tr>
	
	<tr>
		<td>Extra-Pulmonary<br/>All Cases</td>		
		<td><small>${report.epam0004}</small></td>
		<td><small>${report.epaf0004}</small></td>		
		<td><small>${report.epam0514}</small></td>
		<td><small>${report.epaf0514}</small></td>		
		<td><small>${report.epam1524}</small></td>
		<td><small>${report.epaf1524}</small></td>
		<td><small>${report.epam2534}</small></td>
		<td><small>${report.epaf2534}</small></td>
		<td><small>${report.epam3544}</small></td>
		<td><small>${report.epaf3544}</small></td>
		<td><small>${report.epam4554}</small></td>
		<td><small>${report.epaf4554}</small></td>
		<td><small>${report.epam5564}</small></td>
		<td><small>${report.epaf5564}</small></td>
		<td><small>${report.epam65XX}</small></td>
		<td><small>${report.epaf65XX}</small></td>
		<td><small><b>${report.epamTotal}</b></small></td>
		<td><small><b>${report.epafTotal}</b></small></td>		
		<td><small>${report.epamTotal + report.epafTotal}</small></td>
	</tr>
	
	<tr>
		<td>Number tested for<br/>HIV</td>		
		<td><small>${report.eptm0004}</small></td>
		<td><small>${report.eptf0004}</small></td>		
		<td><small>${report.eptm0514}</small></td>
		<td><small>${report.eptf0514}</small></td>		
		<td><small>${report.eptm1524}</small></td>
		<td><small>${report.eptf1524}</small></td>
		<td><small>${report.eptm2534}</small></td>
		<td><small>${report.eptf2534}</small></td>
		<td><small>${report.eptm3544}</small></td>
		<td><small>${report.eptf3544}</small></td>
		<td><small>${report.eptm4554}</small></td>
		<td><small>${report.eptf4554}</small></td>
		<td><small>${report.eptm5564}</small></td>
		<td><small>${report.eptf5564}</small></td>
		<td><small>${report.eptm65XX}</small></td>
		<td><small>${report.eptf65XX}</small></td>
		<td><small><b>${report.eptmTotal}</b></small></td>
		<td><small><b>${report.eptfTotal}</b></small></td>		
		<td><small>${report.eptmTotal + report.eptfTotal}</small></td>
	</tr>
	
	<tr>
		<td>Number of HIV<br/>Positive</td>		
		<td><small>${report.eppm0004}</small></td>
		<td><small>${report.eppf0004}</small></td>		
		<td><small>${report.eppm0514}</small></td>
		<td><small>${report.eppf0514}</small></td>		
		<td><small>${report.eppm1524}</small></td>
		<td><small>${report.eppf1524}</small></td>
		<td><small>${report.eppm2534}</small></td>
		<td><small>${report.eppf2534}</small></td>
		<td><small>${report.eppm3544}</small></td>
		<td><small>${report.eppf3544}</small></td>
		<td><small>${report.eppm4554}</small></td>
		<td><small>${report.eppf4554}</small></td>
		<td><small>${report.eppm5564}</small></td>
		<td><small>${report.eppf5564}</small></td>
		<td><small>${report.eppm65XX}</small></td>
		<td><small>${report.eppf65XX}</small></td>
		<td><small><b>${report.eppmTotal}</b></small></td>
		<td><small><b>${report.eppfTotal}</b></small></td>		
		<td><small>${report.eppmTotal + report.eppfTotal}</small></td>
	</tr>
</table>


<div class="row1">
	<p class="reportTitle"><br/>Block 6: Laboratory diagnostic activity</p>
	<table id="tb4">
		<tr>
			<td>Presumptive TB cases undergoing bacteriological examination</td>		
			<td>Presumptive TB cases with positive bacteriological examination result</td>
		</tr>
		<tr>
			<td><small>0</small></td>		
			<td><small>0</small></td>
		</tr>
	</table>
</div>
<div class="row2">
	<p class="reportTitle"><br/>Block 7: TB/HIV activities (All TB cases registered during the quarter)</p>
	<table id="tb5">
		<tr>
			<td>Patients tested for HIV at the time of diagnosis or with known HIV status on diagnosis</td>		
			<td>HIV positive TB patients</td>
			<td>HIV positive TB patients on antiretroviral therapy (ART)</td>
			<td>HIV positive TB patients on cotrimoxazole preventive therapy (CPT)</td>
		</tr>
		<tr>
			<td><small>${report.initialStatus}</small></td>		
			<td><small>${report.currentStatus}</small></td>		
			<td><small>${report.startedArt}</small></td>		
			<td><small>${report.startedCpt}</small></td>
		</tr>
	</table>
</div>