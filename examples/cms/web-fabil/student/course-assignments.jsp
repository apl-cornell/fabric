<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*" %>
<% Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root = (Element) displayData.getChildNodes().item(0);
   Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
   boolean showWeights = course.hasAttribute(XMLBuilder.A_SHOWASSIGNWEIGHTS);
   boolean showTotals = course.hasAttribute(XMLBuilder.A_SHOWTOTALSCORES);
   boolean hasScores = false; 
   int gradedCount = 0; 
   int nonGradeCount = 0; 
   Element l =(Element) root.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENTS).item(0);
   Element surveyRoot = (Element) root.getElementsByTagName(XMLBuilder.TAG_SURVEYS).item(0);
   %>
<% if (l != null) { %>
<div class="assignment_left">
	<h2>
        Assignments 
        <span class="hide" id="assignmentshead">
          <a class="hide" href="#" onClick="hide('assignments', '(hide)', '(show)'); return false;">(hide)</a>
        </span>
	</h2>
<div id="assignments" class="showhide"><% 
NodeList assignments = null;
if (null == l) { %> 
      Guests cannot view assignments. <% 
} else if ((assignments = l.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENT)).getLength() == 0) { %>
	
		<p>No assignments are currently posted.</p>
	<% 
} else { %>

        <table class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
          <tr>
            <th>&nbsp;</th>
            <th align="center">Due</th>
            <th id="score_hide0" style="display: none" align="center">Score</th>
           	<% if (showWeights) { %>
           	<th align="center">Weight</th>
           	<% } %>
			<th align="center">Max</th>
			<th align="center">High</th>
            <th align="center">Mean</th>
            <th align="center">Median</th>
            <th align="center">Deviation</th>
            <th align="center">Status</th>
          </tr><% 
    for (int i = 0; i < assignments.getLength(); i++) {
         Element assignment = (Element) assignments.item(i); 
         int assignType = Integer.parseInt(assignment.getAttribute(XMLBuilder.A_ASSIGNTYPE));
         if (assignType == Assignment.SURVEY) continue;
         
         String score =  assignment.hasAttribute(XMLBuilder.A_SCORE)? assignment.getAttribute(XMLBuilder.A_SCORE): null;
         String status = assignment.getAttribute(XMLBuilder.A_STATUS);
         boolean showStats = assignment.hasAttribute(XMLBuilder.A_SHOWSTATS);
         boolean graded = status.equals(Assignment.GRADED);
         hasScores = hasScores || (graded && score != null);
         gradedCount = graded ? gradedCount + 1 : gradedCount;
         nonGradeCount = graded ? nonGradeCount : nonGradeCount + 1;
         %>
          <tr>
            <!-- assignment name -->
            <td nowrap>
              <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_ASSIGN %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>">
                <%= assignment.getAttribute(XMLBuilder.A_NAME) %>
              </a>
            </td>
            <!-- open-assignment data -->
            <td align="center" nowrap>
              <%= assignment.getAttribute(XMLBuilder.A_DUEDATE) + " " + assignment.getAttribute(XMLBuilder.A_DUETIME) + assignment.getAttribute(XMLBuilder.A_DUEAMPM) %>
            </td><%
      if (graded){%>
			      <td id="score_hide<%= gradedCount %>" style="display: none" align="center" nowrap>
              <%= score == null ? "&nbsp;" : score %>
            </td>
			     
			     <% if (showWeights) { %>
			     <td align="center" nowrap>
			     			<%= assignment.getAttribute(XMLBuilder.A_WEIGHT) %>
			     </td>
			     <% } %>
                 <td align="center" nowrap>
							<%=assignment.getAttribute(XMLBuilder.A_TOTALSCORE)%>
			     </td>
			     
			<%  if (showStats){%>
            <!-- high -->
            <td align="center" nowrap>
              <%= assignment.hasAttribute(XMLBuilder.A_STATMAX) ? assignment.getAttribute(XMLBuilder.A_STATMAX) : "&nbsp;" %>
            </td>
            <!-- mean -->
            <td align="center" nowrap>
              <%= assignment.hasAttribute(XMLBuilder.A_STATMEAN) ? assignment.getAttribute(XMLBuilder.A_STATMEAN) : "&nbsp;" %>
            </td>
            <!--- median -->
            <td align="center" nowrap>
              <%= assignment.hasAttribute(XMLBuilder.A_STATMEDIAN) ? assignment.getAttribute(XMLBuilder.A_STATMEDIAN) : "&nbsp;" %>
            </td>
            <!-- dev -->
            <td align="center" nowrap>
              <%= assignment.hasAttribute(XMLBuilder.A_STATDEV) ? assignment.getAttribute(XMLBuilder.A_STATDEV) : "&nbsp;" %>
            </td><% 
       		}else {%>
			      <td align="center" colspan="4" nowrap>
              No Statistics Available
            </td>
<%			  }
      } else { %>
            <td id="scorehide<%= nonGradeCount %>" align="center" colspan="<%= showWeights ? "6" : "5" %>" nowrap>
              No Statistics Available
            </td>
            <td id="scoreshow<%= nonGradeCount %>" style="display: none" align="center" colspan="<%= showWeights ? "7" : "6" %>" nowrap>
              No Statistics Available
            </td>
   <% } %>
            <!-- status -->
            <td align="center" nowrap>
              <%= status%>
            </td>
            <!-- examples
                 graded; regrades by Jan 10
                 sumbission closed; not graded
                 1 file remaining; extn. until Jan 10
                 not in a group
            -->
          </tr>
    <% } %>
		<% if (showTotals) { %>
			<tr id="totalScoreRow" style="display: none">
				<td style="color: #ab1a2a" nowrap>Total Weighted Score</td>
				<td>&nbsp;</td>
				<td style="color: #ab1a2a" align="center" class="link"><%= course.getAttribute(XMLBuilder.A_TOTALSCORE) %></td>
				<% if (showWeights) { %>
				<td>&nbsp;</td>
				<% } %>
				<td style="color: #ab1a2a" align="center" class="link"><%= course.getAttribute(XMLBuilder.A_MAXTOTALSCORE) %></td>
                <td style="color: #ab1a2a" align="center" class="link"><%= course.getAttribute(XMLBuilder.A_HIGHTOTALSCORE) %></td>
				<td style="color: #ab1a2a" align="center" class="link"><%= course.getAttribute(XMLBuilder.A_MEANTOTALSCORE) %></td>
				<td style="color: #ab1a2a" align="center" class="link"><%= course.getAttribute(XMLBuilder.A_MEDIANTOTALSCORE) %></td>
				<td style="color: #ab1a2a" align="center" class="link"><%= course.getAttribute(XMLBuilder.A_STDEVTOTALSCORE) %></td>
			</tr>
		<% } %>
		</table>
        <small>Click an assignment's name to see related files and other assignment-specific details.</small>
        &nbsp;&nbsp;
        <% if (hasScores) { %>
          <a class="hide" id="gradeVis" href="#" onClick="showGrade(); return false;">(Show Grades)</a>
        <% } %>

  <% } %>
 </div><!-- assignments -->
 
 <br />
    <% NodeList surveys = surveyRoot.getElementsByTagName(XMLBuilder.TAG_SURVEY); 
		int numSurveys = surveys.getLength();
	%>
	<h2>
        Surveys 
        <span class="hide" id="surveyshead">
          <a class="hide" href="#" onClick="hide('surveys', '(hide)', '(show)'); return false;">(hide)</a>
        </span>
	</h2>
	<div id="surveys" class="showhide">
	<% if (numSurveys == 0) {%>
		<p>No surveys are currently posted.</p>
	<%} else { %>
		<table width="100%" class="assignment_table" cellpadding="2" cellspacing="0" border="0">
			<tr>
              <th>&nbsp;</th>
              <th align="center">Due</th>
              <th align="center">Status</th>
           </tr>
	<% for (int j = 0; j < numSurveys; j++) {
		Element survey = (Element) surveys.item(j);
		String action = "?" + AccessController.P_ACTION + "=" + AccessController.ACT_ASSIGN + "&amp;" + AccessController.P_ASSIGNID + "=" + survey.getAttribute(XMLBuilder.A_ASSIGNID);
	%>	
		<tr>
			<td nowrap><a href="<%= action %>"><%= survey.getAttribute(XMLBuilder.A_NAME) %></a></td>
			<td align="center"><%= survey.getAttribute(XMLBuilder.A_DUEDATE) %></td>
			<td align="center"><%= survey.getAttribute(XMLBuilder.A_STATUS) %></td>
		</tr>
	<% } %> 
	</table>
<% } %>
<br />

</div><!-- surveys -->
</div><!-- assignment_left -->
<% } %>
