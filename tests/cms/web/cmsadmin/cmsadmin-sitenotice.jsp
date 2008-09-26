<%@ page language="java" import="org.w3c.dom.*, cms.www.*, java.util.Date, cms.www.util.*, cms.www.xml.*, cms.auth.*, cms.model.*" %>
<% Document displayData = (Document)session.getAttribute(AccessController.A_DISPLAYDATA);
Principal p = (Principal)session.getAttribute(AccessController.A_PRINCIPAL);
Element root = (Element)displayData.getElementsByTagName(XMLBuilder.TAG_ROOT).item(0);
Date defDate = new Date(System.currentTimeMillis() + DateTimeUtil.SECS_PER_WEEK*1000);
String defAMPM = DateTimeUtil.AMPM.format(defDate);%>
  <h2>
    Edit Site Notices
    <span id="sitenoticehead">
     <a class="hide" href="#" onClick="show('sitenotice', '(show)', '(hide)'); return false;">(show)</a>
    </span>
  </h2>
  <div id="sitenotice" class="showhide" style="display: none">
    <table class="assignment_table">
      <tr>
        <th>
		  Post date
		</th> 
        <th>
		  Author
		</th>
        <th>
		  Notice
		</th>
      </tr>
      <tr>
        <td>
          <span style="text-align:center; font-weight:bold">NEW</span>
        </td>
        <td>
          <span style="color: gray;">(you)</span>
        </td>
        <td>
		  <form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_NEWNOTICE %>" method="post">
          <textarea cols=80 rows=3 name="<%= AccessController.P_NOTICETEXT %>"></textarea><br/>
          Hide/Preview: <input type="checkbox" name="<%= AccessController.P_HIDDEN %>">
          
          Expiration <a href="#" class="hide" onclick="getElementById('newNoticeExpDate').value = ''; getElementById('newNoticeExpTime').value = '';">(clear)</a>:
          <script type="text/javascript">
              var calNewNotice = new CalendarPopup();
          </script>
          <input id="newNoticeExpDate" size="11" name="<%= AccessController.P_NOTICEEXPDATE %>" value="<%= DateTimeUtil.DATE.format(defDate) %>" type="text" />
            <a href="#" id="newExpDateLink" name="newExpDateLink" onclick="calNewNotice.select(getElementById('newNoticeExpDate'), 'newExpDateLink', 'MMM d, yyyy'); return false;" />
              <img class="calicon" src="../images/cal.gif" alt="Select" height="16" width="16">
            </a>
    	  <input id="newNoticeExpTime" size="4" name="<%= AccessController.P_NOTICEEXPTIME %>" value="<%= DateTimeUtil.TIME.format(defDate) %>" type="text">
	      <select name="<%= AccessController.P_NOTICEEXPAMPM %>">
              <option<%= "AM".equals(defAMPM) ? " selected" : "" %>>AM</option>
              <option<%= "PM".equals(defAMPM) ? " selected" : "" %>>PM</option>
	      </select>
          <button name="<%= AccessController.P_SUBMIT %>" value="<%= AccessController.P_SUBMIT %>" type="<%= AccessController.P_SUBMIT %>">Post</button>
          </form>
        </td>
      </tr><%
      	NodeList curNotices = ((Element) root.getElementsByTagName(XMLBuilder.TAG_CURSITENOTICES).item(0)).getChildNodes();
    	if (curNotices.getLength() == 0)
    	{ %>
      <tr>
      <td colspan="3">
		  No current site notices.
	  </td>
	  </tr><%
		}
		else
		{
			for (int i = 0; i < curNotices.getLength(); i++)
			{
				Element entry = (Element) curNotices.item(i);
				String id = entry.getAttribute(XMLBuilder.A_ID);
				String ampm = entry.getAttribute(XMLBuilder.A_DUEAMPM); %>
      <tr>
        <td>
          
        </td>
        <td>
          <%= entry.getAttribute(XMLBuilder.A_POSTER) %>
        </td>
        <td>
		  <form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_EDITNOTICE %>" method="post">
	  	  <input type="hidden" name="<%= AccessController.P_ID %>" value="<%= entry.getAttribute(XMLBuilder.A_ID) %>">
          <textarea cols=80 rows=3 name="<%= AccessController.P_NOTICETEXT %>"><%= entry.getAttribute(XMLBuilder.A_TEXT) %></textarea><br/>
		  
          Hide/Preview: <input type="checkbox" name="<%= AccessController.P_HIDDEN %>"<%= entry.getAttribute(XMLBuilder.A_HIDDEN).equals("true") ? " checked" : "" %>>
          Delete: <input type="checkbox" name="<%= AccessController.P_DELNOTICE %>">
          
          Expiration <a href="#" class="hide" onclick="getElementById('notice<%=id%>ExpDate').value = ''; getElementById('notice<%=id%>ExpTime').value = '';">(clear)</a>:
          <script type="text/javascript">
              var calNotice<%=id%> = new CalendarPopup();
          </script>
          <input id="notice<%=id%>ExpDate" size="11" name="<%= AccessController.P_NOTICEEXPDATE %>" value="<%= entry.getAttribute(XMLBuilder.A_DUEDATE) %>" type="text" />
            <a href="#" id="ExpDate<%=id%>Link" name="ExpDate<%=id%>Link" onclick="calNotice<%=id%>.select(getElementById('notice<%=id%>ExpDate'), 'ExpDate<%=id%>Link', 'MMM d, yyyy'); return false;" />
              <img class="calicon" src="../images/cal.gif" alt="Select" height="16" width="16">
            </a>
    	  <input id="notice<%=id%>ExpTime" size="4" name="<%= AccessController.P_NOTICEEXPTIME %>" value="<%= entry.getAttribute(XMLBuilder.A_DUETIME) %>" type="text"/>
	      <select name="<%= AccessController.P_NOTICEEXPAMPM %>">
              <option<%= "AM".equals(ampm) ? " selected" : "" %>>AM</option>
              <option<%= "PM".equals(ampm) ? " selected" : "" %>>PM</option>
	      </select>
          
          <button name="<%= AccessController.P_SUBMIT %>" value="<%= AccessController.P_SUBMIT %>" type="<%= AccessController.P_SUBMIT %>">Submit</button>
    	  </form>
        </td>
      </tr><%
			}
		} %>
    </table><%
      	NodeList delNotices = ((Element) root.getElementsByTagName(XMLBuilder.TAG_DELSITENOTICES).item(0)).getChildNodes();
    	if (delNotices.getLength() != 0)
    	{ %>
    <span id="delnoticeshead">
      Deleted notices <a href="#" class="hide" onclick="show('delnotices','(show)','(hide)'); return false;">(show)</a>
    </span>
    <div id="delnotices" style="display: none"><%
			for (int i = 0; i < delNotices.getLength(); i++)
			{
			    /* leave deleted field null to undelete notice */
				Element entry = (Element) delNotices.item(i);
				String expDate = entry.getAttribute(XMLBuilder.A_DUEDATE);
				String exp;
				if (expDate == null || expDate.equals(""))
				  exp = "";
				else
				  exp = "(Expired " + expDate + " " +
				        entry.getAttribute(XMLBuilder.A_DUETIME) + " " +
				        entry.getAttribute(XMLBuilder.A_DUEAMPM) + ")";%>
		  <form action="?<%= AccessController.P_ACTION %>=<%= AccessController.ACT_EDITNOTICE %>" method="post">
	  	  <input type="hidden" name="<%= AccessController.P_ID %>" value="<%= entry.getAttribute(XMLBuilder.A_ID) %>">
	  	  <input type="hidden" name="<%= AccessController.P_NOTICETEXT %>" value="<%= entry.getAttribute(XMLBuilder.A_TEXT) %>">
	  	  <input type="hidden" name="<%= AccessController.P_HIDDEN %>" value="<%= entry.getAttribute(XMLBuilder.A_HIDDEN) %>">
	  	  <input type="hidden" name="<%= AccessController.P_NOTICEEXPDATE %>" value="<%= entry.getAttribute(XMLBuilder.A_DUEDATE) %>">
	  	  <input type="hidden" name="<%= AccessController.P_NOTICEEXPTIME %>" value="<%= entry.getAttribute(XMLBuilder.A_DUETIME) %>">
	  	  <input type="hidden" name="<%= AccessController.P_NOTICEEXPAMPM %>" value="<%= entry.getAttribute(XMLBuilder.A_DUEAMPM) %>">
          <%= entry.getAttribute(XMLBuilder.A_DATE) %>: <%= entry.getAttribute(XMLBuilder.A_TEXT) %> <%= exp %>
          <button name="<%= AccessController.P_SUBMIT %>" value="<%= AccessController.P_SUBMIT %>" type="<%= AccessController.P_SUBMIT %>">Undelete</button>
          <br/>
	  	  </form><%
            } %>
    </div><%
        } %>
  </div>