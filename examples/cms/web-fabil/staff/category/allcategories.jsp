<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*"%>
<%
/****************************************************************************
list, but don't display, all (visible and non-removed) categories for a course
****************************************************************************/
	Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root= (Element)displayData.getChildNodes().item(0);
   Element hiddenCtg = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_HIDDENCTG);
   Element visibleCtg  = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_VISIBLECTG);
   int numOfCtg = Integer.parseInt(visibleCtg.getAttribute(XMLBuilder.A_NUMOFCTGS));
	NodeList visCtgList = visibleCtg.getChildNodes();
	if (visCtgList != null && visCtgList.getLength() == 0){%>
No content available
<%  } else{%>
<table id="ctgswaptable" class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
	<tr>
		<th>Order</th>
		<th>Name</th>
		<th>Remove</th><%		
		for (int i=0; i<visCtgList.getLength(); i++){ 
			Element ctg = (Element)visCtgList.item(i);
			String ctgID = ctg.getAttribute(XMLBuilder.A_ID);%>
	<tr>
		<td id="<%= AccessController.P_CTGPOSITION + ctgID + "_cell" %>" align="center">
			<%-- Evan's new reordering code --%>
			<script type="text/javascript">
			getElementById('<%= AccessController.P_CTGPOSITION + ctgID + "_cell" %>').appendChild(registerIDForOrdering('ctgOrdering', '<%= AccessController.P_CTGPOSITION + ctgID %>'));
			</script>
		</td>
		<td align="center">
			<a href="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_EDITCTG %>&amp;<%= AccessController.P_CATID %>=<%= ctgID %>">
			<%=ctg.getAttribute(XMLBuilder.A_NAME) %>
      </a>
		</td>
		<td align="center">
			<input type="checkbox" name="<%= AccessController.P_REMOVECTG + ctgID %>">
		</td>
		<%}%>
</table><% 
	}
 	NodeList hiddenCtgList = hiddenCtg.getChildNodes();
 	if (hiddenCtgList == null) System.out.println("It is null");
	if ((hiddenCtgList != null) && (hiddenCtgList.getLength()>0) ){%>
				<div class="replace">
    				<span id="removedctghead">
      					<a href="#" onClick="show('removedctg', 'Removed categories &raquo;', '&laquo; Removed categores'); return false;">Removed categories &raquo;</a>
    				</span>
    				<table class="replacebody" id="removedctg" cellpadding="0" cellspacing="0">
      					<tr>
       						<th>Name</th>
       						<th>Restore</th>
     					</tr><% 
		for (int i= 0; i<hiddenCtgList.getLength(); i++) { 
    		Element ctg= (Element) hiddenCtgList.item(i); %>
      					<tr class="<%= i % 2 == 0 ? "row_even" : "row_odd" %>">
        					<td><%= ctg.getAttribute(XMLBuilder.A_NAME) %></td>
        					<td class="remove">
          						<input type="checkbox" name="<%= AccessController.P_RESTORECTG + ctg.getAttribute(XMLBuilder.A_ID)%>">
        					</td>
      					</tr><% 
    	} %>
    				</table>
				</div>
<%	}%>
	