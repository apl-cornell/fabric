package fabric.common.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface _Socket<
  InitiatorAddress, ReceiverAddress,
  ServerSocket        extends _ServerSocket        <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  ServerSocketFactory extends _ServerSocketFactory <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  Socket              extends _Socket              <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  SocketFactory       extends _SocketFactory       <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>
> {
    /** @see java.net.Socket#close() */
    public void close() throws IOException;

    /** @see java.net.Socket#connect(SocketAddress) */
    public void connect(InitiatorAddress addr) throws IOException;

    /** @see java.net.Socket#getOutputStream() */
    public OutputStream getOutputStream() throws IOException;
    
    /** @see java.net.Socket#getInputStream() */
    public InputStream getInputStream() throws IOException;
}
