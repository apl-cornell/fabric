package OO7;

import java.util.Collection;
import java.util.Set;

public class BaseAssembly extends Assembly {
  public Collection componentsPriv; // contains CompositeParts
  public Collection componentsShar; // contains CompositeParts

  public BaseAssembly(int id, ComplexAssembly parent, Module mod) {
    super(id);
  }

  public int traverse(BenchmarkOp op) {
    return 0;
  }

  public int traverse7(Set visited) {
    return 0;
  }

}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

