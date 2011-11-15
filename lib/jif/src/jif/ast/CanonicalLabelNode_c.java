package jif.ast;

import jif.extension.LabelTypeCheckUtil;
import jif.types.JifTypeSystem;
import jif.types.label.Label;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.TypeChecker;

/** An implementation of the <code>CanonicalLabelNode</code> interface.
 */
public class CanonicalLabelNode_c extends LabelNode_c implements CanonicalLabelNode
{
    public CanonicalLabelNode_c(Position pos, Label label) {
	super(pos, label);
    }
    public boolean isDisambiguated() {
        return true;
    }    
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        if (!this.label().isCanonical()) {
            // label should be canonical by the time we start typechecking.
            throw new InternalCompilerError(this.label() + " is not canonical.");
        }
        LabelTypeCheckUtil ltcu = ((JifTypeSystem)tc.typeSystem()).labelTypeCheckUtil(); 
        ltcu.typeCheckLabel(tc, label());        
        return super.typeCheck(tc);
    }
}
