package sif.html;

import jif.lang.Label;

/**
 * @author andru
 *
 * A Paragraph is an HTML paragraph.
 */
public final class Paragraph extends BlockContainer {
    public Paragraph(Label L, Label E, Label cL, Label cE, Node n) {
        super(L, E, "p", cL, cE, n);
    }
    public Paragraph(Label L, Label E, Node n) {
        super(L, E, "p", L, E, n);
    }
    public Paragraph(Label L, Label E, String text) {
        super(L, E, "p", L, E, new Text(L, E, text));
    }
    boolean isBigContainer() {
        return true;
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Paragraph) && Node.jif$Instanceof(l, e, o);
    }

    public static Paragraph jif$cast$sif_html_Paragraph(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Paragraph)o;
	throw new ClassCastException();
    }
}
