package bolt.ast;

import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.ArrayTypeNode;
import polyglot.ast.Call;
import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.FieldDecl;
import polyglot.ast.Id;
import polyglot.ast.Javadoc;
import polyglot.ast.New;
import polyglot.ast.Receiver;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.ast.AnnotationElem;
import polyglot.types.Flags;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * NodeFactory for Bolt extension.
 */
public class BoltNodeFactory_c extends BoltAbstractNodeFactory_c
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
  public JoinLabel JoinLabel(Position pos, List<LabelComponent> components) {
    JoinLabel_c n = new JoinLabel_c(pos, components);
    n = ext(n, extFactory().extJoinLabel());
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
  public WriterPolicy WriterPolicy(Position pos, Principal owner,
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
  public ConjunctivePrincipal ConjunctivePrincipal(Position pos,
      List<Principal> conjuncts) {
    ConjunctivePrincipal_c n = new ConjunctivePrincipal_c(pos, conjuncts);
    n = ext(n, extFactory().extConjunctivePrincipal());
    return n;
  }

  @Override
  public DisjunctivePrincipal DisjunctivePrincipal(Position pos,
      List<Principal> disjuncts) {
    DisjunctivePrincipal_c n = new DisjunctivePrincipal_c(pos, disjuncts);
    n = ext(n, extFactory().extDisjunctivePrincipal());
    return n;
  }

  @Override
  public ExprPrincipal ExprPrincipal(Position pos, Expr expr) {
    ExprPrincipal_c n = new ExprPrincipal_c(pos, expr);
    n = ext(n, extFactory().extExprPrincipal());
    return n;
  }

  @Override
  public final FieldDecl FieldDecl(Position pos, Flags flags,
      List<AnnotationElem> annotations, TypeNode type, Id name, Expr init,
      Javadoc javadoc) {
    return FieldDecl(pos, flags, annotations, type, null, name, init, javadoc);
  }

  @Override
  public FieldDecl FieldDecl(Position pos, Flags flags,
      List<AnnotationElem> annotations, TypeNode type, Label label, Id name,
      Expr init, Javadoc javadoc) {
    FieldDecl n =
        super.FieldDecl(pos, flags, annotations, type, name, init, javadoc);
    BoltFieldDeclExt ext = (BoltFieldDeclExt) BoltExt.ext(n);
    ext.label = label;
    return n;
  }

  @Override
  public NewLabel NewLabel(Position pos, Expr location, Label label) {
    NewLabel n = new NewLabel_c(pos, label);
    n = ext(n, extFactory().extNewLabel());
    NewLabelExt ext = (NewLabelExt) BoltExt.ext(n);
    ext.location = location;
    return n;
  }

  @Override
  public NewPrincipal NewPrincipal(Position pos, Expr location,
      Principal principal) {
    NewPrincipal n = new NewPrincipal_c(pos, principal);
    n = ext(n, extFactory().extNewPrincipal());
    NewPrincipalExt ext = (NewPrincipalExt) BoltExt.ext(n);
    ext.location = location;
    return n;
  }

  @Override
  public final New New(Position pos, Expr outer, List<TypeNode> typeArgs,
      TypeNode objectType, List<Expr> args, ClassBody body) {
    return New(pos, outer, null, typeArgs, objectType, args, body);
  }

  @Override
  public New New(Position pos, Expr outer, Expr location,
      List<TypeNode> typeArgs, TypeNode objectType, List<Expr> args,
      ClassBody body) {
    New n = super.New(pos, outer, typeArgs, objectType, args, body);
    BoltNewExt ext = (BoltNewExt) BoltExt.ext(n);
    ext.location = location;
    return n;
  }

  @Override
  public final Call Call(Position pos, Receiver target, List<TypeNode> typeArgs,
      Id name, List<Expr> args) {
    return Call(pos, target, typeArgs, name, null, args);
  }

  @Override
  public Call Call(Position pos, Receiver target, List<TypeNode> typeArgs,
      Id name, Expr location, List<Expr> args) {
    Call n = super.Call(pos, target, typeArgs, name, args);
    BoltCallExt ext = (BoltCallExt) BoltExt.ext(n);
    ext.location = location;
    return n;
  }

  /**
   * @deprecated Use {@link #ArrayTypeNode(Position, TypeNode, Kind)}.
   */
  @Deprecated
  @Override
  public final ArrayTypeNode ArrayTypeNode(Position pos, TypeNode base) {
    throw new InternalCompilerError(
        "Call ArrayTypeNode(Position, TypeNode, Base) instead.");
  }

  @Override
  public ArrayTypeNode ArrayTypeNode(Position pos, TypeNode base,
      ArrayDimKind kind) {
    ArrayTypeNode n = super.ArrayTypeNode(pos, base);
    BoltArrayTypeNodeExt ext = (BoltArrayTypeNodeExt) BoltExt.ext(n);
    ext.kind = kind;
    return n;
  }

  @Override
  public BoltNewArray BoltNewArray(Position pos, Expr location, TypeNode base,
      List<ArrayDim> dims, List<ArrayDimKind> addDims, ArrayInit init) {
    BoltLocatedElementExt ext =
        (BoltLocatedElementExt) extFactory().extBoltNewArray();
    ext.location = location;

    BoltNewArray n = new BoltNewArray_c(pos, base, dims, addDims, init);
    n = ext(n, ext);
    return n;
  }

  @Override
  protected ArrayDim ArrayDim(Position pos, ArrayDimKind kind, Expr length,
      Expr label) {
    ArrayDim n = new ArrayDim_c(pos, kind, length, label);
    n = ext(n, extFactory().extArrayDim());
    return n;
  }

  @Override
  public final ArrayInit ArrayInit(Position pos, List<Expr> elements) {
    return ArrayInit(pos, null, null, elements);
  }

  @Override
  public ArrayInit ArrayInit(Position pos, Expr location, Expr label,
      List<Expr> elements) {
    ArrayInit n = super.ArrayInit(pos, elements);
    BoltArrayInitExt ext = (BoltArrayInitExt) BoltExt.ext(n);
    ext.location = location;
    ext.label = label;
    return n;
  }
}
