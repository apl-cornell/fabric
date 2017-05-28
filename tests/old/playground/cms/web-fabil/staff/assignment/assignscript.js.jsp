<%@page language="java" import="org.w3c.dom.*, cms.www.*, cms.www.xml.*,cms.model.*;" 
%><% Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root= (Element)displayData.getChildNodes().item(0); 
   Element assignment= (Element) root.getElementsByTagName(XMLBuilder.TAG_ASSIGNMENT).item(0);
   Element filetypes= (Element) root.getElementsByTagName(XMLBuilder.TAG_FILETYPES).item(0);
   NodeList types= filetypes.getChildNodes(); 
%>/*
 * Javascript functions for the assignment admin page
 */
 
/*
 * error and warning lists to be displayed by checkData(); can be edited by subroutines, 
 * and are reset each time through checkData()
 *
 * errors will keep the form from being submitted; warnings will not
 */
var errors, warnings;
var e, w; //error, warning counts

/*
 * Check the validity of the form data sent by the assignment admin page
 */
 


/* 
 * Sorting function used in checkData().
 * Assumes input is a length-2 array in which all elements are numbers.
 * Compares based on first column.
 */
function compareBy1stCol(a, b) {
  if (a[0] < b[0]) return -1;
  if (a[0] == b[0]) return 0;
  return 1;
}

/* 
 * array is an n-by-2 array.  Searches between high and low for 
 * entry in which the first element is x.  When found, sets the 
 * second element to 0.
 */
function scoreSearch(array, x, high, low) {
  var mid= Math.floor((high + low)/2);
  if (x > array[mid][0]) scoreSearch(array, x, mid+1, high);
  else if (x < array[mid][0]) scoreSearch(array, x, low, mid);
  else array[mid][1]= 0;
}

