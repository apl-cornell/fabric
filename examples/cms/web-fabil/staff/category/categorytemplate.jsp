<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*"%>
<%
/*************************************************************************
set the order of existing and possibly one new categories in a course,
and, if there's a new category, the order and properties of its columns
*************************************************************************/
	Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root= (Element)displayData.getChildNodes().item(0);
   Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
   Element category= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_CATEGORY);
   Element status= (Element)root.getElementsByTagName(XMLBuilder.TAG_STATUS).item(0);
   String ctgID= category.getAttribute(XMLBuilder.A_ID); %>
<jsp:include page="../../header.jsp" />
<jsp:include page="categoryscript.js.jsp" />
<jsp:include page="../../header-page.jsp" />
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">

<tr>
<jsp:include page="../navbar.jsp"/>
  <td id="course_page_container">
  	<script type="text/javascript">
  	//set up ordering for old and (possibly) new categories
  	createOrdering('ctgOrdering');
	</script>
    <form id="category_form" action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_SETCATEGORY %>&amp;<%= AccessController.P_COURSEID %>=<%= course.getAttribute(XMLBuilder.A_COURSEID) %>&amp;<%= AccessController.P_CATID %>=<%= category.getAttribute(XMLBuilder.A_ID) %>" method="post" enctype="multipart/form-data">
      <div id="course_page">
		<jsp:include page="../../problem-report.jsp"/>
        <span class="assignment_title">Content</span><%
if (ctgID.equals("0"))
{%>
		<h2>
			Existing Content
			<span id="allctghead">
  				<a class="hide" href="#" onClick="hide('allctg', '(hide)', '(show)'); return false;">(hide)</a>
			</span>
		</h2>
		<div id="allctg" class=showhide">
			<div class="assignment_left">
				<jsp:include page="allcategories.jsp"/>
			</div>
		</div><%
}%>
        <h2>
			<%= ctgID.equals("0") ? "New Content Table" : category.getAttribute(XMLBuilder.A_NAME) %>
			<span id="templatehead">
  				<a class="hide" href="#" onClick="hide('template', '(hide)', '(show)'); return false;">(hide)</a>
			</span>
		</h2>
		<div id="template" class="showhide">
   		<div class="assignment_left">
        		<jsp:include page="categorytemplate-general.jsp"/>
        	</div>
        	<div class="assignment_left">
         	<jsp:include page="categorytemplate-columns.jsp"/>
			</div><%
if (ctgID.equals("0"))
{%>
			<div class="assignment_left">
				<table>
					<tr>
						<td align="left">Order in content table list</td>
						<td id="new_ctg_order_cell" align="left"></td>
						<script type="text/javascript">
							//make sure the new category goes at the end of the order by default
							getElementById('new_ctg_order_cell').appendChild(registerIDForOrdering('ctgOrdering', '<%= AccessController.P_NEWCTGPOSITION %>'));
						</script>
					</tr>
				</table>
			</div><%
}%>
		</div>
		<div class="assignment_left">
        	<input type="submit" value="Submit">
        </div>
      </div>
    </form>
  </td>
  <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
</tr>
<jsp:include page="../../footer.jsp"/>