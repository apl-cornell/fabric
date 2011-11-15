package jif.translate;

import jif.types.ActsForConstraint;
import jif.types.JifTypeSystem;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class ActsForConstraintToJavaExpr_c implements
    ActsForConstraintToJavaExpr {

  @Override
  public Expr toJava(ActsForConstraint actsFor, JifToJavaRewriter rw)
      throws SemanticException {
    JifTypeSystem ts = rw.jif_ts();
    Expr actor = actsFor.actor().toJava(rw);
    Expr granter = actsFor.granter().toJava(rw);

    String className = ts.PrincipalUtilClassName();

    String meth;
    if (actsFor.isEquiv()) {
      meth = "equivalentTo";
    } else {
      meth = "actsFor";
    }

    String comparison = className + "." + meth + "((%E), (%E))";
    return rw.qq().parseExpr(comparison, actor, granter);
  }

}
