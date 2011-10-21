<%@page language="java" import="org.w3c.dom.*, cms.www.*, cms.model.*, cms.www.xml.*"%><%
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getFirstChild();
Element assign= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_ASSIGNMENT());
Element assignment= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_ASSIGNMENT());
Element groupsnode= (Element)root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_GROUPS()).item(0);
NodeList groups= groupsnode.getChildNodes();
NodeList graders= root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADER());
int numgroups= groupsnode.getChildNodes().getLength();
Element probsnode= (Element)root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_SUBPROBS()).item(0);
NodeList probs= probsnode.getChildNodes();
int numprobs= probs.getLength();
Element course = XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_COURSE());
int assignType = Integer.parseInt(assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE()));
boolean isAssignment = (assignType == Assignment._Static._Proxy.$instance.get$ASSIGNMENT());
boolean isAdmin = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISADMIN());
boolean isGrades = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISGRADES());
boolean isGroups = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISGROUPS());
String assignid= assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ID());
boolean showGradeMsg = root.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_GRADEMSG());
%>
<span class="assignment_title"><%= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %></span>
<br><br><%
          if (showGradeMsg) {%>
<div class="noticebox" style="font-weight:bold">
	Please select whom to grade.<br/>
	For a single student group, click "Grade" in the rightmost cell of their row.<br/>
	For multiple student groups, select their checkboxes (or choose a preset in the "Click a link to change selection" box), then click "Grade" at the top.<br/>
	Clicking a NetID will show all gradable assignments for the student.<br/>
</div>
<br><br>
<% }%>
<table cellpadding="0" cellspacing="0">
	<tr><%
String mingroup= assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_MINGROUP());
String maxgroup= assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_MAXGROUP());
String groupstring = null;
if (assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNEDGROUPS())) { groupstring= "Staff-created"; }
else if (mingroup.equals(maxgroup)) {
	if ("1".equals(mingroup)){
		groupstring= "1 student"; 
	} else { groupstring= mingroup + " students"; }
}
else { groupstring= mingroup + " to " + maxgroup + " students"; } %>
		<td align="left" valign="top">
			<b>
			Group Size:
			<br>Groups:
<%	if (isAdmin || isGrades) {%>
			<br>Submissions:
			<br>Partial:
			<br>Complete:
<%	}%>
			</b>
		</td>
		<td align="left" valign="top" style="padding-right: 10px">
			<span style="white-space: nowrap"><%= groupstring %></span>
			<br><%= String.valueOf(numgroups) %>
<%	if (isAdmin || isGrades) {%>
			<br><%= groupsnode.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBMISSIONCOUNT()) %>
			<br><%= groupsnode.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_PARTIAL()) %>
			<br><%= groupsnode.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COMPLETE()) %>
<%	}%>
		</td>
<%	if (isAdmin || isGrades) { %>
		<td rowspan="2" style="padding: 0px">
			<table border="0" cellspacing="0">
				<tr>
				<td align="center" valign="top" style="border: solid; border-color: #aaaaaa; border-width: 1px 1px 1px 1px; padding: 5px">
				<center><b>Click a link to change selection:</b></center>
				<hr width="98%" size="1" style="color: #888888">
				<div>
					<a href="javascript:selectAll(<%= assignid %>);">All</a> 
					| <a href="javascript:selectNone(<%= assignid %>);">None</a> 
					| <a href="javascript:selectUngraded(<%= assignid %>);">Ungraded</a> 
					| <a href="javascript:selectPending(<%= assignid %>);">Regrade Pending</a>
				</div>
				<div style="border: solid 1px #dddddd; margin: 5px 0px; padding: 5px; white-space: nowrap">
					<a href="javascript:selectAssignedTo('select_grader_select', 'select_prob_select', '<%= assignid %>', <%= numprobs %>);">Assigned to</a>
						<select id="select_grader_select">
							<optgroup label="Select Grader">
								<option>&lt;unassigned&gt;</option><%
								for (int i= 0; i != graders.getLength(); i++) {
								  	Element grader= (Element)graders.item(i);
								  	String graderInfo= grader.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_LASTNAME()) + ", " + grader.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FIRSTNAME()) + " ("+grader.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID())+")"; %> 
									<option value="<%=grader.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID())%>"><%= graderInfo%></option><%
								}%>
							</optgroup>
						</select><%
		if (numprobs >= 1) {/* there's at least one subproblem */ %>
						<br>for
						<select id="select_prob_select" style="width:15em" size="2" multiple>
							<optgroup label="Select Problem(s)">
<%			for (int i= 0; i != numprobs; i++) {
				Element prob= (Element)probs.item(i);%>
								<option value="<%= i %>"><%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %></option>
<%			}%>
							</optgroup>
						</select><small><br>multiple selections allowed (hold ctrl + click)</small><%
		}%>
					</div>
				<div nowrap>
						<a href="javascript:selectRange(<%= assignid %>, document.getElementById('firstRange').value,document.getElementById('lastRange').value,<%= numgroups %>);">Range:</a>
		            <input type="text" size="2" maxlength="4" style="text-align: right" value="<%= Math.min(1, groups.getLength()) %>" id="firstRange" />
		            <input type="text" size="2" maxlength="4" style="text-align: right" value="<%= groups.getLength() %>" id="lastRange" />
		        </div>
		</td>
		<td rowspan="2" align="center" valign="top" style="border: solid; border-color: #aaaaaa; border-width: 1px 1px 1px 0px; padding: 5px">
			<center><b>Apply to Selected Groups:</b></center>
			<hr width="98%" size="1" style="color: #888888">
			<input type="submit" style="width: 7em" value="<%= AccessController.GA_GRADE %>" id="grade" name="<%= AccessController.P_SUBMIT %>"\>
			&nbsp;<input type="submit" style="width: 7em" value="<%= AccessController.GA_FILES %>" id="files" name="<%= AccessController.P_SUBMIT %>"\>
			<input type="submit" style="width: 7em" value="<%= AccessController.GA_EMAIL %>" id="email" name="<%= AccessController.P_SUBMIT %>"\>
			<br>
