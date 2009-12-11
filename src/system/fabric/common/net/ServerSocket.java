package fabric.common.net;

import java.io.IOException;
import java.net.InetSocketAddress;

public interface ServerSocket<
  _ServerSocket        extends ServerSocket        <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _ServerSocketFactory extends ServerSocketFactory <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _Socket              extends Socket              <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _SocketFactory       extends SocketFactory       <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>
> {
    /** @see java.net.ServerSocket#accept() */
    public _Socket accept() throws IOException;
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress) */
    public void bind(int port) throws IOException;
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress, int) */
    public void bind(int port, int backlog) throws IOException;
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress) */
    public void bind(InetSocketAddress addr) throws IOException;
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress, int) */
    public void bind(InetSocketAddress addr, int backlog) throws IOException;
    
    /** @see java.net.ServerSocket#close() */
    public void close() throws IOException;
}
