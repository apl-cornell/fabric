package fabric.common.net.naming;

import java.io.IOException;

public interface NameService {
  /**
   * Create a socket address suitable for connecting to.
   */
  SocketAddress resolve(String name) throws IOException;

  /** Create a socket address suitable for listening on. */
  SocketAddress localResolve(String name) throws IOException;
}
