/* Functions for the gradeassign page */

  /* Manages checkboxes for selecting groups */

  var pendings= null;
  var ungraded= null;
  /* BEGIN - THESE FUNCTIONS DON'T SEEM TO BE USED ANYWHERE, MAYBE REMOVE THEM?  -JON */
  
  /* Creates the pending checkbox array if it does not exist */
  function createPendings(aid) {
    if (pendings == null) {
      var gradestable= getElementById('grades_table_' + aid);
      pendings= new Array();
      var index= 0;
      var rows= gradestable.rows;
      for (i= 0; i != rows.length; i++) {
        if (rows[i].className == 'pending') {
          var checkbox= rows[i].getElementsByTagName('input').item(0);
          pendings[index++]= checkbox;
        }
      }
    }
  }
  
  /* Creates the ungraded checkbox array if it does not exist */
  function createUngraded(aid) {
    if (ungraded == null) {
      var gradestable= getElementById('grades_table_' + aid);
      ungraded= new Array();
      var index= 0;
      var rows= gradestable.rows;
      for (i= 0; i != rows.length; i++) {
        if (rows[i].className == 'ungraded') {
          var checkbox= rows[i].getElementsByTagName('input').item(0);
          ungraded[index++]= checkbox;
        }
      }
    }
  }

 /* END - THESE FUNCTIONS DON'T SEEM TO BE USED ANYWHERE, MAYBE REMOVE THEM?  -JON */

/*  ---------------------- CHECKBOX FUN BY BARMISH -----------------------*/
  function selectAll(aid) {
    var gradestable= getElementById('grades_table_' + aid);
    var boxes= gradestable.getElementsByTagName('input');
    for (i= 0; i != boxes.length; i++) {
      if (boxes[i].disabled==false) {
      	boxes[i].checked= true;
      }
    }
  }
  
  function selectNone(aid) {
    var gradestable= getElementById('grades_table_' + aid);
    var boxes= gradestable.getElementsByTagName('input');
    for (i= 0; i != boxes.length; i++) {
      boxes[i].checked= false;
    }
  }
  
  function selectPending(aid) {
  	selectNone(aid);
    var gradestable= getElementById('grades_table_' + aid);
    var rows= gradestable.rows;
    for (i= 0; i != rows.length; i++) {
       if (rows[i].className == 'pending') {
       		if (rows[i].getElementsByTagName('input').item(0).disabled==false) {
		         rows[i].getElementsByTagName('input').item(0).checked=true;
		    }
	   }    
    }
  }
  
  
  function selectUngraded(aid) {
   selectNone(aid);
    var gradestable= getElementById('grades_table_' + aid);
    var rows= gradestable.rows;
     for (i= 0; i != rows.length; i++) {
       if (rows[i].className == 'ungraded'){
       		if (rows[i].getElementsByTagName('input').item(0).disabled==false) {
		         rows[i].getElementsByTagName('input').item(0).checked=true;
		    }
	   }    
     }  
  }
  
  function selectRange(aid,a,b,max) {
   var first, second;
   if (a=="") first= 1;
   else      first= parseInt(a);
   if (b=="") second= max;
   else      second= parseInt(b);

   if (!(isNaN(first) || isNaN(second))) { 
		   if (first<1) first=1;
  		   if (second>max) second=max;
		  
		   if (second<first)
		   		selectNone(aid);
		   else {
			   selectNone(aid);
			    
			    var gradestable= getElementById('grades_table_' + aid);
			    var rows= gradestable.rows;
			     for (i= first+1; i != second+2; i++) {
			      	if (rows[i].getElementsByTagName('input').item(0).disabled==false) {
					         rows[i].getElementsByTagName('input').item(0).checked=true;
					}
			     }  
			     
		     
			     
		    }     
    } 
  }  
  
  
  
