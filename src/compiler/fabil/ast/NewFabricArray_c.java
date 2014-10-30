package fabil.ast;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.NewArray_c;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Term;
import polyglot.ast.Term_c;
import polyglot.ast.TypeNode;
import polyglot.types.ArrayType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

// XXX Should be replaced with extension
@Deprecated
public class NewFabricArray_c extends NewArray_c implements NewFabricArray,
Annotated {

  protected Expr label;
  protected Expr location;
  protected Expr accessPolicy;

  @Deprecated
  public NewFabricArray_c(Position pos, TypeNode baseType, List<Expr> dims,
      int addDims, FabricArrayInit init, Expr label, Expr accessLabel,
      Expr location) {
    this(pos, baseType, dims, addDims, init, label, accessLabel, location, null);
  }

  public NewFabricArray_c(Position pos, TypeNode baseType, List<Expr> dims,
      int addDims, FabricArrayInit init, Expr label, Expr accessLabel,
      Expr location, Ext ext) {
    super(pos, baseType, dims, addDims, init, ext);

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
  public NewFabricArray_c updateLabel(Expr label) {
    return updateLabel(this, label);
  }

  protected <N extends NewFabricArray_c> N updateLabel(N n, Expr label) {
    if (n.label == label) return n;
    n = copyIfNeeded(n);
    n.label = label;
    return n;
  }

  @Override
  public Expr accessPolicy() {
    return accessPolicy;
  }

  @Override
  public NewFabricArray_c accessPolicy(Expr accessPolicy) {
    return accessPolicy(this, accessPolicy);
  }

  protected <N extends NewFabricArray_c> N accessPolicy(N n, Expr accessPolicy) {
    if (n.accessPolicy == accessPolicy) return n;
    n = copyIfNeeded(n);
    n.accessPolicy = accessPolicy;
    return n;
  }

  @Override
  public Expr location() {
    return location;
  }

  @Override
  public NewFabricArray_c location(Expr location) {
    return location(this, location);
  }

  protected <N extends NewFabricArray_c> N location(N n, Expr location) {
    if (n.location == location) return n;
    n = copyIfNeeded(n);
    n.location = location;
    return n;
  }

  /**
   * Reconstructs the expression.
   */
  protected <N extends NewFabricArray_c> N reconstruct(N n, TypeNode baseType,
      List<Expr> dims, FabricArrayInit init, Expr location, Expr label,
      Expr accessPolicy) {
    n = super.reconstruct(n, baseType, dims, init);
    n = location(n, location);
    n = updateLabel(n, label);
    n = accessPolicy(n, accessPolicy);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    TypeNode baseType = visitChild(this.baseType, v);
    List<Expr> dims = visitList(this.dims, v);
    FabricArrayInit init = (FabricArrayInit) visitChild(this.init, v);
    Expr location = visitChild(this.location, v);
    Expr label = visitChild(this.label, v);
    Expr accessPolicy = visitChild(this.accessPolicy, v);
    return reconstruct(this, baseType, dims, init, location, label,
        accessPolicy);
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
          "Non-Fabric objects cannot be stored in Fabric arrays.", position());
    }

    if (location != null) {
      if (!ts.isImplicitCastValid(location.type(), ts.Store())) {
        throw new SemanticException("Array location must be a store.",
            location.position());
      }
    }

    if (label != null) {
      if (!ts.isImplicitCastValid(label.type(), ts.Label())) {
        throw new SemanticException("Invalid array label.", label.position());
      }
    }

    if (accessPolicy != null) {
      if (!ts.isImplicitCastValid(accessPolicy.type(), ts.ConfPolicy())) {
        throw new SemanticException("Invalid access policy.",
            accessPolicy.position());
      }
    }

    return result;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
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
      v.visitCFG(baseType, Term_c.<Expr, Expr, Expr> listChild(dims, null),
          ENTRY);
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

  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory filNf = (FabILNodeFactory) nf;
    return filNf.NewFabricArray(this.position, this.baseType, this.label,
        this.accessPolicy, this.location, this.dims, this.addDims,
        (FabricArrayInit) this.init);
  }

}
