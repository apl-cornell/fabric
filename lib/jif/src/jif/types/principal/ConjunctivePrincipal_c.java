package jif.types.principal;

import java.util.*;

import jif.translate.ConjunctivePrincipalToJavaExpr_c;
import jif.translate.PrincipalToJavaExpr;
import jif.types.JifTypeSystem;
import jif.types.JifTypeSystem_c;
import jif.types.LabelSubstitution;
import jif.types.label.JoinLabel_c;
import jif.types.label.Label;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.TypeObject;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class ConjunctivePrincipal_c extends Principal_c implements ConjunctivePrincipal {
    private final Set conjuncts;
    public ConjunctivePrincipal_c(Collection conjuncts, 
                                  JifTypeSystem ts, Position pos, PrincipalToJavaExpr toJava) {
        super(ts, pos, toJava);
        this.conjuncts = new LinkedHashSet(conjuncts);
        if (conjuncts.size() < 2) {
            throw new InternalCompilerError("ConjunctivePrincipal should " +
                        "have at least 2 members");
        }
    }
    
    public boolean isRuntimeRepresentable() {
        for (Iterator iter = conjuncts.iterator(); iter.hasNext(); ) {
            Principal p = (Principal)iter.next();
            if (!p.isRuntimeRepresentable()) return false;
        }
        return true;
    }
    public boolean isCanonical() { 
        for (Iterator iter = conjuncts.iterator(); iter.hasNext(); ) {
            Principal p = (Principal)iter.next();
            if (!p.isCanonical()) return false;
        }
        return true;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        String sep = "&";
        if (Report.should_report(Report.debug, 1)) {
            sb.append("<");
            sep = " and ";
        }
        else if (Report.should_report(Report.debug, 2)) {
            sb.append("<conjunction: ");
            sep = " and ";
        }
        for (Iterator iter = conjuncts.iterator(); iter.hasNext(); ) {
            Principal p = (Principal)iter.next(); 
            if (p instanceof DisjunctivePrincipal) {
                sb.append('(');
                sb.append(p);
                sb.append(')');
            }
            else {
                sb.append(p);            
            }
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
        if (o instanceof ConjunctivePrincipal) {
            ConjunctivePrincipal that = (ConjunctivePrincipal)o;
            return this.conjuncts.equals(that.conjuncts());
        }
        return false;
    }
    
    public int hashCode() {
        return conjuncts.hashCode();
    }

    public Set conjuncts() {
        return Collections.unmodifiableSet(conjuncts);
    }
    
    public Principal simplify() {
        if (!this.isCanonical()) {
            return this;
        }
        
        Set needed = new LinkedHashSet();
        JifTypeSystem jts = (JifTypeSystem) ts;

        for (Iterator i = conjuncts.iterator(); i.hasNext(); ) {
            Principal ci = ((Principal) i.next()).simplify();
            
            if (ci.hasVariables()) {
                needed.add(ci);
            }
            else {
                boolean subsumed = false;
                
                for (Iterator j = needed.iterator(); j.hasNext(); ) {
                    Principal cj = (Principal) j.next();
                    
                    if (cj.hasVariables()) {
                        continue;
                    }

                    if (jts.actsFor(cj, ci)) {
                        subsumed = true;
                        break;
                    }
                    
                    if (jts.actsFor(ci, cj)) { 
                        j.remove();
                    }
                }
                
                if (! subsumed)
                    needed.add(ci);
            }
        }
        
        if (needed.equals(conjuncts)) {
            return this;
        }
        if (needed.size() == 1) {
            return (Principal)needed.iterator().next();
        }

        return new ConjunctivePrincipal_c(needed, (JifTypeSystem)ts, position(), toJava);
    }

    @Override
    public Principal subst(LabelSubstitution substitution)
        throws SemanticException {
      Set substConjuncts = new HashSet();
      for (Iterator it = conjuncts.iterator(); it.hasNext();) {
        Principal conjunct = (Principal) it.next();
        substConjuncts.add(conjunct.subst(substitution));
      }
      
      return new ConjunctivePrincipal_c(substConjuncts, (JifTypeSystem) ts,
          position(), toJava);
    }
    
}
