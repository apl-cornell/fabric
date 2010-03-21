package fabric.translate;

import fabil.ast.FabILNodeFactory;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Cast;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import jif.translate.CastToJavaExt_c;
import jif.translate.JifToJavaRewriter;

public class CastToFabilExt_c extends CastToJavaExt_c {
  protected Type exprType;
  
  @Override
  public NodeVisitor toJavaEnter(JifToJavaRewriter rw) throws SemanticException {
    Cast c = (Cast)this.node();
    exprType = c.expr().type();
    return super.toJavaEnter(rw);
  }
  
  @Override
  public Node toJava(JifToJavaRewriter rw) throws SemanticException {
    Cast c = (Cast)node();
    
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();
    FabILNodeFactory nf = (FabILNodeFactory)rw.java_nf();
    
    if (ts.isPrincipal(castType) 
     && (ts.typeEquals(ts.Worker(), exprType) 
      || ts.typeEquals(ts.RemoteWorker(), exprType))
      || ts.typeEquals(ts.Store(), exprType)) {
      return nf.Call(c.position(), c.expr(), nf.Id(Position.compilerGenerated(), "getPrincipal"));
    }
    
    return super.toJava(rw);
  }
}
