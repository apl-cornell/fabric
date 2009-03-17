package sif.html;

import sif.servlet.Servlet;
import fabric.lang.Principal;
import jif.lang.PrincipalUtil;

/**
 * To create radio buttions, a Radio is needed.
 */
public final class Radio {
    private final String name;
    private final Principal servletP;
    public Radio(Principal servletP, Servlet s) {
        this.servletP = servletP;
        name = s.generateNonce();
    }

    public static boolean jif$Instanceof(Principal P, Object o) {
        if (o instanceof Radio) {
            Radio that = (Radio)o;
            return PrincipalUtil._Impl.equivalentTo(that.servletP, P);
        }
        return false;
    }
    
    public static Radio jif$cast$sif_html_Radio(Principal P, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, o))
            return (Radio)o;
        throw new ClassCastException();
    }
    
}
