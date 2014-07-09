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
package fabric.extension;

import java.util.ArrayList;
import java.util.List;

import jif.extension.JifExprExt;
import jif.translate.ToJavaExt;
import jif.types.JifContext;
import jif.types.JifTypeSystem;
import jif.types.PathMap;
import jif.visit.LabelChecker;
import polyglot.ast.Expr;
import polyglot.ast.Ext;
import polyglot.ast.Node;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import fabric.ast.Store;

/**
 * 
 */
public class StoreJifExt_c extends JifExprExt implements Ext {

  /**
   * @param toJava
   */
  public StoreJifExt_c(ToJavaExt toJava) {
    super(toJava);
  }

  /**
   * @throws SemanticException
   */
  @Override
  public Node labelCheck(LabelChecker lc) throws SemanticException {
    Store c = (Store) node();

    JifContext A = lc.jifContext();
    JifTypeSystem ts = lc.jifTypeSystem();

    Store fe = (Store) node();

    Expr expr = (Expr) lc.labelCheck(c.expr());
    PathMap Xe = getPathMap(expr);

    List<Type> throwTypes = new ArrayList<Type>(fe.del().throwTypes(ts));
    if (!c.exprIsNeverNull()) {
      // null pointer exception may be thrown.
      Type npe = ts.NullPointerException();
      checkAndRemoveThrowType(throwTypes, npe);
      Xe = Xe.exc(Xe.NV(), npe);
    }

    PathMap X = Xe.NV(lc.upperBound(A.pc(), Xe.NV()));

    return updatePathMap(c, X);
  }
}
