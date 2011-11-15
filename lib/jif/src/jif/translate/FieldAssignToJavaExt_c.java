package jif.translate;

import polyglot.ast.Field;
import polyglot.ast.FieldAssign;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class FieldAssignToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        FieldAssign n = (FieldAssign)node();
        return rw.java_nf().FieldAssign(n.position(), (Field)n.left(), n.operator(), n.right());
    }
}
