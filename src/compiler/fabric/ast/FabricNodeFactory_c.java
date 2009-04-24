package fabric.ast;

import java.util.Collections;
import java.util.List;

import fabric.extension.FabricExt;
import fabric.extension.LocatedExt_c;

import jif.ast.*;
import polyglot.ast.*;
import polyglot.types.Flags;
import polyglot.util.Position;

/**
 * NodeFactory for fabric extension.
 */
public class FabricNodeFactory_c extends JifNodeFactory_c implements FabricNodeFactory {

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////
  
  public FabricNodeFactory_c() {
    this(new FabricFabExtFactory_c(new FabricJifExtFactory_c()));
  }
  
  public FabricNodeFactory_c(FabricExtFactory extFactory) {
    this(extFactory, new FabricDelFactory_c());
  }

  public FabricNodeFactory_c(FabricExtFactory extFactory, FabricDelFactory delFactory) {
    super(extFactory, delFactory);
  }

  //////////////////////////////////////////////////////////////////////////////
  // new factory methods                                                      //
  //////////////////////////////////////////////////////////////////////////////  
  
  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic result = new Atomic_c(pos, statements);
    // note: this is correct.  fabricExtFactory() is a factory that returns Jif
    // extension objects with their ext() pointers referring to Fabric Ext
    // objects, which is as it should be.
    result = (Atomic) result.ext(fabricExtFactory().extAtomic());
    result = (Atomic) result.del(fabricDelFactory().delAtomic());
    return result;
  }

  public RetryStmt RetryStmt(Position pos) {
    RetryStmt s = new RetryStmt_c(pos);
    s = (RetryStmt)s.ext(fabricExtFactory().extRetry());
    s = (RetryStmt)s.del(fabricDelFactory().delStmt());
    return s;
  }
  
  public AbortStmt AbortStmt(Position pos) {
    AbortStmt s = new AbortStmt_c(pos);
    s = (AbortStmt)s.ext(fabricExtFactory().extAbort());
    s = (AbortStmt)s.del(fabricDelFactory().delStmt());
    return s;
  }
  
  public Client Client(Position pos) {
    Client n = new Client_c(pos, this);
    n = (Client)n.ext(fabricExtFactory().extClient());
    n = (Client)n.del(fabricDelFactory().delClient());
    return n;
  }
  
  public RemoteClientGetter RemoteClientGetter(Position pos, Expr remoteName) {
    RemoteClientGetter n = new RemoteClientGetter_c(pos, remoteName);
    // TODO add the real extension and delegation for RemoteClientGetter.
    n = (RemoteClientGetter)n.ext(fabricExtFactory().extRemoteClientGetter());
    n = (RemoteClientGetter)n.del(fabricDelFactory().delRemoteClientGetter());
    return n;
  }
  
  public NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims, polyglot.ast.ArrayInit init) {
    NewFabricArray result = new NewFabricArray_c(pos, base, dims, addDims, init);
    result = (NewFabricArray) result.ext(fabricExtFactory().extNewFabricArray());
    result = (NewFabricArray) result.del(fabricDelFactory().delNewFabricArray());
    return result;
  }

  public FabricArrayInit FabricArrayInit(Position position, Expr label, Expr location, List<Expr> elements) {
    FabricArrayInit result = new FabricArrayInit_c(position, elements, label, location);
    result = (FabricArrayInit) result.ext(fabricExtFactory().extFabricArrayInit());
    result = (FabricArrayInit) result.del(fabricDelFactory().delFabricArrayInit());
    return result;
  }

  public FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type) {
    FabricArrayTypeNode result = new FabricArrayTypeNode_c(pos, type);
    result = (FabricArrayTypeNode) result.ext(fabricExtFactory().extFabricArrayTypeNode());
    result = (FabricArrayTypeNode) result.del(fabricDelFactory().delFabricArrayTypeNode());
    return result;
  }

  //////////////////////////////////////////////////////////////////////////////
  // overloaded factory methods                                               //
  //////////////////////////////////////////////////////////////////////////////  

  public New New(Position pos, TypeNode type, Expr location, List<Expr> args) {
    New result = super.New(pos, type, args);
    result = (New) setLocation(result, location);
    return result;
  }

  public New New(Position pos, TypeNode type, Expr location, List<Expr> args, polyglot.ast.ClassBody body) {
    New result = super.New(pos, type, args, body);
    result = (New) setLocation(result, location);
    return result;
  }

  public New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args) {
    New result = super.New(pos, outer, objectType, args);
    result = (New) setLocation(result, location);
    return result;
  }

  public New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args, polyglot.ast.ClassBody body) {
    New result = super.New(pos, outer, objectType, args, body);
    result = (New) setLocation(result, location);
    return result;
  }

  public FabricArrayInit FabricArrayInit(Position position, List<Expr> elements) {
    return FabricArrayInit(position, null, null, elements);
  }
  
  public final NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, List<Expr> dims) {
    return NewFabricArray(pos, base, location, dims, 0, null);
  }

  public final NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims) {
    return NewFabricArray(pos, base, location, dims, addDims, null);
  }

  public final NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, int addDims, polyglot.ast.ArrayInit init) {
    List<Expr> empty = Collections.emptyList();
    return NewFabricArray(pos, base, location, empty, addDims, init);
  }

  public NewLabel NewLabel(Position pos, LabelNode label, Expr location) {
    NewLabel result = super.NewLabel(pos, label);
    result = (NewLabel)setLocation(result, location);
    return result;
  }
  
