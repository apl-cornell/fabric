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
package fabric.common.exceptions;

import java.security.GeneralSecurityException;

/**
 * A wrapper for java.security.GeneralSecurityException. This is here to convey
 * GeneralSecurityExceptions over the network.
 */
public class FabricGeneralSecurityException extends FabricException {
  public FabricGeneralSecurityException(GeneralSecurityException cause) {
    super(cause);
  }

  @Override
  public GeneralSecurityException getCause() {
    return (GeneralSecurityException) super.getCause();
  }
}
