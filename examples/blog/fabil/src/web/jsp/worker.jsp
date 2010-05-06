<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix='fa' uri='http://cornell.edu/taglibs/FabricUtils'%>

<h2>Worker Information - ${worker.name}</h2>

<c:if test="${error != null}">
<b>${error }</b>
</c:if>

<c:choose>
<c:when test="${worker.host == null}">

Please connect this worker to a blog server:

<form action="?" method="post">
<input type="hidden" name="action" value="sethost"/>
Host: <input type="text" value="http://" name="host" /> <input type="submit" value="Submit" />

</form>

</c:when>
<c:otherwise>

<p><b>Current State: </b> ${worker.state }</p>
<c:choose>
    <c:when test="${worker.active }">
        <B>ACTIVE</B>
        <p>Requests Remaining: ${worker.requestsRemaining }</p> 
    </c:when>
    <c:when test="${worker.idle }">
        <b>Connected to: </b> ${worker.host } [<a href="?action=clearhost">Disconnect</a>]
    </c:when>
    <c:when test="${worker.error}">
        <b>Error state:</b><br />
        <pre>${worker.errorMessage }</pre>
    </c:when>
    
    <c:otherwise>
    <em>Unknown state</em>
    </c:otherwise>

</c:choose>

<c:if test="${worker.idle }">

<c:if test="${fa:length(worker.pageStats) > 0}">
<h3>Last benchmark data</h3>

<c:if test="${median != null}">  
<img src="http://chart.apis.google.com/chart?cht=lc&chs=450x200&chtt=History+of+Page+Load+Times&chxt=x,y,x&chds=0,${worker.maxLoadTime*1.3 }&chxl=2:||Request+Number|&chxr=1,0,${worker.maxLoadTime*1.3},${worker.maxLoadTime/4}|0,0,${fa:length(worker.pageStats)},${fa:length(worker.pageStats)/10}&chd=t:${worker.loadTimeValues}" />  

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
   <c:forEach var="entry" items="${worker.pageStats}">
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
