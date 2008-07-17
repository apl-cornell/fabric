<%@page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*" %><%
Document displaydata= (Document) session.getAttribute(AccessController.A_DISPLAYDATA); 
Element root= (Element) displaydata.getFirstChild();
Element assignment= (Element) XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_ASSIGNMENT); 
Element items= (Element) assignment.getElementsByTagName(XMLBuilder.TAG_ITEMS).item(0);
NodeList allitems= items.getElementsByTagName(XMLBuilder.TAG_ITEM);
NodeList hiddenitems= items.getElementsByTagName(XMLBuilder.TAG_HIDDENITEM);
int length= allitems.getLength(); %>
<h2>
  Assignment Files
  <span id="fileshead">
    <a class="hide" href="#" onClick="hide('files', '(hide)', '(show)'); return false;">(hide)</a>
  </span>
</h2>
<div id="files" class="showhide">
  <table id="filestable" class="assignment_table" cellspacing="0" cellpadding="0" style="width: 100%" border="0">
    <tr>
      <th>Name</th>
      <th>File</th>
      <th>Remove</th>
    </tr>
<% 	for (int i= 0; i != length; i++) {
    Element item= (Element) allitems.item(i); 
    String id= item.getAttribute(XMLBuilder.A_ID); %>
    <tr>
    	<input type="hidden" name="<%= AccessController.P_ITEMID %>" value="<%= id %>">
      	<td style="text-align: center">
        	<input type="text" value="<%= item.getAttribute(XMLBuilder.A_NAME) %>" name="<%= AccessController.P_ITEMNAME + id %>">     
        	<p><small>Do not include file type, as it is automatically appended.</small></p>
      	</td>
      	<td style="width: 50%; text-align: center; padding: 1em"><%
    		Element file= (Element) item.getElementsByTagName(XMLBuilder.TAG_FILE).item(0); %>
        	<input type="hidden" name="<%= AccessController.P_ITEMFILENAME + id %>" value="<%= file.getAttribute(XMLBuilder.A_NAME) %>">
        	<%= file.getAttribute(XMLBuilder.A_NAME) %>
        	<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_DOWNLOAD %>&amp;<%= AccessController.P_DOWNLOADTYPE %>=<%= XMLBuilder.T_ITEMFILE %>&amp;<%= AccessController.P_ID %>=<%= id %>" class="button">(download)</a>
        	<div class="replace">
          		<div class="replacenewfile">Replace with new file:  
          			<input type="hidden" id="<%= AccessController.P_HIDDENITEMFILE + id %>" name="<%= AccessController.P_HIDDENITEMFILE + id %>" value="<%= item.getAttribute(XMLBuilder.A_REPLACEITEMFILE) %>">
          			<input type="file" onChange="setFileValue('<%= AccessController.P_ITEMFILE + id %>', '<%= AccessController.P_HIDDENITEMFILE + id %>');"  id="<%= AccessController.P_ITEMFILE + id %>" name="<%= AccessController.P_ITEMFILE + id %>">
          			<script type="text/javascript">
          				var x = getElementById('<%= AccessController.P_ITEMFILE + id %>');
          				x.value = '<%= item.getAttribute(XMLBuilder.A_REPLACEITEMFILE) %>';
          			</script>
          		</div><%
   				NodeList hiddenfiles= item.getElementsByTagName(XMLBuilder.TAG_HIDDENFILE); 
    			if (hiddenfiles.getLength() != 0) { %>
          			<span id="replacefile<%= i %>head">
            			<a href="#" onClick="show('replacefile<%= i %>', 'Restore file &raquo;', '&laquo; Restore file'); return false;">Restore file &raquo;</a>
          			</span>
          		<div id="replacefile<%= i %>" class="replacebody">
            	Restore old file:
            	<table cellpadding="0" cellspacing="0">
              		<tr>
                		<th>Date</th>
                		<th>Name</th>
                		<th class="remove">Restore</th>
              		</tr><%
      				for (int j= 0; j != hiddenfiles.getLength(); j++) { 
        				Element hiddenitem= (Element) hiddenfiles.item(j);
       					String hid= hiddenitem.getAttribute(XMLBuilder.A_ID); %>
						<input type="hidden" name="<%= AccessController.P_HIDDENITEMFILEID + id %>" value="<%= hid %>">
              			<tr class="<%= j % 2 == 0 ? "row_even" : "row_odd" %>">
               				<td>
               					<input type="hidden" name="<%= AccessController.P_HIDDENITEMFILEDATE + id + "_" + hid %>" value="<%= hiddenitem.getAttribute(XMLBuilder.A_DATE) %>">
                				<%= hiddenitem.getAttribute(XMLBuilder.A_DATE) %>
                			</td>
                			<td>
                  				<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_DOWNLOAD %>&amp;<%= AccessController.P_DOWNLOADTYPE %>=<%= XMLBuilder.T_FILEFILE %>&amp;<%= AccessController.P_ID %>=<%= hiddenitem.getAttribute(XMLBuilder.A_ID)%>">
                    				<input type="hidden" name="<%= AccessController.P_HIDDENITEMFILENAME + id + "_" + hid %>" value="<%= hiddenitem.getAttribute(XMLBuilder.A_NAME) %>">
									<%= hiddenitem.getAttribute(XMLBuilder.A_NAME) %>
                  				</a>
                			</td>
                			<td class="remove">
                  				<input type="checkbox" name="<%= AccessController.P_RESTOREITEMFILE + id + "_" +  hid %>"<%= hiddenitem.hasAttribute(XMLBuilder.A_RESTOREITEMFILE) ? " checked" : "" %>>
                			</td>
              			</tr><%
      				} %> <%-- end hiddenfiles for --%>
            	</table>
          		</div>
          		<% } %> <%-- end hiddenfiles if --%>
        	</div>
      	</td>
     	<td style="text-align: center; width: 10%">
        	<input type="checkbox" name="<%= AccessController.P_REMOVEITEM + id %>"<%= item.hasAttribute(XMLBuilder.A_REMOVEITEM) ? " checked" : "" %>>
      	</td>
	</tr><%
  } %> <%-- end existing items for --%>
