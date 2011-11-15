package jif.translate;

import polyglot.ast.Labeled;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class LabeledToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Labeled n = (Labeled)node();
        return rw.java_nf().Labeled(n.position(), n.labelNode(), n.statement());
    }
}
