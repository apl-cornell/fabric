package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;

public final class Body extends BlockContainer {
    public Body(Label L, Label E, Label cL, Label cE, Node contents) {
        super(L, E, "body", cL, cE, contents);
    }
    boolean isBigContainer() {
        return true;
    }    

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Body) && Node.jif$Instanceof(l, e, o);
    }
    
    /** Write everything between the open tag and the close tag. */
    // Overriding the method present in Container.java
    void writeContents(HTMLWriter p) {
    	super.writeContents(p);
        p.finishBody();
    }
    
    /** Write everything inside the tag after the tag name itself. */
    void writeOptions(HTMLWriter p) {
        if (p.produceLabelColorCodings()) {
            p.print(" onkeypress=\"actionDown(event);\" onkeyup=\"actionUp(event);\" onload=\"initialize();\"\n");
        }
    }
    
    

    public static Body jif$cast$sif_html_Body(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Body)o;
	throw new ClassCastException();
    }
}
