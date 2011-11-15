package jif.translate;

import polyglot.ast.Node;
import polyglot.ast.Throw;
import polyglot.types.SemanticException;

public class ThrowToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Throw n = (Throw)node();
        return rw.java_nf().Throw(n.position(), n.expr());
    }
}
