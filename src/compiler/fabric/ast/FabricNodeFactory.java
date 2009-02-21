package fabric.ast;

import java.util.List;

import jif.ast.JifNodeFactory;
import polyglot.ast.*;
import polyglot.util.Position;

/**
 * NodeFactory for fabric extension.
 */
public interface FabricNodeFactory extends JifNodeFactory {
  Atomic Atomic(Position pos, List<Stmt> statements);
  
  New New(Position pos, TypeNode type, Expr location, List<Expr> args);
  New New(Position pos, TypeNode type, Expr location, List<Expr> args, ClassBody body);
  New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args);
  New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args, ClassBody body);
  
  NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims);
  NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims);
  NewArray NewArray(Position pos, TypeNode base, Expr location, int addDims, ArrayInit init);
  NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims, ArrayInit init);
  
  RetryStmt RetryStmt(Position pos);
  AbortStmt AbortStmt(Position pos);
  
  @SuppressWarnings("unchecked")
  Call Call(Position pos, Receiver target, Id name, Expr remoteClient, List args);
}
