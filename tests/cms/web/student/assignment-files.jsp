<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %>
<% Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root = (Element) displayData.getChildNodes().item(0);
   Element assignment = (Element) XMLUtil.getFirstChildByTagName(root,XMLBuilder.TAG_ASSIGNMENT); 
   Element l = (Element) assignment.getElementsByTagName(XMLBuilder.TAG_ITEMS).item(0);
   NodeList files = l == null ? null : l.getElementsByTagName(XMLBuilder.TAG_ITEM); %>
<% if (files != null && files.getLength() > 0) { %>
<h2>
  Files 
  <span id="fileshead">
    <a class="hide" href="#" onClick="hide('files', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>
<div id="files" class="showhide">
  <ul class="filelist">
  <% for (int i = 0; i < files.getLength(); i++) {
       Element entry = (Element) files.item(i);
       String link= "<a href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_DOWNLOAD + 
         "&amp;" + AccessController.P_DOWNLOADTYPE + "=" + XMLBuilder.T_ITEMFILE + 
         "&amp;" + AccessController.P_ID + "=" + entry.getAttribute(XMLBuilder.A_ID) + "\">" + 
         entry.getAttribute(XMLBuilder.A_NAME) + "</a>"; %>
    <li class="<%= i % 2 == 0 ? "row_even" : "row_odd" %>"><%= link %></li>
  <% } %>
  </ul>
</div>
<% } %>