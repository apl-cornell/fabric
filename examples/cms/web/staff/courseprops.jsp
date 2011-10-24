<%@page language="java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*"%><%
Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
User p = (User)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
String courseid = course.getAttribute(XMLBuilder.A_COURSEID); 
boolean cmsAdminOnly = p.isCMSAdmin() && !course.hasAttribute(XMLBuilder.A_ISADMIN); %>
<jsp:include page="../header.jsp" />
<script type="text/javascript">
/*
permissions checking: which of {staff, students, Cornell community, guests} can see what?
*/

  function checkCCs(type) {
    var ths = getElementById('guest' + type);
    if (ths.checked) {
      var coursecc = getElementById('cccoursepage');
      var courseguest = getElementById('guestcoursepage');
      var thscc = getElementById('cc' + type);
      coursecc.checked = true;
      courseguest.checked = true;
      thscc.checked = true;
      if (type == 'solution') {
        var assigncc = getElementById('ccassigns');
        var assignguest = getElementById('guestassigns');
        assigncc.checked = true;
        assignguest.checked = true;
      }
    }
  }
  
  function checkNone() {
      var coursecc = getElementById('cccoursepage');
      var assigncc = getElementById('ccassigns');
      var announcecc = getElementById('ccannounce');
      var solutioncc = getElementById('ccsolution');
      coursecc.checked = false;
      assigncc.checked = false;
      announcecc.checked = false;
      solutioncc.checked = false;
      checkNoneGuest();
  }
  
  function checkNoneGuest() {
      var courseguest = getElementById('guestcoursepage');
      var assignguest = getElementById('guestassigns');
      var announceguest = getElementById('guestannounce');
      var solutionguest = getElementById('guestsolution');
      courseguest.checked = false;
      assignguest.checked = false;
      announceguest.checked = false;
      solutionguest.checked = false;
  }
  
</script>
<jsp:include page="courseprops-staffperms.js.jsp" />
<jsp:include page="../header-page.jsp"/>
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
<%	if (!cmsAdminOnly)
	{%>
		<jsp:include page="navbar.jsp"/>
<%	} %>
    <td valign="top"  id="course_page_container">
      <form action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_SETCOURSEPROPS + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>" method="post">
        <div id="course_page">
			<jsp:include page="../problem-report.jsp"/>
<%	if (cmsAdminOnly)
	{%>
			<jsp:include page="../course-title.jsp" />
<%	}  %>
          <div class="assignment_left">
            <h2>General Properties</h2>
            <table class="assignment_table" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td>Course Name</td>
                <td>
                  <input name="<%= AccessController.P_NAME %>" type="text" size="80" value="<%= course.getAttribute(XMLBuilder.A_COURSENAME) %>">
                </td>
              </tr>
              <tr>
