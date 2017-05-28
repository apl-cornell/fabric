<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
boolean debug= ((Boolean)session.getAttribute(AccessController.A_DEBUG)).booleanValue();
Element principal= XMLUtil.getFirstChildByTagName(root,XMLBuilder.TAG_PRINCIPAL);
String firstName="error", lastName="error", netID="error";
if (principal!=null) {
    firstName= principal.getAttribute(XMLBuilder.A_FIRSTNAME);
    lastName= principal.getAttribute(XMLBuilder.A_LASTNAME);
    netID= principal.getAttribute(XMLBuilder.A_NETID);
    }
%>

<table border="0" width="100%"  cellpadding="8" cellspacing="0">
  <tr>
    <td width="63px" valign="middle" align="center">
      <a href="http://www.cornell.edu"><img src="images/culogo.gif" border="0"></img></a>
    </td>
  
    <td> 
	   <span id="cms_title">
            <a href="/">
                <b>&nbsp;C</b>ourse <b>M</b>anagement <b>S</b>ystem
            </a> 
       </span>
    </td>

    <td valign="bottom" align="right" id="navbar_authuser">
    <font color="#666">
    <% if (debug) {
          if (principal!=null) { %>
             <b>*** Debug Mode ***<br>Logged in as:</b><br>
             <%=firstName%>&nbsp;<%=lastName%>&nbsp;(<%=netID%>)
          <% }
    
    } else {
    
      /* Display authenticated user name */ %>     
      <% String displayuser = null;
      if ( principal == null ) { %>
         <a href="/web/auth/?action=overview">Sign In</a></font>
      <% } else { %>
         <b>Logged in as:</b>
         <% displayuser = firstName + " " + lastName + " (" + netID + ")"; %>
         <%= displayuser %><br><a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_SIGNOUT %>">Sign Out</a></font>
      <% }  
    } %>
    </font>
    </td>
    
    </tr>
</table>

