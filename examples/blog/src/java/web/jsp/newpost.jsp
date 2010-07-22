<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<jsp:include page="header.jsp" />

<h2>Create New Blog Post</h2>
<form method="post">
<input type="hidden" name="action" value="createpost" />
<p style="text-align:left">Title: <input type="text" name="title" /> <br />
Content: <br />
<textarea name="content" rows="10" cols="80"></textarea><br />
<input type="submit" value="Create Blog Post" />
</p>
</form>

<jsp:include page="footer.jsp" />
