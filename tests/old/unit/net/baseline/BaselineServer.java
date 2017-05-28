package net.baseline;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class BaselineServer {
  public static void main(String[] args) throws Exception {
    ServerSocket listener = new ServerSocket(21212);
    Socket       sock     = listener.accept();
    
    DataInputStream  in  = new DataInputStream(sock.getInputStream());
    DataOutputStream out = new DataOutputStream(sock.getOutputStream());
    
    while (true) {
      String msg = in.readUTF();
      out.writeUTF(msg);
      out.flush();
    }
  }
}
