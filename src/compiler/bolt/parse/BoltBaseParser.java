package bolt.parse;

import static bolt.ast.ArrayDimKind.FABRIC;
import static bolt.ast.ArrayDimKind.JAVA;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import bolt.ast.ArrayDimKind;
import bolt.ast.BoltNodeFactory;
import bolt.types.BoltTypeSystem;
import polyglot.ast.CanonicalTypeNode;
import polyglot.ast.LocalDecl;
import polyglot.ast.NodeFactory;
import polyglot.ast.TypeNode;
import polyglot.ext.jl5.ast.AnnotationElem;
import polyglot.lex.Lexer;
import polyglot.types.ArrayType;
import polyglot.types.Flags;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * This mainly exists so that we don't have to write as much code in the PPG
 * file.
 */
public abstract class BoltBaseParser extends polyglot.ext.jl7.parse.Grm {
  public final BoltNodeFactory nf;
  public final BoltTypeSystem ts;

  public BoltBaseParser(Lexer l, TypeSystem ts, NodeFactory nf, ErrorQueue eq) {
    super(l, ts, nf, eq);
    this.nf = (BoltNodeFactory) nf;
    this.ts = (BoltTypeSystem) ts;
  }

  /**
   * @return the source position of the declaration.
   */
  public Position pos(VarDeclarator n) {
    if (n == null) return null;
    return n.pos();
  }

  @Override
  protected Position posForObject(Object o) {
    if (o instanceof VarDeclarator) return pos((VarDeclarator) o);
    return super.posForObject(o);
  }

  protected TypeNode array(TypeNode tn, List<ArrayDimKind> dims) {
    for (ListIterator<ArrayDimKind> it = dims.listIterator(); it
        .hasPrevious();) {
      ArrayDimKind kind = it.previous();

      if (tn instanceof CanonicalTypeNode) {
        Type t = tn.type();

        final ArrayType at;
        switch (kind) {
        case FABRIC:
          at = ts.fabricArrayOf(t);
          break;

        case JAVA:
          at = ts.arrayOf(t);
          break;

        default:
          throw new InternalCompilerError("Unknown ArrayDimKind: " + kind);
        }

        tn = nf.CanonicalTypeNode(pos(tn), at);
        continue;
      }

      switch (kind) {
      case FABRIC:
        tn = nf.ArrayTypeNode(pos(tn), tn, FABRIC);
        break;

      case JAVA:
        tn = nf.ArrayTypeNode(pos(tn), tn, JAVA);
        break;
      }
    }

    return tn;
  }

  /**
   * @deprecated Use {@link #boltVariableDeclarators(TypeNode, List, Flags).
   */
  @Deprecated
  @Override
  public final List<LocalDecl> variableDeclarators(TypeNode a,
      List<polyglot.parse.VarDeclarator> b, Flags flags,
      List<AnnotationElem> annotations) throws Exception {
    throw new InternalCompilerError("Call boltVariableDeclarators() instead.");
  }

  /**
   * @deprecated
   *     Use {@link #boltVariableDeclarators(TypeNode, List, Flags, List).
   */
  @Deprecated
  @Override
  public final List<LocalDecl> variableDeclarators(TypeNode a,
      List<polyglot.parse.VarDeclarator> b, Flags flags) throws Exception {
    throw new InternalCompilerError("Call boltVariableDeclarators() instead.");
  }

  public List<LocalDecl> boltVariableDeclarators(TypeNode tn,
      List<VarDeclarator> decls, Flags flags) throws Exception {
    return boltVariableDeclarators(tn, decls, flags, null);
  }

  public List<LocalDecl> boltVariableDeclarators(TypeNode tn,
      List<VarDeclarator> decls, Flags flags, List<AnnotationElem> annotations)
      throws Exception {
    List<LocalDecl> result = new ArrayList<>();
    for (VarDeclarator d : decls) {
      result.add(nf.LocalDecl(pos(d), flags, annotations, array(tn, d.dims()),
          d.name(), d.init()));
    }

    return result;
  }

}
