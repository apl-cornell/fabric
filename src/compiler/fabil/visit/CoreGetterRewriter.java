package fabil.visit;

import java.util.Stack;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;
import fabil.ast.Annotated;
import fabil.ast.CoreGetter;

public class CoreGetterRewriter extends NodeVisitor {
  
  Stack<Expr> locationStack;
  public CoreGetterRewriter() {
    locationStack = new Stack<Expr>();
  }
  
  @Override
  public NodeVisitor enter(Node n) {
    if (n instanceof Annotated) {
      Annotated an = (Annotated) n;
      locationStack.push(an.location());
    }    
    return this;
  }
  
  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (n instanceof Annotated) {
      locationStack.pop();
    }
    if (n instanceof CoreGetter) {
      return locationStack.peek();
    }
    return super.leave(old, n, v);
  }
}
