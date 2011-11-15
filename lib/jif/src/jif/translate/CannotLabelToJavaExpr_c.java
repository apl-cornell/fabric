package jif.translate;

import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;

public class CannotLabelToJavaExpr_c extends LabelToJavaExpr_c {
    public Expr toJava(Label L, JifToJavaRewriter rw) throws SemanticException {
        throw new InternalCompilerError(L.position(),
                                        "Cannot translate " + L + " to Java.");
    }
}
