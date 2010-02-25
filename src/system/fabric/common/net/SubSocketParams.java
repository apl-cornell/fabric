package fabric.common.net;

import java.net.Socket;
import fabric.common.util.Pair;
import fabric.lang.Principal;

interface SubSocketParams {
  /** Perform the outgoing lookup, connection, and handshake. */
  Pair<Socket, Principal> connect(String name);
  
  /** Perform the incoming handshake. */
  Pair<Socket, Principal> accept (Socket sock);
}
