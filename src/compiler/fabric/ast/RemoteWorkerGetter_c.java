package fabric.ast;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.Expr_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabric.types.FabricTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class RemoteWorkerGetter_c extends Expr_c implements RemoteWorkerGetter {
  protected Expr remoteWorkerName; // cannot be null

  @Deprecated
  public RemoteWorkerGetter_c(Position pos, Expr remoteWorkerName) {
    this(pos, remoteWorkerName, null);
  }

  public RemoteWorkerGetter_c(Position pos, Expr remoteWorkerName, Ext ext) {
    super(pos, ext);
    this.remoteWorkerName = remoteWorkerName;
  }

  protected <N extends RemoteWorkerGetter_c> N reconstruct(N n,
      Expr remoteWorkerName) {
    n = remoteWorkerName(n, remoteWorkerName);
    return n;
  }

  @Override
  public Expr remoteWorkerName() {
    return remoteWorkerName;
  }

  @Override
  public RemoteWorkerGetter remoteWorkerName(Expr expr) {
    return remoteWorkerName(this, expr);
  }

  protected <N extends RemoteWorkerGetter_c> N remoteWorkerName(N n,
      Expr remoteWorkerName) {
    if (n.remoteWorkerName == remoteWorkerName) return n;
    n = copyIfNeeded(n);
    n.remoteWorkerName = remoteWorkerName;
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Expr remoteWorkerName = (Expr) this.remoteWorkerName.visit(v);
    return reconstruct(this, remoteWorkerName);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    v.visitCFG(remoteWorkerName, this, EXIT);
    return succs;
  }

  @Override
  public Term firstChild() {
    return remoteWorkerName;
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();

    if (!remoteWorkerName.isTypeChecked()) {
      return this;
    }

    if (!ts.typeEquals(remoteWorkerName.type(), ts.String())) {
      throw new SemanticException("Remote worker name has to be a String.",
          remoteWorkerName.position());
    }

    return this.type(ts.RemoteWorker());
  }

  @Override
  public String toString() {
    return "worker$(" + remoteWorkerName + ")";
  }
}
