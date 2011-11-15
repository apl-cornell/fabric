package jif.translate;

import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class NewArrayToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        NewArray n = (NewArray)node();
        return rw.java_nf().NewArray(n.position(), n.baseType(), n.dims(), n.additionalDims(), n.init());
    }
}
