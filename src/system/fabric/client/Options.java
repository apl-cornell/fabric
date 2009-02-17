package fabric.client;

import java.io.PrintStream;

import fabric.common.TerminationException;
import fabric.common.UsageError;

public class Options extends fabric.common.Options {
  // The application to run and its parameters.
  public String[] app;

  // This client's name.
  public String name;

  // If creating a new principal, this is the core on which the principal will
  // be created; otherwise, this is null.
  public String core;

  public int port;

  public int threadPool;
  public int maxConnect;

  private Options() {
  }

  public Options(String[] args) throws UsageError {
    parseCommandLine(args);
  }

  @Override
  public void setDefaultValues() {
    this.name = "client0"; // XXX this is for debugging convenience; should be
    // null
    this.app = null;
    this.core = null;
    this.port = 3373;
    this.threadPool = 10;
    this.maxConnect = 25;
  }

  public static void usage(PrintStream out) {
    Options defaults = new Options();

    out.println("Usage: fab [options] [app] [param...]");
    out.println("where");
    out.println("  [app] is the name of Fabric application's main class");
    out.println("  [param...] are the parameters to the Fabric application");
    out.println("and [options] includes:");
    usageForFlag(out, "--name <name>", "this client's name", defaults.name);
    usageForFlag(out, "--port <number>",
        "port on which to listen for remote requests", defaults.port);
    usageForFlag(out, "--pool <number>", "size of worker thread pool for "
        + "serving remote requests", defaults.threadPool);
    usageForFlag(out, "--conn <number>", "maximum number of simultaneous "
        + "connections to support", defaults.maxConnect);
    usageForFlag(out, "--make-principal <core>",
        "create a new principal for this client on the given core and exit");
    usageForFlag(out, "--version", "print version info and exit");
    usageForFlag(out, "--help", "print this message");
  }

  @Override
  protected int parseCommand(String[] args, int index) throws UsageError {
    int i = index;
    if (args[i].equals("-h") || args[i].equals("-help")
        || args[i].equals("--help")) {
      throw new UsageError("", 0);
    }

    if (args[i].equals("--version")) {
      throw new TerminationException(0);
    }

    if (args[i].equals("--name")) {
      this.name = args[i + 1];
      return i + 2;
    }

    if (args[i].equals("--port")) {
      i++;
      try {
        this.port = new Integer(args[i]).intValue();
      } catch (NumberFormatException e) {
        throw new UsageError("Invalid port number: " + args[i]);
      }
      return i + 1;
    }

    if (args[i].equals("--pool")) {
      i++;
      try {
        this.threadPool = new Integer(args[i]).intValue();
      } catch (NumberFormatException e) {
        throw new UsageError("Invalid argument: " + args[i]);
      }
      return i + 1;
    }

    if (args[i].equals("--conn")) {
      i++;
      try {
        this.maxConnect = new Integer(args[i]).intValue();
      } catch (NumberFormatException e) {
        throw new UsageError("Invalid argument: " + args[i]);
      }
      return i + 1;
    }

    if (args[i].equals("--make-principal")) {
      this.core = args[i + 1];
      return i + 2;
    }

    this.app = new String[args.length - i];
    for (int idx = i; idx < args.length; idx++)
      this.app[idx - i] = args[idx];

    return args.length;
  }
}
