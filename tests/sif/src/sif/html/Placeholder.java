package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;

public class Placeholder extends Node {	
    
    public Placeholder(Label L, Label E) {
        super(L, E);
    }
    
    void writeImpl(HTMLWriter p) {
        if (p instanceof HTMLPrecomputerWriter) {
            ((HTMLPrecomputerWriter)p).addPlaceholderNode(p);
        }
    }
    
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Placeholder) && Node.jif$Instanceof(l, e, o);
    }

    public static Placeholder jif$cast$sif_html_Placeholder(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Placeholder)o;
	throw new ClassCastException();
    }
}
