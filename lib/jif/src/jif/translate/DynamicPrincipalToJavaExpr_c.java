package jif.translate;

import jif.types.principal.DynamicPrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class DynamicPrincipalToJavaExpr_c extends PrincipalToJavaExpr_c {
    public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
        DynamicPrincipal p = (DynamicPrincipal) principal;
        return rw.qq().parseExpr(p.path().exprString());
    }
}
