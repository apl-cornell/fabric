<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, java.util.*, cms.model.*, cms.auth.*, cms.www.util.*" %><%
/**********************************************************
* grade one or more students for a specific assignment
**********************************************************/
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getFirstChild();
String meNetID = ((User) session.getAttribute(AccessController.A_PRINCIPAL)).getNetID();
Element course= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_COURSE());
String courseID = course.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COURSEID());
String asgnID = root.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID());
Element assigns = (Element)course.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_ASSIGNMENTS()).item(0);
Element assignment= (Element)XMLUtil._Proxy.getChildrenByTagNameAndAttributeValue(assigns, XMLBuilder._Static._Proxy.$instance.get$TAG_ASSIGNMENT(), XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID(), asgnID).item(0);
NodeList groups= root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_GROUP());
int numgroups= groups.getLength();
NodeList submissions = root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_SUBMISSION());
NodeList allprobs= root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_SUBPROBLEM());
// Find total score probs node & fill in other probs collection
Element totalprob= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_TOTALPROBLEM());
Collection probs= new ArrayList();
for (int j= 0; j != allprobs.getLength(); j++) {
  Element prob= (Element)allprobs.item(j);
  probs.add(prob);
} 
int numprobs= probs.size();
boolean probscol= numprobs > 0;
boolean isAdmin = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISADMIN());
boolean isGrades = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISGRADES());
boolean assignedGraders = assignment.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNEDGRADERS());
boolean checkCanGrade = !isAdmin && assignedGraders;
boolean isQuiz = Integer.parseInt(assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE())) == Assignment._Static._Proxy.$instance.get$QUIZ() || Integer.parseInt(assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNTYPE())) == Assignment._Static._Proxy.$instance.get$SURVEY();
%><jsp:include page="../../header.jsp"/>
<style type="text/css">
  td.comments {text-align: center; width: 21em}
  td.comments h1 {font-size: medium; font-weight: bold}
  td.comments textarea {display: block; margin: 0em auto}
  td.files {width: 10em; padding: 1em}
  #course_page td.files dl {margin: 0em; padding: 0em}
  #course_page td.files dl dt {margin: 0em; padding: 0em; float: none}
  #course_page td.files dl dd {margin: 0em; padding: 0em; float: none; font-size: smaller}
  #course_page td.files ul.oldfiles {font-size: smaller; color: #aaa; margin-top: .5em}
  #course_page td.files ul.oldfiles a {color: #888}
  td.students {padding: 1em; margin: 0em}
  td.linkscores {text-align: center}
  td.scores {padding: 1em; margin: 0em}
  td.logs div table {width: 100%}
  td.logs table td, td.logs table th {border: 0px; text-align: left}
  td.regrades, td.logs {font-size: 0.8em}
  td.regrades h1, td.logs h1 {font-size: 100%; font-weight: bold}
  td.regrades h2, td.logs h2 {font-size: smaller; font-weight: normal}
  td.regrades dl {display: none}
  .gradeall_table {margin: 0em 0em}
  .gradeall_table td ul {list-style-type: none; margin: 0em; padding: 0em}
  .gradeall_table td ul li {margin: 1em 0em; white-space: nowrap}
  span#assign_name {font-size: 12px; font-weight: bold}
   a.whitelink {color: white}   
  .no_borders {border: 0px; border-style: none; border-bottom: 0px; border-right: 0px; width: 100%}
  .no_borders td tr {border: none; border-width: 0px; border-style: none}
  .bottom_hug {vertical-align: bottom; text-align: center}
  .top_hug {vertical-align: top; text-align: left}
  .noaccess {color: #999999 }
</style>
<script type="text/javascript">
  // Array of all score boxes on this page that can be linked
  // Boxes are indexed by [groupID][probID][netID]
  var scores= new Array();
  var probnames= new Array();<%
for (int i= 0; i != numgroups; i++) { 
  Element group= (Element)groups.item(i); 
  String groupID= group.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_GROUPID()); %>
  scores['<%= groupID %>']= new Array();<%
  if (probs.size() == 0) {%>
  	scores['<%= groupID %>']['0'] = new Array();
<%} else {
  	for (Iterator j= probs.iterator(); j.hasNext();) {
    	Element prob= (Element)j.next();
    	String probID= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID()); %>
  		scores['<%= groupID %>']['<%= probID %>']= new Array();<%
    }
  }
} 
for (Iterator j= probs.iterator(); j.hasNext();) {
	Element prob= (Element)j.next();
    String probID= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID()); 
    String probName = prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()); %>
	probnames['<%= probID %>'] = '<%= probName %>';<%
}%>
   
  /* onkeyup handler for grade text boxes */
  function onkeyupHandler(mynetID, myprobID, groupID) {
    // Update the total at the bottom of this section
    var totalscore= 0;
    // Get all grade text boxes & calculate total score
    for (var probID in scores[groupID]) {
      var scorebox= scores[groupID][probID][mynetID];
      var score= parseFloat(scorebox.value);
      if (!isNaN(score))
      totalscore+= score;
    }
    
    // Fill in total score section
    totalspan= getElementById('totalscore_' + mynetID + '_' + groupID);
    txt= totalspan.firstChild;
    txt.data= totalscore;
  
    // Check if the scores are linked for this group
    var linkbox= getElementById('link_' + groupID);
    if (linkbox != null && linkbox.checked) {
      // Get the score for this problem for this student
      var thisbox= scores[groupID][myprobID][mynetID];
      var thisscore= thisbox.value;
      // Go through all the boxes for this problem in this group
      for (var netID in scores[groupID][myprobID]) {
        // Don't bother if this is the netID for the box that was changed
        if (netID != mynetID) {
          // Set score box
          scores[groupID][myprobID][netID].value= thisscore;
          // Fill in total score section for each student
          totalspan= getElementById('totalscore_' + netID + '_' + groupID);
          txt= totalspan.firstChild;
          txt.data= totalscore;
        }
      }
    }
  }
  
  /* Keyup handler for input text boxes when the assignment has one or zero subproblems
   * Give input myprobID = 0 for an assignment with no subproblems */
  function onKeyupHandlerNoProbs(mynetID, groupID, myprobID) {
  	// Check if the scores are linked for this group
    var linkbox= getElementById('link_' + groupID);
    if (linkbox != null && linkbox.checked) {
      var thisbox= scores[groupID][myprobID][mynetID];
      var thisscore= thisbox.value;
      for (var netID in scores[groupID][myprobID]) {
        // Don't bother if this is the netID for the box that was changed
        if (netID != mynetID) {
          // Set score box
          scores[groupID][myprobID][netID].value= thisscore;
        }
      }
  	}
  }
  
  /* Onclick handler for link scores checkboxes */
  function linkScoresHandler(groupID) {
    // Go through all of this group's score boxes, looking for ones that are different
    for (var probID in scores[groupID]) {
      var probscore= null;
      var same = true;
      for (var netID in scores[groupID][probID]) {
        if (scores[groupID][probID][netID].value)
          probscore= probscore == null ? scores[groupID][probID][netID].value : probscore;
      }
      if (probscore == null) continue;
      for (var netID in scores[groupID][probID]) {
     	if (probscore != scores[groupID][probID][netID].value)
     	  same= false;
      }
      if (same) continue;
      var probmsg = "";
      if ( probID > 0 ) probmsg = 'for ' + probnames[probID];
      var msg= 'Grades ' + probmsg + ' are different.\n' + 
               	'Please enter a new grade.';
      // Get desired score
      probscore= prompt(msg, probscore);
      if (probscore == null) continue;
      for (var netID in scores[groupID][probID]) {
		// Fill in everywhere
	    scores[groupID][probID][netID].value= probscore;
      }
    }
  }
  
  /* Shows the logs/regrades */
  function showentry(id) {
    changeDisplay(id, 'block');
    var link= getElementById(id + 'link');
    link.onclick= new Function('hideentry(\'' + id + '\'); return false;'); 
  }
  
  function hideentry(id) {
    changeDisplay(id, 'none');
    var link= getElementById(id + 'link');
    link.onclick= new Function('showentry(\'' + id + '\'); return false;'); 
  }
  
  function allClick(groupid){
    var cell = getElementById('RegradeReqCheckboxes' + groupid);
    var checkBoxes = cell.getElementsByTagName('input');
    for (var i=1; i<checkBoxes.length; i++)
     checkBoxes[i].checked = true;
    return false;
  }
  
  function otherClick(groupid){
    var allCheckbox = getElementById('allParts' + groupid);
    allCheckbox.checked = false;
    return false;
  }
  
  function checkLinkedComments(name, chk) {
    var i = 1;
    var box = getElementById(name + i);
    while (box != null) {
      box.checked = chk;
      box = getElementById(name + (++i));
    }
    return true;
  }
