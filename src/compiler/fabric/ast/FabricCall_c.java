package fabric.ast;

import java.util.List;

import fabric.types.FabricTypeSystem;

import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import jif.ast.JifCall_c;
import jif.ast.JifUtil;
import jif.types.JifContext;
import jif.types.principal.Principal;

public class FabricCall_c extends JifCall_c implements FabricCall {
  protected Expr remoteClient;
  protected Principal remoteClientPrincipal;
  
  @SuppressWarnings("unchecked")
  public FabricCall_c(Position pos, Receiver target, Id name, List args) {
    this(pos, target, name, null, args);
  }

  @SuppressWarnings("unchecked")
  public FabricCall_c(Position pos, Receiver target, Id name, Expr remoteClient, List args) {
    super(pos, target, name, args);
    this.remoteClient = remoteClient;
  }
  
  @SuppressWarnings("unchecked")
  protected FabricCall_c reconstruct(Receiver target, Id name, Expr remoteClient, List arguments) {
    FabricCall_c n = (FabricCall_c)super.reconstruct(target, name, arguments);
    
    if (remoteClient != this.remoteClient) {
      n = (FabricCall_c)n.copy();
      n.remoteClient = remoteClient;
    }
    
    return n;
  }

  public Expr remoteClient() {
    return remoteClient;
  }
  
  public FabricCall remoteClient(Expr remoteClient) {
    if (remoteClient == this.remoteClient) {
      return this;
    }
    
    FabricCall_c n = (FabricCall_c)this.copy();
    n.remoteClient = remoteClient;
    return n;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public Node visitChildren(NodeVisitor v) {
    Receiver target = (Receiver) visitChild(this.target, v);
    Id name = (Id) visitChild(this.name, v);
    Expr remoteClient = (Expr) visitChild(this.remoteClient, v);
    List arguments = visitList(this.arguments, v);
    return reconstruct(target, name, remoteClient, arguments);
  }
  
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricCall c = (FabricCall)super.typeCheck(tc);

    if (c.remoteClient() != null) {
      FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();
      JifContext context = (JifContext)tc.context();

      return c.remoteClientPrincipal(JifUtil.exprToPrincipal(ts, c.remoteClient(), context));
    }
    
    return c;
  }
  
  public Principal remoteClientPrincipal() {
    return remoteClientPrincipal;
  }
  
  public FabricCall remoteClientPrincipal(Principal p) {
    if (p == remoteClientPrincipal) return this;
    FabricCall_c n = (FabricCall_c)copy();
    n.remoteClientPrincipal = p;
    return n;
  }
}
