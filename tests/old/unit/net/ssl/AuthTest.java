package net.ssl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import fabric.common.ConfigProperties;
import fabric.common.KeyMaterial;
import fabric.common.net.handshake.HandshakeAuthenticated;
import fabric.common.net.handshake.Protocol;
import fabric.common.net.handshake.ShakenSocket;
import fabric.worker.Worker;

public final class AuthTest {

  private static final int       port = 11111;
  private static final Semaphore barrier = new Semaphore(1);
  
  public static class UsageException extends Exception {
    public UsageException() {
      super("\n" +
            "Usage: AuthTest [--client <name>] [--server <name>]\n" +
            "       you must specify at least one of --client or --server\n");
    }
  }
  
  public static void main(String[] args) throws Exception {
    
    String clientName = null;
    String serverName = null;
    
    for (int i = 0; i < args.length; i++) {
      if (!(i+1 < args.length))
        throw new UsageException();
      if (args[i].equals("--client"))
        clientName = args[++i];
      else if (args[i].equals("--server"))
        serverName = args[++i];
      else
        throw new UsageException();
    }
    
    if (clientName == null || serverName == null)
      throw new UsageException();

    Worker.initialize(clientName);
    
    ConfigProperties serverProps = new ConfigProperties(serverName);
    final KeyMaterial serverKeys = serverProps.getKeyMaterial();

    // start the server
    barrier.acquire();
    new Thread("server") {
      @Override public void run() {runServer(serverKeys);}
    }.start();
    
    ConfigProperties clientProps = new ConfigProperties(clientName);
    final KeyMaterial clientKeys = clientProps.getKeyMaterial();
    final String sname = serverName;
    
    // start the client
    new Thread("client") {
      @Override public void run() {runClient(sname, clientKeys);}
    }.start();

  }

  private static void runServer(KeyMaterial keys) {
    try {
      Protocol p = new HandshakeAuthenticated(keys);
      
      ServerSocket ssock = new ServerSocket(port);
      barrier.release();
      Socket       sock  = ssock.accept();
      ShakenSocket shake = p.receive(sock);
      
      DataInputStream  in  = new DataInputStream( shake.sock.getInputStream() );
      DataOutputStream out = new DataOutputStream(shake.sock.getOutputStream());
      
      System.out.println(in.readUTF());
      out.writeUTF("Hello Doctor Falken.  How about a nice game of global thermonuclear war?");

      shake.sock.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
  
  private static void runClient(String serverName, KeyMaterial keys) {
    try {
      Protocol p = new HandshakeAuthenticated(keys);
      
      barrier.acquire();
      Socket sock        = new Socket("localhost", port);
      ShakenSocket shake = p.initiate(serverName, sock);
      
      DataInputStream  in  = new DataInputStream( shake.sock.getInputStream() );
      DataOutputStream out = new DataOutputStream(shake.sock.getOutputStream());
      
      out.writeUTF("Hello Server!");
      System.out.println(in.readUTF());
      
      shake.sock.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }
}