/* Checks the validity of data before submission */
function checkData()
{
	//reset error storage
	errors = new Array();
	e = 0;
	warnings = new Array();
	w = 0;
  // process inputs
  var inputs= document.getElementsByTagName('input');
  var notwhitespace= /\S/;
  var reqfilename= /'<%= AccessController.P_REQFILENAME %>'/;
  var reqfiletype= /'<%= AccessController.P_REQFILETYPE %>'/;
  var status= null;
  var lateallowed= null;
  var regrades= null;
  var duedate = null;
  var duetime = null;
  var dueampm = (getElementById('due_ampm').options.selectedIndex == 0) ? 'am' : 'pm';
  var latedate = null;
  var latetime = null;
  var lateampm = (getElementById('late_ampm').options.selectedIndex == 0) ? 'am' : 'pm';
  var regradedate = null;
  var regradetime = null;
  var regradeampm = (getElementById('regrade_ampm').options.selectedIndex == 0) ? 'am' : 'pm';
  var groups= null;
  var groupsmax;
  var groupsmin;
  var groupsbyta= null;
  var maxscore;
  var probscores= new Array();
  var probscores_i= 0;
  var remprobs= new Array();
  var remprobs_i= 0;
  var probtotal= 0;
  for (i= 0; i != inputs.length; i++)
  {
    var input= inputs[i];
    var name= input.name;
    var value= input.value;
    if (name == null) ;
    else if (name == '<%= AccessController.P_NAME %>') {
      if (value == null || value == "" || !notwhitespace.test(value))
        errors[e++]= "Name must be nonempty.";
    } else if (name == '<%= AccessController.P_NAMESHORT %>') {
      if (value == null || value == "" || !notwhitespace.test(value))
        errors[e++]= "Short Name must be nonempty.";
    } else if (name == '<%= AccessController.P_STATUS %>') {
      if (input.checked) {
        if (status != null)
          errors[e++]= "More than one status field selected.";
        else status= value;
      } 
    } else if (name == '<%= AccessController.P_DUEDATE %>') {
    	duedate = parseDate(value);
    	if (duedate == null) errors[e++] = "Due date is not in proper format.";
    	//in case the date is in a format parsable by our Javascript but not our Java code
    	else getElementById('datebox').value = formatDate(duedate, 'MMM dd, yyyy');
    }
    else if (name == '<%= AccessController.P_DUETIME %>')
    {
    	duetime = value;
      if (duetime == null || parseTime(duetime, 'Due time') == null)
      	errors[e++]= "Due time is not in proper format.";
    }
    else if (name == '<%= AccessController.P_GRACEPERIOD %>') {
      var period= parseInt(value);
      if (isNaN(period)) errors[e++]= "Grace Period is not in proper format.";
    } else if (name == '<%= AccessController.P_LATEALLOWED %>') {
      if (input.checked) {
        if (lateallowed != null)
          errors[e++]= "More than one \"Late Allowed\" field selected.";
        else lateallowed= value;
      }
    } else if (name == '<%= AccessController.P_LATEDATE %>') {
      latedate= parseDate(value);
    } else if (name == '<%= AccessController.P_LATETIME %>') {
      latetime= value;
    } else if (name == '<%= AccessController.P_REGRADES %>') {
      if (input.checked) {
        if (regrades != null)
          errors[e++]= "More than one regrade option field selected.";
        else regrades= value;
      }  
    } else if (name == '<%= AccessController.P_REGRADEDATE %>') {
      regradedate= parseDate(value);
    } else if (name == '<%= AccessController.P_REGRADETIME %>') {
      regradetime= value;
    }
    else if (name == '<%= AccessController.P_GROUPS %>') {
      if (input.checked)
        if (groups != null)
          errors[e++]= "More that one group size option selected.";
        else groups= value;
    } else if (name == '<%= AccessController.P_GROUPSMAX %>') {
      groupsmax= parseInt(value);
    } else if (name == '<%= AccessController.P_GROUPSMIN %>') {
      groupsmin= parseInt(value);
    } else if (name == '<%= AccessController.P_GROUPSBYTA %>') {
      if (input.checked)
        if (groupsbyta != null)
          errors[e++]= "More than one \"TA Group Assignment\" option selected.";
        else groupsbyta= value;
    } else if (name == '<%= AccessController.P_TOTALSCORE %>') {
      if (isNaN(parseFloat(value)))
        errors[e++]= "Max score is not a number.";
      else maxscore= parseFloat(value);
    } else if (name == '<%= AccessController.P_WEIGHT %>') {
      if (isNaN(parseFloat(value)))
        errors[e++]= "Weight is not a number.";
    } else if (name.match(/^<%= AccessController.P_REQSIZE %>\d+$/) != null ||
               name.match(/^<%= AccessController.P_NEWREQSIZE %>\d+$/) != null) {
      if (isNaN(parseFloat(value)))
        errors[e++]= "A required submission's last size is not a number.";
    } else if (name.match(/^<%= AccessController.P_SUBPROBSCORE %>\d+$/) != null) {
      if (isNaN(parseFloat(value))) {
        errors[e++]= "A problem's total score is not a number.";
      } else {
        var id= name.split('<%= AccessController.P_SUBPROBSCORE %>')[1];
        probscores[probscores_i++]= new Array(parseInt(id), parseFloat(value));
      }
    } else if (name.match(/^<%= AccessController.P_NEWSUBPROBSCORE %>\d+$/) != null) {
      if (isNaN(parseInt(value))) {
        errors[e++]= "A problem's total score is not a number.";
      } else {
        var id= name.split('<%= AccessController.P_NEWSUBPROBSCORE %>')[1];
        probscores[probscores_i++]= new Array(parseInt(id), parseFloat(value));
      }
    } else if (name.match(/^<%= AccessController.P_REMOVESUBPROB %>\d+$/)) {
      if (input.checked) {
        var id= name.split('<%= AccessController.P_REMOVESUBPROB %>')[1];
        remprobs[remprobs_i++]= id;
      }
    }
  }
  
  if (groups == '<%= AccessController.ONE %>') {
    if (isNaN(groupsmin))
      errors[e++]= "Min group size is not in the proper format";
    else if (isNaN(groupsmax))
      errors[e++]= "Max group size is not in the proper format";
    else if (groupsmax < groupsmin)
      errors[e++]= "Min group size must be less than max group size.";
  }
  
  /*
   * check relative order of dates/times: now < duedate < latedate, regradedate
   *
   * note we've already made sure the various dates and times are correctly formatted
   */
/*  This warning is not really necessary
	if (dateIsBeforeDateObj(duedate, duetime + ' ' + dueampm, new Date(), 'Due time'))
  	warnings[w++] = "Due date/time is in the past.";*/
  if (lateallowed == '<%= AccessController.ONE %>')
  {
  	if (latedate == null) //parseDate() couldn't parse
  		errors[e++] = "Late submission date is not in proper format.";
  	//in case the date is in a format parsable by our Javascript but not our Java code
  	else getElementById('latebox').value = formatDate(latedate, 'MMM dd, yyyy');
  	if (latetime == null || parseTime(latetime, 'Late submission time') == null)
  		errors[e++]= "Late submission time is not in proper format.";
  	if (dateIsBeforeFormInput(latedate, latetime + ' ' + lateampm, 'Late submission date', duedate, duetime + ' ' + dueampm, 'Due time'))
  		errors[e++] = "Late submission date is before due date.";
  }
  if (regrades == '<%= AccessController.ONE %>')
  {
  	if (regradedate == null)
  		errors[e++] = "Regrade submission date is not in proper format.";
  	//in case the date is in a format parsable by our Javascript but not our Java code
  	else getElementById('regradebox').value = formatDate(regradedate, 'MMM dd, yyyy');
  	if (regradetime == null || parseTime(regradetime, 'Regrade submission time') == null)
  		errors[e++]= "Regrade submission time is not in proper format.";
  	if (dateIsBeforeFormInput(regradedate, regradetime + ' ' + regradeampm, 'Regrade submission date', duedate, duetime + ' ' + dueampm, 'Due time'))
  		errors[e++] = "Regrade submission date is before due date.";
  }
  	
  /* make sure subproblem scores total to assignment max score */
  if (probscores_i > remprobs_i) {
    probscores.sort(compareBy1stCol);
    for (i= 0; i != remprobs_i; i++) {
      scoreSearch(probscores, remprobs[i], 0, probscores_i);
    }
    for (i= 0; i != probscores_i; i++) {
      probtotal+= probscores[i][1];
    }
    if (probtotal != maxscore) {
      errors[e++]= "Total subproblem score (" + probtotal + 
                   ") is not equal to assignment total score (" + maxscore + ")."
    }
  }
  var message = "";
  if (warnings.length > 0)
  {
  	message += "Questionable input (won't stop form submission):\n";
  	for (i = 0; i < warnings.length; i++)
  		message += (i + 1) + ". " + warnings[i] + "\n";
  }
  if (errors.length > 0)
  {
    message += "Invalid input (must be fixed now):\n";
    for (i = 0; i < errors.length; i++)
      message += (i + 1) + ". " + errors[i] + "\n";
  }
  if (message.length > 0) alert(message);
  return errors.length == 0;
}
      