<%		if (isAdmin) {%>
			<div id="assign_grader_div" style="border: solid 1px #dddddd; padding: 5px; margin-top: 5px">
<%			if (numprobs > 1) {%>
				<select id="assign_prob_select" name="<%= AccessController.P_ASSIGNPROBNAME %>" style="width: 15em">
					<optgroup label="Select Problem">
						<option>&lt;All Parts&gt;</option>
<%				for (int i= 0; i != numprobs; i++) {
					Element prob= (Element)probs.item(i);%>
						<option><%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %></option>
<%				}%>
					</optgroup>
				</select>
<%			} else if (numprobs == 1) {
				Element prob = (Element)probs.item(0);%>
				<input type="hidden" name="<%= AccessController.P_ASSIGNPROBNAME %>" value="<%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %>">
<% 			} else {%>
				<input type="hidden" name="<%= AccessController.P_ASSIGNPROBNAME %>" value="&lt;All Parts&gt;">
<%			} %>
				
				<select id="assign_grader_select" name="<%= AccessController.P_ASSIGNGRADER %>">
					<optgroup label="Select Grader">
						<option>&lt;unassigned&gt;</option>
<%			for (int i= 0; i != graders.getLength(); i++) {
				Element grader= (Element)graders.item(i);
				String graderInfo= grader.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_LASTNAME()) + ", " + grader.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FIRSTNAME()) + " ("+grader.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID())+")"; %> 
		            <option value="<%=grader.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID())%>"><%= graderInfo%></option>
<%			}%>
					</optgroup>
				</select>
				<br>
				<input style="width: 15em" onClick="return checkSelection('assign_grader_select', 'assign_prob_select');" type="submit" value="Assign Grader" id="submit" name="<%= AccessController.P_SUBMIT %>">
			</div>
<%		} /* admin */ %>
		</td>
		</tr>
		<tr>
			<td colspan="2" align="center" style="border: solid; border-color: #aaaaaa; border-width: 0px 1px 1px 1px; padding: 5px">
				<div id="subgrades" style="width: 100%">
					<a class="button" id="show_assignedto" name="assignedTo" href="javascript:showHideAssignedTo();">(Show Assigned To)</a>
<%		if (numprobs > 1) {%>
					&nbsp;|&nbsp;
					<a class="button" href="#" onClick="showProblemGrades(<%= assignid %>); return false;">(Hide Problem Scores)</a>
<%		}%>
				</div>
			</td>
		</tr>
		</table>
		</td>
<% } %><%-- end isAdmin || isGrades --%>
	</tr>
	<tr>
		<td colspan="2" align="left">
			<div style="margin-right: 10px">
				<br><img src="images/tag_orange.gif" alt="">Regraded
				<br><img src="images/tag_red.gif" alt="">Regrade pending
				<br><img src="images/warning.gif" height="15px" alt="">Over max score
				<br><img src="staff/images/late.bmp" height="12px" alt="">&nbsp;Late submissions
				<br><font size="4">~</font>&nbsp;&nbsp;Averaged among group members
			</div>
		</td>
	</tr>
</table>
<br>
<% if (isAdmin || isGroups) {%>
   <% if (isAssignment) {%>
<div class="assignment_left">
	<h2>
		Group Management
		<span id="groupmgmthead">
			<a id="groupmgmtshowhide" href="#" onClick="show('groupmgmt', ' (show)', ' (hide)'); return false;" class = "hide"> (show)</a>
		</span>
	</h2>
	<div id="groupmgmt" class="showhide" style="display:none">
		<input type="submit" style="width:6em" value="<%= AccessController.GA_GROUP %>" id="group" name="<%= AccessController.P_SUBMIT %>">
		 or <input type="submit" style="width:6em" value="<%= AccessController.GA_UNGROUP %>" id="ungroup" name="<%= AccessController.P_SUBMIT %>">
		selected students/groups
		<br>Or enter NetIDs delimited by spaces or commas:
		<input type="text" size="12" id="netidlist" name="<%= AccessController.P_NETIDLIST %>">
		&nbsp;<input type="submit" value="<%= AccessController.GA_CREATEGROUP %>" id="create" name="<%= AccessController.P_SUBMIT %>">
	<% 	if (!isAdmin && !isGrades) { %><%-- Add selection links for non-admins --%>
			<br>
			Select: <a href="javascript:selectAll(<%= assignid %>);">All</a> 
				| <a href="javascript:selectNone(<%= assignid %>);">None</a>
				| <a href="javascript:selectRange(<%= assignid %>, document.getElementById('firstRange').value,document.getElementById('lastRange').value,<%= numgroups %>);">Range:</a>
	            <input type="text" size="2" maxlength="4" style="text-align: right" value="<%= Math.min(1, groups.getLength()) %>" id="firstRange" />
	            <input type="text" size="2" maxlength="4" style="text-align: right" value="<%= groups.getLength() %>" id="lastRange" />
	<% 	} %>
	</div>

</div>
   <% } %> <%-- End of if isAssignment --%>
<%	}%>
