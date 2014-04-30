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

  protected RemoteWorkerGetter reconstruct(Expr remoteWorkerName) {
    if (this.remoteWorkerName != remoteWorkerName) {
      RemoteWorkerGetter_c n = (RemoteWorkerGetter_c) copy();
      n.remoteWorkerName = remoteWorkerName;
      return n;
    }
    return this;
  }

  @Override
  public Expr remoteWorkerName() {
    return remoteWorkerName;
  }

  @Override
  public RemoteWorkerGetter remoteWorkerName(Expr expr) {
    return reconstruct(expr);
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Expr remoteWorkerName = (Expr) this.remoteWorkerName.visit(v);
    return reconstruct(remoteWorkerName);
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
