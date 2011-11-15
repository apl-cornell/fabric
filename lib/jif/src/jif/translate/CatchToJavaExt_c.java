package jif.translate;

import polyglot.ast.Catch;
import polyglot.ast.Node;
import polyglot.types.SemanticException;

public class CatchToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Catch b = (Catch) node();
        return rw.java_nf().Catch(b.position(), b.formal(), b.body());
    }
}
