package fabric.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;
import java.util.logging.Logger;


import fabric.common.Resources;
import fabric.common.Version;
import fabric.common.exceptions.TerminationException;
import fabric.common.exceptions.UsageError;

// TODO: load resources

public class Main {
  public static void main(String[] args) {
    try {
      start(args);
    } catch (TerminationException te) {
      if (te.getMessage() != null)
        (te.exitCode == 0 ? System.out : System.err).println(te.getMessage());

      System.exit(te.exitCode);
    }
  }

  public static void start(String args[]) {
    try {
      InputStream in = Resources.readFile("etc", "core.properties");
      Properties p = new Properties(System.getProperties());
      p.load(in);
      in.close();

      String loggingProperties = p.getProperty("java.util.logging.config.file");
      if (loggingProperties != null) {
        p.setProperty("java.util.logging.config.file", Resources
            .relpathRewrite(loggingProperties));
      }

      System.setProperties(p);
    } catch (final IOException exc) {
      System.err.println("Failed to load core configuration file");
      exc.printStackTrace();
    }
    
    Logger logger = Logger.getLogger("fabric.core");
    
    logger.info("Core node");
    logger.config("Fabric version " + new Version());
    logger.info("");

    // Parse the command-line options.
    Options opts;
    try {
      opts = new Options(args);
    } catch (UsageError ue) {
      PrintStream out = ue.exitCode == 0 ? System.out : System.err;
      if (ue.getMessage() != null && ue.getMessage().length() > 0) {
        out.println(ue.getMessage());
        out.println();
      }

      Options.usage(out);
      throw new TerminationException(ue.exitCode);
    }
    
    // Start up core-node services.
    try {
      Node node = new Node(opts);
      node.start();
    } catch (IOException e) {
      throw new TerminationException(e.getMessage(), 1);
    }

  }
}
