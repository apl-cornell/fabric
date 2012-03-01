<%@ page language="java" import="org.w3c.dom.*, cms.www.util.Util, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
NodeList cs = root.getElementsByTagName(XMLBuilder.TAG_COURSE);
Element course = (Element) cs.item(cs.getLength() - 1);
String courseid = course.getAttribute(XMLBuilder.A_COURSEID); 
Element prefs = (Element) root.getElementsByTagName(XMLBuilder.TAG_PREFS).item(0);  %>
<jsp:include page="../header.jsp"/>
<style type="text/css">
  #right_float {float: right}
</style>
<jsp:include page="../header-page.jsp"/>
<div id="course_wrapper_withnav">

<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">

  <tr>
<jsp:include page="navbar.jsp"/>
    <td valign="top" id="course_page_container">
      <div id="course_page">
		    <span class="assignment_title">Course Notifications</span>
		    <br><br>
		   	<jsp:include page="../problem-report.jsp"/>
		    <br>
		    <form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_SETSTAFFPREFS + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>">
		    <h2>Send me an E-Mail...</h2>
		    <p><input type="checkbox" name="<%= AccessController.P_PREF_NEWASSIGN %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_NEWASSIGN) ? " checked" : "" %>>
		      ...when a new assignment is released.
		    </p>
		    <p><input type="checkbox" name="<%= AccessController.P_PREF_DATECHANGE %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_DATECHANGE) ? " checked" : "" %>>
		      ...when the due date for an assignment is changed.
		    </p>
		    <p><input type="checkbox" name="<%= AccessController.P_PREF_ASSIGNEDTO %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_ASSIGNEDTO) ? " checked" : "" %>>
		      ...when I am assigned to grade groups for an assignment.
		    </p>
		    <p><input type="checkbox" name="<%= AccessController.P_PREF_FINALGRADES %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_FINALGRADES) ? " checked" : "" %>>
          ...when final grades are released.
        </p>
		    <p><input type="checkbox" name="<%= AccessController.P_PREF_REGRADEREQUEST %>"<%= prefs.hasAttribute(XMLBuilder.A_PREF_NEWREQUEST) ? " checked" : "" %>>
		      ...when a group I am assigned to grade requests a regrade.
		    </p>
        <br>
		    <input type="submit" value="Update">
	    </div>
	  </td>
    <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
	</tr>
<jsp:include page="../footer.jsp"/>