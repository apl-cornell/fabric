package sif.html;

import sif.servlet.HTMLWriter;
import jif.lang.Label;

/**
 * Preformatted text, like <pre>example</example>
 *
 * @author andru
 *
 **/
public final class Pre extends Container {
    public Pre(Label L, Label E, Label cL, Label cE, Node n) {
        super(L, E, "pre", cL, cE, n);
    }
    public Pre(Label L, Label E, String text) {
        super(L, E, "pre", L, E, new Text(L, E, text));
    }
    void writeImpl(HTMLWriter p) {
        p.print("<" + getTag() + ">");
        p.noindent(true);
        if (contents != null) contents.write(p, this);
        p.noindent(false);
        //p.breakLine();
        p.print("</" + getTag() + ">");
        //p.breakLine();
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Pre) && Node.jif$Instanceof(l, e, o);
    }

    public static Pre jif$cast$sif_html_Pre(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Pre)o;
	throw new ClassCastException();
    }
}
