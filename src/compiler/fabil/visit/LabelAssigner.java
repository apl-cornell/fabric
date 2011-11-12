package fabil.visit;

import polyglot.ast.Node;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.types.SemanticException;
import polyglot.visit.ContextVisitor;
import fabil.ExtensionInfo;
import fabil.ast.FabILNodeFactory;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;

/**
 * Assigns object locations to all <code>new</code> expressions.
 */
public class LabelAssigner extends ContextVisitor {
  
  private QQ qq;

  public LabelAssigner(Job job, ExtensionInfo extInfo) {
    super(job, extInfo.typeSystem(), extInfo.nodeFactory());
    this.qq = new QQ(extInfo);
  }
  
  public QQ qq() {
    return qq;
  }
  
  @Override
  public FabILNodeFactory nodeFactory() {
    return (FabILNodeFactory) super.nodeFactory();
  }

  @Override
  public FabILTypeSystem typeSystem() {
    return (FabILTypeSystem) super.typeSystem();
  }

  @Override
  protected Node leaveCall(Node n) throws SemanticException {
    return ext(n).assignLabels(this);
  }

  private FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

}
