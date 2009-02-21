package fabil.ast;

import java.util.Iterator;
import java.util.List;

import fabil.types.FabILTypeSystem;

import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

public class FabILCall_c extends Call_c implements FabILCall {
  Expr remoteClient;
  
  @SuppressWarnings("unchecked")
  public FabILCall_c(Position pos, Receiver target, Id name, List arguments) {
    this(pos, target, name, null, arguments);
  }
  
  @SuppressWarnings("unchecked")
  public FabILCall_c(Position pos, Receiver target, Id name, Expr remoteClient, List arguments) {
    super(pos, target, name, arguments);
    this.remoteClient = remoteClient;
  }
  
  @SuppressWarnings("unchecked")
  protected FabILCall_c reconstruct(Receiver target, Id name, Expr remoteClient, List arguments) {
    FabILCall_c n = (FabILCall_c)super.reconstruct(target, name, arguments);
    
    if (remoteClient != this.remoteClient) {
      n = (FabILCall_c)n.copy();
      n.remoteClient = remoteClient;
    }
    
    return n;
  }
  
  public Expr remoteClient() {
    return remoteClient;
  }
  
  public FabILCall remoteClient(Expr remoteClient) {
    if (remoteClient == this.remoteClient) {
      return this;
    }
    
    FabILCall_c n = (FabILCall_c)this.copy();
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
    FabILCall c = (FabILCall)super.typeCheck(tc);
    
    if (c.remoteClient() != null) {
      if (!c.remoteClient().type().isCanonical()) {
        return c;
      }

      if (c.methodInstance().flags().isStatic()) {
        throw new SemanticException("Remotely calling static methods not supported yet.", c.position());
      }
      
      if (!c.methodInstance().flags().isPublic()) {
        throw new SemanticException("Remotely calling non-public methods not supported yet.", c.position());
      }
      
      FabILTypeSystem ts = (FabILTypeSystem)tc.typeSystem();
      if (!ts.isSubtype(c.remoteClient().type(), ts.RemoteClient())) {
        // The expression after @ has to be a RemoteClient
        throw new SemanticException("Remote method invocations expect remote clients.", 
                                    c.remoteClient().position());
      }
    }

    return c;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public String toString() {
    if (remoteClient == null) {
      return super.toString();
    }
    
    StringBuffer sb = new StringBuffer();
    sb.append(targetImplicit ? "" : target.toString() + ".");
    sb.append(name);
    sb.append("@");
    sb.append(remoteClient);
    sb.append("(");

    int count = 0;

    for (Iterator i = arguments.iterator(); i.hasNext(); ) {
        if (count++ > 2) {
            sb.append("...");
            break;
        }

        Expr n = (Expr) i.next();
        sb.append(n.toString());

        if (i.hasNext()) {
            sb.append(", ");
        }
    }

    sb.append(")");
    return sb.toString();
  }
}
