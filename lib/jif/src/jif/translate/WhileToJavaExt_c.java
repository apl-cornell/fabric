package jif.translate;

import polyglot.ast.Node;
import polyglot.ast.While;
import polyglot.types.SemanticException;

public class WhileToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        While n = (While)node();
        return rw.java_nf().While(n.position(), n.cond(), n.body());
    }
}
