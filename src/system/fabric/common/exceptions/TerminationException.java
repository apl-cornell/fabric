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
package fabric.common.exceptions;

/**
 * This exception signals termination of the Fabric node. It should be used
 * instead of <code>System.exit</code> to allow Fabric to be started from
 * within a JVM that wasn't started specifically for Fabric, e.g., the Apache
 * ANT framework.
 */
public class TerminationException extends RuntimeException {
  final public int exitCode;

  public TerminationException(String msg) {
    this(msg, 1);
  }

  public TerminationException(int exit) {
    this.exitCode = exit;
  }

  public TerminationException(String msg, int exit) {
    super(msg);
    this.exitCode = exit;
  }

  public TerminationException(Exception cause, int exit) {
    super(cause);
    this.exitCode = exit;
  }
}