</script>
<jsp:include page="../../header-page.jsp"/>
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
<jsp:include page="../navbar.jsp"/>
    <td id="course_page_container">
      <div id="course_page">
        <span class="assignment_title"><%= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %>- Grade Students</span>
        <br><br>
				<jsp:include page="../../problem-report.jsp"/>
        <form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_SETSTUDENTGRADES %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID()) %>" method="post" enctype="multipart/form-data">
<%
/* if there's a cookie, sort groups according to it */
Cookie[] cookies = (Cookie[])session.getAttribute(AccessController.A_COOKIES);
System.out.println("looking for cookie 'grades_table_" + courseID + "_" + asgnID + "_sort_order'");
String groupOrder = null;
if (cookies != null)
{
	for (int i = 0; i < cookies.length; i++)
	{
	System.out.println("found cookie w/name '" + cookies[i].getName() + "'");
		if (cookies[i].getName().equals("grades_table_" + courseID + "_" + asgnID + "_sort_order"))
		{
			groupOrder = cookies[i].getValue();
			break;
		}
	}
}
ArrayList sortedGroups = new ArrayList(); //group Elements in sorted order as set on the main grading page
if (groupOrder != null)
{
	String[] groupIDOrder = groupOrder.replaceAll("%20", " ").split("\\s+");
	for (int i = 0; i < groupIDOrder.length; i++)
	{
		System.out.println("group id: '" + groupIDOrder[i] + "'");
		for (int j = 0; j < numgroups; j++)
		{
			System.out.println(" group id: '" + ((Element)groups.item(j)).getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_GROUPID()) + "'");
			if (((Element)groups.item(j)).getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_GROUPID()).equals(groupIDOrder[i]))
				sortedGroups.add((Element)groups.item(j));
		}
	}
}
else /* don't reorder groups */
{
	for (int i = 0; i < numgroups; i++)
		sortedGroups.add((Element)groups.item(i));
}
/* make a table for each group */
for (int i= 0; i < numgroups; i++) { 
  Element group= (Element)sortedGroups.get(i);
  String groupid= group.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_GROUPID());
  NodeList members= group.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_MEMBER());
  int nummembers= members.getLength(); 
  NodeList files= group.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_FILE());
  int numfiles= files.getLength(); 
  NodeList oldfiles= group.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_OLDFILE()); 
  int numoldfiles= oldfiles.getLength();
  NodeList regrades= group.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_REGRADE());
  NodeList comments= group.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_COMMENT());
  int numregrades= regrades.getLength(); 
  int numcomments= comments.getLength();
  int pendingregrades;
  NodeList logs= group.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADELOG());
  int numlogs= logs.getLength(); %>
        <input type="hidden" name="<%= AccessController.P_GROUPID + i %>" value="<%= groupid %>">
        <table class="gradeall_table" id="group_<%= groupid %>" cellpadding="0" cellspacing="0" >
          <tr>
            <th colspan="<%= probscol ? "5" : "4" %>"><%
  for (int j= 0; j != nummembers; j++) { 
    Element member= (Element)members.item(j); %>
              <%= j == 0 ? "" : "," %>
               <%= member.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FIRSTNAME()) %> <%= member.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_LASTNAME()) %>
                
                  (
			              <% if (isAdmin) { %>
			                <a style="color:white;" href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_STUDENTALLGRADES %>&amp;<%= AccessController.P_COURSEID %>=<%= courseID %>&amp;<%= AccessController.P_NETID %>=<%= member.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %>">
			              <% } %>
		 								<%= member.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %>
			              <% if (isAdmin) { %>
			                </a>
			              <% } %>
                  )
           
