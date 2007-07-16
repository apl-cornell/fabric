package OO7;

import java.util.Set;

public class ComplexAssembly extends Assembly {
  public Set subAssemblies; // Contains Assemblies

  public ComplexAssembly (int id, ComplexAssembly parent, int levelNo, Module mod) {
    super(id);
  }

  public int traverse (BenchmarkOp op) {
    return 0;
  }

  public int traverse7 (Set visited) {
    return 0;
  }

}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

