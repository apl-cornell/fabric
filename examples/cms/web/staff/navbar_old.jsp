<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*" %>
<% 
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
String URL= request.getServletPath(); 
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root= (Element)displayData.getFirstChild();
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
Element category= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_CATEGORY);
boolean isNewAssign= false; /* whether this is the new-assignment page   */
boolean isEditPage= false;  /* whether this is the edit-assignment page */
boolean showAddDrop= root.hasAttribute(XMLBuilder.A_SHOWADDDROP);
String assignID="";
Element assignment= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT); 
Element surveys = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_SURVEYS);
assignID= assignment == null ? "" : assignment.getAttribute(XMLBuilder.A_ASSIGNID);
if (URL.equals(AccessController.ASSIGNADMIN_URL)) {
    if (assignID.equals("0")) {
      isEditPage= false;
      isNewAssign= true;
    } else {
      isEditPage= true;
      isNewAssign= false;
    }
}

boolean isAdmin= course.hasAttribute(XMLBuilder.A_ISADMIN);
boolean isGroups = course.hasAttribute(XMLBuilder.A_ISGROUPS);
boolean isGrades = course.hasAttribute(XMLBuilder.A_ISGRADES);
boolean isAssign = course.hasAttribute(XMLBuilder.A_ISASSIGN);
boolean isCategory = course.hasAttribute(XMLBuilder.A_ISCATEGORY);
boolean studentsAccess = isAdmin || (isGroups && isGrades);
boolean isPage = false;
NodeList allassigns = (isAdmin || isGroups || isGrades || isAssign) ? XMLUtil.getFirstChildByTagName(course, XMLBuilder.TAG_ASSIGNMENTS).getChildNodes() : null;
NodeList allcats = (isAdmin || isCategory) ? XMLUtil.getFirstChildByTagName(course, XMLBuilder.TAG_CATEGORIES).getChildNodes() : null;
allassigns = (allassigns == null ? new CMSNodeList() : allassigns);
allcats = (allcats == null ? new CMSNodeList() : allcats);
long courseid= Long.parseLong(course.getAttribute(XMLBuilder.A_COURSEID));
%>
<script type="text/javascript">
<!-- 
var assignedits = new Array();
var assignschedules = new Array();
var assigngrades = new Array();
<% if (allassigns.getLength() == 0) { %>
	assignschedules[0] = 'No Assignments';
	assigngrades[0] = 'No Assignments';
<%}
 int assignCount = 0;
 for (int i = 0; i < allassigns.getLength(); i++) { 
    Element xAssign = (Element) allassigns.item(i);%>
    assignedits[0] <%= assignCount==0 ? "" : "+" %>= '<a href="?<%= AccessController.P_ACTION + "="
    %><%if (xAssign.getAttribute(XMLBuilder.A_ASSIGNTYPE).equals(Integer.toString(Assignment.ASSIGNMENT))){
    	  %><%=AccessController.ACT_ASSIGNADMIN%><%}
		  else if (xAssign.getAttribute(XMLBuilder.A_ASSIGNTYPE).equals(Integer.toString(Assignment.SURVEY))){
		  %><%=AccessController.ACT_SURVEYADMIN%><%}
		  else if (xAssign.getAttribute(XMLBuilder.A_ASSIGNTYPE).equals(Integer.toString(Assignment.QUIZ))){
		  %><%=AccessController.ACT_QUIZADMIN
		  %><%}
		  %><%="&amp;" + AccessController.P_ASSIGNID + "=" + xAssign.getAttribute(XMLBuilder.A_ASSIGNID) %>">&#149;&nbsp;<%= xAssign.getAttribute(XMLBuilder.A_NAME).replaceAll("'", "\\\\'") %></a>';
	<%
    assignCount++;
  }%>