<% } %>
            </th>
          </tr>
          <tr>
          
          <%--Files------------------------------------%>
         <%if (!isQuiz){%>
            <td>Files <%= submissions.getLength() == 0 ? "" : "<span id='upload_" + groupid + "head'><a href=\"#\" onclick=\"show('upload_" + groupid + "', '(upload)', '(upload)'); return false;\">(upload)</a></span>" %></th>
            <%}%>
            <td>Students</td>
            <%=isQuiz ? "<td>Answers</td>" : ""%>
            <td><%= probs.size() > 1 ? "Scores" : "Score" %></td>
            <td>Comments</td>           
          </tr>
          <tr>
          <%if (!isQuiz){%>
	            <td valign="top" class="files"<%= nummembers == 1 ? "" : " rowspan=\"" + Integer.toString(nummembers + 1) + "\"" %>>
	     <% if (submissions.getLength() == 0) { %>
	                No required submissions.
	     <% } else { %>
	              <span class="top_hug">
	    		<% if (numfiles == 0) { %>
	                No submissions.
	              <% } else { %>
	              <ul><%
	      for (int j= 0; j != numfiles; j++) { 
	        Element file= (Element)files.item(j);%>
	                <li>
	                  <dl>
	                    <dt><a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_DOWNLOAD %>&amp;<%= AccessController.P_DOWNLOADTYPE %>=<%= XMLBuilder._Static._Proxy.$instance.get$T_GROUPFILE() %>&amp;<%= AccessController.P_ID %>=<%= file.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBMITTEDFILEID()) %>">
	                      <%= file.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FILENAME()) %>
	                    </a></dt>
	                    <dd><%= file.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DATE()) %></dd>
	                  </dl>
	                </li><%
	      } %>           
	              </ul><%
	  } 
	  if (numoldfiles != 0) { %>
	              <ul class="oldfiles"><%
	      for (int j= 0; j != numoldfiles; j++) { 
	        Element oldfile= (Element)oldfiles.item(j);%>
	                <li>
	                  <dl>
	                    <dt><a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_DOWNLOAD %>&amp;<%= AccessController.P_DOWNLOADTYPE %>=<%= XMLBuilder._Static._Proxy.$instance.get$T_GROUPFILE() %>&amp;<%= AccessController.P_ID %>=<%= oldfile.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBMITTEDFILEID()) %>">
	                      <%= oldfile.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FILENAME()) %>
	                    </a></dt>
	                    <dd><%= oldfile.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DATE()) %></dd>
	                  </dl>
	                </li><%
	      } %>           
	              </ul><%
	  }%></span><br>
	              <div class="bottom_hug" id="upload_<%= groupid %>" style="display: none">
	              <table class="no_borders" cellpadding="0" cellspacing="0" width="100%" height="100%">
	              <% for (int j=0; j < submissions.getLength(); j++) { 
	                    Element sub = (Element) submissions.item(j); 
	                    String subID = sub.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ID()); %>
	                <tr valign="bottom"><td valign="bottom" align="left" style="border: none">
	                  <%= sub.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) + ": " %>
	                </td><td valign="bottom" align="right" style="border: none">
	                  <input size="5" type="file" name="<%= AccessController.P_SUBMITTEDFILE + "_" + groupid + "_" + subID %>">
	                </td></tr>
	              <% } %></table>
	            </div>
	            <% } %>
	            </td><%
	     }%>
	     
       <%--Members------------------------------------%>     
  <%if (nummembers != 1) { %>
            <td class="linkscores" colspan="<%= probscol ? "3" : "2" %>">
              <label>
                <input onclick="if (this.checked) linkScoresHandler('<%= groupid %>');" id="link_<%= groupid %>" type="checkbox" name="link_<%= groupid %>">
                Link Scores
              </label>
            </td><%
  } else { 
    Element member= (Element)members.item(0);
    String netID= member.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID());
    NodeList probscores= member.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()); %>
						<td class="students">
						  <% if (isAdmin) { %>
                <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_STUDENTALLGRADES %>&amp;<%= AccessController.P_COURSEID %>=<%= courseID %>&amp;<%= AccessController.P_NETID %>=<%= netID %>"><%= netID %></a>
              <% } else { %>
                <%= netID %>
              <% } %>
            </td>

