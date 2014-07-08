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

import java.util.List;

import fabric.types.FabricTypeSystem;

import polyglot.ast.Expr;
import polyglot.ast.Expr_c;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

public class RemoteWorkerGetter_c extends Expr_c implements RemoteWorkerGetter {
  protected Expr remoteWorkerName; // cannot be null
  
  public RemoteWorkerGetter_c(Position pos, Expr remoteWorkerName) {
    super(pos);
    this.remoteWorkerName = remoteWorkerName;
  }
  
  protected RemoteWorkerGetter reconstruct(Expr remoteWorkerName) {
    if (this.remoteWorkerName != remoteWorkerName) {
      RemoteWorkerGetter_c n = (RemoteWorkerGetter_c)copy();
      n.remoteWorkerName = remoteWorkerName;
      return n;
    }
    return this;
  }
  
  public Expr remoteWorkerName() {
    return remoteWorkerName;
  }

  public RemoteWorkerGetter remoteWorkerName(Expr expr) {
    return reconstruct(expr);
  }
  
  @Override
  public Node visitChildren(NodeVisitor v) {
    Expr remoteWorkerName = (Expr)this.remoteWorkerName.visit(v);
    return reconstruct(remoteWorkerName);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    v.visitCFG(remoteWorkerName, this, EXIT);
    return succs;
  }

  public Term firstChild() {
    return remoteWorkerName;
  }
  
  @Override
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem)tc.typeSystem();
    
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
