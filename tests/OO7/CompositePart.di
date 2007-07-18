package OO7;

import java.util.Collection;
import java.util.Set;

public class CompositePart {
  public final int id;
  public String    type;
  public int       buildDate;
  public Document  documentation;

  public Collection usedInPriv; // contains BaseAssemblies
  public Collection usedInShar; // contains BaseAssemblies

  public Set        parts; // contains AtomicParts
  public AtomicPart rootPart;

  public CompositePart(int id) {
    this.id = id;
  }

  public int traverse(BenchmarkOp op) {
    return 0;
  }

  public int traverse7() {
    return 0;
  }

  public int reorg1() {
    return 0;
  }

  public int reorg2() {
    return 0;
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

