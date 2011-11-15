package jif.translate;

import jif.types.JifTypeSystem;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class TopPrincipalToJavaExpr_c extends PrincipalToJavaExpr_c {
    public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
        JifTypeSystem ts = rw.jif_ts();
        return rw.qq().parseExpr(ts.PrincipalUtilClassName() + ".topPrincipal()"); 
    }
}
