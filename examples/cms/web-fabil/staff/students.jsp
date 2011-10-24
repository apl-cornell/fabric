<%@ page language="java" import="org.w3c.dom.*, cms.model.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
Element root= (Element) displayData.getFirstChild();
Element course= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_COURSE());
Element students= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_STUDENTS());
Element assignments= XMLUtil._Proxy.getFirstChildByTagName(course, XMLBuilder._Static._Proxy.$instance.get$TAG_ASSIGNMENTS());
String courseid= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID());
boolean hasSection = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_HASSECTION());
boolean isadmin= course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISADMIN());
boolean isgroups= course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISGROUPS());
boolean isgrades= course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISGRADES());
boolean isassign= course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISASSIGN());
boolean showAddDrop= root.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SHOWADDDROP());
boolean showGradeMsg = root.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_GRADEMSG());%>
<jsp:include page="../header.jsp" />
<script type="text/javascript" src="cookies.js"></script> <%-- required for sorttable.js --%>
<script type="text/javascript" src="sorttable.js"></script>
<style type="text/css">
  table#students_table th#finalgrade {width: 1em}
  table#students_table td.finalgrade {text-align: right}
  
  div#addstudents {padding: 1em; background-color: #f7f7f0; border: 1px solid #ddd}
  div#addstudents h1 {font-size: 1em; font-weight: bold; padding: 0em; margin: 0em}
  div#addstudents div {margin: 1em 0em 0em 1em}
  
  span#exportlink {float: left}  
  div#updategrades {float: right; margin-bottom: 1em}
</style>
<script type="text/javascript">
<%-- 
stick orange and red circles in the appropriate table cells,
going strictly by the class names of the TDs
--%>
function insertRegradeImages(table)
{
	for (var i = 0; i < table.rows.length; i++)
	{
		for (var j = 0; j < table.rows[i].cells.length; j++)
		{
			if (table.rows[i].cells[j].className == 'regrade_pending')
			{
				var img = document.createElement('img');
				img.setAttribute('alt', '(Regrade pending)');
				img.setAttribute('src', 'images/tag_red.gif');
				table.rows[i].cells[j].insertBefore(img, table.rows[i].cells[j].firstChild);
			}
			else if (table.rows[i].cells[j].className == 'regraded')
			{
				var img = document.createElement('img');
				img.setAttribute('src', 'images/tag_orange.gif');
				img.setAttribute('alt', '(Regraded)');
				table.rows[i].cells[j].insertBefore(img, table.rows[i].cells[j].firstChild);
			}
		}
	}
}
</script>
<jsp:include page="../header-page.jsp" />
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
<jsp:include page="navbar.jsp" />
    <td id="course_page_container">
      <div id="course_page">
        <jsp:include page="../problem-report.jsp" />
<%-- --------------------------------------------------------------------------------- --%>
          <span class="assignment_title">Add/Drop</span>
           <span class="hide" id="adddrophead">
                 (<a class="hide" href="#" onClick="<%= showAddDrop ? "hide" : "show" %>('adddrop', '<%= showAddDrop ? "hide" : "show" %>', '<%= showAddDrop ? "show" : "hide" %>'); return false;"><%= showAddDrop ? "hide" : "show" %></a>)
                </span> 
             <div  <%= showAddDrop ? "" : "style=\"display: none;\"" %> id="adddrop" class="showhide">    
                
         <form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_ADDSTUDENTS + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>">
 
          <div id="addstudents">
            <h1>Add Students</h1>
            <div>
              <input name="<%= AccessController.P_EMAILADDED %>" type="checkbox" checked> Notify Added Students by E-mail
            </div>
            <div>
              Enter NetIDs
              <input name="<%= AccessController.P_STUDENTSLIST %>" type="text" size="25">
              <input name="<%= AccessController.P_ADDSTUDENTSLIST %>" type="submit" value="<%= AccessController.ADDSTUDENTS %>">
            </div>
            <div>
              Upload a file
              <input name="<%= AccessController.P_STUDENTSFILE %>" type="file" size="20">
              <input name="<%= AccessController.P_ADDSTUDENTSFILE %>" type="submit" value="<%= AccessController.ADDSTUDENTS %>">
            </div>
          </div>
        </form>

         <form method="post" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_DROPSTUDENTS + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>">
    		 <div id="addstudents">
            <h1>Drop Students</h1>
            <div>
              Enter NetIDs
              <input name="<%= AccessController.P_STUDENTSLIST %>" type="text" size="25">
              <input name="<%= AccessController.P_ADDSTUDENTSLIST %>" type="submit" value="Drop Students">
            </div>
          </div>
        </form>