<%--Answers--%>

<%
	if (isQuiz)
	{
		%><td style="vertical-align:top"><br><%
		int answerCount = 0;
		for (Iterator k= probs.iterator(); k.hasNext();) {
            Element prob= (Element)k.next();
			String probid= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID());
			Element answer = (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_ANSWERS() + probid, XMLBuilder._Static._Proxy.$instance.get$TAG_ANSWERS()).item(0); 
			String probNumber = prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ORDER());
			String problemPrefix = (probNumber.equals("0")) ? "" : probNumber + ". ";
			
			if (answer != null)
			{
				answerCount++;
				%>
					<%=problemPrefix + answer.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ANSWERTEXT())%>
				<br>
				<%
			}	
		}
		if (answerCount == 0)
		{
			%>No answers submitted<%
		}
		%></td><%
	}
%>


<%---------------------------------------------------------------------------------------------------------%>

            <td class="scores" style="vertical-align:top"><%
      Element totalgrade= (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE() + totalprob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID()), XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()).item(0);
      boolean hasTotal = totalgrade != null;
      System.out.println("Numprobs: " + numprobs);
      if (numprobs == 0 && hasTotal) { 
         Element score= (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE() + "0", XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()).item(0);
         boolean canGrade = !checkCanGrade || (score != null && score.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CANGRADE()));
              %>
				<input type="hidden" name="<%= AccessController.P_OLDGRADE + "_" + netID + "_0_" + groupid %>" value="<%= score != null ? score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) : "" %>">
              <ul><li><input id="totalscore_<%= netID + "_" + groupid %>" type="text" onkeyup="onKeyupHandlerNoProbs('<%= netID %>','<%= groupid %>','0');" size="3" name="<%= AccessController.P_GRADE %>_<%= netID %>_0_<%= groupid %>" <%= score != null ? "value=\"" + score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) + "\"" : "value=\"\"" %><%= canGrade ? "" : " disabled" %>> /<%= totalprob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></li></ul>
            <script type="text/javascript">
            	// Place above input in table
            	scores['<%= groupid %>']['0']['<%= netID %>']=  getElementById('totalscore_<%= netID + "_" + groupid %>');
				</script>
            

<%
      } else {
        if (numprobs == 1) {
          Element prob= (Element)probs.iterator().next();
          String probid= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID());
          Element score= (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE() + probid, XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()).item(0); 
 		  Element answer = (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_ANSWERS() + probid, XMLBuilder._Static._Proxy.$instance.get$TAG_ANSWERS()).item(0); 
          
          String problemOrder = prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ORDER());
          String problemName = (problemOrder.equals("0")) ? prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) : problemOrder + ". " + prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME());
          
          boolean canGrade = !checkCanGrade || (score != null && score.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CANGRADE()));
          %>
			<%-- Hidden input sends back the original score vales for conflict resolution --%>
			<input type="hidden" name="<%= AccessController.P_OLDGRADE + "_" + netID + "_" + probid + "_" + groupid %>" value="<%= score != null ? score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) : "" %>">
              <ul>
              		<li><%= problemName %>:<br />
              		
              	<% if (prob.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CORRECTANSWER())) {%>
					<strong>Correct answer:</strong> 
					<%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CORRECTANSWER()) %><br />
				<% } %>
              		
              		<input id="score_<%= netID + "_" + probid + "_" + groupid %>" type="text" onkeyup="onKeyupHandlerNoProbs('<%= netID %>','<%= groupid %>','<%= probid %>');" size="3" name="<%= AccessController.P_GRADE + "_" + netID + "_" + probid + "_" + groupid %>" 
			<%if (score != null && !score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()).equals("")){%>
			  <%="value=\"" + score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) + "\""%>
			  <%}%>
			<%= canGrade ? "" : " disabled" %>> /<%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></li></ul>
 			<script type="text/javascript">
             // Place above input in table
             scores['<%= groupid %>']['<%= probid %>']['<%= netID %>']=  getElementById('score_<%= netID + "_" + probid  + "_" + groupid %>');
			</script>
           
