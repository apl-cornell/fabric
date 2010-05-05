package fabric.store;

import java.io.IOException;
import java.io.PrintStream;
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
    System.setProperty("java.util.logging.config.file",
                       Resources.relpathRewrite("etc", "logging.properties"));
    Logger logger = Logger.getLogger("fabric.store");
    
    logger.info("Store node");
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
    
    // Start up store-node services.
    try {
      Node node = new Node(opts);
      node.start();
    } catch (IOException e) {
      throw new TerminationException(e.getMessage(), 1);
    }

  }
}
