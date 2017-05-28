<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); 
Element course = (Element) root.getElementsByTagName(XMLBuilder.TAG_LOGSEARCH_COURSE).item(0);
String courseID = course.getAttribute(XMLBuilder.A_COURSEID);
NodeList assigns = root.getElementsByTagName(XMLBuilder.TAG_LOGSEARCH_ASSIGN);
Element lts = (Element) root.getElementsByTagName(XMLBuilder.TAG_LOGTYPES).item(0);
Element lns = (Element) root.getElementsByTagName(XMLBuilder.TAG_LOGNAMES).item(0);
NodeList logtypes = lts.getChildNodes();
NodeList lognames = lns.getChildNodes();
boolean noLogs = root.hasAttribute(XMLBuilder.A_INITIALSEARCH);
%>
   <h2>Search Logs<%= noLogs ? "" : "<span class=\"showhide\" id=\"logsearchhead\"><a href=\"#\" onclick=\"show('logsearch', ' (Show)', ' (Hide)'); return false;\"> (Show)</a></span>" %></h2>
		  <div id="logsearch"<%= noLogs ? "" : " style=\"display:none\"" %>>
		     <form name="logsearchform" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_SEARCHLOGS_COURSE + "&amp;" + AccessController.P_COURSEID + "=" + courseID %>" method="post">
		      <table class="assignment_table" cellpadding="2" cellspacing="0" border="0">
		        <tr>
		          <td align="center">
		             Course: <i><%= course.getAttribute(XMLBuilder.A_DISPLAYEDCODE) + ": " + course.getAttribute(XMLBuilder.A_COURSENAME) + "</i>" %>
               <input type="hidden" name="<%= AccessController.P_LOGSEARCH_COURSE %>" value="<%= courseID %>">
		          </td>
		          <td align="center">
		            Assignment:
		            <select name="<%= AccessController.P_LOGSEARCH_ASGN %>">
		              <option value="0" selected>Any Assignment</option>
		              <% for (int i=0; i < assigns.getLength(); i++) {
		                    Element a = ((Element) assigns.item(i)); %>
		                    <option value="<%= a.getAttribute(XMLBuilder.A_ASSIGNID) %>">
		                      <%= a.getAttribute(XMLBuilder.A_ASSIGNNAME) %>
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
		            Acting NetID:<p>
		            <input type="text" name="<%= AccessController.P_LOGSEARCH_NETID %>" size="12">
		            </p>
		          </td>
		          <td align="center">
		            Affected NetID:<p>
		            <input type="text" name="<%= AccessController.P_LOGSEARCH_RECNETID %>" size="12">
		            </p>
		          </td>
		          <td align="center">
		            Simulated NetID:<p>
		            <input type="text" name="<%= AccessController.P_LOGSEARCH_SIMNETID %>" size="12">
		            </p>
		          </td>
		        </tr>
		        <tr valign="middle">
		          <td align="center">
		            <table class="no_borders" id="no_borders" cellpadding="0" cellspacing="0" border="0">
		            <tr><td>Log Type(s):</td><td>
		            <select multiple name="<%= AccessController.P_LOGSEARCH_TYPE %>" id="<%= AccessController.P_LOGSEARCH_TYPE %>"
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
		            </select>
		            </td></tr><tr>
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
                <tr><td>
		            Log Name(s):
		            </td><td>
		            <select multiple size="4" name="<%= AccessController.P_LOGSEARCH_NAME %>" id="<%= AccessController.P_LOGSEARCH_NAME %>">
		              <% for (int i=0; i < lognames.getLength(); i++) { 
		                   Element n = (Element) lognames.item(i); %>
		                   <option selected value="<%= n.getAttribute(XMLBuilder.A_LOGNAME) %>">
		                     <%= n.getAttribute(XMLBuilder.A_LOGTYPE) %>: <%= n.getAttribute(XMLBuilder.A_LOGNAME) %>
		                   </option>
		              <% } %>
		            </select>
		            </td></tr><tr>
		            <td align="center" colspan="2"><a href="#" onclick="var nsel = getElementById('<%= AccessController.P_LOGSEARCH_NAME %>');
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
