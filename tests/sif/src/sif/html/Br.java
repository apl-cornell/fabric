package sif.html;

import sif.servlet.HTMLWriter;
import jif.lang.Label;

/**
 * @author andru
 *
 * A <br> line-break element.
 */
public final class Br extends Node {
    public Br(Label L, Label E) {
        super(L, E);
    }

    void writeImpl(HTMLWriter p) {
        //p.allowBreak();
        p.print("<br/>");
        //p.allowBreak(0, 0, "");
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof Br) && Node.jif$Instanceof(l, e, o);
    }
    
    public static Br jif$cast$sif_html_Br(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (Br)o;
        throw new ClassCastException();
    }
    
}
