<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*" %>
<% 
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
String URL= request.getServletPath(); 
User p = (User)session.getAttribute(AccessController.A_PRINCIPAL);
Element root= (Element)displayData.getFirstChild();
Element course= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_COURSE());
Element category= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_CATEGORY());
boolean isNewAssign= false; /* whether this is the new-assignment page   */
boolean isEditPage= false;  /* whether this is the edit-assignment page */
boolean showAddDrop= root.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SHOWADDDROP());
String assignID="";
Element assignment= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_ASSIGNMENT()); 
//Element surveys = XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_SURVEYS());
assignID= assignment == null ? "" : assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID());
if (URL.equals(AccessController.ASSIGNADMIN_URL)) {
    if (assignID.equals("0")) {
      isEditPage= false;
      isNewAssign= true;
    } else {
      isEditPage= true;
      isNewAssign= false;
    }
}

boolean isAdmin= course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISADMIN());
boolean isGroups = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISGROUPS());
boolean isGrades = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISGRADES());
boolean isAssign = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISASSIGN());
boolean isCategory = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISCATEGORY());
boolean studentsAccess = isAdmin || (isGroups && isGrades);
boolean isPage = false;
NodeList allassigns = (isAdmin || isGroups || isGrades || isAssign) ? XMLUtil._Proxy.getFirstChildByTagName(course, XMLBuilder._Static._Proxy.$instance.get$TAG_ASSIGNMENTS()).getChildNodes() : null;
NodeList allsurveys = (isAdmin || isGroups || isGrades || isAssign) ? XMLUtil._Proxy.getFirstChildByTagName(course, XMLBuilder._Static._Proxy.$instance.get$TAG_SURVEYS()).getChildNodes() : null;
NodeList allcats = (isAdmin || isCategory) ? XMLUtil._Proxy.getFirstChildByTagName(course, XMLBuilder._Static._Proxy.$instance.get$TAG_CATEGORIES()).getChildNodes() : null;
allassigns = (allassigns == null ? new CMSNodeList() : allassigns);
allsurveys = (allsurveys == null ? new CMSNodeList() : allsurveys);
allcats = (allcats == null ? new CMSNodeList() : allcats);
String courseid= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID());
%>
<td class="staff_navigation" rowspan="3" width="224px">
 <table border="0" cellpadding="0" cellspacing="0" width="224px">
 <tr>
  <td width="20px">&nbsp;</td>
  <td id="sidenav" colspan="2" width="170px">
  <ul>
  <li>
   <span class="course"><%=course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DISPLAYEDCODE())%></span><br/>
   <span class="semester">(<%=course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SEMESTER())%>)</span>
  </li>
  <li class="sep"><hr></li>
  <li><a <%= URL.equals(AccessController.COURSEADMIN_URL) ? "class=\"currentpage\"" : "" %> href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_COURSEADMIN%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
    Home
  </a></li><%
  /* Course Options */
  if (isAdmin) {%>
  <li><a <%= URL.equals(AccessController.COURSEPROPS_URL) ? "class=\"currentpage\"" : "" %> href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_COURSEPROPS%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
    Setup
  </a></li><%
  }

  /* Update Category Properties/Add new category */
