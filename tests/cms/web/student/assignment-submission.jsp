<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.model.*" %><%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
Element assignment = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
if (assignment == null) System.out.println("Assignment null");
Element group= XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_GROUP);
boolean pastDue = assignment.hasAttribute(XMLBuilder.A_PASTDUE);
String latedate = assignment.getAttribute(XMLBuilder.A_LATEFULLDATE);
boolean hasLate = latedate != null && !latedate.equals("");
boolean pastLate = assignment.hasAttribute(XMLBuilder.A_PASTLATE);
String extension = group.getAttribute(XMLBuilder.A_EXTENSION);
boolean hasExtension = extension != null && !extension.equals("");
boolean pastExtension = group.hasAttribute(XMLBuilder.A_PASTEXTENSION);
String status = assignment.getAttribute(XMLBuilder.A_STATUS);
boolean isOpen = status.equals(Assignment.OPEN);
Element submissions = XMLUtil.getFirstChildByTagName(assignment, XMLBuilder.TAG_SUBMISSIONS);
if (submissions != null) { %>
<h2>
  <a name="submission"></a>
  Submissions 
  <span id="submissionhead">
    <a class="hide" href="#" onClick="hide('submission', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>
<div id="submission" class="showhide"><%
  NodeList files = submissions.getElementsByTagName(XMLBuilder.TAG_ITEM);
  boolean displaySubmit = false;
  if (files.getLength() == 0) { %> 
  <p>No submissions required<%
  } // See if assignment is still open for submissions
  else if (!isOpen) {
    if (hasExtension && !pastExtension) {
      displaySubmit = true; %>
      <p style="color: red">Your group has been granted an extension until: <%= extension %></p>
  <% } else { %>
      <p>Submissions for this assignment are no longer being accepted.</p><%
     }
  }
  // Check if submission deadlines have passed
  else if (pastDue) { 
      if (hasExtension && !pastExtension) { 
        displaySubmit = true; %>
        <p style="color: red">Your group has been granted an extension until: <%= extension %></p>
      <% } else if (hasLate && !pastLate) { 
        displaySubmit = true; %>
        <p style="color: red">Submission deadline has passed.<br>Late submissions are being accepted until: <%= latedate %></p>
      <% } else { %>
          <p>Submissions for this assignment are no longer being accepted.</p><%
         }
  } else {
    displaySubmit = true;
  } %>
<% if (displaySubmit) { %>
  <br>
  <form action="?<%=AccessController.P_ACTION %>=<%= AccessController.ACT_FILESUBMIT %>&amp;<%= AccessController.P_ASSIGNID %>=<%= assignment.getAttribute(XMLBuilder.A_ASSIGNID) %>" enctype="multipart/form-data" method="post">
    <table class="submissions" cellpadding="0" cellspacing="0" border="0">
      <tr>
        <th>&nbsp;</th>
        <th>Format</th>
        <th>Max Size</th>
        <th>&nbsp;</th>
      </tr><%
    for (int i = 0; i < files.getLength(); i++) {
      String style = (i % 2 == 0 ? "row_even" : "row_odd");
      Element file = (Element) files.item(i); %>
      <tr class="<%= style %>">
        <td><%= file.getAttribute(XMLBuilder.A_NAME) %></td>
        <td><%= file.hasAttribute(XMLBuilder.A_TYPELIST) ? file.getAttribute(XMLBuilder.A_TYPELIST) : "Any" %></td>
        <td><%= file.hasAttribute(XMLBuilder.A_SIZE) ? file.getAttribute(XMLBuilder.A_SIZE) + " kb" : "no restriction" %></td>
        <td class="file">
          <input type="file" name="file_<%= file.getAttribute(XMLBuilder.A_ID) %>">
        </td>
      </tr><%
    } %>
    </table>
    <input type="submit" value="Upload">
  </form><%
  }
	Element submitted = XMLUtil.getFirstChildByTagName(group, XMLBuilder.TAG_SUBMITTED);
	if (files.getLength() > 0 && submitted != null) {
	  files = submitted.getChildNodes();%>
<br>
  <table class="submissions" cellpadding="2" cellspacing="0" border="0">
    <tr>
      <th>Submitted</th>
      <th>Date</th>
      <th>By</th>
      <th>Size</th>
      <th>
        <a id="whatsthis" href="#" onClick="alert('MD5 Message-Digest Algorithm is used to produce the fingerprint of a file as a way to verify the integrity of the file content. You can download MD5 utility on most platforms and check fingerprint of your file against the value listed here.'); return false;">What's this?</a>
        MD5
      </th>
    </tr><%
        for (int i = 0; i < files.getLength(); i++) {
          Element file = (Element) files.item(i); 
          String style= (i % 2 == 0) ? "row_even" : "row_odd"; %>
    <tr class="<%= style %>">
      <td><%= file.getAttribute(XMLBuilder.A_NAME) %></td>
      <td><%= file.getAttribute(XMLBuilder.A_DATE) %></td>
      <td><%= file.getAttribute(XMLBuilder.A_USER) %></td>
      <td><%= file.getAttribute(XMLBuilder.A_SIZE) %></td>
      <td class="md5"><%= file.getAttribute(XMLBuilder.A_MD5) %></td>
    </tr><%
        } %>
  </table>
<%  }
 } %>
</div>
