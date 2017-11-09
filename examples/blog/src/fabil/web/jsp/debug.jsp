<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix='fa' uri='http://cornell.edu/taglibs/FabricUtils'%>

<div id="diagshow">
<a href="#" onclick="document.getElementById('diag').style.display='block'; return false">Debug</a>
</div>

<c:set var="stats" scope="session" value="${statistics.currentPageStats}" />
<c:choose>
<c:when test="${debug }">
<div id="diag" style="display:block" >
</c:when>
<c:otherwise>
<div id="diag" style="display:none" >
</c:otherwise>
</c:choose>

<table cellpadding="10" border="1">
<tr>
<td valign="top">
<h2>DB Information</h2>
<p><b>Number of blogs:</b> ${blog.numBlogPosts}<br />
<b>Number of comments:</b> ${blog.numComments}</p>
</td>

<td valign="top">
<h2>Actions</h2>
<form action="?debugaction=recreate" method="post">
<input type="submit" value="Recreate Database"/>
</form>
<!--<form action="?debugaction=addposts" method="post">
  <p>Add <input type="text" size="2" /> posts and comments
<input type="button" value="Go"/></p>
</form> -->
</td>
<td valign="top">
<h2>Last Page Load Data</h2>
<p><b>Time to load:</b> ${stats.pageLoadTime} ms<br />
<b>Time to do JSP processing:</b> ${stats.pageLoadTime - stats.appTime} ms<br />
<b>Transaction commit+prepare time:</b> ${stats.transactionTime } ms<br />
<b>Number of transactions:</b> ${stats.numTransactions }<br />
<b>Number of (application) objects read:</b> ${stats.numReads }<br />
<b>Number of (application) objects updated:</b> ${stats.numUpdates }<br />
<b>Number of (application) objects created:</b> ${stats.numCreates }<br />
</td>

<td valign="top">
<h2>Run Benchmark</h2>
<c:if test="${clientError }">
<p style="color:red">Client Error - Benchmark Stopped</p>
<p>Check client for more details</p>
</c:if>
<c:choose>
<c:when test="${numbusyclients > 0 }">
<p>Benchmark in progress... [<a href="?debugaction=reload">Refresh</a>]<br/> Check on the status of clients to the right.</p>
</c:when>
<c:when test="${fn:length(clientslist) > 0 }">
<form action="?" method="post">
<input type="hidden" name="debugaction" value="benchmark"/>
<p>Number of clients:
<select name="numclients">
    <c:forEach var="i" begin="1" end="${fn:length(clientslist) }">
        <option value="${i }">${i }</option>
    </c:forEach>
</select>
</p>
<p>Action:
<select name="action">
    <c:forEach var="action" items="${actions}">
        <option value="${action }">${action }</option>
    </c:forEach>
</select>
</p>
<p>Sample size: <input type="text" value="100" name="size" /></p>
<input type="submit" value="Run Benchmark" />
</form>
</c:when>
<c:otherwise>
<p>Please connect a client to start a benchmark</p>
</c:otherwise>
</c:choose>
</td>
<td valign="top">
<h2>Clients</h2>
<ul>
 <c:forEach var="entry" items="${clientslist}">
    <li><a href="http://${entry}/web">${entry}</a> [<a href="?clientaction=removeother&amp;id=${entry}">x</a>]</li>
</c:forEach>
</ul>
</td>

</tr>

</table>
<div id="hide">
<a href="#" onclick="document.getElementById('diag').style.display='none'; return false">Hide</a>
</div>

