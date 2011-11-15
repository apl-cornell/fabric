package jif.translate;

import jif.ast.CanonicalPrincipalNode;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class CanonicalPrincipalNodeToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        CanonicalPrincipalNode n = (CanonicalPrincipalNode) node();
        return rw.principalToJava(n.principal());
    }
}
