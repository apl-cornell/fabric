<%@page language="java" import="org.w3c.dom.*, cms.www.*, cms.model.*, cms.www.xml.*"%><%
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getFirstChild();
Element assign= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT); 
Element groupsnode= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_GROUPS);
Element probsnode= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_SUBPROBS);
NodeList groups= groupsnode.getChildNodes();
NodeList probs= probsnode.getChildNodes();
int numgroups= groups.getLength();
int numprobs= probs.getLength();
String assignid= assign.getAttribute(XMLBuilder.A_ID);
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
String courseid= course.getAttribute(XMLBuilder.A_COURSEID);
int assignType = Integer.parseInt(assign.getAttribute(XMLBuilder.A_ASSIGNTYPE));
boolean isAssignment = (assignType == Assignment.ASSIGNMENT);
boolean hasSection = course.hasAttribute(XMLBuilder.A_HASSECTION);
boolean isAdmin = course.hasAttribute(XMLBuilder.A_ISADMIN);
boolean isGrades = course.hasAttribute(XMLBuilder.A_ISGRADES);
boolean isGroups = course.hasAttribute(XMLBuilder.A_ISGROUPS);
boolean assignedGraders = assign.hasAttribute(XMLBuilder.A_ASSIGNEDGRADERS);
int assignedCount = 0;
%>
<link href="calstyle.css" rel="stylesheet" type="text/css">
<script src="CalendarPopup.js" type="text/javascript"></script>
<script type="text/javascript" src="staff/grading/gradeassign.js"></script>
<script type="text/javascript">
//store some info for the show-problem-grades javascript to use later
var numprobs = <%= numprobs %>;
</script>
<style type="text/css">
  .noaccess {color: #999999}
</style>
<input type="hidden" id="ExtensionGroupID" name="<%= AccessController.P_EXTGROUPID %>" value="">
<table class="sortable" id="grades_table_<%= assignid %>" cellpadding="0" cellspacing="0">
  <tr>
    <th class="nosort">&nbsp;</th>
    <th class="nosort">&nbsp;</th>
    <th nowrap align="center" id="~1" >NetID</th>
    <th nowrap align="center">Group</th>
<% if (hasSection) { %>
    <th nowrap align="center">Section</th>
<% } %>
<% if (isAdmin || isGrades) { %>
    <th nowrap class="scores" align="center">Extension</th>
    <% if (isAssignment) { %>
    <th nowrap align="center">Files remaining</th>
    <% } %>

<%--- ASSIGNEDTO HEADER CELLS BEGIN -----%>

		<%
		if (numprobs < 2) { %>
		    <th nowrap style="display: none" name="assigned" id="assigned_<%= assignedCount++ %>" align="center" ><%
		} else { %>
		    <th nowrap style="display: none" name="assigned" id="assigned_<%= assignedCount++ %>" align="center" class="nosort" colspan="<%= numprobs %>"><%
		} %>
		      Assigned To
		    </th> 

<%--- ASSIGNEDTO HEADER CELLS END -----%>
    
    <th nowrap align="center" class="scores" id="gradeheader" colspan="<%= numprobs + 1 %>">Score</th>
    <th class="nosort">&nbsp;</th>
<% } %>
  </tr>

  <tr class="nosort" <%if (numprobs < 2) { %>style="display:none;"<% } %> >
    <th class="nosort" >&nbsp;</th>
    <th class="nosort" >&nbsp;</th>
    <th class="nosort" >&nbsp;</th>
    <th class="nosort" >&nbsp;</th>
<% if (hasSection) { %><th class="nosort">&nbsp;</th><% } %>
<% if (isAdmin || isGrades) { %>
	<% if (isAssignment) { %>
    <th class="nosort" >&nbsp;</th>
    <% } %>
    <th class="nosort" >&nbsp;</th>
<%--- ASSIGNEDTO HEADER CELLS BEGIN -----%>
<%	for (int i= 0; i != numprobs; i++) {
		Element prob= (Element)probs.item(i);%>
    <th nowrap style="display: none" align="center" class="scores" id="assigned_<%= assignedCount++ %>" name="assigned"><%= prob.getAttribute(XMLBuilder.A_NAME) %></th><%
	}%>
<%--- ASSIGNEDTO HEADER CELLS END -----%>
<%--- SUBPROBLEM GRADE HEADER CELLS BEGIN -----%>
<% 	for (int j= 0; j < numprobs; j++) {
		Element prob= (Element)probs.item(j);
		String text = (isAssignment) ? prob.getAttribute(XMLBuilder.A_NAME) : prob.getAttribute(XMLBuilder.A_ORDER); %>
	<th nowrap align="center" class="subprob_score_cell"><%= text %></th>
<%  }%>

	<th nowrap align="center" class="subprob_score_cell">Total</th>
<%--- SUBPROBLEM GRADE HEADER CELLS END -----%>

    <th align="center" id="gradespace" class="nosort" colspan="2">&nbsp;</th>
<% } %>
  </tr>
<%	
for (int i= 0; i != numgroups; i++)
	{
  Element group= (Element)groups.item(i);
  String groupid= group.getAttribute(XMLBuilder.A_GROUPID);
  int remainingsubs= Integer.parseInt(group.getAttribute(XMLBuilder.A_REMAININGSUBS));
  NodeList members= group.getElementsByTagName(XMLBuilder.TAG_MEMBER); 
  int nummembers= members.getLength();
  String lateTag = group.hasAttribute(XMLBuilder.A_LATESUBMISSION) ? " style=\"background-color: rgb(218, 186, 186);\"" : ""; 
  /* 0 for the total score */
  Element grade= (Element)group.getElementsByTagNameNS(XMLBuilder.TAG_GRADE + "0", XMLBuilder.TAG_GRADE).item(0); 
  Element regrade= (Element)group.getElementsByTagName(XMLBuilder.TAG_REGRADE).item(0);
  String status= regrade != null ? regrade.getAttribute(XMLBuilder.A_STATUS) : ""; 
  boolean overMax= (grade==null ? false : grade.hasAttribute(XMLBuilder.A_OVERMAX));
  boolean isAveraged = (grade == null) ? false : grade.hasAttribute(XMLBuilder.A_ISAVERAGE);
  boolean hasExtension=group.hasAttribute(XMLBuilder.A_EXTENSION);
  boolean canGrade = isAdmin || (isGrades && (!assignedGraders || group.hasAttribute(XMLBuilder.A_CANGRADE)));
   %>
  <tr <%
  if (status.equals(RegradeRequest.PENDING)) { %> class="pending"<%
  } else if (grade == null) {%> class="ungraded"<%
  } %>>
    <td class="index" id="cg<%= groupid %>"<%= lateTag %>><%= i + 1 %></td>
    <td<%= lateTag %>><input type="checkbox" name="<%= AccessController.P_GRADEGROUP + groupid %>"></td>
	<td<%= lateTag %>><%
  String netids= "";
  for (int j= 0; j != members.getLength(); j++) {
    Element member= (Element)members.item(j);
    String startTag = null, endTag = null;
     String gradeURL = "?" + AccessController.P_ACTION + "=" + AccessController.ACT_STUDENTALLGRADES + "&" 
          + AccessController.P_COURSEID + "=" + courseid + "&" + AccessController.P_NETID + "=" + member.getAttribute(XMLBuilder.A_NETID);
    if (isAdmin) {
      startTag = "<a href=\"" + gradeURL + "\">";
      endTag = "</a>";
    } else {
      startTag = "";
      endTag = "";
    }
%>
    <%= startTag + member.getAttribute(XMLBuilder.A_NETID) + endTag %>
    <% if (j!=members.getLength()-1) { %> <br> <% } %>
    
    
<%  } %>
 	
 </td>
 <%--                          GROUPS                    --%>     
    <td nowrap<%= lateTag %>><%
    String section = "";
  for (int j= 0; j != members.getLength(); j++) {
    Element member= (Element)members.item(j);
    if (member.hasAttribute(XMLBuilder.A_SECTION)) {
    	section = member.getAttribute(XMLBuilder.A_SECTION);
    	if ((section == "") || (section == null)) section = "&nbsp;";
    }
%>
   <%=  member.getAttribute(XMLBuilder.A_LASTNAME) + ", " + member.getAttribute(XMLBuilder.A_FIRSTNAME)+" "%>
    <% if (j!=members.getLength()-1) { %> <br> <% } %>
    
    
<%  } %>
    </td>
<% if (isAdmin || isGrades) { %>
  <% String hiddenAppend;
     if (hasExtension) {
        hiddenAppend= "<div style=\"display: none;\">"+group.getAttribute(XMLBuilder.A_EXTVAL)+"</div>";
    } else {
    hiddenAppend= "<div style=\"display: none;\">A</div>";
    
    
  }
  
  %>
 <%--                          SECTION                      --%>
 <% if (hasSection) { %>
	<td align="center"<%= lateTag %>>
		<%= section %>
	</td>
 <% } %>
 <%--                          EXTENSION                    --%>
    <td align="center"<%= lateTag %>>      <%=hiddenAppend%>
    <%
    String extAMPM = group.hasAttribute(XMLBuilder.A_EXTAMPM) ? group.getAttribute(XMLBuilder.A_EXTAMPM) : assign.getAttribute(XMLBuilder.A_DUEAMPM); 
    String extTime = group.hasAttribute(XMLBuilder.A_EXTTIME) ? group.getAttribute(XMLBuilder.A_EXTTIME) : assign.getAttribute(XMLBuilder.A_DUETIME);
	if (hasExtension) {%>
	  <div style="background-color: #FFFFCC; text-align: center">
         <b>Extended Until:</b><br>
         <%=group.getAttribute(XMLBuilder.A_EXTENSION)%>, <%=extTime%> <%=extAMPM%>&nbsp;<%
    } else { %>
      <div><%
    } %>
		<font size="1">  
		  (<span class="hide" id="date_<%=groupid%>head"><a class="hide" href="#" onclick="show('date_<%=groupid%>', 'Edit', 'Hide'); return false;">Edit</a>)  </span>
		</font><br/>
		<div class="cal" id="dateselect_<%=groupid%>"></div>
		<div class="showhide" id="date_<%=groupid%>" style="display:none">
          <div class="dateblock" >
            <script type="text/javascript">
              var cal= new CalendarPopup();
            </script>
            <input id="datebox_<%=groupid%>" type="text" size="17" name="<%= AccessController.P_DUEDATE + groupid%>" value="<%= hasExtension ? group.getAttribute(XMLBuilder.A_EXTENSION) : assign.getAttribute(XMLBuilder.A_DUEDATE) %>">
            <a href="#" id="datelink_<%=groupid%>" name="datelink" onclick="cal.select(getElementById('datebox_<%=groupid%>'), 'datelink_<%=groupid%>', 'MMM d, yyyy'); return false;">
              <img class="calicon" src="../images/cal.gif" alt="Select" width="16px" height="16px">
            </a><br>
            <span class="example">e.g. <%= root.getAttribute(XMLBuilder.A_EXAMPLEDATE) %></span><br>
            <input type="text" size="5" name="<%=AccessController.P_DUETIME%><%=groupid%>" value="<%=extTime%>">
            <select name="<%=AccessController.P_DUEAMPM%><%=groupid%>">
              <option<%="AM".equals(extAMPM) ? " selected" : "" %>>AM</option>
              <option<%="PM".equals(extAMPM) ? " selected" : "" %>>PM</option>
            </select>
          </div><%
          if (hasExtension) { %> 
		  <input style="width:5em" type="submit" onclick="var i = getElementById('ExtensionGroupID'); i.value='<%= groupid %>'; return true;" value="<%= AccessController.GA_CHANGE %>" id="files" name="<%= AccessController.P_SUBMIT %>">
		  <input style="width:5em" type="button" onclick="window.location='?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_REMOVEEXTENSION%>&amp;<%=AccessController.P_GROUPID%>=<%=groupid%>&amp;<%=AccessController.P_COURSEID%>=<%=courseid%>'" value="Remove" >
	    </div><%
	      } else { %>
          <input style="width:5em" type="submit" onclick="var i = getElementById('ExtensionGroupID'); i.value='<%= groupid %>'; return true;" value="<%= AccessController.GA_GRANT %>" id="files" name="<%= AccessController.P_SUBMIT %>"> </div>
        </div><%
          }%> 
      </div>
		</td>
<%--                          EXTENSION                    --%>       
	<% if (isAssignment) { %>
    	<td align="center"<%= lateTag %>>
      	<%= remainingsubs %> 
    	</td>
    <% } %>
<%------------------------------ Assigned To Columns BEGIN-------------------------------------%>

<%
  String assignee="";
  if (numprobs == 0) {
    Element assignedto= (Element)group.getElementsByTagNameNS(XMLBuilder.TAG_ASSIGNEDTO + "0", XMLBuilder.TAG_ASSIGNEDTO).item(0); 
    if (assignedto != null) { 
        assignee= assignedto.getAttribute(XMLBuilder.A_FIRSTNAME)+" "+assignedto.getAttribute(XMLBuilder.A_LASTNAME);
    }
  %>
    <td nowrap align="center" style="display: none" id="assigned_<%= assignedCount++ %>" name="assigned"<%= lateTag %>>
	<% if (assignedto == null) { %>
                &nbsp;
        <% } else { %>
            <div onMouseover="ddrivetip('<%=assignee%>')"; onMouseout="hideddrivetip()" >
            <%=assignedto.getAttribute(XMLBuilder.A_NETID) %></div>  
        <% }%>
</td><%
  }
  for (int j= 0; j != numprobs; j++) { 
    Element prob= (Element)probs.item(j); 
    String id= prob.getAttribute(XMLBuilder.A_SUBPROBID); 
    Element assignedto= (Element)group.getElementsByTagNameNS(XMLBuilder.TAG_ASSIGNEDTO + id, XMLBuilder.TAG_ASSIGNEDTO).item(0); 
  if (assignedto!=null)
    assignee= assignedto.getAttribute(XMLBuilder.A_FIRSTNAME)+" "+assignedto.getAttribute(XMLBuilder.A_LASTNAME);
		    %>
    <td nowrap align="center" style="display: none" id="assigned_<%= assignedCount++ %>" name="assigned"<%= lateTag %>>



		<% if (assignedto == null) { %>
                &nbsp;
        <% } else { %>
            <div onMouseover="ddrivetip('<%=assignee%>');" onMouseout="hideddrivetip();">
            <%=assignedto.getAttribute(XMLBuilder.A_NETID) %></div>  
        <% }%>



</td><%
  } %>

<%------------------------------ Assigned to Columns END -------------------------------------%>
<%--------------------------- Subproblem grade Columns BEGIN ---------------------------------%>

<%	boolean overMaxProb = false;
	for (int j = 0; j < numprobs; j++) {
		Element prob= (Element)probs.item(j);
		Element probGrade= (Element)group.getElementsByTagNameNS(XMLBuilder.TAG_GRADE + prob.getAttribute(XMLBuilder.A_SUBPROBID), XMLBuilder.TAG_GRADE).item(0);
		overMaxProb = (probGrade==null ? false : probGrade.hasAttribute(XMLBuilder.A_OVERMAX));%>
	<td nowrap align="right" class="subprob_score_cell"<%= group.hasAttribute(XMLBuilder.A_LATESUBMISSION) ? " style=\"background-color: rgb(218, 186, 186)\"" : "" %>">
<%		if (overMaxProb) {%>
		<img src="images/warning.gif" height="15px" alt="(Over Max Score)">
<%		}%>
		<%= probGrade == null ? " " : probGrade.getAttribute(XMLBuilder.A_SCORE) %>
	</td>
<%	}%>

<%---------------------------- Subproblem grade Columns END ----------------------------------%>

    <td nowrap align="right"<%= lateTag %>>&nbsp;
<%	if (status.equals(RegradeRequest.PENDING)) {%>
      <img src="images/tag_red.gif" alt="(Regrade pending)"><%
	} else if (status.equals(RegradeRequest.REGRADED)) {%>
      <img src="images/tag_orange.gif" alt="(Regraded)"><%
	}
	if (overMax) {%>
     <img src="images/warning.gif" height="15px" alt="(Over Max)">
<%	} %>
		<%= (grade == null) ? "&nbsp;" : grade.getAttribute(XMLBuilder.A_SCORE) %><%
  	if (isAveraged) {%>
		<b>~</b>
<%	}%>
    </td>
    <td nowrap<%= lateTag %>>
    
    <% if (isAssignment) {%> 
    (
    <%}%>
    <% if (canGrade) { %>
        <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_GRADESTUDENTS %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignid %>&amp;<%= AccessController.P_GROUPID %>=<%= groupid %>">Grade</a>
      <% } else { %>
        <span class="noaccess">Grade</span>
      <% } %>
    
    <% if (isAssignment) {%>
	|
      <% if (canGrade) { %>
         <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_GROUPFILES %>&amp;<%= AccessController.P_GROUPID %>=<%= groupid %>">Files</a>
      <% } else { %>
         <span class="noaccess">Files</span>
      <% } %> 
    )
	<%}%>
    </td>
<% } %>
	</tr><%
} %>
</table>
<br>
