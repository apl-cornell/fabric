package fabric.common.net;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class BaseSocketFamily {

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

}

/*
** vim: ts=2 sw=2 et cindent
*/
