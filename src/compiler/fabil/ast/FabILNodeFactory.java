package fabil.ast;

import java.util.List;

import codebases.ast.CodebaseNodeFactory;

import polyglot.ast.ArrayTypeNode;
import polyglot.ast.Call;
import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.ast.NodeFactory;
import polyglot.ast.Receiver;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.util.Position;

/**
 * NodeFactory for FabIL extension.
 */
public interface FabILNodeFactory extends NodeFactory, CodebaseNodeFactory {
  ArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type);

  Atomic Atomic(Position pos, List<Stmt> statements);

  New New(Position pos, TypeNode type, Expr location, List<Expr> args);

  New New(Position pos, TypeNode type, Expr location, List<Expr> args,
      ClassBody body);

  New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args);

  New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args, ClassBody body);

  /**
   * Creates an AST node representing the creation of a Fabric array.
   */
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr accessPolicy, Expr location, List<Expr> dims);

  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr accessPolicy, Expr location, List<Expr> dims, int addDims);

  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr label,
      Expr accessPolicy, Expr location, int addDims, FabricArrayInit init);

  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr updateLabel,
      Expr accessPolicy, Expr location, List<Expr> dims, int addDims,
      FabricArrayInit init);

  FabricArrayInit FabricArrayInit(Position position, List<Expr> elements);

  FabricArrayInit FabricArrayInit(Position position, Expr label,
      Expr accessPolicy, Expr location, List<Expr> elements);

  RetryStmt RetryStmt(Position pos);

  AbortStmt AbortStmt(Position pos);

  StageStmt StageStmt(Position pos);

  Call Call(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> args);

  StoreGetter StoreGetter(Position pos);

  /**
   * @return an AST node representing the provider label for the given type.
   */
  ProviderLabel ProviderLabel(Position pos, TypeNode tn);

}
