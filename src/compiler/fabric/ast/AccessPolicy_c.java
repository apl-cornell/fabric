package fabric.ast;

import java.util.List;

import jif.ast.LabelNode;
import jif.types.label.ConfPolicy;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.ast.Term_c;
import polyglot.types.MemberInstance;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabric.types.AccessPolicyInstance;
import fabric.types.FabricParsedClassType;
import fabric.types.FabricTypeSystem;

/**
 * 
 */
public class AccessPolicy_c extends Term_c implements AccessPolicy {
  protected LabelNode policy;
  protected AccessPolicyInstance accessPolicyInstance;

  /**
   * @param pos
   */
  public AccessPolicy_c(Position pos, LabelNode policy) {
    super(pos);
    this.policy = policy;
  }

  @Override
  public LabelNode policy() {
    return policy;
  }

  public AccessPolicy policy(LabelNode policy) {
    if (policy == this.policy) return this;
    AccessPolicy_c n = (AccessPolicy_c) copy();
    n.policy = policy;
    return n;
  }

  @Override
  public MemberInstance memberInstance() {
    return accessPolicyInstance;
  }

  @Override
  public AccessPolicyInstance accessPolicyInstance() {
    return accessPolicyInstance;
  }

  public AccessPolicy accessPolicyInstance(AccessPolicyInstance api) {
    if (api == this.accessPolicyInstance) return this;
    AccessPolicy_c n = (AccessPolicy_c) copy();
    n.accessPolicyInstance = api;
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    LabelNode policy = (LabelNode) visitChild(this.policy, v);
    return reconstruct(policy);
  }

  protected Node reconstruct(LabelNode policy) {
    if (this.policy != policy) {
      AccessPolicy_c n = (AccessPolicy_c) copy();
      n.policy = policy;
      return n;
    }
    return this;
  }

  @Override
  public Term firstChild() {
    return null;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    return succs;
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();

    FabricParsedClassType ct =
        (FabricParsedClassType) tc.context().currentClass();

    if (ct == null) {
      return this;
    }
    if (!policy.label().isCanonical())
      throw new InternalCompilerError("Access policy is not canonical");
    ConfPolicy pol = ts.confProjection(policy.label());
    AccessPolicyInstance api = ts.accessPolicyInstance(position(), ct, pol);
    ct.setAccessPolicy(pol);
    return accessPolicyInstance(api);
  }
}
