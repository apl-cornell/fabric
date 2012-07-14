package fabric.extension;

import fabric.types.FabricReferenceType;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Expr;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import jif.ast.JifInstantiator;
import jif.ast.Jif_c;
import jif.types.ConstraintMessage;
import jif.types.JifContext;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import jif.visit.LabelChecker;

/**
 * A class containing code for checking access labels on dereferences.
 */
public class DereferenceHelper {
  /**
   * Adds constraints to lc reflecting the fetch side effects of a dereference
   */
  public static void checkDereference(final Receiver ref, LabelChecker lc,
      Position pos) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

    // All classes referred to in executing code have already been fetched
    // during typechecking. Thus static dispatches do not cause fetches
    if (ref instanceof TypeNode) return;

    // this and super are known to be resident when a method is executing. Thus
    // they do not cause side effects when dereferenced.
    if (ref == null || ref instanceof Special) return;

    if (!(ref instanceof Expr))
      throw new InternalCompilerError("unexpected receiver type");

    // get the type of the target
    FabricReferenceType refType = (FabricReferenceType) ts.unlabel(ref.type());

    checkAccess((Expr) ref, refType, lc, pos);
  }

  /**
   * Adds constraints to lc to reflect that ref influences a fetch of something
   * of targetType. For example, the code
   * 
   * <pre>
   * C1 x;
   * (C2) x;
   * </pre>
   * 
   * will cause a flow from the label of x to the access label of C2;
   * checkAccess(x, C2, ...) will add constraints reflecting that. Assumes that
   * ref has already been label checked.
   */
  public static void checkAccess(final Expr ref,
      final FabricReferenceType targetType, LabelChecker lc, Position pos)
      throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

    // check that the pc and ref label can flow to the access label
    JifContext A = lc.context();
    Label objLabel = Jif_c.getPathMap(ref).NV();
    Label pc = ts.join(Jif_c.getPathMap(ref).N(), A.currentCodePCBound());

    // get the access label of the type
    final ConfPolicy accessPolicy = targetType.accessPolicy();
    final Label accessLabel = ts.toLabel(accessPolicy);
    final Label instantiated =
        JifInstantiator.instantiate(accessLabel, A, ref, targetType, objLabel);

    lc.constrain(new NamedLabel("reference label", objLabel),
        LabelConstraint.LEQ, new NamedLabel("access label", instantiated),
        A.labelEnv(), pos, new ConstraintMessage() {
          @Override
          public String msg() {
            return "Dereferencing " + ref + " may cause it to be "
                + "fetched, revealing too much information to its " + "store";
          }
        });
    lc.constrain(new NamedLabel("pc", pc), LabelConstraint.LEQ, new NamedLabel(
        "access label", instantiated), A.labelEnv(), pos,
        new ConstraintMessage() {
          @Override
          public String msg() {
            return "Dereferencing " + ref + " may cause it to be "
                + "fetched, revealing too much information to its " + "store";
          }
        });
  }
}
