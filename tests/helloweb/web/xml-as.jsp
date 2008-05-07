<%@ page import="org.w3c.dom.*" %>
<%
  Document doc  = (Document) session.getAttribute("xmldata");
  Element  root = (Element)  doc.getFirstChild();
%>
<html>
 <body>
  <h1>
   <% if (root.getTagName().equals("complex-assembly")) { %>
    Complex Assembly
   <% } else { %>
    Base Assembly
   <% } %> </h1> </body> </html>
   
