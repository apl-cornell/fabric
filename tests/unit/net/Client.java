import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import fabric.common.net.*;
import fabric.common.net.handshake.HandshakeUnauthenticated;
import fabric.common.net.naming.BogusNameService;

public class Client extends Thread {
  private static SubSocketFactory factory;
  private static String[]         names;
  private static String[]         msgs;
  
  @Override
  public void run() {
    Random rand = new Random();
    try {
      while (true) {
        try { sleep(1000 + rand.nextInt(3000)); } catch (InterruptedException e) {}

        String name = names[rand.nextInt(names.length)];
        System.out.println("connecting to " + name);
        SubSocket   sock = factory.createSocket(name);

        DataOutputStream out = new DataOutputStream(sock.getOutputStream());
        DataInputStream   in = new DataInputStream(sock.getInputStream());
        
        String request = msgs[rand.nextInt(msgs.length)];
        System.out.println("sending " + shorten(request));
        
        out.writeUTF(request);
        out.flush();
        
        System.out.println("receiving reply...");
        String response = in.readUTF();
        System.out.println("received " + shorten(response));
        
        if (!request.equals(response))
          throw new IOException("got wrong reply back");
        
        // TODO:
        // System.out.println("closing socket");
        // sock.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.err.println("client thread dying...");
    }
  }
  
  private static String shorten(String message) {
    // TODO
    if (message.length() > 25)
      message = message.substring(0, 8) + " ... "
              + message.substring(message.length() - 12, message.length());
    return message;
  }
  
  public static void main(String[] args) {
    factory = new SubSocketFactory(
        new HandshakeUnauthenticated(),
        new BogusNameService(3372)
    );
    
    if (0 == args.length)
      args = new String[] {"localhost"};
    names = args;
    
    
    msgs = new String[2];
    msgs[0] = "hello world";
    msgs[1] = "moo";
    
    for (int i = 0; i < 1; i++)
      new Client().start();
  }
}
