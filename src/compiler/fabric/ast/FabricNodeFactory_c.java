package fabric.ast;

import polyglot.ast.*;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.types.Qualifier;
import polyglot.util.*;

import java.util.*;

import jif.ast.JifNodeFactory_c;

/**
 * NodeFactory for ../../fabric extension.
 */
public class FabricNodeFactory_c extends JifNodeFactory_c implements FabricNodeFactory {

  public fabric.ast.Atomic Atomic(Position pos, List<Stmt> statements) {
    // TODO Auto-generated method stub
    return null;
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
