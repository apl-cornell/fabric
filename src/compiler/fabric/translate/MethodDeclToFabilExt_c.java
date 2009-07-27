package fabric.translate;

import fabil.ast.FabILNodeFactory;
import polyglot.ast.If;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.JifToJavaRewriter;
import jif.translate.MethodDeclToJavaExt_c;

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
      ifStmt = ifStmt.alternative(rw.qq().parseStmt("throw new fabric.common.exceptions.InternalError();"));
//      ifStmt = ifStmt.alternative(nf.Throw(Position.compilerGenerated(), nf.New(Position.compilerGenerated(), nf.CanonicalTypeNode(Position.compilerGenerated(), ts.InternalError()), Collections.EMPTY_LIST)));
//      tryStmt = tryStmt.tryBlock(nf.Block(Position.compilerGenerated(), npecall, ifStmt));
      return md.body(nf.Block(Position.compilerGenerated(), ifStmt));
    }
    
    return md;
//    List<Stmt> stmts = new ArrayList<Stmt>(md.body().statements().size() + 1);
    
//    TypeNode client = nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Client());
//    stmts.add(nf.LocalDecl(Position.compilerGenerated(), 
//                           Flags.FINAL, 
//                           client, 
//                           nf.Id(Position.compilerGenerated(), 
//                                 "client$"),
//                           nf.Call(Position.compilerGenerated(), 
//                                   client, 
//                                   nf.Id(Position.compilerGenerated(), 
//                                         "getClient"))));
//    stmts.addAll(md.body().statements());
    
//    return md.body(nf.Block(md.body().position(), stmts));
  }
}
