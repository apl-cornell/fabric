package fabric.common.net;

import java.io.IOException;

public interface _ServerSocketFactory<
  InitiatorAddress, ReceiverAddress,
  ServerSocket        extends _ServerSocket        <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  ServerSocketFactory extends _ServerSocketFactory <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  Socket              extends _Socket              <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>,
  SocketFactory       extends _SocketFactory       <InitiatorAddress, ReceiverAddress, ServerSocket, ServerSocketFactory, Socket, SocketFactory>
> {

    /** Creates a new ServerSocketFactory decorating the given
     * ServerSocketFactory.
     * 
     * @param factory the ServerSocketFactory that will be used to create the
     *        ServerSockets used to implement ServerSockets returned by this
     */
    
    /** @see javax.net.ServerSocketFactory#createServerSocket() */
    public ServerSocket createServerSocket();
    
    /** @see javax.net.ServerSocketFactory#createServerSocket(int) */
    public ServerSocket createServerSocket(ReceiverAddress addr) throws IOException;

    /** @see javax.net.ServerSocketFactory#createServerSocket(int, int) */
    public ServerSocket createServerSocket(ReceiverAddress addr, int backlog) throws IOException;

}
