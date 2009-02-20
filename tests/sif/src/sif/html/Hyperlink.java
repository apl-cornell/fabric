package sif.html;

import sif.servlet.Action;
import sif.servlet.HTMLWriter;
import sif.servlet.Servlet;
import jif.lang.Label;
import jif.lang.Principal;
import jif.lang.PrincipalUtil;

/**
 * @author andru
 *
 * A Hyperlink -- an "a href=" tag.
 */
public class Hyperlink extends Container {
    private final String url;
    final Principal servletP;
    final Action action;
    
    public Hyperlink(Principal servletP, Label L, Label E, String url_, String s) {
        this(servletP, L, E, url_, L, E, new Text(L, E, s));
    }
    public Hyperlink(Principal servletP, Label L, Label E, String url_, Label cL, Label cE, Node n) {
        super(L, E, "a", cL, cE, n);
        this.servletP = servletP;
        url = url_;
        this.action = null;
    }
    public Hyperlink(Principal servletP, Label L, Label E, String url_, Node n) {
        super(L, E, "a", L, E, n);
        this.servletP = servletP;
        url = url_;
        this.action = null;
    }
    public Hyperlink(Principal servletP, Label L, Label E, Action a, String s) {
        this(servletP, L, E, a, L, E, new Text(L, E, s));
    }
    public Hyperlink(Principal servletP, Label L, Label E, Action a, Label cL, Label cE, Node n) {
        super(L, E, "a", cL, cE, n);
        this.servletP = servletP;
        this.action = a;
        this.url = null;
    }
    public Hyperlink(Principal servletP, Label L, Label E, Action a, Node n) {
        super(L, E, "a", L, E, n);
        this.servletP = servletP;
        this.action = a;
        this.url = null;
    }
    void writeOptions(HTMLWriter p) {
        p.print(" href=");
        p.print("\"");
        makeURLString(p);
        p.print("\"");
    }

    /**
     * print the URL string
     * @param p
     */
    void makeURLString(HTMLWriter p) {
        if (this.url != null) {
            if (Servlet.debug(3)) {
                Servlet.DEBUG.println("Making hyperlink for url " + this.url);
            }
            p.print(HTMLWriter.escape_URI(this.url));
        }
        else if (this.action != null) {
            if (Servlet.debug(3)) {
                Servlet.DEBUG.println("Making hyperlink for action " + this.action.getName() + " " + this.action.getClass());
            }
            p.addAction(this.action, this);
            p.printActionURL(this.action, this); 
        }
        else {
            if (Servlet.debug(3)) {
                Servlet.DEBUG.println("Making hyperlink for but did nothing");
            }
            
        }
    }
    
    public static boolean jif$Instanceof(Principal P, Label l, Label e, Object o) {
        if ((o instanceof Hyperlink) && Node.jif$Instanceof(l, e, o)) {
            Hyperlink that = (Hyperlink)o;
            return PrincipalUtil.$Impl.equivalentTo(that.servletP, P);
        }
        return false;
    }
    
    public static Hyperlink jif$cast$sif_html_Hyperlink(Principal P, Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(P, l, e, o))
            return (Hyperlink)o;
        throw new ClassCastException();
    }    
}
