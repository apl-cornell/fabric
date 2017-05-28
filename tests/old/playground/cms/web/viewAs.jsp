<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.model.*, cms.www.xml.*" %>
<%
/***********************************************************************************
* if staff, show the "view as" construction on the right side of the red bar
***********************************************************************************/
String URL= request.getServletPath(); 
session.setAttribute(AccessController.A_URL, URL);
Document displayData= (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
User p = (User)session.getAttribute(AccessController.A_PRINCIPAL);
Element root= (Element)displayData.getFirstChild();
Element course= XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
boolean showViewAs= false;
String courseid = null;
if (course!=null) {
    courseid= course.getAttribute(XMLBuilder.A_COURSEID);
    showViewAs= p.isAdminPrivByCourseID(courseid) || p.isInStaffAsBlankMode(); 
}%>

<% 
if ( showViewAs ) {
%>


<td align="right" nowrap valign="middle">

<form action="?<%= AccessController.P_ACTION + "=" + AccessController.ACT_VIEWSTUDENT + "&amp;" + AccessController.P_COURSEID + "=" + courseid %>" method="post" style="margin-bottom:0;" >
<%--
<script language="Javascript" src="autosuggest.js"></script>
<div id="autosuggest"><ul></ul></div>
--%>

<div id="perspective">

<%
String begintag, endtag;
String begin= "<span class=\"currentpage\">";
String end= "</span>"; 
begintag=begin;
endtag=end;
%>	

&nbsp;&nbsp;
&nbsp;

<b>
 View as:  
</b>
(
<%
if (!p.isInStaffAsCornellMemMode() && !p.isInStaffAsGuestMode() && !p.isInStaffAsStudentMode()) {
    begintag = begin; 
    endtag = end;
  }else {
    begintag = "<a href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_VIEWRESET + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
    endtag= "</a>";
  } %>

<%= begintag %>Staff<%= endtag %>

|

<%
if (p.isInStaffAsCornellMemMode()) {
	begintag = begin; 
	endtag = end;
  }else {
	begintag = "<a href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_VIEWCORNELLMEM + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
	endtag= "</a>";
  } %>

<%= begintag %>CU Member<%= endtag %>

|
<%
if (p.isInStaffAsGuestMode()) {
	begintag = begin; 
	endtag = end;
  }else {
	begintag = "<a href=\"?" + AccessController.P_ACTION + "=" + AccessController.ACT_VIEWGUEST + "&amp;" + AccessController.P_COURSEID + "=" + courseid + "\">";
	endtag= "</a>";
  } %>
<%= begintag %>Guest<%= endtag %>

<%
if (p.isInStaffAsStudentMode()) {
    begintag = begin; 
    endtag = end;
  } 
else {
    begintag="";
    endtag="";
}  
%>


|<%--
<%
Element students= null;
NodeList studs= null;
CMSNodeList studentList= XMLUtil.getChildrenByTagNameAndAttributeValue(root,XMLBuilder.TAG_COURSESTUDENTS,XMLBuilder.A_COURSEID,course.getAttribute(XMLBuilder.A_COURSEID));
if (studentList.getLength()!=0) {
    students= (Element) studentList.get(0);
    studs= students.getChildNodes();
}
--%><%
String link, studNetID, selected;
String apparentNetID= "NetID";
if (p.isInStaffAsStudentMode()) 
    apparentNetID=p.getNetID();
/* name of input box for NetID, fixes bug when user presses Enter instead of clicking OK */
%>
<%=begintag%>Student&nbsp;<%=endtag%>
<input id="netIdBox" type=text size="10" maxLength="10" name="<%= AccessController.P_NETID %>"  value=<%=apparentNetID%>  <%-- onClick="focus();select(); --%>" style="
<%
if (p.isInStaffAsStudentMode()) {
%>
font-weight:bold; font-size: 10px;">
<% 
}
else {
%>  
font-size: 10px;">
<%
}
 %>
<%--
<script language="javascript">
    var students;
<%
    if (studs!=null) {
%>
        students = new Array(<%=studs.getLength()%>);
<%      for (int i= 0; i < studs.getLength(); i++) {
            Element student= (Element) studs.item(i); 
            studNetID= student.getAttribute(XMLBuilder.A_NETID);
%>
            students[<%=i%>]="<%=studNetID%>";
<%      }
    }
%>
   new AutoSuggest(document.getElementById('netIdBox'),students);
   function readBox()
   {
        var targetElement = document.getElementById("netIdBox");
        var netID=targetElement.value;
       <%String viewAsLink= "?" + AccessController.P_ACTION + "=" + AccessController.ACT_VIEWSTUDENT+ "&"+ AccessController.P_COURSEID + "=" + courseid + "&"  + AccessController.P_NETID + "=" ; %>	        
		        var link= "<%= viewAsLink %>";
		        link= link+ netID;
		        window.location.href=link;
        }
</script> --%>
<input type="submit" value="OK" style="font-size: 10px;">

)&nbsp;&nbsp;
</form>
</div>
</td><%
}
%>
