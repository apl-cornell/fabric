package fabric.ast;

import java.util.Collections;
import java.util.List;

import polyglot.ast.*;
import polyglot.ast.Assign.Operator;
import polyglot.types.Flags;
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

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.NodeFactory_c#ArrayAccessAssign(polyglot.util.Position,
   *      polyglot.ast.ArrayAccess, polyglot.ast.Assign.Operator,
   *      polyglot.ast.Expr)
   */
  @Override
  public ArrayAccessAssign ArrayAccessAssign(Position pos, ArrayAccess left,
      Operator op, Expr right) {
    ArrayAccessAssign aaa = new ArrayAccessAssign_c(pos, left, op, right);
    aaa = (ArrayAccessAssign) aaa.ext(extFactory().extArrayAccessAssign());
    aaa = (ArrayAccessAssign) aaa.del(delFactory().delArrayAccessAssign());
    return aaa;
  }

  public Atomic Atomic(Position pos, List<Stmt> statements) {
    Atomic atomic = new Atomic_c(pos, statements);
    atomic = (Atomic) atomic.ext(extFactory().extAtomic());
    atomic = (Atomic) atomic.del(delFactory().delBlock());
    return atomic;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.NodeFactory_c#Cast(polyglot.util.Position,
   *      polyglot.ast.TypeNode, polyglot.ast.Expr)
   */
  @Override
  public Cast Cast(Position pos, TypeNode type, Expr expr) {
    Cast cast = new Cast_c(pos, type, expr);
    cast = (Cast) cast.ext(extFactory().extCast());
    cast = (Cast) cast.del(delFactory().delCast());
    return cast;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.NodeFactory_c#ClassDecl(polyglot.util.Position,
   *      polyglot.types.Flags, polyglot.ast.Id, polyglot.ast.TypeNode,
   *      java.util.List, polyglot.ast.ClassBody)
   */
  @SuppressWarnings("unchecked")
  @Override
  public ClassDecl ClassDecl(Position pos, Flags flags, Id name,
      TypeNode superClass, List interfaces, ClassBody body) {
    ClassDecl n =
        new ClassDecl_c(pos, flags, name, superClass, CollectionUtil
            .nonNullList(interfaces), body);
    n = (ClassDecl) n.ext(extFactory().extClassDecl());
    n = (ClassDecl) n.del(delFactory().delClassDecl());
    return n;
  }

  @SuppressWarnings("unchecked")
  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args, ClassBody body) {
    New n =
        new New_c(pos, outer, objectType, CollectionUtil.nonNullList(args),
            body);
    n = (New) n.ext(extFactory().extNew());
    n = (New) n.del(delFactory().delNew());
    n = (New) ((LocatedExt_c) n.ext()).location(location);

    return n;
  }

  @SuppressWarnings("unchecked")
  public NewArray NewArray(Position pos, TypeNode base, Expr location,
      List<Expr> dims, int addDims, ArrayInit init) {
    NewArray result =
        new NewArray_c(pos, base, CollectionUtil.nonNullList(dims), addDims,
            init);
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

  public New New(Position pos, TypeNode objectType, Expr location,
      List<Expr> args) {
    return New(pos, null, objectType, location, args);
  }

  public New New(Position pos, Expr outer, TypeNode objectType, Expr location,
      List<Expr> args) {
    return New(pos, outer, objectType, location, args, null);
  }

  public New New(Position pos, TypeNode type, Expr location, List<Expr> args,
      polyglot.ast.ClassBody body) {
    return New(pos, null, type, location, args, body);
  }

  @Override
  @SuppressWarnings("unchecked")
  public final NewArray NewArray(Position pos, TypeNode base, List dims,
      int addDims, ArrayInit init) {
    return NewArray(pos, base, null, dims, addDims, init);
  }

  public final NewArray NewArray(Position pos, TypeNode base, Expr location,
      List<Expr> dims) {
    return NewArray(pos, base, location, dims, 0, null);
  }

  public final NewArray NewArray(Position pos, TypeNode base, Expr location,
      List<Expr> dims, int addDims) {
    return NewArray(pos, base, location, dims, addDims, null);
  }

  public final NewArray NewArray(Position pos, TypeNode base, Expr location,
      int addDims, ArrayInit init) {
    List<Expr> emptyList = Collections.emptyList();
    return NewArray(pos, base, location, emptyList, addDims, init);
  }

}
