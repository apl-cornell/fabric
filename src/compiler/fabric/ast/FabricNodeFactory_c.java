package fabric.ast;

import java.util.List;

import fabric.extension.LocatedExt_c;


import jif.ast.JifNodeFactory_c;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.New;
import polyglot.ast.NewArray;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

/**
 * NodeFactory for fabric extension.
 */
public class FabricNodeFactory_c extends JifNodeFactory_c implements FabricNodeFactory {

  //////////////////////////////////////////////////////////////////////////////
  // public constructors                                                      //
  //////////////////////////////////////////////////////////////////////////////
  
  public FabricNodeFactory_c() {
    this(new FabricJifExtFactory_c(new FabricFabExtFactory_c()));
  }
  
  public FabricNodeFactory_c(FabricExtFactory extFactory) {
    this(extFactory, new FabricDelFactory_c());
  }

  public FabricNodeFactory_c(FabricExtFactory extFactory, FabricDelFactory delFactory) {
    super(extFactory, delFactory);
  }

  //////////////////////////////////////////////////////////////////////////////
  // factory methods                                                          //
  //////////////////////////////////////////////////////////////////////////////  
  
  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic result = new Atomic_c(pos, statements);
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
}
