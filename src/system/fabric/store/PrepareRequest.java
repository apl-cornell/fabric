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
package fabric.store;

import java.util.Collection;

import fabric.common.SerializedObject;
import fabric.common.util.LongKeyMap;

/**
 * A convenience class for grouping together the created, modified, and read
 * object sets of a prepare request.
 * 
 * @author mdgeorge
 */
public final class PrepareRequest {
  public final long tid;

  /** The set of created objects */
  public final Collection<SerializedObject> creates;

  /** The collection of modified objects */
  public final Collection<SerializedObject> writes;

  /** The object numbers and version numbers of the read objects */
  public final LongKeyMap<Integer> reads;

  /** The commit time of the transaction, as proposed by the worker */
  public final long commitTime;

  /** Create a PrepareRequest with the provided fields */
  public PrepareRequest(long tid, long commitTime,
      Collection<SerializedObject> creates,
      Collection<SerializedObject> writes, LongKeyMap<Integer> reads) {
    this.tid = tid;
    this.commitTime = commitTime;
    this.creates = creates;
    this.writes = writes;
    this.reads = reads;
  }

}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0
 */
