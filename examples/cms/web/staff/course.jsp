<%@ page language="java" import="org.w3c.dom.*, cms.auth.*, cms.model.*, cms.www.*, cms.www.xml.*" %><%
/**************************************
* main course page
**************************************/
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
User p = (User) session.getAttribute(AccessController.A_PRINCIPAL);
String netid = p.getNetID();
Element root = (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
String courseID = course.getAttribute(XMLBuilder.A_COURSEID);
boolean isadmin = false, isgroups = false, isgrades = false, isassign = false, iscategory = false;
if (!p.isInStaffAsBlankMode()) /* privileges are possible only if acting as staff */
{
	isadmin = course.hasAttribute(XMLBuilder.A_ISADMIN);
	isgroups = course.hasAttribute(XMLBuilder.A_ISGROUPS);
	isgrades = course.hasAttribute(XMLBuilder.A_ISGRADES);
	isassign = course.hasAttribute(XMLBuilder.A_ISASSIGN);
	iscategory = course.hasAttribute(XMLBuilder.A_ISCATEGORY); 
}%>
<jsp:include page="../header.jsp" />
<style type="text/css">
  .noaccess {color: #999999}    
</style>
<jsp:include page="../header-page.jsp" />
<div id="course_wrapper_withnav">

<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
<%Element status= (Element) root.getElementsByTagName(XMLBuilder.TAG_STATUS).item(0); %>
<tr>
	<jsp:include page="navbar.jsp"/>
  <td valign="top" id="course_page_container">
    <div id="course_page">
		<jsp:include page="../problem-report.jsp"/>
      	<jsp:include page="../course-title.jsp" />
<%Element l= (Element) course.getElementsByTagName(XMLBuilder.TAG_DESCRIPTION).item(0);
Text text= (Text)l.getFirstChild();
if (isadmin)
{%>
      <div id="description_title" class="assignment_left">
        <h2>
          Course Description
          <span id="descriptionhead">
            <a class="hide" href="#" onClick="hide('description', '(hide)', '(show)'); return false;">(hide)</a>
          </span>
        </h2>
			<script type="text/javascript">
			/*
			show or hide the form to edit the course description
			
			newEditState is boolean: true = start editing, false = finish editing
			*/
			function editDescription(newEditState)
			{
				if (newEditState)
				{
					changeDisplay('desc_view_span', 'none');
					changeDisplay('desc_form_span', 'inline');
					/*
					reset the text in the textarea so we don't keep changes from an earlier canceled edit
					(the replace() calls are trimming whitespace)
					*/
					var editTextbox = getElementById('desc_edit_box');
					var descViewSpan = getElementById('desc_text');
					editTextbox.value = descViewSpan.innerHTML.replace(/^\s+/, '').replace(/\s+$/, '');
				}
				else
				{
					changeDisplay('desc_view_span', 'inline');
					changeDisplay('desc_form_span', 'none');
				}
			}
			</script>
			<div id="description" class="showhide">
				<span id="desc_view_span" style="display: inline"> <%-- the description as text --%>
					<span id="desc_text">
						<%= text.getData() %>
					</span>
					<br>
					<a href="#" onClick="editDescription(true);">Edit</a>
				</span>
				<span id="desc_form_span" style="display: none"> <%-- the form to edit the description --%>
					<form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_EDITCOURSEDESCRIPTION %>&amp;<%= AccessController.P_COURSEID %>=<%= courseID %>" method="post">				
						<textarea id="desc_edit_box" name="<%= AccessController.P_DESCRIPTION %>" rows="12" cols="60"><%= text.getData() %></textarea>
						<br>
						<input type="submit" value="Submit">
						<input type="button" value="Cancel" onClick="editDescription(false);">
					</form>
				</span>
			</div>
		</div><%
}
else if (!text.getData().equals(""))
{%>
      <div id="description_title" class="assignment_left">
        <h2>
          Course Description
          <span id="descriptionhead">
            <a class="hide" href="#" onClick="hide('description', '(hide)', '(show)'); return false;">(hide)</a>
          </span>
        </h2>
        <div id="description" class="showhide">
        		<%= text.getData() %>
        </div>
      </div><%
}%>
      <jsp:include page="../course-announcements.jsp"/>
      <jsp:include page="assignment/assignment-table.jsp"/>
      <jsp:include page="../course-category.jsp"/>
		<div class="assignment_left">
			<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_NEWCATEGORY %>&amp;<%= AccessController.P_COURSEID %>=<%= courseID %>">Add/Reorder/Remove Content</a>
		</div>
    </div>
  </td>
  <td id="course_menu_container" width="14px"><div id="course_menu_top">&nbsp;</div></td>
</tr>
<jsp:include page="../footer.jsp"/>