boolean noContent = allcats.getLength() == 0;
boolean inCategoryAdmin = URL.equals(AccessController.CATEGORYADMIN_URL);
boolean inNewCategory = category != null && category.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ID()).equals("0");
if (isAdmin || isCategory) { %>
  <li><a <%= inCategoryAdmin && inNewCategory ? "class=\"currentpage\"" : "" %> href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_NEWCATEGORY%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
    Content
  </a>
  <ul>
    <li class="menuhead">
      <a <%= inCategoryAdmin && !inNewCategory ? "class=\"currentpage\" " : noContent ? "class=\"unavailable\" " : "" %>href="#">
        Edit Layout
      </a>
      <ul><%
	if (noContent) {%>
      <li>No content tables to edit.</li>
      <li><a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_NEWCATEGORY%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">(Make new)</a></li><%
    }
    else for (int i = 0; i < allcats.getLength(); i++) {
   	  Element xCat = (Element) allcats.item(i); %>
      <li><a href="?<%= AccessController.P_ACTION%>=<%=AccessController.ACT_EDITCTG%>&amp;<%=AccessController.P_CATID%>=<%=xCat.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ID())%>">&bull;&nbsp;<%= xCat.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %></a></li><%
	}%>
      </ul>
    </li>
    <li class="menuhead">
      <a <%=URL.equals(AccessController.CTGCONTENTSADMIN_URL) ? "class=\"currentpage\" " : noContent ? "class=\"unavailable\" " : ""%>href="#">
        Add/Edit Data
      </a>
      <ul><%
	if (noContent) {%>
      <li>No content tables to edit.</li>
      <li><a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_NEWCATEGORY%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">(Make new)</a></li><%
    }
    else for (int i = 0; i < allcats.getLength(); i++) {
   	  Element xCat = (Element) allcats.item(i); %>
      <li><a href="?<%= AccessController.P_ACTION%>=<%=AccessController.ACT_ADDNEDITCONTENTS%>&amp;<%=AccessController.P_CATID%>=<%=xCat.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ID())%>">&bull;&nbsp;<%= xCat.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %></a></li><%
	}%>
      </ul>
    </li>
  </ul>
  </li><%
}

  /* Students */
if (studentsAccess) {%>
  <li>
    <a <%=URL.equals(AccessController.STUDENTS_URL) && !showAddDrop ? "class=\"currentpage\" " : ""%>href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_STUDENTS%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
      Students
    </a>
    <ul>
      <li><a <%=showAddDrop ? "class=\"currentpage\" " : ""%>href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_ADDSTUDENTPAGE%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
        Add/Drop
      </a></li>
      <li><a <%=URL.equals(AccessController.GRADEALLASSIGNS_URL) ? "class=\"currentpage\" " : ""%>href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_STUDENTS%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>&amp;<%=AccessController.P_GRADEMESSAGE%>=<%=AccessController.P_TRUE%>">
        Grades
      </a></li>
    </ul>
  </li><%
}

  /* Assignment and grading pages (VERY LONG) */
boolean noAssignments = allassigns.getLength() < 1;%>
  <li>
    <a <%=URL.equals(AccessController.ASSIGNLISTADMIN_URL) ? "class=\"currentpage\" " : ""%>href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_ASSIGNLISTADMIN%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
      Assignments
    </a>
    <ul>
    <li class="menuhead">
      <a <%=isNewAssign ? "class=\"currentpage\" " : ""%>href="#">New</a>
      <ul>
      <li><a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_NEWASSIGN%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
        &bull;&nbsp;New Assignment
      </a></li>
      <li><a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_NEWSURVEY%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
        &bull;&nbsp;New Survey
      </a></li>
      <li><a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_NEWQUIZ%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
        &bull;&nbsp;New Quiz
      </a></li>
      </ul>
    </li>
    <li class="menuhead">
      <a <%=isEditPage ? "class=\"currentpage\" " : noAssignments ? "class=\"unavailable\" " : ""%>href="#">
        Edit
      </a>
      <ul><%
