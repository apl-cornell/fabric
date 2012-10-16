// F. Permadi 2005.
// Print DOM tree
// (C) F. Permadi
// www.permadi.com
// This notice and a linkback to www.permadi.com must be provided for any use of this script.
//
// Warning: This script is provided "as is" and without warranty of any kind.
// Use at your own risk.


function runTest()
{
  var demoOutputWindow=window.open();
  var myOutputWindow=window.open();
  var myDocument=myOutputWindow.document.open("text/html", "replace");
  myDocument.writeln("<HTML ID='testhtml'><HEAD><TITLE>Html Title</TITLE></HEAD>");
  myDocument.writeln("<BODY><TABLE><TR><TD>Table cell 1</TD></TR><TR><TD>Table cell 2</TD></TR></TABLE></BODY>");
  myDocument.writeln("</HTML>");
  myDocument.close();
  printDOMTree(myDocument.getElementById("testhtml"), demoOutputWindow);
  myOutputWindow.close();
  domOutputWindow.focus();
}

////////////////////////////////////////////
// This function accepts a DOM element as parameter and prints
// out the descendant DOM structure of the element on a new window.
////////////////////////////////////////////
function printDOMTree(domElement, destinationWindow)
{
 
  var outputWindow=destinationWindow;
  if (!outputWindow)
    outputWindow=window.open();
  outputWindow.document.open("text/html", "replace");

  var myElement=document.getElementById(domElement);
  outputWindow.document.write("<HTML><HEAD><TITLE>Dom Tree</TITLE></HEAD><BODY>\n");
  outputWindow.document.write("<CODE>\n");
  traverseDOMTree(outputWindow.document, myElement, 1);
  outputWindow.document.write("</CODE>\n");
  outputWindow.document.write("</BODY></HTML>\n");
  
  // Must close document, otherwise Mozilla browsers keeps showing loading progress
  outputWindow.document.close();
}

////////////////////////////////////////////
// This function prints the DOM tree of an element.  
// This function is to be called recursively until the DOM tree is fully traversed.
// 
// Parameters:
// targetDocument is where the tree will be printed into
// currentElement is the element that we want to print
// depth is the depth of the current element (it should be 1 for the initial element)
////////////////////////////////////////////
function traverseDOMTree(targetDocument, currentElement, depth)
{
  if (currentElement)
  {
    var j;
    var tagName=currentElement.tagName;
 //   if (tagName)
      targetDocument.writeln("<SPAN STYLE='color:#FF0000;'>&lt;"+currentElement.tagName+"&gt;</SPAN>");
 //   else
//      targetDocument.writeln("[unknown tag]");

    var i=0;
    var currentElementChild=currentElement.childNodes[i];
    while (currentElementChild)
    {
      targetDocument.write("<BR>\n");
      for (j=0; j<depth; j++)
      {
        targetDocument.write("&nbsp;&nbsp;&#166");
      }								
      targetDocument.writeln("<BR>");
      for (j=0; j<depth; j++)
      {
        targetDocument.write("&nbsp;&nbsp;&#166");
      }					
      if (tagName)
        targetDocument.write("--");
      traverseDOMTree(targetDocument, currentElementChild, depth+1);
      i++;
      currentElementChild=currentElement.childNodes[i];
    }
    targetDocument.writeln("<BR>");
    for (j=0; j<depth-1; j++)
    {
      targetDocument.write("&nbsp;&nbsp;&#166");
    }			
    targetDocument.writeln("&nbsp;&nbsp;");
    if (tagName)
      targetDocument.writeln("&lt;/"+tagName+"&gt;");
    currentElement=0;
  }
}
