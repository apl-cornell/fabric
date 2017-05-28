package net.baseline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class BaselineClient {
  public static void main(String[] args) throws Exception {
    Socket sock = new Socket("localhost", 21212);
    
    DataInputStream  in  = new DataInputStream(sock.getInputStream());
    DataOutputStream out = new DataOutputStream(sock.getOutputStream());
    
    int n = 0;
    
    String[] msgs = new String[2];
    msgs[0] = "foo";
    msgs[1] = "bar";
    
    long time = System.currentTimeMillis();
    
    while (true) {
      String msg = msgs[n % 2];
      out.writeUTF(msg);
      out.flush();
      
      String rep = in.readUTF();
      assert(msg.equals(rep));
      
      if (++n % 1000 == 0) {
        long now = System.currentTimeMillis();
        System.out.println("1000 msgs exchanged: " + (now - time) + "ms");
        time = now;
      }
    }
  }
}
