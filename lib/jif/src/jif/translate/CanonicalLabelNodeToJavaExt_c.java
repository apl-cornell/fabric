package jif.translate;

import jif.ast.CanonicalLabelNode;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class CanonicalLabelNodeToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        CanonicalLabelNode n = (CanonicalLabelNode) node();
        return rw.labelToJava(n.label());
    }
}
