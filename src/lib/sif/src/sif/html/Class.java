package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;

/**
 * 
 */
public final class Class extends Node {
    private final String class_;
    private final Node node;
    
    public Class(Label L, Label E, String c, Label cL, Label cE, Node n) {
        super(L, E);
        class_ = c;
        node = n;
    }

    void writeImpl(HTMLWriter p) {
        p.setClass(class_);
        if (node != null) node.write(p, this);
        p.setClass(null);		
    }
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof Class) && Node.jif$Instanceof(l, e, o);
    }
    
    public static Class jif$cast$sif_html_Class(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (Class)o;
        throw new ClassCastException();
    }
    
}
