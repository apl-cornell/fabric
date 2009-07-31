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
import polyglot.util.CodeWriter;
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
  
  @Override
  public void dump(CodeWriter w) {
    super.dump(w);
    w.write("(location ");
    if (location == null) {
      w.write("null");
    } else {
      location.dump(w);
    }
    w.write(")");
  }
  

  /**
   * Checks that the location is compatible with the <code>objectLabel</code>.
   * @param lc
   * @param objectLabel
   */
  public void labelCheck(LabelChecker lc, final Label objectLabel) throws SemanticException {
    Node n = node();

    if (location() != null && objectLabel != null) {
      FabricTypeSystem ts = (FabricTypeSystem)lc.typeSystem();
      JifContext A = lc.jifContext();
      A = (JifContext)n.del().enterScope(A);

      lc.constrain(new NamedLabel("L", objectLabel),
          LabelConstraint.LEQ,
          new NamedLabel("{*->core}", 
              ts.pairLabel(Position.compilerGenerated(), 
                  ts.readerPolicy(Position.compilerGenerated(),
                      ts.topPrincipal(Position.compilerGenerated()),
                      corePrincipal()),
                  ts.topIntegPolicy(Position.compilerGenerated()))),
          A.labelEnv(), 
          n.position(),
          new ConstraintMessage () {
        @Override
        public String msg() {
          return "L <= {*->core} for new C@core() where the field label of C is L.";
        }

        @Override
        public String detailMsg() {
          return "The object being created on the core " + corePrincipal().toString() + " should be readable by the core's principal";
        }

        @Override
        public String technicalMsg() {
          return "The label " + objectLabel.toString() + " should not be more restrictive than the confidentiality label of the core's principal joined with the top integrity label";
        }
      });

      lc.constrain(new NamedLabel("{*<-core}", 
          ts.pairLabel(Position.compilerGenerated(),
              ts.bottomConfPolicy(Position.compilerGenerated()), 
              ts.writerPolicy(Position.compilerGenerated(),
                  ts.topPrincipal(Position.compilerGenerated()), 
                  corePrincipal()))),
          LabelConstraint.LEQ,
          new NamedLabel("L", objectLabel),
          A.labelEnv(),         
          n.position(),
          new ConstraintMessage() {
        @Override
        public String msg() {
          return "{*<-core} <= L for new C@core() where the field label of C is L.";
        }

        @Override
        public String detailMsg() {
          return "The object being created on the core " + corePrincipal().toString() + " should be writeable by the core's principal";
        }

        @Override
        public String technicalMsg() {
          return "The integrity label of the core's principal joined with the bottom confidentiality label should not be more restrictive than the label " + objectLabel.toString();
        }        
      });

      //      lc.constrain(new NamedLabel("{C(L);*<-core}", 
      //                                  ts.pairLabel(Position.compilerGenerated(), 
      //                                               ts.confProjection(objectLabel), 
      //                                               ts.writerPolicy(Position.compilerGenerated(), 
      //                                                               ts.topPrincipal(Position.compilerGenerated()), 
      //                                                               corePrincipal()))), 
      //                   LabelConstraint.LEQ, 
      //                   new NamedLabel("{*->core;I(L)}",
      //                                  ts.pairLabel(Position.compilerGenerated(), 
      //                                               ts.readerPolicy(Position.compilerGenerated(), 
      //                                                               ts.topPrincipal(Position.compilerGenerated()), 
      //                                                               corePrincipal()), 
      //                                               ts.integProjection(objectLabel))),
      //                   A.labelEnv(), 
      //                   n.position(),
      //                   new ConstraintMessage() {
      //        @Override
      //        public String msg() {
      //          return "C(L) <= {*->core} and {*<-core} <= I(L) for new C@core() where the field label of C is L.";
      //        }
      //        
      //        @Override
      //        public String detailMsg() {
      //          return "C(L) <= {*->core} and {*<-core} <= I(L) for new C@core() where the field label of C is L.";
      //        }
      //        
      //        @Override
      //        public String technicalMsg() {
      //          return "C(L) <= {*->core} and {*<-core} <= I(L) for new C@core() where the field label of C is L.";
      //        }
      //      });
    }
  }
}
