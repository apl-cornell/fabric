package fabil.visit;

import polyglot.ast.Node;
import polyglot.frontend.Job;
import polyglot.qq.QQ;
import polyglot.visit.ContextVisitor;
import fabil.ExtensionInfo;
import fabil.extension.FabricExt;

/**
 * Assigns object locations to all <code>new</code> expressions.
 */
public class LocationAssigner extends ContextVisitor {
  
  private QQ qq;

  public LocationAssigner(Job job, ExtensionInfo extInfo) {
    super(job, extInfo.typeSystem(), extInfo.nodeFactory());
    this.qq = new QQ(extInfo);
  }
  
  public QQ qq() {
    return qq;
  }

  @Override
  protected Node leaveCall(Node n) {
    return ext(n).assignLocations(this);
  }

  private FabricExt ext(Node n) {
    return (FabricExt) n.ext();
  }

}
