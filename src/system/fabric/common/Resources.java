/**
 * 
 */
package fabric.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Manages the loading of configuration files
 */
public class Resources {
  public static InputStream readFile(String... relpath) throws IOException {
    String filename = prefix;
    for (String name : relpath)
      filename += File.separator + name;
    
    return new FileInputStream(filename);
  }
  
  private static final String prefix = System.getProperty("fabric.prefix", ".");
}