if (allassigns.getLength() == 0) { %>
        <li>No assignments to edit.</li><%
	if (isAdmin || isAssign) {%>
        <li class="menuhead">
          <a href="#">(Make new)</a>
          <ul>
          <li><a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_NEWASSIGN%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
            &bull;&nbsp;New Assignment
          </a></li>
          <li><a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_NEWSURVEY%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
            &bull;&nbsp;New Survey
          </a></li>
          <li><a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_NEWQUIZ%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
            &bull;&nbsp;New Quiz
          </a></li>
          </ul>
        </li><%
	}
} else for (int i = 0; i < allassigns.getLength(); i++) {
  /* Would it be faster to generate these assignment links once, but pay penalty of string concat instead of writing to the buffer? 
   * Maybe create our own buffer?
   * Though, can use StringBuilder instead of concats if we move to 1.5 or above - Alex, Oct 2007 */
    Element xAssign = (Element) allassigns.item(i);
    String assignType = xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE()); %>
        <li><a href="?<%= AccessController.P_ACTION%>=<%
	if (assignType.equals(Integer.toString(Assignment._Static._Proxy.$instance.get$ASSIGNMENT())))
		{%><%=AccessController.ACT_ASSIGNADMIN%><%}
	else if (assignType.equals(Integer.toString(Assignment._Static._Proxy.$instance.get$SURVEY())))
		{%><%=AccessController.ACT_SURVEYADMIN%><%} 
	else if (assignType.equals(Integer.toString(Assignment._Static._Proxy.$instance.get$QUIZ())))
		{%><%=AccessController.ACT_QUIZADMIN%><%}
	/*still not done with this link...*/%>&amp;<%=AccessController.P_ASSIGNID%>=<%=xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID())%>">
	      &bull;&nbsp;<%=xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME())%>
        </a></li><%
} 
	for (int i = 0; i < allsurveys.getLength(); i++) {
		Element xSurvey = (Element) allsurveys.item(i);
		String url  = "?" + AccessController.P_ACTION + "=" + AccessController.ACT_SURVEYADMIN + "&amp;" + AccessController.P_ASSIGNID + "=" + xSurvey.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID());
	%><li><a href="<%= url %>">&bull;&nbsp;<%=xSurvey.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME())%></a></li>
	<%}
%>
      </ul>
    </li><%
if (isAdmin || isGroups || isGrades) {%>
    <li class="menuhead">
      <a <%=URL.equals(AccessController.GRADEASSIGN_URL) ? "class=\"currentpage\" " : noAssignments ? "class=\"unavailable\" " : ""%>href="#">
        Groups
      </a>
      <ul><%
  if (allassigns.getLength() == 0) { %>
        <li>No assignments.</li><%
        /* Decided @ meeting to not to bother with "make new" menu here because it's too hard to mouse over, and there's an obvious one anyway - Alex, Oct 2007*/
  } else for (int i = 0; i < allassigns.getLength(); i++) { 
    Element xAssign = (Element) allassigns.item(i); %>
        <li><a href="?<%= AccessController.P_ACTION%>=<%=AccessController.ACT_GRADEASSIGN%>&amp;<%=AccessController.P_ASSIGNID%>=<%=xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID())%>">
	      &bull;&nbsp;<%=xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME())%>
        </a></li><%
  } %>
      </ul>
    </li>
    
   <li class="menuhead">
      <a <%=URL.equals(AccessController.SURVEYRESULT_URL) ? "class=\"currentpage\" " : (allsurveys.getLength() < 1) ? "class=\"unavailable\" " : ""%>href="#">
        Surveys
      </a>
      <ul><%
  if (allsurveys.getLength() == 0) { %>
        <li>No surveys.</li><%
        /* Decided @ meeting to not to bother with "make new" menu here because it's too hard to mouse over, and there's an obvious one anyway - Alex, Oct 2007*/
  } else for (int i = 0; i < allsurveys.getLength(); i++) { 
    Element xSurvey = (Element) allsurveys.item(i); %>
        <li><a href="?<%= AccessController.P_ACTION%>=<%=AccessController.ACT_SURVEYRESULT%>&amp;<%=AccessController.P_ASSIGNID%>=<%=xSurvey.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID())%>">
	      &bull;&nbsp;<%=xSurvey.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME())%>
        </a></li><%
  } %>
      </ul>
    </li>
    
    <li class="menuhead"><%
  boolean hasSchedules = false;
  for (int i = 0; i < allassigns.getLength(); i++) { 
    Element xAssign = (Element) allassigns.item(i);
	if (xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_USESCHEDULE()).equals("true")) {
	  if (!hasSchedules) /* first schedule that we came across */ {%>
      <a <%=URL.equals(AccessController.ASSIGNSCHED_URL) ? "class=\"currentpage\" " : ""%>href="#">
        Schedule
      </a>
      <ul><%
      }
	  hasSchedules = true;%>
        <li><a href="?<%= AccessController.P_ACTION%>=<%=AccessController.ACT_SCHEDULE%>&amp;<%=AccessController.P_ASSIGNID%>=<%=xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID())%>">
	      &bull;&nbsp;<%=xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME())%>
        </a></li><%
    }
  }
  if (!hasSchedules) { %>
      <a <%=URL.equals(AccessController.ASSIGNSCHED_URL) ? "class=\"currentpage\" " : "class=\"unavailable\" "%>href="#">
        Schedule
      </a>
      <ul>
        <li>No assignments with schedules.</li><%
  }%>
      </ul>
    </li><%
}

