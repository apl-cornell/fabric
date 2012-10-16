<%@ page language="java" import="org.w3c.dom.*,cms.auth.*, cms.model.*, cms.www.*, cms.www.xml.*" %><%
/*********************************************
display table of assignments on staff-side
main course page
*********************************************/
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
boolean isadmin= course.hasAttribute(XMLBuilder.A_ISADMIN);
boolean isgroups= course.hasAttribute(XMLBuilder.A_ISGROUPS);
boolean isgrades= course.hasAttribute(XMLBuilder.A_ISGRADES);
boolean isassign= course.hasAttribute(XMLBuilder.A_ISASSIGN);
boolean iscategory = course.hasAttribute(XMLBuilder.A_ISCATEGORY);
boolean isstaff = isadmin || isgroups || isgrades || isassign || iscategory;
String URL= request.getServletPath(); 
boolean isAssignOnly = URL.equals(AccessController.ASSIGNLISTADMIN_URL);
NodeList hiddenAssigns = ((Element) root.getElementsByTagName(XMLBuilder.TAG_HIDDENASSIGNMENTS).item(0)).getChildNodes();
%><div class="assignment_left">
<% if (isAssignOnly) { %>
  <span class="assignment_title">Assignments</span>
<% } else  { %>
	<h2>
	  Assignments
	  <span id="assignmentshead">
	    <a class="hide" href="#" onClick="hide('assignments', '(hide)', '(show)'); return false;">(hide)</a>
	  </span>
	</h2>
<div id="assignments" class="showhide">        
<% } %>
<% 
Element l= (Element) root.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENTS).item(0);
NodeList list= l.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENT);
int length= list.getLength();
if (length == 0) { %>
          <p>No assignments have yet been posted.<br><%
} else { %>
          <table width="100%" class="assignment_table" cellpadding="2" cellspacing="0" border="0">
            <tr>
              <th>&nbsp;</th>
             <% if (isadmin || isgrades || isgroups || isassign) { %>
              <th>&nbsp;</th>
             <% } %>
              <th align="center">Due</th>
             <% if (isadmin || isgrades || isassign) { %>
              <th align="center">Wt.</th>
              <th align="center">Max</th>
             <% } %>    
			 				<% if (isadmin || isgrades) { %>
              <th align="center">High</th>
              <th align="center">Mean</th>
              <th align="center">Med.</th>
              <th align="center">Dev.</th>
              <% } %>
              <th align="center">Status</th>
              <% if (isadmin || isassign) { %>
              <th align="center">&nbsp;</th>
              <% } %>
           </tr><%
  for (int i= 0; i != length; i++) { 
    Element item= (Element) list.item(i); 
    int assignType = Integer.parseInt(item.getAttribute(XMLBuilder.A_ASSIGNTYPE));
    if (assignType == Assignment.SURVEY) continue;
    
    String assignid= item.getAttribute(XMLBuilder.A_ID); 
    String total = item.getAttribute(XMLBuilder.A_TOTALSCORE),
      mean = item.hasAttribute(XMLBuilder.A_STATMEAN) ? item.getAttribute(XMLBuilder.A_STATMEAN) : null, 
      max = item.hasAttribute(XMLBuilder.A_STATMAX) ? item.getAttribute(XMLBuilder.A_STATMAX) : null, 
      median = item.hasAttribute(XMLBuilder.A_STATMEDIAN) ? item.getAttribute(XMLBuilder.A_STATMEDIAN) : null, 
      dev = item.hasAttribute(XMLBuilder.A_STATDEV) ? item.getAttribute(XMLBuilder.A_STATDEV) : null,
      weight = item.hasAttribute(XMLBuilder.A_WEIGHT) ? item.getAttribute(XMLBuilder.A_WEIGHT) : null;
  	int type = item.hasAttribute(XMLBuilder.A_ASSIGNTYPE) ? Integer.parseInt(item.getAttribute(XMLBuilder.A_ASSIGNTYPE)) : -1;     
	String action = AccessController.ACT_ASSIGNADMIN;
	if (type == Assignment.QUIZ) action = AccessController.ACT_QUIZADMIN;
	else if (type == Assignment.SURVEY) action = AccessController.ACT_SURVEYADMIN;
  %>
            <tr>
              <td nowrap>
                <%= item.getAttribute(XMLBuilder.A_NAME) %>
<% 

String assignactbegin1= "<a href=\"?" + AccessController.P_ACTION + "=";
String assignactbegin2= "&amp;" + AccessController.P_ASSIGNID + "="; 
String assignactbegin3= "\">";
String assignactend= "</a>"; 
String openSpace= "&nbsp;("; 
String groupAnchor = "groups"; %>
<%
/* HTML to go around "edit | groups | schedule" links next to each assignment name */
String beginedit = null, endedit = null, begingrade = null, endgrade = null, beginschedule = null, endschedule = null;
if (isadmin || isassign) {
  beginedit=  openSpace + assignactbegin1 + action + assignactbegin2 + assignid + assignactbegin3;
  endedit= assignactend;
} else {
  beginedit = "&nbsp;(<span class=\"noaccess\">";
  endedit = "</span>";
}
if (isadmin || isgrades || isgroups) {
	String groupAction = AccessController.ACT_GRADEASSIGN;
	if (type == Assignment.SURVEY) {
		groupAction = AccessController.ACT_SURVEYRESULT;
		groupAnchor = "result";
	}
	begingrade= assignactbegin1 + groupAction + assignactbegin2 + assignid + assignactbegin3;
	endgrade= assignactend;
} else {
  begingrade= "<span class=\"noaccess\">";
  endgrade= "</span>"; 
}
if (!item.getAttribute(XMLBuilder.A_USESCHEDULE).equals("") && /*(isadmin || isassign)*/isstaff) {
  beginschedule= assignactbegin1 + AccessController.ACT_SCHEDULE + assignactbegin2 + assignid + assignactbegin3;
  endschedule= assignactend+")";
} else {
  beginschedule = "<span class=\"noaccess\">";
  endschedule = "</span>)";
}
%>
              </td>
              <% if (isadmin || isgrades || isgroups || isassign) { %>
              <td nowrap>
                <font size=1>
								<%= beginedit %>edit<%= endedit %> | <%= begingrade + groupAnchor + endgrade %>	| <%= beginschedule %>schedule<%= endschedule %>	
                </font>	
              </td>
              <% } %>
              <td align="center"><%= item.getAttribute(XMLBuilder.A_DUEDATE) %></td>
              <% if (isadmin || isgrades || isassign) { %>
                    <td align="center"><%= weight == null ? "&nbsp" : weight %></td>
                    <td align="center"><%= total %></td>
                   
              <% } %>
              
							<% if (isadmin || isgrades) { %>
              	<% if (mean != null || max != null || median != null || dev != null) { %>
                    <td align="center"><%= max == null ? "&nbsp" : max %></td>                   
                    <td align="center"><%= mean == null ? "&nbsp" : mean %></td>
                    <td align="center"><%= median == null ? "&nbsp" : median %></td>
                    <td align="center"><%= dev == null ? "&nbsp" : dev %></td>
							  <% } else { %>
                    <td align="center" colspan="4">No Statistics Available</td>
                <% } %>
							<% } %>
              <td align="center"><%= item.getAttribute(XMLBuilder.A_STATUS) %></td>
              <% if (isadmin || isassign) { %>
              <td align="center">
         
               <script type="text/javascript">
                rollOver("?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_REMOVEASSIGN %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignid %>","remove");
               </script>

              </td>
              <% } %>
            </tr><%
  } %>
<% if (length > 0 && (isadmin || isgrades)) { %>
			<tr>
				<td style="color: #ab1a2a" nowrap>Total Weighted Score</td>
				<td colspan="2">&nbsp;</td>
				<td>&nbsp;</td>
				<td align="center" style="color: #ab1a2a"><%= course.getAttribute(XMLBuilder.A_MAXTOTALSCORE) %></td>
				<td align="center" style="color: #ab1a2a"><%= course.getAttribute(XMLBuilder.A_HIGHTOTALSCORE) %></td>
				<td align="center" style="color: #ab1a2a"><%= course.getAttribute(XMLBuilder.A_MEANTOTALSCORE) %></td>
				<td align="center" style="color: #ab1a2a"><%= course.getAttribute(XMLBuilder.A_MEDIANTOTALSCORE) %></td>
				<td align="center" style="color: #ab1a2a"><%= course.getAttribute(XMLBuilder.A_STDEVTOTALSCORE) %></td>
				<td colspan="2">&nbsp;</td>				
			</tr>
<% } %>
          </table><%
} %>

