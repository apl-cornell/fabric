package fabric.types;

import polyglot.ast.Expr;
import polyglot.types.Context;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import jif.types.JifContext_c;
import jif.types.JifTypeSystem;

public class FabricContext_c extends JifContext_c implements FabricContext {
  protected FabricContext_c(JifTypeSystem ts, TypeSystem jlts) {
    super(ts, jlts);
  }
  
  protected Expr location;
  
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

  public Expr location() {
    return location;
  }

  public Context pushLocation(Expr location) {
    FabricContext_c v = (FabricContext_c) push();
    v.location = location;
    return v;
  }
}
