package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;
import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import jif.extension.JifNewArrayExt;
import jif.translate.ToJavaExt;
import jif.types.label.Label;
import jif.visit.LabelChecker;

public class NewArrayJifExt_c extends JifNewArrayExt {
  public NewArrayJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    NewArray na = (NewArray) node();
    NewFabricArrayExt_c ext = (NewFabricArrayExt_c) FabricUtil.fabricExt(na);

    Type baseType = na.baseType().type();
    while (baseType.isArray()) {
      baseType = baseType.toArray().base();
    }
    if (baseType instanceof FabricClassType) {
      FabricClassType ct = (FabricClassType) baseType;
      // TODO: Implement access label checks for arrays
      FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
      Label accessLabel = ts.toLabel(ct.accessPolicy());
      ext.labelCheck(lc, ct.updateLabel(), accessLabel);
    }

    return super.labelCheck(lc);
  }
}
