/**
 * Copyright (C) 2010-2012 Fabric project group, Cornell University
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
package fabric.parse;

import jif.parse.Amb;
import jif.parse.Grm;
import jif.parse.Wrapper;
import polyglot.ast.Expr;
import polyglot.util.Position;
import fabric.ast.FabricNodeFactory;

/**
 * 
 */
public class AmbStore extends Amb {
  public Amb expr;

  /**
   * @param parser
   * @param pos
   */
  public AmbStore(Amb expr, Grm parser, Position pos) {
    super(parser, pos);
    this.expr = expr;
  }

  @Override
  public Expr wrap() throws Exception {
    return new Wrapper(this);
  }

  @Override
  public Expr toExpr() throws Exception {
    return ((FabricNodeFactory) parser.nf).Store(pos, expr.toExpr());
  }
}
