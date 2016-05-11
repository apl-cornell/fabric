package fabric.ast;

import java.util.List;

import codebases.ast.CodebaseNodeFactory;

import jif.ast.ConstraintNode;
import jif.ast.JifNodeFactory;
import jif.ast.LabelNode;
import jif.ast.NewLabel;
import jif.ast.PrincipalExpr;
import jif.ast.PrincipalNode;
import jif.types.Assertion;

import polyglot.ast.Block;
import polyglot.ast.Call;
import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.Formal;
import polyglot.ast.Id;
import polyglot.ast.Javadoc;
import polyglot.ast.New;
import polyglot.ast.Receiver;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.util.Position;

/**
 * NodeFactory for fabric extension.
 */
public interface FabricNodeFactory extends JifNodeFactory, CodebaseNodeFactory {
  AccessPolicy AccessPolicy(Position pos, LabelNode ln);

  Atomic Atomic(Position pos, List<Stmt> statements);

  AmbNewFabricArray AmbNewFabricArray(Position pos, TypeNode base,
      Expr location, Object expr, List<Expr> dims, int addDims);

  New New(Position pos, TypeNode type, Expr location, List<Expr> args);

  New New(Position pos, TypeNode type, Expr location, List<Expr> args,
      ClassBody body);

  New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args);

  New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args, ClassBody body);

  FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type);

  FabricArrayInit FabricArrayInit(Position pos, List<Expr> elements);

  FabricArrayInit FabricArrayInit(Position pos, Expr label, Expr loc,
      List<Expr> elements);

  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location,
      List<Expr> dims);

  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location,
      List<Expr> dims, int addDims);

  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location,
      int addDims, FabricArrayInit init);

  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location,
      List<Expr> dims, int addDims, FabricArrayInit init);

  RetryStmt RetryStmt(Position pos);

  AbortStmt AbortStmt(Position pos);

  StageStmt StageStmt(Position pos);

  Call Call(Position pos, Receiver target, Id name, Expr remoteWorker,
      List<Expr> args);

  Worker Worker(Position pos);

  RemoteWorkerGetter RemoteWorkerGetter(Position pos, Expr remoteName);

  NewLabel NewLabel(Position pos, LabelNode label, Expr location);

  FabricMethodDecl FabricMethodDecl(Position pos, Flags flags, TypeNode returnType,
      Id name, LabelNode startLabel, LabelNode beginAccessPolicy,
      List<Formal> arguments, LabelNode endLabel, LabelNode endConfPolicy,
      List<TypeNode> exceptions, List<ConstraintNode<Assertion>> constraints,
      Block body, Javadoc javadoc);

  FabricConstructorDecl FabricConstructorDecl(Position pos, Flags flags, Id name,
      LabelNode startLabel, LabelNode beginAccessPolicy, LabelNode returnLabel,
      List<Formal> arguments, LabelNode endConfPolicy,
      List<TypeNode> exceptions, List<ConstraintNode<Assertion>> constraints,
      Block body, Javadoc javadoc);

  PrincipalExpr PrincipalExpr(Position pos, PrincipalNode principal,
      Expr location);

  FabricFieldDecl FabricFieldDecl(Position pos, Flags flags, TypeNode type,
      LabelNode accessLabel, Id name, Expr init, Javadoc javadoc);

  /**
   * @param expr
   * @param pos
   * @return
   */
  fabric.ast.Store Store(Position pos, Expr expr);
}