<%
        } else { %>
					<ul><%
          for (Iterator k= probs.iterator(); k.hasNext();) {
            Element prob= (Element)k.next();
            String probid= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID());
   			String probNumber = prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ORDER());
            Element score= (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE() + probid, XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()).item(0); 
   			Element answer = (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_ANSWERS() + probid, XMLBuilder._Static._Proxy.$instance.get$TAG_ANSWERS()).item(0); 
            
            String problemOrder = prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ORDER());
            String problemName = (problemOrder.equals("0")) ? prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) : problemOrder + ". " + prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME());
            
            boolean canGrade = !checkCanGrade || (score != null && score.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CANGRADE())); 
            if (score != null)
            {%>
				<input type="hidden" name="<%= AccessController.P_OLDGRADE + "_" + netID + "_" + probid + "_" + groupid %>" value="<%= score != null ? score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) : "" %>">
                <li><%= problemName %>:<br />
                	
                <% if (prob.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CORRECTANSWER())) {%>
					<strong>Correct answer:</strong> 
					<%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CORRECTANSWER()) %><br />
				<% } %>
                	
                	<input <%= hasTotal ? "onkeyup=\"onkeyupHandler('" + netID + "', '" + probid + "', '" + groupid + "');\"" : "" %> id="score_<%= netID + "_" + probid + "_" + groupid %>" type="text" size="3" name="<%= AccessController.P_GRADE + "_" + netID + "_" + probid + "_" + groupid %>"
				<%if (score != null && !score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()).equals("")){%>
				 	<%="value=\"" + score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) + "\""%>
				  <%}%>
				<%= canGrade ? "" : " disabled" %>> /<%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></li>
                <script type="text/javascript">
                  // Place above input in table
                  scores['<%= groupid %>']['<%= probid %>']['<%= netID %>']=  getElementById('score_<%= netID + "_" + probid  + "_" + groupid %>');
								</script><%
				}
          } %>
          <% if (hasTotal) { %>
              <li><strong>Total:</strong>&nbsp;&nbsp;&nbsp;<span id="totalscore_<%= netID + "_" + groupid %>">&nbsp;<%= totalgrade == null ? "&nbsp;" : totalgrade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></span> /<%= totalprob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></li>
          <% } %>
              </ul>
<%        } 
      }%>
            </td>
<%	}%>
            <td class="comments" <%= nummembers == 1 ? "" : " rowspan=\"" + Integer.toString(nummembers + 1) + "\"" %>><%
  pendingregrades= 0;
  if (numregrades != 0) {%>
              <ul><%
    boolean isRespond = false;
    for (int j= 0; j != numregrades; j++) { 
      Element regrade= (Element)regrades.item(j);
      String reqid= regrade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_REQUESTID());
      Element firstmem = (Element) group.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_MEMBER()).item(0);
      Element score = (Element) firstmem.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE() + regrade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID()), XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()).item(0);
      boolean canRegrade = !checkCanGrade || (score != null && score.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CANGRADE()));
      if (RegradeRequest._Static._Proxy.$instance.get$PENDING().equals(regrade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_STATUS()))) {
        pendingregrades++; 
            if (canRegrade) {
               isRespond = true; %>
                <li>
                  <label>
                    <input type="checkbox" name="<%= AccessController.P_REGRADERESPONSE + "_" + reqid + "_" + groupid %>">
             <%     if (regrade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID()).equals("0")) {
              %>
                    <strong>Respond</strong> to request for regrade on <strong><%=assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %></strong>
             <%  } else { 
              %>     
                    <strong>Respond</strong> to request for subproblem <strong><%=regrade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBNAME()) %></strong>
              <%  }
              %>      
                    
      
                <a name="regrade"></a><br>
                         <span id="regrade_<%= reqid %>head">
                 (<a class="hide" href="#" onClick="show('regrade_<%= reqid %>', 'expand request', 'hide request'); return false;">expand request</a>)
                </span>

                  </label>
                    <div style="display: none; align: left" id="regrade_<%= reqid %>" class="showhide">   
                <%    Element regr= (Element)regrades.item(j);
                      Text regrtext= (Text)regrade.getFirstChild();      
                %>
                <%
