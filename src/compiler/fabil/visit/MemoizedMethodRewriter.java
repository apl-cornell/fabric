package fabil.visit;


import polyglot.ast.MethodDecl;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.qq.QQ;
import polyglot.util.SimpleCodeWriter;
import polyglot.visit.NodeVisitor;
import fabil.ExtensionInfo;
import fabil.extension.FabILExt;
import fabil.types.FabILFlags;
import fabil.types.FabILTypeSystem;

public class MemoizedMethodRewriter extends NodeVisitor {
  protected QQ qq;
  protected NodeFactory nf;
  protected FabILTypeSystem ts;
  private boolean inMemoizedMethod = false;
  private TypeNode returnType = null;

  public MemoizedMethodRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
    this.ts = extInfo.typeSystem();
  }

  protected FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  @Override
  public NodeVisitor enter(Node n) {
    if (n instanceof MethodDecl) {
      MethodDecl m = (MethodDecl) n;
      if (m.flags().contains(FabILFlags.MEMOIZED)) {
        /* This currently assumes you don't have memoized methods nested */
        inMemoizedMethod = true;
        returnType = m.returnType();
      }
    }
    return this;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    Node newN = ext(n).rewriteMemoizedMethods(this);
    if (n instanceof MethodDecl) {
      inMemoizedMethod = false;
      returnType = null;
    }
    return newN;
  }

  public TypeNode methodReturnType() {
    return returnType;
  }

  public boolean rewriteReturns() {
    return inMemoizedMethod;
  }

  /**
   * @return the qq
   */
  public QQ qq() {
    return qq;
  }

  public NodeFactory nodeFactory() {
    return nf;
  }

  public FabILTypeSystem typeSystem() {
    return ts;
  }
}
