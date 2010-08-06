package sif.html;

import fabric.lang.security.Label;

/**
 * @author andru
 *
 * A <div> node.
 */
public final class Div extends BlockContainer {

    public Div(Label L, Label E, String class_, Label cL, Label cE, Node n) {
        super(L, E, "div", class_, cL, cE, n);
    }
    public Div(Label L, Label E, String class_, Node n) {
        super(L, E, "div", class_, L, E, n);
    }
    boolean isBigContainer() {
        return true;
    }
    

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Div) && Node.jif$Instanceof(l, e, o);
    }

    public static Div jif$cast$sif_html_Div(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Div)o;
	throw new ClassCastException();
    }
}
