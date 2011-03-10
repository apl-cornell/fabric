package fabric.types;

import fabil.types.CodebaseContext;
import fabric.lang.Codebase;
import polyglot.types.Context;
import polyglot.types.Context_c;
import polyglot.types.LocalInstance;
import polyglot.types.Named;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import jif.types.JifContext_c;
import jif.types.JifTypeSystem;

public class FabricContext_c extends JifContext_c implements FabricContext {
  
  protected Codebase codebase;
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
    return v;
  }

  public Codebase currentCodebase() {
    return codebase;
  }
  
  /**
   * Push a source file scope.
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
