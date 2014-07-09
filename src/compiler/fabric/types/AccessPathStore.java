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

import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.types.hierarchy.LabelEnv;
import jif.types.label.AccessPath;
import jif.types.label.AccessPathRoot;
import jif.types.label.Label;
import jif.visit.LabelChecker;
import polyglot.types.Type;
import polyglot.util.Position;

/**
 * 
 */
public class AccessPathStore extends AccessPath {
  protected final AccessPath path;
  protected final Type storeType;

  /**
   * @param path
   * @param fi
   * @param fieldName
   * @param pos
   */
  public AccessPathStore(AccessPath path, Type storeType, Position pos) {
    super(pos);
    this.path = path;
    this.storeType = storeType;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof AccessPathStore) {
      AccessPathStore that = (AccessPathStore) o;
      return this.path.equals(that.path);
    }
    return false;
  }

  @Override
  public boolean isCanonical() {
    return path.isCanonical();
  }

  @Override
  public boolean isUninterpreted() {
    return path.isUninterpreted();
  }

  @Override
  public AccessPath subst(AccessPathRoot r, AccessPath e) {
    AccessPath newPath = path.subst(r, e);
    if (newPath == path) return this;
    if (newPath instanceof AccessPathNew) {
      AccessPathNew apn = (AccessPathNew) newPath;
      return apn.location();
    }

    return new AccessPathStore(newPath, storeType, position());
  }

  @Override
  public Type type() {
    return storeType;
  }

  @Override
  public int hashCode() {
    return "store$".hashCode() ^ path.hashCode();
  }

  @Override
  public boolean isNeverNull() {
    return path.isNeverNull();
  }

  @Override
  public PathMap labelcheck(JifContext A, LabelChecker lc) {
    PathMap Xt = path.labelcheck(A, lc);

    JifTypeSystem ts = (JifTypeSystem) A.typeSystem();

    PathMap X = Xt;
    if (!isNeverNull()) {
      // null pointer exception may be thrown.
      X = Xt.exc(Xt.NV(), ts.NullPointerException());
    }

    Label L = ts.labelOfType(path.type());
    X = X.NV(lc.upperBound(L, X.NV()));
    return X;
  }

  @Override
  public final AccessPathRoot root() {
    return path.root();
  }

  @Override
  public boolean equivalentTo(AccessPath p, LabelEnv env) {
    if (p instanceof AccessPathStore) {
      AccessPathStore apf = (AccessPathStore) p;
      return env.equivalentAccessPaths(this.path(), apf.path());
    }
    return false;
  }

  /**
   * @return The path we are retrieving the store of.
   */
  public AccessPath path() {
    return path;
  }

  @Override
  public String toString() {
    return path + ".store$";
  }

}
