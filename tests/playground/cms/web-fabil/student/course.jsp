<%@ page language="java" import="org.w3c.dom.*, cms.www.*, cms.auth.*, cms.model.*, cms.www.xml.*" %>
<% Document displayData = (Document) session.getAttribute(AccessController.A_DISPLAYDATA);
   Element root = (Element) displayData.getChildNodes().item(0);
	 Element course = XMLUtil.getFirstChildByTagName(root, XMLBuilder.TAG_COURSE);
	 Element principal = (Element) root.getElementsByTagName(XMLBuilder.TAG_PRINCIPAL).item(0);
	 boolean isGuest = principal == null;
   boolean isCCMember = principal != null && principal.hasAttribute(XMLBuilder.A_ISCCMEMBER);
   boolean isStudent = course.hasAttribute(XMLBuilder.A_ISSTUDENT);
   String courseid = null;
   NodeList l2 = course.getElementsByTagName(XMLBuilder.TAG_DESCRIPTION);
   Node n2 = l2.item(0);
   Text description = (Text)n2.getFirstChild(); 
   boolean showfinalgrade = course.hasAttribute(XMLBuilder.A_SHOWFINALGRADES);
   String finalgrade = course.getAttribute(XMLBuilder.A_FINALGRADE);
   boolean showAnnounce = 
      isStudent ? true : 
      isCCMember ? course.hasAttribute(XMLBuilder.A_ANNOUNCECCACCESS) :
      course.hasAttribute(XMLBuilder.A_ANNOUNCEGUESTACCESS);
   boolean showAssign = 
      isStudent ? true :
      isCCMember ? course.hasAttribute(XMLBuilder.A_ASSIGNCCACCESS) : 
      course.hasAttribute(XMLBuilder.A_ASSIGNGUESTACCESS);
   //course.hasAttribute(XMLBuilder.A_CANSEEANNOUNCE);
   //boolean showAssign = course.hasAttribute(XMLBuilder.A_CANSEEASSIGNS);
   try { %>
<jsp:include page="../header.jsp" />
<jsp:include page="../header-page.jsp" />
<div id="course_wrapper_withnav">
<table id="course_wrapper_table" summary="course wrapper" cellpadding="0" cellspacing="0" border="0"  width="100%">

<tr>
	<jsp:include page="../navbar.jsp"/>
  <td valign="top"  id="course_page_container">
  <div id="course_page">
  	<jsp:include page="../problem-report.jsp"/>
    <span class="course_title">
    	<%= course.getAttribute(XMLBuilder.A_DISPLAYEDCODE) + ": " + course.getAttribute(XMLBuilder.A_COURSENAME) + " (" + course.getAttribute(XMLBuilder.A_SEMESTER) + ")" %>
			<% if (Boolean.valueOf(course.getAttribute(XMLBuilder.A_COURSEFROZEN)).booleanValue()) { %>
        <span class="course_title" id="ct_frozen"> [FROZEN]</span>
			<% } %>
    </span><br><br>
<% if (showfinalgrade && finalgrade != null) { %>
    <dl style="font-size: large"><dt>
    <span id="finalgradeshowhead">
      <a class="hide" href="#" onClick="show('finalgradeshow', 'Show', 'Hide'); return false;">Show</a>
    </span> Final Grade: </dt><dd>&nbsp;<div id="finalgradeshow" style="display: none"><%= finalgrade %></div></dd>
    </dl>
    <br><br>
<% } %>
<% if (description != null && !description.getData().equals("")) { %>
    <div class="assignment_left">
      <h2>Course Information
        <span id="infohead">
          <a class="hide" href="#" onClick="hide('info', '(hide)', '(show)'); return false;">(hide)</a>
        </span>
      </h2>
      <div id="info" class="showhide">
        <%= description.getData() %>
      </div>
    </div>
  <% } %>
  <% if (showAnnounce) { %>
  <jsp:include page="../course-announcements.jsp"/>
  <% } %>  
	<% if (showAssign) { %>
	<jsp:include page="course-assignments.jsp"/>
  <% } %>
	<jsp:include page="../course-category.jsp" />
  </div>
  </td>
  <td id="course_menu_container"><div id="course_menu_top">&nbsp;</div></td>
</tr>
<jsp:include  page="../footer.jsp" />
<% } catch (Exception e) {
     e.printStackTrace();
   } %>
