package fabil.extension;

import polyglot.ast.Expr;
import polyglot.ast.NodeFactory;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import fabil.types.FabILTypeSystem;
import fabil.visit.LabelAssigner;
import fabil.visit.LocationAssigner;

/**
 * Provides common functionality to the New and NewArray for managing label and
 * location fields.
 */
public abstract class AnnotatedExt_c extends ExprExt_c {
  private Expr label;
  private Expr location;

  public Expr label() {
    return label;
  }
  
  public Expr location() {
    return location;
  }
  
  public Expr label(Expr label) {
    AnnotatedExt_c result = (AnnotatedExt_c) copy();
    result.label = label;
    
    return (Expr) node().ext(result);
  }

  public Expr location(Expr location) {
    AnnotatedExt_c result = (AnnotatedExt_c) copy();
    result.location = location;

    return (Expr) node().ext(result);
  }
  
  @Override
  public Expr assignLabels(LabelAssigner la) throws SemanticException {
    Expr expr = node();
    if (label != null) return expr;
    
    NodeFactory nf = la.nodeFactory();
    QQ qq = la.qq();
    
    // Need a label. Use null by default for principal objects. The Principal
    // constructor will fill in the appropriate label.
    FabILTypeSystem ts = la.typeSystem();
    if (ts.isPrincipalClass(expr.type())) {
      return label(qq.parseExpr("null"));
    }
    
    // Need a label. By default, we use the same label as the context.
    Context context = la.context();
    ClassType currentClass = context.currentClass();
    if (!ts.isFabricReference(currentClass)) {
      throw new SemanticException("Missing label", expr.position());
    }
    
    Expr defaultLabel;
    if (context.inStaticContext()) {
      defaultLabel =
          qq.parseExpr("%T.$Static.$Proxy.$instance.get$label()", currentClass);
    } else {
      defaultLabel =
        nf.Call(expr.position(), nf.Id(Position.compilerGenerated(),
              "get$label"));
    }
    
    return label(defaultLabel);
  }

  @Override
  public Expr assignLocations(LocationAssigner la) throws SemanticException {
    Expr expr = node();
    if (location != null) return expr;
    
    NodeFactory nf = la.nodeFactory();
    FabILTypeSystem ts = la.typeSystem();
    QQ qq = la.qq();

    // Need a location. By default, we colocate with the context.
    Context context = la.context();
    ClassType currentClass = context.currentClass();
    if (!ts.isFabricReference(currentClass)) {
      throw new SemanticException("Missing location", expr.position());
    }
    
    Expr defaultLocation;
    if (context.inStaticContext()) {
      defaultLocation = qq.parseExpr("%T.$Static.$Proxy.$instance.$getCore()",
          currentClass);
    } else {
      defaultLocation =
          nf.Call(expr.position(), nf.Id(Position.compilerGenerated(),
              "$getCore"));
    }
    
    return location(defaultLocation);
  }
}
