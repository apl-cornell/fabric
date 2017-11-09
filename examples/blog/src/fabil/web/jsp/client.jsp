<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix='fa' uri='http://cornell.edu/taglibs/FabricUtils'%>

<h2>Client Information - ${client.name}</h2>

<c:if test="${error != null}">
<b>${error }</b>
</c:if>

<c:choose>
<c:when test="${client.host == null}">

Please connect this client to a blog server:

<form action="?" method="post">
<input type="hidden" name="action" value="sethost"/>
Host: <input type="text" value="http://" name="host" /> <input type="submit" value="Submit" />

</form>

</c:when>
<c:otherwise>

<p><b>Current State: </b> ${client.state }</p>
<c:choose>
    <c:when test="${client.active }">
        <B>ACTIVE</B>
        <p>Requests Remaining: ${client.requestsRemaining }</p>
    </c:when>
    <c:when test="${client.idle }">
        <b>Connected to: </b> ${client.host } [<a href="?action=clearhost">Disconnect</a>]
    </c:when>
    <c:when test="${client.error}">
        <b>Error state:</b><br />
        <pre>${client.errorMessage }</pre>
    </c:when>

    <c:otherwise>
    <em>Unknown state</em>
    </c:otherwise>

</c:choose>

<c:if test="${client.idle }">

<c:if test="${fa:length(client.pageStats) > 0}">
<h3>Last benchmark data</h3>

<c:if test="${median != null}">
<img src="http://chart.apis.google.com/chart?cht=lc&chs=450x200&chtt=History+of+Page+Load+Times&chxt=x,y,x&chds=0,${client.maxLoadTime*1.3 }&chxl=2:||Request+Number|&chxr=1,0,${client.maxLoadTime*1.3},${client.maxLoadTime/4}|0,0,${fa:length(client.pageStats)},${fa:length(client.pageStats)/10}&chd=t:${client.loadTimeValues}" />

<img src="http://chart.apis.google.com/chart?cht=p3&chtt=Median+Page+Load+Breakdown&chd=t:${median.transactionTime},${median.appTime},${median.pageLoadTime-median.appTime},${median.trasmissionTime}&chs=450x200&chl=Transaction+Time|App+Time|JSP+Time|Transmission+Time" />
<br />
<b>Median Load</b><br/>

<table border="1">
   <tr>
    <th>Transaction Time (ms)</th>
    <th>Application Time (ms)</th>
    <th>JSP Time (ms)</th>
    <th>Transmission Time (ms)</th>
    <th>Total Time (ms)</th>
    <th>Objects Read</th>
    <th>Objects Updated</th>
    <th>Objects Created</th>
   </tr>
   <tr>
    <td>${median.transactionTime }</td>
    <td>${median.appTime }</td>
    <td>${median.pageLoadTime - median.appTime }</td>
    <td>${median.trasmissionTime }</td>
    <td>${median.trasmissionTime + median.pageLoadTime }</td>
    <td>${median.numReads }</td>
    <td>${median.numUpdates }</td>
    <td>${median.numCreates }</td>
    </tr>
</table>
</c:if>
<br />
<b>Raw Data</b>
   <table border="1">
   <tr>
    <th>Transaction Time (ms)</th>
    <th>Application Time (ms)</th>
    <th>JSP Time (ms)</th>
    <th>Transmission Time (ms)</th>
    <th>Total Time (ms)</th>
    <th>Objects Read</th>
    <th>Objects Updated</th>
    <th>Objects Created</th>
   </tr>
   <c:forEach var="entry" items="${client.pageStats}">
   <tr>
    <td>${entry.transactionTime }</td>
    <td>${entry.appTime }</td>
    <td>${entry.pageLoadTime - entry.appTime }</td>
    <td>${entry.trasmissionTime }</td>
    <td>${entry.trasmissionTime + entry.pageLoadTime }</td>
    <td>${entry.numReads }</td>
    <td>${entry.numUpdates }</td>
    <td>${entry.numCreates }</td>
    </tr>
   </c:forEach>
   </table>
</c:if>

</c:if>
</c:otherwise>
</c:choose>
