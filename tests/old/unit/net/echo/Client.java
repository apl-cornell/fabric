package net.echo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import fabric.common.net.*;
import fabric.common.net.handshake.HandshakeUnauthenticated;

@SuppressWarnings("unused")
public class Client extends Thread {
  private static Logger           logger = Logger.getLogger("client");
  private static SubSocketFactory factory;
  private static String[]         names;
  private static String[]         msgs;
  
  @Override
  public void run() {
    Random rand = new Random();
    
    int  n    = 0;
    long time = System.currentTimeMillis();
    
    try {
      while (true) {
        String name = names[rand.nextInt(names.length)];
        logger.info("connecting to " + name);
        SubSocket   sock = factory.createSocket(name);

        DataOutputStream out = new DataOutputStream(sock.getOutputStream());
        DataInputStream   in = new DataInputStream(sock.getInputStream());
        
        String request = msgs[rand.nextInt(msgs.length)];
        logger.info("sending " + shorten(request));
        
        out.writeUTF(request);
        out.flush();
        
        logger.info("receiving reply...");
        String response = in.readUTF();
        logger.info("received " + shorten(response));
        
        if (!request.equals(response))
          throw new IOException("got wrong reply back");
        
        // TODO:
        // logger.info("closing socket");
        // sock.close();
        
        if (++n % 1000 == 0) {
          long now = System.currentTimeMillis();
          System.out.println("1000 msgs exchanged: " + (now - time) + "ms");
          time = now;
        }
      }
    } catch (IOException e) {
      logger.log(Level.SEVERE, "client thread dying...", e);
    }
  }
  
  private static String shorten(String message) {
    // TODO
    if (message.length() > 25)
      message = message.substring(0, 6) + " ... "
              + message.substring(message.length() - 12, message.length());
    return message;
  }
  
  public static void main(String[] args) throws GeneralSecurityException {
    
    factory = new SubSocketFactory(
        new HandshakeUnauthenticated(),
        new BogusNameService(3372)
    );
    
    if (0 == args.length)
      args = new String[] {"localhost"};
    names = args;
    
    
    msgs = new String[3];
    msgs[0] = "hello world";
    msgs[1] = "moo";
    
    StringBuilder msg = new StringBuilder("A ");
    for (int i = 0; i < 1000; i ++)
      msg.append("very, ");
    msg.append("very long message");
    msgs[2] = msg.toString();
    
    for (int i = 0; i < 1; i++)
      new Client().start();
  }
}
