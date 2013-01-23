package fabil.visit;

import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import polyglot.types.PrimitiveType;
import polyglot.types.SemanticException;
import polyglot.visit.NodeVisitor;
import fabil.ast.FabILNodeFactory;
import fabil.ExtensionInfo;
import fabil.extension.FabILExt;
import fabil.types.FabILTypeSystem;

/**
 * NodeVisitor for rewriting methods that are marked to be memoized.
 */
public class MemoizedMethodRewriter extends NodeVisitor {
  protected QQ qq;
  protected FabILNodeFactory nf;
  protected FabILTypeSystem ts;

  public MemoizedMethodRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
    this.ts = extInfo.typeSystem();
  }

  protected FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    return ext(n).rewriteMemoizedMethods(this);
  }

  /**
   * Utility method for figuring out the correct cast for grabbing a value from
   * the memoization cache.
   */
  public TypeNode methodReturnType(MethodDecl md) {
    TypeNode returnType = md.returnType();
    if (returnType.type().isPrimitive()) {
      PrimitiveType p = returnType.type().toPrimitive();
      try {
        return returnType.type(ts.typeForName(p.wrapperTypeString(ts)));
      } catch (SemanticException e) {
        System.out.println("Couldn't find wrapper type!");
      }
    }
    return returnType;
  }

  /**
   * @return the qq
   */
  public QQ qq() {
    return qq;
  }

  public FabILNodeFactory nodeFactory() {
    return nf;
  }

  public FabILTypeSystem typeSystem() {
    return ts;
  }
}
