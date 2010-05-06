<%@ page contentType="text/html" language="Java" %>

<!-- sign-in and sign-out page -->
<% 
  String login_netid = request.getHeader("remote_user");
  String sign = null;
  String bodytext = null;
  if ( login_netid == null ) {
     sign = "- Sign in";
     bodytext = "Please <a href=/web/auth/>click here</a> to login.";
  } else {
     sign = "- Sign out";
     bodytext = "Please exit your browser and sign out of SideCar to clear your credentials." + "<p>If you authenticate using CUWebLogin, please be sure to read the guidelines<br> on how to <a href='http://www.cit.cornell.edu/services/identity/cuweblogin/more.html'>clear your CUWebLogin credentials</a>.</p>";
  }
%>

<html>
<head>
  <link href="style.css" rel="stylesheet" type="text/css">
  <title>
      Cornell University Course Management System <%= sign %>
  </title>
</head>

<body>

<div id="navbar_course">
<span class="navlink_course" id="navlink_course_overview">
Current Login: <%= login_netid %>
</span>
</div>

<div id="welcome_cms">
<center>
<%= bodytext %>
</center>
</div>

</body>
</html>