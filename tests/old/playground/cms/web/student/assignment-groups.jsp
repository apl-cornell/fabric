<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.model.*" %><%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
Element assignment = (Element)XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
Element group = (Element)XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_GROUP);
String groupid= (group == null) ? "" : group.getAttribute(XMLBuilder.A_ID);
int maxgroup= Integer.parseInt(assignment.getAttribute(XMLBuilder.A_MAXGROUP));
String status = assignment.getAttribute(XMLBuilder.A_STATUS);
boolean enableGroups = status.equals(Assignment.OPEN) && !assignment.hasAttribute(XMLBuilder.A_PASTDUE); %>
<% if (group != null) { %>
<h2>
  <a name="group"></a>
  Group Management
  <span id="groupshead">
    <a class="hide" href="#" onClick="hide('groups', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>
<div id="groups" class="showhide">
  <p><%
Element members= (Element)group.getElementsByTagName(XMLBuilder.TAG_MEMBERS).item(0);
NodeList membersNodes = members.getChildNodes(); %>
  Currently in your group: <%= (enableGroups && membersNodes.getLength() > 1) ? ("<a href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_LEAVE + "&amp;" + AccessController.P_GROUPID + "=" + groupid + "\">(leave)</a>") : "" %>
  <span class="indented"><%
for (int i = 0; i < membersNodes.getLength(); i++) {
  Element member = (Element) membersNodes.item(i); %>
    <%= i == 0 ? "" : "," %>
    <span class="personname"><%= member.getAttribute(XMLBuilder.A_NAME) %></span>
    <span class="netid">(<%= member.getAttribute(XMLBuilder.A_NETID) %>)</span><%
} %>
  </span><%
Element invited= (Element)group.getElementsByTagName(XMLBuilder.TAG_INVITATIONS).item(0);
NodeList invitedNodes= invited.getElementsByTagName(XMLBuilder.TAG_INVITATION);
if (enableGroups && invitedNodes.getLength() > 0) { %>
  Invitations sent to:
  <span class="indented"><%
  for (int i = 0; i < invitedNodes.getLength(); i++) {
    Element invitee = (Element) invitedNodes.item(i); %>
    <span class="personname"><%= invitee.getAttribute(XMLBuilder.A_NAME) %></span>
    <a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_CANCEL + "&amp;" + AccessController.P_NETID + "=" + invitee.getAttribute(XMLBuilder.A_NETID) + "&amp;" + AccessController.P_GROUPID + "=" + groupid %>">(cancel)</a><%
  } %>
  </span><%
} 
if (enableGroups && !assignment.hasAttribute(XMLBuilder.A_ASSIGNEDGROUPS) && maxgroup > membersNodes.getLength()) {%>
  <form action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_INVITE + "&amp;" + AccessController.P_GROUPID + "=" + groupid %>" method="post">
    Invite: <input name="invite" type="text" value="" size=30>
    <input type="submit" value="Invite">
  </form><%
} %>
  <p><%
Element invites= (Element)group.getElementsByTagName(XMLBuilder.TAG_GROUPSINVITEDTO).item(0);
NodeList invitesNodes = invites.getElementsByTagName(XMLBuilder.TAG_INVITATION);
if (enableGroups && invitesNodes.getLength() > 0) { %>
   Received invitations from:<br>
   <span class="indented"><%
  for (int i = 0; i < invitesNodes.getLength(); i++) {
    Element invite = (Element) invitesNodes.item(i); 
    NodeList users= invite.getChildNodes();
    int length= users.getLength();
    for (int j= 0; j != length; j++) { 
      Element user= (Element)users.item(j); %>
     <span class="personname"><%= user.getAttribute(XMLBuilder.A_NAME) %></span>
     <span class="netid">(<%= user.getAttribute(XMLBuilder.A_ID) %>)</span><%= j != length - 1 ? "," : "" %><%
    } %>
     <span class="link">(<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_ACCEPT + "&amp;" + AccessController.P_GROUPID + "=" + invite.getAttribute(XMLBuilder.A_ID) %>">accept</a>,
     <a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_DECLINE + "&amp;" + AccessController.P_GROUPID + "=" + invite.getAttribute(XMLBuilder.A_ID) %>">decline</a>)</span><br><%
  } %>
   </span><%
} %>
</div>
<% } %>
