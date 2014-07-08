/**
 * Copyright (C) 2010 Fabric project group, Cornell University
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

import fabric.worker.Store;

/**
 * An <code>AccessError</code> represents an authorisation error.
 */
public class AccessException extends FabricException {
  public AccessException(Store store, long onum) {
    this(store.name(), onum);
  }

  public AccessException(String storeName, long onum) {
    super("Error accessing fab://" + storeName + "/" + onum);
  }

  public AccessException(String message) {
    super(message);
  }
}
