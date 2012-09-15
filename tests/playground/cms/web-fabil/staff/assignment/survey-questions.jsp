<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.model.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); 
Element assignment= (Element) XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
Element filetypes= (Element) root.getElementsByTagName(XMLBuilder.TAG_FILETYPES).item(0);
NodeList subproblems= assignment.getElementsByTagName(XMLBuilder.TAG_SUBPROBLEM);
int assignType = Integer.parseInt(assignment.getAttribute(XMLBuilder.A_ASSIGNTYPE));
boolean isQuiz = assignType == Assignment.QUIZ;
boolean isAssignment = assignType == Assignment.ASSIGNMENT;
boolean isSurvey = assignType == Assignment.SURVEY;
%>

<h2>
  <%= isSurvey ? "Survey" : "Quiz"%> Questions
  <span id="questionshead">
    <a href="#" onClick="hide('questions', '(hide)', '(show)'); return false;" class="hide">(hide)</a>
  </span>
</h2>
<div id="questions" class="showhide">
  <table id="questtable" class="assignment_table" cellpadding="0" cellspacing="0" border="0" style="width: 100%; text-align: center">
    <tr>
      <th colspan = "2">Question Number</th>
      <th>Question Name</th>
      <th>Question Type</th>
      <%=assignType == Assignment.QUIZ ? "<th>Score</th>" : ""%>
	  <th>Remove</th>
    </tr>
    <% for (int i= 0; i != subproblems.getLength(); i++) {
     Element subprob= (Element) subproblems.item(i);
     int subprobType = Integer.parseInt(subprob.getAttribute(XMLBuilder.A_TYPE));
     String subID= subprob.getAttribute(XMLBuilder.A_ID); %>
    <tr id = "quest<%=subprob.getAttribute(XMLBuilder.A_ORDER)%>">
      <td style="text-align: left">
        <%= Integer.parseInt(subprob.getAttribute(XMLBuilder.A_ORDER))%>.
      </td>
      <td style="text-align: left">
      	<a href="#" onClick="moveUp('quest<%=subprob.getAttribute(XMLBuilder.A_ORDER)%>'); return false;")>(Up)</a> 
      	<a href="#" onClick="moveDown('quest<%=subprob.getAttribute(XMLBuilder.A_ORDER)%>'); return false;")>(Down)</a>
      </td>
      <td style="text-align: left" id="sub<%=subID%>">
        <input size="40" name="<%= AccessController.P_SUBPROBNAME + subID %>" value="<%= subprob.getAttribute(XMLBuilder.A_NAME) %>">
        
		<% String choiceDisplay = "none";
      	 if (subprobType == SubProblem.MULTIPLE_CHOICE){
      		choiceDisplay = "block";
      	 }%>

		<div id='choices<%= subID %>' style="display: <%= choiceDisplay %>">
		<%
	        NodeList choices = subprob.getElementsByTagName(XMLBuilder.TAG_CHOICE);
			String answer = subprob.getAttribute(XMLBuilder.A_CORRECTANSWER);
	        
	        for (int j = 0; j < choices.getLength(); j++){
	        	Element choice = (Element)choices.item(j);
	        	String choiceDivID = "choice_" + subID + "_" + j;
	        	%>
				<div id="<%= choiceDivID %>">
				<span><%=choice.getAttribute(XMLBuilder.A_LETTER)%>.</span><input value="<%=choice.getAttribute(XMLBuilder.A_ID)%>" name="<%=AccessController.P_CORRECTCHOICE%><%=subprob.getAttribute(XMLBuilder.A_ID)%>" type="radio" <%=(choice.getAttribute(XMLBuilder.A_ID).equals(answer)) ? "checked" : ""%>>
				<input value="<%=choice.getAttribute(XMLBuilder.A_TEXT)%>" name="<%=AccessController.P_CHOICE%><%=subprob.getAttribute(XMLBuilder.A_ID)%>_<%=choice.getAttribute(XMLBuilder.A_ID)%>" size="20" type="text">
	        	<input type="checkbox" name="<%= AccessController.P_REMOVECHOICE + choice.getAttribute(XMLBuilder.A_ID) %>">
	        	<strong>Remove</strong>
	        	</div>
			<%}
        %>
		</div>
		<div id="addChoice_<%= subID %>" style="display: <%= choiceDisplay %>; padding: 3px"><a class="replace" href="#" onClick="addChoice('choices<%= subID %>', <%=subID%>, false); return false">(Add Choice)</a></div>
		
		<script type="text/javascript">
			choiceindex[<%=subID%>] = <%=choices.getLength()%>;
			var i = <%= subprobType %>;
		</script>

      </td>
      <td style="text-align: center">
	      <select onChange="showAddChoice(this, '<%= subID %>')" style="width: 100%;" name="<%= AccessController.P_SUBPROBTYPE + subID %>" size="1">
	      	<%int probType = Integer.parseInt(subprob.getAttribute(XMLBuilder.A_TYPE));%>
	      	<option value="<%=SubProblem.MULTIPLE_CHOICE%>" <%=probType == SubProblem.MULTIPLE_CHOICE ? "selected" : ""%>>Multiple Choice</option>
	      	<option value="<%=SubProblem.FILL_IN%>" <%=probType == SubProblem.FILL_IN ? "selected" : ""%>>Fill In</option>
	      	<option value="<%=SubProblem.SHORT_ANSWER%>" <%=probType == SubProblem.SHORT_ANSWER ? "selected" : ""%>>Short Answer</option>
	      </select>
      </td>
      <% if (isQuiz) { 
      		String scoreInputName = AccessController.P_SUBPROBSCORE + subID;
      %>
      <td style="text-align: center">
        <input onkeyup="updateTotalScore();return false" id="<%= scoreInputName %>" size="3" name="<%= scoreInputName %>" value="<%= subprob.getAttribute(XMLBuilder.A_TOTALSCORE) %>">
      </td>
      <script type="text/javascript">
      	scoreInputs[<%= i %>] = "<%= scoreInputName %>";
      </script>
      <% } %>
      <td style="text-align: center">
        <input type="checkbox" name="<%= AccessController.P_REMOVESUBPROB + subID %>">
      </td>
    </tr>
<% } %>
	
  </table>
<script type="text/javascript">
	questindex = <%= subproblems.getLength()%>;
</script>
  <table id="addsub" cellpaddign='5' cellspacing='0' border='0' width='100%'>
  	<tr>
  		<td>
    <a href="#" onClick="addQuestRow(<%=assignType==Assignment.QUIZ ? "true" : "false"%>); return false;" class="button">(New Row)</a>&nbsp;
  		</td>
	<% if (isQuiz) { %>
	<!-- display total score here -->
		<td align='right'>
		<strong>Total Score:</strong>
		<span id='total_score'><%= assignment.getAttribute(XMLBuilder.A_TOTALSCORE) %></span>
		</span>
	<% } %>
	</tr>
  </table>
  <br />

