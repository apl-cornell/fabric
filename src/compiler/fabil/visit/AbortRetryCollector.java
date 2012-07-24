package fabil.visit;

import java.util.List;

import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;
import fabil.ast.AbortStmt;
import fabil.ast.Atomic;
import fabil.ast.RetryStmt;

public class AbortRetryCollector extends NodeVisitor {
  protected List<AbortStmt> aborts;
  protected List<RetryStmt> retries;

  public AbortRetryCollector(List<AbortStmt> aborts, List<RetryStmt> retries) {
    this.aborts = aborts;
    this.retries = retries;
  }

  public List<AbortStmt> getAborts() {
    return aborts;
  }

  public List<RetryStmt> getRetries() {
    return retries;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof AbortStmt) {
      aborts.add((AbortStmt) n);
    } else if (n instanceof RetryStmt) {
      retries.add((RetryStmt) n);
    } else if (n instanceof Atomic) {
      // Only abort/retry the innermost atomic block.
      aborts.clear();
      retries.clear();
    }
    return n;
  }
}
