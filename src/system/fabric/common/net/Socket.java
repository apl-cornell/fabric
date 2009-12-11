package fabric.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public interface Socket<
  _ServerSocket        extends ServerSocket        <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _ServerSocketFactory extends ServerSocketFactory <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _Socket              extends Socket              <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>,
  _SocketFactory       extends SocketFactory       <_ServerSocket, _ServerSocketFactory, _Socket, _SocketFactory>
> {
    /** @see java.net.Socket#close() */
    public void close() throws IOException;

    /** @see java.net.Socket#connect(SocketAddress) */
    public void connect(InetSocketAddress addr) throws IOException;

    /** @see java.net.Socket#getOutputStream() */
    public OutputStream getOutputStream() throws IOException;
    
    /** @see java.net.Socket#getInputStream() */
    public InputStream getInputStream() throws IOException;
}