/* XXX GradeCommentInfo.formatComments(regrtext.getData()) */ 
                 %>
                
                    </div>


<%
      } %>
                </li><%
            }
    } %>
              </ul>
              <h1><%= isRespond ? "Response:" : "" %></h1><%
  } %>              
              <textarea rows="9" cols="30" name="<%= AccessController.P_COMMENTTEXT + groupid %>"></textarea>
              <input type="file" name="<%= AccessController.P_COMMENTFILE + groupid %>" size="26">
            </td>
          </tr><%
  if (nummembers != 1) {
    for (int j= 0; j != nummembers; j++) {
      Element member= (Element)members.item(j);
      String netID= member.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()); %>
          <tr>
            <td class="students">
              <% if (isAdmin) { %>
                <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_STUDENTALLGRADES %>&amp;<%= AccessController.P_COURSEID %>=<%= courseID %>&amp;<%= AccessController.P_NETID %>=<%= member.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %>">
              <% } %>							
							<%= member.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %>
              <% if (isAdmin) { %>
                </a>
              <% } %>
            </td>

<%---------------------------------------------------------------------------------------------------------%>

				<td class="scores">
<%
      Element totalgrade= (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE() + totalprob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID()), XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()).item(0);
      boolean hasTotal = totalgrade != null;
      if (numprobs == 0) {
            Element score= (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE() + "0", XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()).item(0); 
            boolean canGrade = !checkCanGrade || (score != null && score.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CANGRADE())); 
            if (score != null) {%> 
				<input type="hidden" name="<%= AccessController.P_OLDGRADE + "_" + netID + "_0_" + groupid %>" value="<%= score != null ? score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) : "" %>">
              <ul><li><input id="totalscore_<%= netID + "_" + groupid %>" type="text" onkeyup="onKeyupHandlerNoProbs('<%= netID %>','<%= groupid %>','0');" size="3" name="<%= AccessController.P_GRADE %>_<%= netID %>_0_<%= groupid %>" <%= score != null ? "value=\"" + score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) + "\"" : "value=\"\"" %><%= canGrade ? "" : " disabled" %>> /<%= totalprob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></li></ul>
            	<script type="text/javascript">
             		// Place above input in table
             		scores['<%= groupid %>']['0']['<%= netID %>']=  getElementById('totalscore_<%= netID + "_" + groupid %>');
				</script>
            <%
            }
      } else {
        if (numprobs == 1) {
          Element prob= (Element)probs.iterator().next();
          String probid= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID());
          Element score= (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE() + probid, XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()).item(0); 
          boolean canGrade = !checkCanGrade || (score != null && score.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CANGRADE())); %>
			<input type="hidden" name="<%= AccessController.P_OLDGRADE + "_" + netID + "_" + probid + "_" + groupid %>" value="<%= score != null ? score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) : "" %>">
              <ul><li><%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %>:&nbsp;&nbsp;&nbsp;<input id="score_<%= netID + "_" + probid + "_" + groupid %>" type="text" onkeyup="onKeyupHandlerNoProbs('<%= netID %>','<%= groupid %>','<%= probid %>');" size="3" name="<%= AccessController.P_GRADE %>_<%= netID %>_<%= probid %>_<%= groupid %>" <%= score != null ? "value=\"" + score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) + "\"": "" %><%= canGrade ? "" : " disabled" %>> /<%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></li></ul>
        	<script type="text/javascript">
             // Place above input in table
             scores['<%= groupid %>']['<%= probid %>']['<%= netID %>']=  getElementById('score_<%= netID + "_" + probid  + "_" + groupid %>');
			</script>
        
       <% } else { %>
              <ul><%
          for (Iterator k= probs.iterator(); k.hasNext();) {
            Element prob= (Element)k.next();
            String probid= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID());
            Element score= (Element)member.getElementsByTagNameNS(XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE() + probid, XMLBuilder._Static._Proxy.$instance.get$TAG_GRADE()).item(0); 
            boolean canGrade = !checkCanGrade || (score != null && score.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_CANGRADE())); 
                if (score != null) {%>
				<input type="hidden" name="<%= AccessController.P_OLDGRADE + "_" + netID + "_" + probid + "_" + groupid %>" value="<%= score != null ? score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) : "" %>">
               		<li><%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME()) %>:&nbsp;&nbsp;&nbsp;<input <%= hasTotal ? "onkeyup=\"onkeyupHandler('" + netID + "', '" + probid + "', '" + groupid + "')\"" : "" %> id="score_<%= netID + "_" + probid  + "_" + groupid %>" type="text" size="3" name="<%= AccessController.P_GRADE %>_<%= netID %>_<%= probid %>_<%= groupid %>" <%= score != null ? "value=\"" + score.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) + "\"" : "" %><%= canGrade ? "" : " disabled" %>> /<%= prob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></li>
                <script type="text/javascript">
                  // Place above input in table
                  scores['<%= groupid %>']['<%= probid %>']['<%= netID %>']=  getElementById('score_<%= netID + "_" + probid  + "_" + groupid %>');
								</script><%
                }
          } %>
            <% if (hasTotal) { %>
              <li>Total:&nbsp;&nbsp;&nbsp;<span id="totalscore_<%= netID + "_" + groupid %>">&nbsp;<%= totalgrade == null ? "&nbsp;" : totalgrade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></span> /<%= totalprob.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SCORE()) %></li>
            <% } %>
              </ul>
