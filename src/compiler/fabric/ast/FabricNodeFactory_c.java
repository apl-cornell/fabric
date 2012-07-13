package fabric.ast;

import java.util.Collections;
import java.util.List;

import jif.ast.AmbPrincipalNode;
import jif.ast.ConstraintNode;
import jif.ast.JifClassDecl;
import jif.ast.JifNodeFactory_c;
import jif.ast.LabelNode;
import jif.ast.NewLabel;
import jif.ast.ParamDecl;
import jif.ast.PrincipalExpr;
import jif.ast.PrincipalNode;
import jif.types.Assertion;
import polyglot.ast.Call;
import polyglot.ast.CanonicalTypeNode;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.Disamb;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.Import;
import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.ast.Receiver;
import polyglot.ast.SourceFile;
import polyglot.ast.Stmt;
import polyglot.ast.TopLevelDecl;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import codebases.ast.CBSourceFile_c;
import codebases.ast.CodebaseDecl;
import codebases.ast.CodebaseDecl_c;
import codebases.ast.CodebaseNode;
import codebases.ast.CodebaseNode_c;
import fabric.common.FabricLocation;
import fabric.extension.FabricExt;
import fabric.extension.LocatedExt_c;

/**
 * NodeFactory for fabric extension.
 */
public class FabricNodeFactory_c extends JifNodeFactory_c implements
    FabricNodeFactory {

  // ////////////////////////////////////////////////////////////////////////////
  // public constructors //
  // ////////////////////////////////////////////////////////////////////////////

  public FabricNodeFactory_c() {
    this(new FabricFabExtFactory_c(new FabricJifExtFactory_c()));
  }

  public FabricNodeFactory_c(FabricExtFactory extFactory) {
    this(extFactory, new FabricDelFactory_c());
  }

  public FabricNodeFactory_c(FabricExtFactory extFactory,
      FabricDelFactory delFactory) {
    super(extFactory, delFactory);
  }

  @Override
  public Disamb disamb() {
    return new FabricDisamb_c();
  }

  @Override
  public CodebaseNode CodebaseNode(Position pos, FabricLocation ns,
      String name, FabricLocation externalNS) {
    return CodebaseNode(pos, ns, name, externalNS, null);
  }

  @Override
  public CodebaseNode CodebaseNode(Position pos, FabricLocation ns,
      String name, FabricLocation externalNS, Package package_) {
    CodebaseNode n = new CodebaseNode_c(pos, ns, name, externalNS, package_);
    n = (CodebaseNode) n.ext(fabricExtFactory().extCodebaseNode());
    n = (CodebaseNode) n.del(fabricDelFactory().delCodebaseNode());
    return n;
  }

  @Override
  public CodebaseDecl CodebaseDecl(Position pos, Id name) {
    CodebaseDecl n = new CodebaseDecl_c(pos, name);
    n = (CodebaseDecl) n.ext(fabricExtFactory().extCodebaseDecl());
    n = (CodebaseDecl) n.del(fabricDelFactory().delCodebaseDecl());
    return n;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // new factory methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic result = new Atomic_c(pos, statements);
    // note: this is correct. fabricExtFactory() is a factory that returns Jif
    // extension objects with their ext() pointers referring to Fabric Ext
    // objects, which is as it should be.
    result = (Atomic) result.ext(fabricExtFactory().extAtomic());
    result = (Atomic) result.del(fabricDelFactory().delAtomic());
    return result;
  }

  @Override
  public AmbNewFabricArray AmbNewFabricArray(Position pos, TypeNode base,
      Expr loc, Object expr, List<Expr> dims, int addDims) {
    AmbNewFabricArray result =
        new AmbNewFabricArray_c(pos, base, loc, expr, dims, addDims);
    result =
        (AmbNewFabricArray) result.ext(fabricExtFactory()
            .extAmbNewFabricArray());
    result =
        (AmbNewFabricArray) result.del(fabricDelFactory()
            .delAmbNewFabricArray());
    return result;
  }

  @Override
  public RetryStmt RetryStmt(Position pos) {
    RetryStmt s = new RetryStmt_c(pos);
    s = (RetryStmt) s.ext(fabricExtFactory().extRetryStmt());
    s = (RetryStmt) s.del(fabricDelFactory().delStmt());
    return s;
  }

  @Override
  public AbortStmt AbortStmt(Position pos) {
    AbortStmt s = new AbortStmt_c(pos);
    s = (AbortStmt) s.ext(fabricExtFactory().extAbortStmt());
    s = (AbortStmt) s.del(fabricDelFactory().delStmt());
    return s;
  }

  @Override
  public Worker Worker(Position pos) {
    Worker n = new Worker_c(pos, this);
    n = (Worker) n.ext(fabricExtFactory().extWorker());
    n = (Worker) n.del(fabricDelFactory().delWorker());
    return n;
  }

  @Override
  public RemoteWorkerGetter RemoteWorkerGetter(Position pos, Expr remoteName) {
    RemoteWorkerGetter n = new RemoteWorkerGetter_c(pos, remoteName);
    // TODO add the real extension and delegation for RemoteWorkerGetter.
    n = (RemoteWorkerGetter) n.ext(fabricExtFactory().extRemoteWorkerGetter());
    n = (RemoteWorkerGetter) n.del(fabricDelFactory().delRemoteWorkerGetter());
    return n;
  }

  @Override
  public NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, List<Expr> dims, int addDims, FabricArrayInit init) {
    NewFabricArray result =
        new NewFabricArray_c(pos, base, dims, addDims, init);
    result =
        (NewFabricArray) result.ext(fabricExtFactory().extNewFabricArray());
    result =
        (NewFabricArray) result.del(fabricDelFactory().delNewFabricArray());
    return result;
  }

  @Override
  public FabricArrayInit FabricArrayInit(Position position, Expr label,
      Expr location, List<Expr> elements) {
    FabricArrayInit result =
        new FabricArrayInit_c(position, elements, label, location);
    result =
        (FabricArrayInit) result.ext(fabricExtFactory().extFabricArrayInit());
    result =
        (FabricArrayInit) result.del(fabricDelFactory().delFabricArrayInit());
    return result;
  }

  @Override
  public FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type) {
    FabricArrayTypeNode result = new FabricArrayTypeNode_c(pos, type);
    result =
        (FabricArrayTypeNode) result.ext(fabricExtFactory()
            .extFabricArrayTypeNode());
    result =
        (FabricArrayTypeNode) result.del(fabricDelFactory()
            .delFabricArrayTypeNode());
    return result;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // overloaded factory methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public New New(Position pos, TypeNode type, Expr location, List<Expr> args) {
    New result = New(pos, type, args);
    result = (New) setLocation(result, location);
    return result;
  }

  @Override
  public New New(Position pos, TypeNode type, Expr location, List<Expr> args,
      polyglot.ast.ClassBody body) {
    New result = New(pos, type, args, body);
    result = (New) setLocation(result, location);
    return result;
  }

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args) {
    New result = New(pos, outer, objectType, args, null);
    result = (New) setLocation(result, location);
    return result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, List args,
      ClassBody body) {
    if (body != null)
      throw new InternalCompilerError("Fabric does not support inner classes.");
    if (outer != null)
      throw new InternalCompilerError("Fabric does not support inner classes.");

    @SuppressWarnings("unchecked")
    New n = new FabricNew_c(pos, objectType, args, body);
    n = (New) n.ext(extFactory().extNew());
    n = (New) n.del(delFactory().delNew());
    return n;
  }

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args, polyglot.ast.ClassBody body) {
    New n = New(pos, outer, objectType, args, body);
    n = (New) setLocation(n, location);
    return n;
  }

  @Override
  public FabricArrayInit FabricArrayInit(Position position, List<Expr> elements) {
    return FabricArrayInit(position, null, null, elements);
  }

  @Override
  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, List<Expr> dims) {
    return NewFabricArray(pos, base, location, dims, 0, null);
  }

  @Override
  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, List<Expr> dims, int addDims) {
    return NewFabricArray(pos, base, location, dims, addDims, null);
  }

  @Override
  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, int addDims, FabricArrayInit init) {
    List<Expr> empty = Collections.emptyList();
    return NewFabricArray(pos, base, location, empty, addDims, init);
  }

  @Override
  public NewLabel NewLabel(Position pos, LabelNode label, Expr location) {
    NewLabel result = super.NewLabel(pos, label);
    result = (NewLabel) setLocation(result, location);
    return result;
  }

  @Override
  public PrincipalExpr PrincipalExpr(Position pos, PrincipalNode principal,
      Expr location) {
    PrincipalExpr n = super.PrincipalExpr(pos, principal);
    n =
        (PrincipalExpr) n.del(((FabricDelFactory) delFactory())
            .delPrincipalExpr());
    n = (PrincipalExpr) setLocation(n, location);
    return n;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // overridden factory methods //
  // ////////////////////////////////////////////////////////////////////////////

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public ClassDecl ClassDecl(Position pos, Flags flags, Id name,
      TypeNode superClass, List interfaces, ClassBody body) {
    ClassDecl n =
        new ClassDecl_c(pos, flags, name, Collections.<ParamDecl> emptyList(),
            superClass, interfaces, Collections.<PrincipalNode> emptyList(),
            Collections.<ConstraintNode<Assertion>> emptyList(), body);
    n = (ClassDecl) n.ext(extFactory().extClassDecl());
    n = (ClassDecl) n.del(delFactory().delClassDecl());
    return n;
  }

  @Override
  public JifClassDecl JifClassDecl(Position pos, Flags flags, Id name,
      List<ParamDecl> params, TypeNode superClass, List<TypeNode> interfaces,
      List<PrincipalNode> authority,
      List<ConstraintNode<Assertion>> constraints, ClassBody body) {
    JifClassDecl n =
        new ClassDecl_c(pos, flags, name, params, superClass, interfaces,
            authority, constraints, body);
    n = (JifClassDecl) n.ext(extFactory().extClassDecl());
    n = (JifClassDecl) n.del(delFactory().delClassDecl());
    return n;
  }

  @Override
  public FabricFieldDecl FabricFieldDecl(Position pos, Flags flags,
      TypeNode type, LabelNode accessLabel, Id name, Expr init) {
    FabricFieldDecl n =
        new FabricFieldDecl_c(pos, flags, type, accessLabel, name, init);
    n = (FabricFieldDecl) n.ext(extFactory().extFieldDecl());
    n = (FabricFieldDecl) n.del(delFactory().delFieldDecl());
    return n;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public ClassBody ClassBody(Position pos, List members) {
    @SuppressWarnings("unchecked")
    ClassBody n = new ClassBody_c(pos, CollectionUtil.nonNullList(members));
    n = (ClassBody) n.ext(extFactory().extClassBody());
    n = (ClassBody) n.del(delFactory().delClassBody());
    return n;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public Call Call(Position pos, Receiver target, Id name, List args) {
    return Call(pos, target, name, null, args);
  }

  @Override
  public CanonicalTypeNode CanonicalTypeNode(Position pos, Type type) {
    return super.CanonicalTypeNode(pos, type);
  }

  @Override
  public Call Call(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> args) {
    Call n = new FabricCall_c(pos, target, name, remoteWorker, args);
    n = (Call) n.ext(extFactory().extCall());
    n = (Call) n.del(delFactory().delCall());
    return n;
  }

  @Override
  public AmbPrincipalNode AmbPrincipalNode(Position pos, Expr expr) {
    AmbPrincipalNode n = new FabricAmbPrincipalNode_c(pos, expr);
    n = (AmbPrincipalNode) n.ext(jifExtFactory().extAmbPrincipalNode());
    n = (AmbPrincipalNode) n.del(delFactory().delExpr());
    return n;
  }

  @Override
  public AmbPrincipalNode AmbPrincipalNode(Position pos, Id name) {
    AmbPrincipalNode n = new FabricAmbPrincipalNode_c(pos, name);
    n = (AmbPrincipalNode) n.ext(jifExtFactory().extAmbPrincipalNode());
    n = (AmbPrincipalNode) n.del(delFactory().delExpr());
    return n;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public SourceFile SourceFile(Position pos, PackageNode packageName,
      List imports, List decls) {
    return SourceFile(pos, packageName, Collections.EMPTY_LIST, imports, decls);
  }

  @Override
  public SourceFile SourceFile(Position pos, PackageNode packageName,
      List<CodebaseDecl> codebases, List<Import> imports,
      List<TopLevelDecl> decls) {
    SourceFile sf =
        new CBSourceFile_c(pos, packageName, imports, codebases, decls);
    sf = (SourceFile) sf.ext(jifExtFactory().extSourceFile());
    sf = (SourceFile) sf.del(delFactory().delSourceFile());
    return sf;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // private helper methods //
  // ////////////////////////////////////////////////////////////////////////////

  private FabricDelFactory fabricDelFactory() {
    return (FabricDelFactory) this.delFactory();
  }

  private FabricExtFactory fabricExtFactory() {
    return (FabricExtFactory) this.extFactory();
  }

  /**
   * Update the provided node with a given location.
   * 
   * @param result
   *          a Node having a LocatedExt as a Fabric extension
   * @param location
   *          the expression representing the location
   * @return a copy of <code>result</code> with the Fabric extension updated
   *         with the location
   */
  protected Node setLocation(Node result, Expr location) {
    FabricExt fabExt = FabricUtil.fabricExt(result);
    // Ext jifExt = result.ext();
    // Ext fabExt = jifExt.ext();

    fabExt = ((LocatedExt_c) fabExt).location(location);

    result = FabricUtil.updateFabricExt(result, fabExt);
    // jifExt = jifExt.ext(fabExt);
    // result = result.ext(jifExt);

    return result;
  }
}
