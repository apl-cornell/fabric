<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %>
<%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getFirstChild();
String action = root.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ACTION());
String error = root.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ERR_NAME());
String message = root.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ERR_MSG());
NodeList errs = root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_ERROR_LINE());
%>
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
<script language="javascript" src="header.js"></script>
<div id="navbar_course">

</div>
<div id="course_wrapper">
	<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="85%">
	  <tr>
	  	<td>
		<div id="welcome_cms">
			<img src="images/warning.gif" height="15px">
			An unexpected error prevented the system from completing your request.<br><br>
			Below is detailed information about the bug encountered, which has been forwarded to the CMS
			development team for review.<br><br><br>
			<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_OVER %>">
				Return to Overview Page
			</a>
		</div>
	    </td>
	  </tr>
	  <tr>
	  	<td>
	  		<span id="errordivhead">
	  			<a href="#" class="hide" onclick="show('errordiv','(show)','(hide)'); return false;">(show)</a>
	  		</span>
	  		<div id="errordiv" style="display: none">
	  			<strong>
	  				Action: <%= action %><br>
	  				Exception: <%= error %><br>
	  				Message: <%= message %><br><br>
	  			</strong>
	  			<br>
	  			<% for (int i=0; i < errs.getLength(); i++) {
	  				Element xErr = (Element) errs.item(i); %>
					&nbsp;&nbsp;&nbsp;<%= xErr.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ERR_CLASS()) %>.<%= xErr.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ERR_METHOD()) %>(<%= xErr.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ERR_FILE()) %>:<%= xErr.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ERR_LINE()) %>)
					<br>
				<% } %>
	  		</div>
	  	</td>
	  </tr>
	</table>
</div>

</body>
</html>


