package sif.html;

import sif.servlet.HTMLWriter;
import jif.lang.Label;
import jif.lang.LabelUtil;

/** A Node represents part of an HTML document.
 *  It can be written out to an HTMLWriter in a
 *  somewhat pretty-printed style.
 **/
public abstract class Node {        
    final Label L;
    final Label E;
    
    Node(Label L, Label E) {
        this.L = L;
        this.E = E;
    }
    
    /** Write out HTML for this node to p. */
    public final void write(HTMLWriter p, Node parent) {
        p.enterNode(parent, this);
        this.writeImpl(p);
        p.leaveNode(parent, this);
    }

    /** Write out HTML for this node to p. */
    abstract void writeImpl(HTMLWriter p);
    
    public final Label getL() { return L; }
    public final Label getE() { return E; }
    
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        if (o instanceof Node) {
            Node that = (Node)o;
            return LabelUtil.$Impl.equivalentTo(that.L, l) && LabelUtil.$Impl.equivalentTo(that.E, e);
        }
        return false;
    }
    
    public static Node jif$cast$sif_html_Node(Label l, Label e, Object o) {
        if (o == null) return (Node)null;
        if (jif$Instanceof(l, e, o))
            return (Node)o;
        throw new ClassCastException();
    }
    public String toString() {
        return this.getClass().getName();
    }
}
