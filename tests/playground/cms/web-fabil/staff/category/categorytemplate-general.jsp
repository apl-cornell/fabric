<%@ page language="java" import="org.w3c.dom.*, cms.auth.*, cms.model.*,cms.www.*,cms.www.xml.*, edu.cornell.csuglab.cms.util.category.*"%>
<% Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root= (Element)displayData.getChildNodes().item(0);
   Element course= (Element)root.getElementsByTagName(XMLBuilder.TAG_COURSE).item(0); 
   Element category= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_CATEGORY); 
   String catID = category.getAttribute(XMLBuilder.A_ID);
   String sortOrder = category.getAttribute(XMLBuilder.A_ORDER);
   int authorzn = Integer.parseInt(category.getAttribute(XMLBuilder.A_AUTHORZN));%>

<h3>
	General:
</h3>
	<table class="assignment_table" width="100%" cellpadding="2" cellspacing="0" border="0">
		<tr>
			<td>Name</td>
			<td>
				<input type="text" size="50" name="<%=(category.hasAttribute(XMLBuilder.A_NAME) ? AccessController.P_CTGNAME+catID  : AccessController.P_NEWCTGNAME) %>" value="<%= (category.hasAttribute(XMLBuilder.A_NAME) ? category.getAttribute(XMLBuilder.A_NAME) : "") %>">
			</td>
		</tr>
		<tr>
	  		<td>Sort Order</td>
			<td>
				<span title="Rows are sorted in ascending order">
	      		<input type="Radio" value="<%=Category.ASCENDING%>" name="<%=AccessController.P_ORDER%>" <%=(sortOrder.equals("1") ? " checked>" : ">")%>
	      			Ascending
	    		</span><br>
	    		<span title="Rows are sorted in descending order">
	      		<input type="Radio" value="<%=Category.DESCENDING%>" name="<%=AccessController.P_ORDER%>" <%=(sortOrder.equals("1") ? ">" : "checked>")%>
	      			Descending
	    		</span><br>
	  		</td>
		</tr>
		<tr>
			<td>Max Items to Show</td>
			<td>
				<span title="Select max items to show">
				<% String numToShow = category.getAttribute(XMLBuilder.A_NUMSHOW).equals(""+Category.SHOWALL)?"":category.getAttribute(XMLBuilder.A_NUMSHOW);%>
				<input type="Text" size="5" name="<%=AccessController.P_NUMSHOWITEMS%>" value="<%=numToShow%>">
				&nbsp;(blank interpreted as Show All)
				</span>
			</td>
		</tr>
		<tr>
			<td>Content Accessible By</td>
			<td>
				<input type="Radio" value="<%=User.AUTHORIZATION_LEVEL_STAFF%>" name="<%=AccessController.P_AUTHORZN%>" <%=authorzn == User.AUTHORIZATION_LEVEL_STAFF ? "checked>" : ">"%>
					Admin Only
				<br>
				<input type="Radio" value="<%=User.AUTHORIZATION_LEVEL_STUDENT%>" name="<%=AccessController.P_AUTHORZN%>" <%=authorzn == User.AUTHORIZATION_LEVEL_STUDENT ? "checked>" : ">"%>
					Admin and Students
				<br>
				<input type="Radio" value="<%=User.AUTHORIZATION_LEVEL_CORNELL_COMMUNITY%>" name="<%=AccessController.P_AUTHORZN%>" <%=authorzn == User.AUTHORIZATION_LEVEL_CORNELL_COMMUNITY ? "checked>" : ">"%>
					Cornell Community
				<br>
				<input type="Radio" value="<%=User.AUTHORIZATION_LEVEL_GUEST%>" name="<%=AccessController.P_AUTHORZN%>" <%=authorzn == User.AUTHORIZATION_LEVEL_GUEST ? "checked>" : ">"%>
					Guest
			</td>	
	</table>
