package jif.types.principal;

import jif.translate.BottomPrincipalToJavaExpr_c;
import jif.types.JifTypeSystem;
import polyglot.main.Report;
import polyglot.types.TypeObject;
import polyglot.util.Position;

public class BottomPrincipal_c extends Principal_c implements BottomPrincipal {
    public BottomPrincipal_c(JifTypeSystem ts, Position pos) {
        super(ts, pos, new BottomPrincipalToJavaExpr_c());
    }
    
    public boolean isBottomPrincipal() { return true; }
    public boolean isRuntimeRepresentable() { return true; }
    public boolean isCanonical() { return true; }
    
    public String toString() {
        if (Report.should_report(Report.debug, 2)) {
            return "<bottom principal>";
        }
        return "_";
    }
    
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        return o instanceof BottomPrincipal_c;
    }
    
    public int hashCode() {
        return 4212;
    }
}
