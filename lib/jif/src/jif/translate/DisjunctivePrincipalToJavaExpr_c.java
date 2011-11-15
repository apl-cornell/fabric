package jif.translate;

import java.util.Iterator;

import jif.types.JifTypeSystem;
import jif.types.principal.DisjunctivePrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class DisjunctivePrincipalToJavaExpr_c extends PrincipalToJavaExpr_c {
    public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
        JifTypeSystem ts = rw.jif_ts();
        Expr e = null;
        DisjunctivePrincipal dp = (DisjunctivePrincipal) principal;
        for (Iterator iter = dp.disjuncts().iterator(); iter.hasNext();) {
            Principal p = (Principal)iter.next();
            Expr pe = rw.principalToJava(p);
            if (e == null) {
                e = pe;
            }
            else {
                e = rw.qq().parseExpr(ts.PrincipalUtilClassName() + ".disjunction(%E, %E)", pe, e);
            }
        }
        return e;
    }
}
