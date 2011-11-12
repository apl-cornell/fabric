package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;

/**
 * A special character, e.g. "&nbsp;" or "&#8211;"
 */
public class SpecialChar extends Node {
    private final String name;

    public SpecialChar(Label L, Label E, String n) {
        super(L, E);
        name = n;
        for (int i = 0; i < n.length(); i++) {
            if (!Character.isLetterOrDigit(n.charAt(i)))
                throw new IllegalArgumentException("Invalid character in special character name");
        }
    }
    public SpecialChar(Label L, Label E, int n) {
        super(L, E);
        name = "#" + n;
    }

    final void writeImpl(HTMLWriter p) {
        p.print("&");
        p.print(name);
        p.print(";");
    }
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof SpecialChar) && Node.jif$Instanceof(l, e, o);
    }
    
    public static SpecialChar jif$cast$sif_html_SpecialChar(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (SpecialChar)o;
        throw new ClassCastException();
    }    
}
