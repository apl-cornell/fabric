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
package fabric.common.exceptions;

import fabric.lang.security.Principal;
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

  public AccessException(Principal accessor, fabric.lang.Object accessee) {
    this("access", accessor, accessee);
  }

  public AccessException(String accessType, Principal accessor,
      fabric.lang.Object accessee) {
    super(message(accessType, accessor, accessee));
  }

  private static String message(String accessType, Principal accessor,
      fabric.lang.Object accessee) {

    String msg = "Access Denied.  \n";
    msg += "Principal " + accessor.name();
    msg +=
        " is not trusted to " + accessType + " object " + accessee.$getOnum()
            + "@" + accessee.$getStore().name() + ".\n";
    msg += "The object's label is " + accessee.get$$updateLabel() + ".\n";
    msg += "The object's type is  " + accessee.getClass() + ".\n";
    return msg;
  }
}
