package fabric.dissemination.pastry;

import java.io.IOException;

import rice.environment.Environment;
import rice.pastry.NodeHandle;
import rice.pastry.NodeIdFactory;
import rice.pastry.PastryNode;
import rice.pastry.socket.SocketPastryNodeFactory;
import rice.pastry.standard.RandomNodeIdFactory;

public class Node {

  protected Environment env;
  protected SocketPastryNodeFactory pnf;
  protected PastryNode node;
  protected Disseminator disseminator;

  public Node() throws IOException {
    env = new Environment();
    NodeIdFactory idf = new RandomNodeIdFactory(env);
    pnf = new SocketPastryNodeFactory(idf, 3373, env);

    // TODO get bootstrap node
    NodeHandle bootstrap = null;
    node = pnf.newNode(bootstrap);
    waitForReady();

    disseminator = new Disseminator(node);
  }

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

  public void destroy() {
    env.destroy();
  }

  // just for testing
  public static void main(String[] args) throws IOException {
    new Node().destroy();
  }

}
