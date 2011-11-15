package jif.translate;

import polyglot.ast.CanonicalTypeNode;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class CanonicalTypeNodeToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        CanonicalTypeNode n = (CanonicalTypeNode) node();
        return rw.typeToJava(n.type(), n.position());
    }
}