<% 	NodeList newItems = items.getElementsByTagName(XMLBuilder.TAG_NEWITEM);
	for (int j=0; j < newItems.getLength(); j++) {
		Element newItem = (Element) newItems.item(j); %>
		<tr id="file<%= j %>">
			<td style="text-align: center; width: 10%; white-space: nowrap">
				<input type="text" size="40" name="<%= AccessController.P_NEWITEMNAME + j %>" value="<%= newItem.getAttribute(XMLBuilder.A_NAME) %>">
				<p><small>Do not include file type, as it is automatically appended.</small></p>
			</td>
			<td style="padding: 1em; width: 50%; text-align: center">
				<input type="hidden" id="<%= AccessController.P_NEWITEMFILEPATH + j %>" name="<%= AccessController.P_NEWITEMFILEPATH + j %>" value="<%= newItem.getAttribute(XMLBuilder.A_NEWITEMFILEPATH) %>">
				<input onChange="setFileValue('<%= AccessController.P_NEWITEMFILE + j %>', '<%= AccessController.P_NEWITEMFILEPATH + j %>');" type="file" id="<%= AccessController.P_NEWITEMFILE + j %>" name="<%= AccessController.P_NEWITEMFILE + j %>" value="<%= newItem.getAttribute(XMLBuilder.A_NEWITEMFILEPATH) %>">
			</td>
			<td style="width: 10%; text-align: center; white-space: nowrap">
				<a href="#" onClick="removeLink('<%= AccessController.P_NEWITEMFILE + j %>'); return false;">(Remove row)</a>
			</td>
		</tr>
<% } %>
</table>
<script type="text/javascript">
	filesindex = <%= newItems.getLength() %>;
</script>
  <a id="addfiles" href="#" onClick="addOuterRow(); return false;" class="button">(New Row)</a><%
