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

/**
 * A chain of command sources.
 */
public class ChainedCommandSource extends CommandSource {
  protected final CommandSource[] sources;
  protected int curSourceNum;
  protected final boolean exitOnAnyError;

  public ChainedCommandSource(CommandSource... sources) {
    this(false, sources);
  }

  public ChainedCommandSource(boolean exitOnAnyError, CommandSource... sources) {
    this.sources = sources;
    this.curSourceNum = 0;
    this.exitOnAnyError = exitOnAnyError;
  }

  @Override
  public List<String> getNextCommand(List<String> buf) {
    while (curSourceNum < sources.length) {
      List<String> result = sources[curSourceNum].getNextCommand(buf);
      if (result != null) return result;

      // Current source has dried up. Move to the next one.
      curSourceNum++;
    }

    return null;
  }

  @Override
  public boolean reportError(String message) {
    boolean curSourceExited = sources[curSourceNum].reportError(message);
    if (curSourceExited) curSourceNum++;
    return exitOnAnyError;
  }
}
