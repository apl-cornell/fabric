<%@page language="java" import="cms.www.*"%>
<script type="text/javascript">
/**************************************************************************************************
* categoryscript.js: all necessary category-related Javascript
**************************************************************************************************/

/****************************************************************************************
* allow reordering of a nonconstant number of elements (in this case, category columns)
* on a single page by means of dropdown menus
*
* the user should call only createOrdering(), (un)registerIDForOrdering()
*
* the values can be submitted through a form; the lowest index held as a value is 1, not 0
****************************************************************************************/

//each element of orderings is a map from the ordering ID to another assoc array mapping element IDs to indices
var orderings = new Array(); //associative arrays: map user-defined IDs to index starting with 1
orderings.length = 0; //the length property of an associative array must be changed by the user

/*
create an ordering with the given ID;
the elements in this ordering are separate from others, so multiple groups of elements on the same page
can be ordered
*/
function createOrdering(orderingID)
{
	orderings[orderingID] = new Array();
	orderings[orderingID].length = 0;
}
/*
change a value in the array and perform DHTML changes necessary to display it

note a select node's option list has 0-based indexing
*/
function setIndex(orderingID, elementID, newIndex)
{
	orderings[orderingID][elementID] = newIndex + 1;
	getElementById(elementID).selectedIndex = newIndex;
}

/*
add the given ID to the list, after all current elements in order;
return a SELECT element with the current number of indices as options;
update the option lists of all other registered elements
*/
function registerIDForOrdering(orderingID, elementID)
{
	var menu = document.createElement('select');
	menu.setAttribute('id', elementID);
	menu.setAttribute('name', elementID); //in case it's being used as a form field
	for (var i = 0; i < orderings[orderingID].length + 1; i++)
	{
		var option = document.createElement('option');
		option.setAttribute('value', '' + (i + 1));
		option.appendChild(document.createTextNode('' + (i + 1)));
		menu.appendChild(option);
	}
	
	/* This line caused error in IE 7. Removing this line does not have any affect 
	   on Firefox, i.e I don't see the blank option, but fixed the problem in IE 
	   "Object doesn't support this action". So it is commented and can be deleted 
	   when all is OK. [Nov 2007, da10]
	delete menu.firstChild; //remove the blank option that gets stuck in by default
	*/
		
	menu.selectedIndex = orderings[orderingID].length;
	//update previously registered elements
	for (var id2 in orderings[orderingID])
	{
		var selector = getElementById(id2);
		var option = document.createElement('option');
		option.setAttribute('value', '' + (orderings[orderingID].length + 1));
		option.appendChild(document.createTextNode('' + (orderings[orderingID].length + 1)));
		selector.appendChild(option);
	}
	menu.setAttribute('onChange', 'changeIndex(\'' + orderingID + '\', this.id, this.selectedIndex);');
	orderings[orderingID][elementID] = orderings[orderingID].length + 1;
	orderings[orderingID].length++; //javascript doesn't autochange the length property of an associative array
	return menu;
}

/*
remove the given ID from the list and shuffle remaining IDs so their indices are consecutive and start at 1;
update the option lists of remaining elements
*/
function unregisterIDForOrdering(orderingID, elementID)
{
	//shuffle remaining entries and update their option lists
	for (var id2 in orderings[orderingID])
	{
		if (orderings[orderingID][id2] > orderings[orderingID][elementID])
			setIndex(orderingID, id2, orderings[orderingID][id2] - 2);
		var selector = getElementById(id2);
		delete selector.options[selector.options.length - 1];
	}
	delete orderings[orderingID][elementID];
	orderings[orderingID].length--; //javascript doesn't autochange the length property of an associative array
}

/*
change the index of the given ID and shuffle other IDs appropriately
*/
function changeIndex(orderingID, elementID, newIndex)
{
	var oldIndex = orderings[orderingID][elementID];
	orderings[orderingID][elementID] = newIndex + 1;
	if (orderings[orderingID][elementID] > oldIndex)
	{
		for (var id2 in orderings[orderingID])
		{
			if (id2 != elementID && orderings[orderingID][id2] > oldIndex && orderings[orderingID][id2] <= orderings[orderingID][elementID])
				setIndex(orderingID, id2, orderings[orderingID][id2] - 2);
		}
	}
	else if (orderings[orderingID][elementID] < oldIndex)
	{
		for (var id2 in orderings[orderingID])
		{
			if (id2 != elementID && orderings[orderingID][id2] >= orderings[orderingID][elementID] && orderings[orderingID][id2] < oldIndex)
				setIndex(orderingID, id2, orderings[orderingID][id2]);
		}
	}
}

