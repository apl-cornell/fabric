<%@ page language="java" import="org.w3c.dom.*, cms.model.*, cms.www.*, cms.www.xml.*"%>
<% Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root= (Element)displayData.getChildNodes().item(0);
   Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE); 
   Element category= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_CATEGORY);
   String ctgID= category.getAttribute(XMLBuilder.A_ID);
   String sortByColID = category.getAttribute(XMLBuilder.A_SORTBYID);
   int numOfCol = 0;%>
<script type="text/javascript">
  	//set up ordering for old and new columns
  	createOrdering('colOrdering');
</script><%
if (!ctgID.equals("0"))
{
 	NodeList columnList = CategoryXMLUtil.getColumnList(category);%>
<h3>Columns:</h3>
<%	if (columnList== null || columnList.getLength() == 0)
	{%>
	No existing columns
<%	}
	else
	{%>
	<table id="colswaptable" class="assignment_table" cellpadding="2" cellspacing="0" border="0" width="100%">
    	<tr>
   		<th nowrap>Sort By Col</th>
   		<th nowrap>Order</th>
   		<th nowrap>Column Name</th>
   		<th nowrap>Column Type</th>
   		<th nowrap onMouseover="ddrivetip('Hidden columns aren\'t shown to students but can be edited')" onMouseout="hideddrivetip()">Hidden</th>
   		<th nowrap onMouseover="ddrivetip('Removed columns aren\'t shown and can\'t be edited')" onMouseout="hideddrivetip()">Remove</th>
   	</tr>
<% 	numOfCol = columnList.getLength();
		for (int i=0; i<columnList.getLength(); i++)
		{
			Element col = (Element)columnList.item(i);
			String colID = col.getAttribute(XMLBuilder.A_ID);%>
		<tr>
<%			if (colID.equals(sortByColID))
			{%>
			<td align="center" nowrap>
				<input type="radio" value="<%=colID%>" name="<%= AccessController.P_COLSORTBY %>" checked>
			</td>
<%			}
			else
			{%>
			<td align="center" nowrap>
				<input type="radio" value="<%=colID%>" name="<%= AccessController.P_COLSORTBY %>">
			</td>
<%			}%>
			<td id="col_order_cell_<%= colID %>" align="center" nowrap>
				<%-- Evan's new reordering code --%>
				<script language="javascript">
					getElementById('col_order_cell_<%= colID %>').appendChild(registerIDForOrdering('colOrdering', '<%= AccessController.P_COLPOSITION + colID%>'));
				</script>
			</td>
			<td align="center" nowrap>
				<input type="text" size="40" name="<%= AccessController.P_COLNAME + colID %>" value="<%= col.getAttribute(XMLBuilder.A_NAME) %>">
			</td>
			<td align="center" nowrap>
				<%= col.getAttribute(XMLBuilder.A_TYPE) %>
			</td>
			<td align="center" nowrap>
				<input type="checkbox" onClick="getElementById('hidecol<%= colID %>').value = (getElementById('hidecol<%= colID %>').value == 'false') ? 'true' : 'false';" <%= (col.getAttribute(XMLBuilder.A_HIDDEN) == "true") ? "checked" : "" %>>
				<input type="hidden" id="hidecol<%= colID %>" name="<%= AccessController.P_COLHIDDEN + colID %>" value="<%= (col.getAttribute(XMLBuilder.A_HIDDEN) == "true") ? "true" : "false" %>">
			</td>
			<td align="center" nowrap>
				<input type="checkbox" name="<%= AccessController.P_REMOVECOL + colID %>" <%= (col.getAttribute(XMLBuilder.A_REMOVED) == "true") ? "checked" : "" %>>
			</td>

<%		}%>
   </table>
<%	}
  NodeList removedList = CategoryXMLUtil.getRemovedColumnList(category);
  int length = 0;
  if (removedList != null && (length=removedList.getLength())!= 0)
  {%>
<div class="replace">
    <span id="removedcolshead">
      <a href="#" onClick="show('removedcols', 'Removed columns &raquo;', '&laquo; Removed columns'); return false;">Removed columns &raquo;</a>
    </span>
    <table class="replacebody" id="removedcols" cellpadding="0" cellspacing="0">
      <tr>
        <th>Col Name</th>
        <th>Col Type</th>
        <th>Restore</th>
      </tr>
<%	for (int i= 0; i <length; i++)
  	{
  		Element col= (Element) removedList.item(i); %>
      <tr class="<%= i % 2 == 0 ? "row_even" : "row_odd" %>">
        <td><%= col.getAttribute(XMLBuilder.A_NAME) %></td>
        <td><%= col.getAttribute(XMLBuilder.A_TYPE) %></td>
        <td class="remove">
          <input type="checkbox" name="<%= AccessController.P_RESTORECOL + col.getAttribute(XMLBuilder.A_ID)%>">
        </td>
      </tr>
  <% } %>
    </table>
</div>
<% }  %><%
}%>

