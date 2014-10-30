package fabil.ast;

import java.util.Iterator;
import java.util.List;

import polyglot.ast.Call_c;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.SemanticException;
import polyglot.util.Copy;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class FabILCall_c extends Call_c implements FabILCall {
  protected Expr remoteWorker;

  @Deprecated
  public FabILCall_c(Position pos, Receiver target, Id name,
      List<Expr> arguments) {
    this(pos, target, name, arguments, null);
  }

  public FabILCall_c(Position pos, Receiver target, Id name,
      List<Expr> arguments, Ext ext) {
    this(pos, target, name, null, arguments, ext);
  }

  @Deprecated
  public FabILCall_c(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> arguments) {
    this(pos, target, name, remoteWorker, arguments, null);
  }

  public FabILCall_c(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> arguments, Ext ext) {
    super(pos, target, name, arguments, ext);
    this.remoteWorker = remoteWorker;
  }

  protected <N extends FabILCall_c> N reconstruct(N n, Receiver target,
      Id name, Expr remoteWorker, List<Expr> arguments) {
    n = super.reconstruct(n, target, name, arguments);
    n = remoteWorker(n, remoteWorker);

    return n;
  }

  @Override
  public Expr remoteWorker() {
    return remoteWorker;
  }

  @Override
  public FabILCall remoteWorker(Expr remoteWorker) {
    return remoteWorker(this, remoteWorker);
  }

  protected <N extends FabILCall_c> N remoteWorker(N n, Expr remoteWorker) {
    if (n.remoteWorker == remoteWorker) return n;
    if (n == this) n = Copy.Util.copy(n);
    n.remoteWorker = remoteWorker;
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Receiver target = visitChild(this.target, v);
    Id name = visitChild(this.name, v);
    Expr remoteWorker = visitChild(this.remoteWorker, v);
    List<Expr> arguments = visitList(this.arguments, v);
    return reconstruct(this, target, name, remoteWorker, arguments);
  }

  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabILCall c = (FabILCall) super.typeCheck(tc);

    if (c.remoteWorker() != null) {
      if (!c.remoteWorker().type().isCanonical()) {
        return c;
      }

      if (c.methodInstance().flags().isStatic()) {
        throw new SemanticException(
            "Remotely calling static methods not supported yet.", c.position());
      }

      if (!c.methodInstance().flags().isPublic()) {
        throw new SemanticException(
            "Remotely calling non-public methods not supported yet.",
            c.position());
      }

      FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
      if (!ts.isSubtype(c.remoteWorker().type(), ts.RemoteWorker())) {
        // The expression after @ has to be a RemoteWorker
        throw new SemanticException(
            "Remote method invocations expect remote workers.", c
            .remoteWorker().position());
      }
    }

    return c;
  }

  @Override
  public String toString() {
    if (remoteWorker == null) {
      return super.toString();
    }

    StringBuffer sb = new StringBuffer();
    sb.append(targetImplicit ? "" : target.toString() + ".");
    sb.append(name);
    sb.append("@");
    sb.append(remoteWorker);
    sb.append("(");

    int count = 0;

    for (Iterator<Expr> i = arguments.iterator(); i.hasNext();) {
      if (count++ > 2) {
        sb.append("...");
        break;
      }

      Expr n = i.next();
      sb.append(n.toString());

      if (i.hasNext()) {
        sb.append(", ");
      }
    }

    sb.append(")");
    return sb.toString();
  }
}
