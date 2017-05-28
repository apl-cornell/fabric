<%@ page language="java" import="org.w3c.dom.*,cms.auth.*, cms.model.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
String URL= request.getServletPath();
User p= (User) session.getAttribute(AccessController.A_PRINCIPAL);
String netid= p.getNetID();
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
String courseid= course.getAttribute(XMLBuilder.A_COURSEID); 
Element announcements =  XMLUtil.getFirstChildByTagName(course, XMLBuilder.TAG_COURSEANNOUNCEMENTS);
if (announcements != null) { 
boolean inStudentView =  URL.equals(AccessController.COURSE_URL);
String divID = inStudentView ? "id=\"topblock\"" : "";
int numTotalAnnounce = Integer.parseInt(announcements.getAttribute(XMLBuilder.A_NUMTOTALANNOUNCE));
int numRecentAnnounce = Integer.parseInt(announcements.getAttribute(XMLBuilder.A_NUMRECENTANNOUNCE));
boolean isadmin = false, iscategory = false;
if (!p.isInStaffAsBlankMode()) {
  isadmin= course.hasAttribute(XMLBuilder.A_ISADMIN) && !inStudentView;
  iscategory = course.hasAttribute(XMLBuilder.A_ISCATEGORY);
}
boolean display = numRecentAnnounce < numTotalAnnounce;
String style = display ? "": "style = \"display:none\"";
numRecentAnnounce--;%>
<div class="assignment_left">
  	<h2>
		Announcements
        <span id="announcementshead">
      		<a id="announcementsshowhide" href="#" onClick="hide('announcements', ' (hide)', ' (show)'); return false;" class = "hide"> (hide)</a>
       	</span>
   	</h2>
   	<div id="announcements" class="showhide"><%
NodeList list= announcements.getChildNodes();
int length= list.getLength();
if (length == 0) { %>
		<p>No announcements have yet been posted.<br><%
} else { %>
		<table width="100%" cellpadding="2" cellspacing="0" border="0"><%
  for (int i= 0; i != list.getLength(); i++) { 
    Element item= (Element) list.item(i); 
    String poster= item.getAttribute(XMLBuilder.A_POSTER); 
    String txt = item.getAttribute(XMLBuilder.A_TEXT);
    String editInfo = item.getAttribute(XMLBuilder.A_OLDTEXT);
    String announceid = item.getAttribute(XMLBuilder.A_ID);
    NodeList history = item.getElementsByTagName(XMLBuilder.TAG_ANNOUNCEMENTHISTORY).item(0).getChildNodes(); %>
            <tr>
              <td>
                <p style="margin-left: 3em; font-size: smaller"><i><%= item.getAttribute(XMLBuilder.A_DATE) %></i><%
    if (isadmin || iscategory) { %>
                <span id="editannounce_<%= announceid %>head"><a class="hide" href="#" onclick="show('editannounce_<%= announceid %>','(edit)','(edit)'); return false;">(edit)</a>:</span></p><%
    } %>
				<p><%= txt %></p>
				<span style="font-size: smaller; font-style: italic"><p style="margin-left: 9em">-<%= poster %></p>
					<%= editInfo.equals("") ? "" : "<p style=\"margin-left: 10em; font-size: smaller\">("+editInfo+")</p>" %></span>
 <% if (isadmin || iscategory) { %>
				<div id="editannounce_<%= announceid %>" style="display: none; border: 1px solid #ddd">
					<form action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_SETANNOUNCE + "&amp;" + AccessController.P_ID + "=" + announceid %>" method="post"> 
					<input type="checkbox" name="<%= AccessController.P_REMOVEANNOUNCE %>">Remove this announcement<br>
					<textarea name="<%= AccessController.P_ANNOUNCE %>" rows="5" cols="60"><%= txt %></textarea>
					<p><input type="submit" value="Update"></p></form><br>
					<% if (history.getLength() > 0) { %>
             Previous Versions:
             <ul><% 
                for (int j= 0; j<history.getLength(); j++){
                    Element old = (Element)history.item(j); %>
                <li><%=old.getAttribute(XMLBuilder.A_TEXT)%></li><%
                } %>
             </ul> 
          <% } %></p>
				</div>
 <% } %>			
				</p>
              </td>
            </tr><%}%>
		</table><%
} 
NodeList hiddens = root.getElementsByTagName(XMLBuilder.TAG_HIDDENANNOUNCE);
if (hiddens.getLength() > 0 && (isadmin || iscategory)) { %>
    <p>Restore removed Announcements <span id="hiddenannouncedivhead"><a class="hide" href="#" onclick="show('hiddenannouncediv','(show)','(hide)'); return false;">(show)</a></span><br>
    <div id="hiddenannouncediv" style="display: none">
    <table width="100%" cellpadding="2" cellspacing="0" border="0">
      <% for (int i=0; i < hiddens.getLength(); i++) { 
          Element xHiddenAnnounce = (Element) hiddens.item(i); %>
          <tr><td>Posted by <%= xHiddenAnnounce.getAttribute(XMLBuilder.A_POSTER) %> on <%= xHiddenAnnounce.getAttribute(XMLBuilder.A_DATE) %>&nbsp;
          <a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_RESTOREANNOUNCE + "&amp;" + AccessController.P_ID + "=" + xHiddenAnnounce.getAttribute(XMLBuilder.A_ID) %>">Restore</a></td>
          </tr><tr><td><p style="margin-left: 20px">
          <%= xHiddenAnnounce.getAttribute(XMLBuilder.A_TEXT) %></p></td></tr>
      <% } %>
    </table></div>
<%
}
if (isadmin || iscategory) { %>
		<p>Post a new Announcement <span id="newannouncedivhead"><a class="hide" href="#" onclick="show('newannouncediv','(show)','(hide)'); return false;">(show)</a></span><br>
		<div id="newannouncediv" style="display: none">
			<form action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_NEWANNOUNCE + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>" method="post">
				<textarea name="<%= AccessController.P_ANNOUNCE %>" rows="5" cols="60"></textarea><br>
	            <input type="submit" value="Submit">
			</form>
	  </div>
<%
} %>

	</div>
</div>
<% } %>
