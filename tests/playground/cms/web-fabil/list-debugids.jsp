<%@ page language="Java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
boolean debug= ((Boolean)session.getAttribute(AccessController.A_DEBUG)).booleanValue();
if (debug)
{
  Element debugids= (Element) root.getElementsByTagName(XMLBuilder._Static._Proxy.$instance.get$TAG_DEBUGIDS()).item(0);
  if (debugids == null)
  { %>
<font color=#ff0000>WARNING: session debug flag set, but no debug ID list found in XML tree!</font>
<%}
	else
	{
  	NodeList l= debugids.getChildNodes();
	  for (int i= 0; i != l.getLength(); i++)
	  {
	    Element id= (Element) l.item(i);
	    String debugid= id.getAttribute(XMLBuilder._Static._Proxy.$instance.get$A_DEBUGID()); %>
<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_OVER %>&amp;<%= AccessController.P_DEBUGID %>=<%= debugid %>"><%= debugid %></a>
<%	}
  }
}  %>