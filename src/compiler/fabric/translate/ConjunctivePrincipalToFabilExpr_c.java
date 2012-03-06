package fabric.translate;

import java.util.Iterator;

import fabric.visit.FabricToFabilRewriter;

import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import jif.translate.ConjunctivePrincipalToJavaExpr_c;
import jif.translate.JifToJavaRewriter;
import jif.types.JifTypeSystem;
import jif.types.principal.ConjunctivePrincipal;
import jif.types.principal.Principal;

public class ConjunctivePrincipalToFabilExpr_c extends
ConjunctivePrincipalToJavaExpr_c {
  public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
    FabricToFabilRewriter ffrw = (FabricToFabilRewriter) rw;
    JifTypeSystem ts = rw.jif_ts();
    Expr e = null;
    ConjunctivePrincipal cp = (ConjunctivePrincipal) principal;
    for (Iterator iter = cp.conjuncts().iterator(); iter.hasNext();) {
      Principal p = (Principal)iter.next();
      Expr pe = rw.principalToJava(p);
      if (e == null) {
        e = pe;
      }
      else {
        e = rw.qq().parseExpr(ts.PrincipalUtilClassName() + ".conjunction(%E, %E, %E)",
            ffrw.currentLocation(), pe, e);
      }
    }
    return e;
  }
}
