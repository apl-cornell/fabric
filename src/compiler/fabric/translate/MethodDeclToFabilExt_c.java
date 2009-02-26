package fabric.translate;

import java.util.*;

import fabil.ast.FabILNodeFactory;
import fabil.types.FabILTypeSystem;
import polyglot.ast.If;
import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.JifToJavaRewriter;
import jif.translate.MethodDeclToJavaExt_c;

public class MethodDeclToFabilExt_c extends MethodDeclToJavaExt_c {
  @SuppressWarnings("unchecked")
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    MethodDecl md = (MethodDecl)super.toJava(rw);
    
    if (md.body() == null) {
      // abstract method
      return md;
    }
    
    FabILNodeFactory nf = (FabILNodeFactory)rw.nodeFactory();
    FabILTypeSystem ts = (FabILTypeSystem)rw.java_ts();
    
    if (md.name().endsWith("_remote")) {
      // Fabric wrapper
      // The body has to be an if statement
      If ifStmt = (If)md.body().statements().get(0);
//      ifStmt = ifStmt.alternative(rw.qq().parseStmt("throw new java.lang.InternalError()"));
      ifStmt = ifStmt.alternative(nf.Throw(Position.compilerGenerated(), nf.New(Position.compilerGenerated(), nf.CanonicalTypeNode(Position.compilerGenerated(), ts.InternalError()), Collections.EMPTY_LIST)));
      return md.body(nf.Block(Position.compilerGenerated(), ifStmt));
    }
    
    List<Stmt> stmts = new ArrayList<Stmt>(md.body().statements().size() + 1);
    
    TypeNode client = nf.CanonicalTypeNode(Position.compilerGenerated(), ts.Client());
    stmts.add(nf.LocalDecl(Position.compilerGenerated(), 
                           Flags.FINAL, 
                           client, 
                           nf.Id(Position.compilerGenerated(), 
                                 "client$"),
                           nf.Call(Position.compilerGenerated(), 
                                   client, 
                                   nf.Id(Position.compilerGenerated(), 
                                         "getClient"))));
    stmts.addAll(md.body().statements());
    
    return md.body(nf.Block(md.body().position(), stmts));
  }
}
