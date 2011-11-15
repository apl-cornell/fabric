package jif.types;

import jif.types.label.Label;

public interface LabelLeAssertion extends Assertion
{
    Label lhs();
    Label rhs();
    
    LabelLeAssertion lhs(Label lhs);
    LabelLeAssertion rhs(Label rhs);
    
}
