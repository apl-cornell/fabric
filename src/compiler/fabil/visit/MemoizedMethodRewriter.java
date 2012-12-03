package fabil.visit;

import java.util.Stack;
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
import fabil.types.FabILFlags;
import fabil.types.FabILTypeSystem;

/**
 * NodeVisitor for rewriting methods that are marked to be memoized.
 */
public class MemoizedMethodRewriter extends NodeVisitor {
  protected QQ qq;
  protected FabILNodeFactory nf;
  protected FabILTypeSystem ts;
  /* Keep a stack of each memoized method's return type.  These might (?) be
   * nested if an anonymous class with a memoized method is created in the
   * source program.
   */
  private Stack<TypeNode> returnTypeStack;

  public MemoizedMethodRewriter(ExtensionInfo extInfo) {
    this.qq = new QQ(extInfo);
    this.nf = extInfo.nodeFactory();
    this.ts = extInfo.typeSystem();
    this.returnTypeStack = new Stack<TypeNode>();
  }

  protected FabILExt ext(Node n) {
    return (FabILExt) n.ext();
  }

  @Override
  public NodeVisitor enter(Node n) {
    if (n instanceof MethodDecl) {
      MethodDecl m = (MethodDecl) n;
      if (m.flags().contains(FabILFlags.MEMOIZED)) {
        returnTypeStack.push(m.returnType());
      }
    }
    return this;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    Node newN = ext(n).rewriteMemoizedMethods(this);
    if (n instanceof MethodDecl) {
      MethodDecl method = (MethodDecl) n;
      if (method.flags().contains(FabILFlags.MEMOIZED)) {
        returnTypeStack.pop();
      }
    }
    return newN;
  }

  public TypeNode methodReturnType() {
    TypeNode returnType = returnTypeStack.peek();
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

  public boolean inMemoizedMethod() {
    return !returnTypeStack.empty();
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
