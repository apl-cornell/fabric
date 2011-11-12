<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.model.*"%>
<% Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
   Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
   Element assignment= (Element) XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
   int assignType = Integer.parseInt(assignment.getAttribute(XMLBuilder.A_ASSIGNTYPE));
%>
<link href="calstyle.css" rel="stylesheet" type="text/css">
<script src="CalendarPopup.js" type="text/javascript"></script>
<h2>
General 
<span id="generalhead">
  <a onClick="hide('general', '(hide)', '(show)'); return false;" href="#" class="hide">(hide)</a>
</span>
</h2>
<div id="general" class="showhide">
  <table class="assignment_table" style="width: 100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td>Name</td>
      <td>
        <input type="text" size="50" name="<%= AccessController.P_NAME %>" value="<%= (assignment.hasAttribute(XMLBuilder.A_NAME) ? assignment.getAttribute(XMLBuilder.A_NAME) : "") %>">
      </td>
    </tr>
    <tr>
      <td>Short Name</td>
      <td>
        <input type="text" size="10" maxlength="6" name="<%= AccessController.P_NAMESHORT %>" value="<%= (assignment.hasAttribute(XMLBuilder.A_NAMESHORT) ? assignment.getAttribute(XMLBuilder.A_NAMESHORT) : "") %>">
      </td>
    </tr>
    <tr>
	  <td>Status</td>
	  <td>
