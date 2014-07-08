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

/**
 * A source of commands that has been pre-tokenized.
 */
public class TokenizedCommandSource extends CommandSource {
  protected int nextToken;
  protected final String[] tokens;

  /**
   * @param tokens
   *          an array wherein each element is an individual token.
   */
  public TokenizedCommandSource(String[] tokens) {
    this.tokens = tokens;
    this.nextToken = 0;
  }

  @Override
  public List<String> getNextCommand(List<String> buf) {
    if (nextToken >= tokens.length) return null;

    buf.clear();
    while (nextToken < tokens.length) {
      String token = tokens[nextToken++];
      if (";".equals(token)) return buf;
      buf.add(token);
    }

    return buf;
  }

  @Override
  public boolean reportError(String message) {
    err.println("Error: " + message);
    return true;
  }

}
