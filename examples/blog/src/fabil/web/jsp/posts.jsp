<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix='fa' uri='http://cornell.edu/taglibs/FabricUtils'%>

<jsp:include page="header.jsp" />

<c:choose>
    <c:when test="${param.end==null}">
        <c:set var="end" scope="page" value="3" />
    </c:when>
    <c:otherwise>
        <c:set var="end" scope="page" value="${param.end}" />
    </c:otherwise>
</c:choose>

<c:if test="${passmsg != null || errormsg != null}">
<p class="msg">
    <span class="error">${errormsg}</span>
    <span class="pass">${passmsg}</span>
</p>
</c:if>

<p><a href="?action=createpost">Create New Blog Post</a></p>

<c:forEach var="entry" items="${posts}">
  <div class="blog">
    <h2><a href="?action=viewpost&amp;id=${entry.id}">${entry.title}</a> <span class="delete">[<a href="?action=deletepost&amp;id=${entry.id}">Delete</a>]</span></h2>
    <p>Posted on ${entry.creationTime}</p>
    <div class="entry">${entry.content}</div>
    <div class="bottom"><p>${entry.numComments} comment(s)</p></div>
  </div>
</c:forEach>

<div class="pages">
<p>Showing posts <c:out value="${param.start+1}" default="1" /> to <c:out value="${end+1}" default="1" /></p>
<c:if test="${param.start >= 3}">
    <a href="?start=${param.start-3}&amp;end=${end-3}">Previous page</a>
</c:if>
<c:if test="${numPosts > end}">
    <a href="?start=${param.start+3}&amp;end=${end+3}">Next page</a>
</c:if>
</div>

<jsp:include page="footer.jsp" />
