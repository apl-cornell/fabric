package fabil.visit;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.types.ParsedClassType;
import polyglot.util.InternalCompilerError;
import polyglot.visit.NodeVisitor;
import codebases.types.CodebaseClassType;
import fabil.ExtensionInfo;
import fabil.ast.FabILNodeFactory;
import fabil.ast.ProviderLabel;
import fabil.types.FabILTypeSystem;
import fabric.common.NSUtil;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.WrappedJavaInlineable;
import fabric.util.Map;
import fabric.worker.Worker;

/**
 * Rewrites <code>ProviderLabel</code> AST nodes into
 * <code>get$$updateLabel()</code> calls on class object proxies.
 */
public class ProviderRewriter extends NodeVisitor {

  protected QQ qq;
  protected FabILNodeFactory nf;
  protected FabILTypeSystem ts;
  protected ExtensionInfo extInfo;
  public ProviderRewriter(ExtensionInfo extInfo) {
    this.extInfo = extInfo;
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
    this.ts = extInfo.typeSystem();
  }
  public QQ qq() {
    return qq;
  }
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (!(n instanceof ProviderLabel)) {
      return n;
    }
    ProviderLabel pl = (ProviderLabel) n;
    CodebaseClassType ct = (CodebaseClassType) pl.typeNode().type();    
    if (ct instanceof ParsedClassType && Worker.isInitialized()) {
      Codebase cb = ts.codebaseFromNS(ct.canonicalNamespace());
      // Get a handle to the FClass object.
      Map classes = cb.getClasses();
      Object obj = classes.get(WrappedJavaInlineable.$wrap(ct.fullName()));
      if (obj == null)
        throw new InternalCompilerError("Class not found in its own namespace: " + NSUtil.namespace(cb));
      
      FClass fclass =
          (FClass) fabric.lang.Object._Proxy.$getProxy(classes
              .get(WrappedJavaInlineable.$wrap(ct.fullName())));

      // Convert to an OID.
      String storeName = fclass.$getStore().name();
      long onum = fclass.$getOnum();
      
      // Emit a call to get$$updateLabel() on an appropriate proxy object.
      Expr store =
          qq.parseExpr("fabric.worker.Worker.getWorker().getStore(\"" + storeName
              + "\")");
      Expr fclassProxy =
          qq.parseExpr("new fabric.lang.FClass._Proxy(%E, " + onum + ")", store);
      return qq.parseExpr("%E.get$$updateLabel()", fclassProxy);
    }
    else {
      return qq.parseExpr("fabric.lang.security.LabelUtil._Impl.toLabel(" +
      		"fabric.worker.Worker.getWorker().getLocalStore()," +
      		"fabric.lang.security.LabelUtil._Impl.topInteg())");
    }
  }
  
}
