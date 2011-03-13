package fabil.ast;

import java.util.List;

import polyglot.ast.*;
import polyglot.util.Position;

/**
 * NodeFactory for FabIL extension.
 */
public interface FabILNodeFactory extends NodeFactory, CodebaseNodeFactory {
  ArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type);

  Atomic Atomic(Position pos, List<Stmt> statements);

  New New(Position pos, TypeNode type, Expr label, Expr location,
      List<Expr> args);

  New New(Position pos, TypeNode type, Expr label, Expr location,
      List<Expr> args, ClassBody body);

  New New(Position pos, Expr outer, TypeNode objectType, Expr label,
      Expr location, List<Expr> args);

  New New(Position pos, Expr outer, TypeNode objectType, Expr label,
      Expr location, List<Expr> args, ClassBody body);

  /**
   * Creates an AST node representing the creation of a Fabric array.
   */
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr location, List<Expr> dims);

  /**
   * Creates an AST node representing the creation of a Fabric array.
   */
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr location, List<Expr> dims, int addDims);

  /**
   * Creates an AST node representing the creation of a Fabric array.
   */
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr location, int addDims, FabricArrayInit init);

  /**
   * Creates an AST node representing the creation of a Fabric array.
   */
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr location, List<Expr> dims, int addDims, FabricArrayInit init);

  FabricArrayInit FabricArrayInit(Position position, List<Expr> elements);

  FabricArrayInit FabricArrayInit(Position position, Expr label, Expr location,
      List<Expr> elements);

  RetryStmt RetryStmt(Position pos);

  AbortStmt AbortStmt(Position pos);

  Call Call(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> args);

  StoreGetter StoreGetter(Position pos);

  /**
   * @return an AST node representing the provider label for the given type.
   */
  ProviderLabel providerLabel(Position pos, TypeNode tn);

}
