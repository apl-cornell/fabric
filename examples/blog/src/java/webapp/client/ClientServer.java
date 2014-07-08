/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
