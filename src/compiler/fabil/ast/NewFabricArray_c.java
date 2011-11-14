package fabil.ast;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.NewArray_c;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Term;
import polyglot.ast.TypeNode;
import polyglot.types.ArrayType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

public class NewFabricArray_c extends NewArray_c implements NewFabricArray,
    Annotated {

  protected Expr label;
  protected Expr location;
  protected Expr accessPolicy;

  public NewFabricArray_c(Position pos, TypeNode baseType, List<Expr> dims,
      int addDims, FabricArrayInit init, Expr label, Expr accessLabel, Expr location) {
    super(pos, baseType, dims, addDims, init);

    this.location = location;
    this.label = label;
    this.accessPolicy = accessLabel;
  }

  @Override
  public FabricArrayInit init() {
    return (FabricArrayInit) super.init();
  }

  @Override
  public NewFabricArray_c init(polyglot.ast.ArrayInit init) {
    return (NewFabricArray_c) super.init(init);
  }

  @Override
  public Expr updateLabel() {
    return label;
  }

  @Override
  public Expr accessLabel() {
    return accessPolicy;
  }

  @Override
  public NewFabricArray_c updateLabel(Expr label) {
    NewFabricArray_c n = (NewFabricArray_c) copy();
    n.label = label;
    return n;
  }

  @Override
  public NewFabricArray_c accessPolicy(Expr accessPolicy) {
    NewFabricArray_c n = (NewFabricArray_c) copy();
    n.accessPolicy = accessPolicy;
    return n;
  }

  @Override
  public Expr location() {
    return location;
  }

  @Override
  public NewFabricArray_c location(Expr location) {
    NewFabricArray_c n = (NewFabricArray_c) copy();
    n.location = location;
    return n;
  }

  /**
   * Reconstructs the expression.
   */
  protected NewFabricArray_c reconstruct(TypeNode baseType, List<Expr> dims,
      FabricArrayInit init, Expr location, Expr label, Expr accessLabel) {
    if (baseType != this.baseType || !CollectionUtil.equals(dims, this.dims)
        || init != this.init || location != this.location
        || label != this.label || accessLabel != this.accessPolicy) {
      NewFabricArray_c n = (NewFabricArray_c) copy();
      n.baseType = baseType;
      n.dims = TypedList.copyAndCheck(dims, Expr.class, true);
      n.init = init;
      n.location = location;
      n.label = label;
      n.accessPolicy = accessLabel;
      return n;
    }

    return this;
  }

  @SuppressWarnings("unchecked")
  @Override
  public Node visitChildren(NodeVisitor v) {
    TypeNode baseType = (TypeNode) visitChild(this.baseType, v);
    List<Expr> dims = visitList(this.dims, v);
    FabricArrayInit init = (FabricArrayInit) visitChild(this.init, v);
    Expr location = (Expr) visitChild(this.location, v);
    Expr label = (Expr) visitChild(this.label, v);
    Expr accessLabel = (Expr) visitChild(this.accessPolicy, v);    
    return reconstruct(baseType, dims, init, location, label, accessLabel);
  }

  @Override
  protected ArrayType arrayOf(TypeSystem ts, Type baseType, int dims) {
    return ((FabILTypeSystem) ts).fabricArrayOf(baseType, dims);
  }

  @Override
  public NewFabricArray_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    NewFabricArray_c result = (NewFabricArray_c) super.typeCheck(tc);

    if (!ts.isFabricType(result.baseType)) {
      throw new SemanticException(
          "Non-Fabric objects cannot be stored in Fabric arrays.", node()
              .position());
    }

    if (location != null) {
      if (!ts.isImplicitCastValid(location.type(), ts.Store())) {
        throw new SemanticException("Array location must be a store.", location
            .position());
      }
    }

    if (label != null) {
      if (!ts.isImplicitCastValid(label.type(), ts.Label())) {
        throw new SemanticException("Invalid array label.", label.position());
      }
    }

    if (accessPolicy != null) {
      if (!ts.isImplicitCastValid(accessPolicy.type(), ts.Label())) {
        throw new SemanticException("Invalid access policy.", accessPolicy.position());
      }
    }

    return result;
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List acceptCFG(CFGBuilder v, List succs) {
    if (init != null) {
      v.visitCFG(baseType, listChild(dims, init), ENTRY);
      v.visitCFGList(dims, init, ENTRY);

      Term last = init;
      if (accessPolicy != null) {
        v.visitCFG(last, accessPolicy, ENTRY);
        last = accessPolicy;
      }
      
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
      
      if (accessPolicy != null) {
        v.visitCFGList(dims, accessPolicy, ENTRY);
        last = accessPolicy;
      }

      if (label != null) {
        if (last == null) {
          v.visitCFGList(dims, label, ENTRY);
        } else {
          v.visitCFG(last, label, ENTRY);
        }
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
    return filNf.NewFabricArray(this.position, this.baseType, this.label,
        this.accessPolicy, this.location, this.dims, this.addDims,
        (FabricArrayInit) this.init);
  }

}