<% String status= assignment.getAttribute(XMLBuilder.A_STATUS);
   boolean hidden= Assignment.HIDDEN.equalsIgnoreCase(status);
   boolean open= Assignment.OPEN.equalsIgnoreCase(status);
   boolean closed=  Assignment.CLOSED.equalsIgnoreCase(status);
   boolean graded= Assignment.GRADED.equalsIgnoreCase(status);
   if (!(hidden || open || closed || graded)) hidden = true;%>
	    <span title="Students cannot see the assignment.">
	      <input type="Radio" value="<%= Assignment.HIDDEN %>" name="<%= AccessController.P_STATUS %>" <%= hidden ? "checked" : "" %>> 
	      Hidden: <small>hidden from students (grades still count towards total)</small>
	    </span><br>
	    <span title="Students can see the assignment and submit files.">
	      <input type="Radio" value="<%= Assignment.OPEN %>" name="<%= AccessController.P_STATUS %>" <%= open ? "checked" : "" %>> 
	      Open: <small>students can submit files until due or late deadline, grades hidden</small>
	    </span><br>
        <span title="Students can see the assignment (without grades or statistics) but cannot submit files.">
	      <input type="Radio" value="<%= Assignment.CLOSED %>" name="<%= AccessController.P_STATUS %>" <%= closed ? "checked" : "" %>> 
	      Closed: <small>extension-only submissions, grades hidden</small>
	    </span>
	<% if (assignType != Assignment.SURVEY) { %>
		<br>
	    <span title="Students can see the assignment (with grades) but cannot submit files.">
	      <input type="Radio" value="<%= Assignment.GRADED %>" name="<%= AccessController.P_STATUS %>" <%= graded ? "checked" : "" %>> 
	      Graded: <small>extension-only submissions, grades visible, solutions visible if graded </small>
	    </span>
	<% } %>
	  </td>
	</tr>
	<%if (assignType == Assignment.ASSIGNMENT){%>
	  <tr>
      <td>Maximum Score</td>
      <td>
        <input size="3" type="text" name="<%= AccessController.P_TOTALSCORE %>" value="<%= assignment.getAttribute(XMLBuilder.A_TOTALSCORE) %>"> points
      </td>
    </tr>
    <%}%>
	<% if (assignType != Assignment.SURVEY) {%>
    <tr>
      <td>Weight</td>
      <td>
        <input size="3" type="text" name="<%= AccessController.P_WEIGHT %>" value="<%= assignment.getAttribute(XMLBuilder.A_WEIGHT) %>"> points
      </td>
    </tr>
    <%}%>
    <tr>
      <td>Due Date</td>
      <td>
        <div class="dateblock">
		          <script type="text/javascript">
		            var cal= new CalendarPopup();
		          </script>
		          <%-- I've deliberately not used formattedtextboxes here; this is checked by assignscript.js --%>
		          <input id="datebox" type="text" size="17" name="<%= AccessController.P_DUEDATE %>" value="<%= assignment.getAttribute(XMLBuilder.A_DUEDATE) %>">
			      <a href="#" id="datelink" name="datelink" onClick="cal.select(getElementById('datebox'), 'datelink', 'MMM d, yyyy'); return false;" name="datebox" id="datebox">
		            <img class="calicon" src="../images/cal.gif" alt="Select" width="16px" height="16px">
		          </a>
			      <input type="text" size="5" name="<%= AccessController.P_DUETIME %>" value="<%= assignment.getAttribute(XMLBuilder.A_DUETIME) %>">
			      <select id="due_ampm" name="<%= AccessController.P_DUEAMPM %>">
		          	<% String ampm= assignment.getAttribute(XMLBuilder.A_DUEAMPM); %>
			        <option <%= "AM".equals(ampm) ? "selected" : "" %>>AM</option>
			        <option <%= "PM".equals(ampm) ? "selected" : "" %>>PM</option>
			      </select>
			      <span class="example">e.g. <%= root.getAttribute(XMLBuilder.A_EXAMPLEDATE) %></span>
	    </div>
	    
	    <div style="padding-top: 10px">
          <span id="advanceddatehead">
	        <a onClick="show('advanceddate', 'Advanced Options &raquo;', '&laquo Advanced Options'); return false;" href="#" >Advanced options &raquo;</a>
	      </span>
	      <div id="advanceddate" class="advanceddue" style="display: none">
	        <table class="assignment_table" cellspacing="0" border="0">
              <tr>
                <td width="20%">Grace Period</td>
                <td>
                  <input type="text" size="3" name="<%= AccessController.P_GRACEPERIOD %>" value="<%= ((assignment.hasAttribute(XMLBuilder.A_GRACEPERIOD)) ? assignment.getAttribute(XMLBuilder.A_GRACEPERIOD) : "0") %>"> minutes
                </td>
              </tr>
              <tr>
                <td>Late Submissions</td>
                <td>
                  <input value="<%= AccessController.ZERO %>" name="<%= AccessController.P_LATEALLOWED %>" type="radio" <%= assignment.hasAttribute(XMLBuilder.A_LATEALLOWED) ? "" : "checked" %>> Not Allowed<br>
                  <input id="late_allow_chk" value="<%= AccessController.ONE %>" name="<%= AccessController.P_LATEALLOWED %>" type="radio" <%= assignment.hasAttribute(XMLBuilder.A_LATEALLOWED) ? "checked" : "" %>> Allowed until:
              
                  <div class="dateblock">
                    <script type="text/javascript">
                        var cal2= new CalendarPopup();
                    </script>
                    <%-- I've deliberately not used formattedtextboxes here; this is checked by assignscript.js --%>
                    <input id="latebox" size="17" onchange="if (this.value != '') { var c = getElementById('late_allow_chk'); c.checked = true; }" name="<%= AccessController.P_LATEDATE %>" type="text" value="<%= assignment.getAttribute(XMLBuilder.A_LATEDATE) %>">
                    <a href="#" id="latelink" name="latelink" onClick="cal2.select(getElementById('latebox'), 'latelink', 'MMM d, yyyy'); return false;" name="latebox" id="latebox">
                      <img class="calicon" src="../images/cal.gif" alt="Select" width="16px" height="16px">
                    </a>
                    <input type="text" size="5" onchange="if (this.value != '') { var c = getElementById('late_allow_chk'); c.checked = true; }" name="<%= AccessController.P_LATETIME %>" value="<%= assignment.getAttribute(XMLBuilder.A_LATETIME) %>">
                    <select id="late_ampm" name="<%= AccessController.P_LATEAMPM %>">
