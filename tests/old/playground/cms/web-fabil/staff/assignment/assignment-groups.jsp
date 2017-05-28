<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*"%>
<% Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
   Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); 
   Element assignment= (Element) XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
   String max= assignment.getAttribute(XMLBuilder.A_MAXGROUP);
   String min= assignment.getAttribute(XMLBuilder.A_MINGROUP);
   boolean individual= (max.equals("1") && min.equals("1"));
   boolean assignedgroups = assignment.hasAttribute(XMLBuilder.A_ASSIGNEDGROUPS);
   boolean newassign= assignment.getAttribute(XMLBuilder.A_ASSIGNID).equals("0"); 
   int groupsOpt = 0;
   if (assignment.hasAttribute(XMLBuilder.A_GROUPSVAL)) {
   	String groupsVal = assignment.getAttribute(XMLBuilder.A_GROUPSVAL);
   	groupsOpt = groupsVal.equals(AccessController.TWO) ? 2 : (groupsVal.equals(AccessController.ONE) ? 1 : 0);
   } else {
   	if (individual) groupsOpt = 0;
   	else if (assignedgroups) groupsOpt = 2;
   	else groupsOpt = 1;
   }
   %>
<h2>
  Group Options
  <span id="groupshead">
    <a href="#" onClick="show('groups', '(show)', '(hide)'); return false;" class="hide">(show)</a>
  </span>
</h2>
<div id="groups" class="showhide" style="display: none">
  <table class="assignment_table" cellpadding="2" cellspacing="0">
    <tr>
      <td width="20%">Group Size</td>
      <td>
        <input type="Radio" value="<%= AccessController.ZERO %>" name="<%= AccessController.P_GROUPS %>" <%= (groupsOpt == 0) ? "checked" : "" %>>
          Students work individually.<br> 
        <input type="Radio" value="<%= AccessController.ONE %>" id="groups_var" name="<%= AccessController.P_GROUPS %>" <%= (groupsOpt == 1) ? "checked" : "" %>>
          Students can form groups of
          <input type="Text" size=2 name="<%= AccessController.P_GROUPSMIN %>" value="<%= min %>">
          to <input type="Text" size=2 name="<%= AccessController.P_GROUPSMAX %>" value="<%= max %>"> 
          people.<br>
        <input type="Radio" value="<%= AccessController.TWO %>" name="<%= AccessController.P_GROUPS %>" <%= (groupsOpt == 2) ? "checked" : ""%>> 
          Staff sets student groups.
      </td>
    </tr>
	<tr>
		<td>
			Group Migration
			<br><span class="example">(Migration overrides group size options)</span>
		</td>
		<td><% if (newassign) {%>
        	 Groups will be set initially from this previous assignment:
        	<%} else {%>
			 Any current groupings will be broken and groups will be reformed using
			 groups from the following assignment (if you're using schdules, any scheduling will be undone):<br>
			<%}%>	
        	 <select name="<%= AccessController.P_GROUPSFROM %>">
          	<option value="0" selected> None </option>
<% Element course= (Element) XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
   Element assignments= (Element) course.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENTS).item(0);
   NodeList l= assignments.getChildNodes();
   for (int i= 0; i != l.getLength(); i++)
   {
     Element e= (Element) l.item(i); %>
          	<option value="<%= e.getAttribute(XMLBuilder.A_ASSIGNID) %>"><%= e.getAttribute(XMLBuilder.A_NAME) %></option>
<% } %>
        	</select>
        	<% if (!newassign) {%>
				<br>WARNING: THIS ACTION CANNOT BE UNDONE
			<% } %>
      </td>
    </tr>
  </table>
</div>                