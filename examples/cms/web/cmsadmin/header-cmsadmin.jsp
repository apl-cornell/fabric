<%@ page language="Java" import="cms.www.*, cms.auth.*, cms.model.*" %>
<% boolean debug= ((Boolean)session.getAttribute(AccessController.A_DEBUG)).booleanValue();
User p= (User) session.getAttribute(AccessController.A_PRINCIPAL);
String NetID = p.getNetID(); %>
</head>
<body>

<jsp:include page="../header-login.jsp" />

<div id="navbar_course">
<% String actURL = "?" + AccessController.P_ACTION + "=" + AccessController.ACT_CMSADMIN; %>
  <span class="navlink_course" id="navlink_course_overview">
    <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_OVER %>">CMS Overview</a>
  </span>
   <span class="navlink_course">
    &nbsp;&nbsp;<a href="<%= actURL %>">CMS Admin</a>
  </span>
</div>

<div id="course_background">
