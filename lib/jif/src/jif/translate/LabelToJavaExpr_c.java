package jif.translate;

import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

public abstract class LabelToJavaExpr_c implements LabelToJavaExpr {
    public Expr toJava(Label label, JifToJavaRewriter rw) throws SemanticException {
        throw new InternalCompilerError("Should never be called: " + 
                                        label + " :: " + 
                                        label.getClass().getName());
    }
}