<%if (assignCount == 0){%>
	assignedits[0] = 'No Assignments';
<%}%>
<%	int arrInd = 0, schedCount = 0;
	for (int i = 0; i < allassigns.getLength(); i++) { 
    	Element xAssign = (Element) allassigns.item(i); 
		if (xAssign.getAttribute(XMLBuilder.A_USESCHEDULE).equals("true")) { 
			schedCount++; %>
      		assignschedules[0] <%= arrInd==0 ? "" : "+" %>= '<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_SCHEDULE + "&amp;" + AccessController.P_ASSIGNID + "=" + xAssign.getAttribute(XMLBuilder.A_ASSIGNID) %>">&#149;&nbsp;<%= xAssign.getAttribute(XMLBuilder.A_NAME).replaceAll("'", "\\\\'") %></a>';
    		<%  arrInd++; %>
<%  	} 
   	}
   	if (schedCount == 0) {  %>
		assignschedules[0] = 'No assignments with schedules';
<%	} %>
<% boolean isFirstAssign = true; %>
<% for (int i = 0; i < allassigns.getLength(); i++) { 
    	Element xAssign = (Element) allassigns.item(i); 
    	int assignType = Integer.parseInt(xAssign.getAttribute(XMLBuilder.A_ASSIGNTYPE));
    	String groupAction = (assignType == Assignment.SURVEY) ? AccessController.ACT_SURVEYRESULT : AccessController.ACT_GRADEASSIGN; %>
  assigngrades[0] <%= isFirstAssign ? "" : "+" %>= '<a href="?<%= AccessController.P_ACTION + "=" + groupAction + "&amp;" + AccessController.P_ASSIGNID + "=" + xAssign.getAttribute(XMLBuilder.A_ASSIGNID) %>">&#149;&nbsp;<%= xAssign.getAttribute(XMLBuilder.A_NAME).replaceAll("'", "\\\\'") %></a>';
<% 		isFirstAssign = false;
	}%>

<%
if (surveys != null) {
   		NodeList allSurveys = surveys.getElementsByTagName(XMLBuilder.TAG_SURVEY);
   		int length = allSurveys.getLength();
   		for (int i = 0; i < length; i++) {
   			Element survey = (Element) allSurveys.item(i); 
   			String surveyName = survey.getAttribute(XMLBuilder.A_NAME);
   			String id = survey.getAttribute(XMLBuilder.A_ID); %>
	assigngrades[0] <%= isFirstAssign ? "" : "+" %>= '<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_SURVEYRESULT + "&amp;" + AccessController.P_ASSIGNID + "=" + id %>">&#149;&nbsp;<%= surveyName.replaceAll("'", "\\\\'") %></a>';

<%   		isFirstAssign = false;		
   		}
   	}
%>
var newassigns = new Array();
newassigns[0] = '<a href=<%="\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_NEWASSIGN + "&amp;"+ AccessController.P_COURSEID + "=" + courseid + "\""%>>&#149;&nbsp;New Assignment</a>';
newassigns[0] += '<a href=<%="\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_NEWSURVEY + "&amp;"+ AccessController.P_COURSEID + "=" + courseid + "\""%>>&#149;&nbsp;New Survey</a>';
newassigns[0] += '<a href=<%="\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_NEWQUIZ + "&amp;"+ AccessController.P_COURSEID + "=" + courseid + "\""%>>&#149;&nbsp;New Quiz</a>';

var catedits = new Array();
var catcontents = new Array();
<% 	if (allcats.getLength() == 0) { %>
    	catedits[0] = 'No content tables have been created.';
    	catcontents[0] = 'No content tables have been created.';
<%	}
 	for (int i = 0; i < allcats.getLength(); i++) { 
    	Element xCat = (Element) allcats.item(i); %>
  		catedits[0] <%= i==0 ? "" : "+" %>= '<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_EDITCTG + "&amp;" + AccessController.P_CATID + "=" + xCat.getAttribute(XMLBuilder.A_ID) %>">&#149;&nbsp;<%= xCat.getAttribute(XMLBuilder.A_NAME).replaceAll("'", "\\\\'") %></a>';
<% 	} %>

<% 	for (int i = 0; i < allcats.getLength(); i++) { 
    	Element xCat = (Element) allcats.item(i); %>
  		catcontents[0] <%= i==0 ? "" : "+" %>= '<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_ADDNEDITCONTENTS + "&amp;" + AccessController.P_CATID + "=" + xCat.getAttribute(XMLBuilder.A_ID) %>">&#149;&nbsp;<%= xCat.getAttribute(XMLBuilder.A_NAME).replaceAll("'", "\\\\'") %></a>';
<% 	} %>
var gradestudentmsg = new Array();
var gradegroupsmsg = new Array();
gradestudentmsg[0] = 'To set grades for a certain student, navigate to the \'Students\' page and click on a NetID';
gradegroupsmsg[0] = 'To start setting grades for an assignment, first navigate to the \'Groups\' page for that assignment.';
-->
</script>
<td class="staff_navigation" rowspan="3" width="224px">
 <table border="0" cellpadding="0" cellspacing="0" width="224px">
 <tr>
 <td width="20px">&nbsp;</td>
 <td class="staff_navigation" colspan="2" width="170px">
<ul id="navbar_content">
<li>
<span class="course"> <%= course.getAttribute(XMLBuilder.A_DISPLAYEDCODE)%>&nbsp;</span><br>
<span class="semester"> (<%=course.getAttribute(XMLBuilder.A_SEMESTER)%>)</span>
<hr width="100%" size="1px">
</li>
<%
String begintag, endtag, endtag2; // extra endtag for where its needed
String cpspanbegin= "<span class=\"iscurrentnav\">";
String cpspanend= "</span>";
String noaccbegin = "<li class=\"unavailable\">"; 
String noaccend = "</li>"; %>
<%-- Course Home Page --%>

