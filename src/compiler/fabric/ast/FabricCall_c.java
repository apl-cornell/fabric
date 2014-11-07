package fabric.ast;

import java.util.ArrayList;
import java.util.List;

import jif.ast.JifCall_c;
import jif.types.JifContext;
import jif.types.LabeledType;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Copy;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabric.types.FabricTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class FabricCall_c extends JifCall_c implements FabricCall {
  protected Expr remoteWorker;
  protected Principal remoteWorkerPrincipal;

  @Deprecated
  public FabricCall_c(Position pos, Receiver target, Id name, List<Expr> args) {
    this(pos, target, name, args, null);
  }

  public FabricCall_c(Position pos, Receiver target, Id name, List<Expr> args,
      Ext ext) {
    this(pos, target, name, null, args, null);
  }

  @Deprecated
  public FabricCall_c(Position pos, Receiver target, Id name,
      Expr remoteWorker, List<Expr> args) {
    this(pos, target, name, remoteWorker, args, null);
  }

  public FabricCall_c(Position pos, Receiver target, Id name,
      Expr remoteWorker, List<Expr> args, Ext ext) {
    super(pos, target, name, args, ext);
    this.remoteWorker = remoteWorker;
  }

  protected <N extends FabricCall_c> N reconstruct(N n, Receiver target,
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
  public FabricCall remoteWorker(Expr remoteWorker) {
    return remoteWorker(this, remoteWorker);
  }

  protected <N extends FabricCall_c> N remoteWorker(N n, Expr remoteWorker) {
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
    FabricCall c = (FabricCall) super.typeCheck(tc);

    if (c.remoteWorker() != null) {
      FabricTypeSystem ts = (FabricTypeSystem) tc.typeSystem();
      JifContext context = (JifContext) tc.context();

      // The type must have a remote version.
      ReferenceType rcvrType;
      Type rcType = c.target().type();
      if (rcType instanceof LabeledType) {
        LabeledType lType = (LabeledType) rcType;
        rcvrType = lType.toReference();
      } else if (rcType instanceof ReferenceType) {
        rcvrType = (ReferenceType) rcType;
      } else {
        throw new InternalCompilerError("Stupid compiler");
      }
      List<Type> argTypes =
          new ArrayList<>(c.methodInstance().formalTypes().size() + 1);
      argTypes.add(ts.Principal());
      argTypes.addAll(c.methodInstance().formalTypes());
      if (rcvrType.methods(c.name() + "_remote", argTypes).isEmpty()) {
        // See RemoteCallWrapperAdder.java#leave for conditions for remotely
        // callable methods
        throw new SemanticException(
            "Illegal remote call \""
                + c
                + "\", "
                + "because the dynamic label check might leak information."
                + "\nAlso, make sure the method you are trying to call is public and not static or abstract.",
            c.position());
      }

      return c.remoteWorkerPrincipal(ts.exprToPrincipal(ts, c.remoteWorker(),
          context));
    }

    return c;
  }

  @Override
  public Principal remoteWorkerPrincipal() {
    return remoteWorkerPrincipal;
  }

  @Override
  public FabricCall remoteWorkerPrincipal(Principal p) {
    if (p == remoteWorkerPrincipal) return this;
    FabricCall_c n = (FabricCall_c) copy();
    n.remoteWorkerPrincipal = p;
    return n;
  }
}
