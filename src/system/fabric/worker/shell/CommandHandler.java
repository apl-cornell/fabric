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
package fabric.worker.shell;

import java.util.List;

abstract class CommandHandler {
  /**
   * A description of the parameters supported by this command.
   */
  final String params;

  /**
   * A description of how to use this command.
   */
  final String usage;

  public CommandHandler(String usage) {
    this(null, usage);
  }

  public CommandHandler(String params, String usage) {
    this.params = params;
    this.usage = usage;
  }

  /**
   * Executes the command with the given arguments.
   */
  public abstract void handle(List<String> args) throws HandlerException;
}
