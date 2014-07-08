/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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

import java.net.URI;
import java.util.Collections;
import java.util.List;

import polyglot.ast.ArrayAccess;
import polyglot.ast.ArrayAccessAssign;
import polyglot.ast.ArrayTypeNode;
import polyglot.ast.Assign.Operator;
import polyglot.ast.Call;
import polyglot.ast.Cast;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.Disamb;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.Import;
import polyglot.ast.NodeFactory_c;
import polyglot.ast.PackageNode;
import polyglot.ast.Receiver;
import polyglot.ast.SourceFile;
import polyglot.ast.Stmt;
import polyglot.ast.TopLevelDecl;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import codebases.ast.CBSourceFile_c;
import codebases.ast.CodebaseDecl;
import codebases.ast.CodebaseDecl_c;
import codebases.ast.CodebaseNode;
import codebases.ast.CodebaseNode_c;
import fabil.extension.FabILDelFactory;
import fabil.extension.FabILDelFactory_c;
import fabil.extension.FabILExtFactory;
import fabil.extension.FabILExtFactory_c;

/**
 * NodeFactory for FabIL extension.
 */
public class FabILNodeFactory_c extends NodeFactory_c implements
    FabILNodeFactory {

  public FabILNodeFactory_c() {
    super(new FabILExtFactory_c(), new FabILDelFactory_c());
  }

  @Override
  public FabILExtFactory extFactory() {
    return (FabILExtFactory) super.extFactory();
  }

  @Override
  protected FabILDelFactory delFactory() {
    return (FabILDelFactory) super.delFactory();
  }

  @Override
  public CodebaseNode CodebaseNode(Position pos, URI ns, String name,
      URI externalNS) {
    return CodebaseNode(pos, ns, name, externalNS, null);
  }

  @Override
  public CodebaseNode CodebaseNode(Position pos, URI ns, String name,
      URI externalNS, Package package_) {
    CodebaseNode n = new CodebaseNode_c(pos, ns, name, externalNS, package_);
    n = (CodebaseNode) n.ext(extFactory().extCodebaseNode());
    n = (CodebaseNode) n.del(delFactory().delCodebaseNode());
    return n;
  }

  @Override
  public ArrayAccessAssign ArrayAccessAssign(Position pos, ArrayAccess left,
      Operator op, Expr right) {
    ArrayAccessAssign aaa = new ArrayAccessAssign_c(pos, left, op, right);
    aaa = (ArrayAccessAssign) aaa.ext(extFactory().extArrayAccessAssign());
    aaa = (ArrayAccessAssign) aaa.del(delFactory().delArrayAccessAssign());
    return aaa;
  }

  @Override
  public FabricArrayInit FabricArrayInit(Position pos, List<Expr> elements) {
    return FabricArrayInit(pos, null, null, null, elements);
  }

  @Override
  public FabricArrayInit FabricArrayInit(Position pos, Expr label,
      Expr accessPolicy, Expr location, List<Expr> elements) {
    FabricArrayInit ai =
        new FabricArrayInit_c(pos, elements, label, accessPolicy, location);
    ai = (FabricArrayInit) ai.ext(extFactory().extFabricArrayInit());
    ai = (FabricArrayInit) ai.del(delFactory().delFabricArrayInit());
    return ai;
  }

  @Override
  public ArrayTypeNode ArrayTypeNode(Position pos, TypeNode base) {
    // TODO Auto-generated method stub
    return super.ArrayTypeNode(pos, base);
  }

  @Override
  public FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type) {
    FabricArrayTypeNode atn = new FabricArrayTypeNode_c(pos, type);
    atn = (FabricArrayTypeNode) atn.ext(extFactory().extFabricArrayTypeNode());
    atn = (FabricArrayTypeNode) atn.del(delFactory().delFabricArrayTypeNode());
    return atn;
  }

  @Override
  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic atomic = new Atomic_c(pos, statements);
    atomic = (Atomic) atomic.ext(extFactory().extAtomic());
    atomic = (Atomic) atomic.del(delFactory().delBlock());
    return atomic;
  }

  @Override
  public Cast Cast(Position pos, TypeNode type, Expr expr) {
    Cast cast = new Cast_c(pos, type, expr);
    cast = (Cast) cast.ext(extFactory().extCast());
    cast = (Cast) cast.del(delFactory().delCast());
    return cast;
  }

  @Override
  public ClassDecl ClassDecl(Position pos, Flags flags, Id name,
      TypeNode superClass, List<TypeNode> interfaces, ClassBody body) {
    ClassDecl n =
        new ClassDecl_c(pos, flags, name, superClass,
            CollectionUtil.nonNullList(interfaces), body);
    n = (ClassDecl) n.ext(extFactory().extClassDecl());
    n = (ClassDecl) n.del(delFactory().delClassDecl());
    return n;
  }

  @Override
  public NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr accessPolicy, Expr location, List<Expr> dims, int addDims,
      FabricArrayInit init) {
    NewFabricArray result =
        new NewFabricArray_c(pos, base, CollectionUtil.nonNullList(dims),
            addDims, init, label, accessPolicy, location);
    result = (NewFabricArray) result.ext(extFactory().extNewFabricArray());
    result = (NewFabricArray) result.del(delFactory().delNewArray());
    return result;
  }

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args, ClassBody body) {
    New n =
        new New_c(pos, outer, objectType, CollectionUtil.nonNullList(args),
            body, location);
    n = (New) n.ext(extFactory().extNew());
    n = (New) n.del(delFactory().delNew());

    return n;
  }

  // Constructors with fewer arguments ////////////////////////////////////////

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType,
      List<Expr> args, ClassBody body) {
    return New(pos, outer, objectType, null, args, body);
  }

  @Override
  public New New(Position pos, TypeNode objectType, Expr location,
      List<Expr> args) {
    return New(pos, null, objectType, location, args);
  }

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args) {
    return New(pos, outer, objectType, location, args, null);
  }

  @Override
  public New New(Position pos, TypeNode type, Expr location, List<Expr> args,
      polyglot.ast.ClassBody body) {
    return New(pos, null, type, location, args, body);
  }

  @Override
  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr label, Expr accessPolicy, Expr location, List<Expr> dims) {
    return NewFabricArray(pos, base, label, accessPolicy, location, dims, 0,
        null);
  }

  @Override
  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr label, Expr accessPolicy, Expr location, List<Expr> dims, int addDims) {
    return NewFabricArray(pos, base, label, accessPolicy, location, dims,
        addDims, null);
  }

  @Override
  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr label, Expr accessPolicy, Expr location, int addDims,
      FabricArrayInit init) {
    List<Expr> emptyList = Collections.emptyList();
    return NewFabricArray(pos, base, label, accessPolicy, location, emptyList,
        addDims, init);
  }

  @Override
  public RetryStmt RetryStmt(Position pos) {
    RetryStmt s = new RetryStmt_c(pos);
    s = (RetryStmt) s.ext(extFactory().extRetry());
    s = (RetryStmt) s.del(delFactory().delStmt());
    return s;
  }

  @Override
  public AbortStmt AbortStmt(Position pos) {
    AbortStmt s = new AbortStmt_c(pos);
    s = (AbortStmt) s.ext(extFactory().extAbort());
    s = (AbortStmt) s.del(delFactory().delStmt());
    return s;
  }

  @Override
  public Call Call(Position pos, Receiver target, Id name, List<Expr> args) {
    return Call(pos, target, name, null, args);
  }

  @Override
  public Call Call(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> args) {
    Call n = new FabILCall_c(pos, target, name, remoteWorker, args);
    n = (Call) n.ext(extFactory().extCall());
    n = (Call) n.del(delFactory().delCall());
    return n;
  }

  @Override
  public StoreGetter StoreGetter(Position pos) {
    StoreGetter n = new StoreGetter_c(pos);
    n = (StoreGetter) n.ext(extFactory().extExpr());
    n = (StoreGetter) n.del(delFactory().delExpr());
    return n;
  }

  @Override
  public SourceFile SourceFile(Position pos, PackageNode packageName,
      List<Import> imports, List<TopLevelDecl> decls) {
    return SourceFile(pos, packageName, Collections.EMPTY_LIST, imports, decls);
  }

  @Override
  public SourceFile SourceFile(Position pos, PackageNode packageName,
      List<CodebaseDecl> codebases, List<Import> imports,
      List<TopLevelDecl> decls) {
    SourceFile sf =
        new CBSourceFile_c(pos, packageName, imports, codebases, decls);
    sf = (SourceFile) sf.ext(extFactory().extSourceFile());
    sf = (SourceFile) sf.del(delFactory().delSourceFile());
    return sf;
  }

  @Override
  public ProviderLabel providerLabel(Position pos, TypeNode tn) {
    ProviderLabel pl = new ProviderLabel_c(pos, tn);
    pl = (ProviderLabel) pl.ext(extFactory().extProviderLabel());
    pl = (ProviderLabel) pl.del(delFactory().delProviderLabel());
    return pl;
  }

  @Override
  public CodebaseDecl CodebaseDecl(Position pos, polyglot.ast.Id name) {
    CodebaseDecl n = new CodebaseDecl_c(pos, name);
    n = (CodebaseDecl) n.ext(extFactory().extCodebaseDecl());
    n = (CodebaseDecl) n.del(delFactory().delCodebaseDecl());
    return n;
  }

  @Override
  public Disamb disamb() {
    return new FabILDisamb();
  }
}