if (isAdmin || isGrades) {%>
    <li class="menuhead">
      <a <%=URL.equals(AccessController.GRADESTUDENTS_URL) ? "class=\"currentpage\" " : noAssignments ? "class=\"unavailable\" " : ""%>href="#">
        Grade
      </a>
      <ul><%
  if (allassigns.getLength() == 0) { %>
        <li>No assignments.</li><%
  } else for (int i = 0; i < allassigns.getLength(); i++) { 
    /* This is very similar to groups - Alex, Oct 2007 */
    Element xAssign = (Element) allassigns.item(i); %>
        <li><a href="?<%= AccessController.P_ACTION%>=<%=AccessController.ACT_GRADEASSIGN%>&amp;<%=AccessController.P_ASSIGNID%>=<%=xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID())%>&amp;<%=AccessController.P_GRADEMESSAGE%>=<%=AccessController.P_TRUE%>">
	      &bull;&nbsp;<%=xAssign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME())%>
        </a></li><%
  } %>
      </ul>
    </li><%
}%>
    </ul>
  </li><%-- end assignments block --%><%
   
if (isAdmin) {%>
  <li><a <%=URL.equals(AccessController.COURSELOG_URL) ? "class=\"currentpage\" " : ""%>href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_COURSE_LOGSEARCH%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
    Search Logs
  </a></li>
  <li><a <%=URL.equals(AccessController.UPLOAD_URL) ? "class=\"currentpage\" " : ""%>href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_VIEWUPLOAD%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
    Upload CSV
  </a></li>
  <li><a <%=URL.equals(AccessController.FINALGRADES_URL) ? "class=\"currentpage\" " : ""%>href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_FINALGRADES%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
    Final Grades
  </a></li><%
}
if (isAdmin || isGroups || isGrades) { %>
  <li><a <%=URL.equals(AccessController.EMAIL_URL) ? "class=\"currentpage\" " : ""%>href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_EMAIL%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
    E-mail
  </a></li><%
}%>
  <li><a <%=URL.equals(AccessController.STAFFPREFS_URL) ? "class=\"currentpage\" " : ""%>href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_STAFFPREFS%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>">
    Notifications
  </a></li>
  <li class="sep"><hr></li>
  <li><a href="http://www.cs.cornell.edu/Projects/CMS/help.html" target="_blank">
    Help
  </a></li>
  <li><a href="news://newsstand.cit.cornell.edu/cornell.dept.cs.cms" target="_blank">
    CMS Newsgroup
  </a></li>
  <li><a href="http://www.cs.cornell.edu/Projects/CMS" target="_blank">
    Credits
  </a></li>
  </ul>
  </td>
  <td id="navbar_menu_container" width="14px"><div id="navbar_menu_top">&nbsp;</div></td>
  <td width="20px"> &nbsp;</td>
 </tr>
 <tr><%-- height="14px" was here (why?), but no such attribute - Alex, Oct 2007 --%>
  <td width="20px">&nbsp;</td>
  <td width="61px">
    <div id="navbar_bottom_left">&nbsp;</div>  
  </td>
  <td width="123px"><%-- 
  Without widths, gaps appear for IE
  This one was 109 (sum used to = 224 which is as expected; 123 is 14px too much)
  Don't know why width changed; any way to make this auto? - Alex, Oct 2007 --%>
    <div id="navbar_bottom">&nbsp;</div>  
  </td>
  <td width="14px">
    <div id="navbar_menu_bottom">&nbsp;</div>
  </td>
  <td width="20px">&nbsp;</td>
 </tr>
 </table>
</td>
