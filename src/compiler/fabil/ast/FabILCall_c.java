package fabil.ast;

import java.util.Iterator;
import java.util.List;

import polyglot.ast.Call_c;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

public class FabILCall_c extends Call_c implements FabILCall {
  protected Expr remoteWorker;

  public FabILCall_c(Position pos, Receiver target, Id name,
      List<Expr> arguments) {
    this(pos, target, name, null, arguments);
  }

  public FabILCall_c(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> arguments) {
    super(pos, target, name, arguments);
    this.remoteWorker = remoteWorker;
  }

  protected FabILCall_c reconstruct(Receiver target, Id name,
      Expr remoteWorker, List<Expr> arguments) {
    FabILCall_c n = (FabILCall_c) super.reconstruct(target, name, arguments);

    if (remoteWorker != this.remoteWorker) {
      n = (FabILCall_c) n.copy();
      n.remoteWorker = remoteWorker;
    }

    return n;
  }

  @Override
  public Expr remoteWorker() {
    return remoteWorker;
  }

  @Override
  public FabILCall remoteWorker(Expr remoteWorker) {
    if (remoteWorker == this.remoteWorker) {
      return this;
    }

    FabILCall_c n = (FabILCall_c) this.copy();
    n.remoteWorker = remoteWorker;
    return n;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node visitChildren(NodeVisitor v) {
    Receiver target = (Receiver) visitChild(this.target, v);
    Id name = (Id) visitChild(this.name, v);
    Expr remoteWorker = (Expr) visitChild(this.remoteWorker, v);
    List<Expr> arguments = visitList(this.arguments, v);
    return reconstruct(target, name, remoteWorker, arguments);
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

    for (@SuppressWarnings("unchecked")
    Iterator<Expr> i = arguments.iterator(); i.hasNext();) {
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