</div>
<br>
<%-------------------------------------------------------------------------------------%>
          <span class="assignment_title">Students</span>
          <span class="hide" id="studentshead">
                 (<a class="hide" href="#" onClick="hide('students', 'hide', 'show'); return false;">hide</a>)
          </span><%
          if (showGradeMsg) {
%>		<div class="noticebox" style="font-weight:bold">
			Please select the student or score you wish to change. (Click a NetID or a specific score.)
		</div>
<% }%>
          <div style="margin-bottom:10px">
			<img src="images/tag_orange.gif" alt="">Regraded
			<br><img src="images/tag_red.gif" alt="">Regrade pending
			<br><img src="images/warning.gif" height="15px" alt="">Over max score
		  </div>
    <form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_SETFINALGRADES + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>">
          <div id="students" class="showhide"> 
              <table class="sortable" id="students_table" cellpadding="0" cellspacing="0">
            <tr>
    <th class="nosort">&nbsp;</th>
              <th nowrap class="text" id="~1">Last Name</th>
              <th nowrap class="text">First Name</th>
              <th nowrap class="text">NetID</th>
    	<% if (hasSection) { %>
			  <th nowrap class="text">Section</th>
		<% } %>
<%
NodeList assigns= assignments.getChildNodes();
for (int i= 0; i != assigns.getLength(); i++) {
  Element assign= (Element)assigns.item(i); 
  int type = assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE()) ? Integer.parseInt(assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE())) : -1;
  if (type == Assignment._Static._Proxy.$instance.get$SURVEY()) continue;
  %>
              <th nowrap class="scores">
 <%
String assignactbegin1= "<a href=\"?" + AccessController.P_ACTION + "=";
String assignactbegin2= "&amp;" + AccessController.P_ASSIGNID + "="; 
String assignactbegin3= "\">";
String assignactend= "</a>"; 
String begingrade=null, endgrade=null;
if (isadmin || isgrades || isgroups) {
    begingrade= assignactbegin1 + AccessController.ACT_GRADEASSIGN + assignactbegin2 + assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ID()) + assignactbegin3;
    endgrade= assignactend;
} else {
  begingrade= "<span class=\"noaccess\">";
  endgrade= "</span>"; 
}
%>
<%=begingrade%>
<%= assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAMESHORT()) %>
<%=endgrade%>
</th><%
} %>
              <th nowrap class="scores">Total</th>
              <th nowrap id="finalgrade" class="scores">Final Grade</th>
              <th class="nosort">&nbsp;</th>
            </tr>
<tr class="nosort">
    <th class="nosort" ><div style="display:none">~</div>&nbsp;</th>
              <th class="nosort"><div style="display:none">~</div>&nbsp;</th>
              <th class="nosort"><div style="display:none">~</div>&nbsp;</th>
              <th class="nosort"><div style="display:none">~</div>&nbsp;</th>
			  <% if (hasSection) { %>
			  <th class="nosort"><div style="display:none">~</div>&nbsp;</th>
			  <% } %>
<%
for (int i= 0; i != assigns.getLength(); i++)
{
	Element assign= (Element)assigns.item(i);
	String total = assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_TOTALSCORE()),   
      mean = assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATMEAN()) ? assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATMEAN()) : "", 
      max = assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATMAX()) ? assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATMAX()) : "", 
      median = assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATMEDIAN()) ? assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATMEDIAN()) : "", 
      dev = assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATDEV()) ? assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATDEV()) : "",
      weight = assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_WEIGHT()) ? assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_WEIGHT()) : "",
      nameShort= assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAMESHORT()) ? " ("+assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAMESHORT())+")" : "",
      nameAssign= assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) ? assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) : "";
	  int type = assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE()) ? Integer.parseInt(assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE())) : -1;
	  if (type == Assignment._Static._Proxy.$instance.get$SURVEY()) continue;
