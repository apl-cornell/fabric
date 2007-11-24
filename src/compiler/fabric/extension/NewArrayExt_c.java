package fabric.extension;

import polyglot.ast.*;
import polyglot.qq.QQ;
import polyglot.types.Type;
import polyglot.util.InternalCompilerError;
import fabric.types.FabricTypeSystem;
import fabric.visit.ProxyRewriter;

public class NewArrayExt_c extends LocatedExt_c {

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesImpl(ProxyRewriter pr) {
    QQ qq = pr.qq();
    NewArray newArray = node();

    // Only rewrite if we have an array of primitives or an array of
    // fabric.util.Objects.
    Type baseType = newArray.baseType().type();
    FabricTypeSystem ts = pr.typeSystem();
    if (!baseType.isPrimitive() && !ts.isFabric(baseType)) return newArray;

    if (location == null) location = qq.parseExpr("$getCore()");

    if (newArray.dims().size() > 1)
      throw new InternalCompilerError("Multidimensional arrays not supported.");

    if (newArray.dims().size() < 1)
      throw new InternalCompilerError("Missing array dimension");

    Expr size = (Expr) newArray.dims().get(0);

    Type arrayImplType = ts.fArrayImplOf(baseType);
    Type arrayType = ts.fArrayOf(baseType);
    return qq.parseExpr("(%T) new %T(%E, %E).$getProxy()", arrayType,
        arrayImplType, location, size);
  }

  /*
   * (non-Javadoc)
   * 
   * @see fabric.extension.ExprExt_c#rewriteProxiesOverrideImpl(fabric.visit.ProxyRewriter)
   */
  @Override
  public Expr rewriteProxiesOverrideImpl(ProxyRewriter rewriter) {
    NewArray newArray = node();

    // Only rewrite if we have an array of primitives or an array of
    // fabric.util.Objects.
    Type base = newArray.baseType().type();
    FabricTypeSystem ts = rewriter.typeSystem();
    if (!base.isPrimitive() && !ts.isFabric(base)) {
      return null;
    }

    if (newArray.init() != null) {
      ArrayInit init = newArray.init();
      init = ((ArrayInitExt_c) init.ext()).location(location);
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