/* functions for generating new rows in tables */

/* Problems */
    
var probindex= 0;
    
function makeProbRemoveLink() {
  var link= document.createElement('a');
  link.onclick= new Function('remove(\'prob' + probindex + '\'); return false;');
  link.setAttribute('href', '#');
  var txt= document.createTextNode('(Remove row)');
  link.appendChild(txt);
  return link;
}

function makeProbRow() {
  var row= document.createElement('tr');
  row.id= 'prob' + probindex;
  var cell= document.createElement('td');
  cell.style.textAlign= 'center';
  var input= document.createElement('input');
  input.size= 30;
  input.name= "<%= AccessController.P_NEWSUBPROBNAME %>" + probindex;
  cell.appendChild(input);
  row.appendChild(cell);
  cell= document.createElement('td');
  cell.style.textAlign= 'center';
  input= document.createElement('input');
  input.size= 3;
  input.name= "<%= AccessController.P_NEWSUBPROBSCORE %>" + probindex;
  input.value= '';
  cell.appendChild(input);
  row.appendChild(cell);
  cell= document.createElement('td');
  cell.appendChild(makeProbRemoveLink());
  cell.style.textAlign= 'center';
  cell.style.whiteSpace= 'nowrap';
  row.appendChild(cell);
  probindex++;
  return row;
}
  
function addProbRow() {
  var probtable= getElementById('probtable');
  var probtablebody= probtable.getElementsByTagName('tbody').item(0);
  probtablebody.appendChild(makeProbRow());
}

/* Required Submissions */

var subindex= 0;
    
function makeRemoveLink() {
  var link= document.createElement('a');
  link.onclick= new Function('remove(\'sub' + subindex + '\'); updateTotalScore(); return false;');
  link.setAttribute('href', '#');
  var txt= document.createTextNode('(Remove row)');
  link.appendChild(txt);
  return link;
}

