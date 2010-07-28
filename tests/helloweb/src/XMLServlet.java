import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import fabric.worker.Worker;
import fabric.worker.Store;

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

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
    String   url = null;
    Document xml = null;

    String action = request.getParameter("action");
    if (action == null || action.equals("viewBenchmark")) {
      url = "/xml-db.jsp";
      xml = helper.dbSummary();
    }
    else if (action.equals("viewAssembly")) {
      url = "/xml-as.jsp";
      xml = helper.assemblySummary(request.getParameter("id"));
    }
    else {
      throw new ServletException("Invalid request");
    }

    request.getSession(true).setAttribute("xmldata", xml);
    try {
      ServletContext context = getServletContext();
      RequestDispatcher rd   = context.getRequestDispatcher(url);
      rd.forward(request, response);
    } catch (IOException e) {
      throw new ServletException(e);
    }
  }
  
  @Override
  public void init(ServletConfig context) throws ServletException {
    super.init(context);
    try {
      System.setProperty("fabric.prefix", context.getInitParameter("fabric-prefix"));
      try {
        Worker.initialize(null);
      } catch(IllegalStateException e) {
        // TODO: need to fix this up
        // do nothing, worker already initialized.
      }
      Worker worker = Worker.getWorker();
      Store  local  = worker.getLocalStore();

      this.helper = XMLServletHelper.$Impl.create(local, context.getInitParameter("uri"));
    } catch (final Exception e) {
      throw new ServletException(e);
    }
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

