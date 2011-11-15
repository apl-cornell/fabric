package jif.translate;

import polyglot.ast.For;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ForToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        For n = (For)node();
        return rw.java_nf().For(n.position(), n.inits(), n.cond(), n.iters(), n.body());
    }
}
