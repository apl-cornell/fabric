package fabil.visit;

import polyglot.ast.Assign;
import polyglot.ast.Binary;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;

/**
 * Traverses the AST and normalizes assignment operations. Converts
 * <code>a op= b</code> to <code>a = a op b</code>.
 */
public class AssignNormalizer extends NodeVisitor {
  private NodeFactory nf;

  public AssignNormalizer(NodeFactory nf) {
    this.nf = nf;
  }

  @Override
  public Node leave(Node old, Node n, NodeVisitor v) {
    if (!(n instanceof Assign)) return n;
    Assign assign = (Assign) n;
    Binary.Operator op = assign.operator().binaryOperator();
    if (op == null) return n;

    Expr lhs = assign.left();
    Expr rhs = assign.right();
    assign = assign.operator(Assign.ASSIGN);
    return assign.right(nf.Binary(Position.compilerGenerated(), lhs, op, rhs)
        .type(assign.type()));
  }
}
