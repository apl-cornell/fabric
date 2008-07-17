<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0); 
NodeList assigns = root.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENT);
Element assignment= (Element)assigns.item(assigns.getLength() - 1);
Element description= (Element) assignment.getElementsByTagName(XMLBuilder.TAG_DESCRIPTION).item(0);
Text txt= (Text) description.getFirstChild(); 
if (txt.getData() != null && !txt.getData().equals("")) { %>
<h2>
  Assignment Description
  <span id="descriptionhead">
    <a class="hide" href="#" onClick="hide('description', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>
<div id="description" class="showhide">
  <%= txt.getData() %>
</div><%
} %>