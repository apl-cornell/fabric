<%@ page language="Java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*" %>
<% Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
Element root = (Element) displayData.getChildNodes().item(0);
User p = (User)session.getAttribute(AccessController.A_PRINCIPAL);
String NetID = "";
boolean debug= ((Boolean)session.getAttribute(AccessController.A_DEBUG)).booleanValue(); 
if (debug && p != null) {
	NetID = p.getNetID();
} %>
        <tr>
          <td id="course_page_footer_1">
       
            <span class="spacer300">&nbsp;</span>
            <span class="joke">
<% if (debug) {
     long now= System.currentTimeMillis();
     long then= ((Long)session.getAttribute(AccessController.A_TIME)).longValue();
     now= now-then; %>
              Page rendered in: <%= Long.toString(now) %> ms
<% } %>
            </span> 
          </td>
          <td rowspan="2" id="course_menu_footer_1" valign="top"></td>
        </tr>
        <tr>
          <td valign="top" id="course_page_footer_2">
            <table cellpadding="0" cellspacing="0" border="0">
              <tr>
                <td id="course_page_footer_2_left"></td>
                <%-- the space keeps IE happy --%>
                <td width="100%" id="course_page_footer_2_right">&nbsp;</td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </div>
  </div>
     
<% if (debug) { 
   /* Display debug id and text box to switch id */ %>
   <span><a target="_blank" href="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_PROFILER %>">View Profiler Output</a></span>
   <div align="right">
   <form action="?<%=AccessController.P_ACTION %>=<%=AccessController.ACT_OVER%>" method="post">
   <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_OVER %>&amp;<%= AccessController.P_DEBUGID %>=<%= NetID %>"><%= NetID %></a>
   <input type="text" size="10" name="<%= AccessController.P_DEBUGID %>">
   <input type="submit" value="Switch ID">
   </form>  
   </div>

   <jsp:include page="list-debugids.jsp" />
   <%--<jsp:include page="print-xml-tree.jsp" /> moved to top of each page --%>
<% } %>
  <script type="text/javascript">
    <% if (root.hasAttribute(XMLBuilder.A_JUMPTOGROUP)) { %>
      window.location='<%= "#" + root.getAttribute(XMLBuilder.A_JUMPTOGROUP) %>';
	<% } %>
		
  </script>
</body>
</html>

