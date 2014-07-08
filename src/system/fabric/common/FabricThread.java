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
package fabric.common;

import fabric.worker.transaction.TransactionManager;

/**
 * A FabricThread is a thread that has an associated TransactionManager. This
 * class exists because java.lang.ThreadLocal has a poor implementation that is
 * too slow. FabricThread is an interface because fabric code may extend a class
 * which is itself a subclass of Thread. In this case, the class cannot also be
 * made a subclass of FabricThread (if it were a class) without rewriting the
 * parent class.
 */
public interface FabricThread {
  TransactionManager getTransactionManager();

  void setTransactionManager(TransactionManager tm);

  public static class Impl extends Thread implements FabricThread {
    private TransactionManager tm;

    public Impl() {
      setDaemon(true);
    }

    public Impl(String name) {
      super(name);
      setDaemon(true);
    }

    public Impl(Runnable target) {
      super(target);
      setDaemon(true);
    }

    public Impl(Runnable target, String name) {
      super(target, name);
      setDaemon(true);
    }

    @Override
    public TransactionManager getTransactionManager() {
      return this.tm;
    }

    @Override
    public void setTransactionManager(TransactionManager tm) {
      this.tm = tm;
    }
  }
}
