<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %>
<% Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root = (Element) displayData.getChildNodes().item(0);
   NodeList cs = root.getElementsByTagName(XMLBuilder.TAG_COURSE);
   Element course = (Element) cs.item(cs.getLength() - 1);
   String courseid = course.getAttribute(XMLBuilder.A_COURSEID);
   String code = course.getAttribute(XMLBuilder.A_CODE);
   Element prefs = (Element) root.getElementsByTagName(XMLBuilder.TAG_PREFS).item(0); %>
<jsp:include page="../header.jsp" />
<style type="text/css">
  span#right_float {float: right}
</style>
<jsp:include page="../header-page.jsp" />
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">

<tr>
	<jsp:include page="../navbar.jsp"/>
  <td valign="top"  id="course_page_container">
  <div id="course_page">
    <span class="assignment_title"><%= code %> Notifications</span>
    <br><br>
    <jsp:include page="../problem-report.jsp"/>
    <br>
    <form method="post" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_SETSTUDENTPREFS + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>">
    	<h2>Send me an E-Mail when...</h2>
    	<p><input type="checkbox" name="<%= AccessController.P_PREF_NEWASSIGN %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_NEWASSIGN) ? " checked" : "" %>>
		  ...a new assignment is released.
		</p>
		<p><input type="checkbox" name="<%= AccessController.P_PREF_DATECHANGE %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_DATECHANGE) ? " checked" : "" %>>
		  ...the due date for an assignment is changed.
		</p>
		<p><input type="checkbox" name="<%= AccessController.P_PREF_INVITATION %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_INVITATION) ? " checked" : "" %>>
		  ...you receive a new group invitation, or an invitation you have sent is accepted/rejected.
		</p>
		<p><input type="checkbox" name="<%= AccessController.P_PREF_GRADERELEASE %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_GRADERELEASE) ? " checked" : "" %>>
		  ...grades for an assignment are released.
		</p>
		<p><input type="checkbox" name="<%= AccessController.P_PREF_GRADECHANGE %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_GRADECHANGE) ? " checked" : "" %>>
		  ...one of your grades is changed (i.e. regrade response).
		</p>
		<p><input type="checkbox" name="<%= AccessController.P_PREF_FILESUBMIT %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_FILESUBMIT) ? " checked" : "" %>>
		  ...your group successfully uploads a submission.
		</p>
		<p><input type="checkbox" name="<%= AccessController.P_PREF_FINALGRADES %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_FINALGRADES) ? " checked" : "" %>>
		  ...final grades for the course are released.
		</p>
		<p><input type="checkbox" name="<%= AccessController.P_PREF_TIMESLOTCHANGE %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_TimeslotCHANGE) ? " checked" : "" %>>
		  ...scheduled time slot changes.
		</p>
		<br/>
		<p>
		<input type="radio" name="<%= AccessController.P_APPLYSCOPE %>" value="<%= AccessController.P_APPLYTHIS %>" checked> Apply to this course only<br/>
		<input type="radio" name="<%= AccessController.P_APPLYSCOPE %>" value="<%= AccessController.P_APPLYALL %>"> Apply to all current courses<br/> <%-- TODO: add future courses too --%>
		</p>
		<input type="submit" value="Update">
    </form>
  </div>
  </td>
  <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
</tr>
<jsp:include page="../footer.jsp" />