package jif.translate;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public abstract class ExprToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Expr e = (Expr) node();
        e = e.type(null);
        return exprToJava(rw);
    }

    public abstract Expr exprToJava(JifToJavaRewriter rw) throws SemanticException;
}
