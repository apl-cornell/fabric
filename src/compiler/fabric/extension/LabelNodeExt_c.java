package fabric.extension;

import jif.ast.Jif_c;
import jif.translate.ToJavaExt;
import jif.types.label.IntegPolicy;

public class LabelNodeExt_c extends Jif_c implements LabelNodeExt {
  /**
   * An upper bound (in the trust ordering) on the referential authority policy
   * of the referent described by this label. ("a+")
   */
  protected IntegPolicy refAuthUpper;

  /**
   * A lower bound (in the trust ordering) on the referential authority policy
   * of the referent described by this label. ("a-")
   */
  protected IntegPolicy refAuthLower;

  public LabelNodeExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public IntegPolicy refAuthUpperBound() {
    return refAuthUpper;
  }

  @Override
  public IntegPolicy refAuthLowerBound() {
    return refAuthLower;
  }

  @Override
  public LabelNodeExt refAuthUpperBound(IntegPolicy upper) {
    LabelNodeExt_c n = copy();
    n.refAuthUpper = upper;
    return n;
  }

  @Override
  public LabelNodeExt refAuthLowerBound(IntegPolicy lower) {
    LabelNodeExt_c n = copy();
    n.refAuthLower = lower;
    return n;
  }

  @Override
  public LabelNodeExt_c copy() {
    return (LabelNodeExt_c) super.copy();
  }
}
