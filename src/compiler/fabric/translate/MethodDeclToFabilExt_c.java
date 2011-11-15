package fabric.translate;

import java.util.Collections;
import java.util.List;

import fabil.ast.FabILNodeFactory;
import polyglot.ast.Binary;
import polyglot.ast.Block;
import polyglot.ast.Expr;
import polyglot.ast.Formal;
import polyglot.ast.If;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.Stmt;
import polyglot.ast.StringLit;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.JifToJavaRewriter;
import jif.translate.MethodDeclToJavaExt_c;
import jif.types.ActsForConstraint;
import jif.types.Assertion;
import jif.types.JifTypeSystem;
import jif.types.LabelLeAssertion;

public class MethodDeclToFabilExt_c extends MethodDeclToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    MethodDecl md = (MethodDecl)super.toJava(rw);
    
    if (md.body() == null) {
      // abstract method
      return md;
    }
    
    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
    
    if (md.name().endsWith("_remote")) {
      // Fabric wrapper
      // Rewrite the else block to throw an exception
      If ifStmt = (If)md.body().statements().get(0);
      ifStmt = ifStmt.alternative(rw.qq().parseStmt("throw new fabric.worker.remote.RemoteCallLabelCheckFailedException();"));
      return md.body(nf.Block(Position.compilerGenerated(), ifStmt));
    }
    
    return md;
  }
  
  /** Rewrite static main(principal p, String[] args) {...} to
   * static main(String[] args) {Principal p = Runtime.getUser(); {...} };
   */
  @SuppressWarnings("unchecked")
  @Override
  public Node staticMainToJava(JifToJavaRewriter rw, MethodDecl n) throws SemanticException {
      Formal formal0 = (Formal)n.formals().get(0); // the principal
      Formal formal1 = (Formal)n.formals().get(1); // the string array
      List<Formal> formalList = Collections.singletonList(formal1);

      Block origBody = n.body();

      JifTypeSystem jifTs = rw.jif_ts();
      TypeNode type = rw.qq().parseType(jifTs.PrincipalClassName());
      Expr init = rw.qq().parseExpr(jifTs.RuntimePackageName()
          + ".Runtime.user(null)");

      Stmt declPrincipal =
          rw.java_nf().LocalDecl(origBody.position(),
                             Flags.FINAL,
                             type,
                             rw.java_nf().Id(Position.compilerGenerated(), formal0.name()),
                             init);
    
    // Translate the constraints and use them to guard the body.
    Block newBody = guardWithConstraints(rw, origBody);
    // Wrap with a transaction if there are constraints
    if (!mi.constraints().isEmpty())
      newBody =
          ((FabILNodeFactory) rw.java_nf()).Atomic(origBody.position(), newBody
              .statements());
      
      newBody = rw.java_nf().Block(origBody.position(),
                                         declPrincipal,
                                         newBody);

      n = rw.java_nf().MethodDecl(n.position(),
                                  n.flags(),
                                  n.returnType(),
                                  rw.java_nf().Id(Position.compilerGenerated(), n.name()),
                                  formalList,
                                  n.throwTypes(),
                                  newBody);
      n = n.methodInstance(null);
      return n;
  }

  protected Block guardWithConstraints(JifToJavaRewriter rw, Block b) throws SemanticException {
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
              nf.Throw(pos, nf.New(pos, nf.CanonicalTypeNode(pos, rw
                      .java_ts().Error()), Collections
                      .singletonList(errorMessage)));
      return nf.Block(pos, nf.If(pos, guard, b, error));
  }
}
