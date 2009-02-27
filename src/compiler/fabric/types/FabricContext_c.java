package fabric.types;

import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import jif.types.JifContext_c;
import jif.types.JifTypeSystem;

public class FabricContext_c extends JifContext_c {
  protected FabricContext_c(JifTypeSystem ts, TypeSystem jlts) {
    super(ts, jlts);
  }
  
  @Override
  public LocalInstance findLocal(String name) throws SemanticException {
    if (name.equals("client$")) {
      return ((FabricTypeSystem)typeSystem()).clientLocalInstance();
    }
    return super.findLocal(name);
  }
}