<% /*
deal with both CMS admins and normal course admins viewing this page,
since a CMS admin with course admin permission will see this page rather than the cmsadmincourseprops page
*/
if (p.isCMSAdmin()) //give permission to change the actual code
{ %>
					<%-- <input type="hidden" name="<%= AccessController.P_DISPLAYEDCODE %>" value="<%= course.getAttribute(XMLBuilder.A_DISPLAYEDCODE) %>"> --%>
					<td>Course Code</td>
               <td>
                  <input name="<%= AccessController.P_CODE %>" type="text" size="20" maxlength="15" value="<%= course.getAttribute(XMLBuilder.A_CODE) %>">
               </td>
          </tr>
          <tr>
<% } else { //normal course admin; give permission to change the *displayed* code only
%>
					 <input type="hidden" name="<%= AccessController.P_CODE %>" value="<%= course.getAttribute(XMLBuilder.A_CODE) %>">
<% } %>
                <td>Displayed Course Code</td>
                <td>
                  <input name="<%= AccessController.P_DISPLAYEDCODE %>" type="text" size="20" maxlength="15" value="<%= course.getAttribute(XMLBuilder.A_DISPLAYEDCODE) %>">
                </td>
              </tr>
              <tr>
                <td>Cornell Community Access</td>
                <td>
                  <input id="cccoursepage" onclick="if (!this.checked) { checkNone(); }" name="<%= AccessController.P_COURSECCACCESS %>" type="checkbox" <%= course.hasAttribute(XMLBuilder.A_COURSECCACCESS) ? "checked" : "" %>>
                  Allow Kerberos authenticated users (anyone with a NetID) to view course page
                  <ul type="circle">
                    <li>
                      <input id="ccannounce" onclick="if (this.checked) { var ccp = getElementById('cccoursepage'); ccp.checked = true; }" name="<%= AccessController.P_ANNOUNCECCACCESS %>" type="checkbox" <%= course.hasAttribute(XMLBuilder.A_ANNOUNCECCACCESS) ? "checked" : "" %>>
                      Allow authenticated users to view course announcements
                    </li>
                    <li>
                      <input id="ccassigns" onclick="if (this.checked) { var ccp = getElementById('cccoursepage'); ccp.checked = true; } else { var ccs = getElementById('ccsolution'); ccs.checked = false; }" name="<%= AccessController.P_ASSIGNCCACCESS %>" type="checkbox" <%= course.hasAttribute(XMLBuilder.A_ASSIGNCCACCESS) ? "checked" : "" %>>
                      Allow authenticated users to view course assignments and download assignment files
                    </li>
                    <li>
                      <input id="ccsolution" onclick="if (this.checked) { var ccp = getElementById('cccoursepage'); var cca = getElementById('ccassigns'); ccp.checked = true; cca.checked = true; }" name="<%= AccessController.P_SOLUTIONCCACCESS %>" type="checkbox" <%= course.hasAttribute(XMLBuilder.A_SOLUTIONCCACCESS) ? "checked" : "" %>>
                      Allow authenticated users to download assignment solutions once the assignment is 'Graded' status
                    </li>
                  </ul>
                </td>
              </tr>
              <tr>
              	<td>Guest Access</td>
                <td>
                	<input id="guestcoursepage" onclick="if (!this.checked) { checkNoneGuest(); } else { var ccp = getElementById('cccoursepage'); ccp.checked = true; }" name="<%= AccessController.P_COURSEGUESTACCESS %>" type="checkbox" <%= course.hasAttribute(XMLBuilder.A_COURSEGUESTACCESS) ? "checked" : "" %>>
                	Allow non-authenticated guests (just plain anyone) to view course page
                	<ul type="circle">
                    <li>
                      <input id="guestannounce" onclick="checkCCs('announce');" name="<%= AccessController.P_ANNOUNCEGUESTACCESS %>" type="checkbox" <%= course.hasAttribute(XMLBuilder.A_ANNOUNCEGUESTACCESS) ? "checked" : "" %>>
                      Allow guests to view course announcements
                    </li>
                		<li>
                			<input id="guestassigns" onclick="checkCCs('assigns');" name="<%= AccessController.P_ASSIGNGUESTACCESS %>" type="checkbox" <%= course.hasAttribute(XMLBuilder.A_ASSIGNGUESTACCESS) ? "checked" : "" %>>
                			Allow guests to view course assignments
                		</li>
                		<li>
                      <input id="guestsolution" onclick="checkCCs('solution');" name="<%= AccessController.P_SOLUTIONGUESTACCESS %>" type="checkbox" <%= course.hasAttribute(XMLBuilder.A_SOLUTIONGUESTACCESS) ? "checked" : "" %>>
                      Allow guests to download assignment solutions once the assignment is 'Graded' status
                    </li>
                	</ul>
                </td>
              </tr>
            </table>
          </div>
          <div class="assignment_left">
            <h2>Course Staff and Privileges</h2>
            <table id="permtable" class="assignment_table" cellpadding="0" cellspacing="0" border="0">
              <tr>
                <th style="width: 10%">Remove</th>
                <th>&nbsp;</th>
                <th>
                            <DIV onMouseover="ddrivetip('Full access to everything the system allows; <br>equivalent to Prof. level access')"; onMouseout="hideddrivetip()" >
                                 <u>Admin</u>
                            </div>
                </th>
                <th>
                            <DIV onMouseover="ddrivetip('Have access to <i>Manage Groups</i> page; <br>can create and disband groups')"; onMouseout="hideddrivetip()" >
                                 <u>Groups</u>
                            </div>
                </th>
                <th>
                            <DIV onMouseover="ddrivetip('Have access to <i>Manage Groups</i> page;<br>can access files; can enter and change grades for all groups<br>(unless course staff are specifically assigned to certain groups and subproblems)')"; onMouseout="hideddrivetip()" >
                                 <u>Grades</u>
                            </div>
                </th>
                <th>
                            <DIV onMouseover="ddrivetip('Have access to <i>Assignments</i>page;<br>can create and edit assignments')"; onMouseout="hideddrivetip()" >
                                 <u>Assignments</u>
                            </div>
                </th>
                <th>        <DIV onMouseover="ddrivetip('Have access to <i>Content</i> page;<br>can create and edit content tables')"; onMouseout="hideddrivetip()" >
                                 <u>Content</u>
                            </div>
                 </th>
              </tr><%
