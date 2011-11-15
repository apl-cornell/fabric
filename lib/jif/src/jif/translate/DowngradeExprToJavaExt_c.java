package jif.translate;

import jif.ast.DowngradeExpr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public class DowngradeExprToJavaExt_c extends ToJavaExt_c {
    public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
        DowngradeExpr n = (DowngradeExpr) node();
        return rw.bypass(n.bound()).bypass(n.label());
    }

    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        DowngradeExpr n = (DowngradeExpr) node();
        return n.expr();
    }
}