function makeSubRow() {
  var row= document.createElement('tr');
  row.id= 'sub' + subindex;
  var cell= document.createElement('td');
  cell.style.textAlign= 'center';
  var name= document.createElement('input');
  name.setAttribute('type', 'text');
  name.setAttribute('size', '40');
  name.setAttribute('name', '<%= AccessController.P_NEWREQFILENAME %>' + subindex);
  cell.appendChild(name);
  var p = document.createElement('p');
  var sm = document.createElement('small');
  var txt = document.createTextNode('Do not include file type, as it is automatically appended.');
  sm.appendChild(txt);
  p.appendChild(sm);
  cell.appendChild(p);
  row.appendChild(cell);
  cell= document.createElement('td');
  cell.style.textAlign= 'center';
  cell.style.width= '10%';
  var sel= document.createElement('select');
  sel.setAttribute('multiple', 'true');
  sel.setAttribute('size', '3');
  sel.setAttribute('name', '<%= AccessController.P_NEWREQFILETYPE %>' + subindex);
  sel.style.width= '100%';
  var opt;
<% 
  for (int i= 0; i < types.getLength(); i++) { 
%>opt= document.createElement('option');
  txt= document.createTextNode('<%= ((Element) types.item(i)).getAttribute(XMLBuilder.A_TYPE) %>');
  opt.appendChild(txt);
  sel.appendChild(opt);<%
  }
%>cell.appendChild(sel);
  row.appendChild(cell);
  cell= document.createElement('td');
  cell.style.textAlign= 'center';
  cell.style.whiteSpace= 'nowrap';
  var size= document.createElement('input');
  size.setAttribute('type','text');
  size.setAttribute('size', '4');
  size.setAttribute('name', '<%= AccessController.P_NEWREQSIZE %>' + subindex);
  size.setAttribute('value', '100');
  cell.appendChild(size);
  txt= document.createTextNode('kB');
  cell.appendChild(txt);
  row.appendChild(cell);
  cell= document.createElement('td');
  cell.style.width= '10%';
  cell.style.textAlign= 'center';
  cell.style.whiteSpace = 'nowrap';
  txt= makeRemoveLink();
  cell.appendChild(txt);
  row.appendChild(cell);
  subindex++;
  return row;
}
    
function addSubRow() {
  var subtable= getElementById('subtable');
  var subtablebody;
  if (subtable == null) {
    subtable= document.createElement('table');
    subtable.id= 'subtable';
    subtable.className= 'assignment_table'
    subtablebody= document.createElement('tbody');
    subtable.appendChild(subtablebody);
    var submissions= getElementById('submissions');
    var addsub= getElementById('addsub');
    submissions.insertBefore(subtable, addsub);
  } else subtablebody= subtable.getElementsByTagName('tbody').item(0);
  subtablebody.appendChild(makeSubRow());
}

/* Assignment Files */

var filesindex= 0;
    
var typeinds= new Array(8);	// 8 is starting value
    
// Creates the "remove row" links
function removeLink(rowID) {
  var remlink= document.createElement('a');                    // a
  remlink.onclick= new Function('removeRow(\'' + rowID + '\'); return false;');
  remlink.href= "#";
  var linktext= document.createTextNode('(Remove row)');           // text
  remlink.appendChild(linktext);                                 // /text
  return remlink;
}
    
// Removes a row (target of "remove row" links)
function removeRow(rowID) {
  var row= getElementById(rowID);
  row.parentNode.removeChild(row);
}
    
