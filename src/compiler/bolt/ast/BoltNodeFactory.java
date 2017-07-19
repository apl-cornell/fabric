package bolt.ast;

import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.ArrayTypeNode;
import polyglot.ast.Block;
import polyglot.ast.Call;
import polyglot.ast.ClassBody;
import polyglot.ast.Expr;
import polyglot.ast.FieldDecl;
import polyglot.ast.Id;
import polyglot.ast.Javadoc;
import polyglot.ast.New;
import polyglot.ast.Receiver;
import polyglot.ast.Stmt;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.ast.AnnotationElem;
import polyglot.ext.jl7.ast.JL7NodeFactory;
import polyglot.types.Flags;
import polyglot.util.Position;

/**
 * NodeFactory for Bolt extension.
 */
public interface BoltNodeFactory extends JL7NodeFactory {
  /**
   * @return a {@link Label} representing the empty label.
   */
  Label emptyLabel(Position pos);

  JoinLabel JoinLabel(Position pos, LabelComponent... components);

  JoinLabel JoinLabel(Position pos, List<LabelComponent> components);

  MeetLabel MeetLabel(Position pos, LabelComponent... components);

  MeetLabel MeetLabel(Position pos, List<LabelComponent> components);

  /**
   * @return a confidentiality policy representing public information.
   */
  ConfPolicy publicPolicy(Position pos);

  ReaderPolicy ReaderPolicy(Position pos, Principal owner, Principal reader);

  /**
   * @return an integrity policy representing untrusted information.
   */
  IntegPolicy untrustedPolicy(Position pos);

  WriterPolicy WriterPolicy(Position pos, Principal owner, Principal writer);

  TopPrincipal TopPrincipal(Position pos);

  BottomPrincipal BottomPrincipal(Position pos);

  ExprLabel ExprLabel(Position pos, Expr expr);

  ConjunctivePrincipal ConjunctivePrincipal(Position pos,
      List<Principal> conjuncts);

  DisjunctivePrincipal DisjunctivePrincipal(Position pos,
      List<Principal> disjuncts);

  ExprPrincipal ExprPrincipal(Position pos, Expr expr);

  FieldDecl FieldDecl(Position pos, Flags flags,
      List<AnnotationElem> annotations, TypeNode type, Label label, Id name,
      Expr init, Javadoc javadoc);

  NewLabel NewLabel(Position pos, Label label);

  NewLabel NewLabel(Position pos, Expr location, Label label);

  NewPrincipal NewPrincipal(Position pos, Principal principal);

  NewPrincipal NewPrincipal(Position pos, Expr location, Principal principal);

  New New(Position pos, Expr outer, Expr location, List<TypeNode> typeArgs,
      TypeNode objectType, List<Expr> args, ClassBody body);

  Call Call(Position pos, Receiver target, Id name, Expr location,
      List<Expr> args);

  Call Call(Position pos, Receiver target, List<TypeNode> typeArgs, Id name,
      Expr location, List<Expr> args);

  ArrayTypeNode ArrayTypeNode(Position pos, TypeNode base, ArrayDimKind kind);

  BoltNewArray BoltNewArray(Position pos, TypeNode base, List<ArrayDim> dims,
      List<ArrayDimKind> addDims);

  BoltNewArray BoltNewArray(Position pos, TypeNode base, List<ArrayDim> dims,
      List<ArrayDimKind> addDims, ArrayInit init);

  BoltNewArray BoltNewArray(Position pos, Expr location, TypeNode base,
      List<ArrayDim> dims, List<ArrayDimKind> addDims);

  BoltNewArray BoltNewArray(Position pos, Expr location, TypeNode base,
      List<ArrayDim> dims, List<ArrayDimKind> addDims, ArrayInit init);

  /**
   * Creates an {@link ArrayDim} for a Java array.
   */
  ArrayDim ArrayDim(Position pos, Expr length);

  /**
   * Creates an {@link ArrayDim} for a Fabric array.
   */
  ArrayDim ArrayDim(Position pos, Expr length, Expr label);

  ArrayInit ArrayInit(Position pos, Expr location, Expr label);

  ArrayInit ArrayInit(Position pos, Expr location, Expr label,
      List<Expr> elements);

  Block Atomic(Position pos, List<TypeNode> throwTypes, List<Stmt> stmts);
}
