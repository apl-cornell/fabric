<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); %>
  <h2>
    <a name="n_assignments"></a>
    View Open Assignments
    <span id="assignmentshead">
     <a class="hide" href="#" onClick="show('assignments', ' (show)', '(hide)'); return false;">(show)</a>
    </span>
  </h2>
  <div id="assignments" class="showhide" style="display: none">
    <table class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
    	<tr>
    		<th align="left">Course</th>
    		<th align="left">Name</th>
    		<th align="left">Due</th>
    	</tr>
<% Element asgns = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_OPENASSIGNMENTS);
NodeList assignmentList = asgns.getChildNodes();
for (int i = 0; i < assignmentList.getLength(); i++)
{ 
	Element item = (Element)assignmentList.item(i); %>
      <tr>
        <td align="left"><%= item.getAttribute(XMLBuilder.A_COURSENAME) %></td>
        <td align="left"><%= item.getAttribute(XMLBuilder.A_NAME) %></td>
        <td align="left"><%= item.getAttribute(XMLBuilder.A_DUEDATE) %></td>
      </tr><%
} %>
    </table>
  </div>