package fabric.common;

public abstract class Warranty extends ExpirableToken implements Comparable<Warranty> {

  /**
   * @param expiry expiry time, in milliseconds since the epoch.
   */
  public Warranty(long expiry) {
    super(expiry);
  }

  // Deserialization constructor.
  protected Warranty() {
    super();
  }

  /**
   * Orders by expiry time.
   */
  @Override
  public int compareTo(Warranty o) {
    if (expiry() > o.expiry()) return 1;
    if (expiry() < o.expiry()) return -1;
    return 0;
  }
}