<%      } 
      }%>      
            </td>
          </tr><%
    }
  } %> 
          <tr>
            <td class="logs" colspan="<%= probscol ? "5" : "4" %>"><%
  if (nummembers != 1) { %>
              <%-- Script element determines whether or not the link scores checkbox should be checked --%>
              <script type="text/javascript">
                var checked= true;
								var box;
								for (var probid in scores['<%= groupid %>']) {
                  var sofar= -1;
								  for (var netid in scores['<%= groupid %>'][probid]) {
								    if (sofar == -1) {
								      sofar= scores['<%= groupid %>'][probid][netid].value;
								    } else if (sofar != scores['<%= groupid %>'][probid][netid].value) {
								      checked= false;
								      break;
								    }								      
								  }
								  if (!checked)
								    break;
								}
                var linkbox= getElementById('link_<%= groupid %>');
                linkbox.checked= checked;
              </script><%
  } %>
                 <h1>Logs
<%	if (numlogs > 0) {%>
                 (<a class="hide" id="logs<%= groupid %>link" href="javascript:showentry('logs<%= groupid %>', '<%=numlogs%> entries', '<%=numlogs%> entries');"><%=numlogs%> entries</a>)
<%	} else {%>
						(0 entries)
<%	}%>
               	 </h1>
    <div style="display: none;" id="logs<%= groupid %>" class="showhide"> 

<% if (numlogs != 0) { %>
          
                <table cellpadding="0" cellspacing="0">
                  <tr>
                    <td><b>Time</b></td>
                    <td><b>netID</b></td>
                    <td><b>Grader</b></td>
                    <td><b>Details</b></td>
                  </tr><%
    for (int j= 0; j != numlogs; j++) {
      Element log= (Element)logs.item(j); %>
                  <tr class="<%= j % 2 == 0 ? "row_even" : "row_odd"%>">
                    <td><%= log.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DATE()) %></td>
                    <td><%= log.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %></td>
                    <td><%= log.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_GRADER()) %></td>
                    <td><%= log.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_TEXT()) %></td>
                  </tr><%
    } %>
                </table>
        <br>   

<%
  } %>
              </div>
            </td>
          </tr>
          <tr>
            <td class="regrades" colspan="<%= probscol ? "5" : "4" %>">
              <h1>
                Regrade Requests
                <span class="hide">(<%= (numregrades > 0 ? "<a class=\"hide\" id=\"regrades_" + groupid + "link\" href=\"#\" onclick=\"showentry('regrades_" + groupid + "'); return false;\">" : "")%><%= numregrades %> entries<%= numregrades > 0 ? ", " + pendingregrades + " pending" : "" %><%= numregrades > 0 ? "</a>" : "" %>)</span>
                <span class="hide" id="regrade<%= groupid %>head">(<a href="#" onclick="show('regrade<%= groupid %>','Add Request','Add Request'); return false;">Add Request</a>)</span>
              </h1>
<%--         New Regrades          --%>
		        <div id="regrade<%= groupid %>" class="showhide" style="display: none">
		        <table class="grading" cellpadding="10px" border="0" cellspacing="0" width="100%">
		          <tr ><%
		          if (allprobs.getLength() > 0){%>
		            <td id="RegradeReqCheckboxes<%= groupid %>" valign="top" style="left-padding:"1"">Submit Request for:<br>
		              <input type="checkbox" id="allParts<%= groupid %>" name="allParts" value="allParts" onClick="allClick('<%= groupid %>');">
		              All Parts<br><br><%
		            for (int j= 0; j<allprobs.getLength(); j++){
		              Element problem = (Element) allprobs.item(j); 
		              String elemname = AccessController.P_REGRADESUB + "_" + problem.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_SUBPROBID()) + "_" + groupid;%>
		              <input type="checkbox" name="<%=elemname%>" value="<%=elemname%>" onClick="otherClick('<%= groupid %>');">
		              <%=problem.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NAME())%><br>
		        <%    }%>
		            </td><%
		          }else{%>
		              <input type="hidden" name="<%=AccessController.P_REGRADEWHOLE + "_" + groupid%>" value="">
		        <%}%>
		            <td>
                  <p style="white-space: no-wrap">Enter new request:</p>
                  <input type="hidden" name="<%= AccessController.P_REGRADENETID + "_" + groupid %>" value="<%= meNetID %>">
		              <textarea name="<%= AccessController.P_REGRADEREQUEST + "_" + groupid %>" rows="6" cols="45"></textarea>
		            </td>
		          </tr>
		        </table>
		        </div>
<%--         Old Regrades        --%>

