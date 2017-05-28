<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
boolean noLogs = root.hasAttribute(XMLBuilder.A_INITIALSEARCH);
%><jsp:include page="../header.jsp"/>
<script src="CalendarPopup.js" type="text/javascript"></script>
<style type="text/css">
  div#div_left {float: left}
  span#span_right {float: right}
  .showhide {font-weight: normal}
</style>
<link href="calstyle.css" rel="stylesheet" type="text/css">
<jsp:include page="../header-page.jsp"/>
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">

  <tr>
<jsp:include page="navbar.jsp"/>
    <td valign="top" id="course_page_container">
      <div id="course_page">
        <jsp:include page="../problem-report.jsp"/>
        <br>
        <div class="assignment_left">
        	<jsp:include page="logsearch.jsp"/>
        </div>
        <% if (!noLogs) { %>
        <div class="assignment_left">
          <jsp:include page="logresults.jsp"/>
        </div>
        <% } %>
 	    </div>
	  </td>
    <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
	</tr>
<jsp:include page="../footer.jsp"/>