/********************************************************************************
* header.js: included in every page; Javascript infrastructure code as well as
*     functions replacing basic browser-specific actions
********************************************************************************/

/**************************************************************
* miscellany, some specific to certain pages (categories, ...)
**************************************************************/
  
    // rollover button IDs
    var rollNum=1;
    
    function getElementById(id) {
      if (document.getElementById) { // DOM3 = IE5, NS6
        return document.getElementById(id);
      } else if (document.layers) { // Netscape 4
        eval("return document." + id);
      } else { // IE 4
        eval("return document.all." + id);
      }
    }
    
    // Changes a block-level element's display property
    function changeDisplay(divid, disp) {
      getElementById(divid).style.display = disp;
    }
    
    // add new function to collaspe or expand after checkbox
	function swapDisplay(divid) {
      if (getElementById(divid).style.display == 'none') {
        getElementById(divid).style.display = 'block';
      } else {
        getElementById(divid).style.display = 'none';
      }
    }
    
    // Replaces the link in a show/hide "header" to the given text and given onClick function
    function changeHead(headid, linktxt, linkonclick) {
      var head= getElementById(headid);
      var oldlink= head.getElementsByTagName('a').item(0);
      var newlink= document.createElement('a');
      var txt= document.createTextNode(linktxt);
      newlink.appendChild(txt);
      newlink.onclick= linkonclick;
      if (oldlink.href != null) newlink.href= oldlink.href;
      if (oldlink.className != null) newlink.className= oldlink.className;
      if (oldlink.id != null) newlink.id= oldlink.id;
      head.replaceChild(newlink, oldlink);
    }
    
    // Sets a block level element's display property to "none"
	  // And changes its "header" link text and href 
	  function hide(divid, curtxt, newtxt) {
	 	changeDisplay(divid, 'none');
	    var onclick= new Function('show(\'' + divid + '\', \'' + newtxt + '\', \'' + curtxt + '\'); return false;');
	    changeHead(divid + 'head', newtxt, onclick);
	    setCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + divid + '*' + 'hidden', '1', null);
	   	setCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + divid + '*' + 'showval', newtxt, null);
	   	setCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + divid + '*' + 'hideval', curtxt, null);
	  }
  
	  // Sets a block level element's display to default ('').
	  // And changes its "header" link text and href
	function show(divid, curtxt, newtxt) {
	    changeDisplay(divid, '');
	    var onclick= new Function('hide(\'' + divid + '\', \'' + newtxt + '\', \'' + curtxt + '\'); return false;');
	    changeHead(divid + 'head', newtxt, onclick);
	   	setCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + divid + '*' + 'hidden', '0', null);
	   	setCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + divid + '*' + 'showval', curtxt, null);
	   	setCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + divid + '*' + 'hideval', newtxt, null);
  	}
  	
  	
  	//Get the value of a GET variable
  	function getQueryVariable(variable){
  		var query = window.location.search.substring(1);
  		var vars = query.split("&");
  		for (var i=0;i<vars.length;i++) {
    		var pair = vars[i].split("=");
    		if (pair[0] == variable) {
      			return pair[1];
    		}
  		}
  		return null;
	}
  	
  	function hideByCookie()
  	{
  		var x = document.getElementsByTagName('div');
		for (var i=0; i<x.length; i++)
		{
			//if (x[i].className == 'showhide')
			{
				if (getCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + x[i].id + '*' + 'hidden') == '1')
				{
					var showval = getCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + x[i].id + '*' + 'showval');
					var hideval = getCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + x[i].id + '*' + 'hideval');
					hide(x[i].id, hideval, showval);
				}
				else if (getCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + x[i].id + '*' + 'hidden') == '0')
				{
					var showval = getCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + x[i].id + '*' + 'showval');
					var hideval = getCookie(getQueryVariable('action') + '*' + getQueryVariable('courseid') + '*' + x[i].id + '*' + 'hideval');
					show(x[i].id, showval, hideval);
				}
			}
		}
  	}
    
    // Sets a category's display property to "none"
    // And changes its "header" link text and href 
    function hideCategory(divid, curtxt, newtxt) {
   	  changeDisplay(divid, 'none');
      var onclick= new Function('showCategory(\'' + divid + '\', \'' + newtxt + '\', \'' + curtxt + '\'); return false;');
      changeHead(divid + 'head', newtxt, onclick);
      //hide the 'show less/more' link for this category
      changeDisplay(divid + 'expand', 'none');
    }
    
    // Sets a category's display property to 'block'.
    // And changes its "header" link text and href
    function showCategory(divid, curtxt, newtxt) {
      changeDisplay(divid, 'block');
      var onclick= new Function('hideCategory(\'' + divid + '\', \'' + newtxt + '\', \'' + curtxt + '\'); return false;');
      changeHead(divid + 'head', newtxt, onclick);
      //show the 'show less/more' link for this category
      changeDisplay(divid + 'expand', '');
    }
    
    //return the table that holds category contents to be displayed
    function getCtgTable(ctgID)
    {
    	return getElementById(ctgID).getElementsByTagName('table').item(0);
    }
    
    //show all content in a category
	  function expandCategory(ctgID,isAnnounce, shortNumShow)
	  {
	  		//see http://www.howtocreate.co.uk/tutorials/index.php?tut=0&part=24 for how to get at DOM info for tables
	  		var ctgTable = getElementById(ctgID + 'table');
	  		//set the display properties of all extra rows to default
	  		for (var i = shortNumShow; i < ctgTable.tBodies[0].rows.length; i++) {
	  			ctgTable.tBodies[0].rows[i].style.display = '';
	  	  }
	  		//change the function called by the link, as well as its text
	  		var callingItem = getElementById(ctgID + 'expand');
	  		//callingItem.setAttribute('onclick', callingItem.getAttribute('onclick').replace('expand', 'collapse'));
	  		callingItem.onclick = new Function('collapseCategory(\'' + ctgID + '\', ' + isAnnounce + ', ' + shortNumShow + '); return false;');
	  		if (isAnnounce) {
	  		  callingItem.firstChild.nodeValue = '(show 1 week)';
	  		} else {
	  		  callingItem.firstChild.nodeValue = '(show at most '+ shortNumShow+ ')';
	  		}
	  }
	  
	  //show limited content in a category
	  function collapseCategory(ctgID,isAnnounce,shortNumShow) {
        var ctgTable = getElementById(ctgID + 'table');
	  		//set the display properties of all extra rows to none
	  		for (var i = shortNumShow + 1; i < ctgTable.tBodies[0].rows.length; i++) {
	  			ctgTable.tBodies[0].rows[i].style.display = 'none';
	  		}
	  		//change the function called by the link, as well as its text
	  		var callingItem = getElementById(ctgID + 'expand');
        if (callingItem != null) {
          callingItem.onclick = new Function('expandCategory(\'' + ctgID + '\', ' + isAnnounce + ', ' + shortNumShow + '); return false;');
	  	  	callingItem.firstChild.nodeValue = '(show all)';
	  	  }
	  }
	  
	  function hideGrade()
	  {
	  	//var assignTable = getElementById('assignments').getElementsByTagName('table').item(0).tBodies[0];
	  	//var headerList = assignTable.rows[0].getElementsByTagName('th');
	  	//headerList.item(2).style.display = 'none';
	  	//headerList.item(3).style.display = 'none';
	  	//for (var i= 1; i< assignTable.rows.length; i++){
	  	//	var row = assignTable.rows[i];
	  	//	var cellList = row.getElementsByTagName('td');
	  	//	if (cellList.length > 3){
	  	//		cellList.item(2).style.display = 'none';
	  	//		cellList.item(3).style.display = 'none';
	  	//	}	  		
	  	//	else{
	  	//		var datecell = cellList.item(1);
		  //		var span = datecell.getAttribute('colspan')- 2;
	  	//		datecell.setAttribute('colspan', span);
	  	//	}
	  	//}
	  	var td = getElementById('score_hide0');
	  	var i = 0;
	  	while (td != null) {
	  	  td.style.display = 'none';
	  	  i = i + 1;
	  	  td = getElementById('score_hide' + i);
	  	}
	  	i = 1;
      td = getElementById('scoreshow' + i);
      var htd = getElementById('scorehide' + i);
      while (td != null) {
        td.style.display = 'none';
        htd.style.display = '';
        i = i + 1;
        td = getElementById('scoreshow' + i);
        htd = getElementById('scorehide' + i);
      }
      var totalScore = getElementById('totalScoreRow');
      if (totalScore != null) {
      	totalScore.style.display = 'none';
      }
	  	var callingItem = getElementById('gradeVis');
	  	callingItem.onclick = new Function('showGrade(); return false');
	  	callingItem.firstChild.nodeValue = '(Show Grades)';
	  }
	  
	  function showGrade(){
	  	//var assignTable = getElementById('assignments').getElementsByTagName('table').item(0).tBodies[0];
	  	//var headerList = assignTable.rows[0].getElementsByTagName('th');
	  	//headerList.item(2).style.display = '';
	  	//headerList.item(3).style.display = '';
	  	//for (var i= 1; i< assignTable.rows.length; i++){
	  	//	var row = assignTable.rows[i];
	  	//	var cellList = row.getElementsByTagName('td');
	  	//	if (cellList.length > 3){
	  	//		cellList.item(2).style.display = '';
	  	//		cellList.item(3).style.display = '';
	  	//	}else{
	  	//		var datecell = cellList.item(1);
	  	//		var span = datecell.getAttribute('colspan')- 0 + 2;
	  	//		datecell.setAttribute('colspan', span);
	  	//	}
	  	//}
	  	var td = getElementById('score_hide0');
      var i = 0;
      while (td != null) {
        td.style.display = '';
        i = i + 1;
        td = getElementById('score_hide' + i);
      }
      i = 1;
      td = getElementById('scoreshow' + i);
      var htd = getElementById('scorehide' + i);
      while (td != null) {
        td.style.display = '';
        htd.style.display = 'none';
        i = i + 1;
        td = getElementById('scoreshow' + i);
        htd = getElementById('scorehide' + i);
      }
      var totalScore = getElementById('totalScoreRow');
      if (totalScore != null) {
      	totalScore.style.display = '';
      }
	  	var callingItem = getElementById('gradeVis');
      callingItem.onclick = new Function('hideGrade(); return false');
	  	callingItem.firstChild.nodeValue = '(Hide Grades)';
	  }
	  
    function remove(id) {
      var thing= getElementById(id);
      thing.parentNode.removeChild(thing);
    }
    
    function setEdit(){
    	var parameter = getElementById('edit'); // AccessController.P_EDIT
    	parameter.value = 'true'; // AccessController.P_TRUE
		return false;
    }
    
    function setFileValue(fromID, toID) {
    	var from = getElementById(fromID);
    	var to = getElementById(toID);
    	to.value = from.value;
    	return true;
    }
    
    // writes code for rollover buttons
    function rollOver(rollURL, buttonID, aTag) {
	   if (aTag==undefined) {
	      aTag="";
	   }
	   var image_on=  "images/" + buttonID + "_on.gif",
	       image_off= "images/" + buttonID + ".gif";
	   var part01= " <a onmouseover=\"if (document.images) document.",
	       part02= "rollID"+rollNum,
	       part03= ".src=",
	       part04= "\'" + image_on + "\'",
	       part05= ";\" onmouseout=\"if (document.images) document.",
	       part06= "rollID"+rollNum,
	       part07= ".src=",
	       part08= "\'" + image_off + "\';\"",
	       part09= "  href=\"",
	       part10= rollURL,
	       part11="\"><img src=",
	       part12= "\"" + image_off + "\"",
	       part13= "name=\"",
	       part14= "rollID"+rollNum,
	       part15="\" border=0></a>";
	   var rolloverCode= part01 + part02 + part03 + part04 + part05 +
	                     part06 + part07 + part08 + aTag + part09 + part10 + 
	                     part11 + part12 + part13 + part14 + part15;
	   rollNum++;
	   document.write(rolloverCode);
   }
   
