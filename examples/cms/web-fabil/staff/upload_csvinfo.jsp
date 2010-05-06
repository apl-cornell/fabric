<%@ page language="java" import="org.w3c.dom.*, cms.auth.Principal, cms.www.*, cms.www.util.*, cms.www.xml.*" %>
<%
/***************************************************************************************************
* upload a file with any kind of student info, or download a template for a final-grade upload
***************************************************************************************************/
%>
<jsp:include page="../header.jsp" />
<jsp:include page="../header-page.jsp" />
<style type="text/css">
div.filetransfer {padding: 10px; background-color: #f7f7f0; border: 1px solid #ddd}
div.filetransfer h1 {font-size: 1em; font-weight: bold; padding: 0px; margin: 0px}
div.filetransfer div {margin: 1em 0em 0em 1em}
</style>
<div id="course_wrapper_withnav">
	<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
			<jsp:include page="navbar.jsp" />
			<td id="course_page_container">
				<div id="course_page">
					<jsp:include page="../problem-report.jsp" />
					<jsp:include page="../course-title.jsp" />
					<jsp:include page="../upload.jsp" />
				</div>
			</td>
		</tr>
<jsp:include page="../footer.jsp"/>
