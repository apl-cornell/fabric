package sif.html;

import sif.servlet.Action;
import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;
import fabric.lang.security.Principal;
import fabric.lang.security.PrincipalUtil;

/** An OffsiteForm contains Inputs and generates requests to be submitted to
 * an external site. */
public final class OffsiteForm extends BlockContainer {
    private final String action;
    private final Principal servletP;
    private final boolean useGetMethod;
    
    public OffsiteForm(Principal servletP, Label L, Label E, String action_, boolean useGetMethod, Label cL, Label cE, Node n) {
        super(L, E, "form", cL, cE, n);
        this.servletP = servletP;
        action = action_;
	this.useGetMethod = useGetMethod;
    }
    void writeOptions(HTMLWriter p) {
        p.print(" method=" + (useGetMethod ? "GET" : "POST"));
        p.print(" enctype=\"multipart/form-data\"");
        //p.breakLine();
        p.print(" action=");
        p.print("\"");
        p.print(HTMLWriter.escape_URI(action));
        p.print("\"");
    }
    
    void writeContents(HTMLWriter p) {
        // p.breakLine();
        if (contents != null)
            contents.write(p, this);
    }
    boolean isBigContainer() {
        return true;
    }
    
    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
	if ((o instanceof OffsiteForm) && Node.jif$Instanceof(l, e, o)) {
	    OffsiteForm that = (OffsiteForm)o;
	    return PrincipalUtil._Impl.equivalentTo(that.servletP, P);
	}
    return false;
    }

    public static OffsiteForm jif$cast$sif_html_OffsiteForm(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
	if (jif$Instanceof(P, l, e, o))
	    return (OffsiteForm)o;
	throw new ClassCastException();
    }
}
