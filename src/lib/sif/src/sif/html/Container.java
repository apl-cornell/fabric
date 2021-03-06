package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;

/**
 * @author andru
 *
 * A container is an HTML form that starts with a
 * begin tag and ends with an end tag. This class has
 * package scope to prevent arbitrary use.
 */
abstract class Container extends Tag {
    protected final Node contents;
      
    Container(Label L, Label E, String tag_, Label cL, Label cE, Node n) {
        super(L, E, tag_, null);
        this.contents = n;
    }
    
    Container(Label L, Label E, String tag_, String class_, Label cL, Label cE, Node n) {
        super(L, E, tag_, class_);
        this.contents = n;
    }
    
    void writeOptions(HTMLWriter p) {
        // default: no options.
    }
    
    /** Write everything between the open tag and the close tag. */
    void writeContents(HTMLWriter p) {
        if (contents != null)
            contents.write(p, this);
    }
    
    final void writeRest(HTMLWriter p) {
        writeContents(p);
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Container) && Node.jif$Instanceof(l, e, o);
    }

    public static Container jif$cast$sif_html_Container(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Container)o;
	throw new ClassCastException();
    }
}
