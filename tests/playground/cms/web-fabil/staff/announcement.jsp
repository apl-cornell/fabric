<%@ page language="java" import="org.w3c.dom.*,cms.auth.*, cms.model.*, cms.www.*, cms.www.xml.*" %><%
 Document xml= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root= (Element) xml.getFirstChild();
   Element course= (Element) root.getElementsByTagName(XMLBuilder.TAG_COURSE).item(0);
   Element announcement= (Element) root.getElementsByTagName(XMLBuilder.TAG_ANNOUNCEMENT).item(0); %>
<jsp:include page="../header.jsp"/>
<jsp:include page="../header-page.jsp"/> 
		<div id="course_wrapper">
      <table summary="course wrapper" cellpadding="0" cellspacing="0" border="0">

            <tr> 
              <td id="course_page_container" width="75%" valign="top">
				<jsp:include page="../problem-report.jsp"/> 
                <form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_SETANNOUNCE %>" method="post">
                  <table id="course_page" width="100%" cellpadding="20px" cellspacing="0" border="0">
                    <tr>
                      <td class="assignment_left">
                        <h2>Edit Announcement</h2>
                        <textarea cols="60" rows="8" name="<%= AccessController.P_ANNOUNCE %>" onchange="setEdit();"><%= 
                          announcement.getAttribute(XMLBuilder.A_TEXT) %></textarea>
                        <input type="hidden" name="<%= AccessController.P_ID %>" value="<%= announcement.getAttribute(XMLBuilder.A_ID) %>">
                        <input type="hidden" id="<%=AccessController.P_EDIT%>" name="<%=AccessController.P_EDIT %>" value="<%=AccessController.P_FALSE%>">
                        <input type="submit" value="Submit">
                      </td>
					</tr><%
	NodeList history = CategoryXMLUtil.getListByTagName(root, XMLBuilder.TAG_ANNOUNCEMENTHISTORY);
	int length = history.getLength();
	if (length > 0){%>
					<tr>
					  <td class="assignment_left">
					 	<h2>
          			       Previous Version
	          			  <span id="announcementshead">
	            		    <a class="hide" href="#" onClick="hide('announcements', '(hide)', '(show)'); return false;">(hide)</a>
          				  </span>
        			   	</h2>
        				<div id="announcements" class="showhide">
        				  <ul type="circle"><%
		for (int i= 0; i<length; i++){
			Element item = (Element)history.item(i);%>
							<li><%=item.getAttribute(XMLBuilder.A_TEXT)%></li><%
		}%>
						  </ul> 
        				</div>
					  </td>
					</tr><%
	}%>                      
                    <tr>
                      <td class="assignment_left">
                        <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_COURSEADMIN %>&amp;<%= AccessController.P_COURSEID %>=<%= course.getAttribute(XMLBuilder.A_COURSEID) %>">
                          <p>&lt;&lt; Back
                        </a>
                      </td>
                    </tr>
                  </table>
                </form>
              </td>
            </tr>
<jsp:include page="../footer.jsp"/>