/* Old navbar initialization javascript
   startList = function() {
    if (document.all && document.getElementById) {
        navRoot = document.getElementById("dmenu");
        for (i=0; i < navRoot.childNodes.length; i++) {
            node = navRoot.childNodes[i];
            if (node.nodeName=="LI") {
                node.onmouseover=function() {
                    this.className+=" over";
                }
                node.onmouseout=function() {
                    this.className=this.className.replace(" over", "");
                }
            }
        }
    }
   }
*/
   
/***********************************************
* Cool DHTML tooltip script- � Dynamic Drive DHTML code library (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit Dynamic Drive at http://www.dynamicdrive.com/ for full source code
***********************************************/

var offsetxpoint=-60 //Customize x offset of tooltip
var offsetypoint=20 //Customize y offset of tooltip
var ie=document.all
var ns6=document.getElementById && !document.all
var enabletip=false

function ietruebody(){
    return (document.compatMode && document.compatMode!="BackCompat")? document.documentElement : document.body
}

function ddrivetip(thetext, thecolor, thewidth) {
	var tipobj= getElementById('dhtmltooltip');
    if (typeof thewidth!='undefined') {
          tipobj.style.width=thewidth+'px';
    }
    if (typeof thecolor!='undefined' && thecolor!='') {
          tipobj.style.backgroundColor=thecolor;
    }
    tipobj.innerHTML=thetext;
    enabletip=true;
    return false;
}

