package fabric.types;

import jif.types.hierarchy.LabelEnv;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathRoot;
import jif.types.label.AccessPathThis;
import polyglot.ast.New;
import polyglot.types.ClassType;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * 
 */
public class AccessPathNew extends AccessPathRoot {

  protected ClassType ct;
  protected New node;
  protected AccessPath location;

  /**
   * @param pos
   */
  protected AccessPathNew(New n, ClassType ct, AccessPath location, Position pos) {
    super(pos);
    this.ct = ct;
    this.node = n;
    this.location = location;
  }

  public AccessPath location() {
    return location;
  }

  @Override
  public boolean isCanonical() {
    return true;
  }

  @Override
  public boolean isNeverNull() {
    return true;
  }

  @Override
  public AccessPath subst(AccessPathRoot r, AccessPath e) {
    if (r instanceof AccessPathNew) {
      if (this.equals(r)) {
        return e;
      }
    }
    return this;
  }

  @Override
  public String toString() {
    return "new(" + ct.fullName() + ")";
  }

  @Override
  public String exprString() {
    return toString();
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof AccessPathNew) {
      AccessPathNew that = (AccessPathNew) o;
      if (this.node == that.node || this.node == null || that.node == null)
        return true;
      return this.node.equals(that.node);
    }
    return false;
  }

  @Override
  public boolean equivalentTo(AccessPath p, LabelEnv env) {
    if (this == p || equals(p)) return true;
    if (p instanceof AccessPathThis) {
      return p.type().equals(ct);
    }
    return false;
  }

  @Override
  public Type type() {
    return ct;
  }

  @Override
  public int hashCode() {
    return -4544466;
  }

//  @Override
//  public PathMap labelcheck(JifContext A, LabelChecker lc) {
//      JifTypeSystem ts = (JifTypeSystem) A.typeSystem();
//      JifClassType ct = (JifClassType) A.currentClass();
//
//      PathMap X = ts.pathMap();
//      X = X.N(A.pc());
//
//      // X(this).NV = this_label, which is upper-bounded by the begin label.
//      X = X.NV(lc.upperBound(ct.thisLabel(), A.pc()));
//      return X;
//  }

}
