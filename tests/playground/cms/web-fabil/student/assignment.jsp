<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.model.*" %><%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
Element assignment = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
Element principal = (Element) root.getElementsByTagName(XMLBuilder.TAG_PRINCIPAL).item(0);
boolean isGuest = principal == null;
boolean isCCMember = principal != null && principal.hasAttribute(XMLBuilder.A_ISCCMEMBER);
boolean isStudent = course.hasAttribute(XMLBuilder.A_ISSTUDENT);
int assignmentType = Integer.parseInt(assignment.getAttribute(XMLBuilder.A_ASSIGNTYPE));
String status = assignment.getAttribute(XMLBuilder.TAG_STATUS);

if (assignment == null || status.equals(Assignment.HIDDEN)) { %> 
<p>Error: Assignment data not available <%
} else {
  boolean isOpen = status.equals(Assignment.OPEN) && !assignment.hasAttribute(XMLBuilder.A_PASTDUE); 
  boolean isGraded = status.equals(Assignment.GRADED); %>
<jsp:include page="../header.jsp" />
<style type="text/css">
  ul.filelist {list-style-type: none; padding: 0em; border: 1px solid #ddd}
  ul.filelist li {padding: 0.5em}
  ul#summary {list-style-type: none; padding: 0em}
  ul#summary li {padding: 0em; margin: 0em}
  table.submissions {border: 1px solid #ddd; width: 100%}
  table.submissions th {border-bottom: 1px solid #ddd; padding: 0.3em; text-align: center}
  table.submissions td {padding-left: 1em}
</style>
<jsp:include page="../header-page.jsp" />
<div id="course_wrapper">
  <table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">

    <tr>
      <jsp:include page="../navbar.jsp"/>
      <td id="course_page_container">
        <div id="course_page">
        	<jsp:include page="../problem-report.jsp"/>
          <span class="assignment_title"><%= assignment.getAttribute(XMLBuilder.A_NAME) %></span>
          <div class="assignment_left">
            <jsp:include page="assignment-description.jsp" />
          </div>
          <div class="assignment_left" >
            <jsp:include page="assignment-summary.jsp"/>
          </div>
          <div class="assignment_left">
            <jsp:include page="assignment-files.jsp" />
          </div><%
if (isStudent) { 
		if (assignment.hasAttribute(XMLBuilder.A_USESCHEDULE)) { %>
		<div class="assignment_left">
			<jsp:include page="assignment-schedule.jsp" />
		</div>
		<% } 
  if (isOpen) { %>
      		<div class="assignment_left">
            	<jsp:include page="assignment-groups.jsp" />
          	</div><%
  } 
  if (assignmentType == Assignment.ASSIGNMENT)
  {
  %>
          <div class="assignment_left">
           <jsp:include page="assignment-submission.jsp" />
          </div><%
  }
  else
  {
  %>
          <div class="assignment_left">
          <jsp:include page="survey-questions.jsp" />
          </div><%
  }
  if (isGraded) { %>
          <div class="assignment_left">
            <jsp:include page="assignment-comments.jsp" />
          </div>
				  <div class="assigment_left">
					<jsp:include page="assignment-regraderequest.jsp" />
				  </div><%
  }
} %>
        </div>
      </td>
     <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
  </tr>
  <jsp:include  page="../footer.jsp" /><%
} %>
