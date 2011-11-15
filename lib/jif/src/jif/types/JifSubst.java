package jif.types;

import java.util.List;

import jif.types.label.Label;
import jif.types.principal.Principal;
import polyglot.ext.param.types.Subst;

public interface JifSubst extends Subst
{
    public Assertion substConstraint(Assertion constraint);
    public Label substLabel(Label label);
    // public Label substLabel(Label label, Label thisL);
    public Principal substPrincipal(Principal principal);

    public List substConstraintList(List constraints);
    public List substLabelList(List labels);
    public List substPrincipalList(List principals);

    public Param get(ParamInstance pi);
    public void put(ParamInstance pi, Param param);
}
