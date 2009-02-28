package fabric.lang;

public class DefaultStatistics implements Statistics {

  public void commitRead()  { }

  public void commitWrote() { }

  public int generatePromise() {
    return 0;
  }

  public static final DefaultStatistics instance = new DefaultStatistics();
}
