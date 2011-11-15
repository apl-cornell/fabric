package jif.translate;

import polyglot.ast.Node;
import polyglot.ast.SwitchBlock;
import polyglot.types.SemanticException;

public class SwitchBlockToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        SwitchBlock n = (SwitchBlock)node();
        return rw.java_nf().SwitchBlock(n.position(), n.statements());
    }
}
