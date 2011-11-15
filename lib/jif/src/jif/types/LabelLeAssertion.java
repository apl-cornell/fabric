package jif.types;

import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import jif.translate.JifToJavaRewriter;
import jif.types.label.Label;

public interface LabelLeAssertion extends Assertion
{
    Label lhs();
    Label rhs();
    
    LabelLeAssertion lhs(Label lhs);
    LabelLeAssertion rhs(Label rhs);
    
    Expr toJava(JifToJavaRewriter rw) throws SemanticException;
}
