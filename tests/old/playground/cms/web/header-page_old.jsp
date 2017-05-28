<%@ page language="Java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*" %>
<% Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
String NetID = p.getNetID();
boolean debug= ((Boolean)session.getAttribute(AccessController.A_DEBUG)).booleanValue(); 
String URL= request.getServletPath(); 
boolean cmsOverview= URL.equals(AccessController.OVERVIEW_URL);
boolean cmsAdminOverview= (URL.equals(AccessController.CMSADMIN_URL)||URL.equals(AccessController.CMSADMINCOURSEPROPS_URL)||URL.equals(AccessController.CMSADMIN_LOGRESULTS_URL));
%>
</head>
<body>
<%
if (debug) {%>
	<%-- for debug purposes --%>
	<jsp:include page="print-xml-tree.jsp" /><%
}%>
<div id="dhtmltooltip"></div>
<script type="text/javascript">
window.onload=startList;
<%-- window.onload=hideByCookie; --%>
document.onmousemove=positiontip
</script>
<div id="navbar_course">
<table border="0" width="100%"  cellpadding="0" cellspacing="0">
  <tr>
<td nowrap align="left" valign="middle" rowspan="2">
<ul class="topmenu" id="dmenu">

<%
Node l = root.getElementsByTagName(XMLBuilder.TAG_STUDENTCOURSES).item(0);
int length= 0;
NodeList myCourses = l.getChildNodes();
length= myCourses.getLength();
String link=null;
String courseName=null;
boolean isAdminForAnyCourse= (root.getElementsByTagName(XMLBuilder.TAG_STAFFCOURSES).item(0).getChildNodes().getLength()!=0);
String studentCourseMenuTitle= (isAdminForAnyCourse ? "Student Courses&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "Courses&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" );
if (length!=0) {
%>    
    <li class="topmenuli"><a class="topmenutitle"><%=studentCourseMenuTitle%></a>
    <ul class="submenuul">

<%
for (int i = 0; i < length; i++) {
  Element course = (Element) myCourses.item(i); 
  link= "?"+ AccessController.P_ACTION + "=" + AccessController.ACT_COURSE + "&amp;" + AccessController.P_COURSEID + "=" + course.getAttribute(XMLBuilder.A_COURSEID) + "&amp;" + AccessController.P_RESET + "=1";
  courseName=course.getAttribute(XMLBuilder.A_DISPLAYEDCODE);
%>   
        <li class="submenuli"><a href="<%=link%>"><%=courseName %></a></li>
<%
} %>
    </ul>

    </li>
<%
} %>


<li class="topmenuli">
<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_OVER %>" 
  class="topmenutitle<%=cmsOverview ? "_active" : "" %>">CMS Overview</a>

</li>


<%
l= root.getElementsByTagName(XMLBuilder.TAG_STAFFCOURSES).item(0);
myCourses= l.getChildNodes();
length= myCourses.getLength();
if (length!=0) {
%>

    <li class="topmenuli"><a  class="topmenutitle">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Staff Courses</a>

    <ul class="submenuul">
    <%
for (int i= 0; i != length; i++) { 
  Element course= (Element) myCourses.item(i); 
  link= "?"+ AccessController.P_ACTION + "=" + AccessController.ACT_COURSEADMIN  + "&amp;" + AccessController.P_COURSEID + "=" + course.getAttribute(XMLBuilder.A_COURSEID);
  courseName=course.getAttribute(XMLBuilder.A_DISPLAYEDCODE);
  
  %>
        <li class="submenuli"><a href="<%=link%>"><%=courseName %></a></li>
       <%
}

%>
    </ul>

    </li>
<%
}%>

<%
if (p.isCMSAdmin())
{ %>
    <li class="topmenuli">
    <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_CMSADMIN %>" 
      class="topmenutitle<%=cmsAdminOverview ? "_active" : "" %>">
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CMS Admin&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
</li>
    </a><%
} %>
</ul>
</td>
<%
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
boolean showViewAs= false;
long courseid=0;
if (course!=null) {
    courseid= Long.parseLong(course.getAttribute(XMLBuilder.A_COURSEID));
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
