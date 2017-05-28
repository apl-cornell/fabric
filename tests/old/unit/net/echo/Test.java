package net.echo;


public class Test {
  public static void main(String[] args) throws Exception {
    EchoServer.main(args);
    Thread.sleep(100);
    Client.main(args);
  }
}
