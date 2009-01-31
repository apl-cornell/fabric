package fabric.core;

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

  /** The set of created objects */
  public Collection<SerializedObject> creates;

  /** The collection of modified objects */
  public Collection<SerializedObject> writes;

  /** The object numbers and version numbers of the read objects */
  public LongKeyMap<Integer> reads;

  /** Create a PrepareRequest with the provided fields */
  public PrepareRequest(Collection<SerializedObject> creates,
      Collection<SerializedObject> writes, LongKeyMap<Integer> reads) {
    this.creates = creates;
    this.writes = writes;
    this.reads = reads;
  }

}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0
 */
