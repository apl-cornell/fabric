package fabil.visit;

import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;
import fabil.ast.FabILNodeFactory;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;

/**
 * A pass that collects initializers for static fields and moves them into an
 * atomic static initializer.
 */
public class StaticInitializerCollector extends NodeVisitor {

  protected FabILNodeFactory nf;
  protected FabILTypeSystem ts;

  public StaticInitializerCollector(FabILNodeFactory nf, FabILTypeSystem ts) {
    this.nf = nf;
    this.ts = ts;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).collectStaticInitializers(this);
  }

  private FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  public FabILNodeFactory nodeFactory() {
    return nf;
  }

  public FabILTypeSystem typeSystem() {
    return ts;
  }
}
