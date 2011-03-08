package fabil.types;

import fabric.lang.Codebase;
import polyglot.types.Package;
import polyglot.types.Package_c;
import polyglot.types.TypeSystem;

public class CodebasePackage_c extends Package_c implements CodebasePackage {
  protected Codebase codebase;

  public CodebasePackage_c(TypeSystem ts, Package prefix, String name) {
    super(ts, prefix, name);
  }

  public Codebase codebase() {
    return codebase;
  }

  public void setCodebase(Codebase codebase) {
    this.codebase = codebase;
  }

}
