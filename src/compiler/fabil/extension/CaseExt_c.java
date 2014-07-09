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
package fabil.extension;

import polyglot.ast.Case;
import polyglot.ast.Ext;
import polyglot.ast.Field;
import polyglot.ast.IntLit;
import polyglot.ast.Lit;
import polyglot.ast.Node;
import polyglot.util.InternalCompilerError;
import fabil.visit.ProxyRewriter;

/**
 * 
 */
public class CaseExt_c extends FabILExt_c implements Ext {

  @Override
  public Node rewriteProxiesOverride(ProxyRewriter rewriter) {
    Case c = (Case) node();
    if (c.expr() instanceof Field) {
      Field f = (Field) c.expr();
      if (f.flags().isStatic() && f.flags().isFinal()) {
        // need to replace static field with constant value
        // since static fields are translated to calls
        if (!f.constantValueSet())
          throw new InternalCompilerError("Expected constant value.");
        Object val = f.constantValue();
        Lit e;
        if (val instanceof Integer) {
          e =
              rewriter.nodeFactory().IntLit(f.position(), IntLit.INT,
                  ((Integer) val).longValue());
        } else if (val instanceof Short) {
          e =
              rewriter.nodeFactory().IntLit(f.position(), IntLit.INT,
                  ((Short) val).longValue());
        } else if (val instanceof Character) {
          e =
              rewriter.nodeFactory().CharLit(f.position(),
                  ((Character) val).charValue());
        } else if (val instanceof Byte) {
          e =
              rewriter.nodeFactory().IntLit(f.position(), IntLit.INT,
                  ((Byte) val).longValue());
        } else {
          throw new InternalCompilerError("Unexpected type: " + val.getClass());
        }
        return c.expr(e);
      }
    }
    return c;
  }
}
