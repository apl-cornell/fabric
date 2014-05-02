package fabil.ast;

import java.util.List;

import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Term;
import polyglot.ast.Term_c;
import polyglot.ast.TypeNode;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeChecker;
import fabil.types.FabILTypeSystem;

//XXX Should be replaced with extension
@Deprecated
public class New_c extends polyglot.ast.New_c implements New, Annotated {

  protected Expr location;

  @Deprecated
  public New_c(Position pos, Expr qualifier, TypeNode tn, List<Expr> arguments,
      ClassBody body, Expr location) {
    this(pos, qualifier, tn, arguments, body, location, null);
  }

  public New_c(Position pos, Expr qualifier, TypeNode tn, List<Expr> arguments,
      ClassBody body, Expr location, Ext ext) {
    super(pos, qualifier, tn, arguments, body, ext);

    this.location = location;
  }

  @Override
  public New objectType(TypeNode tn) {
    return (New) super.objectType(tn);
  }

  @Override
  public Expr location() {
    return location;
  }

  @Override
  public New_c location(Expr location) {
    return location(this, location);
  }

  protected <N extends New_c> N location(N n, Expr location) {
    if (n.location == location) return n;
    n = copyIfNeeded(n);
    n.location = location;
    return n;
  }

  /**
   * Reconstructs the expression.
   */
  protected <N extends New_c> N reconstruct(N n, Expr qualifier, TypeNode tn,
      List<Expr> arguments, ClassBody body, Expr location) {
    n = super.reconstruct(n, qualifier, tn, arguments, body);
    n = location(n, location);
    return n;
  }

  @Override
  public New_c visitChildren(NodeVisitor v) {
    Expr qualifier = visitChild(this.qualifier, v);
    TypeNode tn = visitChild(this.objectType, v);
    List<Expr> arguments = visitList(this.arguments, v);
    ClassBody body = visitChild(this.body, v);
    Expr location = visitChild(this.location, v);

    return reconstruct(this, qualifier, tn, arguments, body, location);
  }

  @Override
  public New_c typeCheck(TypeChecker tc) throws SemanticException {
    FabILTypeSystem ts = (FabILTypeSystem) tc.typeSystem();
    New_c result = (New_c) super.typeCheck(tc);

    if (location != null) {
      if (!ts.isImplicitCastValid(location.type(), ts.Store())) {
        throw new SemanticException("Array location must be a store.",
            location.position());
      }
    }

    return result;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    if (qualifier != null) {
      v.visitCFG(qualifier, objectType, ENTRY);
    }

    Term last = objectType;

    if (location != null) {
      v.visitCFG(last, location, ENTRY);
      last = location;
    }

    if (body() != null) {
      v.visitCFG(last, listChild(arguments, body()), ENTRY);
      v.visitCFGList(arguments, body(), ENTRY);
      v.visitCFG(body(), this, EXIT);
    } else {
      if (!arguments.isEmpty()) {
        v.visitCFG(last, Term_c.<Expr, Expr, Expr> listChild(arguments, null),
            ENTRY);
        v.visitCFGList(arguments, this, EXIT);
      } else {
        v.visitCFG(last, this, EXIT);
      }
    }

    return succs;
  }

  @Override
  public Node disambiguateOverride(Node parent, AmbiguityRemover ar)
      throws SemanticException {
    New_c nn = (New_c) super.disambiguateOverride(parent, ar);

    if (nn.location != null) {
      nn = nn.location(nn.visitChild(nn.location, ar));
    }

    // If we have an anonymous class implementing an interface, make its
    // supertype fabric.lang.Object.
    if (nn.body() != null && nn.objectType().isDisambiguated()
        && nn.objectType().type().toClass().flags().isInterface()) {
      ParsedClassType anonType = nn.anonType();
      anonType.superType(((FabILTypeSystem) ar.typeSystem()).FObject());
    }

    return nn;
  }

  @Override
  public Node typeCheckOverride(Node parent, TypeChecker tc)
      throws SemanticException {
    New_c n = (New_c) super.typeCheckOverride(parent, tc);
    if (n == null) return null;

    if (n.location != null) {
      n = n.location(n.visitChild(n.location, tc));
    }

    return n;
  }

  @Override
  public Node copy(NodeFactory nf) {
    FabILNodeFactory filNf = (FabILNodeFactory) nf;
    return filNf.New(this.position, this.qualifier, this.objectType,
        this.location, this.arguments, this.body);
  }
}
