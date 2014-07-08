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
package fabric.worker;

import java.util.Collections;
import java.util.List;

import fabric.common.exceptions.InternalError;
import fabric.net.RemoteNode;

public class TransactionAtomicityViolationException extends InternalError {
  public final List<RemoteNode> failed;
  public final List<RemoteNode> unreachable;

  public TransactionAtomicityViolationException(List<RemoteNode> failed,
      List<RemoteNode> unreachable) {
    this.failed = Collections.unmodifiableList(failed);
    this.unreachable = Collections.unmodifiableList(unreachable);
  }

  public TransactionAtomicityViolationException() {
    this.failed = Collections.emptyList();
    this.unreachable = Collections.emptyList();
  }

}
