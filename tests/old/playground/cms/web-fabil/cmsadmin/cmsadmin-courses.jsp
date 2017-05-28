<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); %>
  <h2>
    <a name="n_courses"></a>
    Add/Edit Courses
    <span id="courseshead">
      <a class="hide" href="#" onClick="hide('courses', '(hide)', '(show)'); return false;">(hide)</a>
  	</span>
  </h2>
  <div id="courses" class="showhide">
    <table class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
    	<tr>
    		<th align="left">Code</th>
    		<th align="left">Name</th>
    		<th align="left">Enrollment</th>
        <th>&nbsp;</th>
        <th>&nbsp;</th>
     	</tr>
<% Element crs = (Element)root.getElementsByTagName(XMLBuilder.TAG_ALLCOURSES).item(0);
NodeList courseList = crs.getChildNodes();
for (int i = 0; i < courseList.getLength(); i++)
{ 
	Element item = (Element)courseList.item(i); %>
      <tr>
        <td align="left"><%= item.getAttribute(XMLBuilder.A_CODE) %></td>
        <td align="left"><%= item.getAttribute(XMLBuilder.A_COURSENAME) %></td>
        <td align="left"><%= item.getAttribute(XMLBuilder.A_ENROLLMENT) %></td>
        <td align="center"><a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_CMSADMINCOURSEPROPS %>&amp;<%= AccessController.P_COURSEID %>=<%= item.getAttribute(XMLBuilder.A_COURSEID) %>">Course Admin</a></td>
        <td align="center"><a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_FINALGRADES + "&amp;" + AccessController.P_COURSEID + "=" + item.getAttribute(XMLBuilder.A_COURSEID) %>">Upload Classlist</a></td>
  	  </tr><%
} %>
    </table>
  	<p><form name="addcourse" action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_ADDCOURSE %>" method="post">
  		<b>Create Course:</b>
	  	<div class="admin_formbox" style="width: 70%">
		   	<table border="0">
		  		<tr>
			 			<td align="right" nowrap>Code (e.g. COM S 211):</td>
		   			<td align="left"><input type="text" name="<%= AccessController.P_CODE %>" size="15" maxlength="15"></td>
		      </tr>
		    	<tr>
		      	<td align="right" nowrap>Name (e.g. Intro to Java):</td>
		      	<td align="left"><input type="text" name="<%= AccessController.P_COURSENAME %>" size="60" maxlength="300"></td>
		      </tr>
		   		<tr>
		  			<td></td>
		  			<td align="left"><input type="submit" value="Create"></td>
		  		</tr>
		   	</table>
		  </div>
    </form>
  </div>