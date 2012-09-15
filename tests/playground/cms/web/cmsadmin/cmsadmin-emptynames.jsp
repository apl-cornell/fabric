<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); 
NodeList userlist = root.getElementsByTagName(XMLBuilder.TAG_NAMELESSUSER);
%>
<% if (userlist.getLength() > 0) { %>
  <h2>
    <a name="n_emptynames"></a>
    Users Missing Names
    <span id="emptynamehead">
      <a class="hide" href="#" onClick="show('emptyname', ' (show)', ' (hide)'); return false;"> (show)</a>
    </span>
  </h2>
  <div id="emptyname" class="showhide" style="display: none">
      <table class="assignment_table" cellpadding="2" cellspacing="0" border="0">
      <tr><th>NetID</th><th>First Name</th><th>Last Name</th></tr>
			<% for (int i=0; i < userlist.getLength(); i++) {
          Element xUser = (Element) userlist.item(i); 
          String netID = xUser.getAttribute(XMLBuilder.A_NETID); %>
          <tr>
            <td><%= netID %></td>
            <td><input name="<%= AccessController.P_FIRSTNAME + netID %>" type="text" value="<%= xUser.getAttribute(XMLBuilder.A_FIRSTNAME) %>"></td>
            <td><input name="<%= AccessController.P_LASTNAME + netID %>" type="text" value="<%= xUser.getAttribute(XMLBuilder.A_LASTNAME) %>"></td>
          </tr>
			<% } %>
      </table>
  </div>
<% } %>