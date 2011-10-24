<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, java.util.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
NodeList rows = XMLUtil.getChildrenByTagName(root, XMLBuilder.TAG_ROW);
int confirm_type = Integer.parseInt(root.getAttribute(XMLBuilder.A_CONFIRMTYPE));
String ID = (confirm_type == XMLBuilder.CONFIRM_ASSIGNINFO) ? root.getAttribute(XMLBuilder.A_ASSIGNID) 
			: ((confirm_type == XMLBuilder.CONFIRM_COURSEINFO || confirm_type == XMLBuilder.CONFIRM_FINALGRADES) ? root.getAttribute(XMLBuilder.A_COURSEID)
				: "");
%><jsp:include page="../../header.jsp"/>
<style type="text/css">
  div#div_left {float: left}
  span#span_right {float: right}
  .note {color: red}
  .showhide {font-weight: normal}
  .confirmtable {border-width: thin; width: 100%}
</style>
<jsp:include page="../../header-page.jsp"/>
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0" width="100%">
  <tr>
  <% if (confirm_type != XMLBuilder.CONFIRM_GENERAL) { %>
		<jsp:include page="../navbar.jsp"/>
 	<% } %>
    <td valign="top" id="course_page_container">
      <div id="course_page">
        <jsp:include page="../../problem-report.jsp"/>
        <h2>Parsed Data:</h2>
        <table class="confirmtable" cellpadding="0" cellspacing="0"><%
        TreeSet newNetIDs = new TreeSet();
        for (int i=0 ; i != rows.getLength(); i++) { 
          Element row= (Element)rows.item(i); 
          NodeList errors= row.getElementsByTagName(XMLBuilder.TAG_ERROR);
          NodeList cells= row.getElementsByTagName(XMLBuilder.TAG_ELEMENT); %>
          <tr><td><%= i == 0 ? "" : String.valueOf(i) %></td><%
          for (int j= 0; j != cells.getLength(); j++) {
            Element cell= (Element)cells.item(j);%>
            <td<%= cell.hasAttribute(XMLBuilder.A_COLSPAN) ? (" colspan=" + cell.getAttribute(XMLBuilder.A_COLSPAN)) : "" %>>
							<%= cell.getAttribute(XMLBuilder.A_DATA) %>
            </td><%
          } %>
          </tr><%
          if (errors.getLength() != 0) {%>
          <tr><td>&nbsp;</td>
          <td colspan="<%= cells.getLength() %>">
            <br>Errors:
            <ul><%
            for (int j= 0; j != errors.getLength(); j++) {
              Element error= (Element)errors.item(j); %>
              <li><%= error.getAttribute(XMLBuilder.A_DATA) %></li><%
              
              if (error.getAttribute(XMLBuilder.A_DATA).indexOf("is not an enrolled student in this course") != -1)
              {
              	String errorString = error.getAttribute(XMLBuilder.A_DATA);
             	newNetIDs.add(errorString.split(" ")[0].replaceAll("'", ""));
              }
            } %>
          </ul></td></tr><%
          } %><%
        } %>
        </table>
        <% if (confirm_type == XMLBuilder.CONFIRM_FINALGRADES) { %>
          <br><p class="note">Only data for the 'NetID' and 'Final Grade' columns are displayed if the file parsed correctly.</p>
        <% } %>
        <br><%
                    
	      switch(confirm_type) {
	        // Confirming an assignment grades CSV file
	        case XMLBuilder.CONFIRM_ASSIGNINFO:
	        
	        NodeList errors= root.getElementsByTagName(XMLBuilder.TAG_ERROR);
            boolean stopError = false;
            if (root.hasAttribute(XMLBuilder.A_ERROR) && errors.getLength() > newNetIDs.size())
            {
            	stopError = true;
            }
            
            if (!root.hasAttribute(XMLBuilder.A_ERROR)) { %>
	            <form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_CONFIRMTABLE %>&amp;<%= AccessController.P_ASSIGNID %>=<%= ID %>" method="post">
		            <%if (newNetIDs.size() > 0)
		            {
			            Iterator it = newNetIDs.iterator();
				       	%>
						<input type="checkbox" name="<%=AccessController.P_ADDSTUDENTSLIST%>" value = "on" onClick = "document>
				       	<input type="hidden" name="<%=AccessController.P_STUDENTSLIST%>" value = "<%
							boolean needsComma = false;
							while(it.hasNext())
							{
								String currentNetID = it.next().toString();
								if (needsComma)
								{%>,<%}
								%><%=currentNetID%><%
							needsComma = true;
							}%>"> 
						
						Enroll the following students in the course:<br>
				       	<ul>
				       	<%
				       	it = newNetIDs.iterator();
				       	
				       	while(it.hasNext())
				       	{
							String currentNetID = it.next().toString();%>
							<li><%=currentNetID%><br>
				       	<%
				       	}
				       	%>
						</ul>
						<br>
						<%
			       	}%>
	              <input type="submit" value="Submit Grades">
	            </form><%
	          } else { %>
	            <form id="uploadgrades" method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_UPLOADGRADES %>&amp;<%= AccessController.P_ASSIGNID %>=<%= ID %>">
	              Re-upload grades file: <input type="file" size="10" name="<%= AccessController.P_GRADESFILE %>"><input type="submit" value="Upload">
	            </form>
				<br>
				<%if (!stopError) {%>
					<form id = "submitgrades" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_CONFIRMTABLE %>&amp;<%= AccessController.P_ASSIGNID %>=<%= ID %>&amp;<%=AccessController.P_NEWNETIDS%>=true" method="post">
			            <%if (newNetIDs.size() > 0)
			            {
				            Iterator it = newNetIDs.iterator();
					       	%>
							<input type="hidden" name="<%=AccessController.P_ADDSTUDENTSLIST%>" value = "true">
					       	<input type="hidden" name="<%=AccessController.P_STUDENTSLIST%>" value = "<%
								boolean needsComma = false;
								while(it.hasNext())
								{
									String currentNetID = it.next().toString();
									if (needsComma)
									{%>,<%}
									%><%=currentNetID%><%
								needsComma = true;
								}%>">
				       	<%}%>
					  <input name="<%= AccessController.P_EMAILADDED %>" type="checkbox" checked> Notify New Students of Enrollment by E-mail
		              Enroll Missing NetIDs & Submit Grades: <input type="submit" value="Submit Grades">
		            </form><%
		         }
	          }
	          break;
	          
	        // Confirming a final grades CSV file
	        case XMLBuilder.CONFIRM_FINALGRADES: 
            if (!root.hasAttribute(XMLBuilder.A_ERROR)) { %>
	            <form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_CONFIRMFINALGRADES %>&amp;<%= AccessController.P_COURSEID %>=<%= ID %>" method="post">
	              <input type="submit" value="Submit Grades">
	            </form><%
	          } else { %>
		          <form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_FINALGRADESFILE + "&amp;" + AccessController.P_COURSEID + "=" + ID %>">
		              Re-upload final grades file: <input type="file" size="5" name="<%= AccessController.P_GRADESFILE %>"><input type="submit" value="Upload">
		          </form><%
		        }
	          break;
	        // Confirming student info for a course
			case XMLBuilder.CONFIRM_COURSEINFO:
            if (!root.hasAttribute(XMLBuilder.A_ERROR)) { %>
              <form action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_CONFIRMSTUDENTINFO + "&amp;" + AccessController.P_COURSEID + "=" + ID %>" method="post">
                <input type="submit" value="Commit Data">
              </form><%
            }
            else
            {
            	if (session.getAttribute(AccessController.A_ISCLASSLIST).equals(new Boolean(true)))
					{%>
					<form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_UPLOADSTUDENTINFO + "&amp;" + AccessController.P_COURSEID + "=" + ID + "&amp;" + AccessController.P_ISCLASSLIST + "=yes" %>">
<%					}
					else
					{%>
					<form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_UPLOADSTUDENTINFO + "&amp;" + AccessController.P_COURSEID + "=" + ID %>">
<%					}%>
                  Re-upload file: <input type="file" size="5" name="<%= AccessController.P_UPLOADEDCSV %>"><input type="submit" value="Upload">
              </form><%
            }
	          break;
	        //confirming student info for the system
	        case XMLBuilder.CONFIRM_GENERAL:
	        	if (!root.hasAttribute(XMLBuilder.A_ERROR)) { %>
              <form action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_CONFIRMSTUDENTINFO %>" method="post">
                <input type="submit" value="Commit Data">
              </form><%
            } else { %>
              <form method="post" enctype="multipart/form-data" action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_UPLOADSTUDENTINFO %>">
                  Re-upload file: <input type="file" size="5" name="<%= AccessController.P_UPLOADEDCSV %>"><input type="submit" value="Upload">
              </form><%
            }
	      } %>
		</div>
    </td>
    <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
  </tr>
<jsp:include page="../../footer.jsp"/>