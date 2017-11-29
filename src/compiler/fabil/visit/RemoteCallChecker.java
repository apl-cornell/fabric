package fabil.visit;

import fabil.extension.FabILExt;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.visit.ErrorHandlingVisitor;

public class RemoteCallChecker extends ErrorHandlingVisitor {
  public RemoteCallChecker(Job job, TypeSystem ts, NodeFactory nf) {
    super(job, ts, nf);
  }

  protected FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  @Override
  protected Node leaveCall(Node n) throws SemanticException {
    return ext(n).checkRemoteCalls(this);
  }
}
