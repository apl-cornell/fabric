<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*" %>
<% 
/****************************************************************
* CMS overview page: high-level view of everything useful
****************************************************************/
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element  root        = (Element)  displayData.getChildNodes().item(0);
Node     l           = root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_ALLDUEASSIGNMENTS()).item(0); 
boolean  isGuest     = ((User) session.getAttribute(AccessController.A_PRINCIPAL)).isGuest();
NodeList semesters   = root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_SEMESTER());
%>
<jsp:include page="header.jsp" />
<jsp:include page="header-page.jsp" />

<div id="course_wrapper">
 <table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
   <jsp:include page="navbar.jsp"/>
   <td valign="top" id="course_page_container">
    <div id="course_page">
     <span class="course_title">CMS Overview</span>
     <br/> <%

     NodeList curNotices = ((Element) root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_CURSITENOTICES()).item(0)).getChildNodes();
     if (curNotices.getLength() != 0) { %>
      <div class="noticebox">
       <h2>CMS Notices</h2> <%
       for (int i = 0; i < curNotices.getLength(); i++) {
        Element entry = (Element) curNotices.item(i); %>
        <span style="margin-left: 3em; font-size: smaller; float:right; font-style: italic;">
         <%= entry.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_POSTER()) %>,
         <%= entry.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DATE())   %>
        </span>
        <p>
         <%= entry.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_TEXT()) %>
         <%= (entry.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_HIDDEN()).equals("true"))  ? " <span style=\"font-size: smaller; color:red\">(preview)</span>" : "" %>
         <%= (entry.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_EXPIRED()).equals("true")) ? " <span style=\"font-size: smaller; color:red\">(expired)</span>" : "" %>
        </p> <%
       }%>
      </div><%
     }%>
     <br/><%
     NodeList studentCourses = root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_STUDENTCOURSES()).item(0).getChildNodes();
     NodeList staffCourses   = root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_STAFFCOURSES()).item(0).getChildNodes();
     %> <%= studentCourses.getLength() %> <%= staffCourses.getLength() %> <%
     if (studentCourses.getLength()>0 || staffCourses.getLength()>0) { // ***%>
      <div class="assignment_left">
       <h2>My Courses</h2>
       <table class="assignment_table" cellpadding="0" cellspacing="0" border="0" style="width: 100%">
        <tr><th>Course Code</th><th>Course Name</th></tr>
        <%
        for (int i=0; i < studentCourses.getLength(); i++) { 
         Element xCourse = (Element) studentCourses.item(i); %>
         <tr>
          <td><a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_COURSE + "&amp;" + AccessController.P_COURSEID + "=" + xCourse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID()) %>"><%= xCourse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CODE()) %></a></td>
          <td><%= xCourse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSENAME()) %></td>
         </tr> <%
        } 
        for (int i=0; i < staffCourses.getLength(); i++) { 
         Element xCourse = (Element) staffCourses.item(i); %>
         <tr>
          <td><a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_COURSEADMIN + "&amp;" + AccessController.P_COURSEID + "=" + xCourse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID()) %>"> <%= xCourse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CODE()) %></a> (Staff)</td>
          <td><%= xCourse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSENAME()) %></td>
         </tr> <%
        } %>
       </table>
      </div> <%

      NodeList myAssignments = l.getChildNodes();
      if (myAssignments.getLength()>0) { %>            
       <div class="assignment_left">
        <h2>Current Assignments</h2>
        <table class="assignment_table" cellpadding="0" cellspacing="0" border="0" style="width: 100%">
         <tr>
          <th style="text-align: left">Course</th>
          <th style="text-align: left">Assignment</th>
          <th style="text-align: center">Time Remaining</th>
          <th style="text-align: right">Status</th>
         </tr><%
         for (int i = 0; i < myAssignments.getLength(); i++) {
          Element assignment = (Element) myAssignments.item(i); %>
          <tr>
           <td style="text-align: left;   white-space: nowrap"> <a href="?<%= AccessController.P_ACTION %>=<%=AccessController.ACT_COURSE %>&amp;<%= AccessController.P_COURSEID %>=<%= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID()) %>"> <%= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSENAME()) %> </a> </td>
           <td style="text-align: left;   white-space: nowrap"> <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_ASSIGN %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID()) %>"> <%= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %> </a> </td>
           <td style="text-align: center; white-space: nowrap"> <%= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DUEDATE()) %> </td>
           <td style="text-align: right;  white-space: nowrap"> <%= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATUS()) %> </td>
          </tr><%
         } %>
        </table>
       </div> <%
      }

      l = root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_ALLANNOUNCEMENTS()).item(0);
      NodeList myAnnouncements = l.getChildNodes();
      if (myAnnouncements.getLength() > 0) { %>
       <div class="assignment_left">
        <h2>Recent Announcements</h2> <%

        for (int i = 0; i < myAnnouncements.getLength(); i++) {
         Element course = (Element) myAnnouncements.item(i); %>
         <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_COURSE %>&amp;<%= AccessController.P_COURSEID %>=<%= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID()) %>"> <%= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSENAME()) %> </a><%
         NodeList announcements = course.getChildNodes();
         for (int j = 0; j < announcements.getLength(); j++) {
          Element announcement = (Element) announcements.item(j); %>
          <p style="margin-left: 5em; font-size: smaller; font-style: italic;"><%= announcement.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DATE()) %> by <%= announcement.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_POSTER()) %></p>
          <p style="margin-left: 3em"><%= announcement.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_TEXT()) %></p><%
         }
        } %>
       </div> <%
      }
     } //close ***


     NodeList guestCourses = root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_GUESTCOURSE());
     if (isGuest || guestCourses.getLength() > 0) { %>             
      <div class="assignment_left">
       <h2>Available Courses <%
        if (!isGuest) { %>
         <span id="guestcourseshead"> <a href="#" class="hide" onclick="show('guestcourses',' (show)',' (hide)'); return false;"> (show)</a> </span> <%
        } %>
       </h2> <%

       if (!isGuest) { %>
        <div id="guestcourses" style="display: none"> <% // --------V
       } %>

       <table class="assignment_table" cellpadding="0" cellspacing="0" border="0" style="width: 100%">
        <tr><th>Course Code</th><th>Course Name</th></tr> <%
        for (int i=0; i < guestCourses.getLength(); i++) { 
         Element xCourse = (Element) guestCourses.item(i); %>
         <tr>
          <td><a href="?<%= AccessController.P_ACTION   + "=" + AccessController.ACT_COURSE + "&" + AccessController.P_COURSEID + "=" + xCourse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID()) %>"> <%= xCourse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CODE()) %></a></td>
          <td><%= xCourse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSENAME()) %></td>
         </tr> <%
        } %>
       </table> <%

       if (!isGuest) { %>
        </div> <% // -----------------------------------------------^
       } %>

      </div> <%
     }

     if (semesters.getLength() > 0) { %>
      <div class="assignment_left">
       <h2>Other Semesters
        <span id="semestershowhead"> <a href="#" class="hide" onclick="hide('semestershow',' (hide)',' (show)'); return false;">(hide)</a> </span>
       </h2>

       <div id="semestershow">
        <table class="assignment_table" cellpadding="0" cellspacing="0" border="0" style="width: 100%"> <%
         for (int i=0; i < semesters.getLength(); i++) { 
          Element xSemester = (Element) semesters.item(i); %>
          <tr>
           <td><a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_OVER + "&amp;" + AccessController.P_SEMESTERID + "=" + xSemester.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ID()) %>"> <%= xSemester.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %></a></td>
          </tr> <%
         } %>
        </table>
       </div>
      </div> <%
     } %>

    </div>
   </td>

   <td id="course_menu_container">
     <div id="course_menu_top">&nbsp;</div>
   </td>
  </tr>
  <jsp:include  page="footer.jsp" />

