package fabric.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.Principal;

import fabric.common.exceptions.NoSuchNodeError;

/**
 * Abstracts remote cores and remote clients.
 */
public interface RemoteNode {
  /**
   * @return the node's hostname.
   */
  String name();

  /**
   * @param useSSL
   *          whether SSL is being used. This is ignored if the node type
   *          doesn't support non-SSL connections.
   * @return true iff there is an active connection with the node.
   */
  boolean isConnected(boolean useSSL);

  /**
   * <p>
   * Establishes a connection with a node at the given host. A helper for
   * <code>Message.send(Core)</code>.
   * </p>
   * 
   * @param useSSL
   *          Whether SSL is being used. This is ignored if the node type
   *          doesn't support non-SSL connections.
   * @param addr
   *          The host to connect to.
   * @param remotePrincipal
   *          The principal associated with the node we're connecting to.
   * @throws NoSuchNodeError
   *           If the node is not hosted at the given address.
   * @throws IOException
   *           If there was an I/O error.
   */
  void connect(boolean useSSL, InetSocketAddress addr, Principal remotePrincipal)
      throws NoSuchNodeError, IOException;

  /**
   * @param useSSL
   *          Whether SSL is being used. This is ignored if the node type
   *          doesn't support non-SSL connections.
   * @return the data input stream to use for communicating with the node.
   */
  DataInputStream dataInputStream(boolean useSSL);

  /**
   * @param useSSL
   *          Whether SSL is being used. This is ignored if the node type
   *          doesn't support non-SSL connections.
   * @return the data output stream to use for communicating with the node.
   */
  DataOutputStream dataOutputStream(boolean useSSL);
}
