package fabric.translate;

import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import jif.translate.JifToJavaRewriter;
import jif.translate.PairLabelToJavaExpr_c;
import jif.types.label.*;

public class FabricPairLabelToFabilExpr_c extends PairLabelToJavaExpr_c {
  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw) throws SemanticException {
    PairLabel pl = (PairLabel)label;
        
    if (pl.confPolicy().isBottomConfidentiality() && pl.integPolicy().isTopIntegrity()) {
        return rw.qq().parseExpr(rw.runtimeLabelUtil() + ".noComponents()");
    }
    
    Expr cexp = policyToJava(pl.confPolicy(), rw);
    Expr iexp = policyToJava(pl.integPolicy(), rw);
    
    if (pl.confPolicy() instanceof ConfProjectionPolicy_c  
     || pl.integPolicy() instanceof IntegProjectionPolicy_c) {
      if (!(pl.confPolicy() instanceof ConfProjectionPolicy_c)) {
        cexp = rw.qq().parseExpr(rw.runtimeLabelUtil() + ".toLabel(%E)", cexp);
      }
      if (!(pl.integPolicy() instanceof IntegProjectionPolicy_c)) {
        iexp = rw.qq().parseExpr(rw.runtimeLabelUtil() + ".toLabel(%E)", iexp);
      }
      return rw.qq().parseExpr(rw.runtimeLabelUtil() + ".meet(%E, %E)", cexp, iexp);
    }
    
    return rw.qq().parseExpr(rw.runtimeLabelUtil() + ".toLabel(%E, %E)", cexp, iexp); 
  }
  
  @Override
  public Expr policyToJava(Policy p, JifToJavaRewriter rw) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem)rw.jif_ts();
    
    if (p instanceof IntegProjectionPolicy_c) {
      IntegProjectionPolicy_c ipp = (IntegProjectionPolicy_c)p;
      Label l = 
        ts.meet(ipp.label(), 
                ts.pairLabel(Position.compilerGenerated(), 
                             ts.bottomConfPolicy(Position.compilerGenerated()), 
                             ts.topIntegPolicy(Position.compilerGenerated())));
      return l.toJava(rw);
    }
    else if (p instanceof ConfProjectionPolicy_c) {
      ConfProjectionPolicy_c cpp = (ConfProjectionPolicy_c)p;
      Label l = 
        ts.meet(cpp.label(), 
                ts.pairLabel(Position.compilerGenerated(), 
                             ts.topConfPolicy(Position.compilerGenerated()), 
                             ts.bottomIntegPolicy(Position.compilerGenerated())));
      return l.toJava(rw);
    }

    return super.policyToJava(p, rw);
  }
}
