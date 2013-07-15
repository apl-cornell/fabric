package fabric.ast;

import fabric.types.AccessPolicyInstance;
import jif.ast.LabelNode;
import polyglot.ast.ClassMember;

/**
 * 
 */
public interface AccessPolicy extends ClassMember {
  /**
   * @return
   */
  LabelNode policy();

  /**
   * @return
   */
  AccessPolicyInstance accessPolicyInstance();
}
