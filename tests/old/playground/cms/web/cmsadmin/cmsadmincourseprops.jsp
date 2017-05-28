<%@page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*"%><%
Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
String courseid = course.getAttribute(XMLBuilder.A_COURSEID); %>
<jsp:include page="../header.jsp" />
<jsp:include page="../staff/courseprops-staffperms.js.jsp" />
<jsp:include page="header-cmsadmin.jsp"/>
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
	
  <tr>
    <td id="course_page_container">
      <form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_SETCOURSEPROPS %>" method="post">
      	<!-- include hidden set commands for the course properties that we aren't making settable on this page -->
      	<input type="hidden" name="<%= AccessController.P_COURSEID %>" value="<%= course.getAttribute(XMLBuilder.A_COURSEID) %>">
      	<input type="hidden" name="<%= AccessController.P_NAME %>" value="<%= course.getAttribute(XMLBuilder.A_COURSENAME) %>">
      	<input type="hidden" name="<%= AccessController.P_DISPLAYEDCODE %>" value="<%= course.getAttribute(XMLBuilder.A_DISPLAYEDCODE) %>">
<% Element l= (Element) course.getElementsByTagName(XMLBuilder.TAG_DESCRIPTION).item(0); 
Text text= (Text)l.getFirstChild();%>
        <input type="hidden" name="<%= AccessController.P_DESCRIPTION %>" value="<%= text.getData() %>">
<% if (Boolean.valueOf(course.getAttribute(XMLBuilder.A_SHOWGRADERID)).booleanValue())
{ %>
        <input type="hidden" name="<%= AccessController.P_SHOWGRADERID %>"><%
} //else no set command is necessary %>
        <div id="course_page">
					<jsp:include page="../problem-report.jsp"/>
          <div class="assignment_left">
            <h2>General Properties</h2>
            <table class="assignment_table" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td align="left">Course Code</td>
                <td align="left">
                  <input type="text" size="15" maxlength="15" name="<%= AccessController.P_CODE %>" value="<%= course.getAttribute(XMLBuilder.A_CODE) %>">
                </td>
              </tr>
            </table>
          </div>
          <div class="assignment_left">
            <h2>Course Staff Privileges</h2>
            <table id="permtable" class="assignment_table" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <th style="width: 10%">Remove</th>
                <th>&nbsp;</th>
                <th>Admin</th>
                <th>Groups</th>
                <th>Grades</th>
                <th>Assignments</th>
                <th>Content</th>
              </tr><%
l= (Element) course.getElementsByTagName(XMLBuilder.TAG_STAFF).item(0); 
NodeList list= l.getChildNodes();
int length= list.getLength();
for (int i= 0; i != length; i++)
{
  Element staff= (Element)list.item(i); 
  String netid= staff.getAttribute(XMLBuilder.A_NETID); %>
              <tr>
                <td align="center">
                  <input type="checkbox" name="<%= netid + AccessController.P_REMOVE %>">
                </td>
                <td>
                  <span class="name"><%= staff.getAttribute(XMLBuilder.A_NAME) %></span>
                  <span class="netid">(<%= netid %>)</span>
                </td>
                <td align="center">
                  <input onclick="if (this.checked) adminClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISADMIN %>" id="<%= netid + AccessController.P_ISADMIN %>" <%= staff.hasAttribute(XMLBuilder.A_ISADMIN) ? "checked" : "" %>>
                </td>
                <td align="center">
                  <input onclick="if (!this.checked) otherClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISGROUPS %>" id="<%= netid + AccessController.P_ISGROUPS %>" <%= staff.hasAttribute(XMLBuilder.A_ISGROUPS) ? "checked" : "" %>>
                </td>
                <td align="center">
                  <input onclick="if (!this.checked) otherClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISGRADES %>" id="<%= netid + AccessController.P_ISGRADES %>" <%= staff.hasAttribute(XMLBuilder.A_ISGRADES) ? "checked" : "" %>>
                </td>
                <td align="center">
                  <input onclick="if (!this.checked) otherClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISASSIGN %>" id="<%= netid + AccessController.P_ISASSIGN %>" <%= staff.hasAttribute(XMLBuilder.A_ISASSIGN) ? "checked" : "" %>>
                </td>
                <td align="center">
                  <input onclick="if (!this.checked) otherClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISCATEGORY %>" id="<%= netid + AccessController.P_ISCATEGORY %>" <%= staff.hasAttribute(XMLBuilder.A_ISCATEGORY) ? "checked" : "" %>>
                </td>
              </tr><% 
} %>
            </table>
            <a href="#" onclick="addPermRow(); return false;">(New Row)</a>
          </div>
          <div class="assignment_left">
            <input type="submit" value="Set">
          </div>
          <div class="assignment_left">
            <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_CMSADMIN %>">&lt;&lt; Back</a>
          </div>
        </div>
      </form>
    </td>
  </tr>
<jsp:include page="../footer.jsp"/>