package sif.html;

import sif.servlet.Servlet;
import jif.lang.Label;
import jif.lang.Principal;

/** An input that allows typing in a password. */
public final class PasswordInput extends TextInput {
    public PasswordInput(Principal servletP, Label L, Label E, Servlet s, Label inputLabel, int size, String initial) {
        super(servletP, L, E, s, inputLabel, size, initial);
    }
    public PasswordInput(Principal servletP, Label L, Label E, Input input, int size, String initial) {
        super(servletP, L, E, input, size, initial);
    }
    String typeName() {return "password";}

    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        return ((o instanceof PasswordInput) && InputNode.jif$Instanceof(P, l, e, o));
    }

    public static PasswordInput jif$cast$sif_html_PasswordInput(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (PasswordInput)o;
        throw new ClassCastException();
    }
    
}
