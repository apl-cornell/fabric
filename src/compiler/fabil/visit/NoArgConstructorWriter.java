package fabil.visit;

import codebases.visit.CBTypeBuilder;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.visit.NodeVisitor;
import polyglot.visit.TypeBuilder;
import polyglot.visit.TypeChecker;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;
import fabil.ExtensionInfo;

/**
 * Adds in a no argument constructor to the class for use by the computation
 * warranties system.
 */
public class NoArgConstructorWriter extends NodeVisitor {
  protected QQ qq;
  protected NodeFactory nf;
  protected FabILTypeSystem ts;
  private TypeChecker tc;
  private TypeBuilder tb;

  public NoArgConstructorWriter(Job job) {
    this.qq = new QQ(job.extensionInfo());
    this.nf = job.extensionInfo().nodeFactory();
    this.ts = ((ExtensionInfo) job.extensionInfo()).typeSystem();
    this.tc = new TypeChecker(job, this.ts, this.nf);
    this.tb = new CBTypeBuilder(job, this.ts, this.nf);
  }

  protected FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  public QQ qq() {
    return qq;
  }

  public NodeFactory nodeFactory() {
    return nf;
  }

  public FabILTypeSystem typeSystem() {
    return ts;
  }

  public TypeChecker typeChecker() {
    return tc;
  }

  public TypeBuilder typeBuilder() {
    return tb;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).addNoArgumentConstructor(this);
  }
}
