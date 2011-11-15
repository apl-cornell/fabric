package jif.translate;

import polyglot.ast.ArrayAccess;
import polyglot.ast.ArrayAccessAssign;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class ArrayAccessAssignToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        ArrayAccessAssign n = (ArrayAccessAssign)node();
        return rw.java_nf().ArrayAccessAssign(n.position(), (ArrayAccess)n.left(), n.operator(), n.right());
    }
}
