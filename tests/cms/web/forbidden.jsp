<%@ page contentType="text/html" language="java" import="cms.www.*" 
%><?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >
  <head>
    <title>403 Forbidden</title>
    <style type="text/css">
      body {font-family: sans-serif; text-align: center}
      h1 {font-size: larger}
      a {color: #ab1a2a; text-decoration: none}
      a:hover {text-decoration: underline}
    </style>
  </head>
  <body>
    <h1>Forbidden Request</h1>
    <p>You do not have permission to view the requested information or perform the
    requested action.</p>
    <p>
    <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_OVER %>">Course Overview</a>
    </p>
  </body>
</html>