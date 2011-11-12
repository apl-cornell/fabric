<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, java.util.*"%><%
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getChildNodes().item(0);
Element principal = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_PRINCIPAL);
String userNetID = principal.getAttribute(XMLBuilder.A_NETID);
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
Element staff= XMLUtil.getFirstChildByTagName(course, XMLBuilder.TAG_STAFF);
Element assignment= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT); 
Element schedule= XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_SCHEDULE);
boolean isadmin = course.hasAttribute(XMLBuilder.A_ISADMIN);
boolean isassign = course.hasAttribute(XMLBuilder.A_ISASSIGN);
Collection timeslots = XMLUtil.getChildrenByTagName(schedule, XMLBuilder.TAG_TIMESLOT);
Element unsched = XMLUtil.getFirstChildByTagName(schedule, XMLBuilder.TAG_UNSCHEDULEDGROUPS);
Collection unschedGroups = null;
if (unsched != null) {
	unschedGroups = XMLUtil.getChildrenByTagName(unsched, XMLBuilder.TAG_GROUP);
}
Element status= (Element)root.getElementsByTagName(XMLBuilder.TAG_STATUS).item(0);
String assignID= assignment.getAttribute(XMLBuilder.A_ASSIGNID);
%><jsp:include page="../../header.jsp" />
<script type="text/javascript"><jsp:include page="assignscript.js.jsp"/></script>
<jsp:include page="../../header-page.jsp" />
<div id="course_wrapper">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
  <td id="course_page_container">
  <div id="course_page">
  	<span class="assignment_title"><%= assignID.equals("0") ? "New Assignment" : assignment.getAttribute(XMLBuilder.A_NAME) %></span><%
		if (!"0".equals(assignID)) { %>
        <%
        } %>
	  	
  	<div class="assignment_left">
	<p>
		Time slot duration: <%= schedule.getAttribute(XMLBuilder.A_TSDURATIONSTR) %> minutes.
		Time slot group limit: <%= schedule.getAttribute(XMLBuilder.A_TSMAXGROUPS) %>.
	</p>
	
	<table class="assignment_table" cellspacing="0" border="0">
	<%	/* Iterate over all timeslots in XML tree and output to screen */
		Iterator its = timeslots.iterator();
		int numSlotsShown = 0;
		Element curTimeSlot = null;
		while(its.hasNext())
		{
			curTimeSlot = (Element)its.next();
			if (isadmin || isassign || curTimeSlot.getAttribute(XMLBuilder.A_TSSTAFF).trim().equals(userNetID))
			{
				numSlotsShown++;%>
		<tr>
			<td>
				<%-- list timeslot info --%>
				<span class="personname"><%= curTimeSlot.getAttribute(XMLBuilder.A_TSNAME) %></span> 
				<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTDATE) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTTIME) %><%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTAMPM) %> - 
				<%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDDATE) %> <%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDTIME) %><%= curTimeSlot.getAttribute(XMLBuilder.A_TSENDAMPM) %> @ 
				<%= curTimeSlot.getAttribute(XMLBuilder.A_TSLOCATION) %> (<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTAFFNAME) %>)
				<div id="timeslot_<%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTDATE) %><%= curTimeSlot.getAttribute(XMLBuilder.A_TSSTARTTIME) %>_" class="showhide">
				<%-- cycle through XML to list groups and members --%>
<%				Collection tsGroups = XMLUtil.getChildrenByTagName(curTimeSlot, XMLBuilder.TAG_GROUP);
				Element tsGroup = null;
				if (tsGroups.size() > 0)
				{
					Iterator ig = tsGroups.iterator();
					while (ig.hasNext())
					{
						tsGroup = (Element)ig.next();
						Element tsGroupMembers = XMLUtil.getFirstChildByTagName(tsGroup, XMLBuilder.TAG_MEMBERS);
						if (tsGroupMembers != null)
						{
							Collection tsMembers = XMLUtil.getChildrenByTagName(tsGroupMembers, XMLBuilder.TAG_MEMBER);
							Iterator igm = tsMembers.iterator();
							Element curMember = null; 
							boolean firstOne = true; %>
					<span class="indented">
<%							while (igm.hasNext())
							{
								curMember = (Element)igm.next(); %>
						<%= firstOne ? "" : ", " %>
						<%= curMember.getAttribute(XMLBuilder.A_NAME) %> <span class="netid">(<%= curMember.getAttribute(XMLBuilder.A_NETID) %>)</span>
						<% firstOne = false; %>
<%							} 
						}%>
					</span>
<%					}
				}
				else
				{%>
					<span class="indented">No groups have signed up.</span>
<%				}%> 
				</div>
			</td>
		</tr>
<%			}
		}
		if (numSlotsShown == 0)
		{%>
		<tr>
			<td>
				No timeslots are assigned to you.
			</td>
		</tr>
<%		}%>
	</table>
  	</div>
<%-- only admin or assign priv can view unscheduled groups --%>
<%	if (isadmin || isassign)
	{%>
  	<div class="assignment_left">
  	<h2>Unscheduled Groups</h2>
	<table class="assignment_table" cellspacing="0" border="0">
	<%	if (unschedGroups != null && unschedGroups.size() > 0) {
			Iterator iusg = unschedGroups.iterator();
			Element curGroup = null;
			while (iusg.hasNext()) {
				curGroup = (Element)iusg.next(); %>
				<tr><td>
			<%	Element usGroupMembers = XMLUtil.getFirstChildByTagName(curGroup, XMLBuilder.TAG_MEMBERS);
				if (usGroupMembers != null) {
					Collection curMems = XMLUtil.getChildrenByTagName(usGroupMembers, XMLBuilder.TAG_MEMBER);
					Iterator iusgm = curMems.iterator();
					Element curMember = null;
					boolean firstOne = true;
					while (iusgm.hasNext()) {
						curMember = (Element)iusgm.next(); %>
						<%= firstOne ? "" : ", " %>
						<%= curMember.getAttribute(XMLBuilder.A_NAME) %> <span class="netid">(<%= curMember.getAttribute(XMLBuilder.A_NETID) %>)</span>
						<% firstOne = false; %>
				<%	} %>
					</td></tr>
			<%	}
			} 
		} else { %>
			<tr><td>All groups are scheduled.</td></tr>		
	<%	} %>
	</table>  	
  	</div>
<%	} /* end unscheduled groups */%>
  </div>
  </td>
</tr>
<jsp:include page="../../footer.jsp"/>