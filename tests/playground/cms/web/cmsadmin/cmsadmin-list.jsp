<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
User p = (User)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); %>
  <h2>
    <a name="n_adminlist"></a>
    Edit CMS Administrators
    <span id="adminlisthead">
      <a class="hide" href="#" onClick="hide('adminlist', '(hide)', '(show)'); return false;">(hide)</a>
    </span>
  </h2>
  <div id="adminlist" class="showhide">
    <table class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
      <tr>
        <th align="left">NetID</th>
        <th align="left">Name</th>
        <th>&nbsp;</th>
      </tr>
<% Element al = (Element)root.getElementsByTagName(XMLBuilder.TAG_CMSADMINLIST).item(0);
NodeList adminList = al.getChildNodes();
for (int i = 0; i < adminList.getLength(); i++)
{
    Element item = (Element)adminList.item(i); %>
      <tr>
        <td align="left"><%= item.getAttribute(XMLBuilder.A_NETID) %></td>
        <td align="left"><%= item.getAttribute(XMLBuilder.A_NAME) %></td>
        <td align="center">
   <% if (!p.getNetID().equals(item.getAttribute(XMLBuilder.A_NETID)))
   	{ %>
          <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_REMOVECMSADMIN %>&amp;<%= AccessController.P_NETID %>=<%= item.getAttribute(XMLBuilder.A_NETID) %>">remove</a>
   <% } %>
			</td>
      </tr><%
} %>
    </table>
    <p>
    <form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_ADDCMSADMIN %>" method="post">
      NetID: <input type="text" name="<%= AccessController.P_NETID %>" size="10" maxlength="8">
      &nbsp;&nbsp;<input type="submit" value="Add Admin">
    </form>
  </div>