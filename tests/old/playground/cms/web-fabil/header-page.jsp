<%@ page language="Java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*" %>
<% Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
User p = (User)session.getAttribute(AccessController.A_PRINCIPAL);
String NetID = p.getNetID();
boolean debug= ((Boolean)session.getAttribute(AccessController.A_DEBUG)).booleanValue(); 
String URL= request.getServletPath(); 
boolean cmsAdminOverview= (URL.equals(AccessController.CMSADMIN_URL)||URL.equals(AccessController.CMSADMINCOURSEPROPS_URL)||URL.equals(AccessController.CMSADMIN_LOGRESULTS_URL));
%>
</head>
<body>
<%
if (debug && false) {%>
	<%-- for debug purposes --%>
	<jsp:include page="print-xml-tree.jsp" /> <%
}%>
<div id="dhtmltooltip"></div>
<script type="text/javascript">
document.onmousemove=positiontip
</script>
<div id="navbar_course">
<table border="0" width="100%"  cellpadding="0" cellspacing="0">
  <tr>
<td id="topnav" nowrap align="left" valign="middle" rowspan="2">
<ul>
<%
Node l = root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_STUDENTCOURSES()).item(0);
int length= 0;
NodeList myCourses = l.getChildNodes();
length= myCourses.getLength();
String link=null;
String courseName=null;
boolean isAdminForAnyCourse= (root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_STAFFCOURSES()).item(0).getChildNodes().getLength()!=0);
String studentCourseMenuTitle= (isAdminForAnyCourse ? "Student Courses" : "Courses" );
if (length!=0) {
%>    
    <li class="menuhead"><a><%=studentCourseMenuTitle%></a>
    <ul><%
for (int i = 0; i < length; i++) {
  Element course = (Element) myCourses.item(i); 
  link= "?"+ AccessController.P_ACTION + "=" + AccessController.ACT_COURSE + "&amp;" + AccessController.P_COURSEID + "=" + course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID()) + "&amp;" + AccessController.P_RESET + "=1";
  courseName=course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DISPLAYEDCODE());
%>   
       <li><a href="<%=link%>"><%=courseName %></a></li><%
} %>
    </ul>
    </li><%
} %>
<li><a <%=URL.equals(AccessController.OVERVIEW_URL) ? "class=\"currentpage\" " : "" %>href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_OVER %>">
CMS Overview
</a></li>

<%
/* STAFF */
l= root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_STAFFCOURSES()).item(0);
myCourses= l.getChildNodes();
length= myCourses.getLength();
if (length!=0) {
%>
    <li class="menuhead"><a>Staff Courses</a>
    <ul><%
for (int i= 0; i != length; i++) { 
  Element course= (Element) myCourses.item(i); 
  link= "?"+ AccessController.P_ACTION + "=" + AccessController.ACT_COURSEADMIN  + "&amp;" + AccessController.P_COURSEID + "=" + course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID());
  courseName=course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DISPLAYEDCODE());
  
  %>
        <li><a href="<%=link%>"><%=courseName %></a></li><%
}
%>
    </ul>
    </li><%
}
if (p.isCMSAdmin())
{ %>
    <li><a <%=cmsAdminOverview ? "class=\"currentpage\" " : "" %>href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_CMSADMIN %>">
      CMS Admin
    </a></li><%
} %>
</ul>
</td>
<%
Element course= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_COURSE());
boolean showViewAs= false;
String courseid=null;
if (course!=null) {
    courseid= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID());
    showViewAs= p.isAdminPrivByCourseID(courseid) || p.isInStaffAsBlankMode(); 
}
%>
<% if (showViewAs) { %>
<td valign="middle" align="right" id="header_authuser">
<jsp:include page="header-login.jsp" />
</td>

</tr>
<tr>
<jsp:include page="viewAs.jsp" />
</tr>
</table>
<% } else { %>

    <td valign="bottom" align="right" id="header_authuser" rowspan="2">
    <jsp:include page="header-login.jsp" />
        </td>
    
</tr>
</table>
<% }%>
</div>
<div id="course_background">
<br>
