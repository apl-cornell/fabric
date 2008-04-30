/**
 * This test uses the same basic design as the current CMS implementation.
 * This servlet accesses the fabric objects, and then builds an XML dom tree
 * which is placed in the session, and then the browser is redirected to the
 * appropriate jsp.
 *
 * How anyone could think this is the right design is beyond me
 */
public class XMLServlet extends HttpServlet {
  XMLServletHelper helper;

  protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    String   url = null;
    Document xml = null;

    String action = request.getParameter("action");
    if (action == null || action.equals("viewBenchmark")) {
      url = "xml-db.jsp";
      xml = helper.dbSummary();
    }
    else if (action.equals("viewAssembly")) {
      url = "xml-as.jsp";
      xml = helper.assemblySummary(request.getParameter("id"));
    }
    else {
      throw new ServletException("Invalid request");
    }

    resuest.getSession(true).setAttribute("xmldata", xml);
    getServletContext().getRequestDispatcher(url).forward(request, response);
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