// creates a row of the outer table
function makeOuterRow() {
  var firstrow= document.createElement('tr');    // TR
  var rowID= 'file' + filesindex;
  firstrow.id= rowID;
  var namecell= document.createElement('td');      // TD
  namecell.style.textAlign= 'center';
  var name= document.createElement('input');         // input
  name.setAttribute('type', 'text');
  name.setAttribute('size', '40');
  name.setAttribute('name', '<%= AccessController.P_NEWITEMNAME %>' + filesindex);
  namecell.appendChild(name);
  var p = document.createElement('p');
  var sm = document.createElement('small');
  var txt = document.createTextNode('Do not include file type, as it is automatically appended.');
  sm.appendChild(txt);
  p.appendChild(sm);
  namecell.appendChild(p);
  namecell.style.width= '10%';
  namecell.style.whiteSpace= 'nowrap';
  firstrow.appendChild(namecell);
  typeinds[filesindex]= 0;
  var filecell= document.createElement('td');          // div
  filecell.style.padding= '1em';
  filecell.style.width= '50%';
  filecell.style.textAlign= 'center';
  var hidinput = document.createElement('input');
  hidinput.setAttribute('type', 'hidden');
  hidinput.setAttribute('name', '<%= AccessController.P_NEWITEMFILEPATH %>' + filesindex);
  hidinput.setAttribute('id', '<%= AccessController.P_NEWITEMFILEPATH %>' + filesindex);
  hidinput.setAttribute('value', '');
  filecell.appendChild(hidinput);
  var upload= document.createElement('input');
  upload.setAttribute('type', 'file');
  upload.setAttribute('name', '<%= AccessController.P_NEWITEMFILE %>' + filesindex);
  upload.setAttribute('id', '<%= AccessController.P_NEWITEMFILE %>' + filesindex);
  upload.setAttribute('onChange', new Function('setFileValue(\'<%= AccessController.P_NEWITEMFILE %>\' + filesindex, \'<%= AccessController.P_NEWITEMFILEPATH %>\' + filesindex);'));
  filecell.appendChild(upload);
  firstrow.appendChild(filecell);                      // /div
  var remcell= document.createElement('td');       // td
  remcell.style.width= '10%';
  remcell.style.textAlign= 'center';
  remcell.style.whiteSpace= 'nowrap';
  remcell.appendChild(removeLink(rowID));  // a /a
  firstrow.appendChild(remcell);                   // /td
  filesindex++;
  if (filesindex > typeinds.length) {
    var newinds= new Array(2*typeinds.length);
    for (i= 0; i != newinds.length; i++) {
      newinds[i]= typeinds.length;
    }
    typeinds= newinds;
  }
  return firstrow;
}   

// adds a row to the outer table
function addOuterRow() {
  var filestable= getElementById('filestable');
  var filestablebody;
  if (filestable == null) {
    filestable= document.createElement('table');
    filestable.id= 'filestable';
    filestable.className= 'assignment_table';
    filestable.setAttribute('cellpadding', '0');
    filestable.setAttribute('cellspacing', '0');
    filestablebody= document.createElement('tbody');
    filestable.appendChild(filestablebody);
    var header= document.createElement('tr');
    var cell= document.createElement('th');
    var txt= document.createTextNode('Name');
    cell.appendChild(txt);
    header.appendChild(cell);
    cell= document.createElement('th');
    txt= document.createTextNode('File');
    cell.appendChild(txt);
    header.appendChild(cell);
    cell= document.createElement('th');
    txt= document.createTextNode('Remove');
    cell.appendChild(txt);
    header.appendChild(cell);
    filestablebody.appendChild(header);
    var files= getElementById('files');
    var addfiles= getElementById('addfiles');
    files.insertBefore(filestable, addfiles);
  } else filestablebody= filestable.getElementsByTagName('tbody').item(0);
  filestablebody.appendChild(makeOuterRow());
}

/* Survey Questions */

var questindex= 0;
var choiceindex = new Array();
    
function makeQuestRemoveLink() {
  var link= document.createElement('a');
  link.onclick= new Function('remove(\'quest' + questindex + '\'); updateTotalScore(); return false;');
  link.setAttribute('href', '#');
  var txt= document.createTextNode('(Remove row)');
  link.appendChild(txt);
  return link;
}

function makeQuestUpLink() {
  var link= document.createElement('a');
  link.onclick= new Function('moveUp(\'quest' + questindex + '\'); return false;');
  link.setAttribute('href', '#');
  var txt= document.createTextNode('(Up)');
  link.appendChild(txt);
  return link;
}

function makeQuestDownLink() {
  var link= document.createElement('a');
  link.onclick= new Function('moveDown(\'quest' + questindex + '\'); return false;');
  link.setAttribute('href', '#');
  var txt= document.createTextNode('(Down)');
  link.appendChild(txt);
  return link;
}

function moveUp(rowID)
{
  var row= getElementById(rowID);
  var questtable= getElementById('questtable');
  if (row.rowIndex > 1)
  {
	  row.parentNode.insertBefore(row, questtable.rows[row.rowIndex - 1]); 
  }
  renumberQuestions();
}

function moveDown(rowID)
{
  var row= getElementById(rowID);
  var questtable= getElementById('questtable');
  if (row.rowIndex < questtable.rows.length - 1)
  {
	  row.parentNode.insertBefore(row, questtable.rows[row.rowIndex + 2]); 
  }
  renumberQuestions();
}

