package jif.ast;

import jif.types.JifTypeSystem;
import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.TypeBuilder;

/**
 * A <code>ConstArrayTypeNode</code> is a type node for a non-canonical
 * const array type.
 */
public class ConstArrayTypeNode_c extends ArrayTypeNode_c implements ConstArrayTypeNode
{
    public ConstArrayTypeNode_c(Position pos, TypeNode base) {
        super(pos, base);
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem)tb.typeSystem();
        return type(ts.constArrayOf(position(), base.type()));
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        JifTypeSystem ts = (JifTypeSystem)ar.typeSystem();
        NodeFactory nf = ar.nodeFactory();
        if (! base.isDisambiguated()) {
            return this;
        }

        Type baseType = base.type();

        if (! baseType.isCanonical()) {
            return this;
        }

        return nf.CanonicalTypeNode(position(),
                                    ts.constArrayOf(position(), baseType));
    }


    public String toString() {
        return base.toString() + "const []";
    }
}
