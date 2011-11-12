package fabric.ast;

import java.util.ArrayList;
import java.util.List;

import jif.ast.JifCall_c;
import jif.ast.JifUtil;
import jif.types.JifContext;
import jif.types.LabeledType;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabric.types.FabricTypeSystem;

public class FabricCall_c extends JifCall_c implements FabricCall {
  protected Expr remoteWorker;
  protected Principal remoteWorkerPrincipal;
  
  public FabricCall_c(Position pos, Receiver target, Id name, List<Expr> args) {
    this(pos, target, name, null, args);
  }

  public FabricCall_c(Position pos, Receiver target, Id name,
      Expr remoteWorker, List<Expr> args) {
    super(pos, target, name, args);
    this.remoteWorker = remoteWorker;
  }
  
  protected FabricCall_c reconstruct(Receiver target, Id name,
      Expr remoteWorker, List<Expr> arguments) {
    FabricCall_c n = (FabricCall_c)super.reconstruct(target, name, arguments);
    
    if (remoteWorker != this.remoteWorker) {
      n = (FabricCall_c)n.copy();
      n.remoteWorker = remoteWorker;
    }
    
    return n;
  }

  @Override
  public Expr remoteWorker() {
    return remoteWorker;
  }
  
  @Override
  public FabricCall remoteWorker(Expr remoteWorker) {
    if (remoteWorker == this.remoteWorker) {
      return this;
    }
    
    FabricCall_c n = (FabricCall_c)this.copy();
    n.remoteWorker = remoteWorker;
    return n;
  }
  
  @Override
  public Node visitChildren(NodeVisitor v) {
    Receiver target = (Receiver) visitChild(this.target, v);
    Id name = (Id) visitChild(this.name, v);
    Expr remoteWorker = (Expr) visitChild(this.remoteWorker, v);
    @SuppressWarnings("unchecked")
    List<Expr> arguments = visitList(this.arguments, v);
    return reconstruct(target, name, remoteWorker, arguments);
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricCall c = (FabricCall)super.typeCheck(tc);

    if (c.remoteWorker() != null) {
      FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();
      JifContext context = (JifContext)tc.context();

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
      List<Type> argTypes = new ArrayList<Type>(c.methodInstance().formalTypes().size() + 1);
      argTypes.add(ts.Principal());
      argTypes.addAll(c.methodInstance().formalTypes());
      // TODO This assumes that all passes have already run on rcvrType
      // The assumption is not true if rcvrType is the same class as this call is present in
      if (rcvrType.methods(c.name() + "_remote", argTypes).isEmpty()) {
        // See RemoteCallWrapperAdder.java#leave for conditions for remotely callable methods
        throw new SemanticException("Illegal remote call \"" + c + "\", " +
        		            "because the dynamic label check might leak information." +
                "\nAlso, make sure the method you are trying to call is public and not static or abstract.",
        		            c.position());
      }
      
      return c.remoteWorkerPrincipal(JifUtil.exprToPrincipal(ts, c.remoteWorker(), context));
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
    FabricCall_c n = (FabricCall_c)copy();
    n.remoteWorkerPrincipal = p;
    return n;
  }
}
