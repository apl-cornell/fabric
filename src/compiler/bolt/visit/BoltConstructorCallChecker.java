package bolt.visit;

import polyglot.ast.ConstructorCall;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.visit.ContextVisitor;
import polyglot.visit.NodeVisitor;

/**
 * Ensures that constructor calls only occur within constructors.
 */
public class BoltConstructorCallChecker extends ContextVisitor {

  public BoltConstructorCallChecker(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  @Override
  protected NodeVisitor enterCall(Node n) throws SemanticException {
    if (n instanceof ConstructorCall) {
      if (!(context().currentCode() instanceof ConstructorInstance)) {
        ConstructorCall cc = (ConstructorCall) n;
        throw new SemanticException(
            "Call to " + cc.kind().toString() + " must occur in a constructor.",
            cc.position());
      }
    }

    return this;
  }
}
