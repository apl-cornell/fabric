package fabric.worker.shell;

import java.util.List;

/**
 * A command source that blocks indefinitely when asked for a command.
 */
public class DummyCommandSource extends CommandSource {

  private static Object condVar = new Object();

  public static void signalToQuit() {
    synchronized (condVar) {
      condVar.notifyAll();
    }
  }

  @Override
  public List<String> getNextCommand(List<String> buf) {
    try {
      synchronized (condVar) {
        condVar.wait();
      }
    } catch (InterruptedException e) {
    }

    return null;
  }

  @Override
  public boolean reportError(String message) {
    err.println("Error: " + message);
    return false;
  }

}
