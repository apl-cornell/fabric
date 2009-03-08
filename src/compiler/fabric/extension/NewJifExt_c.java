package fabric.extension;

import fabric.ast.FabricUtil;
import fabric.types.FabricClassType;
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
    if (newType instanceof FabricClassType) {
      FabricClassType ct = (FabricClassType)newType;
      ext.labelCheck(lc, ct.defaultFieldLabel());
    }
    
    return super.labelCheck(lc);
  }
}
