<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0); 
NodeList logs = ((Element) root.getElementsByTagName(XMLBuilder.TAG_LOGSEARCH_RESULTS).item(0)).getChildNodes(); 
int count = 0; %>
<script type="text/javascript" src="../header.js"></script>
<script type="text/javascript">
	function showLogDetails(show) {
		var logspan = getElementById('showlogdetails');
		var newlink= document.createElement('a');
		var oldlink= logspan.getElementsByTagName('a').item(0);
		var txt= document.createTextNode(show ? '(Hide Log Details)' : '(Show Log Details)');
		newlink.appendChild(txt);
		newlink.href= '#';
		newlink.onclick = new Function('showLogDetails(' + (show ? 'false' : 'true') + ');');
		logspan.replaceChild(newlink, oldlink);
		var i = 0;
		var logrow;
		while ((logrow = getElementById('logdetail_' + i++)) != null) {
			logrow.style.display = show ? '' : 'none';
		}
		return false;
	}
</script>
    <h2>Search Results:</h2><br>
	      <% if (logs.getLength() == 0) { %>
             No matching logs found.
        <% } else { %> 
        	<span id="showlogdetails">
        		<a href="#" onclick="showLogDetails(true);">(Show Log Details)</a>
            </span>
             <table id="log_results_table" cellpadding="1" cellspacing="0" border>
             <tr>
                <th>Log Type</th><th>Action</th><th>Acting NetID</th><th>Acting IP Address</th>
                <th>Affected NetIDs</th><th>Simulated NetID</th><th>Assignment</th><th>Date</th>
             </tr>
	           <% for (int i=0; i < logs.getLength(); i++) {
	               Element l = (Element) logs.item(i);
	               String logID = l.getAttribute(XMLBuilder.A_LOGID);
	               NodeList recs = l.getElementsByTagName(XMLBuilder.TAG_RECNETID);
	               String recNetIDs = "";
	               for (int j=0; j < recs.getLength(); j++) {
	                  String netid = ((Element)recs.item(j)).getAttribute(XMLBuilder.A_NETID);
	                  recNetIDs += (recNetIDs.equals("") ? netid : (", " + netid));
	               }
	               %>
                 <tr><td><%= l.getAttribute(XMLBuilder.A_LOGTYPE) %></td>
                     <td><%= l.getAttribute(XMLBuilder.A_LOGNAME) %>&nbsp;</td>
                     <td><%= l.getAttribute(XMLBuilder.A_NETID) %>&nbsp;</td>
                     <td><%= l.getAttribute(XMLBuilder.A_IPADDRESS) %>&nbsp;</td>
                     <td><%= recNetIDs %>&nbsp;</td>
                     <td><%= (l.hasAttribute(XMLBuilder.A_SIMNETID) ? l.getAttribute(XMLBuilder.A_SIMNETID) : "") %>&nbsp;</td>
                     <td><%= (l.hasAttribute(XMLBuilder.A_ASSIGNMENT) ? l.getAttribute(XMLBuilder.A_ASSIGNMENT) : "") %>&nbsp;</td>
                     <td><%= l.getAttribute(XMLBuilder.A_DATE) %>&nbsp;</td>
                 </tr>
                 <tr align="left" id='logdetail_<%= count++ %>' style="display:none">
                 	<td align="left" colspan='9'><ul>
                     <% NodeList details = l.getElementsByTagName(XMLBuilder.TAG_LOGDETAIL);
                        for (int j=0; j < details.getLength(); j++) { %>
                          <li><%= ((Element)details.item(j)).getAttribute(XMLBuilder.A_DETAILS) %>
                     <% } %>
                     </ul>
                    </td>
                 </tr>
             <% } %>
             </table>
	      <% } %>
