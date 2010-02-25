package fabric.common.net;

import java.net.Socket;

import fabric.common.util.Pair;
import fabric.lang.Principal;

public final class UnauthenticatedParams implements SubSocketParams {

  public Pair<Socket, Principal> accept(Socket sock) {
    throw new NotImplementedException();
  }

  public Pair<Socket, Principal> connect(String name) {
    throw new NotImplementedException();
  }

}
