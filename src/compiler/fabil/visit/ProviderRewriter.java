package fabil.visit;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.visit.NodeVisitor;
import fabil.ExtensionInfo;
import fabil.ast.FabILNodeFactory;
import fabil.ast.ProviderLabel;
import fabil.types.CodebaseClassType;
import fabil.types.FabILTypeSystem;
import fabric.lang.Codebase;
import fabric.lang.FClass;
import fabric.lang.WrappedJavaInlineable;
import fabric.util.Map;

/**
 * Rewrites <code>ProviderLaber</code> AST nodes into <code>get$label()</code>
 * calls on class object proxies.
 */
public class ProviderRewriter extends NodeVisitor {

  protected QQ qq;
  protected FabILNodeFactory nf;
  protected FabILTypeSystem ts;
  
  public ProviderRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
    this.ts = extInfo.typeSystem();
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (!(n instanceof ProviderLabel)) {
      return n;
    }
    
    ProviderLabel pl = (ProviderLabel) n;
    CodebaseClassType ct = (CodebaseClassType) pl.typeNode().type();
    
    // Get a handle to the FClass object.
    Codebase cb = ct.codebase();
    Map classes = cb.getClasses();
    FClass fclass = (FClass) classes.get(WrappedJavaInlineable.$wrap(ct.fullName()));
    
    // Convert to an OID.
    String storeName = fclass.$getStore().name();
    long onum = fclass.$getOnum();
    
    // Emit a call to get$label() on an appropriate proxy object.
    Expr store =
        qq.parseExpr("fabric.worker.Worker.getWorker().getStore(" + storeName
            + ")");
    Expr fclassProxy =
        qq.parseExpr("new fabric.lang.FClass._Proxy(%E, " + onum + ")", store);
    return qq.parseExpr("%E.get$label()", fclassProxy);
  }
  
}
