package fabric.ast;

import java.util.List;

import polyglot.ast.AmbTypeNode;
import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.ParsedClassType;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.AmbiguityRemover;
import polyglot.visit.BodyDisambiguator;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PruningVisitor;
import polyglot.visit.SignatureDisambiguator;
import polyglot.visit.SupertypeDisambiguator;
import polyglot.visit.TypeChecker;

/**
 * <code>New</code> nodes in Fabric contain information on where the object
 * should be located.
 */
public class New_c extends polyglot.ast.New_c implements New {

  protected Expr location;

  public New_c(Position pos, Expr qualifier, TypeNode tn, Expr location,
      List<Expr> arguments, ClassBody body) {
    super(pos, qualifier, tn, arguments, body);
    this.location = location;
  }

  public Expr location() {
    return location;
  }

  public New location(Expr location) {
    New_c result = (New_c) copy();
    result.location = location;
    return result;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.New_c#disambiguateOverride(polyglot.ast.Node,
   *      polyglot.visit.AmbiguityRemover)
   */
  @Override
  public Node disambiguateOverride(Node parent, AmbiguityRemover ar)
      throws SemanticException {
    // XXX Most of this was copied from the overridden implementation.
    New nn = this;
    New old = nn;

    BodyDisambiguator bd = new BodyDisambiguator(ar);
    NodeVisitor childv = bd.enter(parent, this);

    if (childv instanceof PruningVisitor) {
      return nn;
    }

    BodyDisambiguator childbd = (BodyDisambiguator) childv;

    // Disambiguate the qualifier and object type, if possible.
    if (nn.qualifier() == null) {
      nn =
          (New) nn.objectType((TypeNode) nn
              .visitChild(nn.objectType(), childbd));
      if (childbd.hasErrors()) throw new SemanticException();

      if (!nn.objectType().isDisambiguated()) {
        return nn;
      }

      ClassType ct = nn.objectType().type().toClass();

      if (ct.isMember() && !ct.flags().isStatic()) {
        nn = (New) ((New_c) nn).findQualifier(ar, ct);

        nn = (New) nn.qualifier((Expr) nn.visitChild(nn.qualifier(), childbd));
        if (childbd.hasErrors()) throw new SemanticException();
      }
    } else {
      nn = (New) nn.qualifier((Expr) nn.visitChild(nn.qualifier(), childbd));
      if (childbd.hasErrors()) throw new SemanticException();

      if (nn.objectType() instanceof AmbTypeNode
          && ((AmbTypeNode) nn.objectType()).qual() == null) {

        // We have to disambiguate the type node as if it were a member of the
        // static type, outer, of the qualifier. For Java this is simple: type
        // nested type is just a name and we
        // use that name to lookup a member of the outer class. For some
        // extensions (e.g., PolyJ), the type node may be more complex than
        // just a name. We'll just punt here and let the extensions handle
        // this complexity.

        String name = ((AmbTypeNode) nn.objectType()).name();

        if (nn.qualifier().isDisambiguated() && nn.qualifier().type() != null
            && nn.qualifier().type().isCanonical()) {
          TypeSystem ts = ar.typeSystem();
          NodeFactory nf = ar.nodeFactory();
          Context c = ar.context();

          if (!nn.qualifier().type().isClass()) {
            throw new SemanticException(
                "Cannot instantiate member class of non-class type.", nn
                    .position());
          }

          ClassType outer = nn.qualifier().type().toClass();
          ClassType ct = ts.findMemberClass(outer, name, c.currentClass());
          TypeNode tn = nf.CanonicalTypeNode(nn.objectType().position(), ct);
          nn = (New) nn.objectType(tn);
        }
      } else if (!nn.objectType().isDisambiguated()) {
        throw new SemanticException(
            "Only simply-named member classes may be instantiated by a qualified new expression.",
            nn.objectType().position());
      } else {
        // already disambiguated
      }
    }

    // Disambiguate the location expression.
    nn = nn.location((Expr) nn.visitChild(nn.location(), childbd));

    // Now disambiguate the actuals.
    nn = (New) nn.arguments(nn.visitList(nn.arguments(), childbd));
    if (childbd.hasErrors()) throw new SemanticException();

    if (nn.body() != null) {
      if (!nn.objectType().isDisambiguated()) {
        return nn;
      }

      ClassType ct = nn.objectType().type().toClass();

      ParsedClassType anonType = nn.anonType();

      if (anonType != null && !anonType.supertypesResolved()) {
        if (!ct.flags().isInterface()) {
          anonType.superType(ct);
        } else {
          anonType.superType(ar.typeSystem().Object());
          anonType.addInterface(ct);
        }

        anonType.setSupertypesResolved(true);
      }

      SupertypeDisambiguator supDisamb = new SupertypeDisambiguator(childbd);
      nn = (New) nn.body((ClassBody) nn.visitChild(nn.body(), supDisamb));
      if (supDisamb.hasErrors()) throw new SemanticException();

      SignatureDisambiguator sigDisamb = new SignatureDisambiguator(childbd);
      nn = (New) nn.body((ClassBody) nn.visitChild(nn.body(), sigDisamb));
      if (sigDisamb.hasErrors()) throw new SemanticException();

      // Now visit the body.
      nn = (New) nn.body((ClassBody) nn.visitChild(nn.body(), childbd));
      if (childbd.hasErrors()) throw new SemanticException();
    }

    nn = (New) bd.leave(parent, old, nn, childbd);

    return nn;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.New_c#typeCheckOverride(polyglot.ast.Node,
   *      polyglot.visit.TypeChecker)
   */
  @Override
  public Node typeCheckOverride(Node parent, TypeChecker tc)
      throws SemanticException {
    // XXX Most of this was copied from the overridden implementation.
    New nn = this;
    New old = nn;

    BodyDisambiguator bd = new BodyDisambiguator(tc);
    NodeVisitor childv = tc.enter(parent, this);

    if (childv instanceof PruningVisitor) {
      return nn;
    }

    TypeChecker childtc = (TypeChecker) childv;

    if (nn.qualifier() != null) {
      nn = (New) nn.qualifier((Expr) nn.visitChild(nn.qualifier(), childtc));
      if (childtc.hasErrors()) throw new SemanticException();

      if (!nn.qualifier().type().isCanonical()) {
        return nn;
      }

      // Force the object type and class body, if any, to be disambiguated.
      nn = (New) bd.visitEdge(parent, nn);
      if (bd.hasErrors()) throw new SemanticException();

      if (!nn.objectType().isDisambiguated()) {
        return nn;
      }
    }

    // Now type check the rest of the children.
    nn =
        (New) nn.objectType((TypeNode) nn.visitChild(nn.objectType(), childtc));
    if (childtc.hasErrors()) throw new SemanticException();

    if (!nn.objectType().type().isCanonical()) {
      return nn;
    }

    nn = nn.location((Expr) nn.visitChild(nn.location(), childtc));
    if (childtc.hasErrors()) throw new SemanticException();

    nn = (New) nn.arguments(nn.visitList(nn.arguments(), childtc));
    if (childtc.hasErrors()) throw new SemanticException();

    nn = (New) nn.body((ClassBody) nn.visitChild(nn.body(), childtc));
    if (childtc.hasErrors()) throw new SemanticException();

    nn = (New) tc.leave(parent, old, nn, childtc);

    return nn;
  }

  /** Reconstruct the expression. */
  protected New_c reconstruct(Expr qualifier, TypeNode tn, Expr location,
      List<Expr> arguments, ClassBody body) {
    if (qualifier != this.qualifier || tn != this.tn
        || location != this.location
        || !CollectionUtil.equals(arguments, this.arguments)
        || body != this.body) {
      New_c n = (New_c) copy();
      n.tn = tn;
      n.qualifier = qualifier;
      n.location = location;
      n.arguments = TypedList.copyAndCheck(arguments, Expr.class, true);
      n.body = body;
      return n;
    }

    return this;
  }

  /** Visit the children of the expression. */
  @SuppressWarnings("unchecked")
  @Override
  public Node visitChildren(NodeVisitor v) {
    Expr qualifier = (Expr) visitChild(this.qualifier, v);
    TypeNode tn = (TypeNode) visitChild(this.tn, v);
    Expr location = (Expr) visitChild(this.location, v);
    List<Expr> arguments = visitList(this.arguments, v);
    ClassBody body = (ClassBody) visitChild(this.body, v);
    return reconstruct(qualifier, tn, location, arguments, body);
  }
}
