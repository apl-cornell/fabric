package jif.extension;

import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.*;
import jif.types.label.ArgLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.ast.Formal;
import polyglot.ast.Node;
import polyglot.types.ArrayType;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/** The Jif extension of the <code>Formal</code> node. 
 * 
 *  @see polyglot.ast.Formal
 */
public class JifFormalExt extends Jif_c
{
    public JifFormalExt(ToJavaExt toJava) {
        super(toJava);
    }

    public Node labelCheck(LabelChecker lc) throws SemanticException {
        JifContext A = lc.jifContext();
	A = (JifContext) node().del().enterScope(A);
        
        // let's check some invariants
        Formal f = (Formal)node();
        JifFormalDel jfd = (JifFormalDel)f.del();
        JifTypeSystem ts = lc.jifTypeSystem();
        JifLocalInstance li = (JifLocalInstance)f.localInstance();
        

        if (!jfd.isCatchFormal()) {
//            System.err.println("Formal " + f.name() + " of " + A.currentCode());        
//            System.err.println("   type of node is " + f.declType()); 
//            System.err.println("   type of local instance is " + li.type()); 
//            System.err.println("   label of local instance is " + li.label()); 

            // the label of the type of the node should be an ArgLabel
            if (!(ts.labelOfType(f.declType()) instanceof ArgLabel)) {
                throw new InternalCompilerError("Invariant broken: " +
                                                "after disambiguation we expect the label of a " +
                                                "Formal's declared type to be an ArgLabel"); 
            }
            if (!(li.label() instanceof ArgLabel)) {
                throw new InternalCompilerError("Invariant broken: " +
                                                "after disambiguation we expect the label of a " +
                                                "Formal's local instance to be an ArgLabel"); 
            }
            ArgLabel al = (ArgLabel)li.label();        
            if (ts.isLabeled(li.type()) && !(ts.labelOfType(li.type()).equals(al.upperBound()))) {
                throw new InternalCompilerError("Invariant broken: " +
                                                "after disambiguation we expect the label of a " +
                                                "Formal's local instance's type to be the upper " +
                                                "bound of the ArgLabel for the formal."); 
            }
            
            checkVariance(lc, f);
        }
        
        return node();
    }

    /**
     * Ensure that covariant labels do not occur in the type of the formal for methods.
     * Constructors do not require this check.
     * @param lc 
     * @param f 
     * @throws SemanticException 
     */
    protected void checkVariance(LabelChecker lc, Formal f) throws SemanticException {
        if (!(lc.context().currentCode() instanceof ConstructorInstance)) {
            TypeSubstitutor tsb = new FormalVarianceLabelSubstr(f.position());
            tsb.rewriteType(f.declType());
        }
    }
    
    /**
     * Visitor to ensure that labels do not use
     * covariant labels in the wrong places 
     */    
    protected static class FormalVarianceLabelSubstr extends TypeSubstitutor {
        /* 
         * Don't check subst types, as the subtype checker will take care of those.
         */
        protected boolean recurseIntoSubstType(JifSubstType type) {
            return false;
        }
        public FormalVarianceLabelSubstr(Position pos) {
            super(new FormalVarianceLabelChecker(pos));
        }

    }


    /**
     * Checker to ensure that labels do not use
     * covariant labels in the wrong places
     */    
    protected static class FormalVarianceLabelChecker extends LabelSubstitution {
        private Position declPosition;

        FormalVarianceLabelChecker(Position declPosition) {
            this.declPosition = declPosition;
        }
        public Label substLabel(Label L) throws SemanticException {
            if (L.isCovariant()) {
                throw new SemanticDetailedException("Covariant labels cannot occur in the type of formal arguments.",
                                                    "The type of a formal argument cannot contain the covariant components such as " + 
                                                    L + ". ",
                                                    declPosition);
            }
            return L;
        }
    }    
}
