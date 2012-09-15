/* 
 * Acquired from http://www.kryogenix.org/code/browser/sorttable/
 *
 * Modifications for CMS:
 *  - Small addition; can flag some columns as *unsortable*
 *  - No longer assumes all cells of the first row are the headers - now just looks
 *    for all "th" elements
 *  - Related to above point - had to make code for determining what to sort by\
 *    more generic
 *  - can flag a row as not to be sorted - to stay in its position
 *  - cells tagged with class "index" are filled with their row index (starting from 1)
 *  - can call "makeSortable" multiple times for changed table
 
 *  - the column that is sorted by default should have an id whose first char is ~
 */


addEvent(window, "load", sortables_init);

var SORT_COLUMN_INDEX;
var upDown; // for keeping empty cells on the bottom

function sortables_init() {
    // Find all tables with class sortable and make them sortable
    if (!document.getElementsByTagName) return;
    tbls = document.getElementsByTagName("table");
    for (ti=0;ti<tbls.length;ti++) {
        thisTbl = tbls[ti];
        if (((' '+thisTbl.className+' ').indexOf("sortable") != -1) && (thisTbl.id)) {
            //initTable(thisTbl.id);
            ts_makeSortable(thisTbl);
        }
    }
}

function ts_makeSortable(table) {
    var headers= table.getElementsByTagName('th');
    
    for (var i=0;i<headers.length;i++) {
        var cell = headers[i];
        styleAtt= cell.getAttribute('style');
   //     if (styleAtt.replace (/;/g, "").replace (/\s/g, "")=="display:none")
     //   	styleAtt=" ";
        var begintag= '<a href="#" id="sort_link_' + i + '" class="sortheader" style="' + ' ' +  '" onclick="ts_resortTable(this);return false;">';
        var endtag= '<span class="sortarrow"><img src=\"images/updownarrow.gif\" border=\"0\" style=\"margin-left: 10px\"></span></a>';
        // Strip sort code if it exists 
        if (cell.hasChildNodes()) {
          if (cell.firstChild.hasChildNodes() && cell.firstChild.className == 'sortheader') {
            cell.replaceChild(cell.firstChild.firstChild, cell.firstChild);
          }
        }
       	if (cell.className != 'nosort' && cell.id[0] != '~') {
       	  var txt = ts_getInnerText(cell);
          cell.innerHTML =txt+ begintag+endtag;
        }
       if (cell.id[0] == '~') {
       	  cell.innerHTML = ts_getInnerText(cell)+"<a href=\"#\" id=\"sort_link_" + i + "\" class=\"sortheader\" style=\"\" onclick=\"ts_resortTable(this);return false;\">"+"<span sortdir=\"down\" style=\"vertical-align: middle;\" class=\"sortarrow\"><img src=\"images/downarrow.gif\" border=\"0\" style=\"margin-left: 10px\"></span>";        
       	} 
         
    }
}

function ts_getInnerText(el) {
	if (typeof el == "string") return el;
	if (typeof el == "undefined") { return el };
	if (el.innerText) return el.innerText;	//Not needed but it is faster
	var str = "";
	
	var cs = el.childNodes;
	var l = cs.length;
	for (var i = 0; i < l; i++) {
		switch (cs[i].nodeType) {
			case 1: //ELEMENT_NODE
				str += ts_getInnerText(cs[i]);
				break;
			case 3:	//TEXT_NODE
				str += cs[i].nodeValue;
				break;
		}
	}

	return str;
}

