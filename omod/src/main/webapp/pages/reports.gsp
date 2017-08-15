<%
    ui.decorateWith("appui", "standardEmrPage", [title: title])
%>

<style>
	#breadcrumbs a, #breadcrumbs a:link, #breadcrumbs a:visited {
		text-decoration: none;
	}
	body {
		margin-top: 20px;
	}
	form input{
		margin: 0px;
		display: inline-block;
		min-width: 50px;
		padding: 2px 10px;
	}
	.info-header span{
		cursor: pointer;
		display: inline-block;
		float: right;
		margin-top: -2px;
		padding-right: 5px;
	}
	.dashboard .info-section {
		margin: 2px 5px 5px;
	}
	.toast-item{
		background-color: #222;
	}
	form input:focus, form select:focus, form textarea:focus, form ul.select:focus, .form input:focus, .form select:focus, .form textarea:focus, .form ul.select:focus{
		outline: 1px none #007fff;
		box-shadow: 0 0 1px 0px #888!important;
	}
	.name {
		color: #f26522;
	}
	@media all and (max-width: 768px) {
		.onerow {
			margin: 0 0 100px;
		}
	}
	form .advanced {
		background: #363463 none repeat scroll 0 0;
		border-color: #dddddd;
		border-style: solid;
		border-width: 1px;
		color: #fff;
		cursor: pointer;
		float: right;
		padding: 5px 0;
		text-align: center;
		width: 18%;
	}
	form .advanced i{
		font-size: 24px;
	}
	.add-on {
		float: right;
		left: auto;
		margin-left: -29px;
		margin-top: 5px;
		position: absolute;
		color: #f26522;
	}
	.ui-widget-content a {
		color: #007fff;
	}
	td a{
		cursor: pointer;
		text-decoration: none;
	}
	td a:hover{
		text-decoration: none;
	}
	.recent-seen{
		background: #fff799 none repeat scroll 0 0!important;
		color: #000 !important;
	}
	.recent-lozenge {
		border: 1px solid #f00;
		border-radius: 4px;
		color: #f00;
		display: inline-block;
		font-size: 0.7em;
		padding: 1px 2px;
		vertical-align: text-bottom;
	}
	table th, table td {
		white-space: nowrap;
	}	
	.dialog-content ul li input[type="text"],
	.dialog-content ul li input[type="password"],
	.dialog-content ul li select, 
	.dialog-content ul li textarea {
		border: 1px solid #ddd;
		display: inline-block;
		height: 40px;
		margin: 1px 0;
		min-width: 20%;
		padding: 5px 0 5px 10px;
		width: 68%;
	}
	#modal-overlay {
		background: #000 none repeat scroll 0 0;
		opacity: 0.3 !important;
	}
	form label,
	.form label {
		display: inline-block;
		width: 110px;
	}
	.dialog select option {
		font-size: 1em;
	}
	.dialog .dialog-content li {
		margin-bottom: 0;
	}
	.dialog ul {
		margin-bottom: 10px;
	}
	label.user-locations input{
		margin-top: 4px;
	}
	.select-report{
		cursor: pointer;
	}
	.report-container {
		margin: 10px 0px;
	}
	p.reportTitle{
		font-family: "OpenSansBold";
		margin: 10px 0;
		text-align: center;
	}
	span.underline{
		border-bottom: 1px solid #f00;
		display: inline-block;
		min-width: 100px;
	}
</style>

<div class="clear"></div>
<div class="container">
    <div class="example">
        <ul id="breadcrumbs">
            <li>
                <a href="${ui.pageLink('referenceapplication','home')}">
                <i class="icon-home small"></i></a>
            </li>
            <li>
                <i class="icon-chevron-right link"></i>
                <a class="select-report">Reports</a>
            </li>
            <li>
				<i class="icon-chevron-right link"></i>
                ${title}
            </li>
        </ul>
    </div>
	
    <div class="patient-header new-patient-header">
        <div class="demographics">
            <h1 class="name" style="border-bottom: 1px solid #ddd;">
                <span><i class="icon-copy small"></i> VIEW REPORTS &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </h1>
            <br/>
        </div>		
		
    </div>
</div>
<div class="clear"></div>
<div class="report-container">
	<% if (report == "cohort") { %>
		${ui.includeFragment("mdrtbdashboard", "reportCohort", [qtr: mnth, year: year, facility: facility.id])}
	<% } else if (report == "casefinding") { %>
		${ui.includeFragment("mdrtbdashboard", "reportCasefinding", [qtr: mnth, year: year, facility: facility.id])}
	<% } else if (report == "distribution") { %>
		${ui.includeFragment("mdrtbdashboard", "reportDistribution", [patient: 0])}
	<% } else if (report == "defaulting") { %>
		${ui.includeFragment("mdrtbdashboard", "reportDefaulters", [patient: 0])}
	<% } %>            
</div>