%>
              <th class="nosort" align="center">
              	<div onMouseover="ddrivetip('<b>Name:</b> <%= nameAssign.replaceAll("\"", "&quot;").replaceAll("'", "\\\\'") %><%= nameShort.replaceAll("\"", "&quot;").replaceAll("'", "\\\\'") %><br><b>Weight:</b> <%=weight%><br><b>Max:</b> <%=total%><br><b>High:</b> <%=max%><br><b>Mean:</b> <%=mean%><br><b>Median:</b> <%=median%><br><b>Dev:</b> <%=dev%>');" onMouseout="hideddrivetip()">
          		<div style="display:none">~</div> <center><small> <u>Stats</u> </small></center></div>
              </th><%
} %>
              <th class="nosort" align="center">
              	<div onMouseover="ddrivetip('<b>Max:</b> <%= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_MAXTOTALSCORE()) %><br><b>High:</b> <%= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_HIGHTOTALSCORE()) %><br><b>Mean:</b> <%= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_MEANTOTALSCORE()) %><br><b>Median:</b> <%= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_MEDIANTOTALSCORE()) %><br><b>Dev:</b> <%= course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STDEVTOTALSCORE()) %>');" onMouseout="hideddrivetip()">
              	<div style="display:none">~</div>  <center><small> <u>Stats</u> </small></center></div>
              </th>
              <th class="nosort"><div style="display:none">~</div>&nbsp;</th>
              <th class="nosort"><div style="display:none">~</div>&nbsp;</th>
            </tr><%
NodeList studs= XMLUtil._Proxy.getChildrenByAttributeValue(students, XMLBuilder._Static._Proxy.$instance.get$A_ENROLLED(), Student._Static._Proxy.$instance.get$ENROLLED());
boolean hasDroppedStuds = students.getChildNodes().getLength() > studs.getLength();
for (int i= 0; i < studs.getLength(); i++) {
  Element student= (Element) studs.item(i);
  /* hidden append for second sort by */
  String hiddenAppend= "<span style=\"display: none;\">"+","+student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_LASTNAME())+","+student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FIRSTNAME())+","+student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID())+"</span>";
  String totalscore= student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_TOTALSCORE()); 
  String netID = student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()); 
  String section = student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SECTION());
  String gradeURL = "?" + AccessController.P_ACTION + "=" + AccessController.ACT_STUDENTALLGRADES + "&" 
        + AccessController.P_COURSEID + "=" + courseid + "&" + AccessController.P_NETID + "=" + netID; 
        %>
<%-- ----------------------------------- begin row --------------------------------------------------------- --%>
            <tr>
            <td nowrap class="index"><%= i + 1 %></td>
              <td><%= student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_LASTNAME())%><%=hiddenAppend%></td>
              <td><%= student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FIRSTNAME())%><%=hiddenAppend%></td>
              <td>
              		<% if (isadmin) { %><a href="<%= gradeURL %>"><% } %><%= netID %><% if (isadmin) { %></a><% } %>
			  </td>
		<% if (hasSection) { %>
 			  <td align="center"><%= section != "" ? section : "*" %><%=hiddenAppend%></td>
 		<% } %>
<%
  for (int j= 0; j != assigns.getLength(); j++) {
    Element assign= (Element)assigns.item(j);
    String assignTag= "id"+assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID());
    Element grade= (Element)student.getElementsByTagName(assignTag).item(0);
    String score= "*";
    if (grade != null && grade.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE())) score= grade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE());
    String assignID=grade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID());
    String groupID=grade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_GROUPID());
    String regradeStatus = grade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_REGRADE());
	boolean overMaxScore = grade.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_OVERMAX());
  	int type = assign.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE()) ? Integer.parseInt(assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE())) : -1;     
    if (type == Assignment._Static._Proxy.$instance.get$SURVEY()) continue;
