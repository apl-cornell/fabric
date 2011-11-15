package jif.ast;

import polyglot.ast.Ambiguous;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;

/** An ambiguous label node. */
public abstract class AmbLabelNode_c extends LabelNode_c
                                  implements LabelNode, Ambiguous
{
    public AmbLabelNode_c(Position pos) {
	super(pos);
    }

    public final boolean isDisambiguated() {
        return false;
    }

    /** Disambiguate the type of this node. */
    public abstract Node disambiguate(AmbiguityRemover ar)
	throws SemanticException;
}
