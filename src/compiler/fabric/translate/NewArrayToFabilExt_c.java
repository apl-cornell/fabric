package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.ast.FabricUtil;
import fabric.extension.NewArrayExt_c;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import jif.translate.JifToJavaRewriter;
import jif.translate.NewArrayToJavaExt_c;
import jif.types.label.Label;

public class NewArrayToFabilExt_c extends NewArrayToJavaExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    NewArray n = (NewArray)node();
    NewArrayExt_c ext = (NewArrayExt_c)FabricUtil.fabricExt(n);
    
    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();

    Type base = n.type();
    while (base.isArray()) {
      base = base.toArray().base();
    }
    
    Label baseLabel = ts.labelOfType(base);
    Expr labelExpr = null;
    if (baseLabel != null) {
      labelExpr = rw.labelToJava(baseLabel);
    }
    
    return nf.NewArray(n.position(), n.baseType(), labelExpr, ext.location(), 
                       n.dims(), n.additionalDims(), n.init());
  }
}
