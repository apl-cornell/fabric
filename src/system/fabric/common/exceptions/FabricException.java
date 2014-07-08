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
 * A <code>FabricException</code> represents an error that may be communicated
 * by the store to a worker.
 */
public class FabricException extends Exception {
  public FabricException() {
  }

  public FabricException(String msg) {
    super(msg);
  }

  public FabricException(Throwable cause) {
    super(cause);
  }

  public FabricException(String message, Throwable cause) {
    super(message, cause);
  }
}
