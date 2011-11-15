package jif.types.label;

import java.util.LinkedHashSet;
import java.util.Set;

import jif.types.LabelSubstitution;
import jif.types.principal.Principal;
import jif.types.principal.VarPrincipal;
import polyglot.types.SemanticException;

/**
 * This class is used to implement
 * {@link Label#variables() Label.variables()}. It constructs a set of
 * <code>Variable</code>s.
 */
public class VariableGatherer extends LabelSubstitution {
    public final Set variables = new LinkedHashSet();

    public Label substLabel(Label L) throws SemanticException {
        if (L instanceof VarLabel) {
            variables.add(L);
        }
        return L;
    }
    public Principal substPrincipal(Principal p) throws SemanticException {
        if (p instanceof VarPrincipal) {
            variables.add(p);
        }
        return p;
    }

}