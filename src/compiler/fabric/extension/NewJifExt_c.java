package fabric.extension;

import fabil.types.FabILTypeSystem;
import fabric.ast.FabricUtil;
import fabric.types.FabricClassType;
import fabric.types.FabricTypeSystem;
import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import jif.extension.JifNewExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

public class NewJifExt_c extends JifNewExt {
  public NewJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    New n = (New)node();
    NewExt_c ext = (NewExt_c)FabricUtil.fabricExt(n);
    
    Type newType = n.objectType().type();
    // bypass check if this is a principal object. This condition will be enforced
    // with the $addDefaultDelegates method
    if (newType instanceof FabricClassType && 
        !newType.isSubtype(((FabILTypeSystem)lc.typeSystem()).DelegatingPrincipal())) {
      FabricClassType ct = (FabricClassType)newType;
      ext.labelCheck(lc, ct.defaultFieldLabel());
    }
    
    return super.labelCheck(lc);
  }
}
