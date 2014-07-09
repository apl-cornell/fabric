/**
 * Copyright (C) 2010-2014 Fabric project group, Cornell University
 *
 * This file is part of Fabric.
 *
 * Fabric is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 2 of the License, or (at your option) any later
 * version.
 * 
 * Fabric is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.
 */
package fabric.types;

import jif.types.hierarchy.LabelEnv;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathRoot;
import polyglot.types.ClassType;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * 
 */
public class AccessPathNew extends AccessPathRoot {

  protected ClassType ct;
  protected AccessPath location;

  /**
   * @param pos
   */
  protected AccessPathNew(ClassType ct, AccessPath location, Position pos) {
    super(pos);
    this.ct = ct;
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
    //XXX: This is not really correct.  Two independent new
    //     expressions might be considered equivalent 
    //     with this definition. Really need an 'instance' for
    //     each new to distinguish them.
    if (o instanceof AccessPathNew) {
      AccessPathNew that = (AccessPathNew) o;
      if (this.ct == that.ct || this.ct == null || that.ct == null)
        return true;
      return this.ct.equals(that.ct);
    }
    return false;
  }

  @Override
  public boolean equivalentTo(AccessPath p, LabelEnv env) {
    if (this == p || equals(p)) return true;
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
