package jif.translate;

import jif.types.label.DynamicLabel;
import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

public class DynamicLabelToJavaExpr_c extends LabelToJavaExpr_c {
    public Expr toJava(Label label, JifToJavaRewriter rw) throws SemanticException {
        DynamicLabel L = (DynamicLabel) label;
        return rw.qq().parseExpr(L.path().exprString());
    }
}
