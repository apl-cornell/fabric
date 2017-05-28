<%@page language="java" import="org.w3c.dom.*,java.io.StringWriter, cms.www.*, cms.www.util.*" %>
<%
if (((Boolean)session.getAttribute(AccessController.A_DEBUG)).booleanValue())
{ %>
<div>
	<h4>
		XML Tree
		<span id="xmltreehead">
		  	<a class="hide" href="#" onClick="show('xmltree', '(show)', '(hide)'); return false;">(show)</a>
		</span>
	</h4>
	<div id="xmltree" class="showhide" style="display:none">
<%-- print the full xml tree to the page --%>
<% Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
StringWriter writer = new StringWriter();
DOMWriter.write(displayData, writer); %>
		<pre><%-- avoid having the browser think the XML is HTML --%>
<%= writer.toString() %>
		</pre>
	</div>
</div><%
} %>