function positiontip(e){
    if (enabletip){
		var tipobj= getElementById('dhtmltooltip');
        var curX=(ie)? event.x+ietruebody().scrollLeft : e.pageX;
        var curY=(ie)? event.y+ietruebody().scrollTop : e.pageY;
        //Find out how close the mouse is to the corner of the window
        var rightedge=ie&&!window.opera? ietruebody().clientWidth-event.clientX-offsetxpoint : window.innerWidth-e.clientX-offsetxpoint-20
        var bottomedge=ie&&!window.opera? ietruebody().clientHeight-event.clientY-offsetypoint : window.innerHeight-e.clientY-offsetypoint-20
        
        var leftedge=(offsetxpoint<0)? offsetxpoint*(-1) : -1000
    
        //if the horizontal distance isn't enough to accomodate the width of the context menu
        if (rightedge<tipobj.offsetWidth)
             //move the horizontal position of the menu to the left by it's width
             tipobj.style.left=ie? ietruebody().scrollLeft+event.clientX-tipobj.offsetWidth+"px" : window.pageXOffset+e.clientX-tipobj.offsetWidth+"px"
        else if (curX<leftedge)
             tipobj.style.left="5px"
        else
            //position the horizontal position of the menu where the mouse is positioned
            tipobj.style.left=curX+offsetxpoint+"px"
        
        //same concept with the vertical position
        if (bottomedge<tipobj.offsetHeight)
            tipobj.style.top=ie? ietruebody().scrollTop+event.clientY-tipobj.offsetHeight-offsetypoint+"px" : window.pageYOffset+e.clientY-tipobj.offsetHeight-offsetypoint+"px"
        else
            tipobj.style.top=curY+offsetypoint+"px"
        
        tipobj.style.visibility="visible"
        tipobj.style.fontFamily="Verdana"
        tipobj.style.fontSize="10px"
    }
}

function hideddrivetip(){
    if (ns6||ie){
    	var tipobj= getElementById('dhtmltooltip');
        enabletip=false;
        tipobj.style.visibility="hidden";
        tipobj.style.left="-1000px";
        tipobj.style.backgroundColor='';
        tipobj.style.width='';
    }
}
   

   