<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %>
<% Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root = (Element) displayData.getChildNodes().item(0);
   Element l = (Element) root.getElementsByTagName(XMLBuilder.TAG_CLARIFICATIONS).item(0);
   NodeList clarifications = null; %>
<h2>
  <a name="clarifications"></a>
  Clarifications
  <span id="clarificationshead">
    <a class="hide" href="#" onClick="hide('clarifications', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>  
<div id="clarifications" class="showhide">
  <p>
<% if (null == l) { %> 
  Error: no clarifications in data 
<% } else if ((clarifications = l.getChildNodes()).getLength() == 0) { %> 
  No clarifications have been posted. 
<% } else {
     for (int i = 0; i < clarifications.getLength(); i++) {
     Element item = (Element) clarifications.item(i); %>
  <%= item.getAttribute(XMLBuilder.A_POSTER) %>
  (<%= item.getAttribute(XMLBuilder.A_DATE) %>):
  <%= item.getAttribute(XMLBuilder.A_TEXT) %>
  <br><br>
  <% }
   } %>
<!-- Request clarification by: <a href="">email</a>, <a href="">newsgroup</a>, <a href="">weblog</a> -->
</div>
