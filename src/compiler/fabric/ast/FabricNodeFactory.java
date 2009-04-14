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
  
  New New(Position pos, TypeNode type, Expr location, List<Expr> args);
  New New(Position pos, TypeNode type, Expr location, List<Expr> args, ClassBody body);
  New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args);
  New New(Position pos, Expr outer, TypeNode objectType, Expr location, List<Expr> args, ClassBody body);
  
  FabricArrayTypeNode FabricArrayTypeNode(Position pos, TypeNode type);
  FabricArrayInit     FabricArrayInit(Position pos, List<Expr> elements);
  FabricArrayInit     FabricArrayInit(Position pos, Expr label, Expr loc, List<Expr> elements);
  
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, List<Expr> dims);
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims);
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, int addDims, ArrayInit init);
  NewFabricArray NewFabricArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims, ArrayInit init);
  
  RetryStmt RetryStmt(Position pos);
  AbortStmt AbortStmt(Position pos);
  
  @SuppressWarnings("unchecked")
  Call Call(Position pos, Receiver target, Id name, Expr remoteClient, List args);
  
  Client Client(Position pos);
  
  RemoteClientGetter RemoteClientGetter(Position pos, Expr remoteName);
  
  NewLabel NewLabel(Position pos, LabelNode label, Expr location);
  Node setLocation(Node result, Expr location);
}
