package sif.html;

import sif.servlet.HTMLWriter;
import fabric.lang.security.Label;

/**
 * A Image -- an "img src=" tag.
 */
public class Img extends Tag {
    private final String url;
    
    public Img(Label L, Label E, String url_) {
        super(L, E, "img", null);
        this.url = url_;
    }
    public Img(Label L, Label E, String url_, String c) {
        super(L, E, "img", c);
        this.url = url_;
    }


    void writeOptions(HTMLWriter p) {
        p.print(" src=");
        p.print("\"");
        p.print(HTMLWriter.escape_URI(url));
        p.print("\"");
    }
    
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return ((o instanceof Img) && Node.jif$Instanceof(l, e, o));
    }
    
    public static Img jif$cast$sif_html_Hyperlink(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (Img)o;
        throw new ClassCastException();
    }    
}
