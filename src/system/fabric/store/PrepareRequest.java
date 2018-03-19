package fabric.store;

import java.util.Collection;
import java.util.Set;

import fabric.common.SerializedObject;
import fabric.common.util.LongKeyMap;
import fabric.common.util.LongSet;
import fabric.common.util.Oid;
import fabric.common.util.Pair;
import fabric.worker.metrics.ExpiryExtension;

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
   * The collection of modified objects
   */
  public final Collection<SerializedObject> writes;

  /** The object numbers, version numbers, and expiries of the read objects */
  public final LongKeyMap<Pair<Integer, Long>> reads;

  /**
   * The collection of extensions
   */
  public final Collection<ExpiryExtension> extensions;

  /**
   * The delayed extensions triggered by this transaction.
   * Maps onums being committed to oids that will be extended.
   */
  public final LongKeyMap<Set<Oid>> extensionsTriggered;

  /**
   * The delayed extensions with no trigger that should run at the store after
   * this commits.
   */
  public final LongSet delayedExtensions;

  /** Create a PrepareRequest with the provided fields */
  public PrepareRequest(long tid, Collection<SerializedObject> creates,
      Collection<SerializedObject> writes,
      LongKeyMap<Pair<Integer, Long>> reads,
      Collection<ExpiryExtension> extensions,
      LongKeyMap<Set<Oid>> extensionsTriggered, LongSet delayedExtensions) {
    this.tid = tid;
    this.creates = creates;
    this.writes = writes;
    this.reads = reads;
    this.extensions = extensions;
    this.extensionsTriggered = extensionsTriggered;
    this.delayedExtensions = delayedExtensions;
  }

}

/*
 * * vim: ts=2 sw=2 et cindent cino=\:0
 */
