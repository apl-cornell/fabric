<%@page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.www.util.DateTimeUtil, java.sql.Timestamp" %><%
Document displaydata= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
Element root= (Element) displaydata.getFirstChild();
Element assignment= (Element) XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT); 
Element schedule= (Element) XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_SCHEDULE);
%>
<h2>
  Schedule Options
  <span id="scheduleshead">
    <a class="hide" href="#" onClick="hide('schedules', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>
<div id="schedules" class="showhide">
	<p><input type="checkbox" onClick="swapDisplay('schedoptions'); return true;" name="<%= AccessController.P_USESCHEDULE %>"<%= assignment.hasAttribute(XMLBuilder.A_USESCHEDULE) ? " checked" : ""%>> Enable scheduling</p>
	<div id="schedoptions" class="showhide"<%= assignment.hasAttribute(XMLBuilder.A_USESCHEDULE) ? "" : " style=\"display: none\""%>>
		<table class="assignment_table" cellpadding="2" cellspacing="0">
    		<tr>
    			<td width="20%">Time slot duration:</td>
    			<td><input type="text" size="15" id="tsdurationbox" name="<%= AccessController.P_TSDURATIONSTR %>" value="<%= !schedule.getAttribute(XMLBuilder.A_TSDURATIONSTR).equals("") ? schedule.getAttribute(XMLBuilder.A_TSDURATIONSTR) : "30" %>"> minutes</td>
    			<script language="javascript">
					registerIntegerFormatTextbox('tsdurationbox');
				</script>
    		</tr>
    		<tr>
    			<td>Time slot group limit:</td>
    			<td><input type="text" size="5" id="tslimitbox" name="<%= AccessController.P_TSMAXGROUPS %>" value="<%= !schedule.getAttribute(XMLBuilder.A_TSMAXGROUPS).equals("") ? schedule.getAttribute(XMLBuilder.A_TSMAXGROUPS) : "1" %>"></td>
    			<script language="javascript">
					registerIntegerFormatTextbox('tslimitbox');
				</script>
    		</tr>
    		<tr>
    			<td>
    				Schedule lock time:
    				<br><span class="example">(Deadline for students to change timeslots; if empty, no deadline)</span>
    			</td>
    			<td>
	    			<div class="dateblock">
			      	<script type="text/javascript">
			         	var cal= new CalendarPopup();
			         </script>
			         <input id="tslock_datebox" type="text" size="17" name="<%= AccessController.P_SCHEDULE_LOCKDATE %>" value="<%= schedule.hasAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE) ? schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKDATE) : assignment.getAttribute(XMLBuilder.A_DUEDATE)%>">
				      <a href="#" id="datelink" name="datelink" onClick="cal.select(getElementById('tslock_datebox'), 'tslock_datebox', 'MMM d, yyyy'); return false;" name="datebox" id="datebox">
			         	<img class="calicon" src="../images/cal.gif" alt="Select" width="16px" height="16px">
			         </a>
				      <input id="tslock_timebox" type="text" size="5" name="<%= AccessController.P_SCHEDULE_LOCKTIME %>" value="<%= schedule.hasAttribute(XMLBuilder.A_SCHEDULE_LOCKTIME) ? schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKTIME) : "12:00" %>">
				      <script type="text/javascript">
							registerDateFormatTextbox('tslock_datebox');
							registerTimeFormatTextbox('tslock_timebox');
						</script>
				      <select id="tslock_ampm" name="<%= AccessController.P_SCHEDULE_LOCKAMPM %>">
			         	<% String ampm= schedule.getAttribute(XMLBuilder.A_SCHEDULE_LOCKAMPM); %>
							<option <%= "AM".equals(ampm) ? "selected" : "" %>>AM</option>
							<option <%= "PM".equals(ampm) ? "selected" : "" %>>PM</option>
				      </select>
				      <span class="example">e.g. <%= root.getAttribute(XMLBuilder.A_EXAMPLEDATE) %></span>
		    		</div>
		   	</td>
    		</tr>
    	</table>
	</div>
</div>