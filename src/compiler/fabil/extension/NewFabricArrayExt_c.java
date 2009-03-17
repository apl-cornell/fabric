package fabil.extension;

import polyglot.ast.*;
import polyglot.qq.QQ;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import fabil.ast.FabricArrayInit;
import fabil.ast.NewFabricArray;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class NewFabricArrayExt_c extends AnnotatedExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#rewriteProxiesImpl(fabil.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    QQ qq = pr.qq();
    NewFabricArray newArray = node();

    // Only rewrite if we have a Fabric array.
    FabILTypeSystem ts = pr.typeSystem();
    if (!ts.isFabricArray(newArray.type())) return newArray;

    if (newArray.dims().size() > 1)
      throw new InternalCompilerError("Multidimensional arrays not supported.");

    if (newArray.dims().size() < 1)
      throw new InternalCompilerError("Missing array dimension");

    Expr size = (Expr) newArray.dims().get(0);

    Type baseType = newArray.type().toArray().base();
    Type arrayImplType = ts.fabricRuntimeArrayImplOf(baseType);
    Type arrayType = ts.fabricRuntimeArrayOf(baseType);
    String typeArg = "";
    if (baseType.isReference()) {
      if (ts.isPureFabricType(baseType)) typeArg = baseType.toString();
      else typeArg = "fabric.lang.Object";
      typeArg += "._Proxy.class, ";
    }
    return qq.parseExpr("(%T) new %T(%E, %E, " + typeArg + "%E).$getProxy()",
        arrayType, arrayImplType, newArray.location(), newArray.label(), size);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabil.visit.ProxyRewriter)
   */
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
      return (Expr) newArray.visitChild(init, rewriter);
    }

    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see polyglot.ast.Ext_c#node()
   */
  @Override
  public NewFabricArray node() {
    return (NewFabricArray) super.node();
  }

}
