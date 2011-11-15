package jif.translate;

import jif.ast.LabelExpr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;

public class LabelExprToJavaExt_c extends ToJavaExt_c {
    public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
        LabelExpr n = (LabelExpr) node();
        return rw.bypass(n.label());
    }

    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        LabelExpr n = (LabelExpr) node();
        return n.visitChild(n.label(), rw);
    }
}
