package jif.translate;

import polyglot.ast.*;
import polyglot.types.SemanticException;

public class InitializerToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Initializer n = (Initializer) node();
        
        // if it is an instance initializer, we need to move the code to
        // the initializer method.
        if (!n.flags().isStatic()) {
            rw.addInitializer(n.body());
            Block empty = rw.java_nf().Block(n.position());
            n = rw.java_nf().Initializer(n.position(), n.flags(), empty);
        }
        else {
            n = rw.java_nf().Initializer(n.position(), n.flags(), n.body());
        }
        return n;
    }
}
