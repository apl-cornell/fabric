package webapp.blog;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class BlogServer {
  public static void main(String[] args) throws Exception {
    new BlogServer();

  }

  public BlogServer() throws Exception {
    String jetty_home = System.getProperty("jetty.home", "./classes");

    Server server = new Server(8080);

    WebAppContext webapp = new WebAppContext();
    webapp.setContextPath("/");
    webapp.setWar(jetty_home + "/web");
    webapp.addServlet(BlogServlet.class, "/web");
    server.setHandler(webapp);

    server.start();
    server.join();

  }

}
