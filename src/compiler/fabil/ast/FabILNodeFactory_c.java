package fabil.ast;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import codebases.ast.CBSourceFile_c;
import codebases.ast.CodebaseDecl;
import codebases.ast.CodebaseDecl_c;
import codebases.ast.CodebaseNode;
import codebases.ast.CodebaseNode_c;

import fabil.extension.FabILDelFactory;
import fabil.extension.FabILDelFactory_c;
import fabil.extension.FabILExtFactory;
import fabil.extension.FabILExtFactory_c;

import polyglot.ast.ArrayAccess;
import polyglot.ast.ArrayAccessAssign;
import polyglot.ast.Assign.Operator;
import polyglot.ast.Call;
import polyglot.ast.Cast;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.DelFactory;
import polyglot.ast.Disamb;
import polyglot.ast.Expr;
import polyglot.ast.ExtFactory;
import polyglot.ast.Id;
import polyglot.ast.Import;
import polyglot.ast.Javadoc;
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

/**
 * NodeFactory for FabIL extension.
 */
public class FabILNodeFactory_c extends NodeFactory_c
    implements FabILNodeFactory {

  public FabILNodeFactory_c() {
    this(new FabILExtFactory_c(), new FabILDelFactory_c());
  }

  public FabILNodeFactory_c(ExtFactory extFactory, DelFactory delFactory) {
    super(extFactory, delFactory);
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
    n = ext(n, extFactory().extCodebaseNode());
    n = del(n, delFactory().delCodebaseNode());
    return n;
  }

  @Override
  public ArrayAccessAssign ArrayAccessAssign(Position pos, ArrayAccess left,
      Operator op, Expr right) {
    ArrayAccessAssign aaa = new ArrayAccessAssign_c(pos, left, op, right);
    aaa = ext(aaa, extFactory().extArrayAccessAssign());
    aaa = del(aaa, delFactory().delArrayAccessAssign());
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
    ai = ext(ai, extFactory().extFabricArrayInit());
    ai = del(ai, delFactory().delFabricArrayInit());
    return ai;
  }

  @Override
  public FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type) {
    FabricArrayTypeNode atn = new FabricArrayTypeNode_c(pos, type);
    atn = ext(atn, extFactory().extFabricArrayTypeNode());
    atn = del(atn, delFactory().delFabricArrayTypeNode());
    return atn;
  }

  @Override
  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic atomic = new Atomic_c(pos, statements);
    atomic = ext(atomic, extFactory().extAtomic());
    atomic = del(atomic, delFactory().delBlock());
    return atomic;
  }

  @Override
  public Cast Cast(Position pos, TypeNode type, Expr expr) {
    Cast cast = new Cast_c(pos, type, expr);
    cast = ext(cast, extFactory().extCast());
    cast = del(cast, delFactory().delCast());
    return cast;
  }

  @Override
  public ClassDecl ClassDecl(Position pos, Flags flags, Id name,
      TypeNode superClass, List<TypeNode> interfaces, ClassBody body,
      Javadoc javadoc) {
    ClassDecl n = new ClassDecl_c(pos, flags, name, superClass,
        CollectionUtil.nonNullList(interfaces), body, javadoc);
    n = ext(n, extFactory().extClassDecl());
    n = del(n, delFactory().delClassDecl());
    return n;
  }

  @Override
  public NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr accessPolicy, Expr location, List<Expr> dims, int addDims,
      FabricArrayInit init) {
    NewFabricArray result =
        new NewFabricArray_c(pos, base, CollectionUtil.nonNullList(dims),
            addDims, init, label, accessPolicy, location);
    result = ext(result, extFactory().extNewFabricArray());
    result = del(result, delFactory().delNewArray());
    return result;
  }

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args, ClassBody body) {
    New n = new New_c(pos, outer, objectType, CollectionUtil.nonNullList(args),
        body, location);
    n = ext(n, extFactory().extNew());
    n = del(n, delFactory().delNew());

    return n;
  }

  // Constructors with fewer arguments ////////////////////////////////////////

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, List<Expr> args,
      ClassBody body) {
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
      Expr label, Expr accessPolicy, Expr location, List<Expr> dims,
      int addDims) {
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
    s = ext(s, extFactory().extRetry());
    s = del(s, delFactory().delStmt());
    return s;
  }

  @Override
  public AbortStmt AbortStmt(Position pos) {
    AbortStmt s = new AbortStmt_c(pos);
    s = ext(s, extFactory().extAbort());
    s = del(s, delFactory().delStmt());
    return s;
  }

  @Override
  public StageStmt StageStmt(Position pos) {
    StageStmt s = new StageStmt_c(pos);
    s = ext(s, extFactory().extStage());
    s = del(s, delFactory().delStmt());
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
    n = ext(n, extFactory().extCall());
    n = del(n, delFactory().delCall());
    return n;
  }

  @Override
  public StoreGetter StoreGetter(Position pos) {
    StoreGetter n = new StoreGetter_c(pos);
    n = ext(n, extFactory().extStoreGetter());
    n = del(n, delFactory().delExpr());
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
    sf = ext(sf, extFactory().extSourceFile());
    sf = del(sf, delFactory().delSourceFile());
    return sf;
  }

  @Override
  public ProviderLabel ProviderLabel(Position pos, TypeNode tn) {
    ProviderLabel pl = new ProviderLabel_c(pos, tn);
    pl = ext(pl, extFactory().extProviderLabel());
    pl = del(pl, delFactory().delProviderLabel());
    return pl;
  }

  @Override
  public CodebaseDecl CodebaseDecl(Position pos, polyglot.ast.Id name) {
    CodebaseDecl n = new CodebaseDecl_c(pos, name);
    n = ext(n, extFactory().extCodebaseDecl());
    n = del(n, delFactory().delCodebaseDecl());
    return n;
  }

  @Override
  public Disamb disamb() {
    return new FabILDisamb();
  }
}
