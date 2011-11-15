package jif.translate;

import jif.types.principal.ExternalPrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class ExternalPrincipalToJavaExpr_c extends PrincipalToJavaExpr_c {
    public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException {
        ExternalPrincipal P = (ExternalPrincipal) principal;
        return rw.qq().parseExpr("jif.principals.%s.getInstance()", P.name());
    }
}
