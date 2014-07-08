/**
 * Copyright (C) 2010-2013 Fabric project group, Cornell University
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
package fabric.translate;

import jif.translate.JifToJavaRewriter;
import jif.translate.ProviderLabelToJavaExpr_c;
import jif.types.label.Label;
import jif.types.label.ProviderLabel;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import fabil.ast.FabILNodeFactory;

public class ProviderLabelToFabilExpr_c extends ProviderLabelToJavaExpr_c {

  @Override
  public Expr toJava(Label label, JifToJavaRewriter rw)
      throws SemanticException {
    FabILNodeFactory nf = (FabILNodeFactory) rw.nodeFactory();
    ProviderLabel provider = (ProviderLabel) label;
    if (provider.isTrusted()) {
      return label.typeSystem().bottomLabel().toJava(rw);
    }

    Position pos = provider.position();
    if (pos == null) pos = Position.compilerGenerated();
    return nf.providerLabel(pos, rw.typeToJava(provider.classType(), pos));
  }

}
