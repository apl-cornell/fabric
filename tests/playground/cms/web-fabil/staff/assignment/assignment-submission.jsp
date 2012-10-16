<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, java.util.*" %><%
Document displayData= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
Element root= (Element) displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); 
Element assignment= (Element) XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT);
Element submissions= (Element) assignment.getElementsByTagName(XMLBuilder.TAG_SUBMISSIONS).item(0); 
Element filetypes= (Element) root.getElementsByTagName(XMLBuilder.TAG_FILETYPES).item(0);
NodeList types= filetypes.getChildNodes(); %>
<h2>
  Required Submissions
  <span id="submissionshead">
    <a href="#" onClick="hide('submissions', '(hide)', '(show)'); return false;" class="hide">(hide)</a>
  </span>
</h2>
<div id="submissions" class="showhide">
  <table id="subtable" class="assignment_table" cellpadding="0" cellspacing="0" border="0" style="width: 100%; text-align: center">
    <tr>
      <th>File Name</th>
      <th>File Types</th>
      <th>Max Size</th>
      <th>Remove</th>
    </tr><%
/* Modify old Required Files */
NodeList files= submissions.getElementsByTagName(XMLBuilder.TAG_ITEM); 
int length= files.getLength();
for (int i= 0; i != length; i++) { 
  Element file= (Element)files.item(i); 
  String id= file.getAttribute(XMLBuilder.A_ID);
  String fname= file.getAttribute(XMLBuilder.A_NAME); %>
    <tr>
      <td>
        <input type="text" size="40" name="<%= AccessController.P_REQFILENAME + id %>" value="<%= fname %>">
        <p><small>Do not include file type, as it is automatically appended.</small></p>
      </td>
      <td style="width: 10%">
        <select name="<%= AccessController.P_REQFILETYPE + id %>" size="3" multiple style="width: 10em"><%
  NodeList formats= file.getChildNodes();
  Hashtable formathash= new Hashtable();
  for (int j= 0; j != formats.getLength(); j++) {
    Element format= (Element) formats.item(j);
    String formstr= format.getAttribute(XMLBuilder.A_TYPE).trim();
    formathash.put(formstr, format);
  }
  for (int j= 0; j != types.getLength(); j++) {
    Element item= (Element) types.item(j);
    String type= item.getAttribute(XMLBuilder.A_TYPE).trim();
    boolean checked= formathash.containsKey(type); %>
          <option<%= checked ? " selected" : " " %>><%= type %></option><%
  } %>
        </select>
      </td>
      <td style="width: 0%; white-space: nowrap">
        <input type="text" name="<%= AccessController.P_REQSIZE + id %>" value="<%= file.getAttribute(XMLBuilder.A_SIZE) %>" size="4">kB
              
      </td>
      <td style="width: 10%">
        <input type="checkbox" name="<%= AccessController.P_REMOVEREQ + id %>"<%= file.hasAttribute(XMLBuilder.A_REMOVED) ? " checked" : "" %>>
      </td>
    </tr><%
} %>
<%-- Add required submissions added before error --%>
<% 	NodeList newsubs = submissions.getElementsByTagName(XMLBuilder.TAG_NEWITEM);
	for (int j=0; j < newsubs.getLength(); j++) {
		Element newsub = (Element) newsubs.item(j); %>
		<tr id="sub<%= j %>">
			<td style="text-align: center">
				<input value="<%= newsub.getAttribute(XMLBuilder.A_NAME)%>" name="<%= AccessController.P_NEWREQFILENAME + j %>" type="text" size="40">
				<p><small>Do not include file type, as it is automatically appended.</small></p>
			</td>
			<td style="text-align: center; width: 10%">
			<% 	NodeList typelist = newsub.getChildNodes();
				HashSet ts = new HashSet();
				for (int k=0; k < typelist.getLength(); k++) {
					Element type = (Element) typelist.item(k);
					ts.add(type.getAttribute(XMLBuilder.A_TYPE));
				} %>
				<select name="<%= AccessController.P_NEWREQFILETYPE + j%>" size="3" style="width: 100%" multiple>
				<%	
				for (int k=0; k < types.getLength(); k++) {
					Element item= (Element) types.item(k);
    				String type= item.getAttribute(XMLBuilder.A_TYPE).trim(); %>
					<option<%= ts.contains(type) ? " selected" : "" %>><%= type %></option>
				<%} %>
				</select>
			</td>
			<td style="text-align: center; white-space: nowrap">
				<input type="text" size="4" value="<%= newsub.getAttribute(XMLBuilder.A_SIZE) %>" name="<%= AccessController.P_NEWREQSIZE + j %>">kB
			</td>
			<td style="text-align: center; width: 10%; white-space: nowrap">
				<a href="#" onClick="remove('<%= "sub" + j %>'); return false;">(Remove Row)</a>
			</td>
		</tr>
<% 	} %>
  </table>
<script type="text/javascript">
	subindex = <%= newsubs.getLength() %>;
</script>
  <div id="addsub">
    <div class="joke" style="float: right">Ctrl+click to select multiple file types.</div>
    <a href="#" onClick="addSubRow(); return false;" class="button">(New Row)</a>
  </div><%
NodeList hiddenfiles= submissions.getElementsByTagName(XMLBuilder.TAG_HIDDENITEM);
length= hiddenfiles.getLength();
if (length != 0) { %>
  <div class="replace">
    <span id="removedsubshead">
      <a href="#" onClick="show('removedsubs', 'Removed submissions &raquo;', '&laquo; Removed submissions'); return false;">Removed submissions &raquo;</a>
    </span>
    <table class="replacebody" id="removedsubs" cellpadding="0" cellspacing="0">
      <tr>
        <th>Name</th>
        <th>Types</th>
        <th>Max Size</th>
        <th>Restore</th>
      </tr><%
  for (int i= 0; i != length; i++) { 
    Element file= (Element) hiddenfiles.item(i); 
    String ID = file.getAttribute(XMLBuilder.A_ID); %>
      <tr class="<%= i % 2 == 0 ? "row_even" : "row_odd" %>">
        <td><input type="hidden" name="<%= AccessController.P_HIDDENREQNAME + ID %>" value="<%= file.getAttribute(XMLBuilder.A_NAME) %>">
        	<%= file.getAttribute(XMLBuilder.A_NAME) %>
		</td><%
    NodeList formats= file.getChildNodes();
    String res= "";
    for (int j= 0; j != formats.getLength(); j++) { 
      Element format= (Element) formats.item(j);
      if (j != 0) res+= ", ";
      String type = format.getAttribute(XMLBuilder.A_TYPE).trim();
      res+= type; %>
      <input type="hidden" name="<%= AccessController.P_HIDDENREQTYPE + ID %>" value="<%= type %>">
 <%   } %>
        <td>(<%= res %>)
        </td>
        <td><input type="hidden" name="<%= AccessController.P_HIDDENREQSIZE + ID %>" value="<%= file.getAttribute(XMLBuilder.A_SIZE) %>">
        	<%= file.getAttribute(XMLBuilder.A_SIZE) %>
		</td>
        <td class="remove">
          <input type="checkbox" name="<%= AccessController.P_RESTOREREQ + ID %>"<%= file.hasAttribute(XMLBuilder.A_RESTORED) ? " checked" : "" %>>
        </td>
      </tr><%
  } %>
    </table>
  </div><%
} %>
</div>