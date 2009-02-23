package fabric.ast;

import java.util.Collections;
import java.util.List;

import fabric.extension.LocatedExt_c;

import jif.ast.JifClassDecl;
import jif.ast.JifNodeFactory_c;
import polyglot.ast.Call;
import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Id;
import polyglot.ast.New;
import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
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

  public NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims) {
    NewArray result = super.NewArray(pos, base, dims);
    result = (NewArray) setLocation(result, location);
    return result;
  }

  public NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims) {
    NewArray result = super.NewArray(pos, base, dims, addDims);
    result = (NewArray) setLocation(result, location);
    return result;
  }

  public NewArray NewArray(Position pos, TypeNode base, Expr location, int addDims, polyglot.ast.ArrayInit init) {
    NewArray result = super.NewArray(pos, base, addDims, init);
    result = (NewArray) setLocation(result, location);
    return result;
  }

  public NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims, polyglot.ast.ArrayInit init) {
    NewArray result = super.NewArray(pos, base, dims, addDims, init);
    result = (NewArray) setLocation(result, location);
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
  private Node setLocation(Node result, Expr location) {
    Ext jifExt = result.ext();
    Ext fabExt = jifExt.ext();
    
    fabExt = ((LocatedExt_c) fabExt).location(location);
    jifExt = jifExt.ext(fabExt);
    result = result.ext(jifExt);
    
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
  
  public Client Client(Position pos) {
    Client n = new Client_c(pos);
    // TODO add the real extension and delegation for Client.
    n = (Client)n.ext(extFactory().extExpr());
    n = (Client)n.del(delFactory().delExpr());
    return n;
  }
  
  public RemoteClientGetter RemoteClientGetter(Position pos, Expr remoteName) {
    RemoteClientGetter n = new RemoteClientGetter_c(pos, remoteName);
    // TODO add the real extension and delegation for RemoteClientGetter.
    n = (RemoteClientGetter)n.ext(extFactory().extExpr());
    n = (RemoteClientGetter)n.del(delFactory().delExpr());
    return n;
  }
}
