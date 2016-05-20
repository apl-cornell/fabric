package fabric.translate;

import fabric.visit.FabricToFabilRewriter;
import jif.translate.DisjunctivePrincipalToJavaExpr_c;
import jif.translate.JifToJavaRewriter;
import jif.types.JifTypeSystem;
import jif.types.principal.DisjunctivePrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class DisjunctivePrincipalToFabilExpr_c
    extends DisjunctivePrincipalToJavaExpr_c {
  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw, Expr qualifier)
      throws SemanticException {
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    JifTypeSystem ts = rw.jif_ts();
    Expr e = null;
    DisjunctivePrincipal dp = (DisjunctivePrincipal) principal;

    for (Principal p : dp.disjuncts()) {
      Expr pe = rw.principalToJava(p, qualifier);
      if (e == null) {
        e = pe;
      } else {
        e = rw.qq().parseExpr(
            ts.PrincipalUtilClassName() + ".disjunction(%E, %E, %E)",
            ffrw.currentLocation(), pe, e);
      }
    }
    return e;
  }

}
