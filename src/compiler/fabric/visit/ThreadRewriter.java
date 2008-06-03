package fabric.visit;

import fabric.ExtensionInfo;
import fabric.ast.FabricNodeFactory;
import fabric.extension.FabricExt;
import fabric.types.FabricTypeSystem;
import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.visit.NodeVisitor;

/**
 * Rewrites threads, hooking them into the client transaction manager.
 */
public class ThreadRewriter extends NodeVisitor {
  private QQ qq;
  private FabricTypeSystem ts;
  private FabricNodeFactory nf;

  public ThreadRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.ts = extInfo.typeSystem();
    this.nf = extInfo.nodeFactory();
  }

  public FabricNodeFactory nodeFactory() {
    return nf;
  }

  public FabricTypeSystem typeSystem() {
    return ts;
  }

  public QQ qq() {
    return qq;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).rewriteThreads(this);
  }

  private FabricExt ext(Node n) {
    return (FabricExt) n.ext();
  }

  /**
   * Determines whether the given ClassType should be rewritten with hooks into
   * the client transaction manager.
   */
  public boolean shouldRewrite(ClassType type) {
    // Should only rewrite Threads.
    if (!ts.isThread(type)) return false;

    // Check all supertypes. If any were compiled by fabc, then no need to
    // rewrite.
    ClassType thread = ts.Thread();
    do {
      type = (ClassType) type.superType();
      if (ts.isCompiledByFabc(type)) return false;
    } while (!type.equals(thread));

    return true;
  }
}
