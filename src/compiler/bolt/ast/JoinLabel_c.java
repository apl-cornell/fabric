package bolt.ast;

import java.util.Arrays;
import java.util.List;

import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.util.CodeWriter;
import polyglot.util.CollectionUtil;
import polyglot.util.ListUtil;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class JoinLabel_c extends Label_c implements JoinLabel {
  private static final long serialVersionUID = SerialVersionUID.generate();

  protected List<LabelComponent> components;

  public JoinLabel_c(Position pos, LabelComponent... components) {
    this(pos, components, null);
  }

  public JoinLabel_c(Position pos, LabelComponent[] components, Ext ext) {
    this(pos, Arrays.asList(components), ext);
  }

  public JoinLabel_c(Position pos, List<LabelComponent> components) {
    this(pos, components, null);
  }

  public JoinLabel_c(Position pos, List<LabelComponent> components, Ext ext) {
    super(pos, null);
    assert components != null;
    this.components = ListUtil.copy(components, true);
  }

  @Override
  public List<LabelComponent> components() {
    return components;
  }

  @Override
  public JoinLabel components(List<LabelComponent> components) {
    return components(this, components);
  }

  protected <N extends JoinLabel_c> N components(N n,
      List<LabelComponent> components) {
    if (CollectionUtil.equals(n.components, components)) return n;
    n = copyIfNeeded(n);
    n.components = ListUtil.copy(components, true);
    return n;
  }

  protected <N extends JoinLabel_c> N reconstruct(N n,
      List<LabelComponent> components) {
    n = components(n, components);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    List<LabelComponent> components = visitList(this.components, v);
    return reconstruct(this, components);
  }

  @Override
  public Term firstChild() {
    return listChild(components, null);
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    v.visitCFGList(components, this, EXIT);
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
