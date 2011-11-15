package jif.translate;

import polyglot.ast.Node;
import polyglot.ast.Special;
import polyglot.types.SemanticException;

public class SpecialToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Special n = (Special)node();
        return rw.java_nf().Special(n.position(), n.kind(), n.qualifier());
    }
}
