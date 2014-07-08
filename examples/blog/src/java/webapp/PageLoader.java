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
package webapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PageLoader {

  public static PageStats getPageBenchmark(String page) throws IOException {
    URL url = new URL(page);
    URLConnection yc = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(
        yc.getInputStream()));
    String inputLine;
    String last = null;
    String second = null;
    while ((inputLine = in.readLine()) != null) {
      second = last;
      last = inputLine;
    }
    in.close();

    return PageStats.fromSerialized(second);
  }

  public static String getPage(String page) throws IOException {
    URL url = new URL(page);
    URLConnection yc = url.openConnection();
    BufferedReader in = new BufferedReader(new InputStreamReader(
        yc.getInputStream()));
    String inputLine;
    String total = "";
    while ((inputLine = in.readLine()) != null)
      total += inputLine + "\n";
    in.close();

    return total;
  }
}
