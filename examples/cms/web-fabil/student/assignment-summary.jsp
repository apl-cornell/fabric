<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.model.*" %><% 
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
Element assignment = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
String status = assignment.getAttribute(XMLBuilder.A_STATUS);
Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
Element principal = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_PRINCIPAL);
boolean isGuest = principal == null;
boolean isCCMember = principal != null && principal.hasAttribute(XMLBuilder.A_ISCCMEMBER);
boolean isStudent = course.hasAttribute(XMLBuilder.A_ISSTUDENT);
boolean showGrades = isStudent && status.equals(Assignment.GRADED);
boolean showStats = isStudent && assignment.hasAttribute(XMLBuilder.A_SHOWSTATS);
boolean hasScore = assignment.hasAttribute(XMLBuilder.A_SCORE);
boolean graded = status.equals(Assignment.GRADED);
boolean closed = status.equals(Assignment.CLOSED);
boolean regrades = assignment.hasAttribute(XMLBuilder.A_STUDENTREGRADES) &&
      assignment.hasAttribute(XMLBuilder.A_REGRADEDATE);
boolean latesubmit = assignment.hasAttribute(XMLBuilder.A_LATEDATE);
boolean showSolutions = 
    isStudent ? ((graded && hasScore) || ((graded || closed) && assignment.hasAttribute(XMLBuilder.A_SHOWSOLUTION))) :
    isCCMember ? (graded && course.hasAttribute(XMLBuilder.A_SOLUTIONCCACCESS)) : 
    (graded && course.hasAttribute(XMLBuilder.A_SOLUTIONGUESTACCESS));
String duedatefull = assignment.getAttribute(XMLBuilder.A_DUEDATE) + ", " + 
                  assignment.getAttribute(XMLBuilder.A_DUETIME) +
                  assignment.getAttribute(XMLBuilder.A_DUEAMPM);
String latedatefull = latesubmit ? assignment.getAttribute(XMLBuilder.A_LATEDATE) + ", " + 
                  assignment.getAttribute(XMLBuilder.A_LATETIME) +
                  assignment.getAttribute(XMLBuilder.A_LATEAMPM) : null;
String regradedatefull = regrades ? assignment.getAttribute(XMLBuilder.A_REGRADEDATE) + ", " + 
                  assignment.getAttribute(XMLBuilder.A_REGRADETIME) +
                  assignment.getAttribute(XMLBuilder.A_REGRADEAMPM) : null;
Element solution = (Element) root.getElementsByTagName(XMLBuilder.TAG_SOLFILE).item(0);
%>
<style type="text/css">
  .sgradetable tr, td, th {vertical-align: middle, text-align: center, border-width: thin}
</style>
<h2>Summary</h2>
<dl id="summary">
  <dt>Due:</dt><dd><%= duedatefull %></dd>
<% if (assignment.hasAttribute(XMLBuilder.A_DUECOUNTDOWN)) { %>
  <dt>Time Remaining:</dt><dd><%= assignment.getAttribute(XMLBuilder.A_DUECOUNTDOWN) %></dd>
<% } %>
  <dt>Groups:</dt><dd><%
if (assignment.hasAttribute(XMLBuilder.A_ASSIGNEDGROUPS)) { %>
    Groups assigned by staff<%
} else {
  String mingroup= assignment.getAttribute(XMLBuilder.A_MINGROUP);
  String maxgroup= assignment.getAttribute(XMLBuilder.A_MAXGROUP); %>
    Groups of 
    <%= mingroup.equals(maxgroup) ? mingroup : mingroup + " to " + maxgroup %>
    student<%= mingroup.equals(maxgroup) && mingroup.equals("1") ? "" : "s" %><%
} %>
  </dd>
  <dt>Late Submissions:</dt><dd><%
  if (latesubmit) { %>
    Accepted until <%= latedatefull %><%
  } else { %> 
    No Late submissions accepted <%
  } %>
  </dd>
  <dt>Regrades:</dt><dd><%
  if (regrades) { %>
    Accepted until <%= regradedatefull %><%
  } else { %> 
    No online regrade requests accepted <%
  } %>
  </dd>
<% Element group = (Element)XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_GROUP);
NodeList members = null;
if (group != null) {
	Element membersList = XMLUtil.getFirstChildByTagName(group, XMLBuilder.TAG_MEMBERS);
	members = membersList.getChildNodes();
}
if (group != null && !status.equals(Assignment.OPEN) && members.getLength() > 1) { %>
  <dt>My Group:</dt><dd>
<%     for (int i = 0; i < members.getLength(); i++) {
         Element member = (Element) members.item(i); %>
         <%= (i != 0 ? ", " : "") + member.getAttribute(XMLBuilder.A_NAME) %>
    <% }   %>
  </dd>
<% } %>
</dl>
<br>
<% if (showGrades) { %>
  <% NodeList problems = assignment.getElementsByTagName(XMLBuilder.TAG_SUBPROBLEM);
  	int length = problems.getLength(); %>
<br><h2>Scores</h2>
<table class="sgradetable" width="30%" cellpadding="1" cellspacing="0">
  <tr>
  	<th align="center">&nbsp;</th>
  	<th align="center">Score</th>
  	<th align="center">Max</th>
  </tr>
<%
     for (int i = 0; i < problems.getLength(); i++) {
       Element problem = (Element) problems.item(i); %>
  <tr>
    <td align="center"><%= problem.getAttribute(XMLBuilder.A_NAME) %></td>
    <td align="center"><%= showGrades ? problem.getAttribute(XMLBuilder.A_SCORE) : "" %></td>
    <td align="center"><%= problem.getAttribute(XMLBuilder.A_TOTALSCORE) %></td>
  </tr><% 
  	} %>
  <tr>
    <td align="center"><%= problems.getLength() > 0 ? "Total" : assignment.getAttribute(XMLBuilder.A_NAME) %></td>
    <td align="center"><%= showGrades ? assignment.getAttribute(XMLBuilder.A_SCORE) : "" %></td>
    <td align="center"><%= assignment.getAttribute(XMLBuilder.A_TOTALSCORE) %></td>
  </tr>
</table>
<% } %>
<br>
<% if (showGrades && showStats) { %>
<h2>Statistics</h2>
<table class="sgradetable" width="50%" cellpadding="1" cellspacing="0">
  <tr>
	<th align="center">High</th>
  	<th align="center">Mean</th>
  	<th align="center">Median</th> 	
  	<th align="center">Deviation</th>
  </tr>
	<tr>
		<td align="center"><%=assignment.getAttribute(XMLBuilder.A_STATMAX)%></td>
		<td align="center"><%=assignment.getAttribute(XMLBuilder.A_STATMEAN)%></td>
		<td align="center"><%=assignment.getAttribute(XMLBuilder.A_STATMEDIAN)%></td>
		<td align="center"><%=assignment.getAttribute(XMLBuilder.A_STATDEV)%></td>
	</tr>		
</table>
<% } %>
	<% if (showSolutions && solution != null) { %>
      <br>
      Download solutions <a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_DOWNLOAD + "&amp;" + AccessController.P_ID + "=" + solution.getAttribute(XMLBuilder.A_ID) + "&amp;" + AccessController.P_DOWNLOADTYPE + "=" + XMLBuilder.T_SOLFILE %>">here</a>
	<% } %>