Element l= (Element) course.getElementsByTagName(XMLBuilder.TAG_STAFF).item(0); 
NodeList list= l.getChildNodes();
int length= list.getLength();
for (int i= 0; i != length; i++) {
  Element staff= (Element)list.item(i); 
  String netid= staff.getAttribute(XMLBuilder.A_NETID); %>
              <tr>
                <td align="center">
                  <input type="checkbox" name="<%= netid + AccessController.P_REMOVE %>">
                </td>
                <td>
                  <span class="name"><%= staff.getAttribute(XMLBuilder.A_NAME) %></span>
                  <span class="netid">(<%= netid %>)</span>
                </td>
                <td align="center">
                  <input onclick="if (this.checked) adminClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISADMIN %>" id="<%= netid + AccessController.P_ISADMIN %>" <%= staff.hasAttribute(XMLBuilder.A_ISADMIN) ? "checked" : "" %>>
                </td>
                <td align="center">
                  <input onclick="if (!this.checked) otherClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISGROUPS %>" id="<%= netid + AccessController.P_ISGROUPS %>" <%= staff.hasAttribute(XMLBuilder.A_ISGROUPS) ? "checked" : "" %>>
                </td>
                <td align="center">
                  <input onclick="if (!this.checked) otherClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISGRADES %>" id="<%= netid + AccessController.P_ISGRADES %>" <%= staff.hasAttribute(XMLBuilder.A_ISGRADES) ? "checked" : "" %>>
                </td>
                <td align="center">
                  <input onclick="if (!this.checked) otherClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISASSIGN %>" id="<%= netid + AccessController.P_ISASSIGN %>" <%= staff.hasAttribute(XMLBuilder.A_ISASSIGN) ? "checked" : "" %>>
                </td>
                <td align="center">
                  <input onclick="if (!this.checked) otherClick('<%= netid %>', false);" type="checkbox" name="<%= netid + AccessController.P_ISCATEGORY %>" id="<%= netid + AccessController.P_ISCATEGORY %>" <%= staff.hasAttribute(XMLBuilder.A_ISCATEGORY) ? "checked" : "" %>>
                </td>
              </tr><% 
} %>
            </table>
            <a href="#" onclick="addPermRow(); return false;">(New Row)</a>
          </div>
          <%--
          <div class="assignment_left">
				<h2>Grading Options</h2>
				<table class="assignment_table" cellpadding="0" cellspacing="0" border="0">
					<tr>
                <td>
                	<input name="<%= AccessController.P_SHOWGRADERID %>" type="checkbox" <%= Boolean.valueOf(course.getAttribute(XMLBuilder.A_SHOWGRADERID)).booleanValue() ? "\"checked\"" : "" %>>
                	Show Grader's NetID
                </td>
                <td>Allows students to see who graded each assignment</td>
              </tr>
            </table>
			</div> --%>
          <div class="assignment_left">
		  			<h2>Other Options</h2>
			  		<table class="assignment_table" cellpadding="0" cellspacing="0" border="0">
			  <tr>
			  	<td>
                	<input name="<%= AccessController.P_HASSECTION %>" type="checkbox" <%= Boolean.valueOf(course.getAttribute(XMLBuilder.A_HASSECTION)).booleanValue() ? "\"checked\"" : "" %>>
                	Enable sections
                </td>
			  	<td>Allow this course to support sections</td>
			  </tr>
              <tr>
                <td>
                	<input name="<%= AccessController.P_FREEZECOURSE %>" type="checkbox" <%= Boolean.valueOf(course.getAttribute(XMLBuilder.A_COURSEFROZEN)).booleanValue() ? "\"checked\"" : "" %>>
                	Freeze course
                </td>
                <td>Stop any attempted changes to this course in the database</td>
              </tr>
              <tr>
                <td>
                	<input name="<%= AccessController.P_FINALGRADES %>" type="checkbox" <%= Boolean.valueOf(course.getAttribute(XMLBuilder.A_SHOWFINALGRADES)).booleanValue() ? "\"checked\"" : "" %>>
                  Release Final Grades to students
                </td>
                <td>Allow students to see their final grades online</td>
              </tr>
              <tr>
                <td>
                	<input name="<%= AccessController.P_SHOWTOTALSCORES %>" type="checkbox" <%= Boolean.valueOf(course.getAttribute(XMLBuilder.A_SHOWTOTALSCORES)).booleanValue() ? "\"checked\"" : "" %>>
                  Show Total Scores to students
                </td>
                <td>Allow students to see their cumulative total score from all assignments</td>
              </tr>
              <tr>
                <td>
                	<input name="<%= AccessController.P_SHOWASSIGNWEIGHTS %>" type="checkbox" <%= Boolean.valueOf(course.getAttribute(XMLBuilder.A_SHOWASSIGNWEIGHTS)).booleanValue() ? "\"checked\"" : "" %>>
                  Show Assignment Weights to students
                </td>
                <td>Allow students to see how each assignment is weighted toward the total score</td>
              </tr>
              
            </table>
		  		</div>
          <div class="assignment_left">
            <input type="submit" value="Set">
          </div>
        </div>
      </form>
    </td>
      <td id="course_menu_container" width="14px"><div id="course_menu_top">&nbsp;</div></td>
  </tr>
<jsp:include page="../footer.jsp"/>
