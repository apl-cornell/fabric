<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="header.jsp" />
  <div class="blog">
    <h2>${entry.title} <span class="delete">[<a href="?action=deletepost&amp;id=${entry.id}">Delete</a>]</span></h2>
    <p>Posted on ${entry.creationTime}</p>
    <div class="entry">${entry.content}</div>
    <div class="bottom"><p>${fn:length(entry.comments)} comment(s)</p></div> 
  </div>   

<div class="comments">

<h3>Comments</h3>

<c:if test="${passmsg != null || errormsg != null}">
<p class="msg">
    <span class="error">${errormsg}</span>
    <span class="pass">${passmsg}</span>    
</p>
</c:if>

<c:forEach var="comment" items="${entry.comments}">


<span class="c"> Posted on ${comment.creationTime} by ${comment.username}</span>
<p>
${comment.content }
</p>

</c:forEach>

<h3>Post a Comment</h3>
<form action="?action=postcomment" method="post">
<input type="hidden" name="id" value="${entry.id}"></input>
<p>Name: <input type="text" name="name" /></p>
<p>Comment:</p>
<p><textarea name="comment" cols="80" rows="10"></textarea>
<br />
<input type="submit" value="Submit Comment" /></p>
</form>


</div>

<jsp:include page="footer.jsp" />