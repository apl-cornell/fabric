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
/**
 * 
 */
package fabric.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Manages the loading of configuration files
 */
public class Resources {
  public static String relpathRewrite(String... relpath) {
    String filename = prefix;
    for (String name : relpath)
      filename += File.separator + name;

    return filename;
  }

  public static File getFile(String... relpath) {
    return new File(relpathRewrite(relpath));
  }

  public static InputStream readFile(String... relpath) throws IOException {
    return new FileInputStream(relpathRewrite(relpath));
  }

  public static OutputStream writeFile(String... relpath) throws IOException {
    return new FileOutputStream(relpathRewrite(relpath));
  }

  private static final String prefix;

  static {
    // Read FABRIC_HOME environment variable.
    String fabHome = System.getenv("FABRIC_HOME");
    if (fabHome == null) fabHome = ".";
    prefix = System.getProperty("fabric.prefix", fabHome);
  }
}
