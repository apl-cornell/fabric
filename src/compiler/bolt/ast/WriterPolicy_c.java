package bolt.ast;

import java.util.List;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class WriterPolicy_c extends IntegPolicy_c implements WriterPolicy {
  protected Principal owner;
  protected Principal writer;

  public WriterPolicy_c(Position pos, Principal owner, Principal writer) {
    this(pos, owner, writer, null);
  }

  public WriterPolicy_c(Position pos, Principal owner, Principal writer,
      Ext ext) {
    super(pos, ext);
    this.owner = owner;
    this.writer = writer;
  }

  @Override
  public Principal owner() {
    return owner;
  }

  @Override
  public WriterPolicy owner(Principal owner) {
    return owner(this, owner);
  }

  protected <N extends WriterPolicy_c> N owner(N n, Principal owner) {
    if (n.owner == owner) return n;
    n = copyIfNeeded(n);
    n.owner = owner;
    return n;
  }

  @Override
  public Principal writer() {
    return writer;
  }

  @Override
  public WriterPolicy writer(Principal writer) {
    return writer(this, writer);
  }

  protected <N extends WriterPolicy_c> N writer(N n, Principal writer) {
    if (n.writer == writer) return n;
    n = copyIfNeeded(n);
    n.writer = writer;
    return n;
  }

  protected <N extends WriterPolicy_c> N reconstruct(N n, Principal owner,
      Principal writer) {
    n = owner(n, owner);
    n = writer(n, writer);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    Principal owner = visitChild(this.owner, v);
    Principal writer = visitChild(this.writer, v);
    return reconstruct(this, owner, writer);
  }

  @Override
  public Term firstChild() {
    return owner;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    v.visitCFG(owner, writer, ENTRY);
    v.visitCFG(writer, this, EXIT);
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
