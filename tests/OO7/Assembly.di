package OO7;

import java.util.Set;

public abstract class Assembly {
  public final int       id;
  public char[]          type;
  public int             buildDate;
  public ComplexAssembly superAssembly;
  public Module          module;

  public Assembly(int id) {
    this.id = id;
  }

  public abstract int          traverse  (BenchmarkOp op);
  public abstract int          traverse7 (Set visited);

  public void doNothing() {
  }
}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

