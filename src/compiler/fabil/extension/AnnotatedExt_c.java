package fabil.extension;

import java.util.Collections;

import polyglot.ast.Call;
import polyglot.ast.Field;
import polyglot.ast.Receiver;
import polyglot.qq.QQ;
import polyglot.types.*;
import polyglot.util.Position;
import fabil.ast.Annotated;
import fabil.ast.FabILNodeFactory;
import fabil.types.FabILTypeSystem;
import fabil.visit.LabelAssigner;
import fabil.visit.LocationAssigner;

/**
 * Provides common functionality to the New and NewArray for managing label and
 * location fields.
 */
public abstract class AnnotatedExt_c extends ExprExt_c {
  @Override
  public Annotated assignLabels(LabelAssigner la) throws SemanticException {
    Annotated expr = node();
    if (expr.label() != null) return expr;

    FabILNodeFactory nf = la.nodeFactory();
    FabILTypeSystem ts = la.typeSystem();
    QQ qq = la.qq();

    if (!ts.isFabricReference(expr.type())) return expr;

    // Need a label. Use null by default for principal objects. The Principal
    // constructor will fill in the appropriate label.
    if (ts.isPrincipalClass(expr.type())) {
      return (Annotated) expr.label(qq.parseExpr("null")).type(ts.Null());
    }

    // Need a label. By default, we use the same label as the context.
    Context context = la.context();
    ClassType currentClass = context.currentClass();
    if (!ts.isFabricReference(currentClass)) {
      throw new SemanticException("Missing label", expr.position());
    }

    Position pos = Position.compilerGenerated();
    if (context.inStaticContext()) {
      Receiver receiver =
          nf.CanonicalTypeNode(Position.compilerGenerated(), currentClass);
      Field defaultLabel = nf.Field(pos, receiver, nf.Id(pos, "label"));
      defaultLabel = (Field) defaultLabel.type(ts.Label());
      defaultLabel =
          defaultLabel.fieldInstance(ts.fieldInstance(pos, currentClass,
              Flags.STATIC, ts.Label(), "label"));
      return expr.label(defaultLabel);
    }
    
    Receiver receiver = qq.parseExpr("this").type(currentClass);
    Call defaultLabel = nf.Call(pos, receiver, nf.Id(pos, "get$label"));

    Flags flags = Flags.NONE;
    MethodInstance mi =
        ts.methodInstance(pos, currentClass, flags, ts.Label(), "get$label",
            Collections.emptyList(), Collections.emptyList());
    defaultLabel = defaultLabel.methodInstance(mi);

    return expr.label(defaultLabel);
  }

  @Override
  public Annotated assignLocations(LocationAssigner la)
      throws SemanticException {
    Annotated expr = node();
    if (expr.location() != null) return expr;

    FabILNodeFactory nf = la.nodeFactory();
    FabILTypeSystem ts = la.typeSystem();
    QQ qq = la.qq();

    if (!ts.isFabricReference(expr.type())) return expr;

    // Need a location. By default, we colocate with the context.
    Context context = la.context();
    ClassType currentClass = context.currentClass();
    if (!ts.isFabricReference(currentClass)) {
      throw new SemanticException("Missing location", expr.position());
    }

    Receiver receiver;
    if (context.inStaticContext()) {
      receiver =
          nf.CanonicalTypeNode(Position.compilerGenerated(), currentClass);
    } else {
      receiver = qq.parseExpr("this").type(currentClass);
    }

    Position pos = Position.compilerGenerated();
    Call defaultLocation = nf.Call(pos, receiver, nf.Id(pos, "$getCore"));

    Flags flags = Flags.NONE;
    if (context.inStaticContext()) flags = Flags.STATIC;

    MethodInstance mi =
        ts.methodInstance(pos, currentClass, flags, ts.Label(), "$getCore",
            Collections.emptyList(), Collections.emptyList());
    defaultLocation = defaultLocation.methodInstance(mi);
    return expr.location(defaultLocation);
  }

  @Override
  public Annotated node() {
    return (Annotated) super.node();
  }
}
