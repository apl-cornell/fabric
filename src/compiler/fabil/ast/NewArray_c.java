package fabil.ast;

import java.util.List;

import fabil.types.FabILTypeSystem;

import polyglot.ast.*;
import polyglot.types.SemanticException;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;

public class NewArray_c extends polyglot.ast.NewArray_c implements NewArray,
    Annotated {

  protected Expr label;
  protected Expr location;

  public NewArray_c(Position pos, TypeNode baseType, List<Expr> dims,
      int addDims, ArrayInit init, Expr label, Expr location) {
    super(pos, baseType, dims, addDims, init);

    this.location = location;
    this.label = label;
  }

  @Override
  public ArrayInit init() {
    return (ArrayInit) super.init();
  }

  @Override
  public NewArray_c init(polyglot.ast.ArrayInit init) {
    return (NewArray_c) super.init(init);
  }

  public Expr label() {
    return label;
  }

  public NewArray_c label(Expr label) {
    NewArray_c n = (NewArray_c) copy();
    n.label = label;
    return n;
  }

  public Expr location() {
    return location;
  }

  public NewArray_c location(Expr location) {
    NewArray_c n = (NewArray_c) copy();
    n.location = location;
    return n;
  }

  /**
   * Reconstructs the expression.
   */
  protected NewArray_c reconstruct(TypeNode baseType, List<Expr> dims,
      ArrayInit init, Expr location, Expr label) {
    if (baseType != this.baseType || !CollectionUtil.equals(dims, this.dims)
        || init != this.init || location != this.location
        || label != this.label) {
      NewArray_c n = (NewArray_c) copy();
      n.baseType = baseType;
      n.dims = TypedList.copyAndCheck(dims, Expr.class, true);
      n.init = init;
      n.location = location;
      n.label = label;
      return n;
    }

    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node visitChildren(NodeVisitor v) {
    TypeNode baseType = (TypeNode) visitChild(this.baseType, v);
    List<Expr> dims = visitList(this.dims, v);
    ArrayInit init = (ArrayInit) visitChild(this.init, v);
    Expr location = (Expr) visitChild(this.location, v);
    Expr label = (Expr) visitChild(this.label, v);
    return reconstruct(baseType, dims, init, location, label);
  }

  @Override
  public NewArray_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    NewArray_c result = (NewArray_c) super.typeCheck(tc);

    if (location != null) {
      if (!ts.isImplicitCastValid(location.type(), ts.Core())) {
        throw new SemanticException("Array location must be a core.", location
            .position());
      }
    }

    if (label != null) {
      if (!ts.isImplicitCastValid(label.type(), ts.Label())) {
        throw new SemanticException("Invalid array label.", label.position());
      }
    }

    return result;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    if (init != null) {
      v.visitCFG(baseType, listChild(dims, init), ENTRY);
      v.visitCFGList(dims, init, ENTRY);

      Term last = init;
      if (label != null) {
        v.visitCFG(last, label, ENTRY);
        last = label;
      }

      if (location != null) {
        v.visitCFG(last, location, ENTRY);
        last = location;
      }

      v.visitCFG(last, this, EXIT);
    } else {
      v.visitCFG(baseType, listChild(dims, null), ENTRY);
      Term last = null;

      if (label != null) {
        v.visitCFGList(dims, label, ENTRY);
        last = label;
      }

      if (location != null) {
        if (last == null) {
          v.visitCFGList(dims, location, ENTRY);
        } else {
          v.visitCFG(last, location, ENTRY);
        }
        last = location;
      }

      if (last == null)
        v.visitCFGList(dims, this, EXIT);
      else v.visitCFG(last, this, EXIT);
    }

    return succs;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory filNf = (FabILNodeFactory) nf;
    return filNf.NewArray(this.position, this.baseType, this.label,
        this.location, this.dims, this.addDims, (ArrayInit) this.init);
  }

}
