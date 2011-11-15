package jif.types.principal;

import jif.translate.TopPrincipalToJavaExpr_c;
import jif.types.JifTypeSystem;
import polyglot.main.Report;
import polyglot.types.TypeObject;
import polyglot.util.Position;

public class TopPrincipal_c extends Principal_c implements TopPrincipal {
    public TopPrincipal_c(JifTypeSystem ts, Position pos) {
        super(ts, pos, new TopPrincipalToJavaExpr_c());
    }
    
    public boolean isTopPrincipal() { return true; }
    public boolean isRuntimeRepresentable() { return true; }
    public boolean isCanonical() { return true; }
    
    public String toString() {
        if (Report.should_report(Report.debug, 2)) {
            return "<top principal>";
        }
        return "*";
    }
    
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        return o instanceof TopPrincipal_c;
    }
    
    public int hashCode() {
        return 451212;
    }
}
