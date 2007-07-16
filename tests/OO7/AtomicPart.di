package OO7;

import java.util.Collection;
import java.util.Set;

public class AtomicPart {
  public int    id;
  public char[] type;
  public int    buildDate;
  public int    x, y;
  public int    docID;

  public Collection to;   // contains Connections
  public Collection from; // contains Connections

  public CompositePart   parent;

  public AtomicPart(int partID) {
  }

  public void swapXY() {
  }

  public void toggleDate() {
  }

  public int traverse(BenchmarkOp op, Set visited) {
    return 0;
  }

  public void doNothing() {}
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

