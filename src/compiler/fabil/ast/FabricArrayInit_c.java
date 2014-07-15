package fabil.ast;

import java.util.List;

import polyglot.ast.ArrayInit_c;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Term;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class FabricArrayInit_c extends ArrayInit_c implements FabricArrayInit,
Annotated {

  protected Expr location;
  protected Expr label;
  protected Expr accessPolicy;

  @Deprecated
  public FabricArrayInit_c(Position pos, List<Expr> elements, Expr label,
      Expr accessLabel, Expr location) {
    this(pos, elements, label, accessLabel, location, null);
  }

  public FabricArrayInit_c(Position pos, List<Expr> elements, Expr label,
      Expr accessLabel, Expr location, Ext ext) {
    super(pos, elements, ext);

    this.location = location;
    this.label = label;
    this.accessPolicy = accessLabel;
  }

  @Override
  public FabricArrayInit elements(List<Expr> elements) {
    return (FabricArrayInit) super.elements(elements);
  }

  @Override
  public Expr location() {
    return location;
  }

  @Override
  public FabricArrayInit_c location(Expr location) {
    return location(this, location);
  }

  protected <N extends FabricArrayInit_c> N location(N n, Expr location) {
    if (n.location == location) return n;
    n = copyIfNeeded(n);
    n.location = location;
    return n;
  }

  @Override
  public Expr updateLabel() {
    return label;
  }

  @Override
  public FabricArrayInit_c updateLabel(Expr label) {
    return updateLabel(this, label);
  }

  protected <N extends FabricArrayInit_c> N updateLabel(N n, Expr label) {
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
  public FabricArrayInit_c accessPolicy(Expr accessLabel) {
    FabricArrayInit_c n = (FabricArrayInit_c) copy();
    n.accessPolicy = accessLabel;
    return n;
  }

  protected <N extends FabricArrayInit_c> N accessPolicy(N n, Expr accessPolicy) {
    if (n.accessPolicy == accessPolicy) return n;
    n = copyIfNeeded(n);
    n.accessPolicy = accessPolicy;
    return n;
  }

  /**
   * Reconstructs the initializer.
   */
  protected <N extends FabricArrayInit_c> N reconstruct(N n,
      List<Expr> elements, Expr location, Expr label, Expr accessPolicy) {
    n = super.reconstruct(n, elements);
    n = location(n, location);
    n = updateLabel(n, label);
    n = accessPolicy(n, accessPolicy);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    List<Expr> elements = visitList(this.elements, v);
    Expr location = visitChild(this.location, v);
    Expr label = visitChild(this.label, v);
    Expr accessLabel = visitChild(this.accessPolicy, v);
    return reconstruct(this, elements, location, label, accessLabel);
  }

  @Override
  public FabricArrayInit_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    FabricArrayInit_c result = (FabricArrayInit_c) super.typeCheck(tc);

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
  protected Type arrayOf(TypeSystem ts, Type baseType) {
    return ((FabILTypeSystem) ts).fabricArrayOf(baseType);
  }

  @Override
  public Type childExpectedType(Expr child, AscriptionVisitor av) {
    FabILTypeSystem ts = (FabILTypeSystem) av.typeSystem();

    if (child == location) return ts.Store();
    if (child == label) return ts.Label();
    if (child == accessPolicy) return ts.ConfPolicy();

    Type t = av.toType();
    Type baseType = t.toArray().base();
    if (ts.isJavaInlineable(baseType)) return ts.FObject();

    return super.childExpectedType(child, av);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    Term last = null;

    if (accessPolicy != null) {
      v.visitCFGList(elements, accessPolicy, ENTRY);
      last = accessPolicy;
    }

    if (label != null) {
      if (last == null) {
        v.visitCFGList(elements, label, ENTRY);
      } else {
        v.visitCFG(last, label, ENTRY);
      }
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

  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory filNf = (FabILNodeFactory) nf;
    return filNf.FabricArrayInit(this.position, this.label, this.accessPolicy,
        this.location, this.elements);
  }

}
