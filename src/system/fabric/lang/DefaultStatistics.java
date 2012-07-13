package fabric.lang;

public class DefaultStatistics implements Statistics {

  @Override
  public void commitRead() {
  }

  @Override
  public void commitWrote() {
  }

  @Override
  public int generatePromise() {
    return 0;
  }

  public static final DefaultStatistics instance = new DefaultStatistics();
}
