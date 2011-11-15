package jif.translate;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ArrayAccessToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        ArrayAccess n = (ArrayAccess)node();
        return rw.java_nf().ArrayAccess(n.position(), n.array(), n.index());
    }
}
