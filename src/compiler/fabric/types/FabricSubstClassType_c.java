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

import java.net.URI;

import jif.types.JifSubst;
import jif.types.JifSubstClassType_c;
import jif.types.JifTypeSystem;
import jif.types.label.ConfPolicy;
import jif.types.label.Label;
import polyglot.types.ClassType;
import polyglot.types.SemanticException;
import polyglot.util.Position;
import codebases.types.CodebaseClassType;

public class FabricSubstClassType_c extends JifSubstClassType_c implements
    FabricSubstType {
  public FabricSubstClassType_c(JifTypeSystem ts, Position pos, ClassType base,
      JifSubst subst) {
    super(ts, pos, base, subst);
  }

  protected ConfPolicy accessPolicy;

  @Override
  public Label updateLabel() {
    FabricParsedClassType base = (FabricParsedClassType) base();
    Label l = base.updateLabel();
    if (l == null) return null;

    JifSubst subst = (JifSubst) subst();
    return subst.substLabel(base.updateLabel());
  }

  @Override
  public ConfPolicy accessPolicy() throws SemanticException {
    if (accessPolicy == null) accessPolicy = defaultAccessPolicy();
    return accessPolicy;
  }

  @Override
  public URI canonicalNamespace() {
    return ((CodebaseClassType) base).canonicalNamespace();
  }

  protected ConfPolicy defaultAccessPolicy() throws SemanticException {
    FabricParsedClassType base = (FabricParsedClassType) base();
    ConfPolicy c = base.accessPolicy();
    if (c == null) return null;
    FabricTypeSystem fts = (FabricTypeSystem) ts;
    Label l = fts.toLabel(c);
    JifSubst subst = (JifSubst) subst();
    return subst.substLabel(l).confProjection();
  }
}
