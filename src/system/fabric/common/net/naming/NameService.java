package fabric.common.net.naming;

import java.io.IOException;

import fabric.common.ConfigProperties;

public interface NameService {
  /**
   * Create a socket address suitable for connecting to.
   */
  SocketAddress resolve(String name, PortType portType) throws IOException;

  /** Create a socket address suitable for listening on. */
  SocketAddress localResolve(String name, PortType portType) throws IOException;

  public enum PortType {
    WORKER {
      @Override
      int getPort(ConfigProperties p) {
        return p.workerPort;
      }

      @Override
      public String dnsKey() {
        return "fabric-worker";
      }

      @Override
      public String toString() {
        return "WORKER";
      }
    },
    STORE {
      @Override
      int getPort(ConfigProperties p) {
        return p.storePort;
      }

      @Override
      public String dnsKey() {
        return "fabric-store";
      }

      @Override
      public String toString() {
        return "STORE";
      }
    },
    ;

    abstract String dnsKey();

    abstract int getPort(ConfigProperties p);
  }
}
