package jif.translate;

import java.io.Serializable;

import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public interface PrincipalToJavaExpr extends Serializable {
    public Expr toJava(Principal principal, JifToJavaRewriter rw) throws SemanticException;
}
