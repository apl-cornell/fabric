package fabric.store;

import java.io.PrintStream;

import fabric.common.exceptions.TerminationException;
import fabric.common.exceptions.UsageError;

public class Options extends fabric.common.Options {

  /**
   * The name of the store.
   */
  public String storeName;

  public int threadPool;
  public int timeout;

  private Options() {
  }

  public Options(String[] args) throws UsageError {
    super(args);
  }

  @Override
  public void setDefaultValues() {
    this.storeName  = null;
    this.threadPool = 10;
    this.timeout    = 15;
  }

  @Override
  public void validateOptions() throws UsageError {
    if (null == storeName)
      throw new UsageError("No store specified");
  }

  public static void usage(PrintStream out) {
    Options defaults = new Options();

    out.println("Usage: fab-store [options]");
    out.println("where [options] includes:");
    usageForFlag(out, "--store <hostname>", "The name of the store.");
    usageForFlag(out, "--pool <number>", "size of pool of message-handler "
        + "threads", defaults.threadPool);
    usageForFlag(out, "--timeout <seconds>", "time-out for idle worker "
        + "connections", defaults.timeout);
    usageForFlag(out, "--nossl", "disables SSL for debugging purposes");
    usageForFlag(out, "--version", "print version info");
    usageForFlag(out, "--help", "print this message");
  }

  /**
   * Parse a command.
   * 
   * @return the next index to process. i.e., if calling this method processes
   *         two commands, then the return value should be index+2.
   */
  @Override
  protected int parseCommand(String args[], int index) throws UsageError {
    int i = index;
    if (args[i].equals("-h") || args[i].equals("-help")
        || args[i].equals("--help")) {
      throw new UsageError("", 0);
    }

    if (args[i].equals("--version")) {
      throw new TerminationException(0);
    }

    if (args[i].equals("--store")) {
      i++;
      storeName = args[i];
      return i + 1;
    }

    if (args[i].equals("--pool")) {
      i++;
      try {
        this.threadPool = Integer.parseInt(args[i]);
      } catch (NumberFormatException e) {
        throw new UsageError("Invalid argument: " + args[i]);
      }
      return i + 1;
    }

    if (args[i].equals("--timeout")) {
      i++;
      try {
        this.timeout = new Integer(args[i]).intValue();
      } catch (NumberFormatException e) {
        throw new UsageError("Invalid argument: " + args[i]);
      }
      return i + 1;
    }

    if (args[i].equals("--nossl")) {
      i++;
      DEBUG_NO_SSL = true;

      return i;
    }

    return i;
  }
}
