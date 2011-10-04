package fabil.ast;

import java.util.Collections;
import java.util.List;

import polyglot.ast.*;
import polyglot.ast.Assign.Operator;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
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
  public CodebaseDisamb disamb() {
    return new FabILDisamb_c();

  }

  @Override
  public CodebasePackageNode PackageNode(Position pos, Package p) {
    CodebasePackageNode n = new CodebasePackageNode_c(pos, p);
    n = (CodebasePackageNode) n.ext(extFactory().extPackageNode());
    n = (CodebasePackageNode) n.del(delFactory().delPackageNode());
    return n;
  }

  /*
   * (non-Javadoc)
   * @see polyglot.ast.NodeFactory_c#ArrayAccessAssign(polyglot.util.Position,
   * polyglot.ast.ArrayAccess, polyglot.ast.Assign.Operator, polyglot.ast.Expr)
   */
  @Override
  public ArrayAccessAssign ArrayAccessAssign(Position pos, ArrayAccess left,
      Operator op, Expr right) {
    ArrayAccessAssign aaa = new ArrayAccessAssign_c(pos, left, op, right);
    aaa = (ArrayAccessAssign) aaa.ext(extFactory().extArrayAccessAssign());
    aaa = (ArrayAccessAssign) aaa.del(delFactory().delArrayAccessAssign());
    return aaa;
  }

  public FabricArrayInit FabricArrayInit(Position pos, List<Expr> elements) {
    return FabricArrayInit(pos, null, null, null, elements);
  }

  public FabricArrayInit FabricArrayInit(Position pos, Expr label,
      Expr accessLabel, Expr location, List<Expr> elements) {
    FabricArrayInit ai =
        new FabricArrayInit_c(pos, elements, label, accessLabel, location);
    ai = (FabricArrayInit) ai.ext(extFactory().extFabricArrayInit());
    ai = (FabricArrayInit) ai.del(delFactory().delFabricArrayInit());
    return ai;
  }

  @Override
  public ArrayTypeNode ArrayTypeNode(Position pos, TypeNode base) {
    // TODO Auto-generated method stub
    return super.ArrayTypeNode(pos, base);
  }

  public FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type) {
    FabricArrayTypeNode atn = new FabricArrayTypeNode_c(pos, type);
    atn = (FabricArrayTypeNode) atn.ext(extFactory().extFabricArrayTypeNode());
    atn = (FabricArrayTypeNode) atn.del(delFactory().delFabricArrayTypeNode());
    return atn;
  }

  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic atomic = new Atomic_c(pos, statements);
    atomic = (Atomic) atomic.ext(extFactory().extAtomic());
    atomic = (Atomic) atomic.del(delFactory().delBlock());
    return atomic;
  }

  /*
   * (non-Javadoc)
   * @see polyglot.ast.NodeFactory_c#Cast(polyglot.util.Position,
   * polyglot.ast.TypeNode, polyglot.ast.Expr)
   */
  @Override
  public Cast Cast(Position pos, TypeNode type, Expr expr) {
    Cast cast = new Cast_c(pos, type, expr);
    cast = (Cast) cast.ext(extFactory().extCast());
    cast = (Cast) cast.del(delFactory().delCast());
    return cast;
  }

  /*
   * (non-Javadoc)
   * @see polyglot.ast.NodeFactory_c#ClassDecl(polyglot.util.Position,
   * polyglot.types.Flags, polyglot.ast.Id, polyglot.ast.TypeNode,
   * java.util.List, polyglot.ast.ClassBody)
   */
  @Override
  public ClassDecl ClassDecl(Position pos, Flags flags, Id name,
      TypeNode superClass, @SuppressWarnings("rawtypes") List interfaces,
      ClassBody body) {
    @SuppressWarnings("unchecked")
    ClassDecl n =
        new ClassDecl_c(pos, flags, name, superClass,
            CollectionUtil.nonNullList(interfaces), body);
    n = (ClassDecl) n.ext(extFactory().extClassDecl());
    n = (ClassDecl) n.del(delFactory().delClassDecl());
    return n;
  }

  @SuppressWarnings("unchecked")
  public NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label, Expr accessLabel,
      Expr location, List<Expr> dims, int addDims, FabricArrayInit init) {
    NewFabricArray result =
        new NewFabricArray_c(pos, base, CollectionUtil.nonNullList(dims),
            addDims, init, label, accessLabel, location);
    result = (NewFabricArray) result.ext(extFactory().extNewFabricArray());
    result = (NewFabricArray) result.del(delFactory().delNewArray());
    return result;
  }

  @SuppressWarnings("unchecked")
  public New New(Position pos, Expr outer, TypeNode objectType, Expr label, Expr accessLabel,
      Expr location, List<Expr> args, ClassBody body) {
    New n =
        new New_c(pos, outer, objectType, CollectionUtil.nonNullList(args),
            body, label, accessLabel, location);
    n = (New) n.ext(extFactory().extNew());
    n = (New) n.del(delFactory().delNew());

    return n;
  }
  
  // Constructors with fewer arguments ////////////////////////////////////////

  @SuppressWarnings("unchecked")
  @Override
  public New New(Position pos, Expr outer, TypeNode objectType,
      @SuppressWarnings("rawtypes") List args, ClassBody body) {
    return New(pos, outer, objectType, null, null, null, args, body);
  }

  public New New(Position pos, TypeNode objectType, Expr label, Expr accessLabel, Expr location,
      List<Expr> args) {
    return New(pos, null, objectType, label, accessLabel, location, args);
  }

  public New New(Position pos, Expr outer, TypeNode objectType, Expr label, Expr accessLabel,
      Expr location, List<Expr> args) {
    return New(pos, outer, objectType, label, accessLabel, location, args, null);
  }

  public New New(Position pos, TypeNode type, Expr label, Expr accessLabel, Expr location,
      List<Expr> args, polyglot.ast.ClassBody body) {
    return New(pos, null, type, label, accessLabel, location, args, body);
  }

  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr label, Expr accessLabel, Expr location, List<Expr> dims) {
    return NewFabricArray(pos, base, label, accessLabel, location, dims, 0, null);
  }

  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr label, Expr accessLabel, Expr location, List<Expr> dims, int addDims) {
    return NewFabricArray(pos, base, label, accessLabel, location, dims, addDims, null);
  }

  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr label, Expr accessLabel, Expr location, int addDims, FabricArrayInit init) {
    List<Expr> emptyList = Collections.emptyList();
    return NewFabricArray(pos, base, label, accessLabel, location, emptyList, addDims, init);
  }
  
  public RetryStmt RetryStmt(Position pos) {
    RetryStmt s = new RetryStmt_c(pos);
    s = (RetryStmt) s.ext(extFactory().extRetry());
    s = (RetryStmt) s.del(delFactory().delStmt());
    return s;
  }

  public AbortStmt AbortStmt(Position pos) {
    AbortStmt s = new AbortStmt_c(pos);
    s = (AbortStmt) s.ext(extFactory().extAbort());
    s = (AbortStmt) s.del(delFactory().delStmt());
    return s;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Call Call(Position pos, Receiver target, Id name,
      @SuppressWarnings("rawtypes") List args) {
    return Call(pos, target, name, null, args);
  }

  public Call Call(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> args) {
    Call n = new FabILCall_c(pos, target, name, remoteWorker, args);
    n = (Call) n.ext(extFactory().extCall());
    n = (Call) n.del(delFactory().delCall());
    return n;
  }

  public StoreGetter StoreGetter(Position pos) {
    StoreGetter n = new StoreGetter_c(pos);
    n = (StoreGetter) n.ext(extFactory().extExpr());
    n = (StoreGetter) n.del(delFactory().delExpr());
    return n;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public SourceFile SourceFile(Position pos, PackageNode packageName,
      List imports, List decls) {
    SourceFile sf = new CodebaseSourceFile_c(pos, packageName, imports, decls);
    sf = (SourceFile) sf.ext(extFactory().extSourceFile());
    sf = (SourceFile) sf.del(delFactory().delSourceFile());
    return sf;
  }

  public ProviderLabel providerLabel(Position pos, TypeNode tn) {
    ProviderLabel pl = new ProviderLabel_c(pos, tn);
    pl = (ProviderLabel) pl.ext(extFactory().extProviderLabel());
    pl = (ProviderLabel) pl.del(delFactory().delProviderLabel());
    return pl;
  }

}
