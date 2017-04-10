<%
    ui.decorateWith("appui", "standardEmrPage", [title: "User Details"])
%>

<style>
	#breadcrumbs a, #breadcrumbs a:link, #breadcrumbs a:visited {
		text-decoration: none;
	}
	body {
		margin-top: 20px;
	}
	.col1, .col2, .col3, .col4, .col5, .col6, .col7, .col8, .col9, .col10, .col11, .col12 {
		color: #555;
		text-align: left;
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
	.col4 label {
		width: 110px;
		display: inline-block;
	}

	.col4 input[type=text] {
		display: inline-block;
		padding: 2px 10px;
	}

	.col4 select {
		padding: 2px 10px;
	}

	form select {
		min-width: 50px;
		display: inline-block;
	}
	.addon{
		display: inline-block;
		float: right;
		margin: 5px 0 0 145px;
		position: absolute;
	}
	#lastDayOfVisit label{
		display:none;
	}
	#lastDayOfVisit input{
		width:172px !important;
		height: 34px;
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
	td:nth-child(4){
		text-align: center;
	}
	td:nth-child(5){
		text-transform: capitalize;
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
                <a href="${ui.pageLink('mdrtbdashboard','users')}">Manage Users</a>
            </li>
			<li>
                <i class="icon-chevron-right link"></i>
                <span>${user.getPersonName().getFullName()}</span>
            </li>
        </ul>
    </div>
	
    <div class="patient-header new-patient-header">
        <div class="demographics">
            <h1 class="name" style="border-bottom: 1px solid #ddd;">
                <span>&nbsp; USER DETAILS &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            </h1>
            <br/>
        </div>
		
        
    </div>
</div>
