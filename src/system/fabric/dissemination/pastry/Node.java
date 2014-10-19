package fabric.dissemination.pastry;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import rice.environment.Environment;
import rice.environment.params.Parameters;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;
import fabric.common.Logging;
import fabric.dissemination.Cache;

/**
 * Represents a pastry node with a dissemination process. Creating an instance
 * of this class will set up a pastry node on the local machine, attempt to join
 * a pastry ring, and start a disseminator application associated with the
 * pastry node created.
 */
public class Node {
  // should be in params file?
  private static int DEFAULT_PORT = 13373;

  private static String PASTRY_CONFIG_KEY_PREFIX =
      "fabric.dissemination.pastry.";

  protected Environment env;
  protected SocketPastryNodeFactory pnf;
  protected PastryNode node;
  protected Disseminator disseminator;

  public Node(Properties dissemConfig, Cache cache) throws IOException {
    env = new Environment();

    // Some default environment values.
    Map<String, String> defaults = new HashMap<>();
    defaults.put("bootstrap", "localhost:" + DEFAULT_PORT);
    defaults.put("loglevel", "SEVERE");
    defaults.put("replication_interval", "300000");
    defaults.put("aggregation_interval", "600000");

    // Load values from dissemConfig into the parameters for the Pastry
    // environment.
    Parameters params = env.getParameters();
    for (String key : dissemConfig.stringPropertyNames()) {
      if (!key.startsWith(PASTRY_CONFIG_KEY_PREFIX)) continue;

      String value = dissemConfig.getProperty(key);
      key = key.substring(PASTRY_CONFIG_KEY_PREFIX.length());
      params.setString(key, value);
      defaults.remove(key);
    }

    // Load defaults for any keys that weren't specified in dissemConfig.
    for (Map.Entry<String, String> entry : defaults.entrySet()) {
      params.setString(entry.getKey(), entry.getValue());
    }

    String bootstrap = params.getString("bootstrap");
    String[] parts = bootstrap.split(":");
    String bootHost = parts.length > 0 ? parts[0] : null;

    // convert "localhost" into actual host name. needed to work on windows
    if (bootHost.equals("localhost"))
      bootHost = InetAddress.getLocalHost().getHostName();
    int bootPort =
        parts.length == 2 ? Integer.parseInt(parts[1]) : DEFAULT_PORT;
    InetSocketAddress boot = new InetSocketAddress(bootHost, bootPort);

    NodeIdFactory idf = new RandomNodeIdFactory(env);
    pnf = new SocketPastryNodeFactory(idf, DEFAULT_PORT, env);

    node = pnf.newNode(pnf.getNodeHandle(boot));
    waitForReady(); // waits until the pastry node is actually set up

    disseminator = new Disseminator(node, cache);
  }

  /** Returns the disseminator application of this node. */
  public Disseminator disseminator() {
    return disseminator;
  }

  private void waitForReady() throws IOException {
    int spinCount = 0;
    final long startTime = System.currentTimeMillis();
    synchronized (node) {
      while (!node.isReady() && !node.joinFailed()) {
        try {
          node.wait(500);
        } catch (InterruptedException e) {
          Logging.logIgnoredInterruptedException(e);
        }
        if (++spinCount == 2) {
          System.out.println("Waiting for Pastry node to start up...");
//          System.out.println("If this takes too long, consider setting or "
//              + "reducing the value of the");
//          System.out.println("  " + PASTRY_CONFIG_KEY_PREFIX
//              + "pastry_protocol_" + "periodicLeafSet_lease_period");
//          System.out.println("configuration parameter.");
        }
        if (spinCount % 20 == 0) {
          if (System.currentTimeMillis() - startTime > 60000) {
            // XXX
            System.out.println("Waited 60 seconds, and still nothing. "
                + "Giving up and killing worker.");
            System.exit(1);
          }
          System.out.println("Still waiting...");
        }
      }

      if (node.joinFailed()) {
        throw new IOException("Network error: "
            + "Could not join Pastry network. (" + node.joinFailedReason()
            + ")");
      }
    }

    System.out.println("Pastry node started");
  }

  /**
   * Shuts down and destroys this node.
   */
  public void destroy() {
    env.destroy();
  }

}
