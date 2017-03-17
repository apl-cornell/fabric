package bolt.ast;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.ast.Term_c;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class ArrayDim_c extends Term_c implements ArrayDim {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected ArrayDimKind kind;
  protected Expr length;
  protected Expr label;

  public ArrayDim_c(Position pos, ArrayDimKind kind, Expr length, Expr label) {
    this(pos, kind, length, label, null);
  }

  public ArrayDim_c(Position pos, ArrayDimKind kind, Expr length, Expr label,
      Ext ext) {
    super(pos, ext);

    assert kind != null;
    assert length != null;

    this.kind = kind;
    this.length = length;
    this.label = label;
  }

  @Override
  public ArrayDimKind kind() {
    return kind;
  }

  @Override
  public ArrayDim kind(ArrayDimKind kind) {
    return kind(this, kind);
  }

  protected <N extends ArrayDim_c> N kind(N n, ArrayDimKind kind) {
    if (n.kind == kind) return n;
    n = copyIfNeeded(n);
    n.kind = kind;
    return n;
  }

  @Override
  public Expr length() {
    return length;
  }

  @Override
  public ArrayDim length(Expr length) {
    return length(this, length);
  }

  protected <N extends ArrayDim_c> N length(N n, Expr length) {
    if (n.length == length) return n;
    n = copyIfNeeded(n);
    n.length = length;
    return n;
  }

  @Override
  public Expr label() {
    return label;
  }

  @Override
  public ArrayDim label(Expr label) {
    return label(this, label);
  }

  protected <N extends ArrayDim_c> N label(N n, Expr label) {
    if (n.label == label) return n;
    n = copyIfNeeded(n);
    n.label = label;
    return n;
  }

  protected <N extends ArrayDim_c> N reconstruct(N n, Expr length, Expr label) {
    n = length(n, length);
    n = label(n, label);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Expr length = visitChild(this.length, v);
    Expr label = visitChild(this.label, v);
    return reconstruct(this, length, label);
  }

  @Override
  public Term firstChild() {
    return length;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    if (label == null) {
      v.visitCFG(length, this, EXIT);
    } else {
      v.visitCFG(length, label, ENTRY);
      v.visitCFG(label, this, EXIT);
    }

    return succs;
  }

  @Override
  public void prettyPrint(CodeWriter w, PrettyPrinter pp) {
    // TODO Auto-generated method stub
    int TODO;
    super.prettyPrint(w, pp);
  }

  @Override
  public void dump(CodeWriter w) {
    // TODO Auto-generated method stub
    int TODO;
    super.dump(w);
  }

  @Override
  public String toString() {
    // TODO Auto-generated method stub
    int TODO;
    return super.toString();
  }

}
