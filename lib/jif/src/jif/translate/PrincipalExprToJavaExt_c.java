package jif.translate;

import jif.ast.PrincipalExpr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public class PrincipalExprToJavaExt_c extends ToJavaExt_c {
    public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
        PrincipalExpr n = (PrincipalExpr) node();
        return rw.bypass(n.principal());
    }

    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        PrincipalExpr n = (PrincipalExpr) node();
        return n.visitChild(n.principal(), rw);
    }
}
