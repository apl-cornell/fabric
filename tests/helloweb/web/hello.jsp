<!-- this file will hopefully access fabric from within tomcat!  Cool! -->
<%@ page import="org.w3c.dom.*" %>
<%
  Document doc  = (Document) session.getAttribute("xmldata");
  Element  root = (Element)  doc.getFirstChild();
%>
<html>
 <body>
 <h1>OO7 Benchmark Database</h1>
 <table>
  <tr> <th /> <th>Number of assemblies</th> <th>Design root</th> </tr>
  <%
  NodeList modules = root.getElementsByTagName("module");
  for (int i = 0; i < modules.getLength(); i++) {
   Element e = (Element) modules.item(i); %>
   <tr>
    <td>Module</td>
    <td><%= e.getElementsByTagName("assembly").getLength() %></td>
    <td><%
     Element designRoot = (Element) e.getElementsByTagName("design-root").item(0); %>
     <a href="foo?action=viewAssembly&id=<%= designRoot.getAttribute("id")%>">(view)</a>
    </td>
   </tr> <%
  } %>
 </table>
 </body>
</html>
