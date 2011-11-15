package jif.translate;

import polyglot.ast.Empty;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class EmptyToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Empty n = (Empty)node();
        return rw.java_nf().Empty(n.position());
    }
}
