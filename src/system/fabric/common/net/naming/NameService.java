/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
package fabric.common.net.naming;

import java.io.IOException;

import fabric.common.ConfigProperties;

public interface NameService {
  /**
   * Create a socket address suitable for connecting to.
   */
  SocketAddress resolve(String name, PortType portType) throws IOException;

  /** Create a socket address suitable for listening on. */
  SocketAddress localResolve(String name, PortType portType) throws IOException;

  public enum PortType {
    WORKER {
      @Override
      int getPort(ConfigProperties p) {
        return p.workerPort;
      }

      @Override
      public String dnsKey() {
        return "fabric-worker";
      }

      @Override
      public String toString() {
        return "WORKER";
      }
    },
    STORE {
      @Override
      int getPort(ConfigProperties p) {
        return p.storePort;
      }

      @Override
      public String dnsKey() {
        return "fabric-store";
      }

      @Override
      public String toString() {
        return "STORE";
      }
    },
    ;

    abstract String dnsKey();

    abstract int getPort(ConfigProperties p);
  }
}
