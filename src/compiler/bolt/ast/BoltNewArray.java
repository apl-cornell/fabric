package bolt.ast;

import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.Expr;
import polyglot.ast.NewArray;
import polyglot.ast.TypeNode;

/**
 * Similar to {@link NewArray}, except also tracks whether each dimension is a
 * Java array or a Fabric array.
 */
public interface BoltNewArray extends Expr {
  /**
   * @return the array's base type.
   */
  TypeNode baseType();

  /**
   * Sets the array's base type.
   */
  BoltNewArray baseType(TypeNode baseType);

  /**
   * @return
   *     the number of array dimensions. This is the same as
   *      {@code dims().size() + additionalDims().size()}.
   */
  int numDims();

  /**
   * @return the list of dimension expressions.
   */
  List<ArrayDimExpr> dims();

  /**
   * Sets the list of dimension expressions.
   */
  BoltNewArray dims(List<ArrayDimExpr> dims);

  /**
   * @return the additional dimensions, each designated by its kind.
   */
  List<ArrayDimKind> additionalDims();

  /**
   * Sets the additional dimensions.
   */
  BoltNewArray additionalDims(List<ArrayDimKind> addDims);

  /**
   * @return the array initializer, or null.
   */
  ArrayInit init();

  /**
   * Sets the array initializer.
   */
  BoltNewArray init(ArrayInit init);

}
