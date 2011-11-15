package jif.types.principal;

import java.util.*;

import jif.translate.DisjunctivePrincipalToJavaExpr_c;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class DisjunctivePrincipal_c extends Principal_c implements DisjunctivePrincipal {
    private final Set disjuncts;
    public DisjunctivePrincipal_c(Collection disjuncts, 
                                  JifTypeSystem ts, Position pos) {
        super(ts, pos, new DisjunctivePrincipalToJavaExpr_c());
        this.disjuncts = new LinkedHashSet(disjuncts);
        if (disjuncts.size() < 2) {
            throw new InternalCompilerError("ConjunctivePrincipal should " +
                        "have at least 2 members");
        }
    }
    
    public boolean isRuntimeRepresentable() {
        for (Iterator iter = disjuncts.iterator(); iter.hasNext(); ) {
            Principal p = (Principal)iter.next();
            if (!p.isRuntimeRepresentable()) return false;
        }
        return true;
    }
    public boolean isCanonical() { 
        for (Iterator iter = disjuncts.iterator(); iter.hasNext(); ) {
            Principal p = (Principal)iter.next();
            if (!p.isCanonical()) return false;
        }
        return true;
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        String sep = ",";
        if (Report.should_report(Report.debug, 1)) {
            sb.append("<");
            sep = " or ";
        }
        else if (Report.should_report(Report.debug, 2)) {
            sb.append("<disjunction: ");
            sep = " or ";
        }
        for (Iterator iter = disjuncts.iterator(); iter.hasNext(); ) {
            sb.append(iter.next());
            if (iter.hasNext()) sb.append(sep);
        }
        if (Report.should_report(Report.debug, 1)) {
            sb.append(">");
        }
        else if (Report.should_report(Report.debug, 2)) {
            sb.append(">");
        }
        return sb.toString();
    }
    
    public boolean equalsImpl(TypeObject o) {
        if (this == o) return true;
        if (o instanceof DisjunctivePrincipal) {
            DisjunctivePrincipal that = (DisjunctivePrincipal)o;
            return this.disjuncts.equals(that.disjuncts());
        }
        return false;
    }
    
    public int hashCode() {
        return disjuncts.hashCode();
    }

    public Set disjuncts() {
        return disjuncts;
    }

    @Override
    public Principal subst(LabelSubstitution substitution)
        throws SemanticException {
      Set substDisjuncts = new HashSet();
      for (Iterator it = disjuncts.iterator(); it.hasNext();) {
        Principal disjunct = (Principal) it.next();
        substDisjuncts.add(disjunct.subst(substitution));
      }
      
      return new DisjunctivePrincipal_c(substDisjuncts, (JifTypeSystem) ts,
          position());
    }
}
