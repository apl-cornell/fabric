package fabric.ast;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import codebases.ast.CBSourceFile_c;
import codebases.ast.CodebaseDecl;
import codebases.ast.CodebaseDecl_c;
import codebases.ast.CodebaseNode;
import codebases.ast.CodebaseNode_c;

import fabric.extension.FabricExt;
import fabric.extension.LocatedExt_c;

import jif.ast.AmbPrincipalNode;
import jif.ast.ConstraintNode;
import jif.ast.JifClassDecl;
import jif.ast.JifConstructorDecl;
import jif.ast.JifConstructorDecl_c;
import jif.ast.JifMethodDecl;
import jif.ast.JifNodeFactory_c;
import jif.ast.LabelNode;
import jif.ast.NewLabel;
import jif.ast.ParamDecl;
import jif.ast.PolicyNode;
import jif.ast.PrincipalExpr;
import jif.ast.PrincipalNode;
import jif.types.Assertion;

import polyglot.ast.Block;
import polyglot.ast.Call;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassMember;
import polyglot.ast.ConstructorDecl;
import polyglot.ast.Disamb;
import polyglot.ast.Expr;
import polyglot.ast.Formal;
import polyglot.ast.Id;
import polyglot.ast.Import;
import polyglot.ast.Javadoc;
import polyglot.ast.MethodDecl;
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
    CodebaseNode n = new CodebaseNode_c(pos, ns, name, externalNS, package_);
    n = ext(n, fabricExtFactory().extCodebaseNode());
    n = del(n, fabricDelFactory().delCodebaseNode());
    return n;
  }

  @Override
  public CodebaseDecl CodebaseDecl(Position pos, Id name) {
    CodebaseDecl n = new CodebaseDecl_c(pos, name);
    n = ext(n, fabricExtFactory().extCodebaseDecl());
    n = del(n, fabricDelFactory().delCodebaseDecl());
    return n;
  }

  @Override
  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic result = new Atomic_c(pos, statements);
    result = ext(result, fabricExtFactory().extAtomic());
    result = del(result, fabricDelFactory().delAtomic());
    return result;
  }

  @Override
  public AmbNewFabricArray AmbNewFabricArray(Position pos, TypeNode base,
      Expr loc, Object expr, List<Expr> dims, int addDims) {
    AmbNewFabricArray result =
        new AmbNewFabricArray_c(pos, base, loc, expr, dims, addDims);
    result = ext(result, fabricExtFactory().extAmbNewFabricArray());
    result = del(result, fabricDelFactory().delAmbNewFabricArray());
    return result;
  }

  @Override
  public RetryStmt RetryStmt(Position pos) {
    RetryStmt s = new RetryStmt_c(pos);
    s = ext(s, fabricExtFactory().extRetryStmt());
    s = del(s, fabricDelFactory().delStmt());
    return s;
  }

  @Override
  public AbortStmt AbortStmt(Position pos) {
    AbortStmt s = new AbortStmt_c(pos);
    s = ext(s, fabricExtFactory().extAbortStmt());
    s = del(s, fabricDelFactory().delStmt());
    return s;
  }

  @Override
  public Worker Worker(Position pos) {
    Worker n = new Worker_c(pos, Id(pos, "worker$"));
    n = ext(n, fabricExtFactory().extWorker());
    n = del(n, fabricDelFactory().delWorker());
    return n;
  }

  @Override
  public Store Store(Position pos, Expr expr) {
    Store n = new Store_c(pos, expr);
    n = ext(n, fabricExtFactory().extStore());
    n = del(n, fabricDelFactory().delStore());
    return n;
  }

  @Override
  public RemoteWorkerGetter RemoteWorkerGetter(Position pos, Expr remoteName) {
    RemoteWorkerGetter n = new RemoteWorkerGetter_c(pos, remoteName);
    n = ext(n, fabricExtFactory().extRemoteWorkerGetter());
    n = del(n, fabricDelFactory().delRemoteWorkerGetter());
    return n;
  }

  @Override
  public NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, List<Expr> dims, int addDims, FabricArrayInit init) {
    NewFabricArray result =
        new NewFabricArray_c(pos, base, dims, addDims, init);
    result = ext(result, fabricExtFactory().extNewFabricArray());
    result = del(result, fabricDelFactory().delNewFabricArray());

    LocatedExt_c ext = (LocatedExt_c) FabricUtil.fabricExt(result);
    result = FabricUtil.updateFabricExt(result, ext.location(location));
    return result;
  }

  @Override
  public FabricArrayInit FabricArrayInit(Position pos, Expr label,
      Expr location, List<Expr> elements) {
    FabricArrayInit result =
        new FabricArrayInit_c(pos, elements, label, location);
    result = ext(result, fabricExtFactory().extFabricArrayInit());
    result = del(result, fabricDelFactory().delFabricArrayInit());
    return result;
  }

  @Override
  public FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type) {
    return FabricArrayTypeNode(pos, type, null);
  }

  @Override
  public FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type, LabelNode accessLabel) {
    FabricArrayTypeNode result = new FabricArrayTypeNode_c(pos, type, accessLabel);
    result = ext(result, fabricExtFactory().extFabricArrayTypeNode());
    result = del(result, fabricDelFactory().delFabricArrayTypeNode());
    return result;
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
    if (body != null)
      throw new InternalCompilerError("Fabric does not support inner classes.");
    if (outer != null)
      throw new InternalCompilerError("Fabric does not support inner classes.");

    New n = new FabricNew_c(pos, outer, objectType, args, body);
    n = ext(n, extFactory().extNew());
    n = del(n, delFactory().delNew());
    return n;
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
  public MethodDecl MethodDecl(Position pos, Flags flags,
      TypeNode returnType, Id name, List<Formal> formals,
      List<TypeNode> throwTypes, Block body, Javadoc javadoc) {
    MethodDecl n =
        new FabricMethodDecl_c(pos, flags, returnType, name, null, null,
            formals, null, null, throwTypes,
            Collections.<ConstraintNode<Assertion>> emptyList(),
            body, javadoc);
    n = ext(n, extFactory().extMethodDecl());
    n = del(n, delFactory().delMethodDecl());
    return n;
  }

  @Override
  public JifMethodDecl JifMethodDecl(Position pos, Flags flags,
      TypeNode returnType, Id name, LabelNode startLabel,
      List<Formal> formals, LabelNode endLabel,
      List<TypeNode> throwTypes,
      List<ConstraintNode<Assertion>> constraints, Block body,
      Javadoc javadoc) {
    JifMethodDecl n =
        new FabricMethodDecl_c(pos, flags, returnType, name, startLabel, null,
            formals, endLabel, null, throwTypes, constraints, body, javadoc);
    n = ext(n, extFactory().extMethodDecl());
    n = del(n, delFactory().delMethodDecl());
    return n;
  }

  @Override
  public FabricMethodDecl FabricMethodDecl(Position pos, Flags flags,
      TypeNode returnType, Id name, LabelNode startLabel,
      LabelNode beginAccessPolicy, List<Formal> arguments, LabelNode endLabel,
      LabelNode endConfPolicy, List<TypeNode> exceptions,
      List<ConstraintNode<Assertion>> constraints, Block body, Javadoc javadoc) {
    FabricMethodDecl n =
        new FabricMethodDecl_c(pos, flags, returnType, name, startLabel,
            beginAccessPolicy, arguments, endLabel, endConfPolicy, exceptions,
            constraints, body, javadoc);
    n = ext(n, extFactory().extMethodDecl());
    n = del(n, delFactory().delMethodDecl());
    return n;
  }

  @Override
  public ConstructorDecl ConstructorDecl(Position pos, Flags flags, Id name,
      List<Formal> formals, List<TypeNode> throwTypes, Block body,
      Javadoc javadoc) {
    ConstructorDecl n =
        new FabricConstructorDecl_c(pos, flags, name, null, null, null, formals,
            null, throwTypes,
            Collections.<ConstraintNode<Assertion>> emptyList(), body, javadoc);
    n = ext(n, extFactory().extConstructorDecl());
    n = del(n, delFactory().delConstructorDecl());
    return n;
  }

  @Override
  public JifConstructorDecl JifConstructorDecl(Position pos, Flags flags,
      Id name, LabelNode startLabel, LabelNode returnLabel,
      List<Formal> formals, List<TypeNode> throwTypes,
      List<ConstraintNode<Assertion>> constraints, Block body,
      Javadoc javadoc) {
    JifConstructorDecl n =
        new FabricConstructorDecl_c(pos, flags, name, startLabel, null,
            returnLabel, formals, null, throwTypes, constraints, body,
            javadoc);
    n = ext(n, extFactory().extConstructorDecl());
    n = del(n, delFactory().delConstructorDecl());
    return n;
  }

  @Override
  public FabricConstructorDecl FabricConstructorDecl(Position pos, Flags flags,
      Id name, LabelNode startLabel, LabelNode beginAccessPolicy,
      LabelNode returnLabel, List<Formal> arguments, LabelNode endConfPolicy,
      List<TypeNode> exceptions, List<ConstraintNode<Assertion>> constraints,
      Block body, Javadoc javadoc) {
    FabricConstructorDecl n =
        new FabricConstructorDecl_c(pos, flags, name, startLabel,
            beginAccessPolicy, returnLabel, arguments, endConfPolicy,
            exceptions, constraints, body, javadoc);
    n = ext(n, extFactory().extConstructorDecl());
    n = del(n, delFactory().delConstructorDecl());
    return n;
  }

  @Override
  public PrincipalExpr PrincipalExpr(Position pos, PrincipalNode principal,
      Expr location) {
    PrincipalExpr n = PrincipalExpr(pos, principal);
    // XXX yuck. This should be done by modifying Jif's del factory.
    n = del(n, ((FabricDelFactory) delFactory()).delPrincipalExpr());
    n = setLocation(n, location);
    return n;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // overridden factory methods //
  // ////////////////////////////////////////////////////////////////////////////

  @Override
  public JifClassDecl ClassDecl(Position pos, Flags flags, Id name,
      TypeNode superClass, List<TypeNode> interfaces, ClassBody body,
      Javadoc javadoc) {
    return JifClassDecl(pos, flags, name, Collections.<ParamDecl> emptyList(),
        superClass, interfaces, Collections.<PrincipalNode> emptyList(),
        Collections.<ConstraintNode<Assertion>> emptyList(), body, javadoc);
  }

  @Override
  public JifClassDecl JifClassDecl(Position pos, Flags flags, Id name,
      List<ParamDecl> params, TypeNode superClass, List<TypeNode> interfaces,
      List<PrincipalNode> authority,
      List<ConstraintNode<Assertion>> constraints, ClassBody body,
      Javadoc javadoc) {
    JifClassDecl n =
        new ClassDecl_c(pos, flags, name, params, superClass, interfaces,
            authority, constraints, body, javadoc);
    n = ext(n, extFactory().extClassDecl());
    n = del(n, delFactory().delClassDecl());
    return n;
  }

  @Override
  public ClassBody ClassBody(Position pos, List<ClassMember> members) {
    ClassBody n = new FabricClassBody_c(pos, members);
    n = ext(n, extFactory().extClassBody());
    n = del(n, delFactory().delClassBody());
    return n;
  }

  @Override
  public FabricFieldDecl FabricFieldDecl(Position pos, Flags flags,
      TypeNode type, LabelNode accessLabel, Id name, Expr init, Javadoc javadoc) {
    FabricFieldDecl n =
        new FabricFieldDecl_c(pos, flags, type, accessLabel, name, init,
            javadoc);
    n = ext(n, extFactory().extFieldDecl());
    n = del(n, delFactory().delFieldDecl());
    return n;
  }

  @Override
  public AccessPolicy AccessPolicy(Position pos, LabelNode ln) {
    AccessPolicy n = new AccessPolicy_c(pos, ln);
    n = ext(n, fabricExtFactory().extAccessPolicy());
    n = del(n, fabricDelFactory().delAccessPolicy());
    return n;
  }

  @Override
  public Call Call(Position pos, Receiver target, Id name, List<Expr> args) {
    return Call(pos, target, name, null, args);
  }

  @Override
  public Call Call(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> args) {
    Call n = new FabricCall_c(pos, target, name, remoteWorker, args);
    n = ext(n, extFactory().extCall());
    n = del(n, delFactory().delCall());
    return n;
  }

  @Override
  public AmbPrincipalNode AmbPrincipalNode(Position pos, Expr expr) {
    AmbPrincipalNode n = new FabricAmbPrincipalNode_c(pos, expr);
    n = ext(n, jifExtFactory().extAmbPrincipalNode());
    n = del(n, delFactory().delExpr());
    return n;
  }

  @Override
  public AmbPrincipalNode AmbPrincipalNode(Position pos, Id name) {
    AmbPrincipalNode n = new FabricAmbPrincipalNode_c(pos, name);
    n = ext(n, jifExtFactory().extAmbPrincipalNode());
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
    sf = ext(sf, jifExtFactory().extSourceFile());
    sf = del(sf, delFactory().delSourceFile());
    return sf;
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
