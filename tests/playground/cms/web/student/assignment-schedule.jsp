<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.model.*, java.util.*" %><%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
Element assignment = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
Element schedule = XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_SCHEDULE);
Element group = XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_GROUP);
String groupid= (group == null) ? "" : group.getAttribute(XMLBuilder.A_ID);
/* max number of students in a group */
int maxgroup= Integer.parseInt(assignment.getAttribute(XMLBuilder.A_MAXGROUP));
String status = assignment.getAttribute(XMLBuilder.A_STATUS);
boolean enableGroups = status.equals(Assignment.OPEN) && !assignment.hasAttribute(XMLBuilder.A_PASTDUE); 
boolean isScheduled = group.hasAttribute(XMLBuilder.A_ISSCHEDULED);
String tsid = "";
if (isScheduled)
{
	Element timeslot= XMLUtil.getFirstChildByTagName(group, XMLBuilder.TAG_TIMESLOT);
	tsid = timeslot.getAttribute(XMLBuilder.A_TSID);
}
int maxGroups = 0; /* max number of groups per timeslot */
try
{
	maxGroups = Integer.parseInt(schedule.getAttribute(XMLBuilder.A_TSMAXGROUPS));
}
catch (Exception e) {}
Collection timeslots = XMLUtil.getChildrenByTagName(schedule, XMLBuilder.TAG_TIMESLOT);%>
<style type="text/css">
.noaccess {color: #999999}

.tslist_internal {padding: 1px; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd; border-left: none; border-right: none}
.tslist_extleft {padding: 1px; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd; border-left: 1px solid #ddd; border-right: none}
.tslist_extright {padding: 1px; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd; border-left: none; border-right: 1px solid #ddd}
</style>
<%
boolean scheduleLocked = schedule.hasAttribute(XMLBuilder.A_SCHEDULE_LOCKED);
if (scheduleLocked)
{%>
<h2>
  <a name="schedule"></a>
  Time slots
  <span id="scheduleshead">
    <a class="hide" href="#" onClick="hide('schedules', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>
<div id="schedules" class="showhide">
<b>This schedule is locked. Your assigned timeslot, if any, is indicated in red; other assigned slots are shown in gray.</b>
<table class="assignment_table" cellspacing="0" border="0">
<%-- display all currently available time slots and indicate if one is selected --%>
<%	Iterator its = timeslots.iterator();
	Element curTimeSlot = null;
	while (its.hasNext()) {
		curTimeSlot = (Element)its.next(); %>
		<%	String tsPopStr = curTimeSlot.getAttribute(XMLBuilder.A_TSPOPULATION);
			int tsPop = 0;
			try {
				tsPop = Integer.parseInt(tsPopStr);
			} catch (Exception e) {}
			/* if timeslot is selected or if it is not full, display as an option */
			String location = curTimeSlot.getAttribute(XMLBuilder.A_TSLOCATION);
			if (location == null) location = "";
			if (tsPop < maxGroups || (isScheduled && tsid.equals(curTimeSlot.getAttribute(XMLBuilder.A_TSID)))) { %>
			<tr>
				<td width="2%" align="right" class="tslist_extleft">
				<% if (tsid.equals(curTimeSlot.getAttribute(XMLBuilder.A_TSID)))
					{%>
						&rArr;<% /* right arrow */
					}%>
				</td>
				<%	String styleStr = tsid.equals(curTimeSlot.getAttribute(XMLBuilder.A_TSID)) ? "style=\"color: red;\"" : ""; %>
				<td <%= styleStr %> align="left" class="tslist_internal">
					<i><%= curTimeSlot.getAttribute(XMLBuilder.A_TSNAME) %></i>
				</td>
				<td <%= styleStr %> align="right" class="tslist_internal">
					<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTDATE) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTAMPM) %>
				</td>
				<td <%= styleStr %> align="left" class="tslist_internal">
					- <%--<%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDDATE) %>--%> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDAMPM) %>
				</td>
				<td <%= styleStr %> align="left" class="tslist_internal">
					<%= location.equals("") ? "" : "(" + location + ")" %>
				</td>
				<td <%= styleStr %> align="left" class="tslist_extright">
					(<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTAFFNAME) %>)
				</td>
		<%	}
			else
			{ /* any timeslots that are full are greyed out */ %>
			<tr class="noaccess">
				<td width="2%" align="right" class="tslist_extleft">
				</td>
				<td width="2%" align="left" class="tslist_extleft">
					<i><%= curTimeSlot.getAttribute(XMLBuilder.A_TSNAME) %></i>
				</td>
				<td align="right" class="tslist_extleft">
					<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTDATE) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTAMPM) %>
				</td>
				<td align="left" class="tslist_internal">
					- <%--<%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDDATE) %>--%> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDAMPM) %>
				</td>
				<td align="left" class="tslist_internal">
					<%= location.equals("") ? "" : "(" + location + ")" %>
				</td>
				<td align="left" class="tslist_extright">
					(<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTAFFNAME) %>)
				</td>
				<%-- </span> --%>
		<%	} %>
		</tr>
