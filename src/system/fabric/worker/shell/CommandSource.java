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
package fabric.worker.shell;

import java.io.PrintStream;
import java.util.List;

/**
 * Encapsulates a source of commands for the worker shell.
 */
public abstract class CommandSource {
  protected final PrintStream err;

  public CommandSource() {
    this(System.err);
  }

  public CommandSource(PrintStream err) {
    this.err = err;
  }

  /**
   * @param command
   *          a list into which the command will be parsed.
   * @return the parsed command, or null if there are no more commands.
   */
  public abstract List<String> getNextCommand(List<String> buf);

  /**
   * Reports an error back to the user.
   * 
   * @return whether the shell should exit.
   */
  public abstract boolean reportError(String message);

  /**
   * Reports an error back to the user.
   * 
   * @return whether the shell should exit.
   */
  public boolean reportError(HandlerException e) {
    boolean result = reportError(e.getMessage());
    if (e.getCause() != null) e.getCause().printStackTrace(err);
    return result;
  }
}
