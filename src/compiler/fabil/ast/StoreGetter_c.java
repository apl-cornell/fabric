/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
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
package fabil.ast;

import java.util.List;

import polyglot.ast.Expr_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class StoreGetter_c extends Expr_c implements StoreGetter {

  @Deprecated
  public StoreGetter_c(Position pos) {
    this(pos, null);
  }

  public StoreGetter_c(Position pos, Ext ext) {
    super(pos, ext);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    return succs;
  }

  @Override
  public Term firstChild() {
    return null;
  }

  /** Type check the expression. */
  @Override
  public Node typeCheck(TypeChecker tc) {
    FabILTypeSystem fts = (FabILTypeSystem) tc.typeSystem();
    return type(fts.Store());
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    throw new InternalCompilerError("StoreGetter should have been rewritten.");
  }

}
