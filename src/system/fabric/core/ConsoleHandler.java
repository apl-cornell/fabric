package fabric.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleHandler extends Thread {
  @Override
  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      while (true) {
        String command = in.readLine();
        
        if (null == command || command.equals("quit") || command.equals("exit")) {
          System.out.println("exiting");
          System.exit(0);
        } else {
          System.err.println("unrecognized command");
        }
      }
    } catch (final IOException e) {
      System.exit(1);
    }
  }
}
