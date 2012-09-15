<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element) displayData.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_ROOT()).item(0);
Element course= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_COURSE()); %>


		<div class="course_title">
			<%= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DISPLAYEDCODE()) + ": " + course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSENAME()) + " (" + course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SEMESTER()) + ")" %>
<% if (Boolean.valueOf(course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEFROZEN())).booleanValue())
	{ %>
			<span class="course_title" id="ct_frozen"> [FROZEN]</span>
<% } %>
		</div>
                