package jif.translate;

import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class IdToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Id n = (Id) node();
        return rw.nodeFactory().Id(n.position(), n.id());
    }
}
