/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
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
