package jif.translate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jif.ast.JifMethodDecl;
import jif.types.ActsForConstraint;
import jif.types.Assertion;
import jif.types.JifMethodInstance;
import jif.types.JifPolyType;
import jif.types.JifTypeSystem;
import jif.types.LabelLeAssertion;
import polyglot.ast.*;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

public class MethodDeclToJavaExt_c extends ToJavaExt_c {
  protected JifMethodInstance mi;
  protected List formals;

  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    // Bypass labels and constraints
    JifMethodDecl n = (JifMethodDecl) node();

    mi = (JifMethodInstance) n.methodInstance();
    formals = new ArrayList(n.formals());

    // Bypass startLabel, returnLabel and constraints.
    return rw.bypass(n.startLabel()).bypass(n.returnLabel())
        .bypass(n.constraints());
  }

  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    MethodDecl n = (MethodDecl) node();
    boolean isMainMethod = "main".equals(n.name()) && n.flags().isStatic();
    if (isMainMethod && n.formals().size() == 2) {
      // the method is static main(principal p, String[] args). We
      // need to translate this specially.
      // (The typechecking for JifMethodDecl ensures that the formals
      // are of the correct type.)
      return staticMainToJava(rw, n);
    }

    MethodInstance mi = n.methodInstance();
    List formals = new ArrayList(n.formals().size() + 2);

    // for static methods, add args for the params of the class
    if (mi.flags().isStatic() && mi.container() instanceof JifPolyType) {
      JifPolyType jpt = (JifPolyType) mi.container();
      formals.addAll(ClassDeclToJavaExt_c.produceParamFormals(jpt, rw, false));
    }

    formals.addAll(n.formals());
    n =
        rw.java_nf().MethodDecl(n.position(), n.flags(), n.returnType(),
            rw.java_nf().Id(Position.compilerGenerated(), n.name()), formals,
            n.throwTypes(), n.body());
    n = n.methodInstance(null);

    if (isMainMethod) {
      // Translate the constraints and use them to guard the body.
      n = (MethodDecl) n.body(guardWithConstraints(rw, n.body()));
    }

    return n;
  }

  /**
   * Rewrite static main(principal p, String[] args) {...} to static
   * main(String[] args) {Principal p = Runtime.getUser(); {...} };
   */
  public Node staticMainToJava(JifToJavaRewriter rw, MethodDecl n) throws SemanticException {
    Formal formal0 = (Formal) n.formals().get(0); // the principal
    Formal formal1 = (Formal) n.formals().get(1); // the string array
    List formalList = CollectionUtil.list(formal1);

    Block origBody = n.body();

    JifTypeSystem jifTs = rw.jif_ts();
    TypeNode type = rw.qq().parseType(jifTs.PrincipalClassName());
    Expr init =
        rw.qq().parseExpr(jifTs.RuntimePackageName() + ".Runtime.user(null)");

    Stmt declPrincipal =
        rw.java_nf()
            .LocalDecl(origBody.position(), Flags.FINAL, type,
                rw.java_nf().Id(Position.compilerGenerated(), formal0.name()),
                init);
    // Translate the constraints and use them to guard the body.
    Block newBody = guardWithConstraints(rw, origBody);

    newBody = rw.java_nf().Block(origBody.position(), declPrincipal, newBody);

    n =
        rw.java_nf().MethodDecl(n.position(), n.flags(), n.returnType(),
            rw.java_nf().Id(Position.compilerGenerated(), n.name()),
            formalList, n.throwTypes(), newBody);
    n = n.methodInstance(null);
    return n;
  }

  protected Block guardWithConstraints(JifToJavaRewriter rw, Block b)
      throws SemanticException {
    NodeFactory nf = rw.java_nf();
    @SuppressWarnings("unchecked")
    List<Assertion> constraints = mi.constraints();
    Position pos = b.position();
    Expr guard = null;
    for (Assertion constraint : constraints) {
      Expr conjunct;
      if (constraint instanceof ActsForConstraint) {
        conjunct = ((ActsForConstraint) constraint).toJava(rw);
      } else if (constraint instanceof LabelLeAssertion) {
        conjunct = ((LabelLeAssertion) constraint).toJava(rw);
      } else {
        continue;
      }

      // Turn the constraint into a boolean expression.
      if (guard == null) {
        guard = conjunct;
      } else {
        guard = nf.Binary(pos, guard, Binary.COND_AND, conjunct);
      }
    }

    if (guard == null) return b;
    StringLit errorMessage =
        nf.StringLit(pos, "The method " + mi.debugString()
            + " has constraints that are unsatisfied.");
    Stmt error =
        nf.Throw(pos, nf.New(pos,
            nf.CanonicalTypeNode(pos, rw.java_ts().Error()),
            Collections.singletonList(errorMessage)));
    return nf.Block(pos, nf.If(pos, guard, b, error));
  }
}
