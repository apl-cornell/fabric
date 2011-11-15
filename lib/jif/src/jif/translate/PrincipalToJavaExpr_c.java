package jif.translate;

import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public abstract class PrincipalToJavaExpr_c implements PrincipalToJavaExpr {
    public abstract Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException;
}
