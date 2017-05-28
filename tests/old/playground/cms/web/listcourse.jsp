<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Element courses = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ALLCOURSES);
NodeList courseList = courses.getChildNodes();
int length = courseList.getLength();
if (length > 0){ %>
<td class="staff_navigation" rowspan="3">
  <div id="navbar_top">&nbsp;</div>
  <ul id="navbar_content">
	<li>Course List:</li><%
 	for (int i=0; i<length; i++){
 		Element course = (Element)courseList.item(i);
 		String courseid= course.getAttribute(XMLBuilder.A_COURSEID); 
 		String coursename = course.getAttribute(XMLBuilder.A_CODE);
 		String link = "<a href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_COURSE + "&amp;"+ AccessController.P_COURSEID + "=" + courseid + "\">";%>
	<li>
		<a href="?<%= AccessController.P_ACTION %>=<%=AccessController.ACT_COURSE %>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
			<%=coursename%>
		</a>
	</li><%
 	}%>
  </ul>
  <div id="navbar_bottom">
     <div id="navbar_bottom_left">&nbsp;</div>
  </div>
</td><%
}%>
