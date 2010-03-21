package webapp.blog;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class BlogServer {
  public static void main(String[] args) throws Exception {
    BlogServer.startServer();
  }

  public static void startServer() throws Exception {
    Diagnostics.initializeFabric();
    String jetty_home = System.getProperty("jetty.home", "./bin/fabil");

    LocalStore store = Diagnostics.getLocalStore();
    
    Server server = null;
    
    atomic {
      server = new Server@store(8080);
      WebAppContext webapp = new WebAppContext@store();
      webapp.setContextPath("/");
      webapp.setWar(jetty_home + "/web");
      webapp.addServlet(BlogServlet.class, "/web");
      server.setHandler(webapp);
    }
    
    System.out.println("Starting server...");
    server.start();
    System.out.println("Server started");
    server.join();

  }

}
