package jif.types;

import jif.translate.JifToJavaRewriter;
import jif.translate.LabelLeAssertionToJavaExpr;
import jif.types.label.Label;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject_c;
import polyglot.util.Position;


public class LabelLeAssertion_c extends TypeObject_c implements LabelLeAssertion
{
    LabelLeAssertionToJavaExpr toJava;
    Label lhs;
    Label rhs;
    
    public LabelLeAssertion_c(JifTypeSystem ts, Label lhs, Label rhs,
            Position pos, LabelLeAssertionToJavaExpr toJava) {
        super(ts, pos);
	this.lhs = lhs;
	this.rhs = rhs;
	this.toJava = toJava;
    }

    public Label lhs() {
	return lhs;
    }

    public Label rhs() {
	return rhs;
    }
    
    public LabelLeAssertion lhs(Label lhs) {
        LabelLeAssertion_c n = (LabelLeAssertion_c) copy();
        n.lhs = lhs;
        return n;
    }
    
    public LabelLeAssertion rhs(Label rhs) {
        LabelLeAssertion_c n = (LabelLeAssertion_c) copy();
        n.rhs = rhs;
        return n;
    }

    public boolean isCanonical() {
	return true;
    }
    
    public String toString() {
        return lhs + " assert<= " + rhs;
    }

    @Override
    public Expr toJava(JifToJavaRewriter rw) throws SemanticException {
      return toJava.toJava(this, rw);
    }
}
