package fabric.visit;

import java.util.Collections;

import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.types.*;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import polyglot.visit.NodeVisitor;
import fabric.ast.FabricNodeFactory;
import fabric.types.FabricTypeSystem;

/**
 * Traverses the AST and wraps/unwraps JavaInlineables as necessary.
 */
public class InlineableWrapper extends AscriptionVisitor {

  protected FabricNodeFactory nf;
  protected FabricTypeSystem ts;

  public InlineableWrapper(Job job, FabricTypeSystem ts, FabricNodeFactory nf) {
    super(job, ts, nf);
    this.nf = nf;
    this.ts = ts;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.ErrorHandlingVisitor#leaveCall(polyglot.ast.Node,
   *      polyglot.ast.Node, polyglot.ast.Node, polyglot.visit.NodeVisitor)
   */
  @Override
  protected Node leaveCall(Node parent, Node old, Node n, NodeVisitor v)
      throws SemanticException {
    // Don't rewrite lvalues.
    if (parent instanceof Assign && ((Assign) parent).left() == old) return n;
    return super.leaveCall(parent, old, n, v);
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.visit.AscriptionVisitor#ascribe(polyglot.ast.Expr,
   *      polyglot.types.Type)
   */
  @SuppressWarnings("unchecked")
  @Override
  public Expr ascribe(Expr e, Type toType) {
    Position CG = Position.compilerGenerated();
    Type fromType = e.type();

    if (!fromType.isReference() || !toType.isReference()) return e;

    // Classify fromType and toType into one of three mutually exclusive
    // classes: Java Inlineable (subtype of fabric.lang.JavaInlineable), Fabric
    // Object (other subtypes of fabric.lang.Object), and Java Object (other
    // subtypes of java.lang.Object).
    boolean toInlineable = false, toFabric = false, toJava = false;
    boolean fromInlineable = false, fromFabric = false, fromJava = false;

    if (ts.isJavaInlineable(toType))
      toInlineable = true;
    else if (ts.isFabricReference(toType))
      toFabric = true;
    else toJava = true;

    if (ts.isJavaInlineable(fromType)
        // Stuff coming out of an array of inlineables will already be wrapped. 
        && !(e instanceof ArrayAccess))
      fromInlineable = true;
    else if (ts.isFabricReference(fromType))
      fromFabric = true;
    else fromJava = true;

    // No need to do anything if toType and fromType are in the same class.
    if (toInlineable == fromInlineable && toFabric == fromFabric
        && toJava == fromJava) return e;

    // No need to do anything if converting inlineable -> Java or vice versa.
    if (fromInlineable && toJava || fromJava && toInlineable) return e;

    ClassType wrappedJavaInlineable = ts.WrappedJavaInlineable();

    // If converting to Fabric, wrap.
    if (toFabric) {
      Call call =
          nf.Call(CG, nf.CanonicalTypeNode(CG, wrappedJavaInlineable), nf.Id(
              CG, "$wrap"), e);
      call = (Call) call.type(wrappedJavaInlineable);

      MethodInstance mi =
          ts.methodInstance(CG, wrappedJavaInlineable, Flags.PUBLIC.set(
              Flags.STATIC).set(Flags.FINAL), wrappedJavaInlineable, "$wrap",
              Collections.singletonList(ts.Object()), Collections.emptyList());
      call = (Call) call.type(toType);
      return call.methodInstance(mi);
    }

    // Must be converting from Fabric. Unwrap.
    Call call =
        nf.Call(CG, nf.CanonicalTypeNode(CG, wrappedJavaInlineable), nf.Id(CG,
            "$unwrap"), e);
    call = (Call) call.type(ts.Object());

    MethodInstance mi =
        ts.methodInstance(CG, wrappedJavaInlineable, Flags.PUBLIC.set(
            Flags.STATIC).set(Flags.FINAL), ts.Object(), "$unwrap", Collections
            .singletonList(ts.FObject()), Collections.emptyList());

    Cast result =
        nf.Cast(CG, nf.CanonicalTypeNode(CG, toType), call.methodInstance(mi));
    result = (Cast) result.type(toType);
    return result;
  }
}
