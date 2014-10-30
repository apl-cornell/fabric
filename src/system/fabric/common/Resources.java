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