<% if (numregrades != 0) { %>
              <dl id="regrades_<%= groupid %>"><%
  }
  HashMap commentchks = new HashMap();
  for (int j= 0; j != numregrades; j++) { 
    Element regrade= (Element)regrades.item(j);
    Text regradetext= (Text)regrade.getFirstChild();
    Element regraderesponse= (Element)regrade.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_RESPONSE()).item(0); 
    String checkname = regraderesponse == null ? "" : AccessController.P_REMOVECOMMENT + regraderesponse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COMMENTID());
    Integer count = (Integer) commentchks.get(checkname);
    if (count == null) {
        count = new Integer(1);
    } else {
        count = new Integer(count.intValue() + 1);
    }
    commentchks.put(checkname, count);
    %>
                <div class="<%= j % 2 == 0 ? "row_even" : "row_odd"%>">
                <dt>
                  <h2><strong>Request:</strong> by <%= regrade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %> at <%= regrade.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DATE()) %></h2>
                  <p><%= regradetext.getData() %>
                </dt><% 
    if (regraderesponse != null) {
      Text responsetext= (Text)regraderesponse.getFirstChild(); 
      boolean canRemove = !checkCanGrade || meNetID.equalsIgnoreCase(regraderesponse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID())); %>
                <dd>
                  <h2><strong>Response:</strong> by <%= regraderesponse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()) %> at <%= regraderesponse.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DATE()) %>
                  <% if (canRemove) { %>
                  <span style="float: right"><input type="checkbox" name="<%= checkname %>" id="<%= checkname + count.toString() %>" onclick="checkLinkedComments(this.name, this.checked);"> Remove Response</span>
                  <% } %>
                  </h2>
                  <p><%= responsetext.getData() %>


<%      Element responseFile= (Element) (regraderesponse.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_COMMENTFILE()).item(0));
        if (responseFile!=null) { 
%>
        <p style="margin-left: 20px">Attached file: <a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_DOWNLOAD%>&amp;<%=AccessController.P_ID%>=<%= responseFile.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COMMENTFILEID())%>&amp;<%=AccessController.P_DOWNLOADTYPE%>=<%=XMLBuilder._Static._Proxy.$instance.get$TAG_COMMENTFILE()%>"><%= responseFile.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FILENAME()) %>
        </a></p>
<%      } 
%>

                </dd><%
    }%>
    </div><%
  } 
  if (numregrades != 0) {%>
              </dl><%
  } %>
            </td>
          </tr>
<%-- ------------------- BEGIN COMMENTS -------------------- --%>
          <tr>
           <td class="regrades" colspan="<%= probscol ? "5" : "4" %>">
                 <a name="comments"></a><h1>Comments
                <span class="hide" id="comments<%= groupid %>head">
<%	if (numcomments > 0)
	{%>
                 (<a class="hide" href="#" onClick="show('comments<%= groupid %>', '<%=numcomments%> comments', '<%=numcomments%> comments'); return false;"><%=numcomments%> comments</a>)
<%	}
	else
	{%>
						(0 comments)
<%	}%>
                </span></h1>
    <div style="display: none;" id="comments<%= groupid %>" class="showhide"> 
<%

    for (int j= 0; j != numcomments; j++) { 
        Element comment= (Element)comments.item(j);
        String commentText= ((Text)comment.getFirstChild()).getData();         
        boolean canRemove = !checkCanGrade || meNetID.equalsIgnoreCase(comment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID()));
%>
  <div class="<%= j % 2 == 0 ? "row_even" : "row_odd"%>">

        <h2><strong>Comment:</strong> by <%=comment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_NETID())%> on <%=comment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DATE())%>
        <% if (canRemove) { %>
        <span style="float: right"><input type="checkbox" name="<%= AccessController.P_REMOVECOMMENT + comment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COMMENTID()) %>"> Remove Comment</span>
        <% } %>
        </h2>
        <p style="margin-left: 20px"><%= commentText %></p>
<%      Element commentFile= (Element) (comment.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_COMMENTFILE()).item(0));
        if (commentFile!=null) { 
%>
        <p style="margin-left: 20px">Attached file: <a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_DOWNLOAD%>&amp;<%=AccessController.P_ID%>=<%= commentFile.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_COMMENTFILEID())%>&amp;<%=AccessController.P_DOWNLOADTYPE%>=<%=XMLBuilder._Static._Proxy.$instance.get$TAG_COMMENTFILE()%>"><%= commentFile.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_FILENAME()) %>
        </a></p>
<%      } 
%>
  </div>
<%
    }
%>    

</div>

           </td>
          
          </tr>
                 
<%-- -------------------- END COMMENTS -------------------- --%>

          
        </table><%
        // We're not at the last group block 
        if (i + 1 < numgroups) { %>
          Submit changes at the bottom of the page (<a href="#submit" class="hide">Jump</a>)<br><br>
    <%  }
} %>
        <a name="submit"></a><br><input type="submit" value="Update">
        </form>
         </div>
    </td>
    <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
  </tr>
<jsp:include page="../../footer.jsp"/>
