package fabric.ast;

import java.util.List;

import jif.ast.JifNodeFactory;
import jif.ast.LabelNode;
import jif.ast.NewLabel;
import polyglot.ast.*;
import polyglot.util.Position;

/**
 * NodeFactory for fabric extension.
 */
public interface FabricNodeFactory extends JifNodeFactory {
  Atomic Atomic(Position pos, List<Stmt> statements);
  
  AmbNewFabricArray AmbNewFabricArray(Position pos, TypeNode base, Expr location, Object expr, List dims, int addDims);
  
  New New(Position pos, TypeNode type, Expr location, List<Expr> args);
  New New(Position pos, TypeNode type, Expr location, List<Expr> args, ClassBody body);
  New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args);
  New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args, ClassBody body);
  
  FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type);
  FabricArrayInit     FabricArrayInit(Position pos, List<Expr> elements);
  FabricArrayInit     FabricArrayInit(Position pos, Expr label, Expr loc, List<Expr> elements);
  
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, List<Expr> dims);
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims);
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, int addDims, FabricArrayInit init);
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims, FabricArrayInit init);
  
  RetryStmt RetryStmt(Position pos);
  AbortStmt AbortStmt(Position pos);
  
  @SuppressWarnings("unchecked")
  Call Call(Position pos, Receiver target, Id name, Expr remoteWorker, List args);
  
  Worker Worker(Position pos);
  
  RemoteWorkerGetter RemoteWorkerGetter(Position pos, Expr remoteName);
  
  NewLabel NewLabel(Position pos, LabelNode label, Expr location);
}
