package jif.translate;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Return;
import polyglot.types.SemanticException;

public class ReturnToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Return n = (Return) node();
        n = (Return) rw.java_nf().Return(n.position(), n.expr());

        // Rewrite constructor returns to return this.
        if (rw.inConstructor()) {
            NodeFactory nf = rw.java_nf();
            return n.expr(nf.This(n.position()).type(rw.currentClass()));
        }

        return n;
    }
}
