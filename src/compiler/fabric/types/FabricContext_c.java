package fabric.types;

import fabil.frontend.CodebaseSource;
import fabil.types.CodebaseContext;
import fabil.types.FabILContext_c;
import fabil.types.FabILImportTable;
import fabric.lang.Codebase;
import polyglot.frontend.Source;
import polyglot.types.Context;
import polyglot.types.Context_c;
import polyglot.types.ImportTable;
import polyglot.types.LocalInstance;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import jif.types.JifContext_c;
import jif.types.JifTypeSystem;

public class FabricContext_c extends JifContext_c implements FabricContext {
  
  protected Codebase codebase;
  protected Source source;
  protected String codebasePrefix;

  protected FabricContext_c(JifTypeSystem ts, TypeSystem jlts) {
    super(ts, jlts);
    this.codebase = null;
    this.codebasePrefix = "";
  }
  
  @Override
  protected Context_c push() {
    FabricContext_c v = (FabricContext_c) super.push();
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
      FabricContext_c v = (FabricContext_c) push();
      v.kind = OUTER;
      v.codebase = codebase;
      v.codebasePrefix = codebasePrefix(codebase);
      v.inCode = false;
      v.staticContext = false;
      return v;
  }
  
  public CodebaseSource currentSource() {
    return (CodebaseSource) source;
  }
  
  public CodebaseContext pushSource(CodebaseSource source) {
    FabricContext_c v = (FabricContext_c) push();
    v.kind = OUTER;
    v.source = (Source) source;
    return v;
  }

  @Override
  public Context pushSource(ImportTable it) {
    FabricContext_c v = (FabricContext_c) super.pushSource(it);
    v.source = (Source) ((FabricImportTable) it).source();
    return v;
  }

  /**
   * Finds the definition of a particular type.
   */
  @Override
  public Named find(String name) throws SemanticException {
    //System.out.println("LOOKING FOR " + name + " kind: " + kind);
    if(isOuter()) 
      return ts.systemResolver().find(codebasePrefix + name);
    else
      return super.find(name);
  }
  
  @Override
  public LocalInstance findLocal(String name) throws SemanticException {
    if (name.equals("worker$") || name.equals("worker$'")) {
      return ((FabricTypeSystem)typeSystem()).workerLocalInstance();
    }
    else if (name.endsWith("'")) {
      // XXX HACK!
      return super.findLocal(name.substring(0, name.length() - 1));
    }
    return super.findLocal(name);
  }

  protected static String codebasePrefix(Codebase cb) {
    if(cb != null)
      return "fab://" + cb.$getStore().name() + "/"
        + cb.$getOnum() + "/";
    else
      return "";
  }
}
