<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, edu.cornell.csuglab.cms.util.*" %>
<%
Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getFirstChild();
Element assignment = (Element)XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
NodeList comments = assignment.getElementsByTagName(XMLBuilder.TAG_COMMENTS).item(0).getChildNodes();
%>
<% if (comments.getLength() > 0) { %>
	<h2>
	  Grading Comments &amp; Requests
	  <span id="historyhead">
	    <a class="hide" href="#" onclick="show('history', '(show)', '(hide)'); return false;">(show)</a>
	  </span>
	</h2>
	<div id="history" class="showhide" style="display: none">
	  <table class="grading" cellpadding="10px" border="0" cellspacing="0" width="100%"><%
		 for (int i = 0; i < comments.getLength(); i++) {
		    Element comment = (Element) comments.item(i); 
		    String commentType = comment.getAttribute(XMLBuilder.A_TYPE);
		    String subProblemName = comment.hasAttribute(XMLBuilder.A_SUBPROBNAME) ? " for "+comment.getAttribute(XMLBuilder.A_SUBPROBNAME) : "";
		    String posterID =  comment.getAttribute(XMLBuilder.A_USER); %>
		    <tr class="<%= i % 2 == 0 ? "row_even" : "row_odd"%>">
		      <td>
		      	<div style="align: left">
		    	<strong><%=commentType%></strong>: Submitted by <%=posterID%> on <%=comment.getAttribute(XMLBuilder.A_DATE)%><%=subProblemName%>
			    <p style="margin-left: 20px"><%=GradeCommentInfo.formatComments(comment.getAttribute(XMLBuilder.A_TEXT)) %></p><%
				if (comment.hasAttribute(XMLBuilder.A_COMMENTFILEID)) { %>
		        <p style="margin-left: 20px">Attached file:
		        	<a href="?<%=AccessController.P_ACTION%>=<%=AccessController.ACT_DOWNLOAD%>&amp;<%=AccessController.P_ID%>=<%= comment.getAttribute(XMLBuilder.A_COMMENTFILEID)%>&amp;<%=AccessController.P_DOWNLOADTYPE%>=<%=XMLBuilder.T_COMMENTFILE%>">
					<%= comment.getAttribute(XMLBuilder.A_FILENAME) %>
					</a>
				</p><%
				} %>
				</div>
		      </td>
		    </tr>
	<% } %>
	  </table>
	</div>
<% } %>