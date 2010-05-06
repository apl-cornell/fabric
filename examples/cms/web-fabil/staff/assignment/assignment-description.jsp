<%@ page language="java" import="org.w3c.dom.*, cms.model.*, cms.www.*, cms.www.xml.*" %>
<% Document displaydata= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root= (Element) displaydata.getFirstChild(); 
   Element assignment= (Element) XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
   Element description= (Element) assignment.getElementsByTagName(XMLBuilder.TAG_DESCRIPTION).item(0);
   int assigntype = Integer.parseInt(assignment.getAttribute(XMLBuilder.A_ASSIGNTYPE));
   String type = "Assignment";
   if (assigntype == Assignment.QUIZ) type = "Quiz";
   else if (assigntype == Assignment.SURVEY) type = "Survey";
   Text txt= (Text) description.getFirstChild(); %>
<h2>
  <%= type %> Description
  <span id="descriptionhead">
    <a class="hide" href="#" onClick="show('description', '(show)', '(hide)'); return false;">(show)</a>
  </span>
</h2>
<div id="description" class="showhide" style="text-align: center; display: none">
  <textarea rows="8" cols="60" name="<%= AccessController.P_DESCRIPTION %>"><%= txt.getData() %></textarea>
</div>