function ts_resortTable(lnk) {

    // get the span
    var span;
    for (var ci=0;ci<lnk.childNodes.length;ci++) {
        if (lnk.childNodes[ci].tagName && lnk.childNodes[ci].tagName.toLowerCase() == 'span') span = lnk.childNodes[ci];
    }
    var spantext = ts_getInnerText(span);
    var td = lnk.parentNode;
    
    // Find out the column to sort by
    var tr= td.parentNode;
    var column= -1;
    for (var i= 0; i <= td.cellIndex; i++) {
      if (tr.cells[i].colSpan) {
        if (tr.cells[i].id == "gradeheader")
          column += numprobs + 1;
        else column+= tr.cells[i].colSpan;
      } else {
        column++;
      }
    }
    
    var table = getParent(td,'TABLE');
    
    if (table.rows.length <= 1) return;
    
    // Go down the column until we find the first td
    var tdrowindex= -1;
    for (var i= 0; i != table.rows.length && tdrowindex == -1; i++) {
      if (table.rows[i].cells[column] != null && table.rows[i].cells[column].tagName == 'TD')
        tdrowindex= i;
    }
    if (i == table.rows.length) return;
    
    // Modified, Kevin Barmish, work out sort type based on class attribute
    var sortType= getParent(lnk,"TH").className;
    switch (sortType) {
    	case "text":		sortfn= ts_sort_caseinsensitive;	break;
    	case "scores":		sortfn= ts_sort_scores;				break;
    	case "subprob_score_cell": sortfn= ts_sort_scores;		break;
    	case "date":		sortfn= ts_sort_date;				break;
    	case "numeric":		sortfn= ts_sort_numeric;			break;
    	case "currency":	sortfn=	ts_sort_currency;			break;
    	case "netID":       sortfn= ts_sort_netID;              break;
    	default:			sortfn= ts_sort_caseinsensitive;
    }
    

    SORT_COLUMN_INDEX = column;
    var firstRow = new Array();
    var newRows = new Array();
    for (i=0;i<table.rows[0].length;i++) { firstRow[i] = table.rows[0][i]; }
    var i= 0;
    for (j=1;j<table.rows.length;j++) {
      if (table.rows[j].className != "nosort") {
        newRows[i] = table.rows[j];
        i++;
      }
    }
	// Check direction of sort & set cookies
	setCookie(table.id + '_coln', lnk.id);
	span.setAttribute('style','vertical-align:middle;');
    if (span.getAttribute("sortdir") == 'down') {
    	setCookie(table.id + '_sortdir', 'down')
        upDown=-1; // for score-sort
        newRows.sort(sortfn);
        ARROW = '&nbsp;&nbsp;';
        	newRows.reverse();
        span.setAttribute('sortdir','up');
    } else {
    	setCookie(table.id + '_sortdir', 'up')
    	upDown=1; // for score-sort
        newRows.sort(sortfn);
        ARROW = '&nbsp;&nbsp;';
        span.setAttribute('sortdir','down');
    }

    
    // We appendChild rows that already exist to the tbody, so it moves them rather than creating new ones
    // don't do sortbottom rows
    for (i=0;i<newRows.length;i++) { if (!newRows[i].className || (newRows[i].className && (newRows[i].className.indexOf('sortbottom') == -1))) table.tBodies[0].appendChild(newRows[i]);}
    // do sortbottom rows only
    for (i=0;i<newRows.length;i++) { if (newRows[i].className && (newRows[i].className.indexOf('sortbottom') != -1)) table.tBodies[0].appendChild(newRows[i]);}
    
    // Delete any other arrows there may be showing
    var allspans = document.getElementsByTagName("span");
    for (var ci=0;ci<allspans.length;ci++) {
        if (allspans[ci].className == 'sortarrow') {
            if (getParent(allspans[ci],"table") == getParent(lnk,"table")) { // in the same table as us?
                allspans[ci].innerHTML = '&nbsp;&nbsp;<img src=\"images/updownarrow.gif\" border=\"0\">';
            }
        }
    }
        
      if (span.getAttribute("sortdir") == 'down') {
		span.innerHTML = ARROW+'<img src="images/downarrow.gif" BORDER="0px">';
        
    } else {    
        
        span.innerHTML = ARROW+'<img src="images/uparrow.gif"  BORDER="0px">';
    }
    
    // Renumber rows if necessary
    var first= -1;
    for (var i= 0; i != table.rows.length; i++) {
      var cells= table.rows[i].getElementsByTagName('td');
      for (var j= 0; j != cells.length; j++) {
        if (cells[j].className == 'index') {
          if (first == -1)
            first= i;
          while (cells[j].hasChildNodes()) {
            cells[j].removeChild(cells[j].lastChild);
          }
          var newtxt= document.createTextNode(i - first + 1);
          cells[j].appendChild(newtxt);
        }
      }
    }
}




