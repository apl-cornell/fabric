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
package fabric.lang;

/**
 * <p>
 * A Statistics encapsulates the history of read and write requests for an
 * object. It is maintained at the Store and is used to generate promise
 * durations for the object.
 * </p>
 * <p>
 * Statistics objects are created by the
 * {@link fabric.lang.Object#createStatistics} method. They are only heuristic
 * information and may be discarded and recreated at any time.
 * </p>
 */
public interface Statistics {
  /** Called whenever a transaction that read the object is committed */
  void commitRead();

  /** Called whenever a transaction that wrote the object is committed */
  void commitWrote();

  /**
   * Determine the duration of a promise to issue. While a promise is
   * outstanding, the store will not permit updates to the object to commit.
   * This allows read-only transactions to proceed without contacting the store.
   * 
   * @return the duration, in milliseconds, of the promise.
   */
  int generatePromise();
}
