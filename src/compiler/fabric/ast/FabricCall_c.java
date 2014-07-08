/**
 * Copyright (C) 2010 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.ast;

import java.util.ArrayList;
import java.util.List;

import fabric.types.FabricTypeSystem;

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
import jif.ast.JifCall_c;
import jif.ast.JifUtil;
import jif.types.JifContext;
import jif.types.LabeledType;
import jif.types.principal.Principal;

public class FabricCall_c extends JifCall_c implements FabricCall {
  protected Expr remoteWorker;
  protected Principal remoteWorkerPrincipal;
  
  @SuppressWarnings("unchecked")
  public FabricCall_c(Position pos, Receiver target, Id name, List args) {
    this(pos, target, name, null, args);
  }

  @SuppressWarnings("unchecked")
  public FabricCall_c(Position pos, Receiver target, Id name, Expr remoteWorker, List args) {
    super(pos, target, name, args);
    this.remoteWorker = remoteWorker;
  }
  
  @SuppressWarnings("unchecked")
  protected FabricCall_c reconstruct(Receiver target, Id name, Expr remoteWorker, List arguments) {
    FabricCall_c n = (FabricCall_c)super.reconstruct(target, name, arguments);
    
    if (remoteWorker != this.remoteWorker) {
      n = (FabricCall_c)n.copy();
      n.remoteWorker = remoteWorker;
    }
    
    return n;
  }

  public Expr remoteWorker() {
    return remoteWorker;
  }
  
  public FabricCall remoteWorker(Expr remoteWorker) {
    if (remoteWorker == this.remoteWorker) {
      return this;
    }
    
    FabricCall_c n = (FabricCall_c)this.copy();
    n.remoteWorker = remoteWorker;
    return n;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public Node visitChildren(NodeVisitor v) {
    Receiver target = (Receiver) visitChild(this.target, v);
    Id name = (Id) visitChild(this.name, v);
    Expr remoteWorker = (Expr) visitChild(this.remoteWorker, v);
    List arguments = visitList(this.arguments, v);
    return reconstruct(target, name, remoteWorker, arguments);
  }
  
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
  
  public Principal remoteWorkerPrincipal() {
    return remoteWorkerPrincipal;
  }
  
  public FabricCall remoteWorkerPrincipal(Principal p) {
    if (p == remoteWorkerPrincipal) return this;
    FabricCall_c n = (FabricCall_c)copy();
    n.remoteWorkerPrincipal = p;
    return n;
  }
}
