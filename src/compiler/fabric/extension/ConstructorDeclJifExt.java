package fabric.extension;

import fabric.types.AccessPathStore;
import fabric.types.FabricConstructorInstance;
import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricProcedureInstance;
import fabric.types.FabricTypeSystem;

import jif.extension.JifConstructorDeclExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifConstructorInstance;
import jif.types.JifContext;
import jif.types.JifProcedureInstance;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.PathMap;
import jif.types.label.AccessPathThis;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 *
 */
public class ConstructorDeclJifExt extends JifConstructorDeclExt implements Ext {

  public ConstructorDeclJifExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    // add some useful axioms
    JifContext A = lc.context();
    ClassType ct = A.currentClass();
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    Position pos = Position.compilerGenerated();
    if (ts.descendsFrom(ct, ts.PrincipalClass())) {
      // worker$ actsfor this and store$ actsfor this
      //  holds true for all principals
      A.addActsFor(ts.dynamicPrincipal(pos, new AccessPathStore(
          new AccessPathThis(ct, pos), ts.Store(), pos)), ts.dynamicPrincipal(
              pos, new AccessPathThis(ct, pos)));
      A.addActsFor(ts.workerLocalPrincipal(pos),
          ts.dynamicPrincipal(pos, new AccessPathThis(ct, pos)));
    }
    return super.labelCheck(lc);
  }

  @Override
  protected void setEndOfInitChecking(LabelChecker lc,
      JifConstructorInstance ci) {
    super.setEndOfInitChecking(lc, ci);
    FabricContext A = (FabricContext) lc.context();
    FabricConstructorInstance fci = (FabricConstructorInstance) ci;
    A.setAccessedConf(fci.beginAccessPolicy());
  }

  @Override
  protected void addReturnConstraints(Label Li, PathMap X,
      JifProcedureInstance mi, LabelChecker lc, final Type returnType)
    throws SemanticException {
    super.addReturnConstraints(Li, X, mi, lc, returnType);
    addAccessReturnConstraints(lc, (FabricPathMap) X, (FabricProcedureInstance) mi);
  }

  //TODO: Move this into general FabricProcedureDeclExt?
  /**
   * Add constraints requiring that all accesses in the method have
   * confidentiality upperbounded by the end confidentiality label.
   */
  protected void addAccessReturnConstraints(LabelChecker lc, FabricPathMap X,
      FabricProcedureInstance mi) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();

    // Get the join of all accesses in the method
    NamedLabel accessedConfLabel = new NamedLabel("accessed conf label",
        "the join of the confidentiality policies of referenced fields in the method",
        ts.join(ts.toLabel(A.accessedConf()), X.AC()));

    NamedLabel endConfLabel = new NamedLabel("end conf label",
        "the upper bound on the confidentiality of accessed fields in this method",
        ts.toLabel(mi.endConfPolicy()));

    lc.constrain(accessedConfLabel, LabelConstraint.LEQ, endConfLabel,
        A.labelEnv(), mi.position(), new ConstraintMessage() {
      @Override
      public String msg() {
        return "This method makes more confidential accesses than the ending "
             + "confidentiality label allows.";
      }
    });
  }
}
