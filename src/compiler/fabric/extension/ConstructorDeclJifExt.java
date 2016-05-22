package fabric.extension;

import fabric.types.AccessPathStore;
import fabric.types.FabricConstructorInstance;
import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;

import jif.ast.JifConstructorDecl;
import jif.extension.JifConstructorDeclExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.JifConstructorInstance;
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
    FabricContext A = (FabricContext) lc.context();
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

    // Run the label check
    return super.labelCheck(lc);
  }

  /**
   * Add additional context initialization for conflict labels
   */
  @Override
  protected void setEndOfInitChecking(LabelChecker lc,
      JifConstructorInstance ci) {
    super.setEndOfInitChecking(lc, ci);
    FabricConstructorInstance fci = (FabricConstructorInstance) ci;
    FabricContext A = (FabricContext) lc.context();
    A.setBeginConflictBound(fci.beginConflictLabel());
    A.setConflictLabel(fci.beginConflictLabel());
    A.setEndConflictBound(fci.endConflictLabel());
  }

  /**
   * Add additional ending constraint that we're respecting the end conflict
   * label of the method.
   */
  @Override
  protected void addReturnConstraints(Label Li, PathMap X,
      JifProcedureInstance ci, LabelChecker lc, final Type returnType) throws
  SemanticException {
    super.addReturnConstraints(Li, X, ci, lc, returnType);

    FabricConstructorInstance fci = (FabricConstructorInstance) ci;
    FabricTypeSystem ts = (FabricTypeSystem) lc.jifTypeSystem();
    FabricContext A = (FabricContext) lc.context();
    FabricPathMap Xf = (FabricPathMap) X;

    // Don't bother if we didn't make accesses.
    if (Xf.CL().equals(ts.noAccesses()))
      return;
    
    final String name = ((JifConstructorDecl) node()).name();

    NamedLabel endConflictBoundLabel = new NamedLabel("end conflict label of " + name,
        "lower bound on accesses that can be made up to the end of the body of "
        + name, 
        fci.endConflictLabel());

    NamedLabel endCLN = new NamedLabel("prev conflict label",
        "the meet of the conflict labels of accesses up to the end of the method",
        Xf.CL());

    // Check that the end conflict label is respected by the method body.
    lc.constrain(endConflictBoundLabel,
        LabelConstraint.LEQ,
        endCLN.join(lc, "{⊥→;⊥←}", ts.noComponentsLabel()),
        A.labelEnv(), ci.position(), new ConstraintMessage() {
      @Override
      public String msg() {
        return "The body of " + name + " makes less restricted accesses than "
          + "the ending conflict label allows.";
      }
    });
  }

  @Override
  protected Label checkEnforceSignature(JifProcedureInstance mi,
          LabelChecker lc) throws SemanticException {
    FabricContext A = (FabricContext) lc.context();
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

    // Check that we can actually stage from the start to the end.
    FabricConstructorInstance fci = (FabricConstructorInstance) mi;
    final Position declPos = node().position();
    if (!fci.beginConflictLabel().equals(ts.noAccesses())) {
      lc.constrain(new NamedLabel("end conflict label", fci.endConflictLabel()),
          LabelConstraint.LEQ,
          new NamedLabel("begin conflict label", fci.beginConflictLabel()).join(lc, "{⊥→;⊥←}", ts.noComponentsLabel()),
          A.labelEnv(), declPos,
          new ConstraintMessage() {
            @Override
            public String msg() {
              return "End conflict label must have lower confidentiality than"
                     + " begin conflict label for constructor at " + declPos;
            }
      });

      lc.constrain(new NamedLabel("caller pc", A.pc()),
          LabelConstraint.LEQ,
          new NamedLabel("end conflict label", fci.endConflictLabel()).join(lc, "{⊥→;⊥←}", ts.noComponentsLabel()),
          A.labelEnv(), declPos,
          new ConstraintMessage() {
            @Override
            public String msg() {
              return "Caller pc must be no more secret than the ending stage of the constructor at " + declPos;
            }
      });

      lc.constrain(new NamedLabel("caller pc", A.pc()),
          LabelConstraint.LEQ,
          new NamedLabel("begin conflict label", fci.beginConflictLabel()).join(lc, "{⊥→;⊥←}", ts.noComponentsLabel()),
          A.labelEnv(), declPos,
          new ConstraintMessage() {
            @Override
            public String msg() {
              return "Caller pc must be no more secret than the starting stage of the constructor at " + declPos;
            }
      });
    }

    return super.checkEnforceSignature(mi, lc);
  }
}
