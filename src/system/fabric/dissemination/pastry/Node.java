package fabric.dissemination.pastry;

import java.io.IOException;

import rice.environment.Environment;
import rice.pastry.NodeHandle;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

/**
 * Represents a pastry node with a dissemination process. Creating an instance
 * of this class will set up a pastry node on the local machine, attempt to
 * join a pastry ring, and start a disseminator application associated with the
 * pastry node created.
 */
public class Node {

  protected Environment env;
  protected SocketPastryNodeFactory pnf;
  protected PastryNode node;
  protected Disseminator disseminator;

  /**
   * Creates a node and joins an existing ring.
   * 
   * @param bootstrap the NodeHandle of the bootstrap node.
   * @throws IOException if a network error occurred.
   */
  public Node(NodeHandle bootstrap) throws IOException {
    env = new Environment();
    NodeIdFactory idf = new RandomNodeIdFactory(env);
    pnf = new SocketPastryNodeFactory(idf, 3373, env);

    node = pnf.newNode(bootstrap);
    waitForReady();

    disseminator = new Disseminator(node);
  }
  
  /**
   * Creates a node without joining an existing ring (starts its own ring).
   * 
   * @throws IOException if a network error occurred.
   */
  public Node() throws IOException {
    this(null);
  }

  /**
   * Returns the disseminator application of this node.
   * 
   * @return the disseminator of this node.
   */
  public Disseminator disseminator() {
    return disseminator;
  }

  private void waitForReady() throws IOException {
    synchronized (node) {
      while (!node.isReady() && !node.joinFailed()) {
        try {
          node.wait(500);
        } catch (InterruptedException e) {}
      }

      if (node.joinFailed()) {
        throw new IOException("Network error: "
            + "Could not join Pastry network.");
      }
    }
  }

  /**
   * Shuts down and destroys this node.
   */
  public void destroy() {
    env.destroy();
  }

  // just for testing
  public static void main(String[] args) throws IOException {
    new Node().destroy();
  }

}
