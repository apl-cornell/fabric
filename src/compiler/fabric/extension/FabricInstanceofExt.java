package fabric.extension;

import jif.extension.JifInstanceofExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.ast.Instanceof;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

/**
 * 
 */
public class FabricInstanceofExt extends JifInstanceofExt {

  public FabricInstanceofExt(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Instanceof io = (Instanceof) node();
    Expr ref = (Expr) lc.labelCheck(io.expr());
    
    DereferenceHelper.checkDereference(ref, lc, io.position());
    return super.labelCheck(lc);
  }

}
