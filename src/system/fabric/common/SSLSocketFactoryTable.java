package fabric.common;

import java.util.Map;
import java.util.HashMap;

import javax.net.ssl.SSLSocketFactory;

/**
 * A table of <code>SSLSocketFactory</code>s.
 */
public class SSLSocketFactoryTable {
  private static Map<String, SSLSocketFactory> table =
      new HashMap<String, SSLSocketFactory>();

  public static void register(String hostname, SSLSocketFactory factory) {
    table.put(hostname, factory);
  }

  public static SSLSocketFactory get(String hostname) {
    return table.get(hostname);
  }
}
