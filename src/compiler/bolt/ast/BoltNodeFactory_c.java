package bolt.ast;

import java.util.List;

import polyglot.ast.Expr;
import polyglot.ext.jl7.ast.JL7NodeFactory_c;
import polyglot.util.Position;

/**
 * NodeFactory for Bolt extension.
 */
public class BoltNodeFactory_c extends JL7NodeFactory_c
    implements BoltNodeFactory {
  public BoltNodeFactory_c(BoltLang lang, BoltExtFactory extFactory) {
    super(lang, extFactory);
  }

  @Override
  public BoltExtFactory extFactory() {
    return (BoltExtFactory) super.extFactory();
  }

  @Override
  public Label emptyLabel(Position pos) {
    return JoinLabel(pos, publicPolicy(pos), untrustedPolicy(pos));
  }

  @Override
  public JoinLabel JoinLabel(Position pos, LabelComponent... components) {
    JoinLabel_c n = new JoinLabel_c(pos, components);
    n = ext(n, extFactory().extJoinLabel());
    return n;
  }

  @Override
  public JoinLabel JoinLabel(Position pos, List<LabelComponent> components) {
    JoinLabel_c n = new JoinLabel_c(pos, components);
    n = ext(n, extFactory().extJoinLabel());
    return n;
  }

  @Override
  public MeetLabel MeetLabel(Position pos, LabelComponent... components) {
    MeetLabel_c n = new MeetLabel_c(pos, components);
    n = ext(n, extFactory().extMeetLabel());
    return n;
  }

  @Override
  public MeetLabel MeetLabel(Position pos, List<LabelComponent> components) {
    MeetLabel_c n = new MeetLabel_c(pos, components);
    n = ext(n, extFactory().extMeetLabel());
    return n;
  }

  @Override
  public ConfPolicy publicPolicy(Position pos) {
    return ReaderPolicy(pos, BottomPrincipal(pos), BottomPrincipal(pos));
  }

  @Override
  public ReaderPolicy ReaderPolicy(Position pos, Principal owner,
      Principal reader) {
    ReaderPolicy_c n = new ReaderPolicy_c(pos, owner, reader);
    n = ext(n, extFactory().extReaderPolicy());
    return n;
  }

  @Override
  public IntegPolicy untrustedPolicy(Position pos) {
    return WriterPolicy(pos, BottomPrincipal(pos), BottomPrincipal(pos));
  }

  @Override
  public bolt.ast.WriterPolicy WriterPolicy(Position pos, Principal owner,
      Principal writer) {
    WriterPolicy_c n = new WriterPolicy_c(pos, owner, writer);
    n = ext(n, extFactory().extWriterPolicy());
    return n;
  }

  @Override
  public TopPrincipal TopPrincipal(Position pos) {
    TopPrincipal_c n = new TopPrincipal_c(pos);
    n = ext(n, extFactory().extTopPrincipal());
    return n;
  }

  @Override
  public BottomPrincipal BottomPrincipal(Position pos) {
    BottomPrincipal_c n = new BottomPrincipal_c(pos);
    n = ext(n, extFactory().extBottomPrincipal());
    return n;
  }

  @Override
  public ExprLabel ExprLabel(Position pos, Expr expr) {
    ExprLabel_c n = new ExprLabel_c(pos, expr);
    n = ext(n, extFactory().extExprLabel());
    return n;
  }

  @Override
  public bolt.ast.ConjunctivePrincipal ConjunctivePrincipal(Position pos,
      List<Principal> conjuncts) {
    ConjunctivePrincipal_c n = new ConjunctivePrincipal_c(pos, conjuncts);
    n = ext(n, extFactory().extConjunctivePrincipal());
    return n;
  }

  @Override
  public bolt.ast.DisjunctivePrincipal DisjunctivePrincipal(Position pos,
      List<Principal> disjuncts) {
    DisjunctivePrincipal_c n = new DisjunctivePrincipal_c(pos, disjuncts);
    n = ext(n, extFactory().extDisjunctivePrincipal());
    return n;
  }

  @Override
  public bolt.ast.ExprPrincipal ExprPrincipal(Position pos, Expr expr) {
    ExprPrincipal_c n = new ExprPrincipal_c(pos, expr);
    n = ext(n, extFactory().extExprPrincipal());
    return n;
  }

}
