<%@ page language="java" import="org.w3c.dom.*, cms.www.util.*, cms.model.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
NodeList c = root.getElementsByTagName(XMLBuilder.TAG_COURSE);
Element course = (Element) c.item(c.getLength() - 1);
String courseid = course.getAttribute(XMLBuilder.A_COURSEID);
NodeList emails = root.getElementsByTagName(XMLBuilder.TAG_EMAIL);
Element students= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_STUDENTS);
boolean custom = (students != null && students.getChildNodes() != null && students.getChildNodes().getLength() > 0);
%><jsp:include page="../header.jsp"/>
<style type="text/css">
  table#students_table th#finalgrade {width: 1em}
  table#students_table td.finalgrade {text-align: right}
  
  div#addstudents {padding: 1em; background-color: #f7f7f0; border: 1px solid #ddd}
  div#addstudents h1 {font-size: 1em; font-weight: bold; padding: 0em; margin: 0em}
  div#addstudents div {margin: 1em 0em 0em 1em}
  
  span#exportlink {float: left}  
  div#updategrades {float: right; margin-bottom: 1em}
  
  div#div_left {float: left}
  span#span_right {float: right}
</style>
<script type="text/javascript">
    // Sets a block level element's display property to "none"
    // And changes its "header" link text and href
    //(this is a modified version of the function in header.js; note here we append IDs to divid)
    function hideAll(divid, curtxt, newtxt) {
      var stop=0;
      var i = 5;
      while (stop == 0) {
        var e = getElementById(divid + i);
        if (e == null) {
          stop = 1;
        } else {
          changeDisplay(divid + i, 'none');
        }
        i++;
      }
      var onclick= new Function('showAll(\'' + divid + '\', \'' + newtxt + '\', \'' + curtxt + '\'); return false;');
      changeHead(divid + 'head', newtxt, onclick);
    }
  
    // Sets a block level element's display to default ('').
    // And changes its "header" link text and href
    //(this is a modified version of the function in header.js; note here we append IDs to divid)
    function showAll(divid, curtxt, newtxt) {
      var stop=0;
      var i = 5;
      while (stop == 0) {
        var e = getElementById(divid + i);
        if (e == null) {
          stop = 1;
        } else {
          changeDisplay(divid + i, '');
        }
        i++;
      }
      var onclick= new Function('hideAll(\'' + divid + '\', \'' + newtxt + '\', \'' + curtxt + '\'); return false;');
      changeHead(divid + 'head', newtxt, onclick);
    }
</script>
<jsp:include page="../header-page.jsp"/>
<div id="course_wrapper_withnav">

<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">

  <tr>
