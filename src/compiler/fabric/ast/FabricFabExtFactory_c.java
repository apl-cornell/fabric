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
package fabric.ast;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;
import fabric.extension.ClassBodyExt_c;
import fabric.extension.FabricExt;
import fabric.extension.NewExt_c;
import fabric.extension.NewFabricArrayExt_c;
import fabric.extension.NewLabelExt_c;
import fabric.extension.NodeExt_c;
import fabric.extension.PrincipalExprExt_c;

/**
 * This class constructs {@link FabricExt} objects for the Fabric language
 * constructs.
 * 
 * @author mdgeorge
 */
public class FabricFabExtFactory_c extends AbstractFabExtFactory_c {

  public FabricFabExtFactory_c() {
    super();
  }

  public FabricFabExtFactory_c(ExtFactory next) {
    super(next);
  }

  @Override
  protected Ext extClassBodyImpl() {
    return new ClassBodyExt_c();
  }

  @Override
  protected FabricExt extNodeImpl() {
    return new NodeExt_c();
  }

  @Override
  protected FabricExt extNewImpl() {
    return new NewExt_c();
  }

  @Override
  protected FabricExt extNewFabricArrayImpl() {
    return new NewFabricArrayExt_c();
  }

  @Override
  protected FabricExt extNewLabelImpl() {
    return new NewLabelExt_c();
  }

  @Override
  protected Ext extPrincipalExprImpl() {
    return new PrincipalExprExt_c();
  }
}