function getParent(el, pTagName) {
	if (el == null) return null;
	else if (el.nodeType == 1 && el.tagName.toLowerCase() == pTagName.toLowerCase())	// Gecko bug, supposed to be uppercase
		return el;
	else
		return getParent(el.parentNode, pTagName);
}
function ts_sort_date(a,b) {
    // y2k notes: two digit years less than 50 are treated as 20XX, greater than 50 are treated as 19XX
    var aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]);
    var bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]);
    if (aa.length == 10) {
        dt1 = aa.substr(6,4)+aa.substr(3,2)+aa.substr(0,2);
    } else {
        yr = aa.substr(6,2);
        if (parseInt(yr) < 50) { yr = '20'+yr; } else { yr = '19'+yr; }
        dt1 = yr+aa.substr(3,2)+aa.substr(0,2);
    }
    if (bb.length == 10) {
        dt2 = bb.substr(6,4)+bb.substr(3,2)+bb.substr(0,2);
    } else {
        yr = bb.substr(6,2);
        if (parseInt(yr) < 50) { yr = '20'+yr; } else { yr = '19'+yr; }
        dt2 = yr+bb.substr(3,2)+bb.substr(0,2);
    }
    if (dt1==dt2) return 0;
    if (dt1<dt2) return -1;
    return 1;
}

function ts_sort_currency(a,b) { 
    var aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]).replace(/[^0-9.]/g,'');
    var bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]).replace(/[^0-9.]/g,'');
    return parseFloat(aa) - parseFloat(bb);
}

function ts_sort_numeric(a,b) { 
    var aa = parseFloat(ts_getInnerText(a.cells[SORT_COLUMN_INDEX]));
    if (isNaN(aa)) aa = 0;
    var bb = parseFloat(ts_getInnerText(b.cells[SORT_COLUMN_INDEX])); 
    if (isNaN(bb)) bb = 0;
    return aa-bb;
}


function ts_sort_caseinsensitive(a,b) {  
    var aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]).toLowerCase().replace (/^\s+/, "");
    var bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]).toLowerCase().replace (/^\s+/, "");
    
    var r;
    
    if ((aa.charAt(0) == ',') && (bb.charAt(0) != ',')) r= 1*upDown;
    else if ((aa.charAt(0)  != ',') && (bb.charAt(0)  == ',')) r= -1*upDown;
    else if (aa == bb) r= 0;
    else if (aa < bb) r= -1;
    else r= 1;
       
    return r;
}

function ts_sort_default(a,b) {
    var aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]);
    var bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]);
    if (aa==bb) return 0;
    if (aa<bb) return -1;
    return 1;
}
// ADDED: Kevin Barmish
// this function sorts scores but *'s are always at the end

function ts_sort_scores(a,b) {


    var aa = ts_getInnerText(a.cells[SORT_COLUMN_INDEX]).toLowerCase().replace (/^\s+/, "");
    var bb = ts_getInnerText(b.cells[SORT_COLUMN_INDEX]).toLowerCase().replace (/^\s+/, "");
    
	
    var aNum, bNum, returnValue;
    switch (aa.charAt(0)) {
    	case '0': case '1': case '2': case '3': case '4': 
    	case '5': case '6': case '7': case '8': case '9': 
    	case '-':
    		aNum=true; break;
    	default: aNum=false;
    }
       switch (bb.charAt(0)) {
    	case '0': case '1': case '2': case '3': case '4': 
    	case '5': case '6': case '7': case '8': case '9': 
    	case '-':
    		bNum=true; break;
    	default: bNum=false;
    }
    	
	    if (aNum && !bNum) 			returnValue= -1*upDown;
		else if (!aNum && bNum)		returnValue= 1*upDown;
	    else if (aNum && bNum) 		returnValue= ts_sort_numerictext(a,b);
	    // none begin with number character
	   	else if (aa=='' && bb!='')	returnValue=1;
	    else if (aa!='' && bb=='')	returnValue=-1;
	    else returnValue= ts_sort_caseinsensitive(a,b)*upDown;
		
	    return returnValue;    
}

function ts_sort_netID(a,b) {  
    return 1; // not implemented
}


function ts_sort_numerictext(a,b) { 
    var aa = parseFloat(ts_getInnerText(a.cells[SORT_COLUMN_INDEX]));
    if (isNaN(aa))  aa = 0;
    var bb = parseFloat(ts_getInnerText(b.cells[SORT_COLUMN_INDEX])); 
    if (isNaN(bb)) bb = 0;
    var returnVal= aa-bb;
	if (returnVal==0) return ts_sort_caseinsensitive(a,b)*upDown;
	return returnVal;
}






function addEvent(elm, evType, fn, useCapture)
// addEvent and removeEvent
// cross-browser event handling for IE5+,  NS6 and Mozilla
// By Scott Andrew
{
  if (elm.addEventListener){
    elm.addEventListener(evType, fn, useCapture);
    return true;
  } else if (elm.attachEvent){
    var r = elm.attachEvent("on"+evType, fn);
    return r;
  } else {
    alert("Handler could not be removed");
  }
} 
