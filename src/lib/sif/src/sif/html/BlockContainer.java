package sif.html;

import fabric.lang.security.Label;

/**
 * @author andru
 *
 * A container that is a block-level element like a
 * paragraph, and hence can have a line break placed
 * safely before or after it in the HTML.
 */
public abstract class BlockContainer extends Container {
    BlockContainer(Label L, Label E, String tag, Label cL, Label cE, Node body) {
        super(L, E, tag, cL, cE, body);
    }
    BlockContainer(Label L, Label E, Label cL, Label cE, Node body, String tag) {
        super(L, E, tag, cL, cE, body);
    }
    BlockContainer(Label L, Label E, String tag, String class_, Label cL, Label cE, Node body) {
        super(L, E, tag, class_, cL, cE, body);
    }
    BlockContainer(Label L, Label E, Label cL, Label cE, Node body, String tag, String class_) {
        super(L, E, tag, class_, cL, cE, body);
    }
    
    /**
     * @return true if this blockcontainer typically has lots of stuff in it.
     */
    boolean isBigContainer() {
        return false;
    }


    public static boolean jif$Instanceof(Label l, Label e, Object o) {
	return (o instanceof BlockContainer) && Node.jif$Instanceof(l, e, o);
    }

    public static BlockContainer jif$cast$sif_html_BlockContainer(Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(l, e, o))
	    return (BlockContainer)o;
	throw new ClassCastException();
    }
}
