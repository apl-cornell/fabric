package fabric.common.net;

import java.io.IOException;

public interface _ServerSocket<
  InitiatorAddress, ReceiverAddress,
  ServerSocket        extends _ServerSocket        <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  ServerSocketFactory extends _ServerSocketFactory <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  Socket              extends _Socket              <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  SocketFactory       extends _SocketFactory       <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>
> {
    /** @see java.net.ServerSocket#accept() */
    public Socket accept() throws IOException;
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress) */
    public void bind(ReceiverAddress addr) throws IOException;
    
    /** @see java.net.ServerSocket#bind(java.net.SocketAddress, int) */
    public void bind(ReceiverAddress addr, int backlog) throws IOException;
    
    /** @see java.net.ServerSocket#close() */
    public void close() throws IOException;
}
