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
package fabric.types;

import java.net.URI;

import jif.translate.LabelToJavaExpr;
import jif.types.JifClassType;
import jif.types.label.ProviderLabel_c;
import polyglot.types.ParsedClassType;
import codebases.frontend.ExtensionInfo;
import codebases.types.CodebaseClassType;

public class FabricProviderLabel_c extends ProviderLabel_c {

  public FabricProviderLabel_c(JifClassType classType, LabelToJavaExpr toJava) {
    super(classType, toJava);

    // Classes published to Fabric should never have trusted providers.
    if (classType instanceof ParsedClassType) {
      ExtensionInfo extInfo =
          (ExtensionInfo) classType.typeSystem().extensionInfo();
      CodebaseClassType cbct = (CodebaseClassType) classType;
      URI ns = cbct.canonicalNamespace();
      // If this class isn't from the local or platform namespace,
      // then we should never trust it.
      if (!ns.equals(extInfo.localNamespace())
          && !ns.equals(extInfo.platformNamespace())) {
        this.isTrusted = false;
      }
    }
  }
}
