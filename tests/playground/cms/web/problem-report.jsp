<%@page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %>
<%
/***************************************************************************************
* If the calling page's document contains a report of an error or a warning or just non-error info, 
* display it in a little box at the top of the page.
*
* The correct format for including a status report is that used by XMLBuilder.addStatus().
*
* All STATUS tags under ROOT are displayed here. If none exist, nothing is displayed.
***************************************************************************************/
Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
String error = root.hasAttribute(XMLBuilder.A_ERR_NAME) ? root.getAttribute(XMLBuilder.A_ERR_NAME) : null;
String message = root.getAttribute(XMLBuilder.A_ERR_MSG);
NodeList errs = root.getElementsByTagName(XMLBuilder.TAG_ERROR_LINE);
NodeList stati = (NodeList)root.getElementsByTagName(XMLBuilder.TAG_STATUS);%>
<%
if (stati.getLength() > 0) {%>
	<div class="status">
<%	for (int i=0; i < stati.getLength(); i++) {
		Element status = (Element)stati.item(i); 
		if (status != null) {
			if (i > 0) {%>
		<br>
<%			}%>
        <span class="<%= status.hasAttribute(XMLBuilder.A_ISERROR) ? "status_err" : (status.hasAttribute(XMLBuilder.A_ISWARNING) ? "status_warn" : "status_ok") %>" align="center">
        	<%= status.getAttribute(XMLBuilder.A_TEXT) %>
        </span>
<%	 	}
	}%>
<%	if (error != null) { %>
		<span id="errordivhead">
	  		<a href="#" class="hide" onclick="show('errordiv','(show details)','(hide details)'); return false;">(show details)</a>
	  	</span>
	  	<div id="errordiv" style="display: none">
	  		<strong>
	  			Exception: <%= error %><br>
	  			Message: <%= message %><br><br>
	  		</strong>
	  		<% for (int i=0; i < errs.getLength(); i++) {
	  			Element xErr = (Element) errs.item(i); %>
				&nbsp;&nbsp;&nbsp;<%= xErr.getAttribute(XMLBuilder.A_ERR_CLASS) %>.<%= xErr.getAttribute(XMLBuilder.A_ERR_METHOD) %>(<%= xErr.getAttribute(XMLBuilder.A_ERR_FILE) %>:<%= xErr.getAttribute(XMLBuilder.A_ERR_LINE) %>)
				<br>
			<% } %>
	  	</div>
<%	} %>
	</div><%
}%>
