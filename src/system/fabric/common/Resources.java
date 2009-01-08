/**
 * 
 */
package fabric.common;

import java.io.*;
import java.util.Map;
import java.util.Properties;
import java.util.logging.LogManager;

/**
 * Manages the loading of configuration files
 */
public class Resources {
  public static String relpathRewrite(String...relpath) {
    String filename = prefix;
    for (String name : relpath)
      filename += File.separator + name;
    
    return filename;
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

  /**
   * A class for loading the configuration for java.util.Logger.
   */
  public static class LogConfigLoader {
    public LogConfigLoader() {
      String configFile = System.getProperty("fabric.logging.config.file");
      if (configFile == null) return;

      // Make the filename absolute if it isn't already.
      if (!new File(configFile).isAbsolute()) {
        configFile = prefix + File.separator + configFile;
      }

      // Read the configuration.
      try {
        InputStream in = new FileInputStream(configFile);
        Properties p = new Properties();
        p.load(in);
        in.close();

        // Make the log filename absolute if it isn't already.
        final String key = "java.util.logging.FileHandler.pattern";
        String logFile = p.getProperty(key);
        if (logFile != null && !new File(logFile).isAbsolute()) {
          p.setProperty(key, prefix + File.separator + logFile);
        }

        // Load the properties into the LogManager. ugh.
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream pout = new PrintStream(out);
        for (Map.Entry<Object, Object> entry : p.entrySet()) {
          pout.println(entry.getKey() + " = " + entry.getValue());
        }
        pout.flush();
        out.flush();

        LogManager.getLogManager().readConfiguration(
            new ByteArrayInputStream(out.toByteArray()));
      } catch (IOException e) {
      }

    }
  }
}
