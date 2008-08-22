package fabric.ast;

import java.util.List;


import jif.ast.JifNodeFactory_c;
import polyglot.ast.Expr;
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
  
  public fabric.ast.Atomic Atomic(Position pos, List<Stmt> statements) {
    return new Atomic_c(pos, statements);
  }

  public polyglot.ast.New New(Position pos, TypeNode type, Expr location, List<Expr> args) {
    // TODO Auto-generated method stub
    return null;
  }

  public polyglot.ast.New New(Position pos, TypeNode type, Expr location, List<Expr> args, polyglot.ast.ClassBody body) {
    // TODO Auto-generated method stub
    return null;
  }

  public polyglot.ast.New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args) {
    // TODO Auto-generated method stub
    return null;
  }

  public polyglot.ast.New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args, polyglot.ast.ClassBody body) {
    // TODO Auto-generated method stub
    return null;
  }

  public polyglot.ast.NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims) {
    // TODO Auto-generated method stub
    return null;
  }

  public polyglot.ast.NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims) {
    // TODO Auto-generated method stub
    return null;
  }

  public polyglot.ast.NewArray NewArray(Position pos, TypeNode base, Expr location, int addDims, polyglot.ast.ArrayInit init) {
    // TODO Auto-generated method stub
    return null;
  }

  public polyglot.ast.NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims, polyglot.ast.ArrayInit init) {
    // TODO Auto-generated method stub
    return null;
  }
    // TODO:  Implement factory methods for new AST nodes.
    // TODO:  Override factory methods for overriden AST nodes.
    // TODO:  Override factory methods for AST nodes with new extension nodes.
}
