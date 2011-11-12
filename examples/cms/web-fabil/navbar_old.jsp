<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*" %><%
 Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
 Element root = (Element) displayData.getChildNodes().item(0);
 Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
 boolean isStudent = course != null ? course.hasAttribute(XMLBuilder.A_ISSTUDENT) : false;
 String courseid = (course != null ? course.getAttribute(XMLBuilder.A_COURSEID) : null);
 String URL = request.getServletPath();
 Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
 NodeList allassigns = new CMSNodeList();
 try {
 	Element assigns = (Element) root.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENTS).item(0);
 	if (assigns != null) {
 		allassigns = assigns.getChildNodes();
 	}
 } catch (Exception e) {}
%>
<td class="staff_navigation" rowspan="3" width="224px">
 <table border="0" cellpadding="0" cellspacing="0" width="224px">
 <tr>
  <td width="20px">&nbsp;</td>
 <td class="staff_navigation" colspan="2" width="170px">
  <ul id="navbar_content"><%
if (courseid != null) {%> 
<li>
<span class="course"> <%= course.getAttribute(XMLBuilder.A_DISPLAYEDCODE)%>&nbsp;</span><br>
<span class="semester"> (<%=course.getAttribute(XMLBuilder.A_SEMESTER)%>)</span>
</li>
<%}%>
<script type="text/javascript" src="popupmenu.js"></script>
<script type="text/javascript">
var assigns = new Array();<%
if (allassigns.getLength() == 0) { %>
	assigns[0] = 'No Assignments';
<%}
for (int i = 0; i < allassigns.getLength(); i++)
{
	Element xAssign = (Element) allassigns.item(i);
	/* it could also be a survey/quiz... Panut needs to commit!  This was crashing me - Alex*/
	if (xAssign.getNodeName().equals(XMLBuilder.A_ASSIGNMENT)) {
		String status = xAssign.getAttribute(XMLBuilder.A_STATUS);
	    boolean isHidden = status.equals(Assignment.HIDDEN);
		int type = Integer.parseInt(xAssign.getAttribute(XMLBuilder.A_ASSIGNTYPE));
		String action = "";
		
		if (type == Assignment.ASSIGNMENT)
		{
			action = AccessController.ACT_ASSIGN;
		}
		else if (type == Assignment.SURVEY)
		{
			action = AccessController.ACT_SURVEY;
		}
		else if (type == Assignment.QUIZ)
		{
			action = AccessController.ACT_QUIZ;
		}	
		
		isHidden = status.equals(Assignment.HIDDEN);
		if (!isHidden) {%>
			assigns[0] <%= i==0 ? "" : "+" %>= '<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_ASSIGN + "&amp;" + AccessController.P_ASSIGNID + "=" + xAssign.getAttribute(XMLBuilder.A_ASSIGNID) %>">&#149;&nbsp;<%= xAssign.getAttribute(XMLBuilder.A_NAME).replaceAll("'", "\\\\'") %></a>';<%
		}
	}
}%>
</script><%
if (courseid != null) {%>
    <li<%= URL.equals(AccessController.COURSE_URL) ? " class=\"currentpage\"" : "" %>>
    	<hr width="100%" size="1px">
    	<a href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_COURSE + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>">Home</a>
	</li>
<%	if (isStudent) {
		String begintag = "<li><a href=\"#\" onMouseover=\"showmenu(event, assigns[0], 350); return false;\" onMouseout=\"delayhidemenu();\">";
      	String endtag = "</a></li>";%>
  	<%= begintag %>Assignments<%= endtag %> 
	<%	begintag = "<li><a href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_STUDENTPREFS + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
		endtag = "</a><hr width=\"100%\" size=\"1px\"></li>";%>
  	<%= begintag %>Notifications<%= endtag %>
<% 	}%>
<%}%>
<li>
	<a href="http://www.cs.cornell.edu/Projects/CMS/help.html" target="_blank">Help</a>
</li>
<li>
	<a href="https://gforge.cis.cornell.edu/tracker/?atid=492&amp;group_id=44&amp;func=browse" target="_blank">Suggest Features</a>
</li>
<li>
	<a href="https://gforge.cis.cornell.edu/tracker/?atid=493&amp;group_id=44&amp;func=browse" target="_blank">Report Bugs</a>
</li>
<li>
	<a href="http://www.cs.cornell.edu/Projects/CMS" target="_blank">Credits</a>
</li>
</ul>
</td>

  <td  id="navbar_menu_container" width="14px"><div id="navbar_menu_top">&nbsp;</div></td>
   <td width="20px"> &nbsp;</td>
  </tr>
  <tr height="14px">
   <td width="20px">&nbsp;</td>
  <td width="61px">
     <div id="navbar_bottom_left">&nbsp;</div>  
    </td>
   <td width="109px">     <div id="navbar_bottom">&nbsp;</div>  
    </td>
  <td width="14px"><div id="navbar_menu_bottom">&nbsp;</div>
  </td>
     <td width="20px"> &nbsp;</td>
  </tr>
  </table>
</td> <%-- end of navbar --%>
