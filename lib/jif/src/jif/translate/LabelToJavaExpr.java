package jif.translate;

import java.io.Serializable;

import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public interface LabelToJavaExpr extends Serializable {
    public Expr toJava(Label label, JifToJavaRewriter rw) throws SemanticException;
}
