package OO7;

import java.util.Set;

public class Module {
  public final int id;
  public char[]    type;
  public int       buildDate;
  public Manual    man;

  public Set             assemblies;
  public ComplexAssembly designRoot;

  public Module(int id) {
    this.id = id;
  }

  public int travaerse (BenchmarkOp op) {
    return 0;
  }

  public int scanManual() {
    return 0;
  }

  public int firstLast() {
    return 0;
  }

}

/*
** vim: ts=2 sw=2 et cindent cino=\:0 syntax=java
*/

