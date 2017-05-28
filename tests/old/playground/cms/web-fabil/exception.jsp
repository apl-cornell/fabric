<%@ page contentType="text/html; charset=ISO-8859-1" language="java" %>
<html>
  <head>
    <title>Error Page</title>
  </head>
  <body>
    <font face="Comic Sans MS" size=4>
      <blockquote>
    <center>
      <h1><font color=red>System error</font></h1>
    </center>
    <p>Sorry, an error has occured.  </p>
    <p><pre><%= request.getAttribute("error_message") %></pre></p>
      </blockquote>
    </font>
  </body>
</html>