<br />
<!-- Surveys -->
	<h2>Surveys</h2>
<% 	Element surveyRoot = (Element) root.getElementsByTagName(XMLBuilder.TAG_SURVEYS).item(0);
	NodeList surveys = surveyRoot.getElementsByTagName(XMLBuilder.TAG_SURVEY); 
	int numSurveys = surveys.getLength();
	if (numSurveys == 0) { %>
	<p>No surveys have yet been posted</p>
<% 	} else { %>
	<table width="100%" class="assignment_table" cellpadding="2" cellspacing="0" border="0">
			<tr>
              <th>&nbsp;</th>
             <% if (isadmin || isgrades || isgroups || isassign) { %>
              <th>&nbsp;</th>
             <% } %>
              <th align="center">Due</th>
              <th align="center">Status</th>
              <% if (isadmin || isassign) { %>
              <th align="center">Responses</th>
              <% } %>
           </tr>
	<%
		for (int j = 0; j < numSurveys; j++) {
			Element survey = (Element) surveys.item(j); 	
			String assignid = survey.getAttribute(XMLBuilder.A_ASSIGNID);
			
			String p_action = AccessController.P_ACTION;
			String p_assignid = AccessController.P_ASSIGNID;
			String act_surveyadmin = AccessController.ACT_QUIZADMIN;	// use the same action as quizzes
			String act_surveyresult = AccessController.ACT_SURVEYRESULT;
			String act_schedule = AccessController.ACT_SCHEDULE;
			boolean displayResults = isadmin || isgrades || isgroups;
			boolean displayEdit = isadmin || isassign;
	%>	
		<tr>
			<td nowrap><%= survey.getAttribute(XMLBuilder.A_NAME) %></td>
			 <% if (isadmin || isgrades || isgroups || isassign) { %>
              <td nowrap>
                <font size=1>                
					&nbsp;(
				<% if (displayEdit) {%>
					<a href='<%= "?" + p_action + "=" + act_surveyadmin + "&amp;" + p_assignid + "=" + assignid %>'>edit</a> | 
				<% } else {%>
					<span class="noaccess">edit</span> |
				<% } %>	
					<a href='<%= "?" + p_action + "=" + act_surveyresult + "&amp;" + p_assignid + "=" + assignid %>'>result</a> |  
					<% if (!survey.getAttribute(XMLBuilder.A_USESCHEDULE).equals("") && isstaff) { %>
					<a href='<%= "?" + p_action + "=" + act_surveyadmin + "&amp;" + p_assignid + "=" + assignid %>'>schedule</a>
                	<% } else { %><span class="noaccess">schedule</span><% } %>)
                </font>	
              </td>
              <% } %>
			<td align="center"><%= survey.getAttribute(XMLBuilder.A_DUEDATE) %></td>
			<td align="center"><%= survey.getAttribute(XMLBuilder.A_STATUS) %></td>
			<td align="center"><%= survey.getAttribute(XMLBuilder.A_COUNT) %></td>
		</tr>
	<% } %> 
	</table>
<% } %>

<% if (hiddenAssigns.getLength() > 0) { %>
  <span id="hiddenassignhead"><a href="#" onclick="show('hiddenassign','Show Removed Assignments','Hide Removed Assignments'); return false;" class="hide">Show Removed Assignments</a></span>
  <div id="hiddenassign" style="display: none">
    <table>
    <% for (int j=0; j < hiddenAssigns.getLength(); j++) { 
        Element ha = (Element) hiddenAssigns.item(j); %>
      <tr><td><%= ha.getAttribute(XMLBuilder.A_ASSIGNNAME) %></td><td>(<%= ha.getAttribute(XMLBuilder.A_NAMESHORT) %>)</td>
      <td><a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_RESTOREASSIGN + "&amp;" + AccessController.P_ASSIGNID + "=" + ha.getAttribute(XMLBuilder.A_ASSIGNID) %>">Restore</a></td>
      </tr>
    <% } %>
    </table>
  </div>
<% } %>
<% if (!isAssignOnly) { %>
          <br></div>
<% } %>
      </div>
