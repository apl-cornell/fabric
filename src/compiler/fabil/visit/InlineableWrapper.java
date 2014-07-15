package fabil.visit;

import java.util.Collections;

import polyglot.ast.ArrayAccess;
import polyglot.ast.Assign;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.frontend.Job;
import polyglot.types.ClassType;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import polyglot.visit.NodeVisitor;
import fabil.ast.FabILNodeFactory;
import fabil.types.FabILTypeSystem;
import fabil.types.FabricArrayType;

/**
 * Traverses the AST and wraps/unwraps JavaInlineables as necessary.
 */
public class InlineableWrapper extends AscriptionVisitor {

  protected FabILNodeFactory nf;
  protected FabILTypeSystem ts;

  public InlineableWrapper(Job job, FabILTypeSystem ts, FabILNodeFactory nf) {
    super(job, ts, nf);
    this.nf = nf;
    this.ts = ts;
  }

  @Override
  protected Node leaveCall(Node parent, Node old, Node n, NodeVisitor v)
      throws SemanticException {
    // Don't rewrite lvalues.
    if (parent instanceof Assign && ((Assign) parent).left() == old) return n;

    // Don't rewrite arrays of array accesses.
    if (parent instanceof ArrayAccess && ((ArrayAccess) parent).array() == old)
      return n;
    return super.leaveCall(parent, old, n, v);
  }

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

    // Determine whether the expression e is indexing into a Fabric array.
    boolean isFabricArrayAccess =
        e instanceof ArrayAccess
        && ((ArrayAccess) e).array().type() instanceof FabricArrayType;

    if (ts.isJavaInlineable(fromType)
        // Stuff coming out of a Fabric array of inlineables will already be
        // wrapped.
        && !isFabricArrayAccess)
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
          nf.Call(CG, nf.CanonicalTypeNode(CG, wrappedJavaInlineable),
              nf.Id(CG, "$wrap"), e);
      call = (Call) call.type(wrappedJavaInlineable);

      MethodInstance mi =
          ts.methodInstance(CG, wrappedJavaInlineable,
              Flags.PUBLIC.set(Flags.STATIC).set(Flags.FINAL),
              wrappedJavaInlineable, "$wrap",
              Collections.singletonList((Type) ts.Object()),
              Collections.<Type> emptyList());
      call = (Call) call.type(toType);
      return call.methodInstance(mi);
    }

    // Must be converting from Fabric. Unwrap.
    Call call =
        nf.Call(CG, nf.CanonicalTypeNode(CG, wrappedJavaInlineable),
            nf.Id(CG, "$unwrap"), e);
    call = (Call) call.type(ts.Object());

    MethodInstance mi =
        ts.methodInstance(CG, wrappedJavaInlineable,
            Flags.PUBLIC.set(Flags.STATIC).set(Flags.FINAL), ts.Object(),
            "$unwrap", Collections.<Type> singletonList(ts.FObject()),
            Collections.<Type> emptyList());
    Expr result;
    if (!ts.String().equals(toType)) {
      result =
          nf.Cast(CG, nf.CanonicalTypeNode(CG, toType), call.methodInstance(mi));
      result = result.type(toType);
    } else {
      MethodInstance valueOfMI =
          ts.methodInstance(CG, ts.String(), Flags.PUBLIC.set(Flags.STATIC)
              .set(Flags.FINAL), ts.String(), "valueOf", Collections
              .<Type> singletonList(ts.Object()), Collections
              .<Type> emptyList());
      Call valueOf =
          nf.Call(CG, nf.CanonicalTypeNode(CG, ts.String()),
              nf.Id(CG, "valueOf"), call.methodInstance(mi));
      result = valueOf.methodInstance(valueOfMI).type(ts.String());
    }
    return result;
  }
}
