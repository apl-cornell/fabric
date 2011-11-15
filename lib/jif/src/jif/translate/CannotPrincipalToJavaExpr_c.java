package jif.translate;

import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

public class CannotPrincipalToJavaExpr_c extends PrincipalToJavaExpr_c {
    public Expr toJava(Principal P, JifToJavaRewriter rw) throws SemanticException {
        throw new InternalCompilerError(P.position(),
                                        "Cannot translate " + P + " to Java.");
    }
}
