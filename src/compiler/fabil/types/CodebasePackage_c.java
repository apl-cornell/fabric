package fabil.types;

import fabric.lang.Codebase;
import polyglot.types.Package;
import polyglot.types.Package_c;

public class CodebasePackage_c extends Package_c implements CodebasePackage {
  protected Codebase codebase;
  public CodebasePackage_c(CodebaseTypeSystem ts, Package prefix, String name) {
    super(ts, prefix, name);
  }

  public Codebase codebase() {
    return codebase;
  }

  public CodebasePackage codebase(Codebase cb) {
    CodebasePackage_c cbp = (CodebasePackage_c) copy();
    cbp.codebase = cb;
    return cbp;
  }

}
