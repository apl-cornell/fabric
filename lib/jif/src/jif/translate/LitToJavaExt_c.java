package jif.translate;

import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

public class LitToJavaExt_c extends ToJavaExt_c {
    public Node toJava(JifToJavaRewriter rw) throws SemanticException {
        Lit n = (Lit)node();
        if (n instanceof BooleanLit) {
            BooleanLit l = (BooleanLit)n;
            return rw.java_nf().BooleanLit(n.position(), l.value());
        }
        else if (n instanceof ClassLit) {
            ClassLit l = (ClassLit)n;
            return rw.java_nf().ClassLit(n.position(), l.typeNode());            
        }
        else if (n instanceof FloatLit) {
            FloatLit l = (FloatLit)n;
            return rw.java_nf().FloatLit(n.position(), l.kind(), l.value());            
        }
        else if (n instanceof NullLit) {
            NullLit l = (NullLit)n;
            return rw.java_nf().NullLit(n.position());            
        }
        else if (n instanceof CharLit) {
            CharLit l = (CharLit)n;
            return rw.java_nf().CharLit(n.position(), l.value());            
        }
        else if (n instanceof IntLit) {
            IntLit l = (IntLit)n;
            return rw.java_nf().IntLit(n.position(), l.kind(), l.value());            
        }
        else if (n instanceof StringLit) {
            StringLit l = (StringLit)n;
            return rw.java_nf().StringLit(n.position(), l.value());            
        }
        else {
            throw new InternalCompilerError("Unexpected lit");
        }
    }
}
