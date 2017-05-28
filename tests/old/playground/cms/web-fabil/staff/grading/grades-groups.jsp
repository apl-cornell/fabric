<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Element root= (Element)displayData.getFirstChild();
Element assignment= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_ASSIGNMENT());
String assignid= assignment.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ASSIGNID());
Element course= XMLUtil._Proxy.getFirstChildByTagName(root, XMLBuilder._Static._Proxy.$instance.get$TAG_COURSE());
boolean isAdmin = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISADMIN());
boolean isGrades = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISGRADES());
boolean isGroups = course.hasAttribute(XMLBuilder._Static._Proxy.$instance.get$A_ISGROUPS());%>
<div>
<%	if (isAdmin || isGrades)
	{%>
	  <form id="uploadgrades" method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_UPLOADGRADES %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignid %>">
	  	<table border="0">
	  		<tr>
	  			<td align="left" nowrap>
	  				Upload grades file: <input id="upload_grades_file" type="file" size="10" name="<%= AccessController.P_GRADESFILE %>">
	  				<input type="submit" value="Upload">
	  			</td>
	  		</tr>
	  		<tr>
	  			<td align="left" nowrap>
	  				<div id="export">
			    		<span class="example">(Download <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_EXPORTGRADESTABLE %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignid %>">template</a>)</span>
			    	</div>
	    		</td>
	    	</tr>
	    </table>
	  </form>
<%	}%>
</div>