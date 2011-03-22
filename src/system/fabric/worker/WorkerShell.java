package fabric.worker;

public class WorkerShell {
  protected final Worker worker;
  
  public WorkerShell(Worker worker) {
    this.worker = worker;
  }
  
  public void run() {
    while (true) {
      // For now, do nothing and just act as a dissemination node.
      try {
        Thread.sleep(Long.MAX_VALUE);
      } catch (InterruptedException e) {
      }
    }
  }
}