<%
isPage = URL.equals(AccessController.COURSEADMIN_URL);
  begintag= "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_COURSEADMIN + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
  endtag= "</a></li>";
 %>
    <%= begintag %>Home<%= endtag %>

<%
  /* Course Options */
if (isAdmin) {
  isPage = URL.equals(AccessController.COURSEPROPS_URL);
  begintag= "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_COURSEPROPS + "&amp;"+ AccessController.P_COURSEID + "=" + courseid + "\">";
  endtag= "</a></li>";
%>
    <%= begintag%>Setup<%=endtag%>
<%} %>


<%-- Update Category Properties/Add new category --%>

<%
boolean newcategory = category != null && category.getAttribute(XMLBuilder.A_ID).equals("0");
if (isAdmin || isCategory) {
	isPage = URL.equals(AccessController.CATEGORYADMIN_URL) && newcategory;
	 begintag = "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?"+AccessController.P_ACTION + "=" + AccessController.ACT_NEWCATEGORY + "&amp;"+ AccessController.P_COURSEID + "=" + courseid  +"\">";
	 endtag = "</a></li>";
%>
	<%=begintag%>Content<%=endtag%>
<dl>
<%
  isPage = URL.equals(AccessController.CATEGORYADMIN_URL) && !newcategory;
  begintag= "<dl><dt><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"#\" onclick=\"showmenu(event, catedits[0]); return false;\" onMouseover=\"this.style.textDecoration = 'underline';\" onMouseout=\"this.style.textDecoration = 'none'; delayhidemenu();\">";
  endtag= "</a></dt></dl>";
%>
  <%=begintag%>Edit Layout<%=endtag%>
</dl><dl>
<%
  isPage = URL.equals(AccessController.CTGCONTENTSADMIN_URL);
  begintag= "<dl><dt><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"#\" onclick=\"showmenu(event, catcontents[0]); return false;\" onMouseout=\"delayhidemenu();\">";
  endtag= "</a></dt></dl>";
%>
  <%=begintag%>Add/Edit Data<%=endtag%>
</dl>
<%}%>	

<%-- Students  --%>

<%
if (studentsAccess) {
  /* Students */
  isPage = URL.equals(AccessController.STUDENTS_URL) && !showAddDrop;
  begintag= "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_STUDENTS + "&amp;"+ AccessController.P_COURSEID + "=" + courseid + "\">";
  endtag= "</a></li>";
%>
    <%= begintag %>Students<%= endtag %>

<dl>
<%
  isPage = showAddDrop;
  begintag= "<dl><dt><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_ADDSTUDENTPAGE + "&amp;"+ AccessController.P_COURSEID + "=" + courseid + "\">";
  endtag= "</a></dt></dl>";
%>

  <%= begintag %>Add/Drop<%= endtag %>

</dl>

<dl>

<%
  isPage = URL.equals(AccessController.GRADEALLASSIGNS_URL);
  begintag = "<dl><dt><a " + (isPage ? "class=\"currentpage\" " : "") +  " href=\"#\" onclick=\"showmenu(event, gradestudentmsg[0], 350); return false;\" onMouseout=\"delayhidemenu();\">";
  endtag = "</a></dt></dl>";
%>
  <%= begintag %>Grades<%= endtag %>

</dl>
<%}%>

<%-- Assignment and grading pages  --%>


<%
isPage = URL.equals(AccessController.ASSIGNLISTADMIN_URL);
begintag= "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_ASSIGNLISTADMIN + "&amp;"+ AccessController.P_COURSEID + "=" + courseid + "\">";
endtag= "</a></li>";
%>

<%=begintag%>Assignments<%= endtag %>
<dl>
<%
if (isAdmin || isAssign) {
  begintag= "<dl><dt><a " + (isNewAssign ? "class=\"currentpage\" " : "") + "href=\"#\" onclick=\"showmenu(event, newassigns[0]); return false;\" onMouseout=\"delayhidemenu();\">";
  endtag= "</a></dt></dl>";
%>
<%= begintag %>New<%= endtag %>
<%}%>
<%

boolean isGradeClass= URL.equals(AccessController.GRADEASSIGN_URL);
boolean isGradeStudents= URL.equals(AccessController.GRADESTUDENTS_URL);
boolean isScheduleStudents= URL.equals(AccessController.ASSIGNSCHED_URL);

