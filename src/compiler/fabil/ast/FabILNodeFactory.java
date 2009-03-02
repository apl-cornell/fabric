package fabil.ast;

import java.util.List;

import polyglot.ast.*;
import polyglot.util.Position;

/**
 * NodeFactory for FabIL extension.
 */
public interface FabILNodeFactory extends NodeFactory {
  Atomic Atomic(Position pos, List<Stmt> statements);

  New New(Position pos, TypeNode type, Expr label, Expr location,
      List<Expr> args);

  New New(Position pos, TypeNode type, Expr label, Expr location,
      List<Expr> args, ClassBody body);

  New New(Position pos, Expr outer, TypeNode objectType, Expr label,
      Expr location, List<Expr> args);

  New New(Position pos, Expr outer, TypeNode objectType, Expr label,
      Expr location, List<Expr> args, ClassBody body);

  NewArray NewArray(Position pos, TypeNode base, Expr label, Expr location,
      List<Expr> dims);

  NewArray NewArray(Position pos, TypeNode base, Expr label, Expr location,
      List<Expr> dims, int addDims);

  NewArray NewArray(Position pos, TypeNode base, Expr label, Expr location,
      int addDims, ArrayInit init);

  NewArray NewArray(Position pos, TypeNode base, Expr label, Expr location,
      List<Expr> dims, int addDims, ArrayInit init);

  ArrayInit ArrayInit(Position position, Expr label, Expr location,
      List<Expr> elements);

  RetryStmt RetryStmt(Position pos);

  AbortStmt AbortStmt(Position pos);

  @SuppressWarnings("unchecked")
  Call Call(Position pos, Receiver target, Id name, Expr remoteClient, List args);
}
