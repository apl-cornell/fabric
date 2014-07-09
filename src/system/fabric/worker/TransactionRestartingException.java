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
package fabric.worker;

import fabric.common.TransactionID;
import fabric.common.exceptions.FabricRuntimeException;

/**
 * Indicates that the current transaction should be restarted.
 */
public final class TransactionRestartingException extends
    FabricRuntimeException {
  /**
   * Identifies the transaction that is to be restarted.
   */
  public final TransactionID tid;

  /**
   * @param tid
   *          identifies the transaction that is to be restarted.
   */
  public TransactionRestartingException(TransactionID tid) {
    super("restarting " + tid);
    this.tid = tid;
  }
}
