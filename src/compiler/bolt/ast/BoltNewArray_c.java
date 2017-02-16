package bolt.ast;

import java.util.List;

import polyglot.ast.ArrayInit;
import polyglot.ast.Expr;
import polyglot.ast.Expr_c;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.ast.TypeNode;
import polyglot.util.CodeWriter;
import polyglot.util.CollectionUtil;
import polyglot.util.ListUtil;
import polyglot.util.Position;
import polyglot.util.SerialVersionUID;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;

public class BoltNewArray_c extends Expr_c implements BoltNewArray {

  private static final long serialVersionUID = SerialVersionUID.generate();

  protected TypeNode baseType;
  protected List<ArrayDim> dims;
  protected List<ArrayDimKind> addDims;
  protected ArrayInit init;

  public BoltNewArray_c(Position pos, TypeNode baseType,
      List<ArrayDim> dims, List<ArrayDimKind> addDims, ArrayInit init) {
    this(pos, baseType, dims, addDims, init, null);
  }

  public BoltNewArray_c(Position pos, TypeNode baseType,
      List<ArrayDim> dims, List<ArrayDimKind> addDims, ArrayInit init,
      Ext ext) {
    super(pos, ext);
    // Only init may be null.
    assert baseType != null && dims != null && addDims != null;
    // dims may be empty only if there is an initializer.
    assert !dims.isEmpty() || init != null;
    // init may be non-null only if addDims > 0.
    assert addDims.size() > 0 || init == null;
    // Must allocate something.
    assert dims.size() + addDims.size() > 0;

    this.baseType = baseType;
    this.dims = ListUtil.copy(dims, true);
    this.addDims = ListUtil.copy(addDims, true);
    this.init = init;
  }

  @Override
  public TypeNode baseType() {
    return baseType;
  }

  @Override
  public BoltNewArray baseType(TypeNode baseType) {
    return baseType(this, baseType);
  }

  protected <N extends BoltNewArray_c> N baseType(N n, TypeNode baseType) {
    if (n.baseType == baseType) return n;
    n = copyIfNeeded(n);
    n.baseType = baseType;
    return n;
  }

  @Override
  public List<ArrayDim> dims() {
    return dims;
  }

  @Override
  public BoltNewArray dims(List<ArrayDim> dims) {
    return dims(this, dims);
  }

  protected <N extends BoltNewArray_c> N dims(N n, List<ArrayDim> dims) {
    if (CollectionUtil.equals(n.dims, dims)) return n;
    n = copyIfNeeded(n);
    n.dims = ListUtil.copy(dims, true);
    return n;
  }

  @Override
  public int numDims() {
    return dims.size() + addDims.size();
  }

  @Override
  public List<ArrayDimKind> additionalDims() {
    return addDims;
  }

  @Override
  public BoltNewArray additionalDims(List<ArrayDimKind> addDims) {
    return additionalDims(this, addDims);
  }

  protected <N extends BoltNewArray_c> N additionalDims(N n,
      List<ArrayDimKind> addDims) {
    if (CollectionUtil.equals(n.addDims, addDims)) return n;
    n = copyIfNeeded(n);
    n.addDims = ListUtil.copy(addDims, true);
    return n;
  }

  @Override
  public ArrayInit init() {
    return init;
  }

  @Override
  public BoltNewArray init(ArrayInit init) {
    return init(this, init);
  }

  protected <N extends BoltNewArray_c> N init(N n, ArrayInit init) {
    if (n.init == init) return n;
    n = copyIfNeeded(n);
    n.init = init;
    return n;
  }

  protected <N extends BoltNewArray_c> N reconstruct(N n, TypeNode baseType,
      List<ArrayDim> dims, ArrayInit init) {
    n = baseType(n, baseType);
    n = dims(n, dims);
    n = init(n, init);
    return n;
  }

  @Override
  public Node visitChildren(NodeVisitor v) {
    TypeNode baseType = visitChild(this.baseType, v);
    List<ArrayDim> dims = visitList(this.dims, v);
    ArrayInit init = visitChild(this.init, v);
    return reconstruct(this, baseType, dims, init);
  }

  @Override
  public Term firstChild() {
    return baseType;
  }

  @Override
  public <T> List<T> acceptCFG(CFGBuilder<?> v, List<T> succs) {
    if (init != null) {
      v.visitCFG(baseType, listChild(dims, init), ENTRY);
      v.visitCFGList(dims, init, ENTRY);
      v.visitCFG(init, this, EXIT);
    } else {
      v.visitCFG(baseType, listChild(dims, (Expr) null), ENTRY);
      v.visitCFGList(dims, this, EXIT);
    }

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
