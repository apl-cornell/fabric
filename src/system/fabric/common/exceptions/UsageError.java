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

/**
 * An exception used to indicate an command-line usage error.
 */
public class UsageError extends Exception {
  public final int exitCode;
  public final boolean showSecretMenu;

  public UsageError(String s) {
    this(s, 1);
  }

  public UsageError(String s, int exitCode) {
    this(s, exitCode, false);
  }

  public UsageError(String s, int exitCode, boolean showSecretMenu) {
    super(s);
    this.exitCode = exitCode;
    this.showSecretMenu = showSecretMenu;
  }
}
