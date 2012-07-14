package fabric.common;

public class MemoryHandler extends java.util.logging.MemoryHandler {
  public MemoryHandler() {
    super();
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        push();
      }
    });
  }
}