/*  ---------------------- END CHECKBOX FUN BY BARMISH -----------------------*/

 
  
  /* Show problem grades onclick handler */
  function showProblemGrades(aid) {
    // Set link to hide grades
    var probgrades= getElementById('subgrades');
    var link= probgrades.getElementsByTagName('a').item(1);
    var linktxt= link.firstChild;
    var txt= document.createTextNode('(Hide Problem Scores)');
    link.replaceChild(txt, linktxt);
    link.onclick= new Function('return hideProblemGrades(' + aid + ');');
    
    // Set colspans & sortables
    var gradeheader= getElementById('gradeheader');
    gradeheader.colSpan = 1 + numprobs;
    gradeheader.className= 'nosort';
    var gradespace= getElementById('gradespace');
   	gradespace.colSpan=1;
   	
    //change display property
    var gradestable= getElementById('grades_table_' + aid);
	for (var i = 0; i < gradestable.rows.length; i++)
		for (var j = 0; j < gradestable.rows[i].cells.length; j++)
			if (gradestable.rows[i].cells[j].className == 'subprob_score_cell')
				gradestable.rows[i].cells[j].style.display = '';
    // Redo sortability
    ts_makeSortable(gradestable);
    
    return false;
  }
  
  /* Hide problem grades onclick handler */
  function hideProblemGrades(aid) {
    // Set link to show grades
    var probgrades= getElementById('subgrades');
    var link= probgrades.getElementsByTagName('a').item(1);
    var linktxt= link.firstChild;
    var txt= document.createTextNode('(Show Problem Scores)');
    link.replaceChild(txt, linktxt);
    link.onclick= new Function('return showProblemGrades(' + aid + ');');
    
    // Set colspans & sortables
    var gradeheader= getElementById('gradeheader');
	gradeheader.colSpan= 1;
    gradeheader.className= 'scores';
    var gradespace= getElementById('gradespace');
    gradespace.colSpan=2;
    //change display property
    var gradestable= getElementById('grades_table_' + aid);
	for (var i = 0; i < gradestable.rows.length; i++)
		for (var j = 0; j < gradestable.rows[i].cells.length; j++)
			if (gradestable.rows[i].cells[j].className == 'subprob_score_cell')
				gradestable.rows[i].cells[j].style.display = 'none';
    
    // Redo sortability
    ts_makeSortable(gradestable);
    
    return false;
  }
  
  
  function checkSelection(graderSelectID, subproblemSelectID) {
    var pr = getElementById(subproblemSelectID);
    var gr = getElementById(graderSelectID);
    if ((pr != null && pr.selectedIndex == 0) || gr.selectedIndex == 0) {
      var str = 'Please select a ';
      if (pr != null && pr.selectedIndex == 0 && gr.selectedIndex == 0) {
        str = str + 'problem and grader';
      } else if (gr.selectedIndex == 0) {
        str = str + 'grader';
      } else {
        str = str + 'problem';
      }
      var sm = getElementById('select_error_box');
      if (sm == null) {
	      var dv = getElementById('assign_grader_div');
	      var p = document.createElement('p');
	      var sm = document.createElement('small');
	      sm.id = 'select_error_box';
	      p.style.color = 'red';
        var txt = document.createTextNode(str);
        sm.appendChild(txt);
        p.appendChild(sm);
        dv.appendChild(p);
      } else {
        sm.firstChild.nodeValue = str;
      }
      return false; 
    } else { 
      return true; 
    }
}

/*
select groups assigned to the given grader for the given problem(s),
if the element with subproblemSelectID exists; if it doesn't, look
for groups assigned to the given grader for any problem
*/
function selectAssignedTo(graderSelectID, subproblemSelectID, assignID, numProbs)
{
	var graderSelector = getElementById(graderSelectID);
	var grader = graderSelector.options[graderSelector.selectedIndex].value; //netID of selected grader
	if (grader =='<unassigned>') grader = '';
	var graderRegexp = new RegExp('\\s*' + grader + '\\s*');
	var subproblemSelector = getElementById(subproblemSelectID);
	var subprobs;
	if (subproblemSelector != null)
	{
		subprobs = new Array(subproblemSelector.options.length); //will map indices to bools
		for (var i = 0; i < subproblemSelector.options.length; i++) {
			subprobs[i] = subproblemSelector.options[i].selected;
		}
	}
	else
	{
		subprobs = new Array(1);
		subprobs[0] = true;
	}
	// Reset selection
	selectNone(assignID);
	var gradesTable = getElementById('grades_table_' + assignID);
	var groupRows = gradesTable.tBodies[0].rows;
	for (var i = 2; i < groupRows.length; i++) //first two rows are header stuff
	{
		var assignedToIndex = 6; //index of first cell with assigned-to info
		var numAssignedToCells;
		if (subproblemSelector == null) //there are no subproblems
			numAssignedToCells = 1;
		else numAssignedToCells = numProbs;
		for (var j = 0; j < numAssignedToCells; j++)
		{
			var cell = groupRows[i].cells[j + assignedToIndex];
			var divs = cell.getElementsByTagName('div');
			if (divs.length > 0) //there's an assigned grader
			{
				if (grader != '') //we're looking for an assigned grader
				{
					var assignedGrader = divs[0].firstChild.nodeValue; //should be a netID
					if (subprobs[j] && assignedGrader.match(graderRegexp))
					{
						//check the checkbox at the beginning of the row
						var checkbox = groupRows[i].cells[1].getElementsByTagName('input')[0];
						checkbox.checked = true;
					}
				}
			}
			else //no assigned grader
			{
				if (grader == '' && subprobs[j]) //we're looking for unassigned people
				{
					//check the checkbox at the beginning of the row
					var checkbox = groupRows[i].cells[1].getElementsByTagName('input')[0];
					checkbox.checked = true;
				}
			}
		}
	}
}
  
  // Kevin Barmish added July 14, 2005
  function showHideAssignedTo() {
  	var i = 0;
	var cell = getElementById('assigned_' + i);
	while (cell != null) {
  		if (cell.style.display=='none') {
  			cell.style.display='';
  		} else {
  			cell.style.display='none';
		}
		i = i + 1;
		cell = getElementById('assigned_' + i);
  	}
  	link= getElementById('show_assignedto');
  	var txt = link.firstChild;
  	if (txt.nodeValue == '(Show Assigned To)') {
  		txt.nodeValue = '(Hide Assigned To)';
  	} else {
  		txt.nodeValue = '(Show Assigned To)';
  	}
  }