package jif.translate;

import polyglot.ast.ClassBody;
import polyglot.ast.Node;
import polyglot.types.SemanticException;


public class ClassBodyToJavaExt_c extends ToJavaExt_c {

    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        ClassBody cb = (ClassBody)node();
        return rw.java_nf().ClassBody(cb.position(), cb.members());
    }
}
