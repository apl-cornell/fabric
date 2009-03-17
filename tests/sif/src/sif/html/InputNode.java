package sif.html;

import sif.servlet.Servlet;
import fabric.lang.Principal;
import jif.lang.Label;
import jif.lang.PrincipalUtil;


/**
 * @author andru
 *
 * An Input node is one that accepts user input. It
 * must occur within a form. There are several kinds
 * of Input nodes. Every input is associated with a
 * servlet. Input nodes should result in HTML decorations
 * that describe confidentiality requirements.
 * XXX Currently this last is not implemented.
 */
public abstract class InputNode extends Node {
    public final Input input;
    private final Principal servletP;
    
    InputNode(Principal servletP, Label L, Label E, Input i) {
        super(L, E);
        this.servletP = servletP;
        input = i;
    }
    InputNode(Principal servletP, Label L, Label E, Servlet s, Label inputLabel) {
        this(servletP, L, E, new Input(servletP, s, inputLabel));
    }
    
    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        if ((o instanceof InputNode) && Node.jif$Instanceof(l, e, o)) {
            InputNode that = (InputNode)o;
            return PrincipalUtil._Impl.equivalentTo(that.servletP, P);
        }
        return false;
    }

    public static InputNode jif$cast$sif_html_InputNode(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (InputNode)o;
        throw new ClassCastException();
    }
    
}
