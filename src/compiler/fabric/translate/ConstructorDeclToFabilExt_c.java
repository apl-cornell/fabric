package fabric.translate;

import java.util.ArrayList;
import java.util.List;

import fabil.ast.FabILNodeFactory;
import polyglot.ast.*;
import polyglot.types.SemanticException;
import jif.translate.ConstructorDeclToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class ConstructorDeclToFabilExt_c extends ConstructorDeclToJavaExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Node n = super.toJava(rw);
    if (n instanceof MethodDecl) {
      // The constructor declaration has been rewritten to a method declaration.
      MethodDecl md = (MethodDecl)n;
      if (md.body() != null) {
        FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
//        FabILTypeSystem ts = (FabILTypeSystem)rw.java_ts();

        List<Stmt> stmts = new ArrayList<Stmt>(md.body().statements().size() + 1);
        
//        TypeNode worker = nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Worker());
//        stmts.add(nf.LocalDecl(Position.compilerGenerated(), 
//                               Flags.FINAL, 
//                               worker, 
//                               nf.Id(Position.compilerGenerated(), 
//                                     "worker$"),
//                               nf.Call(Position.compilerGenerated(), 
//                                       worker, 
//                                       nf.Id(Position.compilerGenerated(), 
//                                             "getWorker"))));
        stmts.addAll(md.body().statements());
        
        return md.body(nf.Block(md.body().position(), stmts));
      }
    }
    return n;
  }
}