<% ampm= assignment.getAttribute(XMLBuilder.A_LATEAMPM); %>
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
      </td>
    </tr>
    <tr>
      <td>Show Statistics</td>
      <td>
        <input type="checkbox" name=<%= AccessController.P_SHOWSTATS %> <%= assignment.hasAttribute(XMLBuilder.A_SHOWSTATS) ? "checked" : "" %>>
        Show statistics on student pages.
      </td>
    </tr>
 	<% if (assignType != Assignment.SURVEY) { %>
    <tr>
      <td>Show Solution</td>
      <td>
        <input type="checkbox" name=<%= AccessController.P_SHOWSOLUTION %> <%= assignment.hasAttribute(XMLBuilder.A_SHOWSOLUTION) ? "checked" : "" %>>
        Show solution to all students when assignment is Closed or Graded (instead of only to students with grades when assignment is Graded).
      </td>
    </tr>
    <% } %>
    <% boolean regrades= assignment.hasAttribute(XMLBuilder.A_STUDENTREGRADES); 
   String regradeDate= assignment.getAttribute(XMLBuilder.A_REGRADEDATE); 
   String regradeTime= assignment.getAttribute(XMLBuilder.A_REGRADETIME);
   ampm= assignment.getAttribute(XMLBuilder.A_REGRADEAMPM); %>
	<% if (assignType != Assignment.SURVEY) { %>
	  <tr>
	    <td>Regrades</td>
	    <td>
	      <input value="<%= AccessController.ZERO %>" name="<%= AccessController.P_REGRADES %>" <%= regrades ? "" : "checked" %> type="radio">
	      No online student regrade requests <br>
	      <input id="regrade_allow_chk" value="<%= AccessController.ONE %>" name="<%= AccessController.P_REGRADES %>" <%= regrades ? "checked" : "" %> type="radio">
	      Online student regrade requests allowed until: 
	  <div class="dateblock">
	  	<%-- I've deliberately not used formattedtextboxes here; this is checked by assignscript.js --%>
	    <input id="regradebox" size="17" name="<%= AccessController.P_REGRADEDATE %>" value="<%= regradeDate %>" type="text">
	    <a href="#" id="regradelink" name="regradelink" onClick="cal.select(getElementById('regradebox'), 'regradelink', 'MMM d, yyyy'); return false;">
	      <img class="calicon" src="../images/cal.gif" alt="Select" width="16px" height="16px">
	    </a>
	        <input type="text" size="5" name="<%= AccessController.P_REGRADETIME %>" value="<%= regradeTime %>">
	        <select id="regrade_ampm" name="<%= AccessController.P_REGRADEAMPM %>">
	          <option <%= "AM".equals(ampm) ? "selected" : "" %>>AM</option>
	          <option <%= "PM".equals(ampm) ? "selected" : "" %>>PM</option>
	        </select>
	    <span class="example">e.g. <%= root.getAttribute(XMLBuilder.A_EXAMPLEDATE) %></span>
	      </div>
	    </td>
	 <% } %>
	  </tr>
	  <% boolean assignedgraders= assignment.hasAttribute(XMLBuilder.A_ASSIGNEDGRADERS); %>
	<% if (assignType != Assignment.SURVEY) { %>
    <tr>
      <td>Assigned Graders Only</td>
      <td>
        <input value="<%= AccessController.ZERO %>" name="<%= AccessController.P_GROUPSBYTA %>" type="radio" <%= assignedgraders ? "" : "checked" %>>
        Course Staff with Grades privilege can grade any group.<br>
        <input value="<%= AccessController.ONE %>" name="<%= AccessController.P_GROUPSBYTA %>" type="radio" <%= assignedgraders ? "checked" : "" %>>
        Course Staff with Grades privilege can only grade assigned groups.
      </td>
    </tr>
    <% } %>
  </table>
</div>
                                 