<div id="newcolumns" class="showhide">
	<h3>Add new column:</h3>
  	<script type="text/javascript">
    var index= 0;
    function makeRemoveLink() {
      var link= document.createElement('a');
      link.setAttribute('href', 'javascript:unregisterIDForOrdering(\'colOrdering\', \'<%= AccessController.P_NEWCOLPOSITION %>' + index + '\'); remove(\'sub' + index + '\');');
      var txt= document.createTextNode('Remove column');
      link.appendChild(txt);
      return link;
    }
  
    function makeCol() {
      var col = document.createElement('tr');
      col.id = 'sub' + index;
      
      var cell = document.createElement('td');
      cell.style.textAlign = 'center';
      var name = document.createElement('input');
      var frm = getElementById('category_form');
   	  name.setAttribute('type', 'radio');
      name.setAttribute('value', '<%= AccessController.P_PREFIX_NEW_CONTENT %>'+index);
      name.setAttribute('name', '<%= AccessController.P_COLSORTBY %>');
      cell.appendChild(name);
      col.appendChild(cell);
      
      cell = document.createElement('td');
      cell.style.textAlign = 'center';
      var ord = registerIDForOrdering('colOrdering', '<%= AccessController.P_NEWCOLPOSITION %>' + index); //returns a SELECT element
      cell.appendChild(ord);
      col.appendChild(cell);
      
      cell = document.createElement('td');
      cell.style.textAlign= 'center';
      name = document.createElement('input');
      name.setAttribute('type', 'text');
      name.setAttribute('size', '40');
      name.setAttribute('name', '<%= AccessController.P_NEWCOLNAME %>' + index);
      cell.appendChild(name);
      col.appendChild(cell);
      
      cell = document.createElement('td');
      cell.style.textAlign = 'center';
      var sel = document.createElement('select');
      sel.setAttribute('size', '3');
      sel.setAttribute('name', '<%= AccessController.P_NEWCOLTYPE %>' + index);
      var opt;
      var txt;
  
      opt = document.createElement('option');
      txt = document.createTextNode('<%= CategoryColumn.TEXT %>');
      opt.appendChild(txt);
      opt.selected = true;
      sel.appendChild(opt);
  
   	  opt = document.createElement('option');
      txt = document.createTextNode('<%= CategoryColumn.DATE %>');
      opt.appendChild(txt);
      sel.appendChild(opt);
  
      opt = document.createElement('option');
      txt = document.createTextNode('<%= CategoryColumn.FILE %>');
      opt.appendChild(txt);
      sel.appendChild(opt);
  
      opt = document.createElement('option');
      txt = document.createTextNode('<%= CategoryColumn.LINK %>');
      opt.appendChild(txt);
      sel.appendChild(opt);
      
      opt = document.createElement('option');
      txt = document.createTextNode('<%= CategoryColumn.NUMBER %>');
      opt.appendChild(txt);
      sel.appendChild(opt);
  
      cell.appendChild(sel);
      col.appendChild(cell);
     
     	cell = document.createElement('td');
     	cell.style.textAlign = 'center';
     	var hid = document.createElement('input');
     	hid.setAttribute('type', 'checkbox');
     	hid.setAttribute('name', '<%= AccessController.P_NEWCOLHIDDEN %>' + index);
     	cell.appendChild(hid);
     	col.appendChild(cell);
     
      cell = document.createElement('td');
      cell.style.width = '10%';
      cell.style.textAlign = 'center';
      txt = makeRemoveLink();
      cell.appendChild(txt);
      col.appendChild(cell);
      
      index++;
      return col;
    }
    
    function addSubCol() {
      var subtable = getElementById('subtable');
      var subtablebody = subtable.getElementsByTagName('tbody').item(0);
      subtablebody.appendChild(makeCol());
    }
  </script>
    <div id="addsub">
    	<a href="#" onClick="addSubCol(); return false;">(New Column)</a>
  	</div>
  	<script type="text/javascript">
    /* create header row for table of columns to be created */
    
    var sib= getElementById('addsub');
    var subtable= document.createElement('table');
    subtable.id= 'subtable';
    subtable.className= 'assignment_table';
    subtable.cellPadding= 0;
    subtable.cellSpacing= 0;
    var subtbody= document.createElement('tbody');
    var hrow= document.createElement('tr');
    
    var hcell = document.createElement('th');
    var txt = document.createTextNode('Sort By Col');
    hcell.appendChild(txt);
    hrow.appendChild(hcell);
    
    hcell = document.createElement('th');
    txt = document.createTextNode('Column Order');
    hcell.appendChild(txt);
    hrow.appendChild(hcell);
    
    hcell= document.createElement('th');
    txt= document.createTextNode('Column Name');
    hcell.appendChild(txt);
    hrow.appendChild(hcell);
    
    hcell= document.createElement('th');
    txt= document.createTextNode('Column Type');
    hcell.appendChild(txt);
    hrow.appendChild(hcell);

    hcell= document.createElement('th');
    txt= document.createTextNode('Hidden');
    hcell.appendChild(txt);
    hrow.appendChild(hcell);
    
    hcell = document.createElement('th');
    txt = document.createTextNode(' ');
    hcell.appendChild(txt);
    hrow.appendChild(hcell);
    
    subtbody.appendChild(hrow);
    subtable.appendChild(subtbody);
    sib.parentNode.insertBefore(subtable, sib);
  </script>
</div>
