package fabric.extension;

import java.util.ArrayList;
import java.util.List;

import fabric.ast.FabricNodeFactory;
import fabric.types.FabricContext;
import fabric.types.FabricMethodInstance;
import fabric.types.FabricPathMap;
import fabric.types.FabricTypeSystem;
import fabric.visit.StageTxnMethodAdder;

import jif.extension.JifFieldExt;
import jif.translate.ToJavaExt;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.LabeledType;
import jif.types.NamedLabel;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Binary;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.ast.TypeNode;
import polyglot.ast.Unary;
import polyglot.types.ConstructorInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class FabricFieldExt extends JifFieldExt {

  public FabricFieldExt(ToJavaExt toJava) {
    super(toJava);
  }

  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    // Jif checks.
    Field fe = (Field) super.labelCheck(lc);

    // Access label checks
    DereferenceHelper.checkDereference(fe.target(), lc, node().position());

    // Conflict label checks
    fe = conflictLabelCheck(fe, lc, false);
    return fe;
  }

  /**
   * Check that the access does not leak on conflicts and can be "staged"
   * according to staged commit.
   *
   * Treats the access as a write if isWrite is true and otherwise treats the
   * access as a read.
   *
   * Assumes target has already been checked.
   */
  static protected Field conflictLabelCheck(Field fe, LabelChecker lc,
      final boolean isWrite) throws SemanticException {
    final Field origFE = fe;
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();
    FabricContext A = (FabricContext) lc.context();
    
    // XXX: dumb hack to make sure we're not staging 2 times due to the second
    // label checking pass.
    if (fe.target() instanceof Call) {
      Call c = (Call) fe.target();
      if (c.name().equals(StageTxnMethodAdder.STAGE_TXN_MD_NAME + c.type().toClass().name())) {
        return fe;
      }
    }

    // Only check persistent object accesses
    if (ts.isTransient(fe.target().type())
        && !ts.isFabricArray(fe.target().type()))
      return fe;

    // Don't bother checking final field accesses, they aren't locked during
    // runtime.
    if (fe.fieldInstance().flags().isFinal())
      return fe;

    // If we're in a constructor, we know that we can't conflict with accesses
    // to the currently constructing object.
    if (lc.context().currentCode() instanceof ConstructorInstance &&
        fe.target() instanceof Special) {
      // XXX: Why doesn't inConstructorCall work?
      Special s = (Special) fe.target();
      if (s.kind().equals(Special.THIS))
        return fe;
    }

    Position pos = fe.position();
    // Using this instead of field instance so we get the instantiated label
    Label L = ((LabeledType) fe.type()).labelPart();
    FabricPathMap Xe = (FabricPathMap) getPathMap(fe);

    NamedLabel conflictL;
    if (isWrite) {
      conflictL = new NamedLabel("write conflict label", ts.writeConflict(L));
    } else {
      conflictL = new NamedLabel("read conflict label", ts.readConflict(L));
    }
    NamedLabel pc = new NamedLabel("pc",
        ts.join(ts.join(Xe.N(), A.currentCodePCBound()),
                ts.pairLabel(pos, ts.bottomConfPolicy(pos), ts.topIntegPolicy(pos))));
    NamedLabel conflictPC = new NamedLabel("conflict pc",
        ts.join(ts.meet(Xe.CL(), ts.meet(A.conflictLabel(), A.beginConflictBound())),
                ts.pairLabel(pos, ts.bottomConfPolicy(pos), ts.topIntegPolicy(pos))));

    // Check pc ≤ CL(op field)
    lc.constrain(pc, LabelConstraint.LEQ, conflictL, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Conflicts when " + (isWrite ? "writing" : "reading") + " " +
              origFE + " may leak secret information to other transactions.";
          }
    });

    // Check CL(op field) ≤ meet(CL(prev accesses))
    lc.constrain(conflictL, LabelConstraint.LEQ, conflictPC, A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return (isWrite ? "Write" : "Read") + " access of " + origFE +
              " can't be included in the current transaction stage.";
          }
    });

    if (!lc.context().labelEnv().leq(conflictPC.label(), conflictL.label())) {
      FabricNodeFactory nf = (FabricNodeFactory) lc.nodeFactory();
      // Generate the dynamic check, if !(conflictPC ≤ conflictL) then we need
      // to stage (since the stage label's about to change).
      String stageSuffix = "";
      if (fe.target() instanceof TypeNode) {
        throw new InternalCompilerError("Staging translation does not support non-final static fields");
      } else {
        Expr tgtExp = (Expr) fe.target();
        stageSuffix = tgtExp.type().toClass().name();
      }

      // Make the staging wrapper call.
      Call stageCall = nf.Call(pos, fe.target(),
            nf.Id(pos, StageTxnMethodAdder.STAGE_TXN_MD_NAME + stageSuffix),
                nf.Unary(pos, Unary.NOT,
                  nf.Binary(pos, nf.LabelExpr(pos, conflictPC.label()).type(ts.Label()),
                                  Binary.LE,
                                 nf.LabelExpr(pos, conflictL.label()).type(ts.Label())).type(ts.Boolean())).type(ts.Boolean()));

      // Get the wrapper call type information
      List<Type> stageArgTypes = new ArrayList<>();
      stageArgTypes.add(ts.Boolean());
      FabricMethodInstance mi = (FabricMethodInstance)
        ts.findMethod(fe.target().type().toReference(),
            StageTxnMethodAdder.STAGE_TXN_MD_NAME + stageSuffix, stageArgTypes,
            lc.context().currentClass(), true);

      // Set the type information
      stageCall = stageCall.methodInstance(mi);

      // Hack to avoid null pointers for stuff in the call delegate.
      FabricCallDel fcd = (FabricCallDel) stageCall.del();
      fcd.setReceiverVarLabel(ts);

      // Run label checking on the staging wrapper
      stageCall = (Call) lc.labelCheck(stageCall);

      // Replace the access with a call to the wrapper before the field is
      // grabbed.
      fe = fe.target(stageCall);
    }
    
    // Update the CL
    Xe = Xe.CL(ts.meet(conflictL.label(), conflictPC.label()));
    return (Field) updatePathMap(fe, Xe);
  }

  static protected Receiver checkTarget(LabelChecker lc, Field fe) throws SemanticException {
    return JifFieldExt.checkTarget(lc, fe);
  }
}
