package jif.translate;

import polyglot.ast.Local;
import polyglot.ast.LocalAssign;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class LocalAssignToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        LocalAssign n = (LocalAssign)node();
        return rw.java_nf().LocalAssign(n.position(), (Local)n.left(), n.operator(), n.right());
    }
}
