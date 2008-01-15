package fabric.core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

import fabric.common.InternalError;
import fabric.common.UsageError;

public class Options {

  public int port;

  /**
   * Maps core names to their associated key and trust stores.
   */
  public Map<String, CoreKeyStores> cores;

  public int threadPool;
  public int maxConnect;
  public int timeout;
  public boolean useSSL;

  public static class CoreKeyStores {
    public final KeyStore keyStore;
    public final KeyStore trustStore;
    public final char[] password;

    public CoreKeyStores(KeyStore keyStore, KeyStore trustStore, char[] password) {
      this.keyStore = keyStore;
      this.trustStore = trustStore;
      this.password = password;
    }
  }

  private Options() {
    setDefaultValues();
  }

  public Options(String[] args) throws UsageError {
    this();
    parseCommandLine(args);
  }

  public void setDefaultValues() {
    this.port = 3372;
    this.cores = new TreeMap<String, CoreKeyStores>();
    this.threadPool = 10;
    this.maxConnect = 25;
    this.timeout = 15;
    this.useSSL = true;
  }

  public static void usage(PrintStream out) {
    Options defaults = new Options();

    out.println("Usage: fabric [options]");
    out.println("where [options] includes:");
    usageForFlag(out, "--port <number>", "port on which to listen",
        defaults.port);
    usageForFlag(out,
        "--core <hostname> <keystore file> <truststore file> <passwd>",
        "participate in the given core with the associated key and trust "
            + "stores. Can be specified multiple times.");
    usageForFlag(out, "--pool <number>", "size of worker thread pool",
        defaults.threadPool);
    usageForFlag(out, "--conn <number>", "maximum number of simultaneous "
        + "connections to support", defaults.maxConnect);
    usageForFlag(out, "--timeout <seconds>", "time-out for idle client "
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
  private int parseCommand(String args[], int index) throws UsageError {
    int i = index;
    if (args[i].equals("-h") || args[i].equals("-help")
        || args[i].equals("--help")) {
      throw new UsageError("", 0);
    }

    if (args[i].equals("--version")) {
      throw new Main.TerminationException(0);
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

    if (args[i].equals("--core")) {
      i++;
      String coreName = args[i];
      String keyFile = args[i + 1];
      String trustFile = args[i + 2];
      char[] passwd = args[i + 3].toCharArray();

      if (this.cores.containsKey(coreName))
        throw new UsageError("Duplicate core: " + args[i]);

      KeyStore keyStore, trustStore;
      try {
        keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keyFile), passwd);

        trustStore = KeyStore.getInstance("JKS");
        trustStore.load(new FileInputStream(trustFile), passwd);
      } catch (KeyStoreException e) {
        throw new InternalError("Unable to open key or trust store.", e);
      } catch (NoSuchAlgorithmException e) {
        throw new InternalError(e);
      } catch (CertificateException e) {
        throw new InternalError("Unable to open key or trust store.", e);
      } catch (FileNotFoundException e) {
        throw new UsageError("File not found: " + e.getMessage());
      } catch (IOException e) {
        if (e.getCause() instanceof UnrecoverableKeyException)
          throw new UsageError(
              "Unable to open key or trust store: invalid password.");
        throw new InternalError("Unable to open key or trust store.", e);
      }

      this.cores.put(coreName, new CoreKeyStores(keyStore,
          trustStore, passwd));

      return i + 4;
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
      this.useSSL = false;
        
      return i;
    }

    return i;
  }

  public void parseCommandLine(String args[]) throws UsageError {
    for (int i = 0; i < args.length;) {
      try {
        int ni = parseCommand(args, i);
        if (ni == i) throw new UsageError("Illegal option: " + args[i]);
        i = ni;
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new UsageError("Missing argument");
      }
    }
  }

  /**
   * The maximum width of a line when printing usage information. Used by
   * <code>usageForFlag</code>.
   */
  protected static final int USAGE_SCREEN_WIDTH = 76;

  /**
   * The number of spaces from the left that the description for flags will be
   * displayed. Used by <code>usageForFlag</code>.
   */
  protected static final int USAGE_FLAG_WIDTH = 27;

  /**
   * Output a flag and a description of its usage in a nice format.
   * 
   * @param out
   *                output PrintStream
   * @param flag
   *                the name of the flag.
   * @param description
   *                description of the flag.
   */
  private static void usageForFlag(PrintStream out, String flag, String desc) {
    out.print("  ");
    out.print(flag);

    // cur is where the cursor is on the screen.
    int cur = flag.length() + 2;

    if (cur < USAGE_FLAG_WIDTH) {
      printSpaces(out, USAGE_FLAG_WIDTH - cur);
    } else {
      // The flag is long. Get a new line before printing the description.
      out.println();
      printSpaces(out, USAGE_FLAG_WIDTH);
    }

    cur = USAGE_FLAG_WIDTH;

    // Break up the description.
    StringTokenizer st = new StringTokenizer(desc);
    while (st.hasMoreTokens()) {
      String s = st.nextToken();
      if (cur + s.length() > USAGE_SCREEN_WIDTH) {
        out.println();
        printSpaces(out, USAGE_FLAG_WIDTH);
        cur = USAGE_FLAG_WIDTH;
      }

      out.print(s);
      cur += s.length();
      if (st.hasMoreTokens()) {
        if (cur + 1 > USAGE_SCREEN_WIDTH) {
          out.println();
          printSpaces(out, USAGE_FLAG_WIDTH);
          cur = USAGE_FLAG_WIDTH;
        } else {
          out.print(" ");
          cur++;
        }
      }
    }

    out.println();
  }

  /**
   * Output a flag and a description of its usage in a nice format.
   * 
   * @param out
   *                output PrintStream
   * @param flag
   *                the name of the flag.
   * @param description
   *                description of the flag.
   * @param defVal
   *                default value
   */
  private static void usageForFlag(PrintStream out, String flag, String desc,
      String defVal) {
    usageForFlag(out, flag, desc + " (default: " + defVal + ")");
  }

  /**
   * Output a flag and a description of its usage in a nice format.
   * 
   * @param out
   *                output PrintStream
   * @param flag
   *                the name of the flag.
   * @param description
   *                description of the flag.
   * @param defVal
   *                default value
   */
  private static void usageForFlag(PrintStream out, String flag, String desc,
      int defVal) {
    usageForFlag(out, flag, desc, new Integer(defVal).toString());
  }

  private static void printSpaces(PrintStream out, int n) {
    while (n-- > 0)
      out.print(' ');
  }
}