<%	} %>
</table><%
} /* end "if schedule is locked" display */
else
{%>
<h2>
  <a name="schedule"></a>
  Available time slots
  <span id="scheduleshead">
    <a class="hide" href="#" onClick="hide('schedules', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>
<div id="schedules" class="showhide"><%
	if (!schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE).equals("")) /* there is a deadline */
	{%>
	<span><b>This schedule will be locked at <%= schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE) %> <%= schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKTIME) %> <%= schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKAMPM) %>: you will not be able to change your timeslot after that time.</b></span><%
	}%>
<form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_TIMESLOTSELECT %>&<%= AccessController.P_GROUPID %>=<%= groupid %>&<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>" method="post" enctype="multipart/form-data">
<table cellspacing="0" border="0" width="100%">
<%-- display all currently available time slots and indicate if one is selected --%>
<%	Iterator its = timeslots.iterator();
	Element curTimeSlot = null;
	while (its.hasNext()) {
		curTimeSlot = (Element)its.next(); %>
		<%	String tsPopStr = curTimeSlot.getAttribute(XMLBuilder.A_TSPOPULATION);
			int tsPop = 0;
			try {
				tsPop = Integer.parseInt(tsPopStr);
			} catch (Exception e) {}
			/* if timeslot is selected or if it is not full, display as an option */
			String location = curTimeSlot.getAttribute(XMLBuilder.A_TSLOCATION);
			location = location == null ? "" : location;
			if (tsPop < maxGroups || (isScheduled && tsid.equals(curTimeSlot.getAttribute(XMLBuilder.A_TSID))))
			{%>
			<tr>
				<td width="2%" align="right" class="tslist_extleft">
					<input type="radio" name="<%= AccessController.P_TIMESLOTID %>" value="<%= curTimeSlot.getAttribute(XMLBuilder.A_TSID) %>"<%= (tsid.equals(curTimeSlot.getAttribute(XMLBuilder.A_TSID))) ? " checked" : "" %>>
				</td>
				<%	String styleStr = tsid.equals(curTimeSlot.getAttribute(XMLBuilder.A_TSID)) ? "style=\"color: red;\"" : ""; %>
				<td <%= styleStr %> align="left" class="tslist_internal">
					<i><%= curTimeSlot.getAttribute(XMLBuilder.A_TSNAME) %></i>
				</td>
				<td <%= styleStr %> align="right" class="tslist_internal">
					<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTDATE) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTAMPM) %>
				</td>
				<td <%= styleStr %> align="left" class="tslist_internal">
					- <%--<%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDDATE) %>--%> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDAMPM) %>
				</td>
				<td <%= styleStr %> align="left" class="tslist_internal">
					<%= location.equals("") ? "" : "(" + location + ")" %>
				</td>
				<td <%= styleStr %> align="left" class="tslist_extright">
					(<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTAFFNAME) %>)
				</td>
		<%	} else { /* any timeslots that are full are greyed out */ %>
			<tr class="noaccess">
				<td width="2%" align="center" class="tslist_extleft">
					<input type="radio" disabled <%= tsid.equals(curTimeSlot.getAttribute(XMLBuilder.A_TSID)) ? "selected" : "" %>>
				</td>
				<td align="left" class="tslist_internal">
					<i><%= curTimeSlot.getAttribute(XMLBuilder.A_TSNAME) %></i>
				</td>
				<td align="right" class="tslist_internal">
					<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTDATE) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTAMPM) %>
				</td>
				<td align="left" class="tslist_internal">
					- <%--<%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDDATE) %>--%> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDAMPM) %>
				</td>
				<td align="left" class="tslist_internal">
					<%= location.equals("") ? "" : "(" + location + ")" %>
				</td>
				<td align="left" class="tslist_extright">
					(<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTAFFNAME) %>)
				</td>
				<%--</span></td>--%>
		<%	} %>
		</tr>
		
<%	} %>
   <tr>
    	<td>&nbsp;</td>
	   <%-- submit button for time slot select form --%>
	   <td colspan="5">
	   	<input type="submit" value="Assign/Change time slot">
	   	</form>
	   </td>
	</tr>
	<%-- clear timeslot is another form that unselects the selection --%>
	<%-- Unselect takes groupid and assignmentid --%>
	<tr>
		<td>&nbsp;</td>
		<td colspan="5">
			<form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_TIMESLOTUNSELECT %>&amp;<%= AccessController.P_GROUPID %>=<%= groupid %>&<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>" method="post" enctype="multipart/form-data">
			<input type="submit" value="Clear Time Slot">
			</form>
		</td>
	</tr>
</table><%
} /* end "if schedule not locked" display */ %>
</div>
