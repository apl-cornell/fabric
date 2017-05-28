<%@page language="java" import="cms.www.*"%>
<script type="text/javascript">
/*
code to fiddle with staff permission checkboxes and create new permission-table rows,
used in both the staff/courseprops and cmsadmin/cmsadmincourseprops pages
*/

  var permindex= 0;

  function makePermRemoveLink() {
    var link= document.createElement('a');
    link.onclick= new Function('remove(\'perm' + permindex + '\'); return false;');
    link.setAttribute('href', '#');
    var txt= document.createTextNode('(Remove row)');
    link.appendChild(txt);
    return link;
  }
  
  function makePermRow() {
    var row= document.createElement('tr');
    row.id= 'perm' + permindex;
    
    var cell= document.createElement('td');
    cell.style.textAlign= 'center';
    cell.appendChild(makePermRemoveLink());
    row.appendChild(cell);
    
    cell= document.createElement('td');
    var label = document.createTextNode('NetID:  ');
    var input= document.createElement('input');
    input.setAttribute('type', 'text');
    input.setAttribute('name', '<%= AccessController.P_NEWNETID %>' + permindex);
    input.setAttribute('size', '10');
    input.setAttribute('maxlength', '7');
    cell.appendChild(label);
    cell.appendChild(input);
    row.appendChild(cell);
    
    cell= document.createElement('td');
    cell.style.textAlign= 'center';
    input= document.createElement('input');
    input.setAttribute('type', 'checkbox');
    input.setAttribute('name', '<%= AccessController.P_NEWADMIN %>' + permindex);
    input.id= input.getAttribute('name');
    input.onclick= new Function('if (this.checked) adminClick(' + permindex + ', true);');
    cell.appendChild(input);
    row.appendChild(cell);
    
    cell= document.createElement('td');
    cell.style.textAlign= 'center';
    input= document.createElement('input');
    input.setAttribute('type', 'checkbox');
    input.setAttribute('name', '<%= AccessController.P_NEWGROUPS %>' + permindex);
    input.id= input.getAttribute('name');
    input.onclick= new Function('if (!this.checked) otherClick(' + permindex + ', true)');
    cell.appendChild(input);
    row.appendChild(cell);
    
    cell= document.createElement('td');
    cell.style.textAlign= 'center';
    input= document.createElement('input');
    input.setAttribute('type', 'checkbox');
    input.setAttribute('name', '<%= AccessController.P_NEWGRADES %>' + permindex);
    input.id= input.getAttribute('name');
    input.onclick= new Function('if (!this.checked) otherClick(' + permindex + ', true)');
    cell.appendChild(input);
    row.appendChild(cell);
    
    cell= document.createElement('td');
    cell.style.textAlign= 'center';
    input= document.createElement('input');
    input.setAttribute('type', 'checkbox');
    input.setAttribute('name', '<%= AccessController.P_NEWASSIGN %>' + permindex);
    input.id= input.getAttribute('name');
    input.onclick= new Function('if (!this.checked) otherClick(' + permindex + ', true)');
    cell.appendChild(input);
    row.appendChild(cell);    
    
    cell= document.createElement('td');
    cell.style.textAlign= 'center';
    input= document.createElement('input');
    input.setAttribute('type', 'checkbox');
    input.setAttribute('name', '<%= AccessController.P_NEWCATEGORY %>' + permindex);
    input.id= input.getAttribute('name');
    input.onclick= new Function('if (!this.checked) otherClick(' + permindex + ', true)');
    cell.appendChild(input);
    row.appendChild(cell);
    
    permindex++;
    return row;
  }
    
  function addPermRow() {
    var permtable= getElementById('permtable');
    var permtablebody= permtable.getElementsByTagName('tbody').item(0);
    permtablebody.appendChild(makePermRow());
  }
  
  /* 
   * Handler when an admin checkbox is clicked
   * Should only be called if box was set to checked
   * Params: id - netid if old, number if new
   *         isnew - whether or not this is a new checkbox
   */
  function adminClick(id, isnew) {
    var groups, grades, assigns, ctgs;
    if (isnew)
    {
      groups = '<%= AccessController.P_NEWGROUPS %>' + id;
      grades = '<%= AccessController.P_NEWGRADES %>' + id;
      assigns = '<%= AccessController.P_NEWASSIGN %>' + id;
      ctgs = '<%= AccessController.P_NEWCATEGORY %>' + id;
    }
    else
    {
      groups= id + '<%= AccessController.P_ISGROUPS %>';
      grades= id + '<%= AccessController.P_ISGRADES %>';
      assigns= id + '<%= AccessController.P_ISASSIGN %>';
      ctgs = id + '<%= AccessController.P_ISCATEGORY %>';
    }
    getElementById(groups).checked= true;
    getElementById(grades).checked= true;
    getElementById(assigns).checked= true;
    getElementById(ctgs).checked = true;
  }
  
  /*
   * Handler for when other permission checkbox is clicked
   * Sets admin checkbox to unchecked
   * Should only be called when a box is unchecked
   * Params: id - id of the unchecked box (netid for old, number for new)
   *         isnew - whether or not this is a new checkbox
   */
  function otherClick(id, isnew) {
    var admin;
    if (isnew) {
      admin= '<%= AccessController.P_NEWADMIN %>' + id;
    } else {
      admin= id + '<%= AccessController.P_ISADMIN %>';
    }
    getElementById(admin).checked= false;
  }
</script>