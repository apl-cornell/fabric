package fabric.extension;

import jif.ast.JifInstantiator;
import jif.ast.Jif_c;
import jif.extension.JifNewExt;
import jif.translate.ToJavaExt;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import fabric.ast.FabricUtil;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;

public class NewJifExt_c extends JifNewExt {
  public NewJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    New n = (New) super.labelCheck(lc);
    NewExt_c ext = (NewExt_c) FabricUtil.fabricExt(n);

    Type newType = n.objectType().type();
    // Bypass check if this is a principal object. This condition will be
    // enforced with the $addDefaultDelegates method
    if (newType instanceof FabricClassType
        && !newType.isSubtype(((FabricTypeSystem) lc.typeSystem())
            .DelegatingPrincipal())) {
      FabricClassType ct = (FabricClassType) newType;
      FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

      Label accessLabel = ts.toLabel(ct.accessPolicy());
      if (accessLabel != null) {
        accessLabel =
            JifInstantiator.instantiate(accessLabel, lc.jifContext(), n,
                newType.toReference(), Jif_c.getPathMap(n).NV());
      }

      Label objectLabel = ct.updateLabel();
      if (objectLabel != null) {
        objectLabel =
            JifInstantiator.instantiate(objectLabel, lc.jifContext(), n,
                newType.toReference(), Jif_c.getPathMap(n).NV());
      }

      ext.labelCheck(lc, objectLabel, accessLabel);
    }

    return n;
  }
}