function renumberQuestions()
{
  var questtable= getElementById('questtable');
  for (var i = 1; i < questtable.rows.length; i++)
  {
	  questtable.rows[i].cells[0].innerHTML = i + '.'; 
  }
}

scoreInputs = new Array();

function makeQuestRow(hasScore) {
  questindex++;
  var row= document.createElement('tr');
  row.id= 'quest' + questindex;
  var cell= document.createElement('td');
  cell.style.textAlign= 'left';
  var txt = document.createTextNode(questindex + '.');
  cell.appendChild(txt);
  row.appendChild(cell);
  
  cell= document.createElement('td');
  cell.style.textAlign= 'left';
  txt = makeQuestUpLink();
  cell.appendChild(txt);
  txt = document.createTextNode(' ');
  cell.appendChild(txt);
  txt = makeQuestDownLink();
  cell.appendChild(txt);
  row.appendChild(cell);
  
  cell= document.createElement('td');
  cell.setAttribute('id', 'questcell' + questindex);
  cell.style.textAlign= 'left';
  var name= document.createElement('input');
  name.setAttribute('type', 'text');
  name.setAttribute('size', '40');
  name.setAttribute('name', '<%= AccessController.P_NEWSUBPROBNAME %>' + questindex);
  cell.appendChild(name);
  var newLink = document.createElement('a');
  newLink.setAttribute('href', '#');
  newLink.setAttribute('class', 'hide');
  newLink.className = 'replace';
  
  var subID = questindex;
  var choicesID = 'choices' + subID;
  newLink.onclick = new Function('addChoice(\'' + choicesID + '\' , ' + questindex + ',' + true +'); return false;');
  txt = document.createTextNode('(Add Choice)');
  newLink.appendChild(txt);
  
  var addChoiceDivID = "addChoice_" + questindex;
  var addChoiceDiv = document.createElement('div');
  addChoiceDiv.id = addChoiceDivID;
  addChoiceDiv.style.display = 'block';
  addChoiceDiv.appendChild(newLink);
  
  var choicesDiv = document.createElement('div');
  choicesDiv.id = choicesID;
  choicesDiv.style.display = "block";
  cell.appendChild(choicesDiv);
  row.appendChild(cell);
  
  cell.appendChild(addChoiceDiv);
  
  cell= document.createElement('td');
  cell.style.textAlign= 'center';
  cell.style.width= '10%';
  var sel= document.createElement('select');
  sel.setAttribute('size', '1');
  sel.setAttribute('name', '<%= AccessController.P_NEWSUBPROBTYPE %>' + questindex);
  sel.onchange = function() { showAddChoice(sel, subID); return false;};
  sel.style.width= '100%';
  
  var opt;
  opt= document.createElement('option');
  txt= document.createTextNode('Multiple Choice');
  opt.appendChild(txt);
  opt.setAttribute('value', '<%=SubProblem.MULTIPLE_CHOICE%>');
  sel.appendChild(opt);
  cell.appendChild(sel);
  
  opt= document.createElement('option');
  txt= document.createTextNode('Fill In');
  opt.setAttribute('value', '<%=SubProblem.FILL_IN%>');
  opt.appendChild(txt);
  sel.appendChild(opt);
  cell.appendChild(sel);
  
  opt= document.createElement('option');
  txt= document.createTextNode('Short Answer');
  opt.setAttribute('value', '<%=SubProblem.SHORT_ANSWER%>');
  opt.appendChild(txt);
  sel.appendChild(opt);
  cell.appendChild(sel);
  row.appendChild(cell);
  
  var score= document.createElement('input');
  score.id = "subprobscore" + subID;
  scoreInputs[scoreInputs.length] = score.id;
  if (hasScore)
  {
	  score.setAttribute('type','text');
	  score.setAttribute('size', '3');
	  score.setAttribute('value', '100');
  }
  else
  {
      score.setAttribute('type','hidden');
      score.setAttribute('value', '0');
  }
      
  var scoreID = '<%= AccessController.P_NEWSUBPROBSCORE %>' + questindex;
  score.setAttribute('name', scoreID);
  score.onkeyup = new Function('updateTotalScore(); return false;');
  scoreInputs.push(scoreID);
  
  if (hasScore)
  {
    cell= document.createElement('td');
    cell.style.textAlign= 'center';
    cell.style.whiteSpace= 'nowrap';
    cell.appendChild(score);
    row.appendChild(cell);
  }
   
  cell= document.createElement('td');
  cell.style.width= '10%';
  cell.style.textAlign= 'center';
  cell.style.whiteSpace = 'nowrap';
  txt= makeQuestRemoveLink();
  cell.appendChild(txt);
  if (!hasScore)
  {
  	cell.appendChild(score);
  }
  row.appendChild(cell);
  choiceindex[questindex] = 0;
  questindex++;
  return row;
}
  
