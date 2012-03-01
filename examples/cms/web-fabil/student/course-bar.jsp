<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %>
<% Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root = (Element) displayData.getChildNodes().item(0); %>
<td id="course_menu_container" valign="top" style="width: 25%">
  <div id="course_menu_top">
    <img src="images/dot.gif" width="200px" height="1px" alt="">
  </div>
  <div id="course_menu">
<% Element course = (Element) root.getElementsByTagName(XMLBuilder.TAG_COURSE).item(0);
   if (null == course) {
     throw new Exception("No course data in data tree");
   }
   if (course.hasAttribute("finalgrade")) { %>
    <div class="sidebar_item">
      <h2>Final course grade: <%= course.getAttribute("finalgrade") %></h2>
    </div>
<% } %>
    <div class="sidebar_item">
      <h2><%= course.getAttribute(XMLBuilder.A_CODE) %><br><%= course.getAttribute(XMLBuilder.A_NAME) %></h2>
<% if (course.hasAttribute(XMLBuilder.A_URL)) { %>
      <a href="<%= course.getAttribute(XMLBuilder.A_URL)%>">Course homepage</a><br>
<% } %>
      <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_COURSE %>&amp;<%= AccessController.P_COURSEID %>=<%= course.getAttribute(XMLBuilder.A_ID) %>">Course CMS page</a><br>
    </div>
    <div class="sidebar_item">
      <h2>Assignments <a href="">(view all)</a></h2>
<% Node l = root.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENTS).item(0);
   NodeList assignments = null;
   if (null == l) { %>
      <p>Error: no assignments data.
<% } else if ((assignments = l.getChildNodes()).getLength() == 0) { %>
      <p>No assignments have yet been posted.
<% } else {
     for (int i = 0; i < assignments.getLength(); i++) {
       Element assignment = (Element) assignments.item(i); %>
      <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
          <td valign="top" nowrap>
            <a href="?action=assignment&amp;id=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>">
              <%= assignment.getAttribute(XMLBuilder.A_NAME) %>
            </a>
          </td>
          <td align="right" class="assignment_blurb">
              <%= assignment.getAttribute(XMLBuilder.A_STATUS) %>
          </td>
        </tr>
      </table>
  <% }
   } %>
    </div>
    <div class="sidebar_item">
      <h2>Recent Announcements</h2>
<% l = root.getElementsByTagName(XMLBuilder.TAG_COURSEANNOUNCEMENTS).item(0);
   NodeList myAnnouncements = null;
   if (null == l) { %>
        Error: No announcements in data
<% } else {
    myAnnouncements = l.getChildNodes();
    int length= myAnnouncements.getLength();
    if (length == 0) { %>
        <p>No recent announcements
<%  } else {
      for (int i = 1; i != length; i++) {
        Element announcement = (Element) myAnnouncements.item(i); %>
        <p>
        <%= announcement.getAttribute(XMLBuilder.A_POSTER) %>
        (<%= announcement.getAttribute(XMLBuilder.A_DATE) %>)
        <%= announcement.getAttribute(XMLBuilder.A_TEXT) %><br><br>
   <% }
    } 
  } %>
    </div>
    <div class="sidebar_item">
<!-- XXX this must support more activity
<tr>
  <td nowrap valign="top">Jan 9</td>
  <td><span class="personname">Dan Perry</span> invited you to join his/her group for Assignment 3</td>
</tr>
<tr>
  <td nowrap valign="top">Jan 9</td>
  <td>You left your group for Assignment 3</td>
</tr>
<tr>
  <td nowrap valign="top">Jan 8</td>
  <td>Your group's regrade request for Assignment 1 has been completed</td>
</tr>
<tr>
  <td nowrap valign="top">Jan 7</td>
  <td><span class="personname">Dan Perry</span> submitted a regrade request for Assignment 1</td>
</tr>
-->
    </div>
  </div>
</td>