%>
              <%-- The next line is real long, but don't split it up
                   or the sorttable will break
                --%>
              <td nowrap align="right" <%= regradeStatus.equals(RegradeRequest._Static._Proxy.$instance.get$PENDING()) ? "class=\"regrade_pending\"" : (regradeStatus.equals(RegradeRequest._Static._Proxy.$instance.get$REGRADED()) ? "class=\"regraded\"" : "") %>><%=overMaxScore ? "<img src=\"images/warning.gif\" height=\"15px\" alt=\"(Over Max Score)\">" : ""%>
				<% if (isadmin) { %>
					<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_GRADESTUDENTS %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignID %>&amp;<%= AccessController.P_GROUPID %>=<%= groupID %>">
				<% } %>
				<% if (score.equals("*")) { %><%=hiddenAppend%>&nbsp;&nbsp;*&nbsp;&nbsp;<% } else { %><%= score %><%=hiddenAppend%><% } %>
				<% if (isadmin) { %>
					</a>
				<% } %>
			</td>
<%
  } %>
              <%-- The next line is real long, but don't split it up 
                   or the sorttable will break
                --%>
              <td align="center"><% if (totalscore.equals("")) { %><%=hiddenAppend%>*<% } else {%><%=totalscore %><%=hiddenAppend%><% } %></td>
         <% String finalGrade = student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FINALGRADE()); 
     String numFinaGrade=""; /* index into dropdown menus below */
                    if (finalGrade.equals("A+"))
                            numFinaGrade="1";
                    else if (finalGrade.equals("A"))
                            numFinaGrade="2";
                    else if (finalGrade.equals("A-"))
                            numFinaGrade="3";    
                    else if (finalGrade.equals("B+"))
                            numFinaGrade="4";
                    else if (finalGrade.equals("B"))
                            numFinaGrade="5";
                    else if (finalGrade.equals("B-"))
                            numFinaGrade="6";
                    else if (finalGrade.equals("C+"))
                            numFinaGrade="7";
                    else if (finalGrade.equals("C"))
                            numFinaGrade="8";
                    else if (finalGrade.equals("C-"))
                            numFinaGrade="9";
                    else if (finalGrade.equals("D+"))
                            numFinaGrade="10";
                    else if (finalGrade.equals("D"))
                            numFinaGrade="11";
                    else if (finalGrade.equals("D-"))
                            numFinaGrade="12";
                    else if (finalGrade.equals("F"))
                            numFinaGrade="13";
                    else if (finalGrade.equals("S"))
                            numFinaGrade="14";
                    else if (finalGrade.equals("U"))
                            numFinaGrade="15";
                    else if (finalGrade.equals("W"))
                            numFinaGrade="16";
                    else if (finalGrade.equals("INC"))
                            numFinaGrade="17";
                    else if (finalGrade.equals("AUD"))
                            numFinaGrade="18";
                    else
                            numFinaGrade="g";
                                        %>
              <td class="finalgrade" align="center">
    <div style="display:none"><%=numFinaGrade%></div>            
	<%=hiddenAppend%>
                <select name="<%= AccessController.P_FINALGRADE + student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %>" id="<%= AccessController.P_FINALGRADE + student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %>"<%= isadmin ? "" : " disabled" %>>
                  <option value="null"<%= finalGrade.equals("") ? " selected" : "" %>></option>
                  <option value="A+"<%= finalGrade.equals("A+") ? " selected" : "" %>>A+</option>
                  <option value="A"<%= finalGrade.equals("A") ? " selected" : "" %>>A</option>
                  <option value="A-"<%= finalGrade.equals("A-") ? " selected" : "" %>>A-</option>
                  <option value="B+"<%= finalGrade.equals("B+") ? " selected" : "" %>>B+</option>
                  <option value="B"<%= finalGrade.equals("B") ? " selected" : "" %>>B</option>
                  <option value="B-"<%= finalGrade.equals("B-") ? " selected" : "" %>>B-</option>
                  <option value="C+"<%= finalGrade.equals("C+") ? " selected" : "" %>>C+</option>
                  <option value="C"<%= finalGrade.equals("C") ? " selected" : "" %>>C</option>
                  <option value="C-"<%= finalGrade.equals("C-") ? " selected" : "" %>>C-</option>
                  <option value="D+"<%= finalGrade.equals("D+") ? " selected" : "" %>>D+</option>
                  <option value="D"<%= finalGrade.equals("D") ? " selected" : "" %>>D</option>
                  <option value="D-"<%= finalGrade.equals("D-") ? " selected" : "" %>>D-</option>
                  <option value="F"<%= finalGrade.equals("F") ? " selected" : "" %>>F</option>
                  <option value="S"<%= finalGrade.equals("S") ? " selected" : "" %>>S</option>
                  <option value="U"<%= finalGrade.equals("U") ? " selected" : "" %>>U</option>
                  <option value="W"<%= finalGrade.equals("W") ? " selected" : "" %>>W</option>
                  <option value="INC"<%= finalGrade.equals("INC") ? " selected" : "" %>>INC</option>
                  <option value="AUD"<%= finalGrade.equals("AUD") ? " selected" : "" %>>AUD</option>
                </select>
              </td>
               <td align="center"> 
