<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); 
NodeList courses = ((Element)root.getElementsByTagName(XMLBuilder.TAG_LOGSEARCH_COURSES).item(0)).getChildNodes();
NodeList assigns = ((Element)root.getElementsByTagName(XMLBuilder.TAG_LOGSEARCH_ASSIGNS).item(0)).getChildNodes();
NodeList logtypes = ((Element)root.getElementsByTagName(XMLBuilder.TAG_LOGTYPES).item(0)).getChildNodes();
NodeList lognames = ((Element)root.getElementsByTagName(XMLBuilder.TAG_LOGNAMES).item(0)).getChildNodes();
%>
  <h2>
    <a name="n_logsearch"></a>
    Search Logs
    <span id="logsearchhead"> 
      <a class="hide" href="#" onClick="show('logsearch', '(show)', '(hide)'); return false;">(show)</a>
    </span>
  </h2>
  <div id="logsearch" class="showhide" style="display: none">
  	 <form name="logsearchform" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_SEARCHLOGS_CMSADMIN %>" method="post">
	    <table class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
    		<tr>
    			<td align="center">
	    			Course:
	    			<select name="<%= AccessController.P_LOGSEARCH_COURSE %>" id="<%= AccessController.P_LOGSEARCH_COURSE %>"
              onchange="
                        var asel = getElementById('<%= AccessController.P_LOGSEARCH_ASGN %>');
                        var csel = getElementById('<%= AccessController.P_LOGSEARCH_COURSE %>');
                        asel.options.length = 0;
                        asel.options[0] = new Option('Any Assignment', '0', false, false);
                        var cid = csel.options[csel.selectedIndex].value;
                        if (cid == 0) {
                          var aid = asel.options[asel.selectedIndex].value;
                        } else {
                          var aid = -1;
                        }
                        <% for (int i=0; i < assigns.getLength(); i++) { 
                             Element a = ((Element) assigns.item(i)); %>
                             if (cid == 0 || cid == <%= a.getAttribute(XMLBuilder.A_COURSEID) %>) {
                                if (aid == <%= a.getAttribute(XMLBuilder.A_ASSIGNID) %>) {
                                  var opt = new Option('<%= a.getAttribute(XMLBuilder.A_NAME) %>', '<%= a.getAttribute(XMLBuilder.A_ASSIGNID) %>', false, true);
                                } else {
                                  var opt = new Option('<%= a.getAttribute(XMLBuilder.A_NAME) %>', '<%= a.getAttribute(XMLBuilder.A_ASSIGNID) %>', false, false);
                                }
                                asel.options[asel.options.length] = opt;
                             }
                        <% } %>">
             <option value="0" selected>Any Course</option>
					<% for (int i = 0; i < courses.getLength(); i++) {
	             Element item = (Element)courses.item(i); %>
						    <option value="<%= item.getAttribute(XMLBuilder.A_COURSEID) %>">
									<%= item.getAttribute(XMLBuilder.A_CODE) + ": " + item.getAttribute(XMLBuilder.A_COURSENAME) %>
						    </option><%
             } %>
	    			</select>
	    		</td>
	    		<td align="center">
	    			Assignment:
	    			<select name="<%= AccessController.P_LOGSEARCH_ASGN %>" id="<%= AccessController.P_LOGSEARCH_ASGN %>"
	    			  onchange="
                        var asel = getElementById('<%= AccessController.P_LOGSEARCH_ASGN %>');
                        var csel = getElementById('<%= AccessController.P_LOGSEARCH_COURSE %>');
                        if (asel.selectedIndex != 0) {
                          var aid = asel.options[asel.selectedIndex].value;
                          <% for (int i=0; i < assigns.getLength(); i++) {
                                Element a = ((Element) assigns.item(i)); %>
                                if (aid == <%= a.getAttribute(XMLBuilder.A_ASSIGNID) %>) {
                                  var cid = <%= a.getAttribute(XMLBuilder.A_COURSEID) %>;
                                }
                          <% } %>
                          for (var j=0; j < csel.options.length; j++) {
                            if (csel.options[j].value == cid) {
                              csel.selectedIndex = j;
                            }
                          }
                        }">
	    			  <option value="0" selected>Any Assignment</option>
	    			  <% for (int i=0; i < assigns.getLength(); i++) {
	    			        Element a = ((Element) assigns.item(i)); %>
                    <option value="<%= a.getAttribute(XMLBuilder.A_ASSIGNID) %>">
                      <%= a.getAttribute(XMLBuilder.A_NAME) %>
                    </option>
              <% } %>
	    			</select>
	    		</td>
	    		<td align="center">
            IP Address:
            <input type="text" name="<%= AccessController.P_LOGSEARCH_IP %>" size="24">
	    		</td>
	    	</tr>
	    	<tr>
	    		<td align="center">
	    			Dates <br><font size=1>(Leave blank for no restriction)</font>
	    		</td>
	    		<td align="center">
	    			From:
	    			<div class="cal" id="dateselect"></div>
	    			<script type="text/javascript">
              var cal= new CalendarPopup();
              </script>
	    			<!-- <input type="text" name="<%= AccessController.P_LOGSEARCH_START %>" size="24"> -->
	    			<input id="datebox" type="text" size="17" name="<%= AccessController.P_LOGSEARCH_START %>" value="">
            <a href="#" id="datelink" name="datelink" onClick="cal.select(getElementById('datebox'), 'datelink', 'MMM d, yyyy'); return false;">
              <img class="calicon" src="../images/cal.gif" alt="Select" width="16px" height="16px">
            </a>
	    		</td>
	    		<td align="center">
	    			To:
            <div class="cal" id="dateselect2"></div>
            <script type="text/javascript">
              var cal2= new CalendarPopup();
              </script>
	    			<input id="datebox2" type="text" size="17" name="<%= AccessController.P_LOGSEARCH_END %>" value="">
	          <a href="#" id="datelink2" name="datelink2" onClick="cal2.select(getElementById('datebox2'), 'datelink2', 'MMM d, yyyy'); return false;">
              <img class="calicon" src="../images/cal.gif" alt="Select" width="16px" height="16px">
            </a>
	    		</td>
	    	</tr>
	    	<tr>
	    		<td align="center">
	    			Acting NetID:
	    			<input type="text" name="<%= AccessController.P_LOGSEARCH_NETID %>" size="12">
	    		</td>
	    		<td align="center">
	    			Affected NetID:
	    			<input type="text" name="<%= AccessController.P_LOGSEARCH_RECNETID %>" size="12">
	    		</td>
	    		<td align="center">
	    		  Simulated NetID:
            <input type="text" name="<%= AccessController.P_LOGSEARCH_SIMNETID %>" size="12">
	    		</td>
	    	</tr>
	    	<tr valign="middle">
	    		<td align="center">
	    		  <table class="no_borders" id="no_borders" cellpadding="0" cellspacing="0" border="0">
            <tr><td>Log Type(s):</td><td>
            <select size="4" multiple name="<%= AccessController.P_LOGSEARCH_TYPE %>" id="<%= AccessController.P_LOGSEARCH_TYPE %>"
              onchange="
                        var tsel = getElementById('<%= AccessController.P_LOGSEARCH_TYPE %>');
                        var nsel = getElementById('<%= AccessController.P_LOGSEARCH_NAME %>');
                        nsel.options.length = 0;
                        for (var j=0; j < tsel.options.length; j++) {
                          if (tsel.options[j].selected) {
                            <% for (int i=0; i < lognames.getLength(); i++) {
                                  Element n = (Element) lognames.item(i); %>
                                  if (tsel.options[j].text == '<%= n.getAttribute(XMLBuilder.A_LOGTYPE) %>') {
                                    nsel.options[nsel.options.length] = 
                                      new Option('<%= n.getAttribute(XMLBuilder.A_LOGTYPE) %>: <%= n.getAttribute(XMLBuilder.A_LOGNAME) %>', '<%= n.getAttribute(XMLBuilder.A_LOGNAME) %>', false, true);
                                  }
                            <% } %>
                          }
                        }
                        ">
              <% for (int i=0; i < logtypes.getLength(); i++) { 
                   Element t = (Element) logtypes.item(i); %>
                   <option selected value="<%= t.getAttribute(XMLBuilder.A_LOGTYPE) %>"><%= t.getAttribute(XMLBuilder.A_LOGTYPESTR) %></option>
              <% } %>
            </select></td></tr><tr>
            <td align="center" colspan="2"><a href="#" onclick="var tsel = getElementById('<%= AccessController.P_LOGSEARCH_TYPE %>');
                                     for (var j=0; j < tsel.options.length; j++) {
                                       tsel.options[j].selected = true;
                                     }
                                     tsel.onchange();
                                     return false;">
                <font size=1>select all</font></a>
            </td></tr></table>
	    		</td>
	    		<td align="center">
	    		  <table class="no_borders" id="no_borders" cellpadding="0" cellspacing="0" border="0">
            <tr><td>Log Name(s):
            </td><td>
	    		  <select size="4" multiple name="<%= AccessController.P_LOGSEARCH_NAME %>" id="<%= AccessController.P_LOGSEARCH_NAME %>">
              <% for (int i=0; i < lognames.getLength(); i++) { 
                   Element n = (Element) lognames.item(i); %>
	    		         <option selected value="<%= n.getAttribute(XMLBuilder.A_LOGNAME) %>">
	    		           <%= n.getAttribute(XMLBuilder.A_LOGTYPE) %>: <%= n.getAttribute(XMLBuilder.A_LOGNAME) %>
                   </option>
              <% } %>
	    		  </select>
            </td></tr><tr>
            <td align="center" colspan="2">
	    		  <a href="#" onclick="var nsel = getElementById('<%= AccessController.P_LOGSEARCH_NAME %>');
                                     for (var j=0; j < nsel.options.length; j++) {
                                       nsel.options[j].selected = true;
                                     }
                                     return false;">
                <font size=1>select all</font></a>
            </td></tr></table>
	    		</td>
	    		<td align="center">
	    		  <input type="submit" value="Search">
	    		<td>
	    	</tr>
<!--
the high-level logs, which don't have detail strings, still ought to have enough info to search by,
so we shouldn't need to have the ability to search for a given string
--> 
      </table>
	    			Please note that logs are only written for <b>successfully</b> completed actions. 
	    			Failed actions are not recorded. For instance, if a student's attempt to submit 
	    			a file times out, the attempt will not have a log entry.
	  </form>
  </div>
