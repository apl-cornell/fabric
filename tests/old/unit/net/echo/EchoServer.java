package net.echo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fabric.common.net.*;
import fabric.common.net.handshake.HandshakeUnauthenticated;

@SuppressWarnings("unused")
public class EchoServer extends Thread {
  private static Logger                 logger = Logger.getLogger("server");
  private static SubServerSocketFactory factory;
  
  private final String          name;
  private final SubServerSocket serverSocket;
  
  private EchoServer(String name) throws IOException {
    this.name         = name;
    this.serverSocket = factory.createServerSocket(name);
  }

  @Override
  public void run() {
    try {
      while (true)
        process(serverSocket.accept());
    } catch(IOException e) {
      logger.log(Level.SEVERE, "server socket [" + name + "] dying...", e);
    }
  }
  
  private void process(SubSocket sock) {
    try {
      DataInputStream   in = new DataInputStream(sock.getInputStream());
      DataOutputStream out = new DataOutputStream(sock.getOutputStream());

      logger.info(name + " receiving");
      String request = in.readUTF();
      
      logger.info(name + " echoing: " + request);

      out.writeUTF(request);
      out.flush();
      
      logger.info(name + " sent");
      // TODO
      // sock.close();
    } catch (final IOException e) {
      e.printStackTrace();
      System.err.println("handler dying...");
    }
  }
  
  private class Handler extends Thread {
    private final SubSocket sock;
    
    public Handler(SubSocket sock) {
      this.sock = sock;
    }
    
    @Override
    public void run () {

      process(sock);
    }
  }
  
  public static void main(String[] args) throws IOException, GeneralSecurityException {
    if (0 == args.length)
      args = new String[] {"localhost"};
    
    factory = new SubServerSocketFactory(
        new HandshakeUnauthenticated(),
        new BogusNameService(3372));

    for (String name : args)
      new EchoServer(name).start();
  }
  
}
