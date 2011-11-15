package jif.types;

import jif.types.label.Label;
import polyglot.types.Type;

/** A labeled type. 
 */
public interface LabeledType extends Type
{
    Type typePart();
    LabeledType typePart(Type type);

    Label labelPart();
    LabeledType labelPart(Label L);
}
