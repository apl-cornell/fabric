package fabil.ast;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Term;
import polyglot.types.SemanticException;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

public class ArrayInit_c extends polyglot.ast.ArrayInit_c implements ArrayInit,
    Annotated {

  protected Expr location;
  protected Expr label;

  public ArrayInit_c(Position pos, List<Expr> elements, Expr label,
      Expr location) {
    super(pos, elements);

    this.location = location;
    this.label = label;
  }

  @SuppressWarnings("unchecked")
  @Override
  public ArrayInit elements(List elements) {
    return (ArrayInit) super.elements(elements);
  }

  public Expr location() {
    return location;
  }

  public ArrayInit_c location(Expr location) {
    ArrayInit_c n = (ArrayInit_c) copy();
    n.location = location;
    return n;
  }

  public Expr label() {
    return label;
  }

  public ArrayInit_c label(Expr label) {
    ArrayInit_c n = (ArrayInit_c) copy();
    n.label = label;
    return n;
  }

  /**
   * Reconstructs the initializer.
   */
  protected ArrayInit_c reconstruct(List<Expr> elements, Expr location,
      Expr label) {
    if (!CollectionUtil.equals(elements, this.elements)
        || location != this.location || label != this.label) {
      ArrayInit_c n = (ArrayInit_c) copy();
      n.elements = TypedList.copyAndCheck(elements, Expr.class, true);
      n.location = location;
      n.label = label;
      return n;
    }

    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node visitChildren(NodeVisitor v) {
    List<Expr> elements = visitList(this.elements, v);
    Expr location = (Expr) visitChild(this.location, v);
    Expr label = (Expr) visitChild(this.label, v);
    return reconstruct(elements, location, label);
  }

  @Override
  public ArrayInit_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    ArrayInit_c result = (ArrayInit_c) super.typeCheck(tc);

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

  @Override
  @SuppressWarnings("unchecked")
  public List acceptCFG(CFGBuilder v, List succs) {
    Term last = null;

    if (label != null) {
      v.visitCFGList(elements, label, ENTRY);
      last = label;
    }

    if (location != null) {
      if (last == null) {
        v.visitCFGList(elements, location, ENTRY);
      } else {
        v.visitCFG(last, location, ENTRY);
      }
      last = location;
    }

    if (last == null) {
      v.visitCFGList(elements, this, EXIT);
    } else {
      v.visitCFG(last, this, EXIT);
    }

    return succs;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory filNf = (FabILNodeFactory) nf;
    return filNf.ArrayInit(this.position, this.label, this.location,
        this.elements);
  }

}