if (isAdmin || isAssign) {
  begintag = "<dl><dt><a " + (isEditPage ? "class=\"currentpage\" " : "") +  " href=\"#\" onclick=\"showmenu(event, assignedits[0]); return false;\" onMouseout=\"delayhidemenu();\">";
  endtag = "</a></dt></dl>";
  /*
	if (isEditPage) {
	    begintag= cpspanbegin + begintag;
	    endtag= endtag + cpspanend;
	} 
	 else if (isGradeClass || isGradeStudents ){
		/* make sure edit is a link *
	    begintag= "<dl><dt><a href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_ASSIGNADMIN+ "&amp;"+ AccessController.P_ASSIGNID + "=" + assignID + "\">";
	    endtag= "</a></dt></dl>";	
	}else {  
	    begintag= "<dl><dt>";
	    endtag= "</dt></dl>";
	} 
	*/
%>
<%= begintag %>Edit<%= endtag %>
<%}%>

<%
if (isAdmin || isGroups || isGrades) {
  begintag = "<dl><dt><a " + (isGradeClass ? "class=\"currentpage\" " : "") + " href=\"#\" onclick=\"showmenu(event, assigngrades[0]); return false;\" onMouseout=\"delayhidemenu();\">";
  endtag = "</a></dt></dl>";
%>
<%= begintag %>Groups/Results<%= endtag %>
<%}%>
<%
if ((schedCount > 0) && (isAdmin || isAssign)) {
	begintag="<dl><dt><a " + (isScheduleStudents ? "class=\"currentpage\" " : "") + "href=\"#\" onclick=\"showmenu(event, assignschedules[0], 350); return false;\" onMouseout=\"delayhidemenu();\">";
	endtag= "</a></dt></dl>";
%>

<%= begintag %>Schedule<%= endtag %>
<% } %>
<%
if (isAdmin || isGrades) {
	begintag= "<dl><dt><a " + (isGradeStudents ? "class=\"currentpage\" " : "") +  " href=\"#\" onclick=\"showmenu(event, gradegroupsmsg[0], 350); return false;\" onMouseout=\"delayhidemenu();\">";
	endtag= "</a></dt></dl>";
%>

<%= begintag %>Grade<%= endtag %>
<%}%>
</dl>
 
<% 
if (isAdmin) { 
  isPage = URL.equals(AccessController.COURSELOG_URL);
  begintag = "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_COURSE_LOGSEARCH + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
  endtag = "</a></li>";
%>
    <%= begintag %>Search Logs<%= endtag %>
<%}%>
<% 
if (isAdmin) { 
  isPage = URL.equals(AccessController.UPLOAD_URL);
  begintag = "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_VIEWUPLOAD + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
  endtag = "</a></li>";
%>
    <%= begintag %>Upload CSV<%= endtag %>
<%}%>
<% 
if (isAdmin) {
    isPage = URL.equals(AccessController.FINALGRADES_URL);
    begintag = "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_FINALGRADES + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
    endtag = "</a></li>";
%>
    <%= begintag %>Final Grades<%= endtag %>
<%}%>
<%
if (isAdmin || isGroups || isGrades) {
  isPage = URL.equals(AccessController.EMAIL_URL);
  begintag= "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_EMAIL + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
  endtag= "</a></li>"; 
%>    
    <%= begintag%>E-mail<%=endtag%>
<%}%>
<%
  isPage = URL.equals(AccessController.STAFFPREFS_URL);
  begintag= "<li><a " + (isPage ? "class=\"currentpage\" " : "") + "href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_STAFFPREFS + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
  endtag= "</a></li>";
%>
  <%= begintag%>Notifications<%= endtag %>
 
<li><hr width="100%" size="1px">
<a href="http://www.cs.cornell.edu/Projects/CMS/help.html" target="_blank">Help
</a>  </li>
<li><a href="https://gforge.cis.cornell.edu/tracker/?atid=492&amp;group_id=44&amp;func=browse" target="_blank">Suggest Features
</a>  </li>
<li><a href="https://gforge.cis.cornell.edu/tracker/?atid=493&amp;group_id=44&amp;func=browse" target="_blank">Report Bugs
</a></li>    
<li><a href="http://www.cs.cornell.edu/Projects/CMS" target="_blank">Credits
</a></li>  
  </ul>
  </td>
  <td  id="navbar_menu_container" width="14px"><div id="navbar_menu_top">&nbsp;</div></td>
   <td width="20px"> &nbsp;</td>
  
  </tr>
  <tr height="14px">
   <td width="20px">&nbsp;</td>
  <td width="61px">
     <div id="navbar_bottom_left">&nbsp;</div>  
    </td>
   <td width="109px">     <div id="navbar_bottom">&nbsp;</div>  
    </td>
  <td width="14px"><div id="navbar_menu_bottom">&nbsp;</div>
  </td>
     <td width="20px"> &nbsp;</td>
  </tr>
  </table>
</td>
