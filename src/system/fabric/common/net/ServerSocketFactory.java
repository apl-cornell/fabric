package fabric.common.net;

import java.io.IOException;
import java.net.InetAddress;

public interface ServerSocketFactory<
  _ServerSocket        extends ServerSocket        <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _ServerSocketFactory extends ServerSocketFactory <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _Socket              extends Socket              <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _SocketFactory       extends SocketFactory       <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>> {

    /** Creates a new ServerSocketFactory decorating the given
     * ServerSocketFactory.
     * 
     * @param factory the ServerSocketFactory that will be used to create the
     *        ServerSockets used to implement ServerSockets returned by this
     */
    
    /** @see javax.net.ServerSocketFactory#createServerSocket() */
    public _ServerSocket createServerSocket();
    
    /** @see javax.net.ServerSocketFactory#createServerSocket(int) */
    public _ServerSocket createServerSocket(int port) throws IOException;

    /** @see javax.net.ServerSocketFactory#createServerSocket(int, int) */
    public _ServerSocket createServerSocket(int port, int backlog) throws IOException;

    /** @see javax.net.ServerSocketFactory#createServerSocket(int, int, InetAddress) */
    public _ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException;
    
}
