package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;

/**
 * A Radio Button input. Any of the radio buttons may be queried to find
 * out the value of the button that was selected.
 * 
 * @author andru
 *
 */
public final class RadioButton extends InputNode {
    private final boolean checked;
    public RadioButton(Principal servletP, Label L, Label E, Input i, String value, boolean checked_) {
        super(servletP, L, E, i);		
        checked = checked_;
    }

    public RadioButton(Principal servletP, Label L, Label E, String name, String value, boolean checked_) {
        super(servletP, L, E, name);
        checked = checked_;
    }

    void writeImpl(HTMLWriter p) {
        p.print("<input type=radio");
        p.print(" name=");
        p.printq(getName());
        if (checked) p.print(" checked");
        p.print(" />");
    }
    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        return ((o instanceof RadioButton) && InputNode.jif$Instanceof(P, l, e, o));
    }

    public static RadioButton jif$cast$sif_html_RadioButton(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (RadioButton)o;
        throw new ClassCastException();
    }
    
}
