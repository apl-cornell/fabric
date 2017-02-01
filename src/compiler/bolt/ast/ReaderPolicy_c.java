package bolt.ast;

import java.util.List;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class ReaderPolicy_c extends ConfPolicy_c implements ReaderPolicy {
  private static final long serialVersionUID = SerialVersionUID.generate();

  protected Principal owner;
  protected Principal reader;

  public ReaderPolicy_c(Position pos, Principal owner, Principal reader) {
    this(pos, owner, reader, null);
  }

  public ReaderPolicy_c(Position pos, Principal owner, Principal reader,
      Ext ext) {
    super(pos, ext);
    this.owner = owner;
    this.reader = reader;
  }

  @Override
  public Principal owner() {
    return owner;
  }

  @Override
  public ReaderPolicy owner(Principal owner) {
    return owner(this, owner);
  }

  protected <N extends ReaderPolicy_c> N owner(N n, Principal owner) {
    if (n.owner == owner) return n;
    n = copyIfNeeded(n);
    n.owner = owner;
    return n;
  }

  @Override
  public Principal reader() {
    return reader;
  }

  @Override
  public ReaderPolicy reader(Principal reader) {
    return reader(this, reader);
  }

  protected <N extends ReaderPolicy_c> N reader(N n, Principal reader) {
    if (n.reader == reader) return n;
    n = copyIfNeeded(n);
    n.reader = reader;
    return n;
  }

  protected <N extends ReaderPolicy_c> N reconstruct(N n, Principal owner,
      Principal reader) {
    n = owner(n, owner);
    n = reader(n, reader);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Principal owner = visitChild(this.owner, v);
    Principal reader = visitChild(this.reader, v);
    return reconstruct(this, owner, reader);
  }

  @Override
  public Term firstChild() {
    return owner;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    v.visitCFG(owner, reader, ENTRY);
    v.visitCFG(reader, this, EXIT);
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
