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
package fabric.types;

import jif.translate.JifToJavaRewriter;
import jif.translate.PrincipalToJavaExpr_c;
import jif.types.principal.ExternalPrincipal;
import jif.types.principal.Principal;
import polyglot.ast.Expr;
import polyglot.types.SemanticException;

/**
 * 
 */
public class FabExternalPrincipalToJavaExpr_c extends PrincipalToJavaExpr_c {

  @Override
  public Expr toJava(Principal principal, JifToJavaRewriter rw)
      throws SemanticException {
    ExternalPrincipal P = (ExternalPrincipal) principal;
    return rw.qq().parseExpr("fabric.principals.%s.getInstance()", P.name());
  }
}
