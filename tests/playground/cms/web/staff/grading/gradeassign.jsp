<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
/******************************************************
* main grading page: show all students for a single assignment
******************************************************/
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getFirstChild();
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
String courseID = course.getAttribute(XMLBuilder.A_COURSEID);
Element assignment= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
String asgnID = assignment.getAttribute(XMLBuilder.A_ASSIGNID);
Element status= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_STATUS);
boolean isAdmin = course.hasAttribute(XMLBuilder.A_ISADMIN);
boolean isGrades = course.hasAttribute(XMLBuilder.A_ISGRADES);%>
<jsp:include page="../../header.jsp" />
<jsp:include page="../../header-page.jsp"/>
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" cellpadding="0" cellspacing="0" width="100%">
  <tr>
<jsp:include page="../navbar.jsp"/>
    <td valign="top" id="course_page_container">
      <div id="course_page">
				<jsp:include page="../../problem-report.jsp"/>
        <form action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_APPLYTOGROUPS + "&amp;" + AccessController.P_ASSIGNID + "=" + assignment.getAttribute(XMLBuilder.A_ASSIGNID)%>" method="post">
        	<jsp:include page="grades-summary.jsp"/>
          <jsp:include page="grades-table.jsp"/>
<input type="hidden" id=<%= AccessController.P_COURSEID %> name=<%= AccessController.P_COURSEID %> value=<%= courseID %> />
        </form>
        <jsp:include page="grades-groups.jsp"/>
      </div>
    </td>
    <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
    <script type="text/javascript">
    	<%-- initialize sortable tables; see sorttable.js --%>
    	sortables_init();
    	<%-- Resort table by previous sort ordering --%> 
    	var tablename = 'grades_table_' + <%= asgnID %>;
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
  </tr>
<jsp:include page="../../footer.jsp"/>