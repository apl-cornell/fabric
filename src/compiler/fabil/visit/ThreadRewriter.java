package fabil.visit;

import polyglot.ast.Node;
import polyglot.qq.QQ;
import polyglot.types.ClassType;
import polyglot.visit.NodeVisitor;
import fabil.ExtensionInfo;
import fabil.ast.FabILNodeFactory;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;

/**
 * Rewrites threads, hooking them into the worker transaction manager.
 */
public class ThreadRewriter extends NodeVisitor {
  private QQ qq;
  private FabILTypeSystem ts;
  private FabILNodeFactory nf;

  public ThreadRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.ts = extInfo.typeSystem();
    this.nf = extInfo.nodeFactory();
  }

  public FabILNodeFactory nodeFactory() {
    return nf;
  }

  public FabILTypeSystem typeSystem() {
    return ts;
  }

  public QQ qq() {
    return qq;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).rewriteThreads(this);
  }

  private FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  /**
   * Determines whether the given ClassType should be rewritten with hooks into
   * the worker transaction manager.
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
