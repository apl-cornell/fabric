package jif.translate;

import polyglot.ast.Conditional;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ConditionalToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Conditional n = (Conditional)node();
        return rw.java_nf().Conditional(n.position(), n.cond(), n.consequent(), n.alternative());
    }
}
