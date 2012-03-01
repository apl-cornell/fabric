package fabric.extension;

import polyglot.ast.Cast;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import jif.extension.JifCastExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;

public class FabricCastExt extends JifCastExt {

  public FabricCastExt(ToJavaExt toJava) {
    super(toJava);
  }
  
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Cast cast = (Cast) node();
    Expr ref  = (Expr) lc.labelCheck(cast.expr());
    
    if (ref.type().isReference())
      DereferenceHelper.checkDereference(ref, lc, cast.position());
    
    return super.labelCheck(lc);
  }

}
