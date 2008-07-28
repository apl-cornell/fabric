package fabil.ast;

import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.New;
import polyglot.ast.NewArray;
import polyglot.ast.NodeFactory;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

/**
 * NodeFactory for FabIL extension.
 */
public interface FabILNodeFactory extends NodeFactory {
  Atomic Atomic(Position pos, List<Stmt> statements);
  
  New New(Position pos, TypeNode type, Expr location, List<Expr> args);
  New New(Position pos, TypeNode type, Expr location, List<Expr> args, ClassBody body);
  New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args);
  New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args, ClassBody body);
  
  NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims);
  NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims);
  NewArray NewArray(Position pos, TypeNode base, Expr location, int addDims, ArrayInit init);
  NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims, ArrayInit init);
}
