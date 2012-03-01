<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); 
NodeList nocuids = root.getElementsByTagName(XMLBuilder.TAG_NOCUID); %>
  <h2>
    Enrolled Students Without CUIDs
    <span id="nocuidhead">
      <a class="hide" href="#" onClick="show('nocuid','(show)','(hide)'); return false;">(show)</a>
    </span>
  </h2>
  <div id="nocuid" style="display: none">
    There are currently <%= root.getAttribute(XMLBuilder.A_CUIDCOUNT) %> unique CUIDs in the system. &nbsp;
    <%= nocuids.getLength() == 50 ? "50+" : String.valueOf(nocuids.getLength()) %> enrolled students do not have CUIDs.<br>
    <table class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
      <tr>
        <th align="center">Last Name</th>
        <th align="center">First Name</th>
        <th align="center">NetID</th>
      </tr>
      <% for (int i=0; i < nocuids.getLength(); i++) { 
        Element xNoCUID = (Element) nocuids.item(i); %>
        <tr>
          <td><%= xNoCUID.getAttribute(XMLBuilder.A_LASTNAME) %></td>
          <td><%= xNoCUID.getAttribute(XMLBuilder.A_FIRSTNAME) %></td>
          <td><%= xNoCUID.getAttribute(XMLBuilder.A_NETID) %></td>
        </tr>
      <% } %>
    </table>
  </div>
<%--
  <br>
  <h2>
    Upload CUID File
    <span id="gradeshead">
      <a class="hide" href="#" onClick="hide('grades', '(hide)', '(show)'); return false;">(hide)</a>
    </span>
  </h2>
  <div id="grades" class="showhide">
    <form id="uploadcuids" method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_UPLOADCUIDS %>">
      Upload NetID/CUID CSV File: <input type="file" size="10" name="<%= AccessController.P_CUIDFILE %>"><input type="submit" value="Upload">
      <br><span class="example">CUID is read as text, so formats ###### and 00#######, among others, are fine</span>
      <br><span class="example">Unknown NetIDs will be ignored</span>
    </form>
  </div>
    --%>