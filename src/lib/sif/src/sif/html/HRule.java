package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;

/**
 * @author andru
 *
 * A horizontal rule: <hr>
 */
public final class HRule extends Node {
    public HRule(Label L, Label E) {
        super(L, E);
    }
    void writeImpl(HTMLWriter p) {
        p.print("<hr/>");
        //p.breakLine();
    }
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof HRule) && Node.jif$Instanceof(l, e, o);
    }
    
    public static HRule jif$cast$sif_html_HRule(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (HRule)o;
        throw new ClassCastException();
    }    
}
