package fabric.types;

import jif.types.label.ConfPolicy;
import polyglot.types.Flags;
import polyglot.types.ReferenceType;
import polyglot.types.TypeObject;
import polyglot.types.TypeSystem;
import polyglot.util.Position;

/**
 *
 */
public class AccessPolicyInstance_c implements AccessPolicyInstance {

  protected ConfPolicy policy;
  protected ReferenceType container;
  protected Position pos;

  public AccessPolicyInstance_c(Position pos, ReferenceType ct,
      ConfPolicy policy) {
    this.container = ct;
    this.pos = pos;
    this.policy = policy;
  }

  @Override
  public Flags flags() {
    return Flags.NONE;
  }

  @Override
  public void setFlags(Flags flags) {
    throw new UnsupportedOperationException();
  }

  @Override
  public ReferenceType container() {
    return container;
  }

  @Override
  public void setContainer(ReferenceType container) {
    this.container = container;
  }

  @Override
  public boolean isCanonical() {
    return container.isCanonical();
  }

  @Override
  public TypeSystem typeSystem() {
    return container.typeSystem();
  }

  @Override
  public Position position() {
    return pos;
  }

  @Override
  public ConfPolicy policy() {
    return policy;
  }

  @Override
  public boolean equalsImpl(TypeObject t) {
    if (t == this) return true;

    if (t instanceof AccessPolicyInstance) {
      AccessPolicyInstance api = (AccessPolicyInstance) t;
      return api.container().equals(container) && api.policy().equals(policy);
    }
    return false;
  }

  @Override
  public AccessPolicyInstance copy() {
    return new AccessPolicyInstance_c(pos, container, policy);
  }
}
