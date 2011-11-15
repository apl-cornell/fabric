package jif.translate;

import polyglot.ast.Node;
import polyglot.ast.Unary;
import polyglot.types.SemanticException;

public class UnaryToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Unary n = (Unary)node();
        return rw.java_nf().Unary(n.position(), n.expr(), n.operator());
    }
}
