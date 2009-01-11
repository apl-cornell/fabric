package fabric.client;

import java.io.PrintStream;
import java.util.Arrays;

import fabric.common.TerminationException;
import fabric.common.UsageError;

class Options extends fabric.common.Options {
  // The application to run and its parameters.
  public String[] app;

  // This client's name.
  public String name;

  public Options(String[] args) throws UsageError {
    parseCommandLine(args);
  }

  @Override
  public void setDefaultValues() {
    this.name = null;
    this.app = null;
  }

  public static void usage(PrintStream out) {
    out.println("Usage: fab [options] [app] [param...]");
    out.println("where");
    out.println("  [app] is the name of Fabric application's main class");
    out.println("  [param...] are the parameters to the Fabric application");
    out.println("and [options] includes:");
    usageForFlag(out, "--name <name>", "this client's name");
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
      this.name = args[i+1];
      return i + 2;
    }
    
    this.app = Arrays.copyOfRange(args, i, args.length);
    return args.length;
  }
}
