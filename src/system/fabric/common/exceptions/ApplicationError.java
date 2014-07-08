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
package fabric.common.exceptions;

public class ApplicationError extends Error {
  public ApplicationError() {
    report();
  }

  public ApplicationError(Throwable cause) {
    super(cause);
    report();
  }

  public ApplicationError(String msg) {
    super(msg);
    report();
  }

  public ApplicationError(String msg, Throwable cause) {
    super(msg, cause);
    report();
  }

  private void report() {
    System.err.println("Creating ApplicationError exception:");
    System.err.println("================ BEGIN STACK TRACE ================");
    printStackTrace();
    System.err.println("================= END STACK TRACE =================");
    System.err.println();
  }
}
