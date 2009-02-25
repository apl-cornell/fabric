package fabric.lang;

public class DefaultHistory implements History {

  public void commitRead()  { }

  public void commitWrote() { }

  public int generatePromise() {
    return 0;
  }

  public static final DefaultHistory instance = new DefaultHistory();
}
