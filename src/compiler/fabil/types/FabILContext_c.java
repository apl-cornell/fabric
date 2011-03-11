package fabil.types;

import fabil.frontend.CodebaseSource;
import fabric.lang.Codebase;
import polyglot.frontend.Source;
import polyglot.types.Context;
import polyglot.types.Context_c;
import polyglot.types.ImportTable;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;

/**
 * Codebase support for the FabIL typesystem.  This class
 * duplicates some of the code in FabricContext_c, but enables
 * FabIL classes to use codebase-relative names.
 *
 */
public class FabILContext_c extends Context_c implements FabILContext {
  
  protected Source source;
  protected Codebase codebase;
  protected String codebasePrefix;

  protected FabILContext_c(TypeSystem ts) {
    super(ts);
    this.codebase = null;
    this.codebasePrefix = "";
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
      v.codebasePrefix = codebasePrefix(codebase);
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
    v.source = (Source) ((FabILImportTable) it).source();
    return v;
  }

  /**
   * Finds the definition of a particular type.
   */
  @Override
  public Named find(String name) throws SemanticException {
    if(isOuter()) 
      return ts.systemResolver().find(codebasePrefix + name);
    else
      return super.find(name);
  }
  
  protected static String codebasePrefix(Codebase cb) {
    if(cb != null)
      return "fab://" + cb.$getStore().name() + "/"
        + cb.$getOnum() + "/";
    else
      return "";
  }

}
