package fabil.extension;

import polyglot.ast.*;
import polyglot.qq.QQ;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import fabil.types.FabILTypeSystem;
import fabil.visit.ProxyRewriter;

public class NewArrayExt_c extends AnnotatedExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#rewriteProxiesImpl(fabil.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    QQ qq = pr.qq();
    NewArray newArray = node();

    // Only rewrite if we have a Fabric array.
    FabILTypeSystem ts = pr.typeSystem();
    if (!ts.isFabricArray(newArray.type())) return newArray;
    
    // XXX Replace with a real label.
    Expr label = qq.parseExpr("null");

    if (newArray.dims().size() > 1)
      throw new InternalCompilerError("Multidimensional arrays not supported.");

    if (newArray.dims().size() < 1)
      throw new InternalCompilerError("Missing array dimension");

    Expr size = (Expr) newArray.dims().get(0);

    Type baseType = newArray.type().toArray().base();
    Type arrayImplType = ts.fArrayImplOf(baseType);
    Type arrayType = ts.fArrayOf(baseType);
    String typeArg = "";
    if (baseType.isReference()) {
      typeArg = baseType.toString();
      if (ts.isPureFabricType(baseType)) typeArg += ".$Proxy";
      typeArg += ".class, ";
    }
    return qq.parseExpr("(%T) new %T(%E, %E, " + typeArg + "%E).$getProxy()",
        arrayType, arrayImplType, location(), label, size);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabil.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabil.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter rewriter) {
    NewArray newArray = node();

    // Only rewrite if we have a Fabric array.
    FabILTypeSystem ts = rewriter.typeSystem();
    if (!ts.isFabricArray(newArray.type())) return null;

    if (newArray.init() != null) {
      ArrayInit init = newArray.init();
      init = ((ArrayInitExt_c) init.ext()).location(location());
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
  public NewArray node() {
    return (NewArray) super.node();
  }

}
