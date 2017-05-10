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
	#tb2 tr:first-child td:nth-child(1),
	#tb3 tr:first-child td:nth-child(1){
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
	#tb3 tr:first-child td {
		font-weight: bold;
		font-size: 0.9em;
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

<p class="reportTitle"><br/>Block 1: All TB cases registered during the quarter</p>
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
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
	</tr>
	<tr>
		<td>Pulmonary, Clinically Diagnosed</td>		
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
	</tr>
	<tr>
		<td>Extrapulmonary, Confirmed/Diagnosed</td>		
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
	</tr>
</table>

<p class="reportTitle"><br/>Block 2. All new and relapse cases (bacteriologically confirmed or clinically diagnosed) registered during the quarter</p>
<table id="tb3">
	<tr>
		<td>&nbsp;</td>		
		<td>0-4</td>
		<td>5-14</td>
		<td>15-24</td>
		<td>25-34</td>
		<td>35-44</td>
		<td>45-54</td>
		<td>55-64</td>
		<td>>65</td>
		<td>Total</td>
	</tr>
	
	<tr>
		<td>Male</td>		
		<td>0</td>
		<td>5</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
	</tr>
	
	<tr>
		<td>Female</td>		
		<td>0</td>
		<td>5</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
		<td>0</td>
	</tr>
</table>

<div class="row1">
	<p class="reportTitle"><br/>Block 3: Laboratory diagnostic activity</p>
	<table id="tb4">
		<tr>
			<td>Presumptive TB cases undergoing bacteriological examination</td>		
			<td>Presumptive TB cases with positive bacteriological examination result</td>
		</tr>
		<tr>
			<td>0</td>		
			<td>0</td>
		</tr>
	</table>
</div>
<div class="row2">
	<p class="reportTitle"><br/>Block 4: TB/HIV activities (All TB cases registered during the quarter)</p>
	<table id="tb5">
		<tr>
			<td>Patients tested for HIV at the time of diagnosis or with known HIV status on diagnosis</td>		
			<td>HIV positive TB patients</td>
			<td>HIV positive TB patients on antiretroviral therapy (ART)</td>
			<td>HIV positive TB patients on cotrimoxazole preventive therapy (CPT)</td>
		</tr>
		<tr>
			<td>0</td>		
			<td>0</td>		
			<td>0</td>		
			<td>0</td>
		</tr>
	</table>
</div>