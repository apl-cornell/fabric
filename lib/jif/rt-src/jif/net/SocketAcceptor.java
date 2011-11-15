package jif.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Jif classes can implement this interface, and use the convenience method
 * SocketUtil.acceptConnections.
 */
public interface SocketAcceptor {
    void accept(InputStream input, OutputStream output) throws IOException;
}
