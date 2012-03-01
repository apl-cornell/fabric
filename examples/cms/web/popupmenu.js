/****************************************************************
* Pop-it menu- � Dynamic Drive (www.dynamicdrive.com)
* This notice MUST stay intact for legal use
* Visit http://www.dynamicdrive.com/ for full source code
****************************************************************/

var defaultMenuWidth="250px" //set default menu width.

var linkset=new Array()
/*- SPECIFY MENU SETS AND THEIR LINKS. FOLLOW SYNTAX LAID OUT
EXAMPLE:
linkset[0]='<a href="http://dynamicdrive.com">Dynamic Drive</a>'
linkset[0]+='<hr>' //Optional Separator
linkset[0]+='<a href="http://www.javascriptkit.com">JavaScript Kit</a>'
linkset[0]+='<a href="http://www.codingforums.com">Coding Forums</a>'
linkset[0]+='<a href="http://www.cssdrive.com">CSS Drive</a>'
linkset[0]+='<a href="http://freewarejava.com">Freewarejava</a>'
<a href="#" onMouseover="showmenu(event,linkset[0])" onMouseout="delayhidemenu()">Webmaster Links</a>
////No need to edit beyond here

-*/
var ie5=document.all && !window.opera;
var ns6=document.getElementById;

/*- if (ie5||ns6) { -*/
  document.write('<div id="popitmenu" onMouseover="clearhidemenu();" onMouseout="dynamichide(event)"></div>');
/*- } -*/
function iecompattest(){
  if (document.body && (document.body.clientWidth || document.body.scrollLeft)) {
    return document.body;
  }
  return document.documentElement;
}

function showmenu(e, which, optWidth){
	if (!document.all&&!document.getElementById) {
	  return;
	}
	clearhidemenu();
	var menuobj = getElementById("popitmenu");
	menuobj.innerHTML=which;
	menuobj.style.width=(typeof optWidth!="undefined")? optWidth : defaultMenuWidth;
	menuobj.contentwidth=menuobj.offsetWidth;
	menuobj.contentheight=menuobj.offsetHeight;
	menuobj.style.position = "absolute";
	var widthVal, heightVal, xScroll, yScroll, tmp;
  if (window && window.innerWidth) {
     widthVal = window.innerWidth;
  } else if (document.documentElement && document.documentElement.clientWidth) {
     widthVal = document.documentElement.clientWidth;
  } else {
     widthVal = document.body.clientWidth;
  }
  if (window && window.innerHeight) {
     heightVal = window.innerHeight;
  } else if (document.documentElement && document.documentElement.clientHeight) {
     heightVal = document.documentElement.clientHeight;
  } else {
     heightVal = document.body.clientHeight;
  }
  if (typeof(window.pageYOffset) == 'number') {
    yScroll = window.pageYOffset;
    xScroll = window.pageXOffset;
  } else if ( document.documentElement.scrollTop ) {
    yScroll = document.documentElement.scrollTop;
    xScroll = document.documentElement.scrollLeft;
  } else {
    yScroll = document.body.scrollTop;
    xScroll = document.body.scrollLeft;
  }
	var eventX= (typeof(event) != "undefined" && event.clientX) ? event.clientX : e.clientX;
	var eventY= (typeof(event) != "undefined" && event.clientY) ? event.clientY : e.clientY;
	/*- Adjust the event coords to push the menu under the mouse a bit more -*/
  eventX = eventX - 8;
  eventY = eventY - 8;
	var rightedge = widthVal - eventX;
	var bottomedge = heightVal - eventY;
	if (rightedge < menuobj.contentwidth) {
	  tmp = xScroll + eventX - menuobj.contentwidth;
	  menuobj.style.left = tmp.toString() + "px";
	} else {
	  tmp = xScroll + eventX;
	  menuobj.style.left = tmp.toString() + "px";
	}
	if (bottomedge < menuobj.contentheight) {
	  tmp = yScroll + eventY - menuobj.contentheight;
	  menuobj.style.top = tmp.toString() + "px";
	} else {
	  tmp = yScroll + eventY;
	  menuobj.style.top = tmp.toString() + "px";
	}
	menuobj.style.visibility="visible";
	return false;
}

function contains_ns6(a, b) {
  while (b.parentNode) {
    if ((b = b.parentNode) == a) {
      return true;
    }
  }
  return false;
}

function hidemenu(){
  var menuobj = getElementById("popitmenu")
  if (menuobj) {
  /*- if (window.menuobj) { -*/
    menuobj.style.visibility="hidden";
  }
  /*- } -*/
}

    /*- Set the document on click to hide popup menus
    if (document.onclick && hidemenu) {
      document.onclick=hidemenu;
    } else {
      alert(hidemenu ? 'couldn\'t get hidemenu' : 'couldn\'t get document.onclick');
    } -*/

function dynamichide(e){
  var menuobj = getElementById("popitmenu")
  if (ie5&&!menuobj.contains(e.toElement)) {
    hidemenu();
  } else if (ns6&&e.currentTarget!= e.relatedTarget&& !contains_ns6(e.currentTarget, e.relatedTarget)) {
    hidemenu();
  }
}

function delayhidemenu(){
  delayhide=setTimeout("hidemenu()",1500);
}

function clearhidemenu(){
  if (window.delayhide) {
    clearTimeout(delayhide);
  }
}
