package fabric.ast;

import java.net.URI;
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
import polyglot.ast.ClassBody;
import polyglot.ast.Disamb;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;
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
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import codebases.ast.CBSourceFile_c;
import codebases.ast.CodebaseDecl;
import codebases.ast.CodebaseDecl_c;
import codebases.ast.CodebaseNode;
import codebases.ast.CodebaseNode_c;
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

  // ////////////////////////////////////////////////////////////////////////////
  // new factory methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public CodebaseNode CodebaseNode(Position pos, URI ns, String name,
      URI externalNS) {
    return CodebaseNode(pos, ns, name, externalNS, null);
  }

  @Override
  public CodebaseNode CodebaseNode(Position pos, URI ns, String name,
      URI externalNS, Package package_) {
    CodebaseNode n =
        CodebaseNode(pos, ns, name, externalNS, package_, null, extFactory());
    n = del(n, fabricDelFactory().delCodebaseNode());
    return n;
  }

  protected final CodebaseNode CodebaseNode(Position pos, URI ns, String name,
      URI externalNS, Package package_, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extCodebaseNode());
    return new CodebaseNode_c(pos, ns, name, externalNS, package_, ext);
  }

  @Override
  public CodebaseDecl CodebaseDecl(Position pos, Id name) {
    CodebaseDecl n = CodebaseDecl(pos, name, null, extFactory());
    n = del(n, fabricDelFactory().delCodebaseDecl());
    return n;
  }

  protected final CodebaseDecl CodebaseDecl(Position pos, Id name, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extCodebaseDecl());
    return new CodebaseDecl_c(pos, name, ext);
  }

  @Override
  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic result = Atomic(pos, statements, null, extFactory());
    result = del(result, fabricDelFactory().delAtomic());
    return result;
  }

  protected final Atomic Atomic(Position pos, List<Stmt> statements, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extAtomic());
    return new Atomic_c(pos, statements, ext);
  }

  @Override
  public AmbNewFabricArray AmbNewFabricArray(Position pos, TypeNode base,
      Expr loc, Object expr, List<Expr> dims, int addDims) {
    AmbNewFabricArray result =
        AmbNewFabricArray(pos, base, loc, expr, dims, addDims, null,
            extFactory());
    result = del(result, fabricDelFactory().delAmbNewFabricArray());
    return result;
  }

  protected final AmbNewFabricArray AmbNewFabricArray(Position pos,
      TypeNode base, Expr loc, Object expr, List<Expr> dims, int addDims,
      Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extAmbNewFabricArray());
    return new AmbNewFabricArray_c(pos, base, loc, expr, dims, addDims, ext);
  }

  @Override
  public RetryStmt RetryStmt(Position pos) {
    RetryStmt s = RetryStmt(pos, null, extFactory());
    s = del(s, fabricDelFactory().delStmt());
    return s;
  }

  protected final RetryStmt RetryStmt(Position pos, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extRetryStmt());
    return new RetryStmt_c(pos, ext);
  }

  @Override
  public AbortStmt AbortStmt(Position pos) {
    AbortStmt s = AbortStmt(pos, null, extFactory());
    s = del(s, fabricDelFactory().delStmt());
    return s;
  }

  protected final AbortStmt AbortStmt(Position pos, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extAbortStmt());
    return new AbortStmt_c(pos, ext);
  }

  @Override
  public Worker Worker(Position pos) {
    Worker n = Worker(pos, null, extFactory());
    n = del(n, fabricDelFactory().delWorker());
    return n;
  }

  protected final Worker Worker(Position pos, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extWorker());
    return new Worker_c(pos, Id(pos, "worker$"), ext);
  }

  @Override
  public Store Store(Position pos, Expr expr) {
    Store n = Store(pos, expr, null, extFactory());
    n = del(n, fabricDelFactory().delStore());
    return n;
  }

  protected final Store Store(Position pos, Expr expr, Ext ext,
      ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extStore());
    return new Store_c(pos, expr, ext);
  }

  @Override
  public RemoteWorkerGetter RemoteWorkerGetter(Position pos, Expr remoteName) {
    RemoteWorkerGetter n =
        RemoteWorkerGetter(pos, remoteName, null, extFactory());
    n = del(n, fabricDelFactory().delRemoteWorkerGetter());
    return n;
  }

  protected final RemoteWorkerGetter RemoteWorkerGetter(Position pos,
      Expr remoteName, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extRemoteWorkerGetter());
    return new RemoteWorkerGetter_c(pos, remoteName, ext);
  }

  @Override
  public NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, List<Expr> dims, int addDims, FabricArrayInit init) {
    NewFabricArray result =
        NewFabricArray(pos, base, location, dims, addDims, init, null,
            extFactory());
    result = del(result, fabricDelFactory().delNewFabricArray());
    return result;
  }

  protected final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, List<Expr> dims, int addDims, FabricArrayInit init,
      Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extNewFabricArray());
    return new NewFabricArray_c(pos, base, dims, addDims, init, ext);
  }

  @Override
  public FabricArrayInit FabricArrayInit(Position pos, Expr label,
      Expr location, List<Expr> elements) {
    FabricArrayInit result =
        FabricArrayInit(pos, label, location, elements, null, extFactory());
    result = del(result, fabricDelFactory().delFabricArrayInit());
    return result;
  }

  protected final FabricArrayInit FabricArrayInit(Position pos, Expr label,
      Expr location, List<Expr> elements, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extFabricArrayInit());
    return new FabricArrayInit_c(pos, elements, label, location, ext);
  }

  @Override
  public FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type) {
    FabricArrayTypeNode result =
        FabricArrayTypeNode(pos, type, null, extFactory());
    result = del(result, fabricDelFactory().delFabricArrayTypeNode());
    return result;
  }

  protected final FabricArrayTypeNode FabricArrayTypeNode(Position pos,
      TypeNode type, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext =
            composeExts(ext, ((FabricExtFactory) ef).extFabricArrayTypeNode());
    return new FabricArrayTypeNode_c(pos, type, ext);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // overloaded factory methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public New New(Position pos, TypeNode type, Expr location, List<Expr> args) {
    New result = New(pos, type, args);
    result = setLocation(result, location);
    return result;
  }

  @Override
  public New New(Position pos, TypeNode type, Expr location, List<Expr> args,
      polyglot.ast.ClassBody body) {
    New result = New(pos, type, args, body);
    result = setLocation(result, location);
    return result;
  }

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args) {
    New result = New(pos, outer, objectType, args, null);
    result = setLocation(result, location);
    return result;
  }

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType,
      List<Expr> args, ClassBody body) {
    New n = FabricNew(pos, outer, objectType, args, body, null, extFactory());
    n = del(n, delFactory().delNew());
    return n;
  }

  protected final New FabricNew(Position pos, Expr outer, TypeNode objectType,
      List<Expr> args, ClassBody body, Ext ext, ExtFactory extFactory) {
    if (body != null)
      throw new InternalCompilerError("Fabric does not support inner classes.");
    if (outer != null)
      throw new InternalCompilerError("Fabric does not support inner classes.");

    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extNew());
    return new FabricNew_c(pos, outer, objectType, args, body, ext);
  }

  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args, polyglot.ast.ClassBody body) {
    New n = New(pos, outer, objectType, args, body);
    n = setLocation(n, location);
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
    NewLabel result = NewLabel(pos, label);
    result = setLocation(result, location);
    return result;
  }

  @Override
  public PrincipalExpr PrincipalExpr(Position pos, PrincipalNode principal,
      Expr location) {
    PrincipalExpr n = PrincipalExpr(pos, principal);
    // XXX yuck. This should be done by modifying Jif's del factory. 
    n =
        (PrincipalExpr) n.del(((FabricDelFactory) delFactory())
            .delPrincipalExpr());
    n = setLocation(n, location);
    return n;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // overridden factory methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public JifClassDecl JifClassDecl(Position pos, Flags flags, Id name,
      List<ParamDecl> params, TypeNode superClass, List<TypeNode> interfaces,
      List<PrincipalNode> authority,
      List<ConstraintNode<Assertion>> constraints, ClassBody body) {
    JifClassDecl n =
        FabricClassDecl(pos, flags, name, params, superClass, interfaces,
            authority, constraints, body, null, extFactory());
    n = (JifClassDecl) n.del(delFactory().delClassDecl());
    return n;
  }

  protected final JifClassDecl FabricClassDecl(Position pos, Flags flags,
      Id name, List<ParamDecl> params, TypeNode superClass,
      List<TypeNode> interfaces, List<PrincipalNode> authority,
      List<ConstraintNode<Assertion>> constraints, ClassBody body, Ext ext,
      ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extClassDecl());
    return new ClassDecl_c(pos, flags, name, params, superClass, interfaces,
        authority, constraints, body, ext);
  }

  @Override
  public FabricFieldDecl FabricFieldDecl(Position pos, Flags flags,
      TypeNode type, LabelNode accessLabel, Id name, Expr init) {
    FabricFieldDecl n =
        FabricFieldDecl(pos, flags, type, accessLabel, name, init, null,
            extFactory());
    n = del(n, delFactory().delFieldDecl());
    return n;
  }

  protected final FabricFieldDecl FabricFieldDecl(Position pos, Flags flags,
      TypeNode type, LabelNode accessLabel, Id name, Expr init, Ext ext,
      ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extFieldDecl());
    return new FabricFieldDecl_c(pos, flags, type, accessLabel, name, init, ext);
  }

  @Override
  public AccessPolicy AccessPolicy(Position pos, LabelNode ln) {
    AccessPolicy n = AccessPolicy(pos, ln, null, extFactory());
    n = del(n, fabricDelFactory().delAccessPolicy());
    return n;
  }

  protected final AccessPolicy AccessPolicy(Position pos, LabelNode ln,
      Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extAccessPolicy());
    return new AccessPolicy_c(pos, ln, ext);
  }

  @Override
  public Call Call(Position pos, Receiver target, Id name, List<Expr> args) {
    return Call(pos, target, name, null, args);
  }

  @Override
  public Call Call(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> args) {
    Call n =
        FabricCall(pos, target, name, remoteWorker, args, null, extFactory());
    n = del(n, delFactory().delCall());
    return n;
  }

  protected final Call FabricCall(Position pos, Receiver target, Id name,
      Expr remoteWorker, List<Expr> args, Ext ext, ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extCall());
    return new FabricCall_c(pos, target, name, remoteWorker, args, ext);
  }

  @Override
  public AmbPrincipalNode AmbPrincipalNode(Position pos, Expr expr) {
    AmbPrincipalNode n = FabricAmbPrincipalNode(pos, expr, null, extFactory());
    n = del(n, delFactory().delExpr());
    return n;
  }

  protected final AmbPrincipalNode FabricAmbPrincipalNode(Position pos,
      Expr expr, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extAmbPrincipalNode());
    return new FabricAmbPrincipalNode_c(pos, expr, ext);
  }

  @Override
  public AmbPrincipalNode AmbPrincipalNode(Position pos, Id name) {
    AmbPrincipalNode n = FabricAmbPrincipalNode(pos, name, null, extFactory());
    n = del(n, delFactory().delExpr());
    return n;
  }

  protected final AmbPrincipalNode FabricAmbPrincipalNode(Position pos,
      Id name, Ext ext, ExtFactory extFactory) {
    // XXX TODO FIXME What's the new way of doing things?
    for (ExtFactory ef : extFactory)
      if (ef instanceof FabricExtFactory)
        ext = composeExts(ext, ((FabricExtFactory) ef).extAmbPrincipalNode());
    return new FabricAmbPrincipalNode_c(pos, name, ext);
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
        CBSourceFile(pos, packageName, codebases, imports, decls, null,
            extFactory());
    sf = (SourceFile) sf.del(delFactory().delSourceFile());
    return sf;
  }

  protected final SourceFile CBSourceFile(Position pos,
      PackageNode packageName, List<CodebaseDecl> codebases,
      List<Import> imports, List<TopLevelDecl> decls, Ext ext,
      ExtFactory extFactory) {
    for (ExtFactory ef : extFactory)
      ext = composeExts(ext, ef.extSourceFile());

    return new CBSourceFile_c(pos, packageName, imports, codebases, decls, ext);
  }

  // ////////////////////////////////////////////////////////////////////////////
  // private helper methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Deprecated
  private FabricDelFactory fabricDelFactory() {
    return (FabricDelFactory) this.delFactory();
  }

  private FabricExtFactory fabricExtFactory() {
    return (FabricExtFactory) this.extFactory();
  }

  /**
   * Updates the provided node with a given location.
   * 
   * @param result
   *          a Node having a LocatedExt as a Fabric extension
   * @param location
   *          the expression representing the location
   * @return a copy of <code>result</code> with the Fabric extension updated
   *         with the location
   */
  private <N extends Node> N setLocation(N result, Expr location) {
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
