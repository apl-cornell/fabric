<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.model.*" %><%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
Element assignment = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
if (assignment == null) System.out.println("Assignment null");
Element group= XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_GROUP);
boolean pastDue = assignment.hasAttribute(XMLBuilder.A_PASTDUE);
String latedate = assignment.getAttribute(XMLBuilder.A_LATEFULLDATE);
boolean hasLate = latedate != null && !latedate.equals("");
boolean pastLate = assignment.hasAttribute(XMLBuilder.A_PASTLATE);
String extension = group.getAttribute(XMLBuilder.A_EXTENSION);
boolean hasExtension = extension != null && !extension.equals("");
boolean pastExtension = group.hasAttribute(XMLBuilder.A_PASTEXTENSION);
String status = assignment.getAttribute(XMLBuilder.A_STATUS);
boolean isOpen = status.equals(Assignment.OPEN);
NodeList subproblems= assignment.getElementsByTagName(XMLBuilder.TAG_SUBPROBLEM);
if (subproblems != null) { %>

<script type='text/javascript'>
var confirmMessage = "You are about to submit your answers to " + "<%= assignment.getAttribute(XMLBuilder.A_NAME) %>. Are you sure?";
function confirmSubmit() { return confirm(confirmMessage); }
</script>

<h2>
  <a name="questions"></a>
	Questions
  <span id="questionhead">
    <a class="hide" href="#" onClick="hide('submission', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>
<div id="question" class="showhide"><%
  boolean displaySubmit = false;
	if (!isOpen) {
    if (hasExtension && !pastExtension) {
      displaySubmit = true; %>
      <p style="color: red">Your group has been granted an extension until: <%= extension %></p>
  <% } else { %>
      <p>Submissions for this assignment are no longer being accepted.</p><%
     }
  }
  // Check if submission deadlines have passed
  else if (pastDue) { 
      if (hasExtension && !pastExtension) { 
        displaySubmit = true; %>
        <p style="color: red">Your group has been granted an extension until: <%= extension %></p>
      <% } else if (hasLate && !pastLate) { 
        displaySubmit = true; %>
        <p style="color: red">Submission deadline has passed.<br>Late submissions are being accepted until: <%= latedate %></p>
      <% } else { %>
          <p>Submissions for this assignment are no longer being accepted.</p><%
         }
  } else {
    displaySubmit = true;
  } %>
<% if (displaySubmit) { %>
  <br>
  <form action="?<%=AccessController.P_ACTION %>=<%= AccessController.ACT_SURVEYSUBMIT %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>" enctype="multipart/form-data" method="post">
  <table id="questtable" style="width: 100%; text-align: left">
    <% for (int i= 0; i != subproblems.getLength(); i++) {
     Element subprob= (Element) subproblems.item(i);
     String subID= subprob.getAttribute(XMLBuilder.A_ID); %>
     <tr id = "quest<%=subprob.getAttribute(XMLBuilder.A_ORDER)%>">
      <td style="text-align: left; vertical-align: top;"><%= Integer.parseInt(subprob.getAttribute(XMLBuilder.A_ORDER)) %>.</td>
      <td style="text-align: left">
		<span style="font-weight:bold"><%= subprob.getAttribute(XMLBuilder.A_NAME) %></span>
        <br>
        <%
        	int probType = Integer.parseInt(subprob.getAttribute(XMLBuilder.A_TYPE));
			if (probType == SubProblem.MULTIPLE_CHOICE){
				NodeList choices = subprob.getElementsByTagName(XMLBuilder.TAG_CHOICE);
				for (int j = 0; j < choices.getLength(); j++){
					Element choice = (Element)choices.item(j);%>
					<input name = "<%=AccessController.P_SUBPROBNAME + subprob.getAttribute(XMLBuilder.A_ID)%>" type = "radio" value = "<%=choice.getAttribute(XMLBuilder.A_ID)%>" <%=subprob.hasAttribute(XMLBuilder.A_ANSWER) && subprob.getAttribute(XMLBuilder.A_ANSWER).equals(choice.getAttribute(XMLBuilder.A_ID)) ? "checked" : ""%>>
					<%=choice.getAttribute(XMLBuilder.A_LETTER)%>. <%=choice.getAttribute(XMLBuilder.A_TEXT)%><br>
				<%}
			%>
			<%} else if (probType == SubProblem.FILL_IN){%>
				<input name = "<%=AccessController.P_SUBPROBNAME + subprob.getAttribute(XMLBuilder.A_ID)%>" type = "text" size="40" <%=subprob.hasAttribute(XMLBuilder.A_ANSWER) ? "value=\"" + subprob.getAttribute(XMLBuilder.A_ANSWER) + "\"" : ""%>>
			<%} else if (probType == SubProblem.SHORT_ANSWER){%>
				<textarea name = "<%=AccessController.P_SUBPROBNAME + subprob.getAttribute(XMLBuilder.A_ID)%>" rows="5" cols="40"><%=subprob.hasAttribute(XMLBuilder.A_ANSWER) ? subprob.getAttribute(XMLBuilder.A_ANSWER): ""%></textarea>
			<%}%>
      </td>
    </tr>
<% } %>
  </table>
  <br><br>
  <input type="submit" value="Submit">
  </form>
<%  }
 } %>
</div>