if (hiddenitems.getLength() != 0) { %>
  <div class="replace">
    <span id="removeditemshead">
      <a href="#" onClick="show('removeditems', 'Removed files &raquo;', '&laquo; Removed files'); return false;">Removed files &raquo;</a>
    </span>
    <table class="replacebody" id="removeditems" cellpadding="0" cellspacing="0">
      <tr>
        <th>Date</th>
        <th>Name</th>
        <th>Restore</th>
      </tr><%
  for (int i= 0; i != hiddenitems.getLength(); i++) { 
    Element hiddenitem= (Element) hiddenitems.item(i); 
    String hid= hiddenitem.getAttribute(XMLBuilder.A_ID); %>
      <tr class="<%= i % 2 == 0 ? "row_even" : "row_odd" %>">
        <td><input type="hidden" name="<%= AccessController.P_HIDDENITEMDATE + hid %>" value="<%= hiddenitem.getAttribute(XMLBuilder.A_DATE) %>">
        	<%= hiddenitem.getAttribute(XMLBuilder.A_DATE) %>
		</td>
        <td><input type="hidden" name="<%= AccessController.P_HIDDENITEMNAME + hid %>" value="<%= hiddenitem.getAttribute(XMLBuilder.A_NAME) %>">
        	<%= hiddenitem.getAttribute(XMLBuilder.A_NAME) %>
		</td>
        <td>
          <input type="checkbox" name="<%= AccessController.P_RESTOREITEM + hid %>"<%= hiddenitem.hasAttribute(XMLBuilder.A_RESTOREITEM + hid) ? " checked" : "" %>>
        </td>
      </tr><%
  } %>
    </table>
  </div><%
} %>
  <div id="solblock"><%
Element solfile= (Element) assignment.getElementsByTagName(XMLBuilder.TAG_SOLFILE).item(0);
if (solfile != null) { %>
    <h3>Current Solution file:</h3>
    <div style="float: right">
      Remove
      <input type="checkbox" name="<%= AccessController.P_REMOVESOL %>"<%= solfile.hasAttribute(XMLBuilder.A_REMOVESOL) ? " checked" : "" %>>
    </div>
    <input type="hidden" name="<%= AccessController.P_SOLFILEID %>" value="<%= solfile.getAttribute(XMLBuilder.A_ID) %>">
    <input type="hidden" name="<%= AccessController.P_SOLFILENAME %>" value="<%= solfile.getAttribute(XMLBuilder.A_FILENAME) %>">
    <a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_DOWNLOAD %>&amp;<%= AccessController.P_DOWNLOADTYPE %>=<%= XMLBuilder.T_SOLFILE %>&amp;<%= AccessController.P_ID %>=<%= solfile.getAttribute(XMLBuilder.A_ID) %>"><%= solfile.getAttribute(XMLBuilder.A_FILENAME) %></a><%
} %>
    <h3>Upload new solution:</h3>
    <input type="hidden" id="<%= AccessController.P_SOLFILEPATH %>" name="<%= AccessController.P_SOLFILEPATH %>" value="<%= assignment.getAttribute(XMLBuilder.A_SOLFILEPATH) %>">
    <input id="<%= AccessController.P_SOLFILE %>" onChange="setFileValue('<%= AccessController.P_SOLFILE %>', '<%= AccessController.P_SOLFILEPATH %>');" type="file" name="<%= AccessController.P_SOLFILE %>" value="<%= assignment.hasAttribute(XMLBuilder.A_SOLFILEPATH) ? assignment.getAttribute(XMLBuilder.A_SOLFILEPATH) : "" %>" size="40"><%
NodeList hiddensols= assignment.getElementsByTagName(XMLBuilder.TAG_HIDDENSOLFILE);
length= hiddensols.getLength();
if (length > 0) { %>
    <div class="replace">
      <span id="restoresolhead">
        <a href="#" onClick="show('restoresol', 'Removed solutions &raquo;', '&laquo; Removed solutions'); return false;">Removed solutions &raquo;</a>
      </span>
      <table class="replacebody" id="restoresol" cellpadding="0" cellspacing="0">
        <tr>
          <th>File Name</th>
          <th>Restore</th>
        </tr><%
  for (int i= 0; i != length; i++) { 
  Element item= (Element)hiddensols.item(i); %>
        <tr class="<%= i % 2 == 0 ? "row_even" : "row_odd" %>">
        	<input type="hidden" name="<%= AccessController.P_HIDDENSOLID %>" value="<%= item.getAttribute(XMLBuilder.A_ID) %>">
          <td><input type="hidden" name="<%= AccessController.P_HIDDENSOLNAME + item.getAttribute(XMLBuilder.A_ID) %>" value="<%= item.getAttribute(XMLBuilder.A_FILENAME) %>">
          	<%= item.getAttribute(XMLBuilder.A_FILENAME) %>
		  </td>
          <td>
            <input type="checkbox" name="<%= AccessController.P_RESTORESOL + item.getAttribute(XMLBuilder.A_ID) %>"<%= item.hasAttribute(XMLBuilder.A_RESTORED) ? " checked" : "" %>>
          </td>
        </tr><%
   } %>
      </table>
    </div><%
} %>
  </div>
</div>