package fabric.store;

import java.io.PrintStream;
import java.util.Set;

import fabric.common.Options.Flag.Kind;
import fabric.common.Resources;
import fabric.common.exceptions.UsageError;

public class Options extends fabric.common.Options {
  /**
   * The name of the store.
   */
  public String storeName;

  private static final int DEFAULT_THREAD_POOL_SIZE = 10;
  public int threadPool;

  private static final int DEFAULT_TIMEOUT = 15;
  public int timeout;

  /**
   * The worker shell command to run.
   */
  public String[] cmd;

  private Options() {
  }

  public Options(String[] args) throws UsageError {
    super(args);
  }

  public static void printUsage(PrintStream out, boolean showSecretMenu) {
    new Options().usage(out, showSecretMenu);
  }

  @Override
  protected void populateFlags(Set<Flag> flags) {
    flags.add(new Flag("--name", "<hostname>", "the name of the store") {
      @Override
      public int handle(String[] args, int index) {
        Options.this.storeName = args[index];
        return index + 1;
      }
    });

    flags.add(new Flag("--pool", "<number>", "size of pool of message-handler "
        + "threads", DEFAULT_THREAD_POOL_SIZE) {
      @Override
      public int handle(String[] args, int index) throws UsageError {
        try {
          Options.this.threadPool = Integer.parseInt(args[index]);
        } catch (NumberFormatException e) {
          throw new UsageError("Invalid argument: " + args[index]);
        }
        return index + 1;
      }
    });

    flags.add(new Flag("--timeout", "<seconds>", "timeout for idle worker "
        + "connections", DEFAULT_TIMEOUT) {
      @Override
      public int handle(String[] args, int index) throws UsageError {
        try {
          Options.this.timeout = new Integer(args[index]).intValue();
        } catch (NumberFormatException e) {
          throw new UsageError("Invalid argument: " + args[index]);
        }
        return index + 1;
      }
    });

    flags.add(new Flag(Kind.DEBUG, "--nossl", null,
        "disables SSL for debugging purposes") {
          @Override
          public int handle(String[] args, int index) {
            fabric.common.Options.DEBUG_NO_SSL = true;
            return index;
          }
        });
  }

  @Override
  public void setDefaultValues() {
    this.storeName = null;
    this.threadPool = DEFAULT_THREAD_POOL_SIZE;
    this.timeout = DEFAULT_TIMEOUT;
    this.cmd = null;
    // Default codeCache is set in validateOptions because it depends on
    // storeName.
  }

  @Override
  public void validateOptions() throws UsageError {
    if (null == storeName) throw new UsageError("No store name specified");
    
    // Default codeCache is set here because it depends on storeName.
    if (null == codeCache)
      this.codeCache = Resources.relpathRewrite("var", storeName + "_cache");
  }

  @Override
  public void usageHeader(PrintStream out) {
    out.println("Usage: fab-store [options] [cmd...]");
    out.println("where");
    out.println("  [cmd...] is a command for the worker shell to execute");
    out.println("and [options] includes:");
  }

  @Override
  protected int defaultHandler(String[] args, int index) {
    this.cmd = new String[args.length - index];
    for (int idx = index; idx < args.length; idx++)
      this.cmd[idx - index] = args[idx];
    
    return args.length;
  }
}