function addQuestRow(hasScore) {
  var questtable= getElementById('questtable');
  var questtablebody= questtable.getElementsByTagName('tbody').item(0);
  questtablebody.appendChild(makeQuestRow(hasScore));
  renumberQuestions();
  updateTotalScore();
}

function addChoice(cellId, questindex, isNew) { 
  var correct = document.createElement('input');
  correct.setAttribute('type','radio');
  
  var choiceDivID = "choice_" + questindex + "_" + choiceindex[questindex];

  if (isNew)
	  correct.setAttribute('name','<%= AccessController.P_NEWCORRECTCHOICE %>' +  + questindex);
  else
	 correct.setAttribute('name','<%= AccessController.P_CORRECTCHOICE %>' +  + questindex);
  correct.setAttribute('value',choiceindex[questindex]);

  var choice= document.createElement('input');
  choice.setAttribute('type','text');
  choice.setAttribute('size', '20');
  choice.setAttribute('name', '<%= AccessController.P_NEWCHOICE %>' +  + questindex + '_' + choiceindex[questindex]);
    
  var txt = document.createTextNode(String.fromCharCode(choiceindex[questindex] +  97) + '.');
  var span = document.createElement('span');
  span.appendChild(txt);
  
  // remove link node
  var remove = document.createElement('a');
  remove.href = '';
  remove.onclick = function() { removeElement(choiceDivID, questindex); return false; }
  remove.appendChild(document.createTextNode('remove'));
  
  // wrapper div of the new choice
  var div = document.createElement('div');
  div.id = choiceDivID;
  
  var cell = document.getElementById(cellId);
  div.appendChild(span);
  div.appendChild(correct);
  div.appendChild(document.createTextNode(' '));
  div.appendChild(choice);
  div.appendChild(document.createTextNode(' '));
  div.appendChild(remove);
 
  cell.appendChild(div);

  choiceindex[questindex]++;
  
}

		
		// update the total score on the page with the sum of 
		// max scores of current subproblems
		function updateTotalScore () {
			var numSubProblems = scoreInputs.length;
			var target = document.getElementById('total_score');
			
			if (target == null || target == undefined) return;
			
			var inputs = new Array();
			var sum = 0;
			
			// collect all active input fields on the page
			for (var i = 0; i < numSubProblems; i++) {
				var inputField = document.getElementById(scoreInputs[i]);
				if (inputField == null && inputField == undefined) {
					sum += 0;
				} else {
					sum += parseFloat(inputField.value);
				}
			}
			
			target.firstChild.nodeValue = sum;
			
		}
		
      	/* show the add choice button for multiple-choice survey/quiz question */
		function showAddChoice(select, subID) {
			if (select == undefined || select == null) return;
			var target = "addChoice_" + subID;
			var choices = "choices" + subID;
			for (var i = 0; i < select.options.length; i = i + 1) {
				var option = select.options.item(i);
				if (option.selected) {
					if (option.value == <%= SubProblem.MULTIPLE_CHOICE %>) {
						document.getElementById(target).style.display = "block";
						document.getElementById(choices).style.display = "block";
					} else {
					    document.getElementById(target).style.display = "none";
					    document.getElementById(choices).style.display = "none";
					}
				}
			}
		}
		
		function reorderChoices(id) {
        	var choicesDiv = document.getElementById(id);
            var choiceList = choicesDiv.getElementsByTagName('div');
            for (var i = 0; i < choiceList.length; i++) {
           		var span = choiceList.item(i).getElementsByTagName('span').item(0);
                var txt = String.fromCharCode(i + 97) + ".";
                span.removeChild(span.firstChild);
                span.appendChild(document.createTextNode(txt));
            }
        }
		
		function removeElement(id, subID) {
			var target = document.getElementById(id);
			var parent = target.parentNode;
			parent.removeChild(target);
			reorderChoices(parent.id);
			choiceindex[subID]--;
		}

