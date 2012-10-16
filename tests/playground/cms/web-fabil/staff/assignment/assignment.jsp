<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*,cms.model.*;"%><%
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getChildNodes().item(0);
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
Element assignment= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT); 
Element status= (Element)root.getElementsByTagName(XMLBuilder.TAG_STATUS).item(0);
int assignType = Integer.parseInt(assignment.getAttribute(XMLBuilder.A_ASSIGNTYPE));
String assignID= assignment.getAttribute(XMLBuilder.A_ASSIGNID);
%><jsp:include page="../../header.jsp" />
<script src="CalendarPopup.js" type="text/javascript"></script> <%-- required by formattedtextboxes.js --%>
<script src="datetime.js" type="text/javascript"></script> <%-- required by formattedtextboxes.js --%>
<script src="formattedtextboxes.js" type="text/javascript"></script>
<script type="text/javascript"><jsp:include page="assignscript.js.jsp"/></script>
<jsp:include page="../../header-page.jsp" />
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
<jsp:include page="../navbar.jsp"/>
  <td id="course_page_container">
  	<%
	String assignTypeName = "";
	if (assignType == Assignment.ASSIGNMENT)
	{
		assignTypeName = "Assignment";
	}
	else if (assignType == Assignment.QUIZ)
	{
		assignTypeName = "Quiz";
	}
	else if (assignType == Assignment.SURVEY)
	{
		assignTypeName = "Survey";
	}
  	%>
    <form action="?<%= AccessController.P_ACTION %>=<%=AccessController.ACT_SETASSIGN%>&amp;<%= AccessController.P_COURSEID %>=<%= course.getAttribute(XMLBuilder.A_COURSEID) %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>" 
     method="post" enctype="multipart/form-data" <%--onSubmit="return validateAllFormattedTextboxes() && checkData();" --%>>
      <input type="hidden" name="<%= AccessController.P_ASSIGNID %>" value="<%= assignID %>">
      <input type="hidden" name="<%= AccessController.P_ASSIGNMENTTYPE %>" value="<%= assignType %>">
      <div id="course_page">
				<jsp:include page="../../problem-report.jsp"/>
        <span class="assignment_title"><%= assignID.equals("0") ? "New " + assignTypeName : assignment.getAttribute(XMLBuilder.A_NAME) %></span>
        <div class="assignment_left">
          <jsp:include page="assignment-general.jsp"/>
        </div>
        <div class="assignment_left">
         <jsp:include page="assignment-description.jsp"/>
        </div>
        <% if (assignType == Assignment.ASSIGNMENT) { %>
        <div class="assignment_left">
          <jsp:include page="assignment-groups.jsp"/>
        </div>
        <div class="assignment_left">
          <jsp:include page="assignment-submission.jsp"/>
        </div>
        <div class="assignment_left">
          <jsp:include page="assignment-files.jsp"/>
        </div>
        <div class="assignment_left">
          <jsp:include page="assignment-problems.jsp"/>
        </div>
        <div class="assignment_left">
          <jsp:include page="assignment-schedule.jsp"/>
        </div>
        <%}
        else{%>
   	    <div class="assignment_left">
          <jsp:include page="survey-questions.jsp"/>
        </div>
        <%}%>
        <div class="assignment_left">
          <input type="submit" value="Submit">
        </div>
        <div class="assignment_left">

        </div>
      </div>
    </form>
  </td>
  <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
</tr>
<jsp:include page="../../footer.jsp"/>
