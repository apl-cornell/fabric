<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, java.util.*"%><%
/***************************************************
edit the available timeslots for an assignment

to turn scheduling on/off, see assignment-schedule.jsp
***************************************************/
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getChildNodes().item(0);
Element principal = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_PRINCIPAL);
String userNetID = principal.getAttribute(XMLBuilder.A_NETID);
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
boolean isadmin = course.hasAttribute(XMLBuilder.A_ISADMIN);
boolean isassign = course.hasAttribute(XMLBuilder.A_ISASSIGN);
Element staff= XMLUtil.getFirstChildByTagName(course, XMLBuilder.TAG_STAFF);
Element assignment= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT); 
Element schedule= XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_SCHEDULE);
Collection timeslots = XMLUtil.getChildrenByTagName(schedule, XMLBuilder.TAG_TIMESLOT);
Element unsched = XMLUtil.getFirstChildByTagName(schedule, XMLBuilder.TAG_UNSCHEDULEDGROUPS);
Collection unschedGroups = null;
boolean hasConflicts = false;
if (unsched != null) {
	unschedGroups = XMLUtil.getChildrenByTagName(unsched, XMLBuilder.TAG_GROUP);
}
Element status= (Element)root.getElementsByTagName(XMLBuilder.TAG_STATUS).item(0);
String assignID= assignment.getAttribute(XMLBuilder.A_ASSIGNID);

/* we use the list of scheduled groups a lot, so store in hash map */
java.util.HashMap groupsText = new java.util.HashMap();
if (unschedGroups != null && unschedGroups.size() > 0) {
	Iterator iusg = unschedGroups.iterator();
	Element curGroup = null;
	while (iusg.hasNext()) {
		curGroup = (Element)iusg.next();
		Element usGroupMembers = XMLUtil.getFirstChildByTagName(curGroup, XMLBuilder.TAG_MEMBERS);
		if (usGroupMembers != null) {
			String groupMembersStr = "";
			Collection curMems = XMLUtil.getChildrenByTagName(usGroupMembers, XMLBuilder.TAG_MEMBER);
			Iterator iusgm = curMems.iterator();
			Element curMember = null;
			boolean firstOne = true;
			while (iusgm.hasNext()) {
				curMember = (Element)iusgm.next();
				groupMembersStr += (firstOne ? "" : ", ") + curMember.getAttribute(XMLBuilder.A_NAME) + " (<span class=\"netid\">" + curMember.getAttribute(XMLBuilder.A_NETID) + "</span>)"; 
				firstOne = false;
			}
			String key = curGroup.getAttribute(XMLBuilder.A_ID);
			groupsText.put(key, groupMembersStr);
		}
	} 
} 

%><jsp:include page="../../header.jsp" />
<script type="text/javascript"><jsp:include page="assignscript.js.jsp"/></script>
<link href="calstyle.css" rel="stylesheet" type="text/css">
<script src="CalendarPopup.js" type="text/javascript"></script> <%-- required by formattedtextboxes.js --%>
<script src="datetime.js" type="text/javascript"></script> <%-- required by formattedtextboxes.js --%>
<script src="formattedtextboxes.js" type="text/javascript"></script>

<style type="text/css">
.errorwarning {color: #FF0000; font-weight: bold; }

.tslist_internal {padding: 1px; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd; border-left: none; border-right: none}
.tslist_extleft {padding: 1px; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd; border-left: 1px solid #ddd; border-right: none}
.tslist_extright {padding: 1px; border-top: 1px solid #ddd; border-bottom: 1px solid #ddd; border-left: none; border-right: 1px solid #ddd}
</style>

<jsp:include page="../../header-page.jsp" />
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
<jsp:include page="../navbar.jsp"/>
  <td id="course_page_container">
  <div id="course_page">
  	<jsp:include page="../../problem-report.jsp"/>
  	<span class="assignment_title"><%= assignID.equals("0") ? "New Assignment" : assignment.getAttribute(XMLBuilder.A_NAME) %></span>
	  	<br><br>
  	<div class="assignment_left">
  	<h2>Schedule</h2>
	<p>
	<dl>
		<dt>Time slot duration:</dt>
		<dd><%= schedule.getAttribute(XMLBuilder.A_TSDURATIONSTR) %> minutes</dd>
		<dt>Time slot group limit:</dt>
		<dd><%= schedule.getAttribute(XMLBuilder.A_TSMAXGROUPS) %></dd>
	</dl>
	<span class="example">The group limit is the maximum number of groups that can sign up under a specific time and staff member.</span>
	<dl>
		<dt>Schedule lock time:</dt><%
		if (schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE).equals("")) /* no deadline */
		{%>
			<dd>None</dd><%
		}
		else
		{%>
			<dd><%= schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE) %> <%= schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKTIME) %> <%= schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKAMPM) %></dd><%
		}%>
	</dl>
	<span class="example">After this time students will not be able to change their timeslots.</span>
	</p><br><p>
		<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_PRINTSCHEDULE %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>" target="_blank">Printer-Friendly Listing</a>
	</p>
	</div>
	
	<div class="assignment_left">
