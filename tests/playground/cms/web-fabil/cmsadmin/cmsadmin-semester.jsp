<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); %>
  <h2>
    <a name="a_semester"></a>
    Set Current Semester
    <span id="semesterhead">
      <a class="hide" href="#" onClick="show('semester', '(show)', '(hide)'); return false;">(show)</a>
    </span>
  </h2>
  <div id="semester" class="showhide" style="display: none">
<% Element semRoot = (Element)root.getElementsByTagName(XMLBuilder.TAG_SEMESTERS).item(0); 
Element curSem = (Element)semRoot.getElementsByTagName(XMLBuilder.TAG_CURSEMESTER).item(0); %>
		<span id="admin_cursem" style="font-size:12px;font-weight:bold">
			<%= curSem.getAttribute(XMLBuilder.A_NAME) %>
		</span>
		<table class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
<% NodeList sems = XMLUtil.getChildrenByTagNameAndAttributeValue(semRoot, XMLBuilder.TAG_SEMESTER, XMLBuilder.A_HIDDEN, "false");
for (int i = 0; i < sems.getLength(); i++)
{
	Element item = (Element)sems.item(i);
	if (!item.getAttribute(XMLBuilder.A_HIDDEN).equalsIgnoreCase("true"))
	{ %>
			<tr>
				<td align="left">
					<%= item.getAttribute(XMLBuilder.A_NAME) %>
				</td>
				<td align="center">
					<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_SETCURSEMESTER %>&amp;<%= AccessController.P_ID %>=<%= item.getAttribute(XMLBuilder.A_ID) %>">
						Set as Current
					</a>
				</td>
				<td align="center">
					<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_EDITSEMESTER %>&amp;<%= AccessController.P_ID %>=<%= item.getAttribute(XMLBuilder.A_ID) %>&amp;<%= AccessController.P_HIDDEN %>=true">
						Remove
					</a>
				</td>
			</tr><%
	}
} %>
		</table>
<% NodeList hiddenSems = XMLUtil.getChildrenByTagNameAndAttributeValue(semRoot, XMLBuilder.TAG_SEMESTER, XMLBuilder.A_HIDDEN, "true");
if (hiddenSems.getLength() > 0 )
{ %>
		<div class="replace">
    		<span id="removedsemhead">
      		<a href="#" onClick="show('removedsem', 'Removed semesters &raquo;', '&laquo; Removed semesters'); return false;">Removed semesters &raquo;</a>
    		</span>
    		<table class="showhide" id="removedsem" style="display: none" cellpadding="0" cellspacing="0"><%
   for (int i = 0; i < hiddenSems.getLength(); i++)
  	{
   	Element sem= (Element) hiddenSems.item(i); %>
   			<tr class="<%= i % 2 == 0 ? "row_even" : "row_odd" %>">
   				<td><%= sem.getAttribute(XMLBuilder.A_NAME) %></td>
   				<td align="center">
   					<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_EDITSEMESTER %>&amp;<%= AccessController.P_ID %>=<%= sem.getAttribute(XMLBuilder.A_ID) %>&amp;<%= AccessController.P_HIDDEN %>=false">
   						Restore
   					</a>
   				</td>
   			</tr><%
   } %>
   		</table>
		</div><%
} %>
		<form name="semcreateform" action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_CREATESEMESTER %>" method="post">
  			<script type="text/javascript">
		  	/*
		  	create a list of the next n legal semester names AFTER the current semester
			and stick them into the future-semester-creation list here
			*/
			
			function isSpace(ch)
			{
				return ch == ' ' || ch == '\t' || ch == '\n';
			}
			
			var curSemName = getElementById('admin_cursem').childNodes[0].nodeValue;
			//strip whitespace
			while(isSpace(curSemName.charAt(0)))
				curSemName = curSemName.substring(1);
			while(isSpace(curSemName.charAt(curSemName.length - 1)))
				curSemName = curSemName.substring(0, curSemName.length - 1);
			var curSeason = curSemName.substring(0, curSemName.indexOf(' '));
			var curYear = parseInt(curSemName.substring(curSemName.indexOf(' ') + 1));
			document.write('<select name="<%= AccessController.P_NAME %>">');
			for (i = 0; i < 9; i++)
			{
				if (curSeason == 'Fall') {curSeason = 'Spring'; curYear++;}
				else if (curSeason == 'Spring') curSeason = 'Summer';
				else curSeason = 'Fall';
				document.write('<option');
				if (i == 0) document.write(' selected');
				document.write('>' + curSeason + ' ' + curYear + '</option>\n');
			}
			document.write('</select>');
			</script>
			&nbsp;&nbsp;<input type="submit" value="Add">
		</form>
	</div>
