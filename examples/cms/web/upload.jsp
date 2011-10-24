<%@ page language="java" import="org.w3c.dom.*, java.util.List, cms.auth.*, cms.model.*, cms.www.*, cms.www.util.*, cms.www.xml.*" %>
<%
/***************************************************************************************************
* upload a file with any kind of student info, or download a template for a final-grade upload
***************************************************************************************************/
Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
User p = (User)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
String courseID = null;
if (course != null) /* course-specific page */
{
	courseID = course.getAttribute(XMLBuilder.A_COURSEID);
}%>
<style type="text/css">
div.filetransfer {padding: 10px; margin: 5px 0px; background-color: #f7f7f0; border: 1px solid #ddd}
div.filetransfer h1 {font-size: 1em; font-weight: bold; padding: 0px; margin: 0px}
div.filetransfer div {margin: 1em 0em 0em 1em}
</style>
					<div id="upload_general_div" class="filetransfer">
						Upload general CSV with student information:
						<form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_UPLOADSTUDENTINFO + ((course != null) ? ("&amp;" + AccessController.P_COURSEID + "=" + courseID) : "") %>">
							<input type="file" size="30" name="<%= AccessController.P_UPLOADEDCSV %>">
							&nbsp;<input type="submit" value="Upload">
						</form>
						<div id="uploadhelp_title" class="assignment_left">
							<h2>
								Help
								<span id="uploadhelphead">
									<a class="hide" href="#" onClick="show('uploadhelp', '(show)', '(hide)'); return false;">(show)</a>
								</span>
							</h2>
							<div id="uploadhelp" class="showhide" style="display:none">
								You can upload a CSV-format file with any one or more of the following columns.
								Column names must be given on the first line of the file.
								All lines after the first are taken to contain data. Any column on any line after the first
								may be empty; if this presents a problem, the system will let you know.
								<p>This feature is necessary at least once a semester, to upload a classlist file with various
								information that will go into the exported final grade report.
								<p>
								<ul><%
/* display the list of columns that can be uploaded with whatever permission the principal has */
List appropriateColumnNames = null;
if (course != null) appropriateColumnNames = CSVFileFormatsUtil.getStaffAppropriateFlexibleColumnList();
else appropriateColumnNames = CSVFileFormatsUtil.getCMSAdminAppropriateFlexibleColumnList();
for (int i = 0; i < appropriateColumnNames.size(); i++)
{%>
									<li><%= (String)appropriateColumnNames.get(i) %><%
}%>
								</ul>
							</div>
						</div>
					</div>
					<div id="download_general_div" class="filetransfer">
						<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_EXPORTSTUDENTINFOTEMPLATE + ((course != null) ? ("&amp;" + AccessController.P_COURSEID + "=" + courseID) : "") %>">Download</a> 
						a full template, from which you can remove the columns you are not interested in.
					</div>
					<hr width="98%">
					<div id="upload_classlist_div" class="filetransfer">
						Upload final classlist (<a target="blank" href="http://www.cs.cornell.edu/Projects/CMS/userdocs/finalclasslist.html">specific format</a>, needed at end of semester):
						<form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_UPLOADSTUDENTINFO + ((course != null) ? ("&amp;" + AccessController.P_COURSEID + "=" + courseID) : "") + "&amp;" + AccessController.P_ISCLASSLIST + "=yes" %>">
							<input type="file" size="30" name="<%= AccessController.P_UPLOADEDCSV %>">
							&nbsp;<input type="submit" value="Upload">
						</form>
					</div>
