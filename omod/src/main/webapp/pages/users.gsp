<% if (userId == 0){ %>
	${ui.includeFragment("mdrtbdashboard", "userList")}
<% } else { %>
	${ui.includeFragment("mdrtbdashboard", "userDetails", [userId: userId])}
<% } %>
