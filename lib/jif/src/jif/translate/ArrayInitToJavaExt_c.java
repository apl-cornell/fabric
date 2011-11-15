package jif.translate;

import polyglot.ast.ArrayInit;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ArrayInitToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        ArrayInit n = (ArrayInit)node();
        return rw.java_nf().ArrayInit(n.position(), n.elements());

    }
}
