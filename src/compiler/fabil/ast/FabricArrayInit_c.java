package fabil.ast;

import java.util.List;

import polyglot.ast.ArrayInit_c;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Term;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.AscriptionVisitor;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

public class FabricArrayInit_c extends ArrayInit_c implements FabricArrayInit,
    Annotated {

  protected Expr location;
  protected Expr label;
  protected Expr accessLabel;

  public FabricArrayInit_c(Position pos, List<Expr> elements, Expr label,
      Expr accessLabel, Expr location) {
    super(pos, elements);

    this.location = location;
    this.label = label;
    this.accessLabel = accessLabel;
  }

  @Override
  public FabricArrayInit elements(@SuppressWarnings("rawtypes") List elements) {
    return (FabricArrayInit) super.elements(elements);
  }

  @Override
  public Expr location() {
    return location;
  }

  @Override
  public FabricArrayInit_c location(Expr location) {
    FabricArrayInit_c n = (FabricArrayInit_c) copy();
    n.location = location;
    return n;
  }

  @Override
  public Expr label() {
    return label;
  }

  @Override
  public FabricArrayInit_c label(Expr label) {
    FabricArrayInit_c n = (FabricArrayInit_c) copy();
    n.label = label;
    return n;
  }
  
  @Override
  public Expr accessLabel() {
    return accessLabel;
  }

  @Override
  public FabricArrayInit_c accessLabel(Expr accessLabel) {
    FabricArrayInit_c n = (FabricArrayInit_c) copy();
    n.accessLabel = accessLabel;
    return n;
  }

  /**
   * Reconstructs the initializer.
   */
  protected FabricArrayInit_c reconstruct(List<Expr> elements, Expr location,
      Expr label, Expr accessLabel) {
    if (!CollectionUtil.equals(elements, this.elements)
        || location != this.location || label != this.label
        || accessLabel != this.accessLabel) {
      FabricArrayInit_c n = (FabricArrayInit_c) copy();
      n.elements = TypedList.copyAndCheck(elements, Expr.class, true);
      n.location = location;
      n.label = label;
      n.accessLabel = accessLabel;
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
    Expr accessLabel = (Expr) visitChild(this.accessLabel, v);
    return reconstruct(elements, location, label, accessLabel);
  }

  @Override
  public FabricArrayInit_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    FabricArrayInit_c result = (FabricArrayInit_c) super.typeCheck(tc);

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

    if (accessLabel != null) {
      if (!ts.isImplicitCastValid(accessLabel.type(), ts.Label())) {
        throw new SemanticException("Invalid access policy.", accessLabel.position());
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
    if (child == accessLabel) return ts.Label();

    Type t = av.toType();
    Type baseType = t.toArray().base();
    if (ts.isJavaInlineable(baseType)) return ts.FObject();
    
    return super.childExpectedType(child, av);
  }

  @Override
  @SuppressWarnings("rawtypes")
  public List acceptCFG(CFGBuilder v, List succs) {
    Term last = null;
    
    if (accessLabel != null) {
      v.visitCFGList(elements, accessLabel, ENTRY);
      last = accessLabel;
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

  @SuppressWarnings("unchecked")
  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory filNf = (FabILNodeFactory) nf;
    return filNf.FabricArrayInit(this.position, this.label, this.accessLabel,
        this.location, this.elements);
  }

}
