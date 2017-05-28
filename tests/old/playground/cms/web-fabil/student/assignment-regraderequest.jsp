<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %>
<%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getFirstChild();
Element assignment = (Element)XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
Element group = (Element)XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_GROUP);
NodeList problems = assignment.getElementsByTagName(XMLBuilder.TAG_SUBPROBLEM);
boolean allowed = assignment.hasAttribute(XMLBuilder.A_STUDENTREGRADES);
boolean statusGraded=   assignment.getAttribute(XMLBuilder.A_STATUS).equals("Graded");
boolean statusOpen=   assignment.getAttribute(XMLBuilder.A_STATUS).equals("Open");
boolean pastRegrade = assignment.hasAttribute(XMLBuilder.A_PASTREGRADE);
%>

<script type="text/javascript">
function allClick(){
	var cell = getElementById('RegradeReqCheckboxes');
	var checkBoxes = cell.getElementsByTagName('input');
	for (var i=1; i<checkBoxes.length; i++)
		checkBoxes[i].checked = true;
	return false;
}
function otherClick(){
	var allCheckbox = getElementById('allParts');
	allCheckbox.checked = false;
	return false;
}
	
function limitText(limitField, limitCount, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	} else {
		limitCount.value = limitNum - limitField.value.length;
	}
}
</script>
<% if (allowed && !statusOpen)  { %>
<h2>
  <a name="regrade"></a>
	Request Regrade
	<% if (!pastRegrade && statusGraded) { %>
  <span id="regradehead">
    <a class="hide" href="#" onClick="hide('regrade', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
  <% } %>
</h2>
<% if (pastRegrade || !statusGraded) { %>
Regrade requests are no longer being accepted online.
<% } else { %>
<div id="regrade" class="showhide">
<form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_REQUESTREGRADE %>" method="post">
<table class="grading" cellpadding="10px" border="0" cellspacing="0" width="100%">
	<tr ><%
	if (problems.getLength() > 0){%>
		<td id="RegradeReqCheckboxes" valign="top" style="left-padding:"1"">Submit Request for:<br>
			<input type="checkbox" id="allParts" name="allParts" value="allParts" onClick="allClick();">
			All Parts<br><br><%
		for (int i= 0; i<problems.getLength(); i++){
			Element problem = (Element) problems.item(i); 
			String name = AccessController.P_REGRADESUB+problem.getAttribute(XMLBuilder.A_ID);%>
			<input type="checkbox" name="<%=name%>" value="<%=name%>" onClick="otherClick();">
			<%=problem.getAttribute(XMLBuilder.A_NAME)%><br>
<%		}%>
		</td>
		<td><%
	}else{%>
		<td>
			<input type="hidden" name="<%=AccessController.P_REGRADEWHOLE%>" value="">
<%}%>
			<textarea name="<%= AccessController.P_REGRADEREQUEST %>" rows="8" cols="45"></textarea><br>
			<input type="hidden" name="<%=AccessController.P_ASSIGNID%>" value="<%=assignment.getAttribute(XMLBuilder.A_ASSIGNID)%>">
			<input type="hidden" name="<%=AccessController.P_GROUPID%>" value="<%=group.getAttribute(XMLBuilder.A_ID)%>">
			<input type="submit" value="Request Regrade">
		</td>
	</tr>
</table>
</form>
</div>
<% }
} %>