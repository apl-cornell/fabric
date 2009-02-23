package fabric.ast;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.Expr_c;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;

public class RemoteClientGetter_c extends Expr_c implements RemoteClientGetter {
  protected Expr remoteClientName; // cannot be null
  
  public RemoteClientGetter_c(Position pos, Expr remoteClientName) {
    super(pos);
    this.remoteClientName = remoteClientName;
  }
  
  protected RemoteClientGetter reconstruct(Expr remoteClientName) {
    if (this.remoteClientName != remoteClientName) {
      RemoteClientGetter_c n = (RemoteClientGetter_c)copy();
      n.remoteClientName = remoteClientName;
      return n;
    }
    return this;
  }
  
  public Expr remoteClientName() {
    return remoteClientName;
  }

  public RemoteClientGetter remoteClientName(Expr expr) {
    return reconstruct(expr);
  }
  
  @Override
  public Node visitChildren(NodeVisitor v) {
    Expr remoteClientName = (Expr)this.remoteClientName.visit(v);
    return reconstruct(remoteClientName);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    v.visitCFG(remoteClientName, this, EXIT);
    return succs;
  }

  public Term firstChild() {
    return remoteClientName;
  }
}
