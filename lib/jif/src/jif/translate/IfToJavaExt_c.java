package jif.translate;

import polyglot.ast.If;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class IfToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        If n = (If)node();
        return rw.java_nf().If(n.position(), n.cond(), n.consequent(), n.alternative());
    }
}
