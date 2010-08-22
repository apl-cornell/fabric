package webapp.client;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class ClientServer {
  public static void main(String[] args) throws Exception {
    new ClientServer(args.length > 0 ? args[0] : null);

  }

  public static int port = 9000;

  public ClientServer(String portStr) throws Exception {
    String jetty_home = System.getProperty("jetty.home", "./classes/java");

    port = Integer.parseInt(System.getProperty("port", Integer.toString(port)));
    port = portStr != null ? Integer.parseInt(portStr) : port;
    Server server = new Server(port);

    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    webapp.setWar(jetty_home + "/web");
    webapp.addServlet(ClientServlet.class, "/web");
    server.setHandler(webapp);

    server.start();
    server.join();

  }
}
