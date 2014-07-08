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
package fabric.common;

import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLSocketFactory;

/**
 * A table of <code>SSLSocketFactory</code>s.
 */
public class SSLSocketFactoryTable {
  private static Map<String, SSLSocketFactory> table =
      new HashMap<String, SSLSocketFactory>();

  public static void register(String hostname, SSLSocketFactory factory) {
    table.put(hostname, factory);
  }

  public static SSLSocketFactory get(String hostname) {
    return table.get(hostname);
  }
}