/****************************************************************************************
* add table rows for adding contents and cells for adding new files to existing contents
****************************************************************************************/
    
    /***************************
    NOTE on the parameter name idString used in various functions below:
    this refers to either a string of format <row>_<col> (for newly added cells)
    or a string of format <contentID> (for existing cells);
    it's used to identify the HTML table cell to be changed
    ***************************/

	 var calArray = new Array();
    var numOfDates = 0;
    
   /*
   create a string to identify an object with given type, row and column indices
   
   row indices start at 0; see the variable nextNewRowID in addNedit.jsp, which gets changed in makeRow()
   */
   function makeReqParamString(descriptor, idString)
   {
   	return descriptor + '_' + idString;
   }
   /*
   create an identifying string for a specific file upload: it has a cell and an index
   within that cell
   
   the function name needs a 2 at the end because Javascript has no overloading
   */
   function makeReqParamString2(descriptor, idString, cellIndex)
   {
   	return descriptor + '_' + idString + '_' + cellIndex;
   }
    
  /*
  functions for adding to content in file-list form
  newId is an identifying string for the new element(s)
  
  newRow is boolean: are we adding a file to a content in an existing row or to a row that has
  yet to be created?
  */
	function addFile(cell, cellId, idString, newRow){
		/* add inputs for file URL and displayed name (vars file and filename, respectively) */
    	var fileTable = document.createElement('table');
    	fileTable.setAttribute('border', '0');
    	var fileTablebody = document.createElement('tbody');
    	var fnameRow = document.createElement('tr');
    	var fnameCell = document.createElement('td');
    	fnameCell.setAttribute('align', 'center');
    	var file = document.createElement('input');
		file.setAttribute('type', 'file');
		file.setAttribute('name', makeReqParamString2(newRow ? '<%= AccessController.P_NEW_CONTENT_FILE %>' : '<%= AccessController.P_CONTENT_FILE %>', idString, cell.getAttribute('numfiles')));
		file.setAttribute('value', '');
		file.setAttribute('size', '30');
		fnameCell.appendChild(file);
		fnameRow.appendChild(fnameCell);
		fileTablebody.appendChild(fnameRow);		
		var labelRow = document.createElement('tr');
		var labelCell = document.createElement('td');
		labelCell.setAttribute('align', 'left');
		labelCell.appendChild(document.createTextNode('Displayed name:'));
		labelCell.appendChild(document.createElement('br'));
		var filename = document.createElement('input');
		filename.setAttribute('type', 'text');
		filename.setAttribute('name', makeReqParamString2(newRow ? '<%= AccessController.P_NEW_CONTENT_FILELABEL %>' : '<%= AccessController.P_CONTENT_FILELABEL %>', idString, cell.getAttribute('numfiles')));
		filename.setAttribute('value', '');
		filename.setAttribute('size', '30');
		labelCell.appendChild(filename);
		labelRow.appendChild(labelCell);
		fileTablebody.appendChild(labelRow);		
		fileTable.appendChild(fileTablebody);
		
		var count = cell.getAttribute('numfiles') - 0; /* '- 0' makes it an integer */
		if (count == 0)
		{
			cell.appendChild(fileTable);
			var removeLink = document.createElement('a');
    		removeLink.setAttribute('href', '#');
    		removeLink.setAttribute('class', 'hide');
    		removeLink.onclick = new Function('return removeLastFile(\''+cellId+'\');');
    		removeLink.className = 'replace';
    		text = document.createTextNode('(Remove Last File)');
	    	removeLink.appendChild(text);
    		cell.appendChild(removeLink);
		}
		else
		{
			var removeLink = cell.lastChild;
			cell.insertBefore(fileTable, removeLink);
		}
		cell.setAttribute('numfiles', count+1+'');
    }
    
    /*
    newRow is boolean: is the row this content is in existing or to be created?
    */
    function moreFiles(cellId, idString, newRow){
  		addFile(getElementById(cellId), cellId, idString, newRow);
    	return false;
    }
    
    function removeFile(cell){
    	var child = cell.childNodes;
    	var childLength = child.length;
    	var count = cell.getAttribute('numfiles') - 1;
    	cell.setAttribute('numfiles', count+'');
    	if (count == 0)
    		cell.removeChild(cell.lastChild);
    	cell.removeChild(child[childLength-2]);
    }
    function removeLastFile(cellId){
    	var cell = getElementById(cellId);
    	removeFile(cell);
    	return false;
    }

    var coltypes = new Array();
    var colID = new Array();
    var existingRowIDs = new Array(); //so we can make sure no new rows on the JSP duplicate existing db IDs
    
   /* create a table cell with a link to remove a row */
    function makeRemoveLink(rowID) {
      var link= document.createElement('a');
      link.setAttribute('href', 'javascript:remove(\'sub' + rowID + '\')');
      var txt= document.createTextNode('Remove row');
      link.appendChild(txt);
      return link;
    }
     
   /*
   create a table cell for adding/editing contents in URL format
   
   add is boolean: are we editing or adding contents?
   */
    function makeURL(idString, add){
    	var cell = document.createElement('td');
      cell.style.textAlign = 'center';
      var table = document.createElement('table');
      table.setAttribute('border', '0');
      table.style.borderStyle = 'none';
      var row1 = document.createElement('tr');
      var urlCell1 = document.createElement('td');
      urlCell1.style.borderStyle = 'none';
      urlCell1.appendChild(document.createTextNode('URL:'));
      var urlCell2 = document.createElement('td');
      urlCell2.style.borderStyle = 'none';
		var urlTxt = document.createElement('input');
		urlTxt.setAttribute('type', 'text');
		urlTxt.setAttribute('size', '15');
		urlTxt.setAttribute('name', makeReqParamString(add ? '<%= AccessController.P_NEW_CONTENT_URLADDRESS %>' : '<%= AccessController.P_CONTENT_URLADDRESS %>', idString));
		urlCell2.appendChild(urlTxt);
		row1.appendChild(urlCell1);
		row1.appendChild(urlCell2);
		var row2 = document.createElement('tr');
		var labelCell1 = document.createElement('td');
		labelCell1.style.borderStyle = 'none';
		labelCell1.appendChild(document.createTextNode('Displayed text:'));
		var labelCell2 = document.createElement('td');
		labelCell2.style.borderStyle = 'none';
		var labelTxt = document.createElement('input');
		labelTxt.setAttribute('type', 'text');
		labelTxt.setAttribute('size', '15');
		labelTxt.setAttribute('name', makeReqParamString(add ? '<%= AccessController.P_NEW_CONTENT_URLLABEL %>' : '<%= AccessController.P_CONTENT_URLLABEL %>', idString));
		labelCell2.appendChild(labelTxt);
		row2.appendChild(labelCell1);
		row2.appendChild(labelCell2);
		table.appendChild(row1);
		table.appendChild(row2);
		cell.appendChild(table);
		return cell;
    }
    
   /*
   create a table cell for adding/editing contents in text format
   
   add is boolean: are we editing or adding contents?
   */
    function makeText(idString, add){
       	var cell = document.createElement('td');
      	cell.style.textAlign = 'center';
		var txt = document.createElement('textarea');
		txt.setAttribute('rows', '2');
		txt.setAttribute('cols', '15');
		txt.setAttribute('wrap', 'physical');
		txt.setAttribute('name', makeReqParamString(add ? '<%= AccessController.P_NEW_CONTENT_TEXT %>' : '<%= AccessController.P_CONTENT_TEXT %>', idString));
		cell.appendChild(txt);
		return cell;
    }
    
    /*
    create a table cell for adding/editing contents in integer format
    
    add is boolean: are we editing or adding contents?
    */
    function makeNumber(idString, add)
    {
    	var cell = document.createElement('td');
    	cell.style.textAlign = 'center';
    	var number = document.createElement('input');
    	number.setAttribute('type', 'text');
    	number.setAttribute('size', '6');
    	number.setAttribute('name', makeReqParamString(add ? '<%= AccessController.P_NEW_CONTENT_NUMBER %>' : '<%= AccessController.P_CONTENT_NUMBER %>', idString));
    	number.setAttribute('id', 'number_textbox_' + idString);
    	number.setAttribute('value', '');
    	registerIntegerFormatTextbox(number.id);
    	cell.appendChild(number);
    	return cell;
    }
    
   /* return today's date in string format */
    function today(){
    	var now = new Date();
    	var today ;
    	switch(now.getMonth()){
    		case 0: today = 'January '; break;
    		case 1: today = 'February '; break;
    		case 2: today = 'March '; break;
    		case 3: today = 'April '; break;
    		case 4: today = 'May '; break;
    		case 5: today = 'June '; break;
    		case 6: today = 'July '; break;
    		case 7: today = 'August '; break;
    		case 8: today = 'September '; break;
    		case 9: today = 'October '; break;
    		case 10: today = 'November '; break;
    		default: today = 'December '; break;
    	}
    	today += now.getDate()+', '+now.getFullYear();
    	return today;	
    }

	 function calPopup(dateIndex, idString){
    	var cal = calArray[dateIndex];
    	var txt = getElementById(makeReqParamString('datebox', idString));
    	cal.select(txt, makeReqParamString('datelink', idString), 'MMM d, yyyy');
    	return false;
    }
	    
   /*
   create a table cell for adding/editing contents in date format
   
   add is boolean: are we editing or adding contents?
   */
    function makeDate(idString, add){
    	var cell = document.createElement('td');
    	cell.style.textAlign = 'left';
      	cell.nowrap = true;
    	var div = document.createElement('div');
    	div.className = 'cal';
    	div.id = makeReqParamString('dateselect', idString);
    	cell.appendChild(div);
    	div = document.createElement('div');
    	div.className = 'dateblock';
    	calArray[numOfDates] = new CalendarPopup(makeReqParamString('dateselect', idString));
    	var txt = document.createElement('input');
    	txt.setAttribute('type', 'text');
    	txt.setAttribute('size', '17');
    	datetxtID = makeReqParamString('datebox', idString);
    	txt.setAttribute('id', datetxtID);
    	txt.setAttribute('name', makeReqParamString(add ? '<%= AccessController.P_NEW_CONTENT_DATE %>' : '<%= AccessController.P_CONTENT_DATE %>', idString));
    	txt.setAttribute('value', today());
    	div.appendChild(txt);
    	var dlink = document.createElement('a');
    	dlink.setAttribute('id', makeReqParamString('datelink', idString));
    	dlink.setAttribute('name', makeReqParamString('datelink', idString));
    	dlink.setAttribute('href', "#");
    	dlink.onclick = new Function('calPopup('+numOfDates+', \''+idString+'\'); return false;');
    	var image = document.createElement('img');
		image.setAttribute('class', 'calicon');
    	image.setAttribute('src', '../images/cal.gif');
    	image.setAttribute('alt', 'Select');
    	image.setAttribute('width', '16px');
    	image.setAttribute('height', '16px');
    	image.setAttribute('border', '0');
    	dlink.appendChild(image);
    	div.appendChild(dlink);
    	cell.appendChild(div);
    	numOfDates++;
    	registerDateFormatTextbox(datetxtID);
    	return cell;
    }
    
   /*
   create a table cell for adding contents in file-list format
   */
    function makeFiles(idString){
    	var cell = document.createElement('td');
      	cell.style.textAlign = 'left';
      	cell.setAttribute('nowrap', true);
      	cell.setAttribute('id', makeReqParamString('filecell', idString));
      	cell.setAttribute('numfiles', '0');
      	var newLink = document.createElement('a');
    	newLink.setAttribute('href', '#');
    	newLink.setAttribute('class', 'hide');
    	newLink.className = 'replace';
    	newLink.onclick = new Function('return moreFiles(\'' + cell.id + '\', \'' + idString + '\', true);');
    	var text = document.createTextNode('(Upload Another File)');
    	newLink.appendChild(text);
    	cell.appendChild(newLink);
		return cell;
    }
  
   /* create a table row for adding content in the formats given by the global variable coltypes */
    function makeRow() {
     if (nextNewRowID == maxExistingRowID + 1) {
		  var coltable = getElementById('ctnts');
		  var headerRow = coltable.getElementsByTagName('tr')[0];
	      var columns = headerRow.getElementsByTagName('th');
    	  for (var i=0; i<columns.length - 1; i++){
    	   	coltypes[i] = columns[i].getAttribute('a_coltype');
    	   	colID[i] = columns[i].getAttribute('a_colId');
   		  }
      }
      var row= document.createElement('tr');
      row.id= 'sub' + nextNewRowID;
      for (var i=0; i<coltypes.length; i++){
      	var type = coltypes[i];
      	var idString = nextNewRowID + '_' + colID[i];
      	if (type == 'text') row.appendChild(makeText(idString, true));
      	else if (type == 'url') row.appendChild(makeURL(idString, true));
	  		else if (type == 'date') row.appendChild(makeDate(idString, true));
	  		else if (type == 'number') row.appendChild(makeNumber(idString, true));
	  		else row.appendChild(makeFiles(idString));
	  }
      var cell= document.createElement('td');
      cell.style.width= '10%';
      cell.style.textAlign= 'center';
      var txt= makeRemoveLink(nextNewRowID);
      cell.appendChild(txt);
      row.appendChild(cell);
      nextNewRowID++;
      return row;
    }
    
    /*
    the main function in this javascript file: create a table row for adding a row of category content
    */
    function addSubRow() {
      var subtable= getElementById('subtable');
      var subtablebody= subtable.getElementsByTagName('tbody').item(0);
      subtablebody.appendChild(makeRow());
    }
</script>