<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_DROP + "&amp;" + AccessController.P_COURSEID + "=" + courseid  + "&amp;" + AccessController.P_NETID + "=" + netID %>">
                                        Drop</a>  </td>
  </td>
            </tr>
<%-- ----------------------------------- end row --------------------------------------------------------- --%>
<%
} %>
          </table>
          <script type="text/javascript">
          	/* put red and orange circles into appropriate cells of the student table */
          	insertRegradeImages(getElementById('students_table'));
          </script>
		</div>
		<br>
		<span id="exportlink" style="float: left">
			Download <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_EXPORTTABLE %>&amp;<%= AccessController.P_COURSEID %>=<%= courseid %>">enrolled student table</a> 
         	| <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_EXPORTRUBRIC %>&amp;<%= AccessController.P_COURSEID %>=<%= courseid %>">grading rubric</a>
        </span>
        <% if (isadmin) { %>
			<span id="updategrades" style="float: right">
	            <input type="submit" value="Update Final Grades">
	        </span>
	    <% } %>
    </form>
    <br>&nbsp;<br>
    <% if (isadmin) { %>
		<div id="uploadDownloadActions" style="margin-top: 5px">
	        <form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_FINALGRADESFILE + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>">
	           	Upload final grades file: <input type="file" size="4" name="<%= AccessController.P_GRADESFILE %>"><input type="submit" value="Upload">
	           	<span class="example">(Download <a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_FINALGRADESTEMPLATE + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>">template</a>)</span>
	        </form>
	    </div>
	<% } %>
    <br>&nbsp;<br>          
<%----------------- Dropped--------------------%>                  
<% if (hasDroppedStuds) { %>
 <br>
         <span class="assignment_title"> Dropped Students</span>
           <span class="hide" id="droppedhead">
                 (<a class="hide" href="javascript:show('dropped', 'show', 'hide'); ">show</a>)
                </span> 
             <div style="display: none;" id="dropped" class="showhide">     
          <table class="sortable" id="dropped_students_table" cellpadding="0" cellspacing="0" >
            <tr>
    <th class="nosort">&nbsp;</th>
              <th class="text" id="~2">Last Name</th>
              <th class="text">First Name</th>
              <th class="text">NetID</th><%
assigns= assignments.getChildNodes();
for (int i= 0; i != assigns.getLength(); i++) {
  Element assign= (Element)assigns.item(i); %>
              <th class="scores"><%= assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAMESHORT()) %></th><%
} %>
              <th class="scores">Total</th>
              <th id="finalgrade" class="nosort">Final Grade</th>
              <th class="nosort">&nbsp;</th>
            </tr><%
studs= XMLUtil._Proxy.getChildrenByAttributeValue(students, XMLBuilder._Static._Proxy.$instance.get$A_ENROLLED(), Student._Static._Proxy.$instance.get$DROPPED());
for (int i= 0; i < studs.getLength(); i++) {
  Element student= (Element) studs.item(i); 
  /* hidden append for second sort by */
  String hiddenAppend= "<div style=\"display: none;\">"+","+student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_LASTNAME())+","+student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FIRSTNAME())+","+student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID())+"</div>";
  String totalscore= student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_TOTALSCORE()); 
  String netID = student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID());
