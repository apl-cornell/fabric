package fabric.types;

import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import polyglot.types.ReferenceType;

/**
 * This interface extends polyglot ReferenceTypes with an updateLabel and
 * accessPolicy.
 */

public interface FabricReferenceType extends ReferenceType {
  /**
   * Return the update label that objects of this type are enforced at. This
   * label is the join in the trust ordering of the update labels of every
   * field.
   */
  Label updateLabel();

  /**
   * Return the access label that objects of this type are enforced at. This
   * label is the join in the trust ordering of the access labels of every
   * field and the confidentiality projection of every method's begin label.
   */
  ConfPolicy accessPolicy();
}
