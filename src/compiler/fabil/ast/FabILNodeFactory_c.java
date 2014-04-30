package fabil.ast;

import java.net.URI;
import java.util.Collections;
import java.util.List;

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
import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;
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
    n = del(n, delFactory().delCodebaseNode());
    return n;
  }

  protected final CodebaseNode CodeBaseNode(Position pos, URI ns, String name,
      URI externalNS, Package package_, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extCodebaseNode());
    return new CodebaseNode_c(pos, ns, name, externalNS, package_, ext);
  }

  @Override
  public ArrayAccessAssign ArrayAccessAssign(Position pos, ArrayAccess left,
      Operator op, Expr right) {
    ArrayAccessAssign aaa =
        ArrayAccessAssign(pos, left, op, right, null, extFactory());
    aaa = del(aaa, delFactory().delArrayAccessAssign());
    return aaa;
  }

  protected final ArrayAccessAssign FabILArrayAccessAssign(Position pos,
      ArrayAccess left, Operator op, Expr right, Ext ext, ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extArrayAccessAssign());
    return new ArrayAccessAssign_c(pos, left, op, right, ext);
  }

  @Override
  public FabricArrayInit FabricArrayInit(Position pos, List<Expr> elements) {
    return FabricArrayInit(pos, null, null, null, elements);
  }

  @Override
  public FabricArrayInit FabricArrayInit(Position pos, Expr label,
      Expr accessPolicy, Expr location, List<Expr> elements) {
    FabricArrayInit ai =
        FabricArrayInit(pos, label, accessPolicy, location, elements, null,
            extFactory());
    ai = del(ai, delFactory().delFabricArrayInit());
    return ai;
  }

  protected final FabricArrayInit FabricArrayInit(Position pos, Expr label,
      Expr accessPolicy, Expr location, List<Expr> elements, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extFabricArrayInit());
    return new FabricArrayInit_c(pos, elements, label, accessPolicy, location,
        ext);
  }

  @Override
  public FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type) {
    FabricArrayTypeNode atn =
        FabricArrayTypeNode(pos, type, null, extFactory());
    atn = del(atn, delFactory().delFabricArrayTypeNode());
    return atn;
  }

  protected final FabricArrayTypeNode FabricArrayTypeNode(Position pos,
      TypeNode type, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extFabricArrayTypeNode());
    return new FabricArrayTypeNode_c(pos, type, ext);
  }

  @Override
  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic atomic = Atomic(pos, statements, null, extFactory());
    atomic = del(atomic, delFactory().delBlock());
    return atomic;
  }

  protected final Atomic Atomic(Position pos, List<Stmt> statements, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extAtomic());
    return new Atomic_c(pos, statements, ext);
  }

  @Override
  public Cast Cast(Position pos, TypeNode type, Expr expr) {
    Cast cast = FabILCast(pos, type, expr, null, extFactory());
    cast = del(cast, delFactory().delCast());
    return cast;
  }

  protected final Cast FabILCast(Position pos, TypeNode type, Expr expr,
      Ext ext, ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extCast());
    return new Cast_c(pos, type, expr, ext);
  }

  @Override
  public ClassDecl ClassDecl(Position pos, Flags flags, Id name,
      TypeNode superClass, List<TypeNode> interfaces, ClassBody body) {
    ClassDecl n =
        FabILClassDecl(pos, flags, name, superClass, interfaces, body, null,
            extFactory());
    n = del(n, delFactory().delClassDecl());
    return n;
  }

  protected final ClassDecl FabILClassDecl(Position pos, Flags flags, Id name,
      TypeNode superClass, List<TypeNode> interfaces, ClassBody body, Ext ext,
      ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extClassDecl());
    return new ClassDecl_c(pos, flags, name, superClass,
        CollectionUtil.nonNullList(interfaces), body, ext);
  }

  @Override
  public NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr accessPolicy, Expr location, List<Expr> dims, int addDims,
      FabricArrayInit init) {
    NewFabricArray result =
        NewFabricArray(pos, base, label, accessPolicy, location, dims, addDims,
            init, null, extFactory());
    result = del(result, delFactory().delNewArray());
    return result;
  }

  protected final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr label, Expr accessPolicy, Expr location, List<Expr> dims,
      int addDims, FabricArrayInit init, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extNewFabricArray());
    return new NewFabricArray_c(pos, base, CollectionUtil.nonNullList(dims),
        addDims, init, label, accessPolicy, location, ext);
  }

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args, ClassBody body) {
    New n =
        FabILNew(pos, outer, objectType, location, args, body, null,
            extFactory());
    n = del(n, delFactory().delNew());

    return n;
  }

  protected final New FabILNew(Position pos, Expr outer, TypeNode objectType,
      Expr location, List<Expr> args, ClassBody body, Ext ext,
      ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extNew());
    return new New_c(pos, outer, objectType, CollectionUtil.nonNullList(args),
        body, location, ext);
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
    RetryStmt s = RetryStmt(pos, null, extFactory());
    s = del(s, delFactory().delStmt());
    return s;
  }

  protected final RetryStmt RetryStmt(Position pos, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extRetry());
    return new RetryStmt_c(pos, ext);
  }

  @Override
  public AbortStmt AbortStmt(Position pos) {
    AbortStmt s = AbortStmt(pos, null, extFactory());
    s = del(s, delFactory().delStmt());
    return s;
  }

  protected final AbortStmt AbortStmt(Position pos, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extAbort());
    return new AbortStmt_c(pos, ext);
  }

  @Override
  public Call Call(Position pos, Receiver target, Id name, List<Expr> args) {
    return Call(pos, target, name, null, args);
  }

  @Override
  public Call Call(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> args) {
    Call n =
        FabILCall(pos, target, name, remoteWorker, args, null, extFactory());
    n = del(n, delFactory().delCall());
    return n;
  }

  protected final Call FabILCall(Position pos, Receiver target, Id name,
      Expr remoteWorker, List<Expr> args, Ext ext, ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extCall());
    return new FabILCall_c(pos, target, name, remoteWorker, args, ext);
  }

  @Override
  public StoreGetter StoreGetter(Position pos) {
    StoreGetter n = StoreGetter(pos, null, extFactory());
    n = del(n, delFactory().delExpr());
    return n;
  }

  protected final StoreGetter StoreGetter(Position pos, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extStoreGetter());
    return new StoreGetter_c(pos, ext);
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
        SourceFile(pos, packageName, codebases, imports, decls, null,
            extFactory());
    sf = del(sf, delFactory().delSourceFile());
    return sf;
  }

  protected final SourceFile SourceFile(Position pos, PackageNode packageName,
      List<CodebaseDecl> codebases, List<Import> imports,
      List<TopLevelDecl> decls, Ext ext, ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extSourceFile());
    return new CBSourceFile_c(pos, packageName, imports, codebases, decls, ext);
  }

  @Override
  public ProviderLabel ProviderLabel(Position pos, TypeNode tn) {
    ProviderLabel pl = ProviderLabel(pos, tn, null, extFactory());
    pl = del(pl, delFactory().delProviderLabel());
    return pl;
  }

  protected final ProviderLabel ProviderLabel(Position pos, TypeNode tn,
      Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extProviderLabel());
    return new ProviderLabel_c(pos, tn, ext);
  }

  @Override
  public CodebaseDecl CodebaseDecl(Position pos, polyglot.ast.Id name) {
    CodebaseDecl n = CodebaseDecl(pos, name, null, extFactory());
    n = del(n, delFactory().delCodebaseDecl());
    return n;
  }

  protected final CodebaseDecl CodebaseDecl(Position pos, Id name, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabILExtFactory)
        ext = composeExts(ext, ((FabILExtFactory) ef).extCodebaseDecl());
    return new CodebaseDecl_c(pos, name, ext);
  }

  @Override
  public Disamb disamb() {
    return new FabILDisamb();
  }
}
