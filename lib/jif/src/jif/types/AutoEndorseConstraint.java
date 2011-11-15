package jif.types;

import jif.types.label.Label;

/** The auto endorse constraint. 
 */
public interface AutoEndorseConstraint extends Assertion {
    Label endorseTo();
    AutoEndorseConstraint endorseTo(Label endorseTo);
}
