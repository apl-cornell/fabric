package jif.ast;

import polyglot.ast.*;

/** This class represents a labeled type node. 
 */
public interface LabeledTypeNode extends TypeNode
{
    TypeNode typePart();
    LabeledTypeNode typePart(TypeNode typePart);

    LabelNode labelPart();
    LabeledTypeNode labelPart(LabelNode labelPart);
}
