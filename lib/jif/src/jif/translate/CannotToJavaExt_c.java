package jif.translate;

import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

public class CannotToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Node n = node();
        throw new InternalCompilerError(n.position(),
                                        "Cannot translate " + n + " to Java.");
    }
}
