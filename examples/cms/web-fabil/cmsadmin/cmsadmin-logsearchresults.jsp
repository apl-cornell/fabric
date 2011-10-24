<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); 
NodeList logs = ((Element) root.getElementsByTagName(XMLBuilder.TAG_LOGSEARCH_RESULTS).item(0)).getChildNodes(); %>
<jsp:include page="../header.jsp" />
<script src="CalendarPopup.js" type="text/javascript"></script>
<style type="text/css">
  .course_code_edit {float:right; text-align: right; margin:1em; padding-left: 1em; white-space: nowrap; width: 12em}
</style>
<script type="text/javascript">
    // Sets a block level element's display property to "none"
    // And changes its "header" link text and href 
    function hideL(divid, curtxt, newtxt) {
      changeDisplay(divid, 'none');
      var onclick= new Function('showL(\'' + divid + '\', \'' + newtxt + '\', \'' + curtxt + '\'); return false;');
      changeHead(divid + 'head', newtxt, onclick);
    }
  
    // Sets a block level element's display to default ('').
    // And changes its "header" link text and href
    function showL(divid, curtxt, newtxt) {
      changeDisplay(divid, '');
      var onclick= new Function('hideL(\'' + divid + '\', \'' + newtxt + '\', \'' + curtxt + '\'); return false;');
      changeHead(divid + 'head', newtxt, onclick);
    }
</script>
<link href="calstyle.css" rel="stylesheet" type="text/css">
<jsp:include page="header-cmsadmin.jsp" />

<div id="course_wrapper_withnav">
<table id="course_wrapper_table" cellpadding="0" cellspacing="0" border="0">
<tr>
	<td class="course_title">CMS Administration</td>
</tr>
<tr>
  <td valign="top" id="course_page_container">
    <div id="course_page">
			<jsp:include page="../problem-report.jsp"/>
	    <%-- <div class="assignment_left">
        <jsp:include page="cmsadmin-logsearch.jsp"/>
      </div> --%>
	    <div class="assignment_left" id="topblock">
	      <h2>Search Results:</h2><br>
	      <% if (logs.getLength() == 0) { %>
             No matching logs found.
        <% } else { %> 
             <table id="log_results_table" cellpadding="1" cellspacing="0" border>
             <tr>
                <th>Log Type</th><th>Action</th><th>Acting NetID</th><th>Acting IP Address</th>
                <th>Affected NetIDs</th><th>Simulated NetID</th><th>Course</th><th>Assignment</th><th>Date</th>
             </tr>
	           <% for (int i=0; i < logs.getLength(); i++) {
	               Element l = (Element) logs.item(i);
	               String logID = l.getAttribute(XMLBuilder.A_LOGID);
	               NodeList recs = l.getElementsByTagName(XMLBuilder.TAG_RECNETID);
	               String recNetIDs = "";
	               for (int j=0; j < recs.getLength(); j++) {
	                  String netid = ((Element)recs.item(j)).getAttribute(XMLBuilder.A_NETID);
	                  recNetIDs += (recNetIDs.equals("") ? netid : (", " + netid));
	               }
	               %>
                 <tr><td><%= l.getAttribute(XMLBuilder.A_LOGTYPE) %><br>
                         <span id='logdetail_<%= logID %>head'>
                         <a href="#" style="font-size:1" onclick="showL('logdetail_<%= logID %>', 'Show details', 'Hide details'); return false;">Show details</a>
                         </span></td>
                     <td><%= l.getAttribute(XMLBuilder.A_LOGNAME) %>&nbsp;</td>
                     <td><%= l.getAttribute(XMLBuilder.A_NETID) %>&nbsp;</td>
                     <td><%= l.getAttribute(XMLBuilder.A_IPADDRESS) %>&nbsp;</td>
                     <td><%= recNetIDs %>&nbsp;</td>
                     <td><%= (l.hasAttribute(XMLBuilder.A_SIMNETID) ? l.getAttribute(XMLBuilder.A_SIMNETID) : "") %>&nbsp;</td>
                     <td><%= (l.hasAttribute(XMLBuilder.A_COURSENAME) ? l.getAttribute(XMLBuilder.A_COURSENAME) : "") %>&nbsp;</td>
                     <td><%= (l.hasAttribute(XMLBuilder.A_ASSIGNMENT) ? l.getAttribute(XMLBuilder.A_ASSIGNMENT) : "") %>&nbsp;</td>
                     <td><%= l.getAttribute(XMLBuilder.A_DATE) %>&nbsp;</td>
                 </tr><tr align="left"><td align="left" id='logdetail_<%= logID %>' colspan='9' style="display:none"><ul>
                     <% NodeList details = l.getElementsByTagName(XMLBuilder.TAG_LOGDETAIL);
                        for (int j=0; j < details.getLength(); j++) { %>
                          <li><%= ((Element)details.item(j)).getAttribute(XMLBuilder.A_DETAILS) %>
                     <% } %>
                     </ul></td></tr>
             <% } %>
             </table>
	      <% } %>
				<dl>
		  </div>
    </div>
  </td>
  <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
</tr>
<jsp:include page="../footer.jsp"/>