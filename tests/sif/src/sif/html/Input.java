package sif.html;

import fabric.lang.security.Label;
import fabric.lang.Principal;
import fabric.lang.security.PrincipalUtil;
import sif.servlet.Servlet;

/**
 * @author andru
 *
 */
public final class Input {
    private final String name;
    public final Label inputLbl;
//    public final Label thisLabel;
    final Servlet servlet;
    private final Principal servletP;
    
    /** Construct an input with an automatically generated name. */
    public Input(Principal servletP, Servlet s, Label inputLabel) {
        this.inputLbl = inputLabel;
//        this.thisLabel = thisLabel;
        this.servlet = s;
        this.servletP = servletP;
    	this.name = "i" + s.generateNonce();
    }
    
    /** 
     * Construct an input with a given name. No two inputs in the same servlet
     * may have the same name. Throws an error if they do.
     * @param s the servlet
     * @param n the name of the input
     */
    public Input(Principal servletP, String n, Servlet s, Label inputLabel) {
        this.inputLbl = inputLabel;
//        this.thisLabel = thisLabel;
        this.servlet = s;
        this.servletP = servletP;
        this.name = n;
        if (s == null) throw new Error("Illegal servlet passed in");
        s.addNamedInput(this);
    }
    
    /** @return The name of the input. */
    public String getName() {
        return name;
    }
    
    public int hashCode() {
        return name.hashCode();
    }
    public static boolean jif$Instanceof(fabric.lang.Principal jif$servletP, Object o) {
        if (o instanceof Input) {
            Input that = (Input)o;
            return PrincipalUtil._Impl.equivalentTo(that.servletP, jif$servletP);
        }
        return false;
    }
          
    public static Input jif$cast$sif_html_Input(fabric.lang.Principal jif$servletP, Object o) {
        if (o == null) return (Input)null;
        if (jif$Instanceof(jif$servletP, o))
            return (Input)o;
        throw new ClassCastException();
        
    }
    
}
