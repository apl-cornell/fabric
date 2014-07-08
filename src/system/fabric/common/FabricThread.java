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
package fabric.common;

import fabric.worker.transaction.TransactionManager;

public interface FabricThread {
  TransactionManager getTransactionManager();

  void setTransactionManager(TransactionManager tm);

  public static abstract class AbstractImpl extends Thread implements
      FabricThread {
    private TransactionManager tm;

    protected AbstractImpl() {
    }

    protected AbstractImpl(String name) {
      super(name);
    }

    public TransactionManager getTransactionManager() {
      return tm;
    }

    public void setTransactionManager(TransactionManager tm) {
      this.tm = tm;
    }
  }
}
