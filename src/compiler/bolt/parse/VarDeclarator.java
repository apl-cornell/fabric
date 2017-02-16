package bolt.parse;

import java.util.ArrayList;
import java.util.List;

import bolt.ast.ArrayDimKind;
import polyglot.ast.Expr;
import polyglot.ast.Id;
import polyglot.util.Position;

/**
 * Encapsulates some of the information in a variable declaration. Used only by
 * the parser.
 */
public class VarDeclarator {
  protected Position pos;
  protected Id name;
  protected List<ArrayDimKind> dims;
  protected Expr init;

  public VarDeclarator(Position pos, Id name) {
    this.pos = pos;
    this.name = name;
    this.dims = new ArrayList<>();
    this.init = null;
  }

  public Id name() {
    return name;
  }

  public List<ArrayDimKind> dims() {
    return dims;
  }

  public void addDim(ArrayDimKind kind) {
    dims.add(kind);
  }

  public Expr init() {
    return init;
  }

  public Position pos() {
    return pos;
  }
}
