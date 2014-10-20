package fabric.types;

import jif.types.label.ConfPolicy;
import polyglot.types.MemberInstance;

/**
 *
 */
public interface AccessPolicyInstance extends MemberInstance {
  ConfPolicy policy();
}
