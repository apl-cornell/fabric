package fabil.extension;

import java.util.Collections;

import fabil.ast.Annotated;
import fabil.ast.FabILNodeFactory;
import fabil.ast.FabricArrayInit;
import fabil.ast.NewFabricArray;
import fabil.types.FabILTypeSystem;
import fabil.visit.LabelAssigner;
import fabil.visit.ProxyRewriter;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.Receiver;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.Flags;
import polyglot.types.MethodInstance;
import polyglot.types.ReferenceType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public class NewFabricArrayExt_c extends AnnotatedExt_c {

  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    QQ qq = pr.qq();
    NewFabricArray newArray = node();

    // Only rewrite if we have a Fabric array.
    FabILTypeSystem ts = pr.typeSystem();
    if (!ts.isFabricArray(newArray.type())) return newArray;

    if (newArray.numDims() > 1)
      throw new InternalCompilerError("Multidimensional arrays not supported.");

    if (newArray.dims().size() < 1)
      throw new InternalCompilerError("Missing array dimension");

    Expr size = newArray.dims().get(0);

    Type baseType = newArray.type().toArray().base();
    Type arrayImplType = ts.fabricRuntimeArrayImplOf(baseType);
    Type arrayType = ts.fabricRuntimeArrayOf(baseType);
    String typeArg = "";
    if (baseType.isReference()) {
      if (ts.isPureFabricType(baseType))
        typeArg = baseType.toString();
      else typeArg = "fabric.lang.Object";
      typeArg += "._Proxy.class, ";
    }
    return qq.parseExpr(
        "(%T) new %T(%E, %E, %E, " + typeArg + "%E).$getProxy()", arrayType,
        arrayImplType, newArray.location(), newArray.updateLabel(),
        newArray.accessPolicy(), size);
  }

  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter rewriter) {
    NewFabricArray newArray = node();

    // Only rewrite if we have a Fabric array.
    FabILTypeSystem ts = rewriter.typeSystem();
    if (!ts.isFabricArray(newArray.type())) return null;

    if (newArray.init() != null) {
      FabricArrayInit init = newArray.init().location(newArray.location());
      newArray = newArray.init(init);

      // Translation of initializer will be the array itself.
      return newArray.visitChild(init, rewriter);
    }

    return null;
  }

  @Override
  public NewFabricArray node() {
    return (NewFabricArray) super.node();
  }

  @Override
  public Annotated assignLabels(LabelAssigner la) throws SemanticException {
    NewFabricArray expr = node();
    expr = assignUpdateLabel(la, expr);
    expr = assignAccessPolicy(la, expr);
    return expr;
  }

  private NewFabricArray assignUpdateLabel(LabelAssigner la,
      NewFabricArray expr) throws SemanticException {
    if (expr.updateLabel() != null) return expr;

    FabILNodeFactory nf = la.nodeFactory();
    FabILTypeSystem ts = la.typeSystem();
    QQ qq = la.qq();

    if (!ts.isFabricReference(expr.type())) return expr;

    // Need a label. Use null by default for principal objects. The Principal
    // constructor will fill in the appropriate label.
    if (ts.isPrincipalClass(expr.type())) {
      return expr.updateLabel(qq.parseExpr("null").type(ts.Null()));
    }

    // Need a label. By default, we use the same label as the context.
    Context context = la.context();
    ClassType currentClass = context.currentClass();
    if (!ts.isFabricReference(currentClass)) {
      throw new SemanticException("Missing label", expr.position());
    }

    Receiver receiver;
    if (context.inStaticContext()) {
      receiver =
          nf.CanonicalTypeNode(Position.compilerGenerated(), currentClass);
    } else {
      receiver = qq.parseExpr("this").type(currentClass);
    }

    Position pos = Position.compilerGenerated();
    Call defaultLabel = nf.Call(pos, receiver, nf.Id(pos, "get$$updateLabel"));

    Flags flags = Flags.NONE;
    if (context.inStaticContext()) flags = Flags.STATIC;

    MethodInstance lmi = ts.methodInstance(pos, currentClass, flags, ts.Label(),
        "get$$updateLabel", Collections.<Type> emptyList(),
        Collections.<Type> emptyList());
    defaultLabel = (Call) defaultLabel.type(ts.Label());
    defaultLabel = defaultLabel.methodInstance(lmi);
    return expr.updateLabel(defaultLabel);
  }

  private NewFabricArray assignAccessPolicy(LabelAssigner la,
      NewFabricArray expr) {
    if (expr.accessPolicy() != null) return expr;

    FabILTypeSystem ts = la.typeSystem();
    QQ qq = la.qq();

    if (!ts.isFabricReference(expr.type())) return expr;

    // Need a policy. Use the object label's confidentiality policy by default.
    Expr label = expr.updateLabel();
    Expr policy = qq.parseExpr("%E.confPolicy()", label).type(ts.ConfPolicy());

    MethodInstance mi = ts.methodInstance(Position.compilerGenerated(),
        (ReferenceType) ts.Label(), Flags.PUBLIC, ts.ConfPolicy(), "confPolicy",
        Collections.<Type> emptyList(), Collections.<Type> emptyList());
    policy = ((Call) policy).methodInstance(mi);
    return expr.accessPolicy(policy);
  }

}
