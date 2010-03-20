package fabric.types;

import polyglot.types.TypeObject;
import polyglot.util.Position;
import jif.translate.PrincipalToJavaExpr;
import jif.types.JifClassType;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathLocal;
import jif.types.label.Label;
import jif.types.principal.DynamicPrincipal;
import jif.types.principal.DynamicPrincipal_c;
import jif.visit.LabelChecker;

public class FabricDynamicPrincipal_c extends DynamicPrincipal_c {
  public FabricDynamicPrincipal_c(AccessPath path, JifTypeSystem ts, Position pos, PrincipalToJavaExpr toJava) {
    super(path, ts, pos, toJava);
  }
  
  public boolean equalsImpl(TypeObject o) {
    if (this == o) return true;
    if (! (o instanceof DynamicPrincipal)) {
        return false;
    }

    DynamicPrincipal that = (DynamicPrincipal) o;
    
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
    if (ts.isLocalWorkerAccessPath(this.path()) && ts.isLocalWorkerAccessPath(that.path())) {
      return true;
    }
    
    return super.equalsImpl(o);
  }
  
  public PathMap labelCheck(JifContext A, LabelChecker lc) {
    FabricTypeSystem ts = (FabricTypeSystem)typeSystem();
    if (ts.isLocalWorkerAccessPath(this.path())) {
      Label l = ts.thisLabel(this.position(), (JifClassType)A.currentClass());
      return ts.pathMap().N(l).NV(l);
    }
    return super.labelCheck(A, lc);
  }
}
