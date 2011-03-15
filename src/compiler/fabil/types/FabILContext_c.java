package fabil.types;

import polyglot.frontend.Source;
import polyglot.types.Context;
import polyglot.types.Context_c;
import polyglot.types.ImportTable;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import fabil.frontend.CodebaseSource;
import fabric.lang.Codebase;

/**
 * Codebase support for the FabIL typesystem. This class duplicates some of the
 * code in FabricContext_c, but enables FabIL classes to use codebase-relative
 * names.
 */
public class FabILContext_c extends Context_c implements FabILContext {

  protected Source source;
  protected Codebase codebase;

  protected FabILContext_c(TypeSystem ts) {
    super(ts);
    this.codebase = null;
  }

  @Override
  protected Context_c push() {
    FabILContext_c v = (FabILContext_c) super.push();
    v.codebase = codebase;
    v.source = source;
    return v;
  }

  public Codebase currentCodebase() {
    return codebase;
  }

  /**
   * Push a codebase scope.
   */
  public CodebaseContext pushCodebase(Codebase codebase) {
    FabILContext_c v = (FabILContext_c) push();
    v.kind = OUTER;
    v.codebase = codebase;
    v.inCode = false;
    v.staticContext = false;
    return v;
  }

  /**
   * Return the current source
   */
  public CodebaseSource currentSource() {
    return (CodebaseSource) source;
  }

  public CodebaseContext pushSource(CodebaseSource source) {
    FabILContext_c v = (FabILContext_c) push();
    v.kind = OUTER;
    v.source = (Source) source;
    return v;
  }

  @Override
  public Context pushSource(ImportTable it) {
    FabILContext_c v = (FabILContext_c) super.pushSource(it);
    v.source = (Source) ((CodebaseImportTable) it).source();
    return v;
  }

  /**
   * Finds the definition of a particular type.
   */
  @Override
  public Named find(String name) throws SemanticException {
    if (isOuter())
      return ts.systemResolver().find(
          ((CodebaseTypeSystem) ts).absoluteName(codebase, name,
              ((CodebaseSource) source).isRemote()));
    else return super.find(name);
  }
}
