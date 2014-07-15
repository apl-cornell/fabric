package fabil.visit;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.visit.ErrorHandlingVisitor;
import polyglot.visit.NodeVisitor;
import fabil.ast.AbortStmt;
import fabil.ast.Atomic;
import fabil.ast.RetryStmt;

public class AbortRetryChecker extends ErrorHandlingVisitor {
  protected boolean inAtomic = false;
  protected boolean hasAbort = false;

  public AbortRetryChecker(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  @Override
  protected NodeVisitor enterCall(Node n) {
    if (n instanceof Atomic) {
      AbortRetryChecker v = (AbortRetryChecker) this.copy();
      v.inAtomic = true;
      return v;
    }
    return this;
  }

  @Override
  protected Node leaveCall(Node n) throws SemanticException {
    if (n instanceof AbortStmt) {
      if (!inAtomic) {
        throw new SemanticException(
            "Abort is not statically enclosed in an atomic block.",
            n.position());
      }
      hasAbort = true;
    } else if (n instanceof RetryStmt) {
      if (!inAtomic) {
        throw new SemanticException(
            "Retry is not statically enclosed in an atomic block.",
            n.position());
      }
    } else if (n instanceof Atomic) {
      Atomic atomic = (Atomic) n;
      if (hasAbort) {
        return atomic.mayAbort(true);
      }
      hasAbort = false;
    }
    return n;
  }
}
