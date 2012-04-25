package sif.html;

import sif.servlet.Action;
import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.lang.security.PrincipalUtil;
import fabricated.util.Map;

/**
 *
 * A Hyperlink Request -- an "a href=....?p=v" tag.
 */
public final class HyperlinkRequest extends Hyperlink {
    final Map inputs;
    public HyperlinkRequest(Principal servletP, Label L, Label E, Action a, Map inputs, Label cL, Label cE, Node n) {
        super(servletP, L, E, a, cL, cE, n);
        this.inputs = inputs;
    }
    void makeURLString(HTMLWriter p) {
        p.addAction(this.action, this);
        p.printHyperlinkRequestURL(this.action, this); 
    }
    
    public Map getInputs() {
        return inputs;
    }
    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        if ((o instanceof HyperlinkRequest) && Node.jif$Instanceof(l, e, o)) {
            HyperlinkRequest that = (HyperlinkRequest)o;
            return PrincipalUtil._Impl.equivalentTo(that.servletP, P);
        }
        return false;
    }
    
    public static HyperlinkRequest jif$cast$sif_html_HyperlinkRequest(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (HyperlinkRequest)o;
        throw new ClassCastException();
    }    
}
