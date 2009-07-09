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
import java.util.TreeMap;

import fabric.common.exceptions.InternalError;
import fabric.common.exceptions.TerminationException;
import fabric.common.exceptions.UsageError;

public class Options extends fabric.common.Options {

  public int port;

  /**
   * Maps core names to their associated key and trust stores.
   */
  public Map<String, CoreKeyStores> cores;

  /**
   * The name of the primary core.
   */
  public String primaryCoreName;

  public int threadPool;
  public int timeout;

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
  }

  public Options(String[] args) throws UsageError {
    parseCommandLine(args);
  }

  @Override
  public void setDefaultValues() {
    this.port = 3372;
    this.cores = new TreeMap<String, CoreKeyStores>();
    this.threadPool = 10;
    this.timeout = 15;
    this.primaryCoreName = null;
  }

  public static void usage(PrintStream out) {
    Options defaults = new Options();

    out.println("Usage: fab-core [options]");
    out.println("where [options] includes:");
    usageForFlag(out, "--port <number>", "port on which to listen",
        defaults.port);
    usageForFlag(out,
        "--core <hostname> <keystore file> <truststore file> <passwd>",
        "participate in the given core with the associated key and trust "
            + "stores. Can be specified multiple times. The first core "
            + "specified will be the node's \"primary\" core, and the core "
            + "node's client will run under the primary core's principal.");
    usageForFlag(out, "--pool <number>", "size of worker-thread pool",
        defaults.threadPool);
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
      
      if (this.primaryCoreName == null) this.primaryCoreName = coreName;

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
