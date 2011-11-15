package jif.translate;

import polyglot.ast.Do;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class DoToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Do n = (Do)node();
        return rw.java_nf().Do(n.position(), n.body(), n.cond());
    }
}
