package fabric.worker.shell;

import java.util.List;

/**
 * A command source that blocks indefinitely when asked for a command.
 */
public class DummyCommandSource extends CommandSource {

  @Override
  public List<String> getNextCommand(List<String> buf) {
    try {
      Object o = new Object();
      synchronized (o) {
        o.wait();
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
