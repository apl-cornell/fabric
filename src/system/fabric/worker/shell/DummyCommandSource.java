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
package fabric.worker.shell;

import java.util.List;

import fabric.common.Logging;

/**
 * A command source that blocks indefinitely when asked for a command.
 */
public class DummyCommandSource extends CommandSource {

  private static Object condVar = new Object();

  public static void signalToQuit() {
    synchronized (condVar) {
      condVar.notifyAll();
    }
  }

  @Override
  public List<String> getNextCommand(List<String> buf) {
    try {
      synchronized (condVar) {
        condVar.wait();
      }
    } catch (InterruptedException e) {
      Logging.logIgnoredInterruptedException(e);
    }

    return null;
  }

  @Override
  public boolean reportError(String message) {
    err.println("Error: " + message);
    return false;
  }

}
