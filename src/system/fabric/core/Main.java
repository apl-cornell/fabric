package fabric.core;

import java.io.IOException;
import java.io.PrintStream;


import fabric.common.UsageError;
import fabric.common.Version;

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
    System.out.println("Fabric version " + new Version());
    System.out.println("Core node");
    System.out.println();

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

  /**
   * This exception signals termination of the core node. It should be used
   * instead of <code>System.exit</code> to allow Fabric to be started from
   * within a JVM that wasn't started specifically for Fabric, e.g., the Apache
   * ANT framework.
   */
  public static class TerminationException extends RuntimeException {
    final public int exitCode;

    public TerminationException(String msg) {
      this(msg, 1);
    }

    public TerminationException(int exit) {
      this.exitCode = exit;
    }

    public TerminationException(String msg, int exit) {
      super(msg);
      this.exitCode = exit;
    }

    public TerminationException(Exception cause, int exit) {
      super(cause);
      this.exitCode = exit;
    }
  }
}
