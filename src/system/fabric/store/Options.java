package fabric.store;

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
   * Maps store names to their associated key and trust stores.
   */
  public Map<String, StoreKeyRepository> stores;

  /**
   * The name of the primary store.
   */
  public String primaryStoreName;

  public int threadPool;
  public int timeout;

  public static class StoreKeyRepository {
    public final KeyStore keyStore;
    public final char[] password;

    public StoreKeyRepository(KeyStore keyStore, char[] password) {
      this.keyStore = keyStore;
      this.password = password;
    }
  }

  private Options() {
  }

  public Options(String[] args) throws UsageError {
    super(args);
  }

  @Override
  public void setDefaultValues() {
    this.port = 3372;
    this.stores = new TreeMap<String, StoreKeyRepository>();
    this.threadPool = 10;
    this.timeout = 15;
    this.primaryStoreName = null;
  }

  @Override
  public void validateOptions() throws UsageError {

    if (null == primaryStoreName) throw new UsageError("No stores specified");

  }

  public static void usage(PrintStream out) {
    Options defaults = new Options();

    out.println("Usage: fab-store [options]");
    out.println("where [options] includes:");
    usageForFlag(out, "--port <number>", "port on which to listen",
        defaults.port);
    usageForFlag(
        out,
        "--store <hostname> <keystore file> <passwd>",
        "participate in the given store with the associated key store. Can be "
            + "specified multiple times. The first store specified will be the "
            + "node's \"primary\" store, and the store node's worker will run "
            + "under the primary store's principal.");
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

    if (args[i].equals("--port")) {
      i++;
      try {
        this.port = new Integer(args[i]).intValue();
      } catch (NumberFormatException e) {
        throw new UsageError("Invalid port number: " + args[i]);
      }
      return i + 1;
    }

    if (args[i].equals("--store")) {
      i++;
      String storeName = args[i];
      String keyFile = args[i + 1];
      char[] passwd = args[i + 2].toCharArray();

      if (this.stores.containsKey(storeName))
        throw new UsageError("Duplicate store: " + args[i]);

      KeyStore keyStore;
      try {
        keyStore = KeyStore.getInstance("JKS");
        keyStore.load(new FileInputStream(keyFile), passwd);
      } catch (KeyStoreException e) {
        throw new InternalError("Unable to open key store.", e);
      } catch (NoSuchAlgorithmException e) {
        throw new InternalError(e);
      } catch (CertificateException e) {
        throw new InternalError("Unable to open key store.", e);
      } catch (FileNotFoundException e) {
        throw new UsageError("File not found: " + e.getMessage());
      } catch (IOException e) {
        if (e.getCause() instanceof UnrecoverableKeyException)
          throw new UsageError("Unable to open key store: invalid password.");
        throw new InternalError("Unable to open key store.", e);
      }

      this.stores.put(storeName, new StoreKeyRepository(keyStore, passwd));

      if (this.primaryStoreName == null) this.primaryStoreName = storeName;

      return i + 3;
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
