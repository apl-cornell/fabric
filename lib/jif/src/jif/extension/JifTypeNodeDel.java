package jif.extension;

import jif.types.JifParsedPolyType;
import jif.types.JifTypeSystem;
import jif.types.LabelSubstitution;
import jif.types.TypeSubstitutor;
import jif.types.label.Label;
import jif.types.label.ThisLabel;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;

/** The Jif extension of the <code>TypeNode</code> node. 
 */
public class JifTypeNodeDel extends JifJL_c
{
    public JifTypeNodeDel() {
    }

    /**
     * Prevent an uninstantiated polymorphic class from being used anywhere, 
     * and check that the "this" label is not used in a static context.
     */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        TypeNode tn = (TypeNode) super.typeCheck(tc);
        
        JifTypeSystem ts = (JifTypeSystem)tc.typeSystem();
        if (ts.isLabeled(tn.type())) {
            Label L = ts.labelOfType(tn.type());
            ts.labelTypeCheckUtil().typeCheckLabel(tc, L);
        }
        
        Type unlabeledType = ts.unlabel(tn.type()); 
        
        if (unlabeledType instanceof JifParsedPolyType) {
            JifParsedPolyType jppt = (JifParsedPolyType)unlabeledType;
            if (jppt.params().size() > 0) {
                throw new SemanticException("The polymorphic class " + 
                        jppt.name() + " must be instantiated.",
                        tn.position());
            }
        }
        
        if (tc.context().inStaticContext()) {
            // We're in a static context.
            // Make sure that the label on the type does not mention the
            // "this" label.
            // We need to have check this here, as well as in 
            // AmbThisLabelNode_c because the static context is not set 
            // correctly when disambiguating method signatures.
            TypeSubstitutor tsb = new TypeSubstitutor(new StaticLabelChecker(tn.position()));
            tsb.rewriteType(tn.type());
            
        }
        
        ts.labelTypeCheckUtil().typeCheckType(tc, unlabeledType);                
        return tn;
    }

    /**
     * Checker to ensure that labels in a static context do not use
     * the This label
     */    
    protected static class StaticLabelChecker extends LabelSubstitution {
        private Position position;

        StaticLabelChecker(Position position) {
            this.position = position;
        }
        public Label substLabel(Label L) throws SemanticException {
            if (L instanceof ThisLabel) { 
                throw new SemanticException("The label \"this\" cannot be used " +
                    "in a static context.", 
                        position);
            }
            return L;
        }
    }
    
}