//////////////////////////////////////////////////////////////////////////////
  // overridden factory methods                                               //
  //////////////////////////////////////////////////////////////////////////////  

  @SuppressWarnings("unchecked")
  @Override
  public ClassDecl ClassDecl(Position pos, Flags flags, Id name, TypeNode superClass, List interfaces, ClassBody body) {
    ClassDecl n = new ClassDecl_c(pos, flags, name,
                                  Collections.EMPTY_LIST, superClass, interfaces, 
                                  Collections.EMPTY_LIST, body);
    n = (ClassDecl)n.ext(extFactory().extClassDecl());
    n = (ClassDecl)n.del(delFactory().delClassDecl());
    return n;
  }

  @SuppressWarnings("unchecked")
  @Override
  public JifClassDecl JifClassDecl(Position pos, Flags flags, Id name,
                                   List params, 
                                   TypeNode superClass, List interfaces,
                                   List authority, ClassBody body) {
    JifClassDecl n = new ClassDecl_c(pos, flags, name, params, superClass,
                                     interfaces, authority, body);
    n = (JifClassDecl)n.ext(extFactory().extClassDecl());
    n = (JifClassDecl)n.del(delFactory().delClassDecl());
    return n;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Call Call(Position pos, Receiver target, Id name, List args) {
    return Call(pos, target, name, null, args);
  }
  
  @SuppressWarnings("unchecked")
  public Call Call(Position pos, Receiver target, Id name, Expr remoteClient, List args) {
    Call n = new FabricCall_c(pos, target, name, remoteClient, args);
    n = (Call)n.ext(extFactory().extCall());
    n = (Call)n.del(delFactory().delCall());
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
  
  //////////////////////////////////////////////////////////////////////////////
  // private helper methods                                                   //
  //////////////////////////////////////////////////////////////////////////////
  
  private FabricDelFactory fabricDelFactory() {
    return (FabricDelFactory) this.delFactory();
  }
  
  private FabricExtFactory fabricExtFactory() {
    return (FabricExtFactory) this.extFactory();
  }

  /** Update the provided node with a given location.
   * 
   * @param result   a Node having a LocatedExt as a fabric extension
   * @param location the expression representing the location
   * @return         a copy of <code>result</code> with the fabric extension
   *                 updated with the location
   */
  public Node setLocation(Node result, Expr location) {
    FabricExt fabExt = FabricUtil.fabricExt(result);
//    Ext jifExt = result.ext();
//    Ext fabExt = jifExt.ext();
    
    fabExt = ((LocatedExt_c) fabExt).location(location);
    
    result = FabricUtil.updateFabricExt(result, fabExt);
//    jifExt = jifExt.ext(fabExt);
//    result = result.ext(jifExt);
    
    return result;
  }
}
