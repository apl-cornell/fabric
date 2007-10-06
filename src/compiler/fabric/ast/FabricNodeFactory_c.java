package fabric.ast;

import java.util.Collections;
import java.util.List;

import polyglot.ast.*;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;

import fabric.extension.FabricExtFactory;
import fabric.extension.FabricExtFactory_c;
import fabric.extension.LocatedExt_c;

/**
 * NodeFactory for fabric extension.
 */
public class FabricNodeFactory_c extends NodeFactory_c implements
    FabricNodeFactory {

  public FabricNodeFactory_c() {
    super(new FabricExtFactory_c());
  }
  
  @Override
  public FabricExtFactory extFactory() {
    return (FabricExtFactory) super.extFactory();
  }

  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic atomic = new Atomic_c(pos, statements);
    atomic = (Atomic) atomic.ext(extFactory().extAtomic());
    atomic = (Atomic) atomic.del(delFactory().delBlock());
    return atomic;
  }

  @SuppressWarnings("unchecked")
  public New New(Position pos, Expr outer, TypeNode objectType,
      Expr location, List<Expr> args, ClassBody body) {
    New n =
        new New_c(pos, outer, objectType, CollectionUtil
            .nonNullList(args), body);
    n = (New) n.ext(extFactory().extNew());
    n = (New) n.del(delFactory().delNew());
    n = (New) ((LocatedExt_c) n.ext()).location(location);
    
    return n;
  }

  @SuppressWarnings("unchecked")
  public NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims, ArrayInit init) {
    NewArray result =
      new NewArray_c(pos,base,
                     CollectionUtil.nonNullList(dims),addDims,init);
    result = (NewArray) result.ext(extFactory().extNewArray());
    result = (NewArray) result.del(delFactory().delNewArray());
    result = (NewArray) ((LocatedExt_c) result.ext()).location(location);
    return result;
  }
  
  // Constructors with fewer arguments ////////////////////////////////////////
  
  @SuppressWarnings("unchecked")
  @Override
  public New New(Position pos, Expr outer, TypeNode objectType, List args,
      ClassBody body) {
    return New(pos, outer, objectType, null, args, body);
  }
  
  public New New(Position pos, TypeNode objectType, Expr location, List<Expr> args) {
    return New(pos, null, objectType, location, args);
  }

  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args) {
    return New(pos, outer, objectType, location, args, null);
  }

  public New New(Position pos, TypeNode type, Expr location, List<Expr> args, polyglot.ast.ClassBody body) {
    return New(pos, null, type, location, args, body);
  }

  @Override
  @SuppressWarnings("unchecked")
  public final NewArray NewArray(Position pos, TypeNode base, List dims, int addDims, ArrayInit init) {
    return NewArray(pos, base, null, dims, addDims, init);
  }
  
  public final NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims) {
    return NewArray(pos, base, location, dims, 0, null);
  }

  public final NewArray NewArray(Position pos, TypeNode base, Expr location, List<Expr> dims, int addDims) {
    return NewArray(pos, base, location, dims, addDims, null);
  }

  public final NewArray NewArray(Position pos, TypeNode base, Expr location, int addDims, ArrayInit init) {
    List<Expr> emptyList = Collections.emptyList();
    return NewArray(pos, base, location, emptyList, addDims, init);
  }

}
