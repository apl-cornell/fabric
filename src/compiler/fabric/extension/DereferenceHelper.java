package fabric.extension;

import fabric.types.FabricContext;
import fabric.types.FabricPathMap;
import fabric.types.FabricReferenceType;
import fabric.types.FabricTypeSystem;

import jif.ast.JifExt_c;
import jif.types.ConstraintMessage;
import jif.types.LabelConstraint;
import jif.types.NamedLabel;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathUninterpreted;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import jif.visit.LabelChecker;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NullLit;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * A class containing code for checking access labels on dereferences.
 */
public class DereferenceHelper {

  /**
   * Adds constraints to lc reflecting the fetch side effects of a dereference
   */
  public static Node checkDereference(Node n, final Receiver ref, LabelChecker lc,
      Position pos) throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

    // All classes referred to in executing code have already been fetched
    // during typechecking. Thus static dispatches do not cause fetches
    if (ref instanceof TypeNode) return n;

    // this and super are known to be resident when a method is executing. Thus
    // they do not cause side effects when dereferenced.
    if (ref == null || ref instanceof Special) return n;

    if (!(ref instanceof Expr))
      throw new InternalCompilerError("unexpected receiver type");

    // get the type of the target and check accesses to the object
    FabricReferenceType refType = (FabricReferenceType) ts.unlabel(ref.type());
    return checkAccess(n, (Expr) ref, refType, lc, pos);
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
  public static Node checkAccess(Node n, final Expr ref,
      final FabricReferenceType targetType, LabelChecker lc, Position pos)
          throws SemanticException {
    FabricTypeSystem ts = (FabricTypeSystem) lc.typeSystem();

    // if the ref is a null literal, then the access label check is not required
    if (ref instanceof NullLit) return n;

    // Dereferencing a reference with a transient type does not result in a fetch unless
    // 1) it is java.lang.Object (could refer to a fabric.lang.Object)
    // 2) it is an Interface (could be implemented by a fabric.lang.Object)
    if (ts.isTransient(ref.type())
        && !ref.type().equals(ts.Object())
        && !(ref.type().isClass() && ref.type().toClass().flags().isInterface()))
      return n;

    // check that the pc and ref label can flow to the access label
    FabricContext A = (FabricContext) lc.context();
    Label objLabel = JifExt_c.getPathMap(ref).NV();
    Label pc = ts.join(JifExt_c.getPathMap(ref).N(), A.currentCodePCBound());
    AccessPath storeap = ts.storeAccessPathFor(ref, A);
    if (ts.descendsFrom(targetType, ts.PrincipalClass())) {
      if (ts.isFinalAccessExpr(ref)) {
        // this.store >= this holds true for all principals
        A.addActsFor(ts.dynamicPrincipal(pos, storeap),
            ts.dynamicPrincipal(pos, ts.exprToAccessPath(ref, A)));
      } else {
        // ref is not a final access path, so make an uninterpreted path instead
        A.addActsFor(ts.dynamicPrincipal(pos, storeap),
            ts.dynamicPrincipal(pos, new AccessPathUninterpreted(ref, pos)));
      }
    }

    // get the access label of the type
    final ConfPolicy accessPolicy = targetType.accessPolicy();
    final Label accessLabel = ts.toLabel(accessPolicy);
    final Label instantiated =
        StoreInstantiator.instantiate(accessLabel, A, ref, targetType,
            objLabel, storeap);
    NamedLabel accessPolLabel = new NamedLabel("access label",
          "the access label of the object referenced",
          ts.join(instantiated,
            ts.pairLabel(pos, ts.bottomConfPolicy(pos), ts.topIntegPolicy(pos))));

    lc.constrain(new NamedLabel("reference label", objLabel),
        LabelConstraint.LEQ, accessPolLabel,
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

    // Get join of update labels of previous accesses
    FabricPathMap Xt = (FabricPathMap) JifExt_c.getPathMap(ref);
    NamedLabel accessedLabel = new NamedLabel("accessed label",
        "the join of the update labels of previously accessed objects",
        ts.join(A.accessedLabelBound(),
                ts.join(A.accessedLabel(),
                        Xt.A())));

    // Check that this won't cause abort leaks.
    lc.constrain(accessedLabel, LabelConstraint.LEQ, accessPolLabel,
        A.labelEnv(), pos, new ConstraintMessage() {
      @Override
      public String msg() {
        return "The objects accessed during this method could leak "
             + "information about preceding accesses to the stores "
             + "of previously accessed objects, which have "
             + "access lower bounded by the method's begin access "
             + "label.";
      }
    });
    
    // Fold in this obj's update label into the A FabricPath.
    FabricPathMap X = (FabricPathMap) JifExt_c.getPathMap(n);
    Label newA = ts.join(X.A(), A.accessedLabel());
    n = JifExt_c.updatePathMap(n, X.A(newA));

    // Early check of end access bound to get better location reporting.
    NamedLabel newANamed = new NamedLabel("accessed label",
        "the join of the update labels of previously accessed objects",
        newA);

    NamedLabel endConfBoundLabel = new NamedLabel("end access label",
        "the upper bound on the update labels of objects accessed in this method",
        ts.join(A.endAccessBound(),
          ts.pairLabel(pos, ts.bottomConfPolicy(pos), ts.topIntegPolicy(pos))));

    lc.constrain(newANamed, LabelConstraint.LEQ, endConfBoundLabel,
        A.labelEnv(), pos, new ConstraintMessage() {
      @Override
      public String msg() {
        return "This method makes more restricted accesses than the ending "
             + "access label allows.";
      }
    });

    return n;
  }
}
