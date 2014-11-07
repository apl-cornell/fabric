package fabric.ast;

import jif.ast.LabelNode;
import polyglot.ast.ClassMember;
import fabric.types.AccessPolicyInstance;

public interface AccessPolicy extends ClassMember {
  LabelNode policy();

  AccessPolicy policy(LabelNode policy);

  AccessPolicyInstance accessPolicyInstance();
}
