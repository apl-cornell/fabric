package bolt.ast;

import java.util.Arrays;
import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.Call;
import polyglot.ast.Expr;
import polyglot.ast.FieldDecl;
import polyglot.ast.Id;
import polyglot.ast.Javadoc;
import polyglot.ast.NewArray;
import polyglot.ast.Receiver;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.ast.AnnotationElem;
import polyglot.ext.jl7.ast.JL7NodeFactory_c;
import polyglot.types.Flags;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

public abstract class BoltAbstractNodeFactory_c extends JL7NodeFactory_c
    implements BoltNodeFactory {

  public BoltAbstractNodeFactory_c(BoltLang lang, BoltExtFactory extFactory) {
    super(lang, extFactory);
  }

  @Override
  public final JoinLabel JoinLabel(Position pos, LabelComponent... components) {
    return JoinLabel(pos, Arrays.asList(components));
  }

  @Override
  public final MeetLabel MeetLabel(Position pos, LabelComponent... components) {
    return MeetLabel(pos, Arrays.asList(components));
  }

  @Override
  public final NewLabel NewLabel(Position pos, Label label) {
    return NewLabel(pos, null, label);
  }

  @Override
  public final NewPrincipal NewPrincipal(Position pos, Principal principal) {
    return NewPrincipal(pos, null, principal);
  }

  /**
   * @deprecated use the version that takes in a {@link Javadoc}.
   */
  @Deprecated
  @Override
  public final FieldDecl FieldDecl(Position pos, Flags flags,
      List<AnnotationElem> annotations, TypeNode type, Id name, Expr init) {
    return FieldDecl(pos, flags, annotations, type, name, init, null);
  }

  @Override
  public final Call Call(Position pos, Receiver target, Id name, Expr location,
      List<Expr> args) {
    return Call(pos, target, null, name, location, args);
  }

  /**
   * @deprecated
   *     use {@link #BoltNewArray(Position, TypeNode, List, List, ArrayInit)}.
   */
  @Deprecated
  @Override
  public final NewArray NewArray(Position pos, TypeNode base, List<Expr> dims,
      int addDims, ArrayInit init) {
    throw new InternalCompilerError("Call BoltNewArray() instead.");
  }

  @Override
  public final BoltNewArray BoltNewArray(Position pos, TypeNode base,
      List<ArrayDimExpr> dims, List<ArrayDimKind> addDims) {
    return BoltNewArray(pos, base, dims, addDims, null);
  }

  @Override
  public final BoltNewArray BoltNewArray(Position pos, TypeNode base,
      List<ArrayDimExpr> dims, List<ArrayDimKind> addDims, ArrayInit init) {
    return BoltNewArray(pos, null, base, dims, addDims, init);
  }

  @Override
  public final BoltNewArray BoltNewArray(Position pos, Expr location,
      TypeNode base, List<ArrayDimExpr> dims, List<ArrayDimKind> addDims) {
    return BoltNewArray(pos, location, base, dims, addDims, null);
  }

}
