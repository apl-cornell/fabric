package fabil.types;
import polyglot.types.Package_c;
import fabil.frontend.CodebaseSource;
import fabric.lang.Codebase;

public class CodebasePackage_c extends Package_c implements CodebasePackage {
  protected CodebaseSource source;

  public CodebasePackage_c(CodebaseTypeSystem ts, CodebasePackage prefix, String name) {
    super(ts, prefix, name);
    if(prefix != null)
      this.source = prefix.source();
  }

  public Codebase codebase() {
    if(source != null)
      return source.codebase();
    else 
      return null;
  }
  
  public CodebasePackage source(CodebaseSource src) {
    if (src == null && source == null) return this;

    if (source != null && src != null && source.equals(src)) return this;

    CodebasePackage_c cbp = (CodebasePackage_c) copy();
    cbp.source = src;
    return cbp;
  }
  
  public CodebaseSource source() {
    return source;
  }

}
