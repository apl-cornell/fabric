package sif.html;

import sif.servlet.HTMLWriter;
import jif.lang.Label;

/**
 * A Tag represents an HTML node that has a tag.
 * It may or may not have a corresponding end tag.
 **/
class Tag extends Node {	
    private final String tag;
    private final String class_;
    
    Tag(Label L, Label E, String t, String c) {
        super(L, E);
        tag = t;
        class_ = c;
    }
    
    /** Write out the tag, including an unmatched
     * p.begin().
     * @param p
     */
    final void writeTag(HTMLWriter p) {
        p.print("<");
        p.print(tag);
        if (class_ != null || p.currentClass() != null) {
            p.print(" class=");
            if (class_ != null) {
                p.printq(class_);
            } else {
                p.printq(p.currentClass());
            }
            p.setClass(null);
        }
        writeOptions(p);
        p.print(">");
    }
    /** Write everything after the tag. Must include
     * an extra p.end() to match writeTag(). */
    void writeRest(HTMLWriter p) {}
    
    void writeImpl(HTMLWriter p) {
        p.begin(2);        
        writeTag(p);
        p.unifiedBreak(1);
        writeRest(p);
        p.end();
        p.unifiedBreak(1);
        p.print("</" + getTag() + ">");
    }
    
    /** Write everything inside the tag after the tag name itself. */
    void writeOptions(HTMLWriter p) {}
    
    final String getTag() {
        return this.tag;    	
    }

    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof Tag) && Node.jif$Instanceof(l, e, o);
    }

    public static Tag jif$cast$sif_html_Tag(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (Tag)o;
	throw new ClassCastException();
    }
}
