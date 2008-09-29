<%@ page language="java" import="org.w3c.dom.*,cms.auth.*, cms.model.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
User p= (User) session.getAttribute(AccessController.A_PRINCIPAL);
String netid= p.getNetID();
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
String courseid= course.getAttribute(XMLBuilder.A_COURSEID); 
boolean isadmin= course.hasAttribute(XMLBuilder.A_ISADMIN);
boolean isgroups= course.hasAttribute(XMLBuilder.A_ISGROUPS);
boolean isgrades= course.hasAttribute(XMLBuilder.A_ISGRADES);
boolean isassign= course.hasAttribute(XMLBuilder.A_ISASSIGN); %>
<jsp:include page="../../header.jsp" />
<jsp:include page="../../header-page.jsp" />
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%"><%
Element status= (Element) root.getElementsByTagName(XMLBuilder.TAG_STATUS).item(0); %>


<tr>
	<jsp:include page="../navbar.jsp"/>
  <td valign="top" id="course_page_container">
    <div id="course_page">
			<jsp:include page="../../problem-report.jsp"/>
      <jsp:include page="assignment-table.jsp"/> 
    </div>
  </td>
   <td id="course_menu_container" width="14px"><div id="course_menu_top">&nbsp;</div>
   
   <br><br><br><br><br><br><br><br>
   </td>
</tr>
<jsp:include page="../../footer.jsp"/>
