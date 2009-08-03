package fabric.translate;

import java.util.Collection;

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
    
    if (containsProjection(pl.confPolicy()) || containsProjection(pl.integPolicy())) {
      if (!containsProjection(pl.confPolicy())) {
        cexp = rw.qq().parseExpr(rw.runtimeLabelUtil() + ".toLabel(%E)", cexp);
      }
      if (!containsProjection(pl.integPolicy())) {
        iexp = rw.qq().parseExpr(rw.runtimeLabelUtil() + ".toLabel(%E)", iexp);
      }
      // XXX should it be join or meet?
      return rw.qq().parseExpr(rw.runtimeLabelUtil() + ".join(%E, %E)", cexp, iexp);
    }
        
//    return (Expr) rw.qq().parseExpr(rw.runtimeLabelUtil() + ".toLabel(%E, %E)", cexp, iexp).position(Position.compilerGenerated(label.position().toString())); 
    return rw.qq().parseExpr(rw.runtimeLabelUtil() + ".toLabel(%E, %E)", cexp, iexp); 
  }
  
  protected boolean containsProjection(Policy p) {
    if (p instanceof ConfProjectionPolicy_c) return true;
    else if (p instanceof IntegProjectionPolicy_c) return true;
    else if (p instanceof JoinPolicy_c) {
      JoinPolicy_c jp = (JoinPolicy_c)p;
      for (Policy tp : (Collection<Policy>)jp.joinComponents()) {
        if (containsProjection(tp)) return true;
      }
    }
    else if (p instanceof MeetPolicy_c) {
      MeetPolicy_c mp = (MeetPolicy_c)p;
      for (Policy tp : (Collection<Policy>)mp.meetComponents()) {
        if (containsProjection(tp)) return true;
      }
    }
    return false;
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
    else if (p instanceof JoinPolicy_c) {
      if (containsProjection(p)) {
        JoinPolicy_c jp = (JoinPolicy_c)p;
        Expr result = null;
        for (Policy tp : (Collection<Policy>)jp.joinComponents()) {
          Expr ep = policyToJava(tp, rw);
          if (!containsProjection(tp)) {
            ep = rw.qq().parseExpr(rw.runtimeLabelUtil() + ".toLabel(%E)", ep);
          }
          if (result == null) {
            result = ep;
          }
          else {
            result = rw.qq().parseExpr(rw.runtimeLabelUtil() + ".join(%E, %E)", result, ep);
          }
        }
        return result;
      }
    }
    else if (p instanceof MeetPolicy_c) {
      if (containsProjection(p)) {
        MeetPolicy_c mp = (MeetPolicy_c)p;
        Expr result = null;
        for (Policy tp : (Collection<Policy>)mp.meetComponents()) {
          Expr ep = policyToJava(tp, rw);
          if (!containsProjection(tp)) {
            ep = rw.qq().parseExpr(rw.runtimeLabelUtil() + ".toLabel(%E)", ep);
          }
          if (result == null) {
            result = ep;
          }
          else {
            result = rw.qq().parseExpr(rw.runtimeLabelUtil() + ".meet(%E, %E)", result, ep);
          }
        }
        return result;
      }      
    }

    return super.policyToJava(p, rw);
  }
}
