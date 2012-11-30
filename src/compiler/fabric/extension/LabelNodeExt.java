package fabric.extension;

import jif.types.label.IntegPolicy;

public interface LabelNodeExt extends FabricExt {

  /**
   * @return an upper bound (in the trust ordering) on the referential authority
   *     policy of the referent described by this label. ("a+")
   */
  IntegPolicy refAuthUpperBound();

  /**
   * @return a lower bound (in the trust ordering) on the referential authority
   *     policy of the referent described by this label. ("a-")
   */
  IntegPolicy refAuthLowerBound();

  LabelNodeExt refAuthUpperBound(IntegPolicy upper);

  LabelNodeExt refAuthLowerBound(IntegPolicy lower);

}
