package sif.html;

import sif.servlet.HTMLWriter;
import jif.lang.Label;

/** A Text is a simple sequence of text. */
public final class Text extends Node {
    private final String contents;
    
    public Text(Label L, Label E, String s) {
        super(L, E);
        contents = s; 
    }
    public Text(Label L, Label E, int i) { 
        this(L, E, Integer.toString(i)); 
    }
    public Text(Label L, Label E, boolean b) {
        this(L, E, b ? "true" : "false");
    }

    public Text(Label L, Label E, char c) {
        this(L, E, Character.toString(c));
    }

    public Text(Label L, Label E, long l) {
        this(L, E, Long.toString(l));
    }
    public Text(Label L, Label E, float f) {
        this(L, E, Float.toString(f));
    }
    public Text(Label L, Label E, double d) {
        this(L, E, Double.toString(d));
    }
    
    void writeImpl(HTMLWriter w) { w.escape(contents); }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Text) && Node.jif$Instanceof(l, e, o);
    }

    public static Text jif$cast$sif_html_Text(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Text)o;
	throw new ClassCastException();
    }
    public String toString() {
        return "\"" + contents + "\"";
    }
    
}
