package webapp.client;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webapp.Action;

public class ClientServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  private Client client;

  public ClientServlet() {
    client = new Client("");
  }

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String action = request.getParameter("action");

    if (action == null) {

    } else if (action.equals("sethost")) {
      String host = request.getParameter("host");
      if (!client.setHost(host)) {
        request.setAttribute("error", "Could not connect to host");
      }
    } else if (action.equals("clearhost")) {
      client.setHost(null);
    } else if (action.equals("start")) {
      client.startAction(Action.valueOf(request.getParameter("daction")),
          Integer.parseInt(request.getParameter("requests")));
    }

    request.setAttribute("median", client.getMedian());
    request.setAttribute("client", client);
    showJsp(request, response, "/jsp/client.jsp");
  }

  private void showJsp(HttpServletRequest request,
      HttpServletResponse response, String jsp) throws ServletException,
      IOException {
    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(jsp);
    dispatcher.forward(request, response);
  }

}