<%	if (isadmin || isassign)
	{%>
	<h2>Manage Time Slots</h2>
<%	}
	else
	{%>
	<h2>Time Slots</h2>
<%	}%>
	<% boolean areSlots = false; %>
	<span class="example"><b>(X/Y taken)</b> means X out of the group limit of Y groups are signed up.</span>
  	<form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_TIMESLOTSDELETE %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>" method="post" enctype="multipart/form-data">
	<table cellspacing="0" border="0" width="100%">
	<%	/* Iterate over all timeslots in XML tree and output to screen */
		Iterator its = timeslots.iterator();
		Element curTimeSlot = null;
		String timeSlotOptions = "<option value ='-1'>Unassigned</option>\n";
		while(its.hasNext()) {
			curTimeSlot = (Element)its.next();
			boolean canEditSlot = isadmin || isassign || curTimeSlot.getAttribute(XMLBuilder.A_TSEDITRIGHTS).equals("true");
			Collection tsGroups = XMLUtil.getChildrenByTagName(curTimeSlot, XMLBuilder.TAG_GROUP);
			/* only if user has admin or edit privileges */
			if (canEditSlot)
			{
				areSlots = true;
				String location = curTimeSlot.getAttribute(XMLBuilder.A_TSLOCATION); %>
			<tr>
				<%-- create checkbox and list timeslot info --%>
				<td align="center" class="tslist_extleft">
				<% if (curTimeSlot.hasAttribute(XMLBuilder.A_TSCONFLICTING)) {
					hasConflicts = true; %>
					<img src="images/warning.gif" height="15px" alt="(conflict)">
				<% } %>
				</td>
				<td align="center" class="tslist_internal">
					<input type="checkbox" name="<%= AccessController.P_DELETETIMESLOT %><%= curTimeSlot.getAttribute(XMLBuilder.A_TSID) %>" <%= (isadmin || isassign) ? "" : "disabled" %>>
				</td>
				<td align="left" class="tslist_internal">
					<span class="personname"><%= curTimeSlot.getAttribute(XMLBuilder.A_TSNAME) %></span>
				</td>
				<td align="right" class="tslist_internal">
					<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTDATE) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTAMPM) %>
				</td>
				<td align="left" class="tslist_internal">
					- <%--<%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDDATE) %> --%><%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDTIME) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDAMPM) %>
				</td>
				<td align="left" class="tslist_internal">
					<%= (location != null && !location.equals("")) ? "(" + location + ")" : "" %>
				</td>
				<td align="center" class="tslist_internal">
					<%= "(" + tsGroups.size() + "/" + schedule.getAttribute(XMLBuilder.A_TSMAXGROUPS) + " taken)" %>
				</td>
				<% if (isadmin || isassign)
				{%>
				<td align="left" class="tslist_internal">
					<%= "(" + curTimeSlot.getAttribute(XMLBuilder.A_TSSTAFFNAME) + ")" %>
				</td>
				<% } %>
				<td align="left" class="tslist_extright">
				<div id="timeslot_<%= curTimeSlot.getAttribute(XMLBuilder.A_TSID) %>" class="showhide">
				<%-- cycle through XML to list groups and members --%>
				<%	Element tsGroup = null;
					if (tsGroups.size() > 0) {
						Iterator ig = tsGroups.iterator();
						while (ig.hasNext()) {
							tsGroup = (Element)ig.next();
							Element tsGroupMembers = XMLUtil.getFirstChildByTagName(tsGroup, XMLBuilder.TAG_MEMBERS);
							if (tsGroupMembers != null) {
								Collection tsMembers = XMLUtil.getChildrenByTagName(tsGroupMembers, XMLBuilder.TAG_MEMBER);
								Iterator igm = tsMembers.iterator();
								Element curMember = null; 
								boolean firstOne = true; %>
								<span class="indented">
							<%	while (igm.hasNext()) {
									curMember = (Element)igm.next(); %>
									<%= firstOne ? "" : ", " %>
									<%= curMember.getAttribute(XMLBuilder.A_NAME) %> <span class="netid">(<%= curMember.getAttribute(XMLBuilder.A_NETID) %>)</span>
									<% firstOne = false; %>
							<%	} %>
								<%-- generate link to remove this group --%>
								<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_TIMESLOTUNASSIGN %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>&amp;<%= AccessController.P_TIMESLOTID %>=<%= curTimeSlot.getAttribute(XMLBuilder.A_TSID) %>&amp;<%= AccessController.P_GROUPID %>=<%= tsGroup.getAttribute(XMLBuilder.A_ID) %>">remove</a> 
								</span>
						<%	}
						}
					} 
					
					String maxGroupsStr = schedule.getAttribute(XMLBuilder.A_TSMAXGROUPS);
					int maxGroups = 0;
					try { maxGroups = Integer.parseInt(maxGroupsStr); } catch (Exception e) {}
					if (canEditSlot)
					{
						// if the population is under the max and groups are unscheduled, provide link to add
						if (tsGroups.size() < maxGroups && groupsText.size() > 0) { 
							timeSlotOptions += "<option value='" + curTimeSlot.getAttribute(XMLBuilder.A_TSID) + "'>" + curTimeSlot.getAttribute(XMLBuilder.A_TSNAME) + " - " + curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTDATE) + " " + curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTTIME) + " " + curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTAMPM) + "</option>\n";
						%>
						<%-- use javascript pop-up menu to support adding a group --%>
						<script type="text/javascript" src="../../popupmenu.js"></script>
						<script type="text/javascript">
						var unschedgroups_<%= curTimeSlot.getAttribute(XMLBuilder.A_TSID) %> = new Array();
						<%	if (groupsText.size() > 0) {
							Set groupsTextKeys = groupsText.keySet();
							Iterator igtk = groupsTextKeys.iterator();
							String curKey = null;
							int i = 0;
							while (igtk.hasNext()) {
								curKey = (String)igtk.next(); %>
							unschedgroups_<%= curTimeSlot.getAttribute(XMLBuilder.A_TSID) %>[0] <%= i==0 ? "" : "+" %>= '<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_TIMESLOTASSIGN + "&amp;" + AccessController.P_ASSIGNID + "=" + assignID + "&amp;" + AccessController.P_GROUPID + "=" + curKey + "&amp;" + AccessController.P_TIMESLOTID + "=" + curTimeSlot.getAttribute(XMLBuilder.A_TSID) %>">&#149;&nbsp;<%= (String)groupsText.get(curKey) %></a>';
						<%		i++;
							}
						}%>
						</script>
