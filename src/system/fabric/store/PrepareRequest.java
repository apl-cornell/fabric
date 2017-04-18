package fabric.store;

import java.util.Collection;

import fabric.common.SerializedObject;
import fabric.common.util.LongKeyMap;
import fabric.common.util.Pair;

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

  /**
   * The collection of modified objects paired with whether it's intended as an
   * extension
   */
  public final Collection<Pair<SerializedObject, Boolean>> writes;

  /** The object numbers and version numbers of the read objects */
  public final LongKeyMap<Integer> reads;

  /** Create a PrepareRequest with the provided fields */
  public PrepareRequest(long tid, Collection<SerializedObject> creates,
      Collection<Pair<SerializedObject, Boolean>> writes,
      LongKeyMap<Integer> reads) {
    this.tid = tid;
    this.creates = creates;
    this.writes = writes;
    this.reads = reads;
  }

}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0
 */
