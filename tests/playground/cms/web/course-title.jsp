<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); %>


		<div class="course_title">
			<%= course.getAttribute(XMLBuilder.A_DISPLAYEDCODE) + ": " + course.getAttribute(XMLBuilder.A_COURSENAME) + " (" + course.getAttribute(XMLBuilder.A_SEMESTER) + ")" %>
<% if (Boolean.valueOf(course.getAttribute(XMLBuilder.A_COURSEFROZEN)).booleanValue())
	{ %>
			<span class="course_title" id="ct_frozen"> [FROZEN]</span>
<% } %>
		</div>
                