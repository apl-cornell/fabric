<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
User p = (User)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
%>

<jsp:include page="../header.jsp" />
<jsp:include page="../print-xml-tree.jsp" />
<script src="CalendarPopup.js" type="text/javascript"></script>
<style type="text/css">
  .course_code_edit {float:right; text-align: right; margin:1em; padding-left: 1em; white-space: nowrap; width: 12em}
</style>
<link href="calstyle.css" rel="stylesheet" type="text/css">
<jsp:include page="header-cmsadmin.jsp" />

<div id="course_wrapper_withnav">
<table id="course_wrapper_table" cellpadding="0" cellspacing="0" border="0" width="90%">
<tr>
  <td>&nbsp;</td>
</tr>
<tr>
  <td valign="top" id="course_page_container">
    <div id="course_page">
		<jsp:include page="../problem-report.jsp"/>
	    <span class="course_title">CMS Administration</span><br><br>
	    <ul id="anchors">
	      <li><a href="#n_adminlist">Edit CMS Administrators</a></li>
	      <li><a href="#n_sitenotice">Edit Site Notices</a></li>
	      <li><a href="#n_semester">Set Current Semester</a></li>
	      <li><a href="#n_courses">Add/Edit Courses</a></li>
	      <li><a href="#n_assignments">View Open Assignments</a></li>
	      <li><a href="#n_logsearch">Search Logs</a></li>	      
	      <li><a href="#n_upload">Upload Student Info & Classlist</a></li>
	      <li><a href="#n_emptynames">Users Missing Names</a></li>
	      <li><a href="#n_emptycuids">Enrolled Students Without CUIDs</a></li>
	    </ul>
	    <div class="assignment_left" id="topblock">
<%
Element semRoot = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_SEMESTERS);
Element systemNumbers = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_SYSTEMDATA);
%>
	      <h2>Summary</h2>
				<dl>
					<dt>Current Semester:</dt>
						<dd>
							<b><%= ((Element)semRoot.getElementsByTagName(XMLBuilder.TAG_CURSEMESTER).item(0)).getAttribute(XMLBuilder.A_NAME) %></b>
						</dd>
					<dt>Courses with Enrollment:</dt>
						<dd><%= systemNumbers.getAttribute(XMLBuilder.A_COURSES) %></dd>
					<dt>Active Students:</dt>
						<dd><%= systemNumbers.getAttribute(XMLBuilder.A_ENROLLMENT) %></dd>
					<dt>CMS Users:</dt>
						<dd><%= systemNumbers.getAttribute(XMLBuilder.A_USERS) %></dd>
				</dl>
	    </div>
	  <div class="assignment_left" id="topblock">
        <jsp:include page="cmsadmin-list.jsp"/>
      </div>
	  <div class="assignment_left">
        <jsp:include page="cmsadmin-sitenotice.jsp"/>
      </div>	  
      <div class="assignment_left">
      	<jsp:include page="cmsadmin-semester.jsp"/>
      </div>
      <div class="assignment_left">
	      <jsp:include page="cmsadmin-courses.jsp"/>
      </div>
      <div class="assignment_left">
      	<jsp:include page="cmsadmin-assignments.jsp"/>
      </div>
      <div class="assignment_left">
      	<jsp:include page="cmsadmin-logsearch.jsp"/>
      </div>
      <div class="assignment_left">
      	<h2>
      		<a name="n_upload"></a>
      		Upload Student Info & Classlist
      		<span id="uploadhead">
      		  <a class="hide" href="#" onClick="show('upload', '(show)', '(hide)'); return false;">(show)</a>
      		</span>
      	</h2>
      	<div id="upload" class="showhide" style="display: none">
      		<jsp:include page="../upload.jsp"/>
		</div>
      </div>
      <div class="assignment_left">
        <jsp:include page="cmsadmin-emptynames.jsp"/>
      </div>
      <div class="assignment_left">
      	<jsp:include page="cmsadmin-emptycuids.jsp"/>
      </div>
    </div>
  </td>
  <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
</tr>
<jsp:include page="../footer.jsp"/>
