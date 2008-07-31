package fabric.ast;

import polyglot.ast.*;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.types.Qualifier;
import polyglot.util.*;

import java.util.*;

import fabric.ast.Atomic;

import jif.ast.JifNodeFactory;

/**
 * NodeFactory for ../../fabric extension.
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
}
