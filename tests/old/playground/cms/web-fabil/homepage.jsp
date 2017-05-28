<%@ page language="java" import="cms.www.*" %>
<%-- CMS welcome/sign-in page, with logos and not much else --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>
      Cornell University Course Management System
  </title>
</head>
<body>

<jsp:include page="cuwebbanner/banner.jsp" />

<style type="text/css">
  @import url("/web/style.css");
  @import url("/web/navbar_style.css");
</style>
<div id="navbar_course">
    <span class="navlink_course" id="navlink_course_overview">
        *** CUCS CMS Version 3.3 ***
    </span>
</div>
<div id="course_wrapper">
	<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
	  <tr>
	  	<td>
		<div id="welcome_cms">
			<center>
			Welcome to Computer Science Department<br>Course Management System
			<br><br>
			<small>Please <a href="/web/auth/?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_OVERLOGIN %>">sign in</a> 
			using your Cornell NetID and password 
			<br>or visit this site as a <a href="/web/guest/?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_OVERLOGIN %>">guest</a>.
			</small>
			</center>
		</div>
	    </td>
	  </tr>
	</table>

<!-- Uncomment this block for production server
<center>
<table cellpadding="0" cellspacing="0" border="0" width="70%">
<tr>
  <td align="left"> 
  <div id="welcome_cms">
<b>Important Notes:</b>
<br><br>

<font size="-2">
<ul>
<li> CUWebLogin credentials: CUCS CMS site now directs you to <i>secure</i> 
authentication which gives credentials that last for 8 hours or
until you exit your browser. 
Please be sure to <a target="blank" href="http://www.cit.cornell.edu/services/identity/cuweblogin/more.html">clear your credentials</a> 
to protect against someone accessing this site as you.
<br><br>
</li>
<li> If you encounter problem <a target="blank" href="http://www.cs.cornell.edu/Projects/CMS/userdocs/login.html">authenticating to CMS using CUWebLogin</a>,
try deleting the old "cookies" (in particular from "<%=request.getServerName()%>" 
and "cornell.edu") and check that your web browser is not blocking cookies.
</li>
</ul>
</font>

  </div>
  </td>
  
</tr>
<tr>
<td align="center"><hr size=1>
<a target="blank" href="http://www.cs.cornell.edu/Projects/CMS/userdocs/">Getting Help</a>
</td>
</tr>
</table>
</center>	
-->

</div>

</body>
</html>