%>
           <tr>
            <td class="index"><%= i + 1 %></td>
              <td><%= student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_LASTNAME())%><%=hiddenAppend%></td>
              <td><%= student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FIRSTNAME())%><%=hiddenAppend%></td>
              <td><%= netID %></td><%
  for (int j= 0; j != assigns.getLength(); j++) {
    Element assign= (Element)assigns.item(j);
    String assignTag= "id"+assign.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID());
    Element grade= (Element)student.getElementsByTagName(assignTag).item(0);
    String score= "*";
    String assignID="";
    String groupID="";
    /* grade will be null if this student dropped before this assignment and doesn't have a record for it */
    if (grade != null)
    {
    	score = (grade.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE())) ? grade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) : "*";
    	assignID= grade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID());
    	groupID= grade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_GROUPID());
    }
     %>
              <%-- The next line is real long, but don't split it up 
                   or the sorttable will break
                --%>
              <td align="center"><% if (score.equals("*")) { %><%=hiddenAppend%>*<% } else { %><%= score %><%=hiddenAppend%><% } %></td>
<%
  } %>
              <%-- The next line is real long, but don't split it up 
                   or the sorttable will break
                --%>
              <td align="center"><% if (totalscore.equals("")) { %><%=hiddenAppend%>*<% } else {%><%=totalscore %><%=hiddenAppend%><% } %></td>
              <td class="finalgrade" align="center">
                <%-- <input type="text" size="2" name="<%= AccessController.P_FINALGRADE + student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %>" value="<%= student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FINALGRADE()) %>"> --%>
                <select name="<%= AccessController.P_FINALGRADE + student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %>" id="<%= AccessController.P_FINALGRADE + student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %>" disabled="true">
                  <% String finalGrade = student.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FINALGRADE()); %>
                  <option value="null"<%= finalGrade.equals("") ? " selected" : "" %>></option>
                  <option value="A+"<%= finalGrade.equals("A+") ? " selected" : "" %>>A+</option>
                  <option value="A"<%= finalGrade.equals("A") ? " selected" : "" %>>A</option>
                  <option value="A-"<%= finalGrade.equals("A-") ? " selected" : "" %>>A-</option>
                  <option value="B+"<%= finalGrade.equals("B+") ? " selected" : "" %>>B+</option>
                  <option value="B"<%= finalGrade.equals("B") ? " selected" : "" %>>B</option>
                  <option value="B-"<%= finalGrade.equals("B-") ? " selected" : "" %>>B-</option>
                  <option value="C+"<%= finalGrade.equals("C+") ? " selected" : "" %>>C+</option>
                  <option value="C"<%= finalGrade.equals("C") ? " selected" : "" %>>C</option>
                  <option value="C-"<%= finalGrade.equals("C-") ? " selected" : "" %>>C-</option>
                  <option value="D+"<%= finalGrade.equals("D+") ? " selected" : "" %>>D+</option>
                  <option value="D"<%= finalGrade.equals("D") ? " selected" : "" %>>D</option>
                  <option value="D-"<%= finalGrade.equals("D-") ? " selected" : "" %>>D-</option>
                  <option value="F"<%= finalGrade.equals("F") ? " selected" : "" %>>F</option>
                  <option value="S"<%= finalGrade.equals("S") ? " selected" : "" %>>S</option>
                  <option value="U"<%= finalGrade.equals("U") ? " selected" : "" %>>U</option>
                  <option value="INC"<%= finalGrade.equals("INC") ? " selected" : "" %>>INC</option>
                  <option value="AUD"<%= finalGrade.equals("AUD") ? " selected" : "" %>>AUD</option>
                </select>
              </td>
              <td align="center">
                <a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_REENROLL + "&amp;" +
                              AccessController.P_COURSEID + "=" + courseid  + "&amp;" +
                              AccessController.P_NETID + "=" + netID + "&amp;" +
                              AccessController.P_EMAILADDED + "=on"%>">Re-Enroll (and notify)</a><br/>
                <a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_REENROLL + "&amp;" +
                              AccessController.P_COURSEID + "=" + courseid  + "&amp;" +
                              AccessController.P_NETID + "=" + netID + "&amp;"%>" style="font-size:smaller">(Re-enroll silently)</a>
              </td>
            </tr>
<%
} %>
          </table>         </div>
 <%
} %>
 <%----------------- End dropped --------------------%>          
      </div>
        </div>
    </td>
    <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
    <script type="text/javascript">
    	<%-- initialize sortable tables; see sorttable.js --%>
    	sortables_init();
	    <%-- Resort table by previous sort ordering --%> 
    	var lnk_id = getCookie('students_table_coln');
    	var sort_dir = getCookie('students_table_sortdir');
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
  </tr>
<jsp:include page="../footer.jsp"/>