<%					}%>
						<span class="indented"><a href="#" onclick="showmenu(event, unschedgroups_<%= curTimeSlot.getAttribute(XMLBuilder.A_TSID) %>[0]); return false;" onMouseout="delayhidemenu();">Add a group</a></span>
				<%	} %> 
				</div>
				</td>
			</tr>
<%			}
		} %>
  	<tr>
  		<td></td>
  		<td colspan="8" align="left">
<%		if (areSlots && (isadmin || isassign))
  		{%>
	  		<input type="submit" value="Remove checked time slot(s)">
<%		}
		else
		{%>
		<%= (isadmin || isassign) ? "No time slots currently exist." : "You do not have rights to edit any existing time slots, or none exist." %>
<%		}%>
  		</td>
  	</tr>
	</table>
	<% if (hasConflicts) { %>
		<img src="images/warning.gif" height="15px" alt="(confict)">&nbsp;&nbsp;&nbsp;Denotes a conflicting time slot
  	<% } %>
  	</form>
  	</div>
<%	if (isadmin || isassign)
	{%>
  	<div class="assignment_left">
  	<h2>Add Time Slots</h2>
  	<form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_TIMESLOTSCREATE %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>" method="post" enctype="multipart/form-data" onSubmit="return validateAllFormattedTextboxes();">
	<table class="assignment_table" cellpadding="2" cellspacing="0">
    	<tr>
    		<td width="20%">Name:</td>
    		<td><input type="text" size="25" name="<%= AccessController.P_NEWTSNAME %>"></td>
    	</tr>
    	<tr>
    		<td>Location:</td>
    		<td><input type="text" size="25" name="<%= AccessController.P_NEWTSLOCATION %>"></td>
    	</tr>
    	<tr>
    		<td>Start:</td>
    		<td>
    			<div class="dateblock">
		         <script type="text/javascript">
		         var calpop= new CalendarPopup();
		         </script>
		          
		         <input id="datebox" type="text" size="17" name="<%= AccessController.P_NEWTSSTARTDATE %>" value="<%= assignment.getAttribute(XMLBuilder.A_DUEDATE) %>">
		         <script language="javascript">
					registerDateFormatTextbox('datebox');
					</script>
			      <a href="#" id="datelink" name="datelink" onClick="calpop.select(getElementById('datebox'), 'datelink', 'MMM d, yyyy'); return false;" name="datebox" id="datebox">
		            <img class="calicon" src="../images/cal.gif" alt="Select" width="16px" height="16px">
		          </a>
			      <input type="text" size="5" name="<%= AccessController.P_NEWTSSTARTTIME %>" value="10:00">
			      <select name="<%= AccessController.P_NEWTSSTARTAMPM %>">
			        <option>AM</option>
			        <option>PM</option>
			      </select>
			      <span class="example">e.g. <%= root.getAttribute(XMLBuilder.A_EXAMPLEDATE) %></span>
	    		</div>
       		</td>
    	</tr>
    	<tr>
    		<td>Count:</td>
    		<td>
    			<input type="text" size="5" name="<%= AccessController.P_NEWTSMULTIPLICITY %>" value="1">
    			<br><span class="example">number of consecutive slots</span>
    		</td>
    	</tr>

    	<% if (isadmin || isassign) { %>
    	<tr>
    		<td>Staff:</td>
    		<%-- create drop-down of available course staff --%>
    		<td><select name="<%= AccessController.P_NEWTSSTAFF %>">
					<% 	Collection staffs = XMLUtil.getChildrenByTagName(staff, XMLBuilder.TAG_ITEM);  
						Iterator is = staffs.iterator();
						Element item = null;
						while (is.hasNext()) { 
							item = (Element)is.next(); %>
					<option value="<%= item.getAttribute(XMLBuilder.A_NETID) %>"><%= item.getAttribute(XMLBuilder.A_NAME) %></option>
					<% } %>			
  	  	  		</select></td>
  	  	 </tr>
  	  	 <% } else { %>
				<input type="hidden" name="<%= AccessController.P_NEWTSSTAFF %>" value="<%= userNetID  %>">
		 <% } %>
  	  	 <tr>
  	  	 	<td colspan="2"><input type="submit" value="Add time slot(s)"></td>
  	  	 </tr>
  	</table>  	 	
  	</form>
  	</div>
<%	}%>
  	
<%	if (isadmin || isassign)
	{
  		if (groupsText.size() > 0)
  		{%>
  	  	<div class="assignment_left">
  	<h2>Unscheduled Groups</h2>
  	<form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_TIMESLOTSUPDATE %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>" method="post">
	
	<table class="assignment_table" cellspacing="0" border="0">
	<% 	// pull unscheduled groups from hashtable and output
			Set groupsTextKeys = groupsText.keySet();
			Iterator igtk = groupsTextKeys.iterator();
			String curKey = null;
			while (igtk.hasNext()) {
				curKey = (String)igtk.next(); %>
				<tr>
					<td><%= (String)groupsText.get(curKey) %></td>
					<td>
						<select name="<%=curKey%>">
							<%= timeSlotOptions %>
						</select>
					</td>
				</tr>
<%			} %>
	</table>
	<input type="submit" value="Update time slot(s)">  	
	</form>
  	</div>
<%		}
	}%>
  </div>
  </td>
  <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
</tr>
<jsp:include page="../../footer.jsp"/>