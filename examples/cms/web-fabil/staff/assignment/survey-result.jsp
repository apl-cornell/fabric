<%@ page language="java" import="org.w3c.dom.*,cms.auth.*, cms.model.*, cms.www.*, cms.www.xml.*" %>
<%
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getChildNodes().item(0);
Element result = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_SURVEYRESULT); 
Element assignment = XMLUtil.getFirstChildByTagName(result, XMLBuilder.TAG_ASSIGNMENT);
String assignmentCode = result.getAttribute(XMLBuilder.A_CODE).trim();
String assignmentName = assignment.getAttribute(XMLBuilder.A_NAME).trim();
String courseTitle = assignmentCode + ": " + result.getAttribute(XMLBuilder.A_COURSENAME);
String assignmentType = "";
int assignType = Integer.parseInt(assignment.getAttribute(XMLBuilder.A_TYPE));
assignmentType = (assignType == Assignment.QUIZ) ? "Quiz" : "Survey";
%>
<jsp:include page="../../header.jsp" />
<script src="CalendarPopup.js" type="text/javascript"></script> <%-- required by formattedtextboxes.js --%>
<script src="datetime.js" type="text/javascript"></script> <%-- required by formattedtextboxes.js --%>
<script src="formattedtextboxes.js" type="text/javascript"></script>
<jsp:include page="../../header-page.jsp" />
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
<jsp:include page="../navbar.jsp"/>
  <td id="course_page_container">
  	<div id="course_page">
  	
  	<script type="text/javascript">
  		var resultRowIDs = new Array();
  		var showResultID = "showResultLink";
  		
  		function toggleAllResult() {
  			var toggleLink = document.getElementById(showResultID);
  			var toggleTxt = toggleLink.firstChild.nodeValue;
  			
  			if (toggleTxt == "show result") toggleLink.firstChild.nodeValue = "hide result";
  			else toggleLink.firstChild.nodeValue = "show result";
  			
  			for (var i = 0; i < resultRowIDs.length; i++) {
  				var tr = document.getElementById(resultRowIDs[i]);
  				if (tr.style.display == "none") tr.style.display = "table-row";
  				else tr.style.display = "none";
  			}
  				
  		}
  	</script>
		
	<% String description = assignment.getAttribute(XMLBuilder.A_DESCRIPTION);
		if (description == null || description.equals("")) description = "This survey has no description."; %>
		<div class="course_title"><%= courseTitle %></div>
		<h2><%= assignmentType + ": " + assignmentName %></h2>
		<p><%= description %></p>
		
		<br />
		
		<% 
			int numSubmissions = Integer.parseInt(result.getAttribute(XMLBuilder.A_COUNT));
			String submissionText = Integer.toString(numSubmissions) + " submission";
			String showResultLinkTag = "no result";
			if (numSubmissions > 0) {
				showResultLinkTag = "<a id='showResultLink' href='javascript:void(0);' onclick='toggleAllResult();return false'>show result</a>"; 
			}
			if (numSubmissions > 1){
				submissionText = Integer.toString(numSubmissions) + " submissions";
			}
		
		%>

		
		<h3>
			<%= "Result for this " + assignmentType.toLowerCase() %>
			<%= showResultLinkTag %>	
		</h3>
		<table class="assignment_table" cellpadding="0" cellspacing="0" border="0">
		<%
			NodeList subproblems = result.getElementsByTagName(XMLBuilder.TAG_SUBPROBLEM); 
			int numProblems = subproblems.getLength();
			for (int i = 0; i < numProblems; i++) {
				Element problem = (Element) subproblems.item(i);
				int stype = Integer.parseInt(problem.getAttribute(XMLBuilder.A_TYPE));
				String question = problem.getAttribute(XMLBuilder.A_NAME);
				String sid = problem.getAttribute(XMLBuilder.A_ID);
				String resultTableID = "result" + sid;
				String toggleId = "toggle" +  sid;
		%>
			<tr>
				<td><strong>Question<%= " " + (i+1) + " : " + question %></strong></td>
			</tr>
			
			<script type="text/javascript">
				resultRowIDs[<%= i %>] = "<%= resultTableID %>";
			</script>
			
			<tr id="<%= resultTableID %>" style="display: none"><td colspan="2" >
				<table class="assignment_table result_table" cellpadding="0" cellspacin="0" border="0" width="100%">
			
			<% if (stype == SubProblem.MULTIPLE_CHOICE) { %>
				<tr>
					<th>Choice</th>
					<th style="width:200px; text-align: center"></th>
				</tr>
			<% } else { %>
				<tr><th>Responses</th></tr>
			<% } %>

			<%
				NodeList answers = problem.getElementsByTagName(XMLBuilder.TAG_ANSWER);
				int numAnswers = answers.getLength();
				for (int j = 0; j < numAnswers; j++) {
					Element answer = (Element) answers.item(j);	
					String lastClass = "";
					if (j == numAnswers - 1) lastClass = "class='last'";
					if (stype != SubProblem.MULTIPLE_CHOICE) {
			%>
					<tr><td <%= lastClass %>><%= answer.getAttribute(XMLBuilder.A_TEXT) %></td></tr>
				<% } else { 
						int choiceCount = Integer.parseInt(answer.getAttribute(XMLBuilder.A_COUNT));
						String count = (choiceCount == 1) ? " response" : " responses";
				%>
					<tr><td <%= lastClass %>><%= answer.getAttribute(XMLBuilder.A_LETTER) + ". " + answer.getAttribute(XMLBuilder.A_TEXT) %></td>
					<td <%= lastClass %> style="width:200px; text-align: center"><%= Integer.toString(choiceCount) + count %></td></tr>
				<% } %>
			<% } %>
				</table></td>
			</tr>
		<% } %>
		</table>
		
		<br />
		
		<%  
			String filename = assignmentCode.replace(' ', '_') + "_" + assignmentName.replace(' ', '_') + ".csv";
			String action = AccessController.ACT_SURVEYDOWNLOAD;
			String aid = assignment.getAttribute(XMLBuilder.A_ID);
		%>
		<p>Export as <a href="?action=<%= action + "&" + AccessController.P_ASSIGNID + "=" + aid + "&" + AccessController.P_FILENAME + "=" + filename %>">CSV</a></p>
		
	</div><!-- course_page -->
  </td>
  <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
</tr>
<jsp:include page="../../footer.jsp"/>
