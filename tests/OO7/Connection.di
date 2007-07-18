package OO7;

public class Connection {
  public String type;
  public int    length;

  public AtomicPart from;
  public AtomicPart to;

  public Connection(AtomicPart from, AtomicPart to) {
    this.from = from;
    this.to   = to;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/
