<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.$Static.TAG_ROOT).item(0);
Element course= XMLUtil.$Proxy.getFirstChildByTagName(root, XMLBuilder.$Static.TAG_COURSE); %>


		<div class="course_title">
			<%= course.getAttribute(XMLBuilder.$Static.A_DISPLAYEDCODE) + ": " + course.getAttribute(XMLBuilder.$Static.A_COURSENAME) + " (" + course.getAttribute(XMLBuilder.$Static.A_SEMESTER) + ")" %>
<% if(Boolean.valueOf(course.getAttribute(XMLBuilder.$Static.A_COURSEFROZEN)).booleanValue())
	{ %>
			<span class="course_title" id="ct_frozen"> [FROZEN]</span>
<% } %>
		</div>
                