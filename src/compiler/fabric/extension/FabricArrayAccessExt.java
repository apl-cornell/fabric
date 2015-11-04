package fabric.extension;

import jif.extension.JifArrayAccessExt;
import jif.translate.ToJavaExt;
import jif.visit.LabelChecker;
import polyglot.ast.ArrayAccess;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import fabric.types.FabricReferenceType;

/**
 *
 */
public class FabricArrayAccessExt extends JifArrayAccessExt {

  /**
   * @param toJava
   */
  public FabricArrayAccessExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    ArrayAccess n = (ArrayAccess) super.labelCheck(lc);
    Expr array = n.array();
    Type type = lc.typeSystem().unlabel(array.type());
    return DereferenceHelper.checkAccess(n, array, (FabricReferenceType) type, lc, n.position());
  }

}
