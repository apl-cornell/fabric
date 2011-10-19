package fabric.ast;

import java.util.Collections;
import java.util.List;

import codebases.ast.CodebaseDecl;
import codebases.ast.CodebaseDecl_c;
import codebases.ast.CodebaseDisamb;
import codebases.ast.CodebaseNode;
import codebases.ast.CodebaseNode_c;
import codebases.ast.CBSourceFile_c;

import jif.ast.AmbPrincipalNode;
import jif.ast.JifClassDecl;
import jif.ast.JifDisamb_c;
import jif.ast.JifNodeFactory_c;
import jif.ast.LabelNode;
import jif.ast.NewLabel;
import polyglot.ast.Call;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.Disamb;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.New;
import polyglot.ast.Node;
import polyglot.ast.PackageNode;
import polyglot.ast.Receiver;
import polyglot.ast.SourceFile;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.util.CollectionUtil;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import fabric.extension.FabricExt;
import fabric.extension.LocatedExt_c;
import fabric.lang.Codebase;
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
  public CodebaseNode CodebaseNode(Position pos, Codebase c) {  
    CodebaseNode n = new CodebaseNode_c(pos, c);
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

  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic result = new Atomic_c(pos, statements);
    // note: this is correct. fabricExtFactory() is a factory that returns Jif
    // extension objects with their ext() pointers referring to Fabric Ext
    // objects, which is as it should be.
    result = (Atomic) result.ext(fabricExtFactory().extAtomic());
    result = (Atomic) result.del(fabricDelFactory().delAtomic());
    return result;
  }

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

  public RetryStmt RetryStmt(Position pos) {
    RetryStmt s = new RetryStmt_c(pos);
    s = (RetryStmt) s.ext(fabricExtFactory().extRetryStmt());
    s = (RetryStmt) s.del(fabricDelFactory().delStmt());
    return s;
  }

  public AbortStmt AbortStmt(Position pos) {
    AbortStmt s = new AbortStmt_c(pos);
    s = (AbortStmt) s.ext(fabricExtFactory().extAbortStmt());
    s = (AbortStmt) s.del(fabricDelFactory().delStmt());
    return s;
  }

  public Worker Worker(Position pos) {
    Worker n = new Worker_c(pos, this);
    n = (Worker) n.ext(fabricExtFactory().extWorker());
    n = (Worker) n.del(fabricDelFactory().delWorker());
    return n;
  }

  public RemoteWorkerGetter RemoteWorkerGetter(Position pos, Expr remoteName) {
    RemoteWorkerGetter n = new RemoteWorkerGetter_c(pos, remoteName);
    // TODO add the real extension and delegation for RemoteWorkerGetter.
    n = (RemoteWorkerGetter) n.ext(fabricExtFactory().extRemoteWorkerGetter());
    n = (RemoteWorkerGetter) n.del(fabricDelFactory().delRemoteWorkerGetter());
    return n;
  }

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

  public New New(Position pos, TypeNode type, Expr location, List<Expr> args) {
    New result = New(pos, type, args);
    result = (New) setLocation(result, location);
    return result;
  }

  public New New(Position pos, TypeNode type, Expr location, List<Expr> args,
      polyglot.ast.ClassBody body) {
    New result = New(pos, type, args, body);
    result = (New) setLocation(result, location);
    return result;
  }

  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args) {
    New result = New(pos, outer, objectType, args, null);
    result = (New) setLocation(result, location);
    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, List args,
      ClassBody body) {
    if (body != null)
      throw new InternalCompilerError("Fabric does not support inner classes.");
    if (outer != null)
      throw new InternalCompilerError("Fabric does not support inner classes.");
    New n = new FabricNew_c(pos, objectType, args, body);
    n = (New) n.ext(extFactory().extNew());
    n = (New) n.del(delFactory().delNew());
    return n;
  }

  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args, polyglot.ast.ClassBody body) {
    New n = New(pos, outer, objectType, args, body);
    n = (New) setLocation(n, location);
    return n;
  }

  public FabricArrayInit FabricArrayInit(Position position, List<Expr> elements) {
    return FabricArrayInit(position, null, null, elements);
  }

  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, List<Expr> dims) {
    return NewFabricArray(pos, base, location, dims, 0, null);
  }

  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, List<Expr> dims, int addDims) {
    return NewFabricArray(pos, base, location, dims, addDims, null);
  }

  public final NewFabricArray NewFabricArray(Position pos, TypeNode base,
      Expr location, int addDims, FabricArrayInit init) {
    List<Expr> empty = Collections.emptyList();
    return NewFabricArray(pos, base, location, empty, addDims, init);
  }

  public NewLabel NewLabel(Position pos, LabelNode label, Expr location) {
    NewLabel result = super.NewLabel(pos, label);
    result = (NewLabel) setLocation(result, location);
    return result;
  }

  // ////////////////////////////////////////////////////////////////////////////
  // overridden factory methods //
  // ////////////////////////////////////////////////////////////////////////////

  @SuppressWarnings("unchecked")
  @Override
  public ClassDecl ClassDecl(Position pos, Flags flags, Id name,
      TypeNode superClass, List interfaces, ClassBody body) {
    ClassDecl n =
        new ClassDecl_c(pos, flags, name, Collections.EMPTY_LIST, superClass,
            interfaces, Collections.EMPTY_LIST, Collections.EMPTY_LIST, body);
    n = (ClassDecl) n.ext(extFactory().extClassDecl());
    n = (ClassDecl) n.del(delFactory().delClassDecl());
    return n;
  }

  @SuppressWarnings("unchecked")
  @Override
  public JifClassDecl JifClassDecl(Position pos, Flags flags, Id name,
      List params, TypeNode superClass, List interfaces, List authority,
      List constraints, ClassBody body) {
    JifClassDecl n =
        new ClassDecl_c(pos, flags, name, params, superClass, interfaces,
            authority, constraints, body);
    n = (JifClassDecl) n.ext(extFactory().extClassDecl());
    n = (JifClassDecl) n.del(delFactory().delClassDecl());
    return n;
  }
  
  public FabricFieldDecl FabricFieldDecl(Position pos, Flags flags, 
      TypeNode type, LabelNode accessLabel, Id name, Expr init) {
    FabricFieldDecl n = new FabricFieldDecl_c(pos, flags, type, accessLabel,
        name, init);
    n = (FabricFieldDecl)n.ext(extFactory().extFieldDecl());
    n = (FabricFieldDecl)n.del(delFactory().delFieldDecl());    
    return n;
  }
  

  @SuppressWarnings("unchecked")
  @Override
  public ClassBody ClassBody(Position pos, List members) {
    ClassBody n = new ClassBody_c(pos, CollectionUtil.nonNullList(members));
    n = (ClassBody)n.ext(extFactory().extClassBody());
    n = (ClassBody)n.del(delFactory().delClassBody());
    return n;  }

  @SuppressWarnings("unchecked")
  @Override
  public Call Call(Position pos, Receiver target, Id name, List args) {
    return Call(pos, target, name, null, args);
  }

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
  
  @SuppressWarnings("unchecked")  
  @Override
  public SourceFile SourceFile(Position pos, PackageNode packageName, 
      List imports, List decls) {
    return SourceFile(pos, packageName, Collections.EMPTY_LIST, imports, decls);
  }

  @SuppressWarnings("unchecked")  
//  @Override
  public SourceFile SourceFile(Position pos, PackageNode packageName, List codebases,
      List imports, List decls) {
    SourceFile sf = new CBSourceFile_c(pos, packageName, imports, codebases, decls);
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
