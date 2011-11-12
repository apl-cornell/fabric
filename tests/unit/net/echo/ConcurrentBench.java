package net.echo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

public class ConcurrentBench {
  private static DataOutputStream out;
  private static DataInputStream  in;
  
  static class Producer extends Thread {
    @Override
    public void run() {
      try {
        while(true) {
          out.writeLong(System.currentTimeMillis());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  static class Consumer extends Thread {
    @Override
    public void run() {
      try {
        while(true) {
          long then = in.readLong();
          long now  = System.currentTimeMillis();
          long diff = now - then;
          System.out.println("sent:     " + then);
          System.out.println("received: " + now);
          System.out.println("diff:     " + diff);
          System.out.println();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
  public static void main(String[] args) throws Exception {
    PipedInputStream s = new PipedInputStream();
    in  = new DataInputStream(s);
    out = new DataOutputStream(new PipedOutputStream(s));
    new Producer().start();
    new Consumer().start();
  }
}
