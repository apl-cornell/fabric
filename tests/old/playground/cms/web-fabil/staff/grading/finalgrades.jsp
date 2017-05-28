<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
/************************************************************************
* display name, course options (lecture number, credit hours, etc),
* and final grade for each student in a course
************************************************************************/
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getFirstChild();
Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
String courseID = course.getAttribute(XMLBuilder.A_COURSEID);
boolean hasSection = course.hasAttribute(XMLBuilder.A_HASSECTION);
boolean isStaff = root.hasAttribute(XMLBuilder.A_ISADMIN);
NodeList students = XMLUtil.getChildrenByTagName(root, XMLBuilder.TAG_STUDENT);
%>
<jsp:include page="../../header.jsp"/>
<jsp:include page="../../header-page.jsp"/>
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" cellpadding="0" cellspacing="0" width="100%">
  <tr>
  <% if (isStaff) { %>
		<jsp:include page="../navbar.jsp"/>
  <% } %>
    <td valign="top" id="course_page_container">
      <div id="course_page">
				<jsp:include page="../../problem-report.jsp"/>
        <h2>Student Information &amp; Final Grades (<%= course.getAttribute(XMLBuilder.A_CODE)%>)</h2>

        <table width="100%" class="assignment_table" cellpadding="1" cellspacing="0" border="0">
          <tr>
			<% if (isStaff) { %><th>Final Grade</th><% } %>
            <th>Last Name</th>
            <th>First Name</th>
            <th>CUID</th>
            <th>NetID</th>
       	<% if (hasSection) { %>
            <th>Section</th>
        <% } %>
            <th>Grade Option</th>
            <th>Credit Hours</th>
          </tr>
          <% for (int i=0; i < students.getLength(); i++) {
              Element student = (Element) students.item(i); %>
              <tr>
                <% if (isStaff) { %><td><%= student.getAttribute(XMLBuilder.A_FINALGRADE) %></td><% } %>  
                <td><%= student.getAttribute(XMLBuilder.A_LASTNAME) %></td>
                <td><%= student.getAttribute(XMLBuilder.A_FIRSTNAME) %></td>
                <td><%= student.getAttribute(XMLBuilder.A_CUID) %></td>
                <td><%= student.getAttribute(XMLBuilder.A_NETID) %></td>
          	<% if (hasSection) { %>
                <td><%= student.getAttribute(XMLBuilder.A_SECTION) %></td>
            <% } %>
                <td><%= student.getAttribute(XMLBuilder.A_GRADEOPTION) %></td>
                <td><%= student.getAttribute(XMLBuilder.A_CREDITS) %></td>
              </tr>
          <% } %>
        </table>
        <div style="float:left">
        	<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_VIEWUPLOAD + "&amp;" + AccessController.P_COURSEID + "=" + courseID %>">Upload classlist file</a>
        </div>
        <div style="float: right">
	        Export as <a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_EXPORTFINALGRADES + "&amp;" + AccessController.P_COURSEID + "=" + courseID %>">CSV</a>
	    </div>
      </div>
    </td>
   <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
  </tr>
<jsp:include page="../../footer.jsp"/>