<jsp:include page="navbar.jsp"/>
    <td valign="top" id="course_page_container">
      <div id="course_page">
      	<a name="editable"></a>
        <span class="assignment_title">Send E-mail</span>
        <br>
				<jsp:include page="../problem-report.jsp"/>
        <br>
        <div id="course_page">
        <form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_SENDEMAIL + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>">
        <table class="no_borders" cellpadding="0" cellspacing="1" border="0px">
          <tr><td align="left">Subject:</td>
          <td colspan="2"><input type="text" id="<%= AccessController.P_EMAIL_SUBJECT %>" name="<%= AccessController.P_EMAIL_SUBJECT %>" value="">
          </td></tr>
          <tr><td align="left" valign="top">Message:</td>
          <td colspan="2"><textarea id="<%= AccessController.P_EMAIL_BODY %>" name="<%= AccessController.P_EMAIL_BODY %>" rows="15" cols="55"></textarea>
          </td></tr>
          
	        <tr><td>To:</td>
	        <td>
	        
	           <select id="<%= AccessController.P_EMAIL_RECIPIENTS %>" name="<%= AccessController.P_EMAIL_RECIPIENTS %>">
	           <% if (!custom){ %>
	             <option value="all">All staff and students</option>
	             <option value="staff">Staff only</option>
	             <% }else{ %>
				 <option value="custom">Custom</option>
			<% } %>
	           </select>
	        </td>
	        <td align="left">
	           <input type="submit" value="Send E-mail">
	        </td></tr>
	      </table>
	      
	    </div>
	    <div id="course_page">
	     <span class="assignment_title">Past E-mails</span>
	     <br><br>
	     <% if (emails.getLength() < 1) { %>
         No emails have been sent.
       <% } else { %>
	     <table class="email_table" id="email_table" cellpadding="4" cellspacing="0" border="1" width="100%">
	       <tr>
	         <th>&nbsp;</th>
	         <th>Subject</th>
           <th>Date</th>
		       <th>Sender</th>
		       <th>Recipients</th>
		       <th>&nbsp;</th>
		     </tr>
	       <% for (int i=0; i < emails.getLength(); i++) {
	           Element xEmail = (Element) emails.item(i); 
	           String emailID = xEmail.getAttribute(XMLBuilder.A_ID);
	           String recip = xEmail.getAttribute(XMLBuilder.A_RECIPIENT);
	           String recipTxt = recip.equals("" + Email.STAFF) ? "Staff only" : 
	               (recip.equals("" + Email.STUDENTS) ? "Students only" :
	               "All staff and students"); %>
            <tbody<%= i > 4 ? " id='belowfive" + i + "' style='display: none'" : "" %>>
            <tr>
              <td rowspan="2" valign="middle"><strong><%= i + 1 %></strong> 
              <td style="border-right: none; border-bottom: none"><%= xEmail.getAttribute(XMLBuilder.A_SUBJECT) %></td>
              <td style="border-right: none; border-left: none; border-bottom: none"><%= xEmail.getAttribute(XMLBuilder.A_DATE) %></td>
              <td style="border-right: none; border-left: none; border-bottom: none"><%= xEmail.getAttribute(XMLBuilder.A_SENDER) %></td>
              <td style="border-left: none; border-bottom: none"><%= recipTxt %></td>
              <!-- provide the message body so it can be copied -->
              <td rowspan="2" align="center" valign="middle"><a href="#editable" onclick="
                  var subj = getElementById('<%= AccessController.P_EMAIL_SUBJECT %>');
                  var body = getElementById('<%= AccessController.P_EMAIL_BODY %>');
                  var recp = getElementById('<%= AccessController.P_EMAIL_RECIPIENTS %>');
                  subj.value = '<%= StringUtil.escapeQuote(xEmail.getAttribute(XMLBuilder.A_SUBJECT)) %>';
                  body.value = getElementById('msgresend<%= emailID %>').innerHTML; //this id refers to Show Message
                  recp.selectedIndex = <%= recip.equals("3") ? "0" : recip %>;
                  return true;">View for Resend</a>
              </td>
            </tr>
            <tr>
            	<td colspan="4" style="border-top: none"> 
            		<span id='emailmessage_<%= emailID %>head'>
            			<a href="#" style="font-size:1" onclick="show('emailmessage_<%= emailID %>', 'Show message: ', 'Hide message: '); return false;">
                 			Show message:
                 		</a>
                 		<%-- turn html tags into html special characters so they look right --%>
                 		<div id='emailmessage_<%= emailID %>' style="display:none">
                 			<pre id="msgbody<%= emailID %>"><%= StringUtil.formatNoHTMLString(xEmail.getAttribute(XMLBuilder.A_MESSAGE)) %></pre>
							</div>
							<%-- leave tags alone so they can show up correctly in the textbox above --%>
							<div style="display:none">
                 			<pre id="msgresend<%= emailID %>"><%= xEmail.getAttribute(XMLBuilder.A_MESSAGE) %></pre>
							</div>
              		</span>
            	</td>
            </tr>
            </tbody>
         <% } %>
	     </table>
	     <% } %>
       <% if (emails.getLength() > 5) { %>
       <span id='belowfivehead'>
        <a href="#" onclick="showAll('belowfive', 'Show All', 'Show Latest 5 Only'); return false;">Show All</a>
	     </span>
	     <% } %>
	    </div>
	    <% if (custom){ 
	    	NodeList studs = students.getChildNodes();
	    %>
		<span class="hide" id="studentshead">
                 (<a class="hide" href="#" onClick="hide('students', 'hide', 'show'); return false;">hide</a>)
                </span
	    <div id="students" class="showhide"> 
              <table class="sortable" id="students_table" cellpadding="0" cellspacing="0">
            <tr>
              <th nowrap class="nosort">Email</th>
              <th nowrap class="text" id="~1">Last Name</th>
              <th nowrap class="text">First Name</th>
              <th nowrap class="text">NetID</th>
              
              
      <%    for (int i= 0; i < studs.getLength(); i++) {
  Element student= (Element) studs.item(i);
  /* hidden append for second sort by */
  String hiddenAppend= "<span style=\"display: none;\">"+","+student.getAttribute(XMLBuilder.A_LASTNAME)+","+student.getAttribute(XMLBuilder.A_FIRSTNAME)+","+student.getAttribute(XMLBuilder.A_NETID)+"</span>";
  String netID = student.getAttribute(XMLBuilder.A_NETID); 
        %>
<%-- ----------------------------------- begin row --------------------------------------------------------- --%>
            <tr>
              <td><input type="checkbox" id=netid_<%= netID %> checked name=<%= AccessController.P_EMAIL_NETIDS %> value="<%= netID %>"/></td>
              <td><%= student.getAttribute(XMLBuilder.A_LASTNAME)%><%=hiddenAppend%></td>
              <td><%= student.getAttribute(XMLBuilder.A_FIRSTNAME)%><%=hiddenAppend%></td>
              <td><%= netID %></td>
	    <% } %>
		</table>
		</form>
	    <% } %>
	  </td>
	</div>
    <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
	</tr>
	<script type="text/javascript">
    	<%-- initialize sortable tables; see sorttable.js --%>
    	sortables_init();
    	<%-- Resort table by previous sort ordering --%> 
    	var tablename = 'email_table';
    	var lnk_id = getCookie(tablename + '_coln');
    	var sort_dir = getCookie(tablename + '_sortdir');
    	if (lnk_id && sort_dir) {
    		var lnk = getElementById(lnk_id);
    		var span;
    		for (var ci=0;ci<lnk.childNodes.length;ci++) {
        		if (lnk.childNodes[ci].tagName && lnk.childNodes[ci].tagName.toLowerCase() == 'span') span = lnk.childNodes[ci];
    		}
    		span.setAttribute('sortdir', sort_dir);
    		ts_resortTable(lnk);
    	}
    </script>
<jsp:include page="../footer.jsp"/>
