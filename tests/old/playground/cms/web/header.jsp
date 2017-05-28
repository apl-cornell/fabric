<%@ page language="java" import="org.w3c.dom.*, cms.www.*"%>
<% /* The top of all CMS pages. */
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
boolean debug= ((Boolean)session.getAttribute(AccessController.A_DEBUG)).booleanValue();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta http-equiv="Content-Script-Type" content="text/javascript">
    <link href="/web/style.css" rel="stylesheet" type="text/css">
    <link href="/web/navbar_style.css" rel="stylesheet" type="text/css">
    <title>
      Cornell University Course Management System
    </title>

<script type="text/javascript" src="cookies.js"></script>
<script type="text/javascript" src="header.js"></script>
<script type="text/javascript" src="popupmenu.js"></script>
<script type="text/javascript" src="sorttable.js"></script>
<script type="text/javascript" src="staff/grading/gradeassign.js"></script>
<%-- Pages should now insert any head code they require before importing header-page.jsp --%>