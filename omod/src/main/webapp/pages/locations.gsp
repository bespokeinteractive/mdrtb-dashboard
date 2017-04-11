<% if (locationId == 0){ %>
	${ui.includeFragment("mdrtbdashboard", "locationList")}
<% } else { %>
	${ui.includeFragment("mdrtbdashboard", "locationDetails", [locationId: locationId])}
<% } %>
