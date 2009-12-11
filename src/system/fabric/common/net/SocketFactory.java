package fabric.common.net;

import java.io.IOException;
import java.net.InetAddress;

public interface SocketFactory<
  _ServerSocket        extends ServerSocket        <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _ServerSocketFactory extends ServerSocketFactory <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _Socket              extends Socket              <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _SocketFactory       extends SocketFactory       <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>
> {
    /** @see javax.net.SocketFactory#createSocket() */
    public _Socket createSocket();
    
    /** @see javax.net.SocketFactory#createSocket(String, int) */
    public _Socket createSocket(String host, int port) throws IOException;

    /** @see javax.net.SocketFactory#createSocket(InetAddress, int) */
    public _Socket createSocket(InetAddress host, int port) throws IOException;
}
