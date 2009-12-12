package fabric.common.net;

import java.io.IOException;

public interface _SocketFactory<
  InitiatorAddress, ReceiverAddress,
  ServerSocket        extends _ServerSocket        <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  ServerSocketFactory extends _ServerSocketFactory <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  Socket              extends _Socket              <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  SocketFactory       extends _SocketFactory       <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>
> {
    /** @see javax.net.SocketFactory#createSocket() */
    public Socket createSocket();
    
    /** @see javax.net.SocketFactory#createSocket(InetAddress, int) */
    public Socket createSocket(InitiatorAddress addr) throws IOException;
}
