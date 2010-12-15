package fabric.worker;

import java.io.PrintStream;

import fabric.common.Timing;
import fabric.common.exceptions.TerminationException;
import fabric.common.exceptions.UsageError;


public class Options extends fabric.common.Options {
  // The application to run and its parameters.
  public String[] app;

  // This worker's name.
  public String name;

  // If creating a new principal, this is the store on which the principal will
  // be created; otherwise, this is null.
  public String store;

  public int threadPool;
  public int maxConnect;

  private Options() {
  }

  public Options(String[] args) throws UsageError {
    super(args);
  }

  @Override
  public void setDefaultValues() {
    this.name = System.getenv("HOSTNAME");
    this.app = null;
    this.store = null;
    this.threadPool = 10;
    this.maxConnect = 25;
  }

  @Override
  public void validateOptions() throws UsageError {
    if (null == this.name)
      throw new UsageError("No worker name specified");
  }
  
  public static void usage(PrintStream out) {
    Options defaults = new Options();

    out.println("Usage: fab [options] [app] [param...]");
    out.println("where");
    out.println("  [app] is the name of Fabric application's main class");
    out.println("  [param...] are the parameters to the Fabric application");
    out.println("and [options] includes:");
    usageForFlag(out, "--name <name>", "this worker's name", "$HOSTNAME");
    usageForFlag(out, "--pool <number>", "size of pool of threads for "
        + "serving remote requests", defaults.threadPool);
    usageForFlag(out, "--time <category>", "enable timing of category");
    usageForFlag(out, "--conn <number>", "maximum number of simultaneous "
        + "connections to support", defaults.maxConnect);
    usageForFlag(out, "--make-principal <store>",
        "create a new principal for this worker on the given store and exit");
    usageForFlag(out, "--version", "print version info and exit");
    usageForFlag(out, "--help", "print this message");
  }
  
  private String timeUsage() {
    StringBuffer message = new StringBuffer("possible categories for --time:\n");
    message.append("all");
    for (Timing t : Timing.values()) {
      message.append(", ");
      message.append(t.name().toLowerCase());
    }
    return message.toString();
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
      this.store = args[i + 1];
      return i + 2;
    }
    
    if (args[i].equals("--time")) {
      if (i + 1 >= args.length) {
        System.out.println(timeUsage());
        throw new UsageError("Invalid timing category");
      }
      
      for (Timing t : Timing.values()) {
        if (t.name().equalsIgnoreCase(args[i + 1])) {
          t.enabled = true;
          return i + 2;
        }
      }
      if (args[i + 1].equalsIgnoreCase("all")) {
        for (Timing t : Timing.values())
          t.enabled = true;
        return i + 2;
      }
      
      System.out.println(timeUsage());
      throw new UsageError("Invalid timing category");
    }

    this.app = new String[args.length - i];
    for (int idx = i; idx < args.length; idx++)
      this.app[idx - i] = args[idx];

    return args.length;
  }
}
