<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
boolean debug= ((Boolean)session.getAttribute(AccessController.A_DEBUG)).booleanValue();
Element principal= XMLUtil.getFirstChildByTagName(root,XMLBuilder.TAG_PRINCIPAL);
String firstName="", lastName="", netID = "";
 Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
 String courseid = (course != null ? course.getAttribute(XMLBuilder.A_COURSEID) : null);
if (principal!=null) {
    firstName= principal.getAttribute(XMLBuilder.A_FIRSTNAME);
    lastName= principal.getAttribute(XMLBuilder.A_LASTNAME);
    netID= principal.getAttribute(XMLBuilder.A_NETID);
}
%>



     <font color="#FFFFFF">
    <% if (debug) { %>
          <b>*** Debug Mode ***</b> 
          <% if (principal == null) { %>
             <b>***You are NOT logged in (Guest View)***</b>
          <% } else if (principal!=null) { %>
             <b> Logged in as:</b>
             <%=firstName%>&nbsp;<%=lastName%>&nbsp;(<%=netID%>)
          <% }
    
    } else {
    
      /* Display authenticated user name */ %>     
      <% String displayuser = null;
      if ( principal == null ) { %>
         <b>***You are NOT logged in - &nbsp;&nbsp;</b><a style="color:white;" href="https://<%=request.getServerName()%>/web/auth/?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_OVER%>">SIGN IN</a> to get full access to your courses
      <% } else { %>
         <b>Logged in as:</b>
         <% displayuser = firstName + " " + lastName + " (" + netID + ") "; %>
         <%= displayuser %><a style="color:white;" href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_SIGNOUT %>">Sign Out</a>
      <% } 
    } %>
    </font>

&nbsp;&nbsp;