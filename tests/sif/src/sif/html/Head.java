package sif.html;

import sif.servlet.HTMLWriter;
import jif.lang.Label;

/**
 * The head of an HTML page.
 */
public final class Head extends Node {
    private final String title;
    private final String styleFile;
    private final String scriptFile;
    private final String colorCodingFile;
    
    /** Create a page head. Non-public to allow servlet to control
     *  creation through the createHead() method.
     * @param t The page title
     * @param styleFile The URL of the style file. May be null to signal absence.
     */	 
    public Head(Label L, Label E, String title, String styleFile, String scriptFile, String colorCodingFile) {
        super(L, E);
        this.title = title;
        this.styleFile = styleFile;
        this.scriptFile = scriptFile;
        this.colorCodingFile = colorCodingFile;
    }
    
    void writeImpl(HTMLWriter w) {
        w.begin(2);
        w.print("<head>");
        w.breakLine();
        w.print("<title>");
        w.escape(title);
        w.print("</title>");
        
        if (styleFile != null) {
            w.breakLine();
            w.print("<link ");
            w.begin();
            w.print("id=\"css\" rel=\"stylesheet\" type=\"text/css\" href=");
            w.printq(styleFile);
            w.print(" type=\"text/css\"/>");
            w.end();
        }
        if (scriptFile != null) {
            //w.unifiedBreak(w.getLevel());
            w.begin();
            w.print("<script ");
            //w.allowBreak(0, 2, " ");
            w.begin();
            w.print("language=\"JavaScript\"");
            //w.unifiedBreak(w.getLevel());
            w.print(" type=\"text/javascript\"");
            //w.unifiedBreak(w.getLevel());
            w.print(" src=");
            w.printq(scriptFile);
            //w.allowBreak(0, 2, " ");
            w.print(">");
            w.print("</script>");
            w.end();
        }
        if (colorCodingFile != null) {
            //w.unifiedBreak(w.getLevel());
            w.begin();
            w.print("<script ");
            //w.allowBreak(0, 2, " ");
            w.begin();
            w.print("language=\"JavaScript\"");
            //w.unifiedBreak(w.getLevel());
            w.print(" type=\"text/javascript\"");
            //w.unifiedBreak(w.getLevel());
            w.print(" src=");
            w.printq(colorCodingFile);
            //w.allowBreak(0, 2, " ");
            w.print(">");
            w.print("</script>");
            w.end();
        }
        
        w.end();
        w.breakLine();
        w.print("</head>");
        //w.breakLine();
    }
    public static boolean jif$Instanceof(Label l, Label e, Object o) {
        return (o instanceof Head) && Node.jif$Instanceof(l, e, o);
    }
    
    public static Head jif$cast$sif_html_Head(Label l, Label e, Object o) {
        if (o == null) return null; 
        if (jif$Instanceof(l, e, o))
            return (Head)o;
        throw new ClassCastException();
    }
}
