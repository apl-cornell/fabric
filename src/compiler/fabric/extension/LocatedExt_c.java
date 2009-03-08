package fabric.extension;

import fabric.types.FabricTypeSystem;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.types.principal.Principal;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.util.Position;

/**
 * This class provides common functionality to the New and NewArray for managing
 * a location field
 */
public abstract class LocatedExt_c extends NodeExt_c implements FabricExt {
  protected Expr location;
  protected Principal corePrincipal;

  public Expr location() {
    return location;
  }

  public LocatedExt_c location(Expr location) {
    LocatedExt_c result = (LocatedExt_c) copy();
    result.location = location;
    return result;
  }
  
  public Principal corePrincipal() {
    return corePrincipal;
  }
  
  public LocatedExt_c corePrincipal(Principal p) {
    LocatedExt_c result = (LocatedExt_c)this.copy();
    result.corePrincipal = p;
    return result;
  }
  
  /**
   * Checks that the location is compatible with the <code>objectLabel</code>.
   * @param lc
   * @param objectLabel
   */
  public void labelCheck(LabelChecker lc, Label objectLabel) throws SemanticException {
    Node n = node();
    
    if (location() != null) {
      FabricTypeSystem ts = (FabricTypeSystem)lc.typeSystem();
      JifContext A = lc.jifContext();
      A = (JifContext)n.del().enterScope(A);
      
      lc.constrain(new NamedLabel("{C(L);*<-core}", 
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               ts.confProjection(objectLabel), 
                                               ts.writerPolicy(Position.compilerGenerated(), 
                                                               ts.topPrincipal(Position.compilerGenerated()), 
                                                               corePrincipal()))), 
                   LabelConstraint.LEQ, 
                   new NamedLabel("{*->core;I(L)}",
                                  ts.pairLabel(Position.compilerGenerated(), 
                                               ts.readerPolicy(Position.compilerGenerated(), 
                                                               ts.topPrincipal(Position.compilerGenerated()), 
                                                               corePrincipal()), 
                                               ts.integProjection(objectLabel))),
                   A.labelEnv(), 
                   n.position(),
                   new ConstraintMessage() {
        @Override
        public String msg() {
          return "C(L) <= {*->core} and {*<-core} <= I(L) for new C@core() where the field label of C is L.";
        }
        
        @Override
        public String detailMsg() {
          return "C(L) <= {*->core} and {*<-core} <= I(L) for new C@core() where the field label of C is L.";
        }
        
        @Override
        public String technicalMsg() {
          return "C(L) <= {*->core} and {*<-core} <= I(L) for new C@core() where the field label of C is L.";
        }
      });
    }
  }
}
