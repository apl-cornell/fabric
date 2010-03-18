import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import fabric.common.net.*;
import fabric.common.net.handshake.HandshakeUnauthenticated;
import fabric.common.net.naming.BogusNameService;

public class EchoServer extends Thread {
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
        new Handler(serverSocket.accept()).start();
    } catch(IOException e) {
      e.printStackTrace();
      System.err.println("server socket [" + name + "] dying...");
    }
  }
  
  private void process(SubSocket sock) {
    try {
      DataInputStream   in = new DataInputStream(sock.getInputStream());
      DataOutputStream out = new DataOutputStream(sock.getOutputStream());

      System.out.println(name + " receiving");
      String request = in.readUTF();
      
      try { sleep(100); } catch(InterruptedException e) {}
      
      System.out.println(name + " echoing: " + request);

      out.writeUTF(request);
      out.flush();
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
  
  public static void main(String[] args) throws IOException {
    if (0 == args.length)
      args = new String[] {"localhost"};
    
    factory = new SubServerSocketFactory(
        new HandshakeUnauthenticated(),
        new BogusNameService(3372)
    );

    for (String name : args)
      new EchoServer(name).start();
  }
  
}
