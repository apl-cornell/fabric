package fabric.translate;

import java.util.Collections;
import java.util.List;

import jif.translate.JifToJavaRewriter;
import jif.translate.MethodDeclToJavaExt_c;
import jif.types.JifTypeSystem;
import polyglot.ast.Block;
import polyglot.ast.Expr;
import polyglot.ast.Formal;
import polyglot.ast.If;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;

public class MethodDeclToFabilExt_c extends MethodDeclToJavaExt_c {
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    MethodDecl md = (MethodDecl)super.toJava(rw);
    
    if (md.body() == null) {
      // abstract method
      return md;
    }
    
    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
//    FabILTypeSystem ts = (FabILTypeSystem)rw.java_ts();
    
    if (md.name().endsWith("_remote")) {
      // Fabric wrapper
      // Rewrite the else block to throw an exception
//      Try tryStmt = (Try)md.body().statements().get(0);
//      Eval npecall = (Eval) tryStmt.tryBlock().statements().get(0);
//      If ifStmt = (If)tryStmt.tryBlock().statements().get(1);
      If ifStmt = (If)md.body().statements().get(0);
      ifStmt = ifStmt.alternative(rw.qq().parseStmt("throw new fabric.worker.remote.RemoteCallLabelCheckFailedException();"));
//      ifStmt = ifStmt.alternative(nf.Throw(Position.compilerGenerated(), nf.New(Position.compilerGenerated(), nf.CanonicalTypeNode(Position.compilerGenerated(), ts.InternalError()), Collections.EMPTY_LIST)));
//      tryStmt = tryStmt.tryBlock(nf.Block(Position.compilerGenerated(), npecall, ifStmt));
      return md.body(nf.Block(Position.compilerGenerated(), ifStmt));
    }
    
    return md;
//    List<Stmt> stmts = new ArrayList<Stmt>(md.body().statements().size() + 1);
    
//    TypeNode worker = nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Worker());
//    stmts.add(nf.LocalDecl(Position.compilerGenerated(), 
//                           Flags.FINAL, 
//                           worker, 
//                           nf.Id(Position.compilerGenerated(), 
//                                 "worker$"),
//                           nf.Call(Position.compilerGenerated(), 
//                                   worker, 
//                                   nf.Id(Position.compilerGenerated(), 
//                                         "getWorker"))));
//    stmts.addAll(md.body().statements());
    
//    return md.body(nf.Block(md.body().position(), stmts));
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
}
