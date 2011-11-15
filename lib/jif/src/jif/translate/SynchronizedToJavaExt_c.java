package jif.translate;

import polyglot.ast.Node;
import polyglot.ast.Synchronized;
import polyglot.types.SemanticException;

public class SynchronizedToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Synchronized n = (Synchronized)node();
        return rw.java_nf().Synchronized(n.position(), n.expr(), n.body());
    }